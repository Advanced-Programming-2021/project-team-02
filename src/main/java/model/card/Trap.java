package model.card;

import model.card.informationofcards.*;

import java.util.ArrayList;

public class Trap extends Card {
    private static ArrayList<Trap> allTraps = new ArrayList<>();
    private TrapType trapType;
    private TrapEffect trapEffect;
    private boolean isLimited;

    public Trap(CardType cardType, String name,String id, TrapEffect trapEffect,
                Attribute attribute, String description, TrapType trapType, boolean isLimited) {
        super(cardType, name,id,  attribute, description);
        setTrapType(trapType);
        setTrapEffect(trapEffect);
        setLimited(isLimited);
        allTraps.add(this);
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

    public TrapEffect getTrapEffect() {
        return trapEffect;
    }

    @Override
    public String toString() {
        return "Name: " + this.name +
                "\nTrap" +
                "\nType :" + this.trapType +
                "\nDescription: " + this.description;
    }
    public boolean getIsLimited(){
        return isLimited;
    }
    public Trap clone() throws CloneNotSupportedException {
        return new Trap( cardType,  name, id,  trapEffect,
                 attribute,  description,  trapType,  isLimited);
    }
}