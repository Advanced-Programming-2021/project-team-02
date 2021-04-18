package view.messages;

public enum SuccessMessage {
    SHOW_CURRENT_MENU (""),
    REGISTER_SUCCESSFUL ("user created successfully!"),
    LOGIN_SUCCESSFUL ("user logged in successfully!"),
    LOGOUT ("user logged out successfully!"),
    NICKNAME_CHANGED ("nickname changed successfully!"),
    PASSWORD_CHANGED ("password changed successfully!"),
    DECK_CREATED (""),
    DECK_DELETED (""),
    DECK_ACTIVATED (""),
    CARD_ADDED_TO_THE_DECK (""),
    CARD_REMOVED (""),
    CARD_SELECTED (""),
    CARD_DESELECTED (""),
    //PHASE_NAME,
    //CARD_ADDED_TO_THE_HAND,
    //PLAYERS_TURN,
    SUMMONED_SUCCESSFULLY (""),
    SET_SUCCESSFULLY (""),
    POSITION_CHANGED_SUCCESSFULLY (""),
    FLIP_SUMMON_SUCCESSFUL (""),
    //OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK
    NO_DAMAGE_TO_ANYONE (""),
    //CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK,
    DEFENSIVE_MONSTER_DESTROYED (""),
    NO_CARD_DESTROYED (""),
    //DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_MONSTER,
    //DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DH_MONSTER,
    //OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK
    SPELL_ACTIVATED (""),
    //SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL(AFTER,BEFORE),
    WANTS_ACTIVE_SPELL (""),
    TRAP_ACTIVATED (""),
    COMPLETE_RITUAL_SUMMON (""),
    COMPLETE_SPECIAL_SUMMON (""),
    EMPTY_GRAVEYARD ("");
    //SURRENDER_MESSAGES (2_MESSAGES),
    private String value;

    SuccessMessage(String value) {
        setValue (value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
