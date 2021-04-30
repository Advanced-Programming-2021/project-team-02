package model.card;

import model.card.informationofcards.*;

public class Monster extends Card {
    private int level;
    private int attackPower, defensePower;
    private MonsterType monsterType;
    private MonsterEffect monsterEffect;
    private MonsterActionType monsterActionType;

    public Monster(CardType cardType, String name, String id, MonsterActionType monsterActionType, MonsterEffect monsterEffect, int level, Attribute attribute,
                   String description, int attackPower, int defensePower, MonsterType monsterType) {
        super(cardType, name, id, attribute, description);
        setAttackPower(attackPower);
        setMonsterType(monsterType);
        setDefensePower(defensePower);
        setLevel(level);
        setMonsterActionType(monsterActionType);
        setMonsterEffect(monsterEffect);
    }

    public void setMonsterActionType(MonsterActionType monsterActionType) {
        this.monsterActionType = monsterActionType;
    }

    private void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    private void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    private void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    private void setMonsterEffect(MonsterEffect monsterEffect) {
        this.monsterEffect = monsterEffect;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Name: " + this.name +
                "\nLevel: " + this.level +
                "\nType: " + this.monsterType.getLabel() +
                "\nATK: " + this.attackPower +
                "\nDEF: " + this.defensePower +
                "\nDescription: " + this.description;
    }

//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
}