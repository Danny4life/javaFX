module com.osiki.movie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.osiki.movie to javafx.fxml;
    exports com.osiki.movie;
}