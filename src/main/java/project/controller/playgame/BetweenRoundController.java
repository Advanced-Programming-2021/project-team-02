package project.controller.playgame;

import project.model.Deck;
import project.model.card.Card;
import project.model.game.DuelPlayer;
import project.view.gameview.BetweenRoundView;
import project.view.messages.Error;
import project.view.messages.SuccessMessage;

import java.util.ArrayList;


public class BetweenRoundController {
    private static BetweenRoundController instance = null;
    private BetweenRoundView view;
    private DuelPlayer player1;
    private DuelPlayer player2;
    private boolean isWithAi;
    private BetweenRoundController() {
    }

    public static BetweenRoundController getInstance() {
        if (instance == null)
            instance = new BetweenRoundController();
        return instance;
    }
    public void addCardToMainFromSide(int cardAddressInSide,DuelPlayer player){
        ArrayList<Card> mainCards = player.getPlayDeck().getMainCards();
        ArrayList<Card> sideCards = player.getPlayDeck().getSideCards();
        mainCards.add(sideCards.get(cardAddressInSide));
        sideCards.remove(cardAddressInSide);
    }
    public void changeCard(int cardAddressInMainDeck, int cardAddressInSideDeck, DuelPlayer player) {
        Deck deck = player.getPlayDeck();
        ArrayList<Card> mainCards = player.getPlayDeck().getMainCards();
        ArrayList<Card> sideCards = player.getPlayDeck().getSideCards();
        Card inMainCard;
        Card inSideCard;
        if (cardAddressInMainDeck > mainCards.size() || cardAddressInSideDeck > sideCards.size() || cardAddressInMainDeck <= 0 || cardAddressInSideDeck <= 0) {
            view.showError(Error.WRONG_CARD_CHOICE);
            return;
        }
        inMainCard = mainCards.get(cardAddressInMainDeck - 1);
        inSideCard = sideCards.get(cardAddressInSideDeck - 1);
        if (!inMainCard.getName().equals(inSideCard.getName())) {
            if (deck.getNumberOfCardInDeck(inSideCard) == 3) {
                view.showError(Error.EXCESSIVE_NUMBER_IN_DECK);
            } else {
                mainCards.remove(cardAddressInMainDeck - 1);
                mainCards.add(cardAddressInMainDeck - 1, inSideCard);
                sideCards.remove(cardAddressInSideDeck - 1);
                sideCards.add(cardAddressInSideDeck - 1, inMainCard);
                view.showMessage(SuccessMessage.CHANGED_CARD);
            }
        } else {
            mainCards.remove(cardAddressInMainDeck - 1);
            mainCards.add(cardAddressInMainDeck - 1, inSideCard);
            sideCards.remove(cardAddressInSideDeck - 1);
            sideCards.add(cardAddressInSideDeck - 1, inMainCard);
            view.showMessage(SuccessMessage.CHANGED_CARD);
        }
    }

    public void setView(BetweenRoundView view) {
        this.view = view;
    }

    public void setPlayer1(DuelPlayer player1,boolean isWithAi) {
        this.player1 = player1;
    this.isWithAi = isWithAi;
    }

    public void setPlayer2(DuelPlayer player2,boolean isWithAi) {
        this.player2 = player2;
        this.isWithAi = isWithAi;
    }

    public DuelPlayer getPlayer1() {
        return player1;
    }

    public DuelPlayer getPlayer2() {
        return player2;
    }
}
