module AP.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;

    opens view to javafx.fxml;
    exports view;
}