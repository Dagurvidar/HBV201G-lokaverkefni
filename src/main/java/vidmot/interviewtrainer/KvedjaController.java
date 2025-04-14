package vidmot.interviewtrainer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class KvedjaController {

    public void haetta() {
        InterviewApplication.quit();
    }

    /**
     * Færir notenda aftur tilbaka í spurninga senuna
     *
     * @param event kveikt þegar notendi ýtir á takkan
     */
    @FXML
    private void tilbaka(ActionEvent event) {
        ViewSwitcher.switchTo(View.SPURNINGAR);
    }
}


