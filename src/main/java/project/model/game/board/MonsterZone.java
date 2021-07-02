package project.model.game.board;

import project.model.card.Monster;

public class MonsterZone {
    private Cell[] monsterCells = new Cell[5];

    public MonsterZone() {
        for (int i = monsterCells.length - 1; i >= 0; i--) {
            monsterCells[i] = new Cell();
        }
    }

    public int addCard(Monster monster, CellStatus status) {
        for (int addressOfAdd = 0; addressOfAdd < monsterCells.length; addressOfAdd++) {
            Cell cell = monsterCells[addressOfAdd];
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                cell.setCardInCell(monster);
                cell.setCellStatus(status);
                return (addressOfAdd + 1);
            }
        }
        return 0;
    }

    public void removeCardWithAddress(int address) {
        monsterCells[address - 1].setCellStatus(CellStatus.EMPTY);
        monsterCells[address - 1].setCardInCell(null);

    }

    public Cell getCellWithAddress(int address) {
        return monsterCells[address - 1];
    }
}
