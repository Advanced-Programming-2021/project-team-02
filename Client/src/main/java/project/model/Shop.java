package project.model;

import project.model.card.Card;

import java.util.LinkedHashMap;
import java.util.Map;

public class Shop {
    private static Shop instance = null;
    private static final LinkedHashMap<String, Integer> cards;

    static {
        cards = new LinkedHashMap<> ();
    }

    private Shop() {}

    public static Shop getInstance() {
        if (instance == null) instance = new Shop ();
        return instance;
    }

    public  Map<String, Integer> getCards() {
        return cards;
    }

    public void addCardToShop(Card card, int price) {
        cards.put(card.getName(), price);
        card.setPrice(price);
    }
}
