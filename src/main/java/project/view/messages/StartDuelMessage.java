package project.view.messages;

import javafx.scene.control.Alert;

public enum StartDuelMessage {
    INACTIVE_DECK("You don't have active deck!", Alert.AlertType.ERROR),
    INVALID_DECK("Your deck is invalid!", Alert.AlertType.ERROR),
    SUCCESS("Successful", Alert.AlertType.INFORMATION),
    INVALID_USER_TO_PLAY_WITH("You can't play with user with this username", Alert.AlertType.ERROR),
    YOUR_INACTIVE_DECK("You don't have active deck!", Alert.AlertType.ERROR),
    YOUR_INVALID_DECK("Your deck is invalid!", Alert.AlertType.ERROR),
    OPPONENT_INACTIVE_DECK("Opponent doesn't have active deck!", Alert.AlertType.ERROR),
    OPPONENT_INVALID_DECK("Opponent deck is invalid!", Alert.AlertType.ERROR),
    FILL_OPPONENT_USERNAME("Please fill Opponent username field", Alert.AlertType.ERROR);

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
