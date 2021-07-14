module Client {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;
    requires gson;
    requires java.sql;
    requires opencsv;
    requires AnimateFX;

    opens project.view to javafx.fxml, gson;
    exports project.view;
    opens project.view.gameview;
    exports project.view.gameview;
    opens project.model to gson;
    exports project.model;
    opens project.model.card to gson;
    exports project.model.card;
    exports project.view.messages;
    opens project to javafx.fxml;
    exports project;
}