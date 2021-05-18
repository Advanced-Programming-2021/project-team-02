package model.card;

import model.Shop;
import model.card.informationofcards.Attribute;
import model.card.informationofcards.CardType;

import java.util.ArrayList;

public abstract class Card {
    protected CardType cardType;
    protected String name;
    protected Attribute attribute;
    protected String description;
    protected String id;

    protected Card(CardType cardType, String name, String id, Attribute attribute, String description) {
        setCardType(cardType);
        setName(name);
        setAttribute(attribute);
        setDescription(description);
        setId(id);
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getName() {
        return name;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public String getDescription() {
        return description;
    }

    public static Card getCardByName(String cardName) {
        ArrayList<Card> cards = CardsDatabase.getAllCards();
        for (Card card : cards) {
            if (card.getName().equalsIgnoreCase(cardName))
                return card;
        }
        return null;
    }
}