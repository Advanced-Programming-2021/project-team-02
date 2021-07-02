package project.view.messages;

import javafx.scene.control.Alert;

public enum StartDuelMessage {
    INACTIVE_DECK("Your deck is inactive!", Alert.AlertType.ERROR),
    INVALID_DECK("Your deck is invalid!", Alert.AlertType.ERROR),
    SUCCESS("Successful", Alert.AlertType.INFORMATION);;

    private final String label;
    private final Alert.AlertType alertType;

    StartDuelMessage(String label, Alert.AlertType alertType) {
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
