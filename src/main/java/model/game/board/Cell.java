package model.game.board;

import model.card.Card;

public class Cell {
    private CellStatus cellStatus = CellStatus.EMPTY;
    private Card cardInCell;
    private boolean hasStatusChanged = false;

    public void setCardInCell(Card cardInCell) {
        this.cardInCell = cardInCell;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

    public Card getCardInCell() {
        return cardInCell;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void changeCellStatus(CellStatus newStatus) {
        this.cellStatus = newStatus;
    }

    public void changedStatus(boolean change) {
        hasStatusChanged = change;
    }

    public boolean hasStatusChanged() {
        return hasStatusChanged;
    }
}
