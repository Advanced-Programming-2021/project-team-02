package view;

public enum Menu {
    LOGIN_MENU("LoginMenu"),
    MAIN_MENU("MainMenu"),
    DUEL_MENU("DuelMenu"),
    ONGOING_GAME("Game"),
    DECK_MENU("DeckMenu"),
    SHOP_MENU("ShopMenu"),
    PROFILE_MENU("ProfileMenu"),
    SCOREBOARD_MENU("ScoreboardMenu"),
    EXIT("Exit"),
    BETWEEN_ROUNDS("BetweenRoundsMenu");

    private String value;

    Menu(String value) {
        setValue(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
