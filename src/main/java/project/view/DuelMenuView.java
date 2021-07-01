package project.view;

import project.controller.DuelMenuController;
import project.view.input.Regex;
import project.view.messages.Error;

import java.util.Locale;
import java.util.regex.Matcher;

public class DuelMenuView {
    private static DuelMenuView instance = null;
    private static final DuelMenuController controller = DuelMenuController.getInstance();

    private DuelMenuView() {

    }

    public static DuelMenuView getInstance() {
        if (instance == null) instance = new DuelMenuView();
        return instance;
    }

    public void run(String command) throws CloneNotSupportedException {
        commandRecognition(command);
    }

    public void commandRecognition(String command) throws CloneNotSupportedException {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.MENU_ENTER, command)).matches()) {
            if (matcher.group("menuName").toLowerCase(Locale.ROOT).equals("duel"))
                showDynamicError(Error.BEING_ON_CURRENT_MENU);
            else Error.showError(Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher(Regex.MENU_EXIT, command).matches()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if (Regex.getMatcher(Regex.MENU_SHOW_CURRENT, command).matches()) {
            showCurrentMenu();
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DUEL_NEW_SECOND_PLAYER, command)) != null) {
            controller.startDuelWithOtherPlayer(matcher.group("secondPlayerUsername"), Integer.parseInt(matcher.group("roundNumber")));
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DUEL_NEW_AI, command)) != null) {
            controller.startDuelWithAI(Integer.parseInt(matcher.group("roundNumber")));
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DUEL_NEW_AI, command)) != null) {
            controller.startDuelWithAI(Integer.parseInt(matcher.group("roundNumber")));
        } else if (Regex.getMatcher(Regex.COMMAND_HELP, command).matches()) {
            help();
        } else Error.showError(Error.INVALID_COMMAND);
    }

    public void showDynamicError(Error error) {
        if (error.equals(Error.BEING_ON_CURRENT_MENU)) System.out.printf(error.getValue(), Menu.DUEL_MENU.getValue());
    }

    public void showDynamicErrorForInactiveDeck(Error error, String username) {
        System.out.printf(error.getValue(), username);
    }

    public void showCurrentMenu() {
        System.out.println("Duel Menu");
    }

    public void help() {
        System.out.println("menu show-current\n" +
                "duel new --second-player <secondPlayerUsername> --rounds <1|3>\n" +
                "duel new --s-p <secondPlayerUsername> -r <1|3>\n" +
                "duel --new --ai --rounds <1|3>\n" +
                "duel -n -a -r <1|3>\n" +
                "menu exit\n" +
                "help");
    }
}
