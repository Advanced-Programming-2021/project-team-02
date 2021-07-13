package project.model;

import project.model.card.Card;

import java.util.LinkedHashMap;
import java.util.Map;

public class Shop {
    private static Shop instance = null;
    private LinkedHashMap<String, Integer> cardsWithPrices;
    private LinkedHashMap<String, Integer> cardsWithNumberOfThem;

    {
        cardsWithPrices = new LinkedHashMap<>();
        cardsWithNumberOfThem = new LinkedHashMap<>();
    }

    private Shop() {
    }

    public static Shop getInstance() {
        if (instance == null) instance = new Shop();
        return instance;
    }

    public LinkedHashMap<String, Integer> getCardsWithPrices() {
        return cardsWithPrices;
    }

    public void addCardToShop(Card card, int price) {
        cardsWithPrices.put(card.getName(), price);
        card.setPrice(price);
        cardsWithNumberOfThem.put(card.getName(), 5);
    }

    public LinkedHashMap<String, Integer> getCardsWithNumberOfThem() {
        return cardsWithNumberOfThem;
    }
}
