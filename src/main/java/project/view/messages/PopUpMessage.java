package project.view.messages;

import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class PopUpMessage {
    private static Stage stage;
    private Alert alert;
    public PopUpMessage(Alert.AlertType alertType, String label) {
        if (alertType.equals (Alert.AlertType.ERROR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            this.alert = alert;
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-background-color: #9e376c; -fx-font-family: \"Matrix II Regular\";");
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
            alert.setContentText(label);
            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
            buttonBar.getButtons().forEach(b->b.setCursor(Cursor.HAND));
            alert.initOwner(stage);
            alert.show();
        } else if (alertType.equals (Alert.AlertType.INFORMATION)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            this.alert = alert;
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
            alert.setContentText(label);
            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
            buttonBar.getButtons().forEach(b->b.setCursor(Cursor.HAND));
            alert.initOwner(stage);
            alert.show();
        } else if (alertType.equals (Alert.AlertType.CONFIRMATION)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            this.alert = alert;
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-background-color: #103188; -fx-font-family: \"Matrix II Regular\";");
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
            alert.setContentText(label);
            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
            buttonBar.getButtons().forEach(b->b.setCursor(Cursor.HAND));
            alert.initOwner(stage);
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
}
