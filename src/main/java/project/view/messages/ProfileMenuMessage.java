package project.view.messages;

import javafx.scene.control.Alert;

public enum ProfileMenuMessage {
    SHORT_USERNAME("Username must be at least 6 characters!", Alert.AlertType.ERROR),
    SHORT_PASSWORD("Password must be at least 8 characters!", Alert.AlertType.ERROR),
    USERNAME_CHANGED("Username changed!", Alert.AlertType.INFORMATION),
    NICKNAME_CHANGED("Nickname changed!", Alert.AlertType.INFORMATION),
    USERNAME_TAKEN("Username is already taken!", Alert.AlertType.ERROR),
    NICKNAME_TAKEN("Nickname is already taken!", Alert.AlertType.ERROR),
    INVALID_INPUT("Fill the fields!", Alert.AlertType.ERROR),
    CURRENT_PASSWORD("your current password is wrong", Alert.AlertType.ERROR),
    SAME_PASSWORD("you have entered the same password", Alert.AlertType.ERROR),
    PASSWORD_CHANGED("Password changed!",Alert.AlertType.INFORMATION);
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
