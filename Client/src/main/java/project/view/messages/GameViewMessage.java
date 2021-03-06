package project.view.messages;

import javafx.scene.control.Alert;

public enum GameViewMessage {
    ACTION_NOT_ALLOWED("action not allowed in this phase", Alert.AlertType.ERROR),
    FULL_MONSTER_ZONE("Monster Zone is Full, cant summon!", Alert.AlertType.ERROR),
    USED_SUMMON_OR_SET("You have used summon or set!", Alert.AlertType.ERROR),
    NEED_MORE_TRIBUTE("You dont have enough cards to tribute", Alert.AlertType.ERROR),
    SELECT_CARD("Please select a card", Alert.AlertType.ERROR),
    NONE("", null),
    CARD_ALREADY_ACTIVATED("you have already activated this card", Alert.AlertType.ERROR),
    FULL_SPELL_ZONE("Spell Zone is Full, cant activate!", Alert.AlertType.ERROR),
    CAN_NOT_ACTIVE_TRAP_IN_HAND("you can’t activate effect of trap in hand", Alert.AlertType.ERROR),
    PREPARATIONS_IS_NOT_DONE("Preparations of this spell are not done yet", Alert.AlertType.ERROR),
    NOT_ENOUGH_CARD_TO_TRIBUTE("You dont have enough cards to tribute", Alert.AlertType.ERROR),
    MUST_DRAW_CARD("You must draw card!", Alert.AlertType.ERROR),
    CANCELED("", null),
    CAN_NOT_ATTACK_WITH_THIS_CARD("Cant attack with this card!", Alert.AlertType.ERROR),
    ATTACK_USED_BEFORE("Attack used before", Alert.AlertType.ERROR),
    NO_CARD_SELECTED("No card selected", Alert.AlertType.ERROR),
    CHOOSE_CARD_TO_ATTACK("Choose the card to be attacked", Alert.AlertType.INFORMATION),
    CANT_CHANGE_POSITION_OF_THIS_CARD("Can't change position of this card", Alert.AlertType.INFORMATION),
    USED_CHANGE_POSITION("You have used change position before!", Alert.AlertType.ERROR),
    WRONG_CHOICE("This is invalid choice", Alert.AlertType.ERROR),
    CAN_NOT_SET("Can not set this card!!!", Alert.AlertType.ERROR),
    CANT_SET_FIELD_CARD("Can't set a field card", Alert.AlertType.ERROR), CAN_NOT_RITUAL_SUMMON("Can not ritual summon", Alert.AlertType.ERROR),
    MUST_ACTIVE_RITUAL_SPELL_TO_RITUAL_SUMMON("Can ritual summon only by activating ritual spell", Alert.AlertType.ERROR),
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
