package view.messages;

public enum SuccessMessage {

    REGISTER_SUCCESSFUL("user created successfully!"),
    LOGIN_SUCCESSFUL("user logged in successfully!"),
    LOGOUT("user logged out successfully!"),
    NICKNAME_CHANGED("nickname changed successfully!"),
    PASSWORD_CHANGED("password changed successfully!"),
    DECK_CREATED("deck created successfully!"),
    DECK_DELETED("deck deleted successfully"),
    DECK_ACTIVATED("deck activated successfully"),
    CARD_ADDED_TO_THE_DECK("card added to deck successfully"),
    CARD_REMOVED("card removed form deck successfully"),
    CARD_SELECTED("card selected"),
    CARD_DESELECTED("card deselected"),
    //PHASE_NAME,
    CARD_ADDED_TO_THE_HAND("new card added to the hand : %s"),
    PLAYERS_TURN("its %s’s turn"),
    SUMMONED_SUCCESSFULLY("summoned successfully"),
    SET_SUCCESSFULLY("set successfully"),
    POSITION_CHANGED_SUCCESSFULLY("monster card position changed successfully"),
    FLIP_SUMMON_SUCCESSFUL("flip summoned successfully"),
    OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK("your opponent’s monster is destroyed and your opponent receives" +
            "%d battle damage"),
    NO_DAMAGE_TO_ANYONE("both you and your opponent monster cards are destroyed and no" +
            "one receives damage"),
    CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK("Your monster card is destroyed and you received %d battle" +
            "damage"),
    DEFENSIVE_MONSTER_DESTROYED("the defense position monster is destroyed"),
    NO_CARD_DESTROYED("no card is destroyed"),
    //DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_MONSTER,
    //DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DH_MONSTER,
    //OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK
    SPELL_ACTIVATED("spell activated"),
    //SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL(AFTER,BEFORE),
    WANTS_ACTIVE_SPELL("do you want to activate your trap and spell?"),
    TRAP_ACTIVATED("spell/trap activated"),
    COMPLETE_RITUAL_SUMMON("you should ritual summon right now"),
    COMPLETE_SPECIAL_SUMMON("you should special summon right now"),
    EMPTY_GRAVEYARD("graveyard empty");
    //SURRENDER_MESSAGES (2_MESSAGES),
    private String value;

    SuccessMessage(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
