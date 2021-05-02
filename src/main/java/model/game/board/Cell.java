package model.game.board;

import model.card.Card;

public class Cell {
    private CellStatus cellStatus = CellStatus.EMPTY;
    private Card cardInCell;

    public void putCardInCell(Card card) {
        this.cardInCell = card;
    }

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
}
