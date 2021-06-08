module AP.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;
    requires gson;
    requires java.sql;
    requires opencsv;

    opens view to javafx.fxml;
    exports view;
    opens model to gson;
    exports model;
    opens model.card to gson;
    exports model.card;
    exports view.messages;
}