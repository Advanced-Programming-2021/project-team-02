package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Assets;
import model.User;
import model.card.Card;
import model.game.Duel;
import view.ImportExportMenuView;
import view.MainMenuView;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ImportExportMenuController {
    private static ImportExportMenuController instance = null;
    private final ImportExportMenuView view = ImportExportMenuView.getInstance();

    private ImportExportMenuController() {
    }

    public static ImportExportMenuController getInstance() {
        if (instance == null)
            instance = new ImportExportMenuController();
        return instance;
    }

    public void importCard(String fileName) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(fileName)));
            Card card = new Gson().fromJson(json, new TypeToken<Card>(){}.getType());
            Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).addCard(card);
            view.showSuccessMessage(SuccessMessage.SUCCESS_MESSAGE_FOR_IMPORT);
        } catch (IOException e) {
            view.showError(Error.THERE_IS_NO_SUCH_FILE);
        }
    }

    private void makeFile(String cardName) {
        try {
            Gson gson = new Gson();
            Writer writer = Files.newBufferedWriter(Paths.get(cardName + ".json"));
            gson.toJson(Card.getCardByName(cardName), writer);
            writer.close();
            view.showSuccessMessage(SuccessMessage.SUCCESS_MESSAGE_FOR_EXPORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exportCard(String cardName) {
        HashMap<Card, Integer> allCards = Objects.requireNonNull(Assets.getAssetsByUsername
                (MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllUserCards();
        for (Card card : allCards.keySet()) {
            if (card.getName().equals(cardName)) {
                makeFile(cardName);
                return;
            }
        }
        view.showError(Error.YOU_DO_NOT_HAVE_THIS_CARD);
    }
}
