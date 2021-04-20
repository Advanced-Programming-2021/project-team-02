package model;

import model.card.Card;

import java.util.ArrayList;

public class Deck {
    private final ArrayList<Card> mainCards;
    private final ArrayList<Card> sideCards;
    private String name;
    private boolean isActivated = false;

    public Deck(String name) {
        setName(name);
        mainCards = new ArrayList<>();
        sideCards = new ArrayList<>();
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCardToMainDeck(Card card) {
        mainCards.add(card);
    }

    public void addCardToSideDeck(Card card) {
        sideCards.add(card);
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isMainFull() {
        return mainCards.size() == 60;
    }

    public boolean isSideFull() {
        return sideCards.size() == 15;
    }

    public int getNumberOfCardInMainDeck(Card card) {
        int counter = 0;
        for (Card cardInDeck : mainCards) {
            if (cardInDeck.getName().equals(card.getName()))
                counter++;
        }
        for (Card sideCardInDeck : sideCards) {
            if (sideCardInDeck.getName().equals(card.getName()))
                counter++;
        }
        return counter;
    }

    public ArrayList<Card> getMainCards() {
        return mainCards;
    }

    public ArrayList<Card> getSideCards() {
        return sideCards;
    }

    public boolean isValidDeck() {
        return !(this.mainCards.size() >= 40);
    }

    public boolean containsMainCard(String cardName) {
        for (Card card : mainCards) {
            if (card.getName().equals(cardName))
                return true;
        }
        return false;
    }

    public boolean containsSideCard(String cardName) {
        for (Card card : sideCards) {
            if (card.getName().equals(cardName))
                return true;
        }
        return false;
    }

    public void removeCardFromMainDeck(Card card) {
        mainCards.remove(card);
    }

    public void removeCardFromSideDeck(Card card) {
        sideCards.remove(card);
    }
}
