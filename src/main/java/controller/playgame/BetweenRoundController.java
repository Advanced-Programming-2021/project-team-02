package controller.playgame;

import model.Assets;
import model.Deck;
import model.card.Card;
import model.game.DuelPlayer;
import view.gameview.BetweenRoundView;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.ArrayList;


public class BetweenRoundController {
    private static BetweenRoundController instance = null;
    private BetweenRoundView view;


    private BetweenRoundController() {
    }

    public static BetweenRoundController getInstance() {
        if (instance == null)
            instance = new BetweenRoundController();
        return instance;
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

}
