package project.controller;

import project.model.Assets;
import project.model.Shop;
import project.model.card.Card;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class ShopController {
    private static ShopController instance = null;

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (instance == null) instance = new ShopController();
        return instance;
    }

    public String buyCard(String cardName, String username) {
        System.out.println(cardName);
        LinkedHashMap<String, Integer> cardsLinkedToNumber = Shop.getInstance().getCardsWithNumberOfThem();
        LinkedHashMap<String, Integer> cardsWithPrices = Shop.getInstance().getCardsWithPrices();
        if (cardsLinkedToNumber.get(cardName) == 0)
            return "not enough cards";
        else if (cardsLinkedToNumber.get(cardName) == -1)
            return "forbidden card!";
        synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
            if (cardsLinkedToNumber.get(cardName) != 0 && cardsLinkedToNumber.get(cardName) != -1) {
                Assets assets = Assets.getAssetsByUsername(username);
                Objects.requireNonNull(assets).decreaseCoin(cardsWithPrices.get(cardName));
                assets.addBoughtCard(Card.getCardByName(cardName));
                cardsLinkedToNumber.replace(cardName, cardsLinkedToNumber.get(cardName) - 1);
                return "success";
            } else {
                if (cardsLinkedToNumber.get(cardName) == 0)
                    return "not enough cards";
                else if (cardsLinkedToNumber.get(cardName) == -1)
                    return "forbidden card!";
            }
        }
        return "error!";
    }
}
