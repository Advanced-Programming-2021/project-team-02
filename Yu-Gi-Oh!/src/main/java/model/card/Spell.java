package model.card;

import model.card.informationofcards.*;

public class Spell extends Card {
    private SpellType spellType;
    private SpellEffect spellEffect;
    private boolean isLimited;

    public Spell(CardType cardType, String name,String id, SpellEffect spellEffect, Attribute attribute,
                 String description,SpellType spellType, boolean isLimited) {
        super(cardType, name,id,  attribute, description);
        setSpellType(spellType);
        setSpellEffect(spellEffect);
        setLimited(isLimited);
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
        return "Spell{" +
                "cardType=" + cardType +
                ", name='" + name + '\'' +
                ", attribute=" + attribute +
                ", description='" + description + '\'' +
                ", spellType=" + spellType +
                ", spellEffect=" + spellEffect +
                '}';
    }
}