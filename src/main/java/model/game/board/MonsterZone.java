package model.game.board;

import model.card.Card;
import model.card.Monster;

import java.util.Collections;

public class MonsterZone {
    private Cell[] monsterCells = new Cell[5];

    public MonsterZone() {
        for (int i = monsterCells.length - 1; i >= 0; i--) {
            monsterCells[i] = new Cell();
        }
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
        return monsterCells[address-1];
    }
}
