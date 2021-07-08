package project.controller;

import com.google.gson.Gson;
import project.model.Assets;
import project.model.card.Card;
import project.model.card.CardsDatabase;
import project.view.messages.ImportExportMessages;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class ImportExportController {
    private static ImportExportController instance = null;

    private ImportExportController() {
    }

    public static ImportExportController getInstance() {
        if (instance == null) instance = new ImportExportController();
        return instance;
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
