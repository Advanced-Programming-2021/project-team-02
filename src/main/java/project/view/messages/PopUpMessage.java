package project.view.messages;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;

public class PopUpMessage {
    private Alert alert;
    public PopUpMessage(Alert.AlertType alertType, String label) {
        if (alertType.equals (Alert.AlertType.ERROR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            this.alert = alert;
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-background-color: #9e376c; -fx-font-family: Matrix II;");
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 28; -fx-line-spacing: 7px;");
            alert.setContentText(label);
            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-border-radius: 10; -fx-background-color: #9e376c; -fx-font-size: 20; -fx-text-fill: white; -fx-border-color: white"));
            alert.show();
        } else if (alertType.equals (Alert.AlertType.INFORMATION)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            this.alert = alert;
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-font-family: Matrix II; -fx-background-color: #103188;");
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 28; -fx-line-spacing: 7px;");
            alert.setContentText(label);
            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 20; -fx-text-fill: white;"));
            alert.show();
        } else if (alertType.equals (Alert.AlertType.CONFIRMATION)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            this.alert = alert;
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-background-color: #103188; -fx-font-family: Matrix II;");
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 28; -fx-line-spacing: 7px;");
            alert.setContentText(label);
            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 20; -fx-text-fill: white;"));
            alert.showAndWait ();
        }
    }

    public Alert getAlert() {
        return alert;
    }
}
