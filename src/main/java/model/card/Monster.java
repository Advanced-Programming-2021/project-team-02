package model.card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.card.informationofcards.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Monster extends Card {
    private static ArrayList<Monster> allMonsters = new ArrayList<>();
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
        allMonsters.add(this);
    }

    public void setMonsterActionType(MonsterActionType monsterActionType) {
        this.monsterActionType = monsterActionType;
    }

    private void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    private void setMonsterEffect(MonsterEffect monsterEffect) {
        this.monsterEffect = monsterEffect;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public MonsterActionType getMonsterActionType() {
        return monsterActionType;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public static void jsonMonsters(){
        try {
            FileWriter fileWriter = new FileWriter("monster.json");
            fileWriter.write(new Gson().toJson(allMonsters));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readJsonMonster() {

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

    @Override
    public Monster clone() throws CloneNotSupportedException {
        return new Monster(cardType, name, id, monsterActionType, monsterEffect, level, attribute,
                description, attackPower, defensePower, monsterType);
    }

    public void changeAttackPower(int change) {
        attackPower += change;
    }

    public void changeDefensePower(int change) {
        defensePower += change;
    }

    public MonsterEffect getMonsterEffect() {
        return monsterEffect;
    }
}