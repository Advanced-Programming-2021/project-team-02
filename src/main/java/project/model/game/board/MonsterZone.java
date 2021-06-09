package project.model.game.board;

import project.model.card.Monster;

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
        monsterCells[address].setCellStatus(CellStatus.EMPTY);
        monsterCells[address].setCardInCell(null);

    }

    public Cell getCellWithAddress(int address) {
        return monsterCells[address-1];
    }
}
