package controller;

import model.Assets;
import model.Shop;
import model.User;
import model.card.Card;
import view.ShopMenuView;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.Objects;

public class ShopMenuController {
    private User loggedInUser;
    private static ShopMenuController instance = null;
    private final ShopMenuView view = ShopMenuView.getInstance ();

    private ShopMenuController() {}

    public static ShopMenuController getInstance() {
        if (instance == null) instance = new ShopMenuController ();
        return instance;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void buyCard(String cardName) {
        Card card = Card.getCardByName (cardName);
        Assets assets = Assets.getAssetsByUsername (MainMenuController.getInstance ().getLoggedInUser ().getUsername ());
        if (card == null) {
            Error.showError (Error.CARD_DOES_NOT_EXIST);
            return;
        }
        if (Objects.requireNonNull (assets).getCoin () < Shop.getCards ().get (card)) {
            Error.showError (Error.NOT_ENOUGH_MONEY);
            return;
        }
        Objects.requireNonNull (assets).decreaseCoin (Shop.getCards ().get (card));
        assets.addCard (card);
        view.showDynamicSuccessMessage (SuccessMessage.BOUGHT_CARD_SUCCESSFULLY, cardName);
    }
}
