package model.card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Shop;
import model.card.informationofcards.*;

import java.io.*;
import java.util.ArrayList;

public class CardsDatabase {
    private static CardsDatabase dataBase;
    private static final ArrayList<Card> allCards;
    private static final ArrayList<Spell> allSpells;
    private static final ArrayList<Trap> allTraps;


    static {
        allCards = new ArrayList<>();
        allSpells = new ArrayList<>();
        allTraps = new ArrayList<>();
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
        Monster monster = new Monster(cardType, name, id, monsterActionType, monsterEffect, level, attribute, description, attackPower, defensePower, monsterType);
        allCards.add(card);
        Shop.getInstance().addCardToShop(card, price);
    }

    public static void makeCardSpell(CardType cardType, String name, String id, SpellEffect spellEffect,
                                     Attribute attribute, String description, SpellType spellType, boolean isLimited, int price) {
        Card card = new Spell(cardType, name, id, spellEffect, attribute, description, spellType, isLimited);
        Spell spell = new Spell(cardType, name, id, spellEffect, attribute, description, spellType, isLimited);
        allSpells.add(spell);
        allCards.add(card);
        Shop.getInstance().addCardToShop(card, price);
    }

    public static void makeTrapCard(CardType cardType, String name, String id, TrapEffect trapEffect,
                                    Attribute attribute, String description, TrapType trapType, boolean isLimited, int price) {
        Card card = new Trap(cardType, name, id, trapEffect, attribute, description, trapType, isLimited);
        Trap trap = new Trap(cardType, name, id, trapEffect, attribute, description, trapType, isLimited);
        allTraps.add(trap);
        allCards.add(card);
        Shop.getInstance().addCardToShop(card, price);
    }


    public void readAndMakeCards() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("Monster.csv"));
        csvReader.readLine();
        String row;
        int counter = 2;
        while ((row = csvReader.readLine()) != null) {
            row = row.replaceAll("\"", "");
            if (counter == 38)
                row += "\n" + csvReader.readLine().replaceAll("\"", "");
            String[] data = row.split("[,]+(?=\\S|$)");
            makeCardMonster(CardType.MONSTER, data[0], "1", MonsterActionType.getActionTypeByName(data[4]),
                    MonsterEffect.getMonsterEffectByName(data[0]), Integer.parseInt(data[1]),
                    Attribute.getAttributeByName(data[2]), data[7], Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]), MonsterType.getMonsterTypeByName(data[3]), Integer.parseInt(data[8]));
            counter++;
        }
        csvReader = new BufferedReader(new FileReader("SpellTrap.csv"));
        csvReader.readLine();
        counter = 1;
        while (counter != 12) {
            row = csvReader.readLine();
            row = row.replaceAll("\"", "");
            String[] data = row.split("[,]+(?=\\S|$)");
            makeTrapCard(CardType.TRAP, data[0], "1", TrapEffect.getTrapEffectByName(data[0]), Attribute.TRAP, data[3],
                    TrapType.getTrapTypeByTypeName(data[2]), data[4].equals("Limited"), Integer.parseInt(data[5]));
            counter++;
        }
        counter++;
        while ((row = csvReader.readLine()) != null) {
            row = row.replaceAll("\"", "");
            if (counter == 35)
                row += "\n" + csvReader.readLine().replaceAll("\"", "") + "\n" + csvReader.readLine().replaceAll("\"", "");
            String[] data = row.split("[,]+(?=\\S|$)");
            makeCardSpell(CardType.SPELL, data[0], "1", SpellEffect.getSpellByName(data[0]), Attribute.SPELL, data[3],
                    SpellType.getSpellTypeByTypeName(data[2]), data[4].equals("Limited"), Integer.parseInt(data[5]));
            counter++;
        }
        csvReader.close();
        Monster.jsonMonsters();
        Spell.spellsToJson();
    }
}