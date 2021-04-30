package model;

import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.card.informationofcards.CardType;

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

    public int getNumberOfCardInDeck(Card card) {
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
        return (this.mainCards.size() >= 40);
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

    public Deck copy() throws CloneNotSupportedException {
        Deck deck = new Deck(this.name);
        for (Card card : this.mainCards)
            addCardInCopyDeckSideOrMain(deck, card);
        for (Card card : this.sideCards)
            addCardInCopyDeckSideOrMain(deck, card);
        return deck;
    }

    private void addCardInCopyDeckSideOrMain(Deck deck, Card card) throws CloneNotSupportedException {
        if (card.getCardType().equals(CardType.MONSTER)) {
            Monster monster = ((Monster) card).clone();
            deck.addCardToMainDeck(monster);
        } else if (card.getCardType().equals(CardType.SPELL)) {
            Spell spell = ((Spell) card).clone();
            deck.addCardToMainDeck(spell);
        } else {
            Trap trap = ((Trap) card).clone();
            deck.addCardToMainDeck(trap);
        }
    }

    public Deck shuffleDeck() {
        return null;
    }
}
