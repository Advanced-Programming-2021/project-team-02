package model.game.board;


import model.card.Card;

public class SpellZone {
    private Cell[] spellCells = new Cell[5];

    public SpellZone() {
        for (int i = spellCells.length - 1; i >= 0; i--) {
            spellCells[i] = new Cell();
        }
    }

    public void addCard(Card card, CellStatus status) {
        for (Cell cell : spellCells) {
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                cell.setCardInCell(card);
                cell.setCellStatus(status);
                return;
            }
        }
    }

    public void removewithCard(int address) {
        spellCells[address - 1].setCardInCell(null);
        spellCells[address - 1].setCellStatus(CellStatus.EMPTY);
    }


    public Cell getCellWithAddress(int address) {
        return spellCells[address - 1];
    }
}
