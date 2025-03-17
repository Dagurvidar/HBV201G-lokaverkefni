package vidmot.interviewtrainer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class InterviewApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        var scene = new Scene(new Pane(), 600, 400);
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.VELKOMINN);

        stage.setTitle("Interview Trainer");
        stage.setScene(scene);
        stage.show();
    }

    public static void quit() {
        if (primaryStage != null) {
            primaryStage.close();
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}