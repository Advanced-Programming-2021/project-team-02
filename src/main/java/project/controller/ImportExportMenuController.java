package project.controller;

import com.google.gson.Gson;
import project.model.Assets;
import project.model.card.Card;
import project.model.card.Monster;
import project.model.card.Spell;
import project.model.card.Trap;
import project.model.card.informationofcards.CardType;
import project.view.ImportExportMenuView;
import project.view.messages.Error;
import project.view.messages.SuccessMessage;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            String json = new String(Files.readAllBytes(Paths.get(fileName + ".json")));
            Gson gson = new Gson();
            if (Objects.requireNonNull(Card.getCardByName(fileName)).getCardType().equals(CardType.MONSTER)) {
                Monster card = gson.fromJson(json, Monster.class);
                Objects.requireNonNull(Assets.getAssetsByUsername
                        (MainMenuController.getInstance().getLoggedInUser().getUsername())).addCard(card);
            } else if (Objects.requireNonNull(Card.getCardByName(fileName)).getCardType().equals(CardType.SPELL)) {
                Spell card = gson.fromJson(json, Spell.class);
                Objects.requireNonNull(Assets.getAssetsByUsername
                        (MainMenuController.getInstance().getLoggedInUser().getUsername())).addCard(card);
            } else if (Objects.requireNonNull(Card.getCardByName(fileName)).getCardType().equals(CardType.TRAP)) {
                Trap card = gson.fromJson(json, Trap.class);
                Objects.requireNonNull(Assets.getAssetsByUsername
                        (MainMenuController.getInstance().getLoggedInUser().getUsername())).addCard(card);
            }
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
