package model;

import model.card.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class Assets {
    private String username;
    private int coin;
    private final HashMap<Card, Integer> allCards;
    private final ArrayList<Deck> allDecks;
    private static final HashMap<String, Assets> allAssets;

    static {
        allAssets = new HashMap<>();
    }

    public Assets(String username) {
        setUsername(username);
        allDecks = new ArrayList<>();
        allCards = new HashMap<>();
        allAssets.put(username, this);
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public Deck getDeckByDeckName(String name) {
        for (Deck deck : allDecks)
            if (deck.getName().equals(name)) return deck;
        return null;
    }

    public HashMap<Card, Integer> getAllCards() {
        return allCards;
    }

    public ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

    public int getCoin() {
        return coin;
    }

    public static Assets getAssetsByUsername(String username) {
        for (String key : allAssets.keySet())
            if (key.equals(username)) return allAssets.get(key);
        return null;
    }

    public void addDeck(Deck deck) {
        this.allDecks.add(deck);
    }

    public void addCard(Card card) {

    }

    public void decreaseCoin(int amount) {
        coin -= amount;
    }
}
