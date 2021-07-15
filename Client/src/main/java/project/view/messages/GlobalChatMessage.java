package project.view.messages;

import javafx.scene.control.Alert;

public enum GlobalChatMessage {
    WRITE_FIRST("You need to write something first", Alert.AlertType.ERROR);
    private final String label;
    private final Alert.AlertType alertType;

    GlobalChatMessage(String label, Alert.AlertType alertType) {
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
