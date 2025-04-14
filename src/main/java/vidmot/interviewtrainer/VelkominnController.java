package vidmot.interviewtrainer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Viðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Controller fyrir velkomin skjáinn. Hér getur notandi annað hvort farið
 *            í spurningarnar eða hætta í forritinu.
 *
 *****************************************************************************/
public class VelkominnController {
    /**
     * Fer í kveðjuskjá þegar notandi velur að Quit.
     */
    @FXML
    public void haetta() {
        ViewSwitcher.switchTo(View.KVEDJA);
    }

    /**
     * Skiptir yfir í spurningaskjáinn þegar notandi vill hefja æfinguna.
     */
    @FXML
    public void skiptaISpurningar() {
        ViewSwitcher.switchTo(View.SPURNINGAR);
    }

}