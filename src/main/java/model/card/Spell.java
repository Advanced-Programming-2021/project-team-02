package model.card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.card.informationofcards.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Spell extends Card {

    private static ArrayList<Spell> allSpells = new ArrayList<>();
    private SpellType spellType;
    private SpellEffect spellEffect;
    private boolean isLimited;

    public Spell(CardType cardType, String name, String id, SpellEffect spellEffect, Attribute attribute,
                 String description, SpellType spellType, boolean isLimited) {
        super(cardType, name, id, attribute, description);
        setSpellType(spellType);
        setSpellEffect(spellEffect);
        setLimited(isLimited);
        allSpells.add(this);
    }

    private void setLimited(boolean limited) {
        isLimited = limited;
    }

    private void setSpellType(SpellType spellType) {
        this.spellType = spellType;
    }

    private void setSpellEffect(SpellEffect spellEffect) {
        this.spellEffect = spellEffect;
    }

    @Override
    public String toString() {
        return "Name: " + this.name +
                "\nSpell" +
                "\nType :" + this.spellType +
                "\nDescription: " + this.description;
    }

    public static void spellsToJson() {
        try {
            FileWriter fileWriter = new FileWriter("spell.json");
            fileWriter.write(new Gson().toJson(allSpells));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getIsLimited() {
        return isLimited;
    }

    public SpellEffect getSpellEffect() {
        return spellEffect;
    }

    public SpellType getSpellType() {
        return spellType;
    }

    public Spell clone() throws CloneNotSupportedException {
        return new Spell(cardType, name, id, spellEffect, attribute,
                description, spellType, isLimited);
    }

    public static ArrayList<Spell> getAllSpells() {
        return allSpells;
    }
}