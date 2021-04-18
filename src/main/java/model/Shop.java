package model;

import model.card.Card;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private static Shop instance = null;
    private static final Map<Card, Integer> cards;

    static {
        cards = new HashMap<>();
    }

    private Shop() {}

    public static Shop getInstance() {
        if (instance == null) instance = new Shop ();
        return instance;
    }

    public static Map<Card, Integer> getCards() {
        return cards;
    }

    public void addCardToShop(Card card, int price) {
        cards.put(card, price);
    }
}
