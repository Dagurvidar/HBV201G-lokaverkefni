package vidmot.interviewtrainer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import vinnsla.FeedbackService;


import java.io.IOException;

/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Viðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Controller fyrir glugga sem leyfir notanda að búa til nýjan flokk af spurningum
 *            með hjálp gervigreindar. Notandi slær inn umræðuefni og fær til baka spurningar
 *            sem AI býr til út frá því efni.
 *
 *****************************************************************************/
public class NyFlokkurDialogController extends Dialog<NyFlokkurDialogController.CategoryResult> {

    @FXML
    private TextField fxTopicField;

    /**
     * Smiður sem setur upp dialog-glugga fyrir að búa til nýjan AI-spurningaflokk.
     * Skilar niðurstöðu með nafni og lista af spurningum ef input er gilt.
     */
    public NyFlokkurDialogController() {
        setTitle("Create AI Question Category");
        DialogPane dialogPane = loadDialogPane();
        setDialogPane(dialogPane);

        setResultConverter(button -> {
            if (button.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                String topic = fxTopicField.getText().trim();
                if (!topic.isEmpty()) {
                    try {
                        ObservableList<String> aiQuestions = FeedbackService.generateAIQuestions(topic);
                        String categoryName = "AI Generated: " + topic;
                        return new CategoryResult(categoryName, aiQuestions);
                    } catch (IOException e) {
                        showError("Error generating questions from AI.");
                        e.printStackTrace();
                    }
                }
            }
            return null;
        });
    }

    /**
     * Hleður FXML skjámynd fyrir dialoginn og tengir controller.
     *
     * @return DialogPane hluturinn sem var hlaðinn úr FXML
     */
    private DialogPane loadDialogPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vidmot/interviewtrainer/ny-flokkur-dialog.fxml"));
            loader.setController(this);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load dialog FXML", e);
        }
    }
    /**
     * Sýnir villuskilaboð þegar ekki tekst að búa til spurningar með AI.
     *
     * @param message Villuskilaboð sem birt eru notanda
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("AI Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Innri klasinn sem heldur utan um niðurstöðu dialogsins – þ.e. nafnið á flokknum
     * og spurningarnar sem gervigreindin bjó til.
     */
    public static class CategoryResult {
        private final String categoryName;
        private final ObservableList<String> questions;
        /**
         * Býr til niðurstöðuhlut með nafni og spurningum.
         *
         * @param categoryName Nafn á nýja flokknum
         * @param questions    Listi af spurningum sem AI bjó til
         */
        public CategoryResult(String categoryName, ObservableList<String> questions) {
            this.categoryName = categoryName;
            this.questions = questions;
        }
        /**
         * Skilar heiti flokksins.
         *
         * @return Nafn flokks
         */
        public String getCategoryName() {
            return categoryName;
        }
        /**
         * Skilar spurningunum sem tengjast flokknum.
         *
         * @return Listi af spurningum
         */
        public ObservableList<String> getQuestions() {
            return questions;
        }
    }
}
