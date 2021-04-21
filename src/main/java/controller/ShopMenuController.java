package controller;

import model.Assets;
import model.Shop;
import model.User;
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.card.informationofcards.CardType;
import view.ShopMenuView;
import view.messages.Error;

import java.util.Objects;
import java.util.regex.Matcher;

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

    public void buyCard(Matcher matcher) {
        String cardName = matcher.group ("cardName");
        Card card = Card.getCardByName (cardName);
        Assets assets = Assets.getAssetsByUsername (ShopMenuController.getInstance ().getLoggedInUser ().getUsername ());
        if (card == null) {
            view.showError (Error.CARD_DOES_NOT_EXIST);
            return;
        }
        if (Objects.requireNonNull (assets).getCoin () < Shop.getCards ().get (card)) {
            view.showError (Error.NOT_ENOUGH_MONEY);
            return;
        }
        Objects.requireNonNull (assets).decreaseCoin (Shop.getCards ().get (card));
        assets.addCard (card);
    }
}
