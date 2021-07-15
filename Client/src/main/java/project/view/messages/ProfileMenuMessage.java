package project.view.messages;

import javafx.scene.control.Alert;

public enum ProfileMenuMessage {
    SHORT_PASSWORD("Password must be at least 8 characters!", Alert.AlertType.ERROR),
    NICKNAME_CHANGED("Nickname changed!", Alert.AlertType.INFORMATION),
    NICKNAME_TAKEN("Nickname is already taken!", Alert.AlertType.ERROR),
    FILL_THE_FIELDS("Fill the fields!", Alert.AlertType.ERROR),
    CURRENT_PASSWORD("Your current password is wrong", Alert.AlertType.ERROR),
    SAME_PASSWORD("You have entered the same password", Alert.AlertType.ERROR),
    PASSWORD_CHANGED("Password changed!", Alert.AlertType.INFORMATION),
    ERROR_OCCURRED("Error occurred", Alert.AlertType.ERROR),
    INVALID_INPUT("your input can only contain characters and numbers", Alert.AlertType.ERROR);;
    private final String label;
    private final Alert.AlertType alertType;

    ProfileMenuMessage(String label, Alert.AlertType alertType) {
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
