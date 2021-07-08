package project.view.messages;

import javafx.scene.control.Alert;

public enum DeckMenuMessage {
    DECK_DELETED("ِDeck deleted successfully", Alert.AlertType.INFORMATION),
    DECK_ACTIVATED("Deck has been activated", Alert.AlertType.INFORMATION),
    DECK_ADDED("Deck has been added", Alert.AlertType.INFORMATION),
    DECK_ALREADY_EXIST("Deck is already exist", Alert.AlertType.ERROR),
    DECK_DOES_NOT_EXIST("there is no deck with this name", Alert.AlertType.ERROR),
    CARD_DOES_NOT_EXIST("there is no card with this name", Alert.AlertType.ERROR),
    CARD_DELETED("ِCard deleted successfully", Alert.AlertType.INFORMATION),
    CARD_ADDED_TO_SIDE("Card successfully added to side deck", Alert.AlertType.INFORMATION),
    CARD_ADDED_TO_MAIN("Card successfully added to main deck", Alert.AlertType.INFORMATION),
    MAXIMUM("you have maximum number of this card", Alert.AlertType.ERROR),
    DECK_FULL("deck is full", Alert.AlertType.ERROR),
    DECK_MAXIMUM_NUMBER("you have been reached the maximum number", Alert.AlertType.ERROR),
    YOU_DID_NOT_SELECT_ANY_CARD("you should select a cards first", Alert.AlertType.ERROR);

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
