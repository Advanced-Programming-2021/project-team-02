package project.view.messages;

import javafx.scene.control.Alert;

public enum DeckMenuMessage {
    DECK_DELETED("Deck deleted successfully", Alert.AlertType.INFORMATION),
    DECK_ACTIVATED("Deck has been activated", Alert.AlertType.INFORMATION),
    DECK_ADDED("Deck has been added", Alert.AlertType.INFORMATION),
    DECK_ALREADY_EXIST("Deck already exists", Alert.AlertType.ERROR),
    DECK_DOES_NOT_EXIST("There is no deck with this name", Alert.AlertType.ERROR),
    CARD_DOES_NOT_EXIST("There is no card with this name", Alert.AlertType.ERROR),
    CARD_DELETED("Card deleted successfully", Alert.AlertType.INFORMATION),
    CARD_ADDED_TO_SIDE("Card successfully added to side deck", Alert.AlertType.INFORMATION),
    CARD_ADDED_TO_MAIN("Card successfully added to main deck", Alert.AlertType.INFORMATION),
    MAXIMUM("You have maximum number of this card", Alert.AlertType.ERROR),
    DECK_FULL("Deck is full", Alert.AlertType.ERROR),
    YOU_DID_NOT_SELECT_ANY_CARD("You should select a card first", Alert.AlertType.ERROR),
    DECK_MAXIMUM_NUMBER("you have been reached the maximum number", Alert.AlertType.ERROR);

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
