package vidmot.interviewtrainer;
/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Viðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Enum sem heldur utan um nöfn á FXML skrám fyrir mismunandi viðmót
 *            í Interview Trainer forritinu.
 *
 *****************************************************************************/
public enum View {
    VELKOMINN("velkominn-view.fxml"),
    SPURNINGAR("spurningar-view.fxml"),
    KVEDJA("kvedja-view.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
