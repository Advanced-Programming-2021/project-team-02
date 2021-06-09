package project.model.game.board;

import project.model.card.Card;

public class Cell {
    private CellStatus cellStatus = CellStatus.EMPTY;
    private Card cardInCell;

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
