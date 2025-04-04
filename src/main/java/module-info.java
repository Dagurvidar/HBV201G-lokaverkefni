module vidmot.interviewtrainer {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires org.json;
    requires jdk.jsobject;
    requires java.dotenv;


    opens vidmot.interviewtrainer to javafx.fxml;
    exports vidmot.interviewtrainer;
}