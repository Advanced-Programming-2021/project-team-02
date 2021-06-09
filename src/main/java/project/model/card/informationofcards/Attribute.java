package project.model.card.informationofcards;

public enum Attribute {
    WATER("WATER"),
    FIRE("FIRE"),
    EARTH("EARTH"),
    DARK("DARK"),
    LIGHT("LIGHT"),
    WIND("WIND"),
    SPELL("SPELL"),
    TRAP("TRAP");
    private final String label;
    private static final Attribute[] allAttributes = new Attribute[]{
            WATER,
            FIRE,
            EARTH,
            DARK,
            LIGHT,
            WIND,
            SPELL,
            TRAP
    };

    Attribute(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Attribute getAttributeByName(String attributeName) {
        for (Attribute attribute : allAttributes) {
            if (attribute.label.equals(attributeName))
                return attribute;
        }
        return null;
    }
}
