package model.card;

import model.card.informationofcards.*;

public class Trap extends Card {
    private TrapType trapType;
    private TrapEffect trapEffect;
    private boolean isLimited;

    public Trap(CardType cardType, String name,String id, TrapEffect trapEffect,
                Attribute attribute, String description, TrapType trapType, boolean isLimited) {
        super(cardType, name,id,  attribute, description);
        setTrapType(trapType);
        setTrapEffect(trapEffect);
        setLimited(isLimited);
    }

    private void setLimited(boolean limited) {
        isLimited = limited;
    }

    private void setTrapType(TrapType trapType) {
        this.trapType = trapType;
    }

    private void setTrapEffect(TrapEffect trapEffect) {
        this.trapEffect = trapEffect;
    }

    @Override
    public String toString() {
        return "Trap{" +
                "cardType=" + cardType +
                ", name='" + name + '\'' +
                ", attribute=" + attribute +
                ", description='" + description + '\'' +
                ", trapType=" + trapType +
                ", trapEffect=" + trapEffect +
                '}';
    }
    public boolean getIsLimited(){
        return isLimited;
    }
}