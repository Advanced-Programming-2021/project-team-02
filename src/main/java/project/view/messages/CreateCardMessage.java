package project.view.messages;

import javafx.scene.control.Alert;

public enum CreateCardMessage {

    FILL_THE_BLANKS("you need to fill all the parts", Alert.AlertType.ERROR),
    SELECT_EFFECT("you should select an effect", Alert.AlertType.ERROR),
    SELECT_TYPE("you should select a type", Alert.AlertType.ERROR),
    YOU_SHOULD_ENTER_INTEGER("you should write a number", Alert.AlertType.ERROR),
    PAY_ATTENTION_TO_MINIMUMS("Minimum of attack and defense : 1000 and Minimum of level : 2", Alert.AlertType.ERROR),
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
