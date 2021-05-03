package controller.playgame;

import model.Deck;
import model.card.Card;
import model.game.Duel;
import model.game.DuelPlayer;
import view.gameview.GameView;
import view.messages.Error;

import java.util.Random;
import java.util.regex.Matcher;


public class DuelGameController {
    private static DuelGameController instance;
    private final GameView view = GameView.getInstance();
    private Duel duel;
    private String specifier;

    static {
        instance = new DuelGameController();
    }

    public static DuelGameController getInstance() {
        if (instance == null) instance = new DuelGameController();
        return instance;
    }

    public void startDuel(Duel duel) {
        this.duel = duel;
        starterSpecifier();
        setStartHandCards(duel.getPlayer1(), duel.getPlayer2());
    }

    public void starterSpecifier() {
        if (flipCoin() == 1) {
            setSpecifier(duel.getPlayer1().getNickname());
            RoundGameController.getInstance().setRoundInfo(duel.getPlayer1(), duel.getPlayer2());
        } else {
            setSpecifier(duel.getPlayer2().getNickname());
            RoundGameController.getInstance().setRoundInfo(duel.getPlayer2(), duel.getPlayer1());
        }
    }

    public void checkGameResult(DuelPlayer winner) {

        updateScoreAndCoin(duel.getPlayer1(), duel.getPlayer2());
    }

    public void updateScoreAndCoin(DuelPlayer winner, DuelPlayer loser) {

    }

    public void changeCardBetweenDecks(Matcher matcher) {
        DuelPlayer player = duel.getPlayer1(); // we dont know who! now just for example player 1
        if (duel.getNumberOfRounds() != 3) view.showError(Error.CHANGE_CARDS_IN_ONE_ROUND_DUEL);
        else {
            if (!player.getPlayDeck().containsMainCard(matcher.group("cardNameInMainDeck"))) {
                view.showError(Error.CARD_IS_NOT_IN_MAIN_DECK_TO_CHANGE);
            } else if (player.getPlayDeck().containsSideCard(matcher.group("cardNameInSideDeck"))) {
                view.showError(Error.CARD_IS_NOT_IN_SIDE_DECK_TO_CHANGE);
            } else {
                duel.getPlayer1().getPlayDeck().addCardToSideDeck(Card.getCardByName(matcher.group("cardNameInMainDeck")));
                duel.getPlayer1().getPlayDeck().addCardToMainDeck(Card.getCardByName(matcher.group("cardNameInSideDeck")));
                duel.getPlayer1().getPlayDeck().removeCardFromMainDeck(Card.getCardByName(matcher.group("cardNameInMainDeck")));
                duel.getPlayer1().getPlayDeck().removeCardFromSideDeck(Card.getCardByName(matcher.group("cardNameInSideDeck")));
            }
        }
    }

    private void setStartHandCards(DuelPlayer duelPlayer1, DuelPlayer duelPlayer2) {
        Deck deckFirstPlayer = duelPlayer1.getPlayDeck().shuffleDeck();
        Deck deckSecondPlayer = duelPlayer2.getPlayDeck().shuffleDeck();
        RoundGameController roundGameController = RoundGameController.getInstance();
        for (int i = 0; i < 5; i++) {
            roundGameController.addCardToFirstPlayerHand(deckFirstPlayer.getMainCards().get(i));
            deckFirstPlayer.getMainCards().remove(i);
            roundGameController.addCardToSecondPlayerHand(deckSecondPlayer.getMainCards().get(i));
            deckSecondPlayer.getMainCards().remove(i);
        }
    }

    private int flipCoin() {
        Random randomNum = new Random();
        return randomNum.nextInt(2);
    }

    public String getSpecifier() {
        return specifier;
    }

    public void setSpecifier(String specifier) {
        this.specifier = specifier;
    }
}