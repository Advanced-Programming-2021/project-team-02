package project.view.gameview;

import project.controller.playgame.BetweenRoundController;
import project.controller.playgame.DuelGameController;
import project.model.card.Card;
import project.model.game.DuelPlayer;
import project.view.Menu;
import project.view.MenusManager;
import project.view.input.Regex;
import project.view.messages.Error;
import project.view.messages.SuccessMessage;

import java.util.List;
import java.util.regex.Matcher;

public class BetweenRoundView {
    private static BetweenRoundView instance = null;
    private final BetweenRoundController controller = BetweenRoundController.getInstance();
    private DuelPlayer player1;
    private DuelPlayer player2;
    private int turn = 1;
    private boolean isAI;

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
        BetweenRoundController.getInstance().setView(this);
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.CHANGE_CARD_BETWEEN_ROUNDS, command)).matches()) {
            DuelPlayer player = (turn == 1 ? player1 : player2);
            controller.changeCard(Integer.parseInt(matcher.group("cardAddressInMainDeck")), Integer.parseInt(matcher.group("cardAddressInSideDeck")), player);
        } else if (command.equals("start")) {
            if (isAI) {
                DuelGameController.getInstance().startNextRound();
                MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME_WITH_AI);
                return;
            }
            if (turn == 1) {
                turn = 2;
                System.out.println("now next player can change cards;");
            } else {
                turn = 1;
                DuelGameController.getInstance().startNextRound();
                MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME);
            }
        } else if (command.equals("show deck")) {
            DuelPlayer player = (turn == 1 ? player1 : player2);
            showDeck(player);
        } else if (command.equals("help")) {
            DuelPlayer player = (turn == 1 ? player1 : player2);
            System.out.println(player.getNickname() + " turn to change card");
            help();
        } else System.out.println(Error.INVALID_COMMAND);
    }

    private void help() {
        System.out.println("start" +
                "\nhelp\nshow deck\nchange card <cardAddressInMainDeck> with <cardAddressInSideDeck>");
    }

    public void setPlayer1(DuelPlayer player, boolean isAI) {
        this.player1 = player;
        this.isAI = isAI;
    }

    public void setPlayer2(DuelPlayer player) {
        this.player2 = player;
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showMessage(SuccessMessage message) {
        System.out.println(message.getValue());
    }

    public void showDeck(DuelPlayer player) {
        List<Card> mainCards = player.getPlayDeck().getMainCards();
        List<Card> sideCards = player.getPlayDeck().getSideCards();
        int counter = 1;
        for (Card mainCard : mainCards) {
            System.out.println(counter + "-" + mainCard.getName() + " : " + mainCard.getCardType());
            counter++;
        }
        counter = 1;
        for (Card sideCard : sideCards) {
            System.out.println(counter + "-" + sideCard.getName() + " : " + sideCard.getCardType());
            counter++;
        }
    }
}
