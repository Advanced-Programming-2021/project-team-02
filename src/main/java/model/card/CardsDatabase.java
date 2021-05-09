package model.card;

import com.opencsv.CSVReader;
import model.Shop;
import model.card.informationofcards.*;

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
//        BufferedReader csvReader = new BufferedReader(new FileReader("Monster.csv"));
//        csvReader.readLine();
        //       String row;
        //      int counter = 2;
//        while ((row = csvReader.readLine()) != null) {
//            row = row.replaceAll("\"", "");
//            if (counter == 38)
//                row += "\n" + csvReader.readLine().replaceAll("\"", "");
//            String[] data = row.split("[,]+(?=\\S|$)");
//            makeCardMonster(CardType.MONSTER, data[0], "1", MonsterActionType.getActionTypeByName(data[4]),
//                    MonsterEffect.getMonsterEffectByName(data[0]), Integer.parseInt(data[1]),
//                    Attribute.getAttributeByName(data[2]), data[7], Integer.parseInt(data[5]),
//                    Integer.parseInt(data[6]), MonsterType.getMonsterTypeByName(data[3]), Integer.parseInt(data[8]));
//            counter++;
//        }
//        csvReader = new BufferedReader(new FileReader("SpellTrap.csv"));
//        csvReader.readLine();
//        counter = 1;
//        while (counter != 12) {
//            row = csvReader.readLine();
//            row = row.replaceAll("\"", "");
//            String[] data = row.split("[,]+(?=\\S|$)");
//            makeTrapCard(CardType.TRAP, data[0], "1", TrapEffect.getTrapEffectByName(data[0]), Attribute.TRAP, data[3],
//                    TrapType.getTrapTypeByTypeName(data[2]), data[4].equals("Limited"), Integer.parseInt(data[5]));
//            counter++;
//        }
//        counter++;
//        while ((row = csvReader.readLine()) != null) {
//            row = row.replaceAll("\"", "");
//            if (counter == 35)
//                row += "\n" + csvReader.readLine().replaceAll("\"", "") + "\n" + csvReader.readLine().replaceAll("\"", "");
//            String[] data = row.split("[,]+(?=\\S|$)");
//            makeCardSpell(CardType.SPELL, data[0], "1", SpellEffect.getSpellByName(data[0]), Attribute.SPELL, data[3],
//                    SpellType.getSpellTypeByTypeName(data[2]), data[4].equals("Limited"), Integer.parseInt(data[5]));
//            counter++;
//        }
//        csvReader.close();

        FileReader fileReaderMonster = new FileReader("Monster.csv");
        CSVReader csvReaderMonster = new CSVReader(fileReaderMonster);
        String[] monstersLines;
        csvReaderMonster.readNext();
        while ((monstersLines = csvReaderMonster.readNext()) != null) {
            makeCardMonster(CardType.MONSTER, monstersLines[0], "1", MonsterActionType.getActionTypeByName(monstersLines[4]),
                    MonsterEffect.getMonsterEffectByName(monstersLines[0]), Integer.parseInt(monstersLines[1]),
                    Attribute.getAttributeByName(monstersLines[2]), monstersLines[7], Integer.parseInt(monstersLines[5]),
                    Integer.parseInt(monstersLines[6]), MonsterType.getMonsterTypeByName(monstersLines[3]), Integer.parseInt(monstersLines[8]));
        }

        FileReader fileReaderSpellTrap = new FileReader("SpellTrap.csv");
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
            if (counter == 12) break;
        }
        while ((spellsTrapsLines = csvReaderSpellTrap.readNext()) != null) {
            makeCardSpell(CardType.SPELL, spellsTrapsLines[0], "1", SpellEffect.getSpellByName(spellsTrapsLines[0]),
                    Attribute.SPELL, spellsTrapsLines[3], SpellType.getSpellTypeByTypeName(spellsTrapsLines[2]),
                    spellsTrapsLines[4].equals("Limited"), Integer.parseInt(spellsTrapsLines[5]));
        }
        Monster.jsonMonsters();
        Spell.spellsToJson();
    }
}