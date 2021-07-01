package project.model.game.board;


import project.model.card.Card;

public class SpellZone {
    private Cell[] spellCells = new Cell[5];

    public SpellZone() {
        for (int i = spellCells.length - 1; i >= 0; i--) {
            spellCells[i] = new Cell();
        }
    }

    public int addCard(Card card, CellStatus status) {
        for (int i = 1; i <= 5; i++) {
            if (getCellWithAddress(i).getCellStatus() == CellStatus.EMPTY) {
                getCellWithAddress(i).setCardInCell(card);
                getCellWithAddress(i).setCellStatus(status);
                return i;
            }
        }
        return -1;
    }

    public void removeWithCardAddress(int address) {
        spellCells[address - 1].setCardInCell(null);
        spellCells[address - 1].setCellStatus(CellStatus.EMPTY);
    }


    public Cell getCellWithAddress(int address) {
        return spellCells[address - 1];
    }
}
