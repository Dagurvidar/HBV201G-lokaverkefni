package vidmot.interviewtrainer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import vinnsla.FeedbackService;

import java.io.IOException;

public class SvarDialogController extends Dialog<String> {
    @FXML
    public Label fxSpurning;
    @FXML
    public TextArea fxTextArea;
    @FXML
    public Label fxFeedbackLabel;

    public SvarDialogController(String selectedQuestion) {
        setTitle("Answer a question and get a reply from our chatbot ");

        DialogPane dialogPane = loadDialogPane();
        setDialogPane(dialogPane);

        fxFeedbackLabel.setText("Press Enter to get feedback");
        fxSpurning.setText(selectedQuestion);

        fxTextArea.setOnKeyPressed(this::handleEnterPress);

        setResultConverter(button -> {
            if (button == ButtonType.OK) {
                String answer = fxTextArea.getText();
                String feedback = null;
                try {
                    feedback = FeedbackService.provideFeedback(fxSpurning.getText(), answer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fxFeedbackLabel.setText(feedback);
                return answer;
            }
            return null;
        });
    }

    private void handleEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String userAnswer = fxTextArea.getText();
            new Thread(() -> {
                try {
                    javafx.application.Platform.runLater(() -> fxFeedbackLabel.setText("Getting feedback..."));
                    String feedback = FeedbackService.provideFeedback(fxSpurning.getText(), userAnswer);
                    javafx.application.Platform.runLater(() -> fxFeedbackLabel.setText(feedback));
                } catch (IOException e) {
                    javafx.application.Platform.runLater(() -> fxFeedbackLabel.setText("Error fetching AI feedback"));
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private DialogPane loadDialogPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vidmot/interviewtrainer/svar-view.fxml"));
            loader.setController(this);
            return loader.load();
        } catch (IOException e) {
            System.err.println("Error loading SvarDialogController \n" + e.getMessage());
            ;
            return null;
        }
    }
}
