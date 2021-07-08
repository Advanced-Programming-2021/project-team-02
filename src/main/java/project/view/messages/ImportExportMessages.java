package project.view.messages;

import javafx.scene.control.Alert;

public enum ImportExportMessages {

    CARD_NOT_EXPORTED("card did not exported", Alert.AlertType.ERROR),
    CARD_EXPORTED("card exported successfully", Alert.AlertType.INFORMATION);

    private final String label;
    private final Alert.AlertType alertType;

    ImportExportMessages(String label, Alert.AlertType alertType) {
        this.label = label;
        this.alertType = alertType;
    }

    public Alert.AlertType getAlertType() {
        return alertType;
    }

    public String getLabel() {
        return label;
    }
}
