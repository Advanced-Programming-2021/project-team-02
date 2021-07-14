package project.model;

import project.model.card.Card;

import java.util.LinkedHashMap;
import java.util.Map;

public class Shop {
    private static Shop instance = null;
    private final LinkedHashMap<String, Integer> cards;
    private LinkedHashMap<String, Integer> cardsWithNumberOfThem;

    {
        cards = new LinkedHashMap<>();
        cardsWithNumberOfThem = new LinkedHashMap<>();
    }

    private Shop() {
    }

    public static Shop getInstance() {
        if (instance == null) instance = new Shop();
        return instance;
    }

    public Map<String, Integer> getCardsWithPrices() {
        return cards;
    }

    public LinkedHashMap<String, Integer> getCardsWithNumberOfThem() {
        return cardsWithNumberOfThem;
    }

    public void addCardToShop(Card card, int price) {
        cards.put(card.getName(), price);
        card.setPrice(price);
        cardsWithNumberOfThem.put(card.getName(), 0);
    }
}
