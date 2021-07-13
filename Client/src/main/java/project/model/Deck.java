package project.model;

import project.model.card.Card;
import project.model.card.Monster;
import project.model.card.Spell;
import project.model.card.Trap;
import project.model.card.informationofcards.CardType;
import project.view.messages.DeckMenuMessage;

import java.util.ArrayList;
import java.util.Collections;

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

    public DeckMenuMessage removeCardFromMainDeck(int index) {
        mainCards.remove(index);
        return DeckMenuMessage.CARD_DELETED;
    }

    public DeckMenuMessage removeCardFromSideDeck(int index) {
        sideCards.remove(index);
        return DeckMenuMessage.CARD_DELETED;
    }

    public Deck copy() throws CloneNotSupportedException {
        Deck deck = new Deck(this.name);
        for (Card card : this.mainCards)
            copyCardInMain(deck, card);
        for (Card card : this.sideCards)
            copyCardInSide(deck, card);
        return deck;
    }

    private void copyCardInMain(Deck deck, Card card) throws CloneNotSupportedException {
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

    private void copyCardInSide(Deck deck, Card card) throws CloneNotSupportedException {
        if (card.getCardType().equals(CardType.MONSTER)) {
            Monster monster = ((Monster) card).clone();
            deck.addCardToSideDeck(monster);
        } else if (card.getCardType().equals(CardType.SPELL)) {
            Spell spell = ((Spell) card).clone();
            deck.addCardToSideDeck(spell);
        } else {
            Trap trap = ((Trap) card).clone();
            deck.addCardToSideDeck(trap);
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(mainCards);
        Collections.shuffle(sideCards);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        int i = 1;
        for (Card card : mainCards) {
            if (card instanceof Monster) {
                Monster monster = (Monster) card;
                out.append(i).append(" - ").append(monster.getName());
            } else if (card instanceof Spell) {
                Spell spell = (Spell) card;
                out.append(i).append(" - ").append(spell.getName());
            } else {
                Trap trap = (Trap) card;
                out.append(i).append(" - ").append(trap.getName());
            }
            out.append(" ");
            i++;
        }
        return out.toString();
    }
}
