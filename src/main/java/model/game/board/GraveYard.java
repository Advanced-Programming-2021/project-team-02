package model.game.board;

import model.card.Card;

import java.util.ArrayList;

public class GraveYard {
    private ArrayList<Card> cards;

    public GraveYard() {
        cards = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "GraveYard{" +
                "cards=" + cards +
                '}';
    }
}
