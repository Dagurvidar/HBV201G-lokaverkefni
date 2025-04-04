module vidmot.interviewtrainer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens vidmot.interviewtrainer to javafx.fxml;
    exports vidmot.interviewtrainer;
}