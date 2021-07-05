package project.view.messages;

import javafx.scene.control.Alert;

public enum ShopMenuMessage {
    CARD_ADDED("Card successfully added", Alert.AlertType.INFORMATION),
    NOT_ENOUGH_MONEY("You don't have enough money", Alert.AlertType.ERROR);

    private final String label;
    private final Alert.AlertType alertType;

    ShopMenuMessage(String label, Alert.AlertType alertType) {
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
