package project.controller;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import project.Main;
import project.model.Assets;
import project.model.Deck;
import project.model.card.Card;
import project.model.card.Monster;
import project.model.card.Spell;
import project.model.card.Trap;
import project.model.card.informationofcards.CardType;
import project.view.DeckMenuView;
import project.view.EditDeckView;
import project.view.messages.DeckMenuMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;

public class DeckMenuController {
    private static DeckMenuController instance = null;
    private Label openedDeck;
    private Button openedDeckButton;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private DeckMenuView firstView;
    private EditDeckView editDeckView;
    private boolean isEdit;
    private Socket receiverSocket;
    private DataOutputStream receiverOutput;
    private DataInputStream receiverInput;

    private DeckMenuController() {
    }

    public static DeckMenuController getInstance() {
        if (instance == null)
            instance = new DeckMenuController();
        return instance;
    }

    public void setIsEdit(boolean edit) {
        isEdit = edit;
    }

    public void setEditDeckView(EditDeckView editDeckView) {
        this.editDeckView = editDeckView;
    }

    public void setFirstView(DeckMenuView firstView) {
        this.firstView = firstView;
    }

    public void initializeNetwork() {
        try {
            //receiverSocket = new Socket("localhost", 8000);
            receiverSocket = new Socket("2.tcp.ngrok.io", 18536);
            receiverOutput = new DataOutputStream(receiverSocket.getOutputStream());
            receiverInput = new DataInputStream(receiverSocket.getInputStream());
            receiverOutput.writeUTF("data_transfer_deck " + MainMenuController.getInstance().getLoggedInUserToken());
            receiverOutput.flush();
            startReceiverThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReceiverThread() {
        new Thread(() ->
        {
            try {
                while (true) {
                    String in = receiverInput.readUTF();
                    if (in.equals("close"))
                        break;
                    System.out.println("received assets : " + in);
                    Assets assets = new Gson().fromJson(in, Assets.class);
                    MainMenuController.getInstance().updateLoggedInAsset(assets);
                    //TODO
                    if (isEdit)
                        Platform.runLater(editDeckView::initialize);
                    else Platform.runLater(firstView::showDecks);
                }
                receiverOutput.close();
                receiverInput.close();
                receiverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public DeckMenuMessage createDeck(String deckName) {
        if (doesDeckExist(deckName)) {
            return DeckMenuMessage.DECK_ALREADY_EXIST;
        }
//        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
//        Objects.requireNonNull(assets).createDeck(deckName);
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        String result;
        try {
            dataOutputStream.writeUTF("deck create <" + deckName + "> " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
            switch (result) {
                case "success":
                    return DeckMenuMessage.DECK_ADDED;
                case "exists":
                    return DeckMenuMessage.DECK_ALREADY_EXIST;
                case "failed":
                    return DeckMenuMessage.ERROR_OCCURRED;
            }
        } catch (IOException e) {
            return DeckMenuMessage.ERROR_OCCURRED;
        }
        return DeckMenuMessage.ERROR_OCCURRED;

    }

    public DeckMenuMessage deleteDeck(String deckName) {
        if (!doesDeckExist(deckName)) {
            return DeckMenuMessage.DECK_DOES_NOT_EXIST;
        }
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        String result;
        try {
            dataOutputStream.writeUTF("deck delete <" + deckName + "> " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
            switch (result) {
                case "success":
                    return DeckMenuMessage.DECK_DELETED;
                case "not_exists":
                    return DeckMenuMessage.DECK_DOES_NOT_EXIST;
                case "failed":
                    return DeckMenuMessage.ERROR_OCCURRED;
            }
        } catch (IOException e) {
            return DeckMenuMessage.ERROR_OCCURRED;
        }
//        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
//        Objects.requireNonNull(assets).deleteDeck(deckName);
        return DeckMenuMessage.ERROR_OCCURRED;
    }

    public DeckMenuMessage activateDeck(String deckName) {
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        if (!doesDeckExist(deckName)) {
            return DeckMenuMessage.DECK_DOES_NOT_EXIST;
        }
        //   Objects.requireNonNull(assets).activateDeck(deckName);
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        String result;
        try {
            dataOutputStream.writeUTF("deck activate <" + deckName + "> " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
            switch (result) {
                case "success":
                    return DeckMenuMessage.DECK_ACTIVATED;
                case "failed":
                    return DeckMenuMessage.ERROR_OCCURRED;
            }
        } catch (IOException e) {
            return DeckMenuMessage.ERROR_OCCURRED;
        }
        return DeckMenuMessage.ERROR_OCCURRED;
    }

    public DeckMenuMessage addCardToMainDeck(String deckName, String cardName) {
        if (!isValidDeckToAddCard(cardName, deckName)) return DeckMenuMessage.CARD_DOES_NOT_EXIST;
        Deck deck = Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getDeckByDeckName(deckName);
        if (deck.isMainFull()) {
            return DeckMenuMessage.DECK_FULL;
        }
        Card card = Card.getCardByName(cardName);
        if (Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getNumberOfCards(card) == deck.getNumberOfCardInDeck(card)) {
            return DeckMenuMessage.ENOUGH;
        }

        if (Objects.requireNonNull(card).getCardType().equals(CardType.MONSTER))
            return addMonsterToMainDeck((Monster) card, deck, deckName, cardName);
        else if (card.getCardType().equals(CardType.SPELL))
            return addSpellToMainDeck((Spell) card, deck, deckName, cardName);
        else
            return addTrapToMainDeck((Trap) card, deck, deckName, cardName);
    }

    public DeckMenuMessage addCardToSideDeck(String deckName, String cardName) {
        if (!isValidDeckToAddCard(cardName, deckName)) return DeckMenuMessage.CARD_DOES_NOT_EXIST;
        Deck deck = Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getDeckByDeckName(deckName);
        if (Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getDeckByDeckName(deckName).isSideFull()) {
            return DeckMenuMessage.DECK_FULL;
        }
        Card card = Card.getCardByName(cardName);
        if (Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getNumberOfCards(card) == deck.getNumberOfCardInDeck(card)) {
            return DeckMenuMessage.MAXIMUM;
        }
        if (Objects.requireNonNull(card).getCardType().equals(CardType.MONSTER)) {
            return addMonsterToSideDeck((Monster) card, deck, deckName, cardName);
        } else if (card.getCardType().equals(CardType.SPELL)) {
            return addSpellToSideDeck((Spell) card, deck, deckName, cardName);
        } else {
            return addTrapToSideDeck((Trap) card, deck, deckName, cardName);
        }
    }

    private boolean isValidDeckToAddCard(String cardName, String deckName) {
        if (!doesCardExistInUserCards(cardName)) return false;
        else return doesDeckExist(deckName);
    }

    private DeckMenuMessage addMonsterToMainDeck(Monster monster, Deck deck, String deckName, String cardName) {
        if (!isValidNumberOfCardInDeck(deckName, cardName, false, deck.getNumberOfCardInDeck(monster)))
            return DeckMenuMessage.MAXIMUM;
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        Objects.requireNonNull(assets).addCardToMainDeck(monster, deck);
        return DeckMenuMessage.CARD_ADDED_TO_MAIN;
    }

    private DeckMenuMessage addSpellToMainDeck(Spell spell, Deck deck, String deckName, String cardName) {
        if (!isValidNumberOfCardInDeck(deckName, cardName, spell.getIsLimited(), deck.getNumberOfCardInDeck(spell)))
            return DeckMenuMessage.MAXIMUM;
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        Objects.requireNonNull(assets).addCardToMainDeck(spell, deck);
        return DeckMenuMessage.CARD_ADDED_TO_MAIN;
    }

    private DeckMenuMessage addTrapToMainDeck(Trap trap, Deck deck, String deckName, String cardName) {
        if (!isValidNumberOfCardInDeck(deckName, cardName, trap.getIsLimited(), deck.getNumberOfCardInDeck(trap)))
            return DeckMenuMessage.MAXIMUM;
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        Objects.requireNonNull(assets).addCardToMainDeck(trap, deck);
        return DeckMenuMessage.CARD_ADDED_TO_MAIN;
    }

    private DeckMenuMessage addMonsterToSideDeck(Monster monster, Deck deck, String deckName, String cardName) {
        if (!isValidNumberOfCardInDeck(deckName, cardName, false, deck.getNumberOfCardInDeck(monster)))
            return DeckMenuMessage.MAXIMUM;
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        Objects.requireNonNull(assets).addCardToSideDeck(monster, deck);
        return DeckMenuMessage.CARD_ADDED_TO_SIDE;
    }

    private DeckMenuMessage addSpellToSideDeck(Spell spell, Deck deck, String deckName, String cardName) {
        if (!isValidNumberOfCardInDeck(deckName, cardName, spell.getIsLimited(), deck.getNumberOfCardInDeck(spell)))
            return DeckMenuMessage.MAXIMUM;
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        Objects.requireNonNull(assets).addCardToSideDeck(spell, deck);
        return DeckMenuMessage.CARD_ADDED_TO_SIDE;
    }

    private DeckMenuMessage addTrapToSideDeck(Trap trap, Deck deck, String deckName, String cardName) {
        if (!isValidNumberOfCardInDeck(deckName, cardName, trap.getIsLimited(), deck.getNumberOfCardInDeck(trap)))
            return DeckMenuMessage.MAXIMUM;
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        Objects.requireNonNull(assets).addCardToSideDeck(trap, deck);
        return DeckMenuMessage.CARD_ADDED_TO_SIDE;
    }

    private boolean isValidNumberOfCardInDeck(String deckName, String cardName, boolean isLimited, int numberOfCardInDeck) {
        if (isLimited) {
            return numberOfCardInDeck != 1;
        } else return numberOfCardInDeck != 3;
    }

    public DeckMenuMessage removeCardFromSideDeck(String deckName, String cardName) {
        Deck deck;
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        //TODO
        return null;
    }

    public DeckMenuMessage removeCardFromMainDeck(String deckName, String cardName) {
        Deck deck;
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        //TODO
        return null;
    }

    private boolean doesDeckExist(String deckName) {
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        return Objects.requireNonNull(assets).getDeckByDeckName(deckName) != null;
    }

    private boolean doesCardExistInUserCards(String cardName) {
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        HashMap<String, Integer> userCards = Objects.requireNonNull(assets).getAllUserCards();
        for (String card : userCards.keySet()) {
            if (card.equalsIgnoreCase(cardName))
                if (userCards.get(card) > 0)
                    return true;
        }
        return false;
    }

    public Label getOpenedDeck() {
        return openedDeck;
    }

    public void setOpenedDeck(Label openedDeck) {
        this.openedDeck = openedDeck;
    }

    public Button getOpenedDeckButton() {
        return openedDeckButton;
    }

    public void setOpenedDeckButton(Button openedDeckButton) {
        this.openedDeckButton = openedDeckButton;
    }
}