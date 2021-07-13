package project.controller.playgame;

import javafx.scene.input.DataFormat;
import project.model.Deck;
import project.model.card.Card;
import project.model.game.DuelPlayer;
import project.view.gameview.BetweenRoundView;
import project.view.messages.Error;
import project.view.messages.GameViewMessage;
import project.view.messages.SuccessMessage;

import java.util.ArrayList;


public class BetweenRoundController {
    private static BetweenRoundController instance = null;
    private BetweenRoundView view;
    private DuelPlayer player1;
    private DuelPlayer player2;
    private DuelPlayer current;
    private boolean isWithAi;
    private int firstMainSize;
    private int firstSideSize;
    private DataFormat mainDeckPaneFormat = new DataFormat("MainPane");
    private DataFormat sideDeckPaneFormat = new DataFormat("SidePane");

    private BetweenRoundController() {
    }

    public static BetweenRoundController getInstance() {
        if (instance == null)
            instance = new BetweenRoundController();
        return instance;
    }

    public DataFormat getSideDeckPaneFormat() {
        return sideDeckPaneFormat;
    }

    public DataFormat getMainDeckPaneFormat() {
        return mainDeckPaneFormat;
    }

    public boolean isWithAi() {
        return isWithAi;
    }

    public GameViewMessage addCardToMainFromSide(int cardAddressInSide, DuelPlayer player) {

        ArrayList<Card> mainCards = player.getPlayDeck().getMainCards();
        ArrayList<Card> sideCards = player.getPlayDeck().getSideCards();
        if (mainCards.size() == 60)
            return GameViewMessage.NONE;
        mainCards.add(sideCards.get(cardAddressInSide));
        sideCards.remove(cardAddressInSide);
        return GameViewMessage.SUCCESS;
    }

    public GameViewMessage addCardToSideFromMain(int cardAddressInMain, DuelPlayer player) {

        ArrayList<Card> mainCards = player.getPlayDeck().getMainCards();
        ArrayList<Card> sideCards = player.getPlayDeck().getSideCards();
        if (sideCards.size() == 15)
            return GameViewMessage.NONE;
        sideCards.add(mainCards.get(cardAddressInMain));
        mainCards.remove(cardAddressInMain);
        return GameViewMessage.SUCCESS;
    }

    public void setView(BetweenRoundView view) {
        this.view = view;
    }

    public void setPlayer1(DuelPlayer player1, boolean isWithAi) {
        this.player1 = player1;
        this.isWithAi = isWithAi;
        this.firstMainSize = player1.getPlayDeck().getMainCards().size();
        this.firstSideSize = player1.getPlayDeck().getSideCards().size();
        current = player1;
    }

    public void setPlayer2(DuelPlayer player2, boolean isWithAi) {
        this.player2 = player2;
        this.isWithAi = isWithAi;
    }

    public DuelPlayer getPlayer1() {
        return player1;
    }

    public DuelPlayer getPlayer2() {
        return player2;
    }

    public boolean canChangeTurn() {
        if (firstMainSize == current.getPlayDeck().getMainCards().size()) {
            current = player2;
            firstSideSize = current.getPlayDeck().getSideCards().size();
            firstMainSize = current.getPlayDeck().getMainCards().size();
            return true;
        }
        return false;
    }
}
