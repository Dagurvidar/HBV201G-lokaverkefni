package vidmot.interviewtrainer;

public enum View {
    VELKOMINN("velkominn-view.fxml"),
    SPURNINGAR("spurningar-view.fxml"),
    SVAR("svar-view.fxml"),
    KVEDJA("kvedja-view.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
