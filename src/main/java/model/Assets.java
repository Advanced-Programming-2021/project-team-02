package model;

import model.card.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Assets {
    private String username;
    private int coin;
    private final HashMap<Card, Integer> allCards;
    private final ArrayList<Deck> allDecks;
    private static final HashMap<String, Assets> allAssets;

    static {
        allAssets = new HashMap<>();
    }

    {
        coin = 100000;
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

    public void createDeck(String name) {
        this.allDecks.add(new Deck(name));
    }

    public void deleteDeck(String name) {
        Deck deck = getDeckByDeckName(name);
        if (deck.isActivated())
            Objects.requireNonNull(User.getUserByUsername(username)).deactivatedDeck();
        for (Card card : allCards.keySet()) {
            for (Card mainCard : deck.getMainCards()) {
                if (card.getName().equals(mainCard.getName()))
                    allCards.replace(card, allCards.get(card) + 1);
                for (Card sideCard : deck.getSideCards()) {
                    if (card.getName().equals(sideCard.getName()))
                        allCards.replace(card, allCards.get(card) + 1);
                }
            }
        }
        allDecks.remove(deck);
    }

    public void activateDeck(String deckName) {
        Deck deck = getDeckByDeckName(deckName);
        deck.setActivated(true);
        Objects.requireNonNull(User.getUserByUsername(username)).activatedDeck();
    }

    public void addCardToMainDeck(Card card, Deck deck) {
        deck.addCardToMainDeck(card);
        allCards.replace(card, allCards.get(card) - 1);
    }

    public void addCardToSideDeck(Card card, Deck deck) {
        deck.addCardToSideDeck(card);
        allCards.replace(card, allCards.get(card) - 1);
    }

    public void removeCardFromMainDeck(Card card, Deck deck) {
        deck.removeCardFromMainDeck(card);
        allCards.replace(card, allCards.get(card) + 1);
    }

    public void removeCardFromSideDeck(Card card, Deck deck) {
        deck.removeCardFromSideDeck(card);
        allCards.replace(card, allCards.get(card) + 1);
    }

    public void decreaseCoin(int amount) {
        coin -= amount;
    }
}
