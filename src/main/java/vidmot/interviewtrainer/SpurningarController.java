package vidmot.interviewtrainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import vinnsla.FeedbackService;
import vinnsla.Spurningar;

import java.util.Optional;
/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Viðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Controller fyrir spurningaviðmótið í Interview Trainer. Hér er hægt að skoða
 *            flokka af spurningum, velja spurningu og svara henni. Einnig er hægt að bæta
 *            við nýjum flokkum með aðstoð AI og fara til baka í annað viðmót.
 *
 *****************************************************************************/
public class SpurningarController {
    @FXML
    public Button fxSvara;
    @FXML
    public Label fxFjoldiSvaradraSpurninga;
    @FXML
    public ListView fxSvaradarSpurningar;
    @FXML
    private ListView<String> flokkarListView;
    @FXML
    private ListView<String> spurningarListView;

    private final ObservableList<String> items = FXCollections.observableArrayList();
    private int fjoldiSvaradraSpurninga = 0;
    private final Spurningar spurningar = new Spurningar();
    /**
     * Upphafsstillir viðmótið. Bætir spurningaflokkum í lista og setur hlustara fyrir val.
     */
    public void initialize() {
        fxSvara.setDisable(true);
        flokkarListView.setItems(spurningar.getFlokkar());
        fxSvaradarSpurningar.setItems(items);

        // athugar hvort og hvaða spurningaflokkur er valinn
        flokkarListView.getSelectionModel().selectedItemProperty().
                addListener(((obs, old, newCategory) -> {
                    if (newCategory != null) {
                        spurningarListView.setItems(spurningar.getSpurningarByCategory(newCategory));
                        System.out.println(newCategory + " category chosen");
                    }
                }));

        spurningarListView.getSelectionModel().selectedItemProperty().
                addListener((obs, old, newQuestion) -> {
                    fxSvara.setDisable(newQuestion == null);
                });
    }
    /**
     * Opnar dialog-glugga þar sem notandi getur slegið inn svar við valinni spurningu.
     * Uppfærir fjölda svaraðra spurninga og listann með þeim sem búið er að svara.
     */
    @FXML
    private void handleOpenDialog() {
        String selectedQuestion = spurningarListView.getSelectionModel().getSelectedItem();
        System.out.println("current selection is " + selectedQuestion);
        if (selectedQuestion != null) {
            SvarDialogController dialog = new SvarDialogController(selectedQuestion);
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(answer -> {
                fjoldiSvaradraSpurninga++;
                fxFjoldiSvaradraSpurninga.setText("Number of Question's answered: " +
                        Integer.toString(fjoldiSvaradraSpurninga));
                items.add(selectedQuestion);
                System.out.println("User answered: " + answer);
            });
        }
    }
    /**
     * Færir notanda aftur í upphafssenu (Velkomin).
     */
    public void faraTilBaka() {
        ViewSwitcher.switchTo(View.VELKOMINN);
    }
    /**
     * Færir notanda í kveðjuviðmótið þegar hætta er valið.
     */
    public void haetta() {
        ViewSwitcher.switchTo(View.KVEDJA);
    }
    /**
     * Opnar glugga sem leyfir notanda að búa til nýjan AI-spurningaflokk.
     * Ef gervigreind býr til spurningar, eru þær settar í nýjan flokk og
     * bætt við viðmótið.
     */
    @FXML
    private void handleNewAICategory() {
        NyFlokkurDialogController dialog = new NyFlokkurDialogController();
        Optional<NyFlokkurDialogController.CategoryResult> result = dialog.showAndWait();

        result.ifPresent(categoryResult -> {
            String categoryName = categoryResult.getCategoryName();
            ObservableList<String> questions = categoryResult.getQuestions();

            if (questions != null && !questions.isEmpty()) {
                spurningar.addCategory(categoryName, questions);
                flokkarListView.setItems(spurningar.getFlokkar());
                flokkarListView.getSelectionModel().select(categoryName);

                System.out.println("New AI category added: " + categoryName);
                System.out.println("With questions: " + questions);
            }
        });
    }
}
