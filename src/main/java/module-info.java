module vidmot.interviewtrainer {
    requires javafx.controls;
    requires javafx.fxml;


    opens vidmot.interviewtrainer to javafx.fxml;
    exports vidmot.interviewtrainer;
}