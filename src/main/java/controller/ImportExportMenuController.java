package controller;

import com.google.gson.Gson;
import model.Assets;
import model.User;
import model.card.Card;
import model.game.Duel;
import view.ImportExportMenuView;
import view.MainMenuView;
import view.messages.Error;

import javax.jws.soap.SOAPBinding;
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

    public void importCard(String cardName) {

    }

    private void makeFile(String cardName) {
        try {
            FileWriter fileWriter = new FileWriter(cardName);
            Gson gson = new Gson();
            Writer writer = Files.newBufferedWriter(Paths.get(cardName));
            gson.toJson(Card.getCardByName(cardName), writer);
            writer.close();
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
