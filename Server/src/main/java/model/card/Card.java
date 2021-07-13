package project.model.card;

import project.model.card.informationofcards.Attribute;
import project.model.card.informationofcards.CardType;

import java.util.ArrayList;

public abstract class Card {
    protected CardType cardType;
    protected String name;
    protected Attribute attribute;
    protected String description;
    protected String id;
    protected int price;

    protected Card(CardType cardType, String name, String id, Attribute attribute, String description) {
        setCardType(cardType);
        setName(name);
        setAttribute(attribute);
        setDescription(description);
        setId(id);
    }

    public static Card getCardByName(String cardName) {
        ArrayList<Card> cards = CardsDatabase.getAllCards();
        for (Card card : cards) {
            if (card.getName().equalsIgnoreCase(cardName))
                return card;
        }
        return null;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private void setId(String id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    private void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    private void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }
}