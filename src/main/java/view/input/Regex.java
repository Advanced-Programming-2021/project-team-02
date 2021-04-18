package view.input;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static final String MENU_ENTER = "^menu enter (?<menuName>[A-Za-z]+)$";
    public static final String MENU_EXIT = "^menu exit$";
    public static final String MENU_SHOW_CURRENT = "^menu show-current$";
    public static final ArrayList<String> USER_CREATE;
    public static final ArrayList<String> USER_LOGIN;
    public static final String USER_LOGOUT = "^user logout$";
    public static final String SCOREBOARD_SHOW = "^scoreboard show$";
    public static final String PROFILE_CHANGE_NICKNAME = "^profile change --nickname (?<nickname>.+)$";
    public static final ArrayList<String> PROFILE_CHANGE_PASSWORD;
    public static final String CARD_SHOW = "^card show (?<cardName>[A-Za-z]+)$";
    public static final String DECK_CREATE = "^deck create (?<deckName>[A-Za-z]+)$";
    public static final String DECK_DELETE = "^deck delete (?<deckName>[A-Za-z]+)$";
    public static final String DECK_SET_ACTIVATE = "^deck set-activate (?<deckName>[A-Za-z]+)$";
    public static final String DECK_ADD_CARD = "^$";
    public static final String DECK_REMOVE_CARD = "^$";
    public static final String DECK_SHOW_ALL = "^deck show --all$";
    public static final String DECK_SHOW_DECK_NAME = "^$";
    public static final String DECK_SHOW_CARDS = "^deck show --cards$";
    public static final String SHOP_BUY = "^shop buy (?<cardName>[A-Za-z]+)$";
    public static final String SHOP_SHOW_ALL = "^shop show --all$";
    public static final String DUEL_NEW_SECOND_PLAYER = "^$";
    public static final String DUEL_NEW_AI = "";
    public static final String BOARD_GAME_SELECT_MONSTER = "";
    public static final String BOARD_GAME_SELECT_MONSTER_OPPONENT = "";
    public static final String BOARD_GAME_SELECT_CARD = "";
    public static final String BOARD_GAME_SELECT_DELETE = "";
    public static final String BOARD_GAME_SUMMON = "";
    public static final String BOARD_GAME_SET_MONSTER = "";
    public static final String BOARD_GAME_SET_POSITION = "";
    public static final String BOARD_GAME_FLIP_SUMMON = "";
    public static final String BOARD_GAME_ATTACK = "";
    public static final String BOARD_GAME_ATTACK_DIRECT = "";
    public static final String BOARD_GAME_ACTIVATE_EFFECT = "";
    public static final String BOARD_GAME_SET_SPELL = "";
    public static final String BOARD_GAME_SET_TRAP = "";
    public static final String BOARD_GAME_SURRENDER = "";
    public static final String GRAVEYARD_SHOW = "";
    public static final String GRAVEYARD_BACK = "";
    public static final String CARD_SHOW_SELECTED = "";
    public static final String CHEAT_INCREASE_MONEY = "";
    public static final String CHEAT_SELECT_HAND = "";
    public static final String CHEAT_INCREASE_LP = "";
    public static final String CHEAT_DUEL_SET_WINNER = "";
    public static final String IMPORT_CARD = "";
    public static final String EXPORT_CARD = "";
    public static final String COMMAND_CANCEL = "";

    static {
        USER_CREATE = new ArrayList<>();
        USER_CREATE.add("^user create --username (?<username>.+?) --password (?<password>.+?) --nickname (?<nickname>.+?)$");
        USER_CREATE.add("^user create --username (?<username>.+?) --nickname (?<nickname>.+?) --password (?<password>.+?)$");
        USER_CREATE.add("^user create --nickname (?<nickname>.+?) --username (?<username>.+?) --password (?<password>.+?)$");
        USER_CREATE.add("^user create --nickname (?<nickname>.+?) --password (?<password>.+?) --username (?<username>.+?)$");
        USER_CREATE.add("^user create --password (?<password>.+?) --username (?<username>.+?) --nickname (?<nickname>.+?)$");
        USER_CREATE.add("^user create --password (?<password>.+?) --nickname (?<nickname>.+?) --username (?<username>.+?)$");
        USER_LOGIN = new ArrayList<>();
        USER_LOGIN.add("^user login --username (?<username>.+?) --password (?<password>.+?)$");
        USER_LOGIN.add("^user login --password (?<password>.+?) --username (?<username>.+?)$");
        PROFILE_CHANGE_PASSWORD = new ArrayList<>();
        PROFILE_CHANGE_PASSWORD.add("^profile change --password --current (?<currentPassword>.+?) --new (?<newPassword>.+?)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --password --new (?<newPassword>.+?) --current (?<currentPassword>.+?)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --current (?<currentPassword>.+?) --password --new (?<newPassword>.+?)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --current (?<currentPassword>.+?) --new (?<newPassword>.+?) --password$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --new (?<newPassword>.+?) --current (?<currentPassword>.+?) --password$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --new (?<newPassword>.+?) --password --current (?<currentPassword>.+?)$");
    }

    public static Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }
}