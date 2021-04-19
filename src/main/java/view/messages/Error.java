package view.messages;

public enum Error {
    ENTER_MENU_BEFORE_LOGIN ("please login first"),
    BEING_ON_A_MENU ("menu navigation is not possible"),
    TAKEN_USERNAME ("user with username %s already exists\n"),
    TAKEN_NICKNAME ("user with nickname %s already exists\n"),
    INCORRECT_USERNAME ("Username and password didn't match!"),
    INCORRECT_PASSWORD ("Username and password didn't match!"),
    INVALID_CURRENT_PASSWORD ("current password is invalid"),
    SAME_PASSWORD ("please enter a new password"),
    DECK_EXIST("deck with name %s already exists"),
    DECK_NOT_EXIST("deck with name %s does not exist"),
    //INCORRECT_CARD_NAME(""),
    //DECK_IS_FULL(""),
    //EXCESSIVE_NUMBER_ALLOWED (MORE THAN 3 OF ONE CARD),
    //CARD_DOES_NOT_EXIST_IN_DECK,
    CARD_DOES_NOT_EXIST ("there is no card with this name"),
    NOT_ENOUGH_MONEY ("not enough money"),
    PLAYER_DOES_NOT_EXIST (""),
    //INACTIVATED_DECK,
    //FORBIDDEN_DECK,
    WRONG_ROUNDS_NUMBER (""),
    INVALID_SELECTION (""),
    CARD_NOT_FOUND (""),
    NO_CARD_SELECTED_YET (""),
    CAN_NOT_SUMMON (""),
    ACTION_NOT_ALLOWED (""),
    MONSTER_ZONE_IS_FULL (""),
    ALREADY_SUMMONED (""),
    SET_A_CARD (""),
    NOT_ENOUGH_CARDS_TO_TRIBUTE (""),
    WRONG_MONSTER_ADDRESS (""),
    WRONG_MONSTERS_ADDRESSES (""),
    CAN_NOT_SET (""),
    ACTION_CAN_NOT_WORK (""),
    CAN_NOT_CHANGE_POSITION (""),
    CURRENTLY_IN_POSITION (""),
    ALREADY_CHANGED_POSITION (""),
    FLIP_SUMMON_NOT_ALLOWED (""),
    CAN_NOT_ATTACK (""),
    ALREADY_ATTACKED (""),
    NO_CARD_TO_BE_ATTACKED (""),
    ONLY_SPELL_CAN_ACTIVE (""),
    CAN_NOT_ACTIVE_EFFECT (""),
    CARD_ALREADY_ACTIVATED (""),
    SPELL_ZONE_IS_FULL (""),
    PREPARATIONS_IS_NOT_DONE (""),
    NOT_YOUR_TURN (""),
    CAN_NOT_RITUAL_SUMMON (""),
    LEVEL_DO_NOT_MATCH (""),
    CAN_NOT_SPECIAL_SUMMON (""),
    INVISIBLE_CARD (""),
    INVALID_COMMAND ("invalid command");
    private String value;

    Error(String value) {
        setValue (value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
