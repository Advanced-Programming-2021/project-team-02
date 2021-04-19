package controller;

import model.Assets;
import model.Deck;
import model.User;
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.card.informationofcards.CardType;
import view.DeckMenuView;
import view.MenusManager;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.*;
import java.util.regex.Matcher;

public class DeckMenuController {
    int q = 0;
    private User loggedIInUser;
    private static final DeckMenuController instance;
    private static final DeckMenuView view = DeckMenuView.getInstance();

    static {
        instance = new DeckMenuController();
    }

    public static DeckMenuController getInstance() {
        return instance;
    }

    private void setLoggedIInUser(User loggedIInUser) {
        this.loggedIInUser = loggedIInUser;
    }

    public void createDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        if (doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_EXIST, matcher);
            return;
        }
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        assert assets != null;
        view.showSuccessMessage(SuccessMessage.DECK_CREATED);
        assets.createDeck(deckName);
    }

    public void deleteDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        }
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        assert assets != null;
        view.showSuccessMessage(SuccessMessage.DECK_DELETED);
        assets.deleteDeck(deckName);
    }

    public void activateDeck(Matcher matcher) {
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        assert assets != null;
        String deckName = matcher.group("deckName");
        if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        }
        assets.activateDeck(deckName);
        view.showSuccessMessage(SuccessMessage.DECK_ACTIVATED);
    }

    public void addCardToMainDeck(Matcher matcher) {
        String cardName = matcher.group("cardName");
        String deckName = matcher.group("deckName");
        if (!isValidDeckToAddCard(matcher, cardName, deckName)) return;
        Deck deck = Objects.requireNonNull(Assets.getAssetsByUsername(MenusManager.getInstance()
                .getLoggedInUser().getUsername())).getDeckByDeckName(deckName);
        if (deck.isMainFull()) {
            view.showError(Error.MAIN_DECK_IS_FULL);
            return;
        }
        Card card = Card.getCardByName(cardName);
        if (Objects.requireNonNull(card).getCardType().equals(CardType.MONSTER)) {
            addMonsterToMainDeck((Monster) card, deck, matcher);
        } else if (card.getCardType().equals(CardType.SPELL)) {
            addSpellToMainDeck((Spell) card, deck, matcher);
        } else {
            addTrapToMainDeck((Trap) card, deck, matcher);
        }
    }

    public void addCardToSideDeck(Matcher matcher) {
        String cardName = matcher.group("cardName");
        String deckName = matcher.group("deckName");
        if (!isValidDeckToAddCard(matcher, cardName, deckName)) return;
        Deck deck = Objects.requireNonNull(Assets.getAssetsByUsername(MenusManager.getInstance()
                .getLoggedInUser().getUsername())).getDeckByDeckName(deckName);
        if (deck.isSideFull()) {
            view.showError(Error.MAIN_DECK_IS_FULL);
            return;
        }
        Card card = Card.getCardByName(cardName);
        if (Objects.requireNonNull(card).getCardType().equals(CardType.MONSTER)) {
            addMonsterToSideDeck((Monster) card, deck, matcher);
        } else if (card.getCardType().equals(CardType.SPELL)) {
            addSpellToSideDeck((Spell) card, deck, matcher);
        } else {
            addTrapToSideDeck((Trap) card, deck, matcher);
        }
    }

    private boolean isValidDeckToAddCard(Matcher matcher, String cardName, String deckName) {
        if (!doesCardExist(cardName)) {
            view.showDynamicError(Error.INCORRECT_CARD_NAME, matcher);
            return false;
        } else if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return false;
        }
        return true;
    }

    private void addMonsterToMainDeck(Monster monster, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, false, deck.getNumberOfCardInMainDeck(monster)))
            return;
        deck.addCardToMainDeck(monster);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addSpellToMainDeck(Spell spell, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, spell.getIsLimited(), deck.getNumberOfCardInMainDeck(spell))) return;
        deck.addCardToMainDeck(spell);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addTrapToMainDeck(Trap trap, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, trap.getIsLimited(), deck.getNumberOfCardInMainDeck(trap))) return;
        deck.addCardToMainDeck(trap);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addMonsterToSideDeck(Monster monster, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, false, deck.getNumberOfCardInMainDeck(monster)))
            return;
        deck.addCardToSideDeck(monster);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addSpellToSideDeck(Spell spell, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, spell.getIsLimited(), deck.getNumberOfCardInMainDeck(spell))) return;
        deck.addCardToSideDeck(spell);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addTrapToSideDeck(Trap trap, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, trap.getIsLimited(), deck.getNumberOfCardInMainDeck(trap))) return;
        deck.addCardToSideDeck(trap);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private boolean isValidNumberOfCardInDeck(Matcher matcher, boolean isLimited, int numberOfCardInMainDeck) {
        if (isLimited) {
            if (numberOfCardInMainDeck == 1) {
                view.showDynamicError(Error.CARD_LIMITED_IN_DECK, matcher);
                return false;
            }
        } else {
            if (numberOfCardInMainDeck == 3) {
                view.showDynamicError(Error.EXCESSIVE_NUMBER_IN_DECK, matcher);
                return false;
            }
        }
        return true;
    }

    public void showAllDecks(Matcher matcher) {
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        ArrayList<Deck> decks = assets.getAllDecks();
        Comparator<Deck> comparator = Comparator.comparing(Deck::isActivated).thenComparing(Deck::getName);
        decks.sort(comparator);
        System.out.println("Decks:\n" +
                "Active deck:");
        if (decks.size() > 0) {
            for (int i = 0; i < decks.size(); i++) {
                Deck deck = decks.get(i);
                if (i == 0) {
                    if (deck.isActivated()) {
                        System.out.println(deck.getName() + ":" + deck.getMainCards().size() +
                                ",side deck:" + deck.getSideCards().size() + "," + (deck.isValidDeck() ? "Valid" : "Invalid"));
                    }
                    System.out.println("Other decks:");
                    if (!deck.isActivated()) {
                        System.out.println(deck.getName() + ":" + deck.getMainCards().size() +
                                ",side deck:" + deck.getSideCards().size() + "," + (deck.isValidDeck() ? "Valid" : "Invalid"));
                    }
                }
            }
        } else {
            System.out.println("Other decks:");
        }


    }

    public void showDeck(Matcher matcher) {

    }

    public void showAllCards(Matcher matcher) {

    }

    private boolean doesDeckExist(String deckName) {
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        return assets.getDeckByDeckName(deckName) != null;
    }

    private boolean doesCardExist(String cardName) {
        return Card.getCardByName(cardName) != null;
    }
}
