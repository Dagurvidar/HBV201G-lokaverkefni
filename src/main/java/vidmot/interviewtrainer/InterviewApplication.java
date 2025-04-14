package vidmot.interviewtrainer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Viðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Þetta er aðal JavaFX forritið sem ræsir upp viðmótið fyrir Interview Trainer.
 *            Hér er upphafssena sett og forritið sýnt notandanum. Einnig er til staðar aðferð
 *            til að loka forritinu
 *
 *****************************************************************************/
public class InterviewApplication extends Application {
    private static Stage primaryStage;

    /**
     * Ræsir JavaFX forritið og byrjar á því að sýna velkomin viðmót.
     *
     * @param stage Aðalsvið forritsins sem mun hýsa allar senur
     * @throws IOException ef FXML skrá hleðst ekki rétt inn
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        var scene = new Scene(new Pane(), 800, 500);
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.VELKOMINN);

        stage.setTitle("Interview Trainer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Lokar forritinu
     */
    public static void quit() {
        if (primaryStage != null) {
            primaryStage.close();
        }
        Platform.exit();
    }

    /**
     * Ræsir JavaFX forritið.
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }
}