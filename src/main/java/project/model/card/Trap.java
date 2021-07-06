package project.model.card;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import project.model.card.informationofcards.Attribute;
import project.model.card.informationofcards.CardType;
import project.model.card.informationofcards.TrapEffect;
import project.model.card.informationofcards.TrapType;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Trap extends Card {

    private static ArrayList<Trap> allTraps = new ArrayList<>();

    public TrapType getTrapType() {
        return trapType;
    }

    private TrapType trapType;
    private TrapEffect trapEffect;
    private boolean isLimited;

    public Trap(CardType cardType, String name, String id, TrapEffect trapEffect,
                Attribute attribute, String description, TrapType trapType, boolean isLimited) {
        super(cardType, name,id,  attribute, description);
        setTrapType(trapType);
        setTrapEffect(trapEffect);
        setLimited(isLimited);
        allTraps.add(this);
    }

    public static TrapType trapType(String cardName) {
        for (Trap trap : allTraps) {
            if (trap.getName().equals(cardName)) return trap.getTrapType();
        }
        return null;
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

    public static void trapToJson() {
        try {
            FileWriter fileWriter = new FileWriter("trap.json");
            fileWriter.write(new Gson().toJson(allTraps));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static ArrayList<Trap> getAllTraps() {
        return allTraps;
    }

    public static void fromJson() {
        try {
            String gson = new String(Files.readAllBytes(Paths.get("trap.json")));
            allTraps = new Gson().fromJson(gson, new TypeToken<List<Trap>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}