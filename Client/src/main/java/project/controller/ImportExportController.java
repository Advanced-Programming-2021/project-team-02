package project.controller;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import project.model.Shop;
import project.model.card.*;
import project.model.card.informationofcards.CardType;
import project.view.Utility;
import project.view.messages.ImportExportMessages;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportExportController {
    private static ImportExportController instance = null;
    Utility utility = new Utility();

    private ImportExportController() {
    }

    public static ImportExportController getInstance() {
        if (instance == null) instance = new ImportExportController();
        return instance;
    }

    public ImportExportMessages importCard(String fileName) {
        try {
            String json = new String(Files.readAllBytes(Path.of(fileName)));
            System.out.println(json);
            Pattern pattern = Pattern.compile(".*?,\"cardType\":\"([A-Za-z]+)\",\"name\":\"(\\w+)\".*");
            Matcher matcher = pattern.matcher(json);
            if (matcher.find()) {
                Gson gson = new Gson();
                if (matcher.group(1).equals("MONSTER")) {
                    Monster card = gson.fromJson(json, Monster.class);
                    CardsDatabase.getAllCards().add((Card) card);
                    utility.getStringImageHashMap().put(card.getName(), new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/Picture.jpg"))));
                    return ImportExportMessages.CARD_IMPORTED;
                } else if (matcher.group(1).equals("SPELL")) {
                    Spell card = gson.fromJson(json, Spell.class);
                    CardsDatabase.getAllCards().add((Card) card);
                    utility.getStringImageHashMap().put(card.getName(), new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/Picture.jpg"))));
                    return ImportExportMessages.CARD_IMPORTED;
                } else if (matcher.group(1).equals("TRAP")) {
                    Trap card = gson.fromJson(json, Trap.class);
                    CardsDatabase.getAllCards().add((Card) card);
                    utility.getStringImageHashMap().put(card.getName(), new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/Picture.jpg"))));
                    return ImportExportMessages.CARD_IMPORTED;
                }
            }
        } catch (IOException e) {
            return ImportExportMessages.CARD_NOT_IMPORTED;
        }
        return ImportExportMessages.CARD_NOT_IMPORTED;
    }


    private ImportExportMessages makeFile(String cardName) {
        try {
            Gson gson = new Gson();
            Writer writer = Files.newBufferedWriter(Paths.get(cardName + ".json"));
            gson.toJson(Card.getCardByName(cardName), writer);
            writer.close();
            return ImportExportMessages.CARD_EXPORTED;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ImportExportMessages.CARD_NOT_EXPORTED;
    }

    public ImportExportMessages exportCard(String cardName) {
        for (Card card : CardsDatabase.getAllCards()) {
            if (card.getName().equals(cardName)) {
                return makeFile(cardName);
            }
        }
        return ImportExportMessages.CARD_NOT_EXPORTED;
    }

    public ImportExportMessages SaveToCSV(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (Objects.requireNonNull(card).getCardType() == CardType.MONSTER) {
            return monsterCSV(cardName);
        } else if (card.getCardType() == CardType.SPELL) {
            return spellCSV(cardName);
        } else if (card.getCardType() == CardType.TRAP) {
            return trapCSV(cardName);
        }
        return ImportExportMessages.CARD_NOT_IMPORTED;
    }

    private ImportExportMessages trapCSV(String cardName) {
        final String COMMA_DELIMITER = ",";
        final String LINE_SEPARATOR = "\n";
        Card card = Card.getCardByName(cardName);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(cardName + ".csv");
            Trap trap = (Trap) Trap.getCardByName(cardName);
            fileWriter.append(String.valueOf(Objects.requireNonNull(trap).getName()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(trap.getTrapType()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(trap.getTrapEffect()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(trap.getIsLimited()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(trap.getDescription());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(Shop.getInstance().getCardsWithPrices().get(cardName)));
            fileWriter.append(LINE_SEPARATOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert fileWriter != null;
            fileWriter.close();
            return ImportExportMessages.CARD_EXPORTED;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ImportExportMessages.CARD_NOT_IMPORTED;
    }

    private ImportExportMessages spellCSV(String cardName) {
        final String COMMA_DELIMITER = ",";
        final String LINE_SEPARATOR = "\n";
        Card card = Card.getCardByName(cardName);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(cardName + ".csv");
            Spell spell = (Spell) Trap.getCardByName(cardName);
            fileWriter.append(String.valueOf(Objects.requireNonNull(spell).getName()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(spell.getSpellType()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(spell.getSpellEffect()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(spell.getIsLimited()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(spell.getDescription());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(Shop.getInstance().getCardsWithPrices().get(cardName)));
            fileWriter.append(LINE_SEPARATOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert fileWriter != null;
            fileWriter.close();
            return ImportExportMessages.CARD_EXPORTED;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ImportExportMessages.CARD_NOT_IMPORTED;
    }

    private ImportExportMessages monsterCSV(String cardName) {
        final String COMMA_DELIMITER = ",";
        final String LINE_SEPARATOR = "\n";
        Card card = Card.getCardByName(cardName);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(cardName + ".csv");
            Monster monster = (Monster) Trap.getCardByName(cardName);
            fileWriter.append(String.valueOf(Objects.requireNonNull(monster).getName()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(monster.getMonsterType()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(monster.getMonsterEffect()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(monster.getLevel()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(monster.getAttackPower()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(monster.getDefensePower()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(monster.getDescription());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(Shop.getInstance().getCardsWithPrices().get(cardName)));
            fileWriter.append(LINE_SEPARATOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert fileWriter != null;
            fileWriter.close();
            return ImportExportMessages.CARD_EXPORTED;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ImportExportMessages.CARD_NOT_IMPORTED;
    }

}
