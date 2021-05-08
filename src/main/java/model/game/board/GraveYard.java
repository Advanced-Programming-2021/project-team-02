package model.game.board;

import model.card.Card;

import java.util.ArrayList;

public class GraveYard {
    private ArrayList<Card> cards;

    public GraveYard() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getGraveYard(){
        return cards;
    }

    @Override
    public String toString() {
        return "GraveYard{" +
                "cards=" + cards +
                '}';
    }
}
