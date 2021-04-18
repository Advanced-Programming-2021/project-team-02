package model.card.informationofcards;

public enum MonsterType {
    BEAST("Beast"),
    PYRO("Pyto"),
    FIEND("Fiend"),
    AQUA("Aqua"),
    ROCK("Rick"),
    INSECT("Insect"),
    MACHINE("Machine"),
    FAIRY("Fairy"),
    BEAST_WARRIOR("Beast-Warrior"),
    CYBERSE("Cyberse"),
    THUNDER("Thunder"),
    DRAGON("Dragon"),
    WARRIOR("Warrior"),
    SPELLCASTER("Spellcaster"),
    SEA_SERPENT("Sea Serpent");
    private final String label;
    private static final MonsterType[] allMonsterTypes = new MonsterType[]{
            BEAST,
            PYRO,
            FIEND,
            AQUA,
            ROCK,
            INSECT,
            MACHINE,
            FAIRY,
            BEAST_WARRIOR,
            CYBERSE,
            THUNDER,
            DRAGON,
            WARRIOR,
            SPELLCASTER,
            SEA_SERPENT
    };

    MonsterType(String label) {
        this.label = label;
    }

    public  static MonsterType getMonsterTypeByName(String typeName) {
        for (MonsterType monsterType : allMonsterTypes) {
            if (monsterType.label.equals(typeName))
                return monsterType;
        }
        return null;
    }

    public String getLabel() {
        return label;
    }
}
