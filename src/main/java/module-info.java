module AP.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    //requires javafx.media;
    requires gson;
    requires java.sql;
    requires opencsv;
    requires javafx.media;

    opens project.view to javafx.fxml;
    exports project.view;
    opens project.model to gson;
    exports project.model;
    opens project.model.card to gson;
    exports project.model.card;
    exports project.view.messages;
    opens project to javafx.fxml;
    exports project;
}