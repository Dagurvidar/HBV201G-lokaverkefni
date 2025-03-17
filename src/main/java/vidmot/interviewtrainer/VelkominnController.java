package vidmot.interviewtrainer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class VelkominnController {

    @FXML
    public void haetta() {
        ViewSwitcher.switchTo(View.KVEDJA);
    }

    @FXML
    public void skiptaISpurningar() {
        ViewSwitcher.switchTo(View.SPURNINGAR);
    }

}