package project.view.messages;

import javafx.scene.control.Alert;

public enum CreateCardMessage {

    FILL_THE_BLANKS("You need to fill all the parts", Alert.AlertType.ERROR),
    SELECT_EFFECT("You should select an effect", Alert.AlertType.ERROR),
    SELECT_TYPE("You should select a type", Alert.AlertType.ERROR),
    YOU_SHOULD_ENTER_INTEGER("You should write a number", Alert.AlertType.ERROR),
    PAY_ATTENTION_TO_MINIMUMS("Minimum of attack and defense is 1000 and Minimum of level is 2", Alert.AlertType.ERROR),
    CHOOSE_TYPE("You should choose a type", Alert.AlertType.ERROR),
    REPEATED_NAME("There is a card already with this name", Alert.AlertType.ERROR),
    CARD_CREATED("Card created successfully", Alert.AlertType.INFORMATION);

    private final String label;
    private final Alert.AlertType alertType;

    CreateCardMessage(String label, Alert.AlertType alertType) {
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
