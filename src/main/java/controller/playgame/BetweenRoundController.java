package controller.playgame;

import model.game.DuelPlayer;
import view.gameview.BetweenRoundView;

public class BetweenRoundController {
    private static BetweenRoundController instance = null;
    private final BetweenRoundView view = BetweenRoundView.getInstance();

    private BetweenRoundController() {
    }

    public static BetweenRoundController getInstance() {
        if (instance == null)
            instance = new BetweenRoundController();
        return instance;
    }

    public void changeCard(int cardAddressInMainDeck, int cardAddressInSideDeck, DuelPlayer player) {

    }
}
