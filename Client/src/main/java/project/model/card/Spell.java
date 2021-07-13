package project.model.card;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import project.model.card.informationofcards.Attribute;
import project.model.card.informationofcards.CardType;
import project.model.card.informationofcards.SpellEffect;
import project.model.card.informationofcards.SpellType;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    public static SpellType spellType(String cardName) {
        for (Spell spell : allSpells) {
            if (spell.getName().equals(cardName)) return spell.getSpellType();
        }
        return null;
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

    public static void fromJson() {
        try {
            String gson = new String(Files.readAllBytes(Paths.get("spell.json")));
            allSpells = new Gson().fromJson(gson, new TypeToken<List<Spell>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}