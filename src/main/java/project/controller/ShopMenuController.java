package project.controller;

import project.model.Assets;
import project.model.Shop;
import project.model.card.Card;
import project.view.messages.ShopMenuMessage;
import java.util.Objects;

public class ShopMenuController {
    private static ShopMenuController instance = null;

    private ShopMenuController() {}

    public static ShopMenuController getInstance() {
        if (instance == null) instance = new ShopMenuController ();
        return instance;
    }

    public ShopMenuMessage buyCard(String cardName) {
        Card card = Card.getCardByName (cardName);
        //Assets assets = Assets.getAssetsByUsername (MainMenuController.getInstance ().getLoggedInUser ().getUsername ());
        Assets assets = Assets.getAssetsByUsername("mahdi");
        if (Objects.requireNonNull (assets).getCoin () < Shop.getCards ().get (card)) {
            return ShopMenuMessage.NOT_ENOUGH_MONEY;
        }
        Objects.requireNonNull (assets).decreaseCoin (Shop.getCards ().get (card));
        System.out.println(assets.getCoin());
        assets.addCard (card);
        return ShopMenuMessage.CARD_ADDED;
//        project.view.showDynamicSuccessMessage (SuccessMessage.BOUGHT_CARD_SUCCESSFULLY, cardName);
    }
}
