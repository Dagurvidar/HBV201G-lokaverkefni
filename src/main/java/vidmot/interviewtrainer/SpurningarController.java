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

    public void faraTilBaka() {
        ViewSwitcher.switchTo(View.VELKOMINN);
    }

    public void haetta() {
        ViewSwitcher.switchTo(View.KVEDJA);
    }
}
