package project.controller;

import project.model.Assets;
import project.model.Shop;
import project.model.User;
import project.model.card.Card;
import project.view.messages.Error;
import project.view.messages.ShopMenuMessage;

import java.util.Objects;

public class ShopMenuController {
    private User loggedInUser;
    private static ShopMenuController instance = null;

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

    public ShopMenuMessage buyCard(String cardName) {
        Card card = Card.getCardByName (cardName);
        if (Card.getCardByName(cardName) == null) System.out.println("ridi");
        //Assets assets = Assets.getAssetsByUsername (MainMenuController.getInstance ().getLoggedInUser ().getUsername ());
        Assets assets = Assets.getAssetsByUsername("mahdi");
        if (Objects.requireNonNull (assets).getCoin () < Shop.getCards ().get (card)) {
            return ShopMenuMessage.NOT_ENOUGH_MONEY;
        }
        Objects.requireNonNull (assets).decreaseCoin (Shop.getCards ().get (card));
        assets.addCard (card);
        return ShopMenuMessage.CARD_ADDED;
//        project.view.showDynamicSuccessMessage (SuccessMessage.BOUGHT_CARD_SUCCESSFULLY, cardName);
    }
}
