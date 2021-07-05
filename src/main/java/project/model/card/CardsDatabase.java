package project.model.card;

import com.opencsv.CSVReader;
import project.model.Shop;
import project.model.card.informationofcards.*;

import java.io.*;
import java.util.ArrayList;

public class CardsDatabase {
    private static CardsDatabase dataBase;
    private static final ArrayList<Card> allCards;

    static {
        allCards = new ArrayList<>();
    }

    private CardsDatabase() {
    }

    public static CardsDatabase getInstance() {
        if (dataBase == null)
            dataBase = new CardsDatabase();
        return dataBase;
    }

    public static ArrayList<Card> getAllCards() {
        return allCards;
    }

    public static void makeCardMonster(CardType cardType, String name, String id, MonsterActionType monsterActionType, MonsterEffect monsterEffect,
                                       int level, Attribute attribute, String description, int attackPower, int defensePower, MonsterType monsterType, int price) {
        Card card = new Monster(cardType, name, id, monsterActionType, monsterEffect, level, attribute, description, attackPower, defensePower, monsterType);
        allCards.add(card);
        Shop.getInstance().addCardToShop(card, price);
    }

    public static void makeCardSpell(CardType cardType, String name, String id, SpellEffect spellEffect,
                                     Attribute attribute, String description, SpellType spellType, boolean isLimited, int price) {
        Card card = new Spell(cardType, name, id, spellEffect, attribute, description, spellType, isLimited);
        allCards.add(card);
        Shop.getInstance().addCardToShop(card, price);
    }

    public static void makeTrapCard(CardType cardType, String name, String id, TrapEffect trapEffect,
                                    Attribute attribute, String description, TrapType trapType, boolean isLimited, int price) {
        Card card = new Trap(cardType, name, id, trapEffect, attribute, description, trapType, isLimited);
        allCards.add(card);
        Shop.getInstance().addCardToShop(card, price);
    }

    public void readAndMakeCards() throws IOException {

        FileReader fileReaderMonster = new FileReader("csv/Monster.csv");
        CSVReader csvReaderMonster = new CSVReader(fileReaderMonster);
        String[] monstersLines;
        csvReaderMonster.readNext();
        while ((monstersLines = csvReaderMonster.readNext()) != null) {
            makeCardMonster(CardType.MONSTER, monstersLines[0], "1", MonsterActionType.getActionTypeByName(monstersLines[4]),
                    MonsterEffect.getMonsterEffectByName(monstersLines[0]), Integer.parseInt(monstersLines[1]),
                    Attribute.getAttributeByName(monstersLines[2]), monstersLines[7], Integer.parseInt(monstersLines[5]),
                    Integer.parseInt(monstersLines[6]), MonsterType.getMonsterTypeByName(monstersLines[3]), Integer.parseInt(monstersLines[8]));
        }

        FileReader fileReaderSpellTrap = new FileReader("csv/SpellTrap.csv");
        CSVReader csvReaderSpellTrap = new CSVReader(fileReaderSpellTrap);
        String[] spellsTrapsLines;
        csvReaderSpellTrap.readNext();
        int counter = 0;
        while ((spellsTrapsLines = csvReaderSpellTrap.readNext()) != null) {
            makeTrapCard(CardType.TRAP, spellsTrapsLines[0], "1",
                    TrapEffect.getTrapEffectByName(spellsTrapsLines[0]), Attribute.TRAP, spellsTrapsLines[3],
                    TrapType.getTrapTypeByTypeName(spellsTrapsLines[2]), spellsTrapsLines[4].equals("Limited"),
                    Integer.parseInt(spellsTrapsLines[5]));
            counter++;
            if (counter == 5) break;
        }
        while ((spellsTrapsLines = csvReaderSpellTrap.readNext()) != null) {
            makeCardSpell(CardType.SPELL, spellsTrapsLines[0], "1", SpellEffect.getSpellByName(spellsTrapsLines[0]),
                    Attribute.SPELL, spellsTrapsLines[3], SpellType.getSpellTypeByTypeName(spellsTrapsLines[2]),
                    spellsTrapsLines[4].equals("Limited"), Integer.parseInt(spellsTrapsLines[5]));
        }
        Monster.jsonMonsters();
        Spell.spellsToJson();
        Trap.trapToJson();
        ArrayList<Monster> allMonsters = Monster.getAllMonsters();
        ArrayList<Spell> allSpells = Spell.getAllSpells();
        ArrayList<Trap> allTraps = Trap.getAllTraps();
//        allCards.addAll(allMonsters);
//        allCards.addAll(allSpells);
//        allCards.addAll(allTraps);
        //TODO for reading information from Gson
//        User.fromJson();
//        Monster.fromJson();
//        Spell.fromJson();
//        Trap.fromJson();
//        Assets.fromJson();
    }
}