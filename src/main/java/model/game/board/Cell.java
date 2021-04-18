package model.game.board;

import model.card.Card;

public class Cell {
    private CellStatus cellStatus = CellStatus.EMPTY;
    private Card cardInCell;

    public void putCardInCell(Card card) {
        this.cardInCell = card;
    }

    public void changeCellStatus(CellStatus newStatus) {
        this.cellStatus = newStatus;
    }
}
