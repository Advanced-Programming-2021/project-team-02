package project.view.messages;

import javafx.scene.control.Alert;

public enum DeckMenuMessage {
    DECK_DELETED("ِDeck deleted successfully", Alert.AlertType.CONFIRMATION),
    DECK_ACTIVATED("Deck has been activated", Alert.AlertType.CONFIRMATION),
    DECK_ADDED("Deck has been added", Alert.AlertType.CONFIRMATION),
    DECK_DOES_NOT_EXIST("there is no deck with this name", Alert.AlertType.ERROR);

    private final String label;
    private final Alert.AlertType alertType;

    DeckMenuMessage(String label, Alert.AlertType alertType) {
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
