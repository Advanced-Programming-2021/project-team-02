package model.game;

import model.card.Card;
import model.card.Monster;
import model.game.board.*;

public class PlayerBoard {
    private MonsterZone monsterZone;
    private SpellZone spellZone;
    private GraveYard graveYard;
    private FieldZone fieldZone;

    public PlayerBoard() {
        monsterZone = new MonsterZone();
        spellZone = new SpellZone();
        graveYard = new GraveYard();
        fieldZone = new FieldZone();
    }

    public void addMonsterToBoard(Monster monster, CellStatus status) {
        monsterZone.addCard(monster, status);
    }

    public void addSpellOrTrapToBoard(Card card, CellStatus status) {

    }

    public void removeMonsterFromBoard(int address) {
        monsterZone.removeCard(address - 1);
    }

    public void removeSpellOrTrapFromBoard() {

    }

    public void addCardToGraveYard(Card card) {

    }

    public void changeCardPosition(Card card) {

    }

    public boolean isMonsterZoneFull() {
        for (Cell cell : monsterZone.getMonsterCells()) {
            if (cell.getCellStatus().equals(CellStatus.EMPTY))
                return false;
        }
        return true;
    }

    public Cell getACellOfBoard(Zone zone, int index) {
        if (zone.equals(Zone.MONSTER_ZONE)) {
            return monsterZone.getCellWithAddress(index);
        } else if (zone.equals(Zone.SPELL_ZONE)) {
            return spellZone.getCellWithAddress(index);
        }
        return null;
    }

    public MonsterZone returnMonsterZone(){
        return monsterZone;
    }
}