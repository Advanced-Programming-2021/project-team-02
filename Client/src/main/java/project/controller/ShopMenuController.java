package project.controller;

import project.model.Assets;
import project.model.Shop;
import project.model.card.Card;
import project.view.messages.ShopMenuMessage;

import java.util.HashMap;
import java.util.Objects;

public class ShopMenuController {
    private static ShopMenuController instance = null;

    private ShopMenuController() {
    }

    public static ShopMenuController getInstance() {
        if (instance == null) instance = new ShopMenuController();
        return instance;
    }

    public ShopMenuMessage buyCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        if (Objects.requireNonNull(assets).getCoin() < Shop.getCards().get(card)) {
            return ShopMenuMessage.NOT_ENOUGH_MONEY;
        }
        Objects.requireNonNull(assets).decreaseCoin(Shop.getCards().get(card));
        assets.addCard(card);
        return ShopMenuMessage.CARD_ADDED;
    }
}
