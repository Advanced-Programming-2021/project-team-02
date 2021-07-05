package project.view.messages;

import javafx.scene.control.Alert;

public enum GameViewMessage {
    ACTION_NOT_ALLOWED("action not allowed in this phase", Alert.AlertType.ERROR),
    FULL_MONSTER_ZONE("Monster Zone is Full, cant summon!", Alert.AlertType.ERROR),
    USED_SUMMON_OR_SET("You have used summon or set!", Alert.AlertType.ERROR),
    NEED_MORE_TRIBUTE("You dont have enough cards to tribute", Alert.AlertType.ERROR),
    SELECT_CARD("Please select a card", Alert.AlertType.ERROR),
    NONE("",null),
    SUCCESS("Success!", Alert.AlertType.INFORMATION);
    private final String label;
    private final Alert.AlertType alertType;

    GameViewMessage(String label, Alert.AlertType alertType) {
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
