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
    public void addMonsterToBoard(Monster monster,CellStatus status){

    }
    public void addSpellOrTrapToBoard(Card card, CellStatus status){

    }
    public void addCardToGraveYard(Card card){

    }
    public void changeCartPosition(Card card){

    }
}