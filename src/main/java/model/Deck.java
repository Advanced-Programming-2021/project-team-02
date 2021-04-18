package model;

import model.card.Card;
import model.card.CardsDatabase;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> mainCards;
    private ArrayList<Card> sideCards;
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

    public void addCardToMainDeck(String cardName) {
        mainCards.add(CardsDatabase.getCardByName(cardName));
    }

    public void addCardToSideDeck(String cardName) {
        sideCards.add(CardsDatabase.getCardByName(cardName));
    }
}
