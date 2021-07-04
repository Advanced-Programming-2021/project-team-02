package project.view.messages;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUpMessage {
    private static Stage stage;
    private static Parent parent;
    private Alert alert;

    public PopUpMessage(Alert.AlertType alertType, String label) {
        Alert alert;
        if (alertType.equals (Alert.AlertType.ERROR)) {
            alert = new Alert(Alert.AlertType.ERROR);
            setAlert(alert);
            alert.setContentText(label);
            alert.initOwner(stage);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initStyle(StageStyle.TRANSPARENT);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-background-color: #9e376c; -fx-font-family: \"Matrix II Regular\";");
            dialogPane.getScene().setFill(Color.TRANSPARENT);
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");

            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
            buttonBar.getButtons().forEach(b->b.setCursor(Cursor.HAND));

            alert.show();
        } else if (alertType.equals (Alert.AlertType.INFORMATION)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            setAlert(alert);
            alert.setContentText(label);
            alert.initOwner(stage);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initStyle(StageStyle.TRANSPARENT);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
            dialogPane.getScene().setFill(Color.TRANSPARENT);

            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
            buttonBar.getButtons().forEach(b->b.setCursor(Cursor.HAND));

            alert.show();
        } else if (alertType.equals (Alert.AlertType.CONFIRMATION)) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            setAlert(alert);
            alert.setContentText(label);
            alert.initOwner(stage);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initStyle(StageStyle.TRANSPARENT);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-background-color: #103188; -fx-font-family: \"Matrix II Regular\";");
            dialogPane.getScene().setFill(Color.TRANSPARENT);
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");

            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
            buttonBar.getButtons().forEach(b->b.setCursor(Cursor.HAND));

            alert.showAndWait ();
        }
    }

    public Alert getAlert() {
        return alert;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        PopUpMessage.stage = stage;
    }

    public static Parent getParent() {
        return parent;
    }

    public static void setParent(Parent parent) {
        PopUpMessage.parent = parent;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
