package project.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import project.model.Assets;
import project.model.Deck;
import project.model.card.Card;
import project.model.card.Monster;
import project.model.card.Spell;
import project.model.card.Trap;
import project.model.card.informationofcards.CardType;
import project.view.messages.DeckMenuMessage;

import java.util.HashMap;
import java.util.Objects;

public class DeckMenuController {
    private static DeckMenuController instance = null;
    private Label openedDeck;
    private Button openedDeckButton;

    private DeckMenuController() {
    }

    public static DeckMenuController getInstance() {
        if (instance == null)
            instance = new DeckMenuController();
        return instance;
    }

    public DeckMenuMessage createDeck(String deckName) {
        if (doesDeckExist(deckName)) {
            return DeckMenuMessage.DECK_ALREADY_EXIST;
        }
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        Objects.requireNonNull(assets).createDeck(deckName);
        return DeckMenuMessage.DECK_ADDED;

    }

    public DeckMenuMessage deleteDeck(String deckName) {
        if (!doesDeckExist(deckName)) {
            return DeckMenuMessage.DECK_DOES_NOT_EXIST;
        }
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        Objects.requireNonNull(assets).deleteDeck(deckName);
        return DeckMenuMessage.DECK_DELETED;
    }

    public DeckMenuMessage activateDeck(String deckName) {
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        if (!doesDeckExist(deckName)) {
            return DeckMenuMessage.DECK_DOES_NOT_EXIST;
        }
        Objects.requireNonNull(assets).activateDeck(deckName);
        return DeckMenuMessage.DECK_ACTIVATED;
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