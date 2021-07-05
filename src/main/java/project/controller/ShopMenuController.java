package project.controller;

import project.model.Assets;
import project.model.Shop;
import project.model.User;
import project.model.card.Card;
import project.view.messages.ShopMenuMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        Assets assets = Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername());
        if (Objects.requireNonNull(assets).getCoin() < Shop.getCards().get(card)) {
            return ShopMenuMessage.NOT_ENOUGH_MONEY;
        }
        Objects.requireNonNull(assets).decreaseCoin(Shop.getCards().get(card));
        System.out.println(assets.getCoin());
        assets.addCard(card);
        HashMap<Card, Integer> arrayList = assets.getAllUserCards();
        for (Card card1 : arrayList.keySet()) {
            System.out.println(card1.getName() + " : " + arrayList.get(card1));
        }
        return ShopMenuMessage.CARD_ADDED;
    }
}
