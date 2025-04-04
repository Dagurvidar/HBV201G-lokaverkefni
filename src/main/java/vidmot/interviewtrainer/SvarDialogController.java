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
        setTitle("Svaraðu spurningu og fáðu endurgjöf frá " +
                "ekki-það-snjalla bottanum okkar!");

        DialogPane dialogPane = loadDialogPane();
        setDialogPane(dialogPane);

        fxFeedbackLabel.setText("Ýttu á Enter fyrir endurgjöf");
        fxSpurning.setText(selectedQuestion);

        fxTextArea.setOnKeyPressed(this::handleEnterPress);

        FeedbackService service = new FeedbackService();

        setResultConverter(button -> {
            if (button == ButtonType.OK) {
                String answer = fxTextArea.getText();
                String feedback = service.provideFeedback(answer);
                fxFeedbackLabel.setText(feedback);
                return answer;
            }
            return null;
        });
    }

    private void handleEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            FeedbackService service = new FeedbackService();
            String userAnswer = fxTextArea.getText();
            String feedback = service.provideFeedback(userAnswer);
            fxFeedbackLabel.setText(feedback);
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
