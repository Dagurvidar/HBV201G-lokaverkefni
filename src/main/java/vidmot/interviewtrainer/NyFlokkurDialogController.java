package vidmot.interviewtrainer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import vinnsla.FeedbackService;


import java.io.IOException;

public class NyFlokkurDialogController extends Dialog<NyFlokkurDialogController.CategoryResult> {
    @FXML
    private TextField fxTopicField;

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

    private DialogPane loadDialogPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vidmot/interviewtrainer/ny-flokkur-dialog.fxml"));
            loader.setController(this);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load dialog FXML", e);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("AI Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class CategoryResult {
        private final String categoryName;
        private final ObservableList<String> questions;

        public CategoryResult(String categoryName, ObservableList<String> questions) {
            this.categoryName = categoryName;
            this.questions = questions;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public ObservableList<String> getQuestions() {
            return questions;
        }
    }
}
