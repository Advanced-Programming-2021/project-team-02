package model.card.informationofcards;

public enum TrapType {
    NORMAL("Normal"),
    CONTINUOUS("Continuous"),
    COUNTER("Counter");
    private static final TrapType[] allTrapTypes = new TrapType[]{
            NORMAL,CONTINUOUS,COUNTER
    };
    private String label;
    TrapType(String label){
        this.label = label;
    }
    public static TrapType getTrapTypeByTypeName(String name){
        for (TrapType trapType : allTrapTypes) {
            if (trapType.label.equals(name))
                return trapType;
        }
        return null;
    }

    public String getLabel() {
        return label;
    }
}
