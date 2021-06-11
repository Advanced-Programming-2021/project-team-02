package view.gameview;

import controller.playgame.BetweenRoundController;
import model.game.Duel;
import model.game.DuelPlayer;
import view.Menu;
import view.MenusManager;
import view.input.Regex;
import view.messages.Error;

import java.util.regex.Matcher;

public class BetweenRoundView {
    private static BetweenRoundView instance = null;
    private final BetweenRoundController controller = BetweenRoundController.getInstance();
    private DuelPlayer player1;
    private DuelPlayer player2;
    int turn = 1;

    private BetweenRoundView() {
    }

    public static BetweenRoundView getInstance() {
        if (instance == null)
            instance = new BetweenRoundView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    private void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.CHANGE_CARD_BETWEEN_ROUNDS, command)).matches()) {
            DuelPlayer player = (turn == 1 ? player1 : player2);
            controller.changeCard(Integer.parseInt(matcher.group("cardAddressInMainDeck")), Integer.parseInt(matcher.group("cardAddressInSideDeck")), player);
        } else if (command.equals("start")) {
            if (turn == 1) {
                turn = 2;
            } else {
                turn = 1;
                MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME);
            }
        } else if (command.equals("show deck")) {

        } else if (command.equals("help")) {
            help();
        } else System.out.println(Error.INVALID_COMMAND);
    }

    private void help() {
        System.out.println("start" +
                "\nhelp\nshow deck\nchange card <cardAddressInMainDeck> with <cardAddressInSideDeck>");
    }

    public void setPlayer1(DuelPlayer player) {
        this.player1 = player;
    }

    public void setPlayer2(DuelPlayer player) {
        this.player2 = player;
    }
}
