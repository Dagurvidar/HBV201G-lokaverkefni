package vidmot.interviewtrainer;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import vinnsla.FeedbackService;

import java.io.IOException;
/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Viðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Controller fyrir dialog glugga þar sem notandi getur slegið inn svar við spurningu
 *            og fengið endurgjöf frá gervigreind. Þegar ýtt er á Enter eða OK hnappinn, birtist
 *            svar frá AI þjónustunni.
 *
 *****************************************************************************/
public class SvarDialogController extends Dialog<String> {
    @FXML
    public Label fxSpurning;
    @FXML
    public TextArea fxTextArea;
    @FXML
    public Label fxFeedbackLabel;

    /**
     * Smíðar dialog glugga með valinni spurningu og undirbýr viðmótið.
     *
     * @param selectedQuestion Spurning sem notandi á að svara
     */
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
                    feedback = FeedbackService.provideFeedback(fxSpurning.getText(),answer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fxFeedbackLabel.setText(feedback);
                return answer;
            }
            return null;
        });
    }

    /**
     * Sækir endurgjöf frá gervigreind þegar notandi ýtir á Enter í svarreitnum.
     *
     * @param event KeyEvent sem á sér stað þegar takki er ýttur
     */
    private void handleEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String userAnswer = fxTextArea.getText();
            new Thread(() -> {
                try {
                    String feedback = FeedbackService.provideFeedback(fxSpurning.getText(), userAnswer);
                    javafx.application.Platform.runLater(() -> fxFeedbackLabel.setText(feedback));
                } catch (IOException e) {
                    javafx.application.Platform.runLater(() -> fxFeedbackLabel.setText("Villa við að fá svar frá AI."));
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Hleður FXML skjánum fyrir svarsviðmótið og setur þennan controller sem stýringu.
     *
     * @return DialogPane hlutinn sem var hlaðinn, eða null ef villa átti sér stað
     */
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
