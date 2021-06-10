package project.model.game;

import project.model.card.Card;
import project.model.card.Monster;
import project.model.card.Spell;
import project.model.game.board.*;

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
        spellZone.addCard(card, status);
    }

    public void removeMonsterFromBoardAndAddToGraveYard(int address) {
        graveYard.addCard(monsterZone.getCellWithAddress(address).getCardInCell());
        monsterZone.removeCard(address - 1);

    }

    public void removeSpellOrTrapFromBoard(int address) {
        graveYard.addCard(spellZone.getCellWithAddress(address).getCardInCell());
        spellZone.removeWithCardAddress(address);
    }

    public void addCardToGraveYard(Card card) {
        graveYard.addCard(card);
    }

    public Card getCardInGraveYard(int address) {
        return graveYard.getGraveYardCards().get(address);
    }

    public void changeCardPosition(Card card) {

    }

    public boolean isMonsterZoneFull() {
        if (howManyEmptyCellsWeHaveInZone(Zone.MONSTER_ZONE) == 0)
            return true;
        return false;
    }

    public boolean isSpellZoneFull() {
        if (howManyEmptyCellsWeHaveInZone(Zone.SPELL_ZONE) == 0)
            return true;
        return false;
    }

    public boolean isMonsterZoneEmpty() {
        if (howManyEmptyCellsWeHaveInZone(Zone.MONSTER_ZONE) == 5)
            return true;
        return false;
    }

    public int howManyEmptyCellsWeHaveInZone(Zone zone) {
        if (zone.equals(Zone.MONSTER_ZONE)) {
            int counter = 0;
            for (int i = 1; i <= 5; i++) {
                if (monsterZone.getCellWithAddress(i).getCellStatus().equals(CellStatus.EMPTY)) {
                    counter++;
                }
            }
            return counter;
        } else if (zone.equals(Zone.SPELL_ZONE)) {
            int counter = 0;
            for (int i = 1; i <= 5; i++) {
                if (spellZone.getCellWithAddress(i).getCellStatus().equals(CellStatus.EMPTY)) {
                    counter++;
                }
            }
            return counter;
        }
        return 0;
    }

    public Cell getACellOfBoard(Zone zone, int index) {
        if (zone.equals(Zone.MONSTER_ZONE)) {
            return monsterZone.getCellWithAddress(index);
        } else if (zone.equals(Zone.SPELL_ZONE)) {
            return spellZone.getCellWithAddress(index);
        }
        return null;
    }

    public MonsterZone returnMonsterZone() {
        return monsterZone;
    }

    public SpellZone returnSpellZone() {
        return spellZone;
    }

    public GraveYard returnGraveYard() {
        return graveYard;
    }

    public FieldZone returnFieldZone() {
        return fieldZone;
    }

    public boolean isGraveYardEmpty() {
        return graveYard.getGraveYardCards().size() == 0;
    }

    public void removeFieldSpell() {
        fieldZone.setCard(null);
        fieldZone.setCellStatus(CellStatus.EMPTY);
    }

    public boolean isFieldZoneEmpty() {
        return fieldZone.getCellStatus().equals(CellStatus.EMPTY);
    }

    public FieldZone getFieldZone() {
        return fieldZone;
    }

    public void faceUpActiveFieldSpell(Spell spell) {
        fieldZone.setCard(spell);
        fieldZone.setCellStatus(CellStatus.OCCUPIED);
    }
    public void setFieldSpell(Spell spell){
        fieldZone.setCard(spell);
        fieldZone.setCellStatus(CellStatus.HIDDEN);
    }
}