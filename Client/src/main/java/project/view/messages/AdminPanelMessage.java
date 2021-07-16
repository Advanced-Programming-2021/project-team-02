package project.view.messages;

import javafx.scene.control.Alert;

public enum AdminPanelMessage {
    FORBID_CARD_SUCCESSFULLY("Card became forbidden successfully", Alert.AlertType.ERROR),
    MADE_CARD_AVAILABLE("Card became available successfully", Alert.AlertType.INFORMATION),
    INCREASED_SUCCESSFULLY("Card inventory increased successfully", Alert.AlertType.INFORMATION),
    DECREASED_SUCCESSFULLY("Card inventory decrease successfully", Alert.AlertType.INFORMATION),
    NOTHING_TO_DECREASE("No cards of this type", Alert.AlertType.ERROR),ERROR_OCCURRED("Error occurred", Alert.AlertType.ERROR);
    private String label;
    private Alert.AlertType alertType;

    AdminPanelMessage(String label, Alert.AlertType alertType) {
        this.label = label;
        this.alertType = alertType;
    }

    public String getLabel() {
        return label;
    }

    public Alert.AlertType getAlertType() {

        return this.alertType;
    }
}
