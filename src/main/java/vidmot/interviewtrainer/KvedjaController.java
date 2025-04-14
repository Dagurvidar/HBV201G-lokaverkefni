package vidmot.interviewtrainer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Viðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Controller fyrir kveðju-senuna í Interview Trainer forritinu.
 *            Hér getur notandi annað hvort hætt í forritinu eða farið aftur í
 *            spurningasíðuna.
 *
 *****************************************************************************/
public class KvedjaController {

    /**
     * Lokar forritinu þegar notandi velur að hætta.
     */
    public void haetta() {
        InterviewApplication.quit();
    }

    /**
     * Færir notenda aftur tilbaka í spurninga senuna
     *
     * @param event kveikt þegar notendi ýtir á takkan "Go Back"
     */
    @FXML
    private void tilbaka(ActionEvent event) {
        ViewSwitcher.switchTo(View.SPURNINGAR);
    }
}


