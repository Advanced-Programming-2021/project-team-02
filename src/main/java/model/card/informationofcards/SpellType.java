package model.card.informationofcards;

public enum SpellType {
    CONTINUOUS("Continuous"),
    EQUIP("Equip"),
    FIELD("Field"),
    QUICK_PLAY("Quick-play"),
    COUNTER("Counter"),
    RITUAL("Ritual"),
    NORMAL("Normal");
    private static final SpellType[] allSpellTypes = new SpellType[]{
            CONTINUOUS,
            EQUIP,
            FIELD,
            QUICK_PLAY,
            COUNTER,
            RITUAL,
            NORMAL
    };
    private final String label;

    SpellType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static SpellType getSpellTypeByTypeName(String typeName) {
        for (SpellType spellType : allSpellTypes) {
            if (spellType.label.equals(typeName))
                return spellType;
        }
        return null;
    }
}
