package model.game.board;


import model.card.Card;

public class SpellZone {
    private Cell[] spellCells = new Cell[5];

    public void addCard(Card card, CellStatus status) {
        for (Cell cell : spellCells) {
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                cell.setCardInCell(card);
                cell.setCellStatus(status);
                return;
            }
        }
    }

    public void removeCard(int address) {
        spellCells[address] = null;
        spellCells[address].setCellStatus(CellStatus.EMPTY);
    }

    public void reset() {

    }

    public Cell getCellWithAddress(int address) {
        return spellCells[address];
    }
}
