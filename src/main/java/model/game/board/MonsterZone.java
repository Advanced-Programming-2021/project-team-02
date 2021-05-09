package model.game.board;

import model.card.Card;
import model.card.Monster;

import java.util.Collections;

public class MonsterZone {
    private Cell[] monsterCells = new Cell[5];

    public MonsterZone() {

    }

    public void addCard(Monster monster, CellStatus status) {
        for (Cell cell : monsterCells) {
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                cell.setCardInCell(monster);
                cell.setCellStatus(status);
                return;
            }
        }
    }

    public void removeCard(int address) {
        monsterCells[address] = null;
        monsterCells[address].setCellStatus(CellStatus.EMPTY);
    }

    public Cell getCellWithAddress(int address) {
        return monsterCells[address];
    }
}
