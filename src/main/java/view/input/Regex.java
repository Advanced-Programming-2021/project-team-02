package view.input;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static final String MENU_ENTER = "^menu enter (?<menuName>\\w+)$";
    public static final String MENU_EXIT = "^menu exit$";
    public static final String MENU_SHOW_CURRENT = "^menu show-current$";
    public static final List<String> USER_CREATE;
    public static final List<String> USER_LOGIN;
    public static final String USER_LOGOUT = "^user logout$";
    public static final String SCOREBOARD_SHOW = "^scoreboard show$";
    public static final String PROFILE_CHANGE_NICKNAME = "^profile change --nickname (?<nickname>\\w+)$";
    public static final List<String> PROFILE_CHANGE_PASSWORD;
    public static final String CARD_SHOW = "^card show (?<cardName>[A-Za-z ',-]+)$";
    public static final String DECK_CREATE = "^deck create (?<deckName>[a-zA-Z0-9 -]+?)$";
    public static final String DECK_DELETE = "^deck delete (?<deckName>[a-zA-Z0-9 -]+?)$";
    public static final String DECK_SET_ACTIVATE = "^deck set-activate (?<deckName>[a-zA-Z0-9 -]+?)$";
    public static final List<String> DECK_ADD_CARD_TO_MAIN_DECK;
    public static final List<String> DECK_ADD_CARD_TO_SIDE_DECK;
    public static final List<String> DECK_REMOVE_CARD_MAIN_DECK;
    public static final List<String> DECK_REMOVE_CARD_SIDE_DECK;
    public static final String DECK_SHOW_ALL_DECKS = "^deck show --all$";
    public static final String DECK_SHOW_MAIN_DECK = "^deck show --deck-name (?<deckName>[a-zA-Z0-9 -]+?)$";
    public static final ArrayList<String> DECK_SHOW_SIDE_DECK;
    public static final String DECK_SHOW_ALL_CARDS = "^deck show --cards$";
    public static final String SHOP_BUY = "^shop buy (?<cardName>[A-Za-z ',-]+)$";
    public static final String SHOP_SHOW_ALL = "^shop show --all$";
    public static final List<String> DUEL_NEW_SECOND_PLAYER;
    public static final List<String> DUEL_NEW_AI;
    public static final String BOARD_GAME_SELECT_MONSTER = "^select --monster (?<monsterZoneNumber>\\d)$";
    public static final List<String> BOARD_GAME_SELECT_MONSTER_OPPONENT;
    public static final String BOARD_GAME_SELECT_SPELL = "^select --spell (?<spellZoneNumber>\\d)$";
    public static final List<String> BOARD_GAME_SELECT_SPELL_OPPONENT;
    public static final String BOARD_GAME_SELECT_CARD = "^select (?<cardAddress>.+?)$";
    public static final String BOARD_GAME_SELECT_DELETE = "^select -d$";
    public static final String BOARD_GAME_SUMMON = "^summon$";
    public static final String BOARD_GAME_SET_MONSTER_SPELL_TRAP = "^set$";
    public static final String BOARD_GAME_SET_POSITION = "^set --position (?<position>attack|defense)$";
    public static final String BOARD_GAME_FLIP_SUMMON = "^flip-summon$";
    public static final String BOARD_GAME_ATTACK = "^attack (?<monsterZoneNumber>\\d)$";
    public static final String BOARD_GAME_ATTACK_DIRECT = "^attack direct$";
    public static final String BOARD_GAME_ACTIVATE_EFFECT = "^activate effect$";
    public static final String BOARD_GAME_SURRENDER = "^surrender$";
    public static final String GRAVEYARD_SHOW = "^show graveyard$";
    public static final String GRAVEYARD_BACK = "^back$";
    public static final String CARD_SHOW_SELECTED = "^card show --selected$";
    public static final String CHEAT_INCREASE_MONEY = "^increase --money (?<moneyAmount>\\d+)$";
    public static final List<String> CHEAT_SELECT_HAND;
    public static final String CHEAT_INCREASE_LP = "^increase --LP (?<LPAmount>\\d+)$";
    public static final String CHEAT_DUEL_SET_WINNER = "^duel set-winner (?<winnerNickName>\\w+?)$";
    public static final String IMPORT_CARD = "^import card (?<cardName>[A-Za-z ',-]+)$";
    public static final String EXPORT_CARD = "^export card (?<cardName>[A-Za-z ',-]+)$";
    public static final String COMMAND_CANCEL = "^cancel$";
    public static final String COMMAND_HELP = "^help$";

    static {
        USER_CREATE = new ArrayList<>();
        USER_CREATE.add("^user create --username (?<username>\\w+) --password (?<password>\\w+) --nickname (?<nickname>\\w+)$");
        USER_CREATE.add("^user create --username (?<username>\\w+) --nickname (?<nickname>\\w+) --password (?<password>\\w+)$");
        USER_CREATE.add("^user create --nickname (?<nickname>\\w+) --username (?<username>\\w+) --password (?<password>\\w+)$");
        USER_CREATE.add("^user create --nickname (?<nickname>\\w+) --password (?<password>\\w+) --username (?<username>\\w+)$");
        USER_CREATE.add("^user create --password (?<password>\\w+) --username (?<username>\\w+) --nickname (?<nickname>\\w+)$");
        USER_CREATE.add("^user create --password (?<password>\\w+) --nickname (?<nickname>\\w+) --username (?<username>\\w+)$");
        USER_CREATE.add("^user create -u (?<username>\\w+) -p (?<password>\\w+) -n (?<nickname>\\w+)$");
        USER_CREATE.add("^user create -u (?<username>\\w+) -n (?<nickname>\\w+) -p (?<password>\\w+)$");
        USER_CREATE.add("^user create -n (?<nickname>\\w+) -u (?<username>\\w+) -p (?<password>\\w+)$");
        USER_CREATE.add("^user create -n (?<nickname>\\w+) -p (?<password>\\w+) -u (?<username>\\w+)$");
        USER_CREATE.add("^user create -p (?<password>\\w+) -u (?<username>\\w+) -n (?<nickname>\\w+)$");
        USER_CREATE.add("^user create -p (?<password>\\w+) -n (?<nickname>\\w+) -u (?<username>\\w+)$");
        USER_LOGIN = new ArrayList<>();
        USER_LOGIN.add("^user login --username (?<username>\\w+?) --password (?<password>\\w+?)$");
        USER_LOGIN.add("^user login --password (?<password>\\w+?) --username (?<username>\\w+?)$");
        USER_LOGIN.add("^user login -u (?<username>\\w+?) -p (?<password>\\w+?)$");
        USER_LOGIN.add("^user login -p (?<password>\\w+?) -u (?<username>\\w+?)$");
        PROFILE_CHANGE_PASSWORD = new ArrayList<>();
        PROFILE_CHANGE_PASSWORD.add("^profile change --password --current (?<currentPassword>\\w+) --new (?<newPassword>\\w+)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --password --new (?<newPassword>\\w+) --current (?<currentPassword>\\w+)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --current (?<currentPassword>\\w+) --password --new (?<newPassword>\\w+)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --current (?<currentPassword>\\w+) --new (?<newPassword>\\w+) --password$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --new (?<newPassword>\\w+) --current (?<currentPassword>\\w+) --password$");
        PROFILE_CHANGE_PASSWORD.add("^profile change --new (?<newPassword>\\w+) --password --current (?<currentPassword>\\w+)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change -p -c (?<currentPassword>\\w+) -n (?<newPassword>\\w+)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change -p -n (?<newPassword>\\w+) -c (?<currentPassword>\\w+)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change -c (?<currentPassword>\\w+) -p -n (?<newPassword>\\w+)$");
        PROFILE_CHANGE_PASSWORD.add("^profile change -c (?<currentPassword>\\w+) -n (?<newPassword>\\w+) -p$");
        PROFILE_CHANGE_PASSWORD.add("^profile change -n (?<newPassword>\\w+) -c (?<currentPassword>\\w+) -p$");
        PROFILE_CHANGE_PASSWORD.add("^profile change -n (?<newPassword>\\w+) -p -c (?<currentPassword>\\w+)$");
        CHEAT_SELECT_HAND = new ArrayList<>();
        CHEAT_SELECT_HAND.add("^select --hand (?<cardNumber>.+?) --force$");
        CHEAT_SELECT_HAND.add("^select --force --hand (?<cardNumber>.+?)$");
        CHEAT_SELECT_HAND.add("^select -h (?<cardNumber>.+?) -f$");
        CHEAT_SELECT_HAND.add("^select -f -h (?<cardNumber>.+?)$");
        DECK_ADD_CARD_TO_MAIN_DECK = new ArrayList<>();
        DECK_ADD_CARD_TO_MAIN_DECK.add("^deck add-card --card (?<cardName>[A-Za-z ',-]+?) --deck (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_ADD_CARD_TO_MAIN_DECK.add("^deck add-card --deck (?<deckName>[a-zA-Z0-9 -]+?) --card (?<cardName>[A-Za-z ',-]+?)$");
        DECK_ADD_CARD_TO_MAIN_DECK.add("^deck add-card -c (?<cardName>[A-Za-z ',-]+?) -d (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_ADD_CARD_TO_MAIN_DECK.add("^deck add-card -d (?<deckName>[a-zA-Z0-9 -]+?) -c (?<cardName>[A-Za-z ',-]+?)$");
        DECK_ADD_CARD_TO_SIDE_DECK = new ArrayList<>();
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card --card (?<cardName>[A-Za-z ',-]+?) --deck (?<deckName>[a-zA-Z0-9 -]+?) --side$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card --deck (?<deckName>[a-zA-Z0-9 -]+?) --card (?<cardName>[A-Za-z ',-]+?) --side$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card --side --card (?<cardName>[A-Za-z ',-]+?) --deck (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card --side --deck (?<deckName>[a-zA-Z0-9 -]+?) --card (?<cardName>[A-Za-z ',-]+?)$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card --card (?<cardName>[A-Za-z ',-]+?) --side --deck (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card --deck (?<deckName>[a-zA-Z0-9 -]+?) --side --card (?<cardName>[A-Za-z ',-]+?)$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card -c (?<cardName>[A-Za-z ',-]+?) -d (?<deckName>[a-zA-Z0-9 -]+?) -s$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card -d (?<deckName>[a-zA-Z0-9 -]+?) -c (?<cardName>[A-Za-z ',-]+?) -s$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card -s -c (?<cardName>[A-Za-z ',-]+?) -d (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card -s -d (?<deckName>[a-zA-Z0-9 -]+?) -c (?<cardName>[A-Za-z ',-]+?)$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card -c (?<cardName>[A-Za-z ',-]+?) -s -d (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_ADD_CARD_TO_SIDE_DECK.add("^deck add-card -d (?<deckName>[a-zA-Z0-9 -]+?) -s -c (?<cardName>[A-Za-z ',-]+?)$");
        DECK_REMOVE_CARD_MAIN_DECK = new ArrayList<>();
        DECK_REMOVE_CARD_MAIN_DECK.add("deck rm-card --card (?<cardName>[A-Za-z ',-]+) --deck (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_REMOVE_CARD_MAIN_DECK.add("deck rm-card --deck (?<deckName>[a-zA-Z0-9 -]+?) --card (?<cardName>[A-Za-z ',-]+)$");
        DECK_REMOVE_CARD_MAIN_DECK.add("deck rm-card -c (?<cardName>[A-Za-z ',-]+) -d (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_REMOVE_CARD_MAIN_DECK.add("deck rm-card -d (?<deckName>[a-zA-Z0-9 -]+?) -c (?<cardName>[A-Za-z ',-]+)$");
        DECK_REMOVE_CARD_SIDE_DECK = new ArrayList<>();
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card --card (?<cardName>[A-Za-z ',-]+) --deck (?<deckName>[a-zA-Z0-9 -]+?) --side$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card --deck (?<deckName>[a-zA-Z0-9 -]+?) --card (?<cardName>[A-Za-z ',-]+)$ --side");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card --card (?<cardName>[A-Za-z ',-]+) --side --deck (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card --deck (?<deckName>[a-zA-Z0-9 -]+?) --side --card (?<cardName>[A-Za-z ',-]+)$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card --side --card (?<cardName>[A-Za-z ',-]+) --deck (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card --side --deck (?<deckName>[a-zA-Z0-9 -]+?) --card (?<cardName>[A-Za-z ',-]+)$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card -c (?<cardName>[A-Za-z ',-]+) -d (?<deckName>[a-zA-Z0-9 -]+?) -s$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card -d (?<deckName>[a-zA-Z0-9 -]+?) -c (?<cardName>[A-Za-z ',-]+)$ -s");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card -c (?<cardName>[A-Za-z ',-]+) -s -d (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card -d (?<deckName>[a-zA-Z0-9 -]+?) -s -c (?<cardName>[A-Za-z ',-]+)$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card -s -c (?<cardName>[A-Za-z ',-]+) -d (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_REMOVE_CARD_SIDE_DECK.add("deck rm-card -s -d (?<deckName>[a-zA-Z0-9 -]+?) -c (?<cardName>[A-Za-z ',-]+)$");
        DECK_SHOW_SIDE_DECK = new ArrayList<>();
        DECK_SHOW_SIDE_DECK.add("^deck show --side --deck-name (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_SHOW_SIDE_DECK.add("^deck show --deck-name (?<deckName>[a-zA-Z0-9 -]+?) --side$");
        DECK_SHOW_SIDE_DECK.add("^deck show -s --d-n (?<deckName>[a-zA-Z0-9 -]+?)$");
        DECK_SHOW_SIDE_DECK.add("^deck show --d-n (?<deckName>[a-zA-Z0-9 -]+?) -s$");
        DUEL_NEW_SECOND_PLAYER = new ArrayList<>();
        DUEL_NEW_SECOND_PLAYER.add("^duel new --second-player (?<secondPlayerUsername>\\w+?) --rounds (?<roundNumber>\\d+)$");
        DUEL_NEW_SECOND_PLAYER.add("^duel new --rounds (?<roundNumber>\\d+) --second-player (?<secondPlayerUsername>\\w+?)$");
        DUEL_NEW_SECOND_PLAYER.add("^duel new --s-p (?<secondPlayerUsername>\\w+?) -r (?<roundNumber>\\d+)$");
        DUEL_NEW_SECOND_PLAYER.add("^duel new -r (?<roundNumber>\\d+) --s-p (?<secondPlayerUsername>\\w+?)$");
        DUEL_NEW_AI = new ArrayList<>();
        DUEL_NEW_AI.add("^duel --new --ai --rounds (?<roundNumber>\\d+)$");
        DUEL_NEW_AI.add("^duel --new --rounds (?<roundNumber>\\d+) --ai$");
        DUEL_NEW_AI.add("^duel --ai --rounds (?<roundNumber>\\d+) --new$");
        DUEL_NEW_AI.add("^duel --ai --new --rounds (?<roundNumber>\\d+)$");
        DUEL_NEW_AI.add("^duel --rounds (?<roundNumber>\\d+) --ai --new$");
        DUEL_NEW_AI.add("^duel --rounds (?<roundNumber>\\d+) --new --ai$");
        DUEL_NEW_AI.add("^duel -n -a -r (?<roundNumber>\\d+)$");
        DUEL_NEW_AI.add("^duel -n -r (?<roundNumber>\\d+) -a$");
        DUEL_NEW_AI.add("^duel -a -r (?<roundNumber>\\d+) -n$");
        DUEL_NEW_AI.add("^duel -a -n -r (?<roundNumber>\\d+)$");
        DUEL_NEW_AI.add("^duel -r (?<roundNumber>\\d+) -a -n$");
        DUEL_NEW_AI.add("^duel -r (?<roundNumber>\\d+) -n -a$");
        BOARD_GAME_SELECT_MONSTER_OPPONENT = new ArrayList<>();
        BOARD_GAME_SELECT_MONSTER_OPPONENT.add("^select --monster (?<monsterZoneNumber>\\d) --opponent$");
        BOARD_GAME_SELECT_MONSTER_OPPONENT.add("^select --opponent --monster (?<monsterZoneNumber>\\d)$");
        BOARD_GAME_SELECT_MONSTER_OPPONENT.add("^select -m (?<monsterZoneNumber>\\d) -o$");
        BOARD_GAME_SELECT_MONSTER_OPPONENT.add("^select -o -m (?<monsterZoneNumber>\\d)$");
        BOARD_GAME_SELECT_SPELL_OPPONENT = new ArrayList<>();
        BOARD_GAME_SELECT_SPELL_OPPONENT.add("^select --spell (?<spellZoneNumber>\\d+) --opponent$");
        BOARD_GAME_SELECT_SPELL_OPPONENT.add("^select --opponent --spell (?<spellZoneNumber>\\d+)$");
        BOARD_GAME_SELECT_SPELL_OPPONENT.add("^select -s (?<spellZoneNumber>\\d+) -o$");
        BOARD_GAME_SELECT_SPELL_OPPONENT.add("^select -o -s (?<spellZoneNumber>\\d+)$");
    }

    public static Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static Matcher getMatcherFromAllPermutations(List<String> regexes, String command) {
        Matcher matcher;
        for (String regex : regexes)
            if ((matcher = getMatcher(regex, command)).matches()) return matcher;
        return null;
    }
}