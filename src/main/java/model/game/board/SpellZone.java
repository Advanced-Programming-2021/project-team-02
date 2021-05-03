package model.game.board;


import model.card.Card;

public class SpellZone {
    private Cell[] spellCells = new Cell[5];

    public void addCard(Card card) {

    }

    public void removeCard(int address) {

    }

    public Cell getCellWithAddress(int address) {
        return spellCells[address];
    }
}
