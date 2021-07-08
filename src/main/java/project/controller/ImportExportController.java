package project.controller;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import project.model.Assets;
import project.model.card.*;
import project.model.card.informationofcards.CardType;
import project.view.Utility;
import project.view.messages.ImportExportMessages;

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
}
