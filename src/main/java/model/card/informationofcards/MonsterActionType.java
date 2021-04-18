package model.card.informationofcards;

public enum MonsterActionType {
    NORMAL("Normal"),
    RITUAL("Ritual"),
    EFFECT("Effect");
    private final String label;
    private static final MonsterActionType[] actionTypes = new MonsterActionType[]{
            NORMAL,
            RITUAL,
            EFFECT
    };

    MonsterActionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static MonsterActionType getActionTypeByName(String name) {
        for (MonsterActionType actionType : actionTypes) {
            if (actionType.label.equals(name))
                return actionType;
        }
        return null;
    }
}
