package controller;

import model.Assets;
import model.Deck;
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
    private static  DeckMenuController instance = null;
    private  final DeckMenuView view = DeckMenuView.getInstance();

    private DeckMenuController(){

    }

    public static DeckMenuController getInstance() {
        if (instance == null)
            instance = new DeckMenuController();
        return instance;
    }

    public void createDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        if (doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_EXIST, matcher);
            return;
        }
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        view.showSuccessMessage(SuccessMessage.DECK_CREATED);
        Objects.requireNonNull(assets).createDeck(deckName);
    }

    public void deleteDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        }
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        view.showSuccessMessage(SuccessMessage.DECK_DELETED);
        Objects.requireNonNull(assets).deleteDeck(deckName);
    }

    public void activateDeck(Matcher matcher) {
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        String deckName = matcher.group("deckName");
        if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        }
        Objects.requireNonNull(assets).activateDeck(deckName);
        view.showSuccessMessage(SuccessMessage.DECK_ACTIVATED);
    }

    public void addCardToMainDeck(Matcher matcher) {
        String cardName = matcher.group("cardName");
        String deckName = matcher.group("deckName");
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance()
                .getLoggedInUser().getUsername());
        if (!isValidDeckToAddCard(matcher, cardName, deckName)) return;
        Deck deck = Objects.requireNonNull(assets).getDeckByDeckName(deckName);
        if (deck.isMainFull()) {
            view.showError(Error.MAIN_DECK_IS_FULL);
            return;
        }
        Card card = Card.getCardByName(cardName);
        if (assets.getNumberOfCards(card) == deck.getNumberOfCardInDeck(card)) {
            view.showError(Error.DONT_HAVE_ENOUGH_OF_THIS_CARD);
            return;
        }

        if (Objects.requireNonNull(card).getCardType().equals(CardType.MONSTER))
            addMonsterToMainDeck((Monster) card, deck, matcher);
        else if (card.getCardType().equals(CardType.SPELL))
            addSpellToMainDeck((Spell) card, deck, matcher);
        else
            addTrapToMainDeck((Trap) card, deck, matcher);
    }

    public void addCardToSideDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        String cardName = matcher.group("cardName");
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        if (!isValidDeckToAddCard(matcher, cardName, deckName)) return;
        Deck deck = Objects.requireNonNull(assets).getDeckByDeckName(deckName);
        if (deck.isSideFull()) {
            view.showError(Error.MAIN_DECK_IS_FULL);
            return;
        }
        Card card = Card.getCardByName(cardName);
        if (assets.getNumberOfCards(card) == deck.getNumberOfCardInDeck(card)) {
            view.showError(Error.DONT_HAVE_ENOUGH_OF_THIS_CARD);
            return;
        }
        if (Objects.requireNonNull(card).getCardType().equals(CardType.MONSTER)) {
            addMonsterToSideDeck((Monster) card, deck, matcher);
        } else if (card.getCardType().equals(CardType.SPELL)) {
            addSpellToSideDeck((Spell) card, deck, matcher);
        } else {
            addTrapToSideDeck((Trap) card, deck, matcher);
        }
    }

    private boolean isValidDeckToAddCard(Matcher matcher, String cardName, String deckName) {
        if (!doesCardExistInUserCards(cardName)) {
            view.showDynamicError(Error.INCORRECT_CARD_NAME, matcher);
            return false;
        } else if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return false;
        }
        return true;
    }

    private void addMonsterToMainDeck(Monster monster, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, false, deck.getNumberOfCardInDeck(monster))) return;
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        Objects.requireNonNull(assets).addCardToMainDeck(monster, deck);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addSpellToMainDeck(Spell spell, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, spell.getIsLimited(), deck.getNumberOfCardInDeck(spell))) return;
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        Objects.requireNonNull(assets).addCardToMainDeck(spell, deck);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addTrapToMainDeck(Trap trap, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, trap.getIsLimited(), deck.getNumberOfCardInDeck(trap))) return;
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        Objects.requireNonNull(assets).addCardToMainDeck(trap, deck);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addMonsterToSideDeck(Monster monster, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, false, deck.getNumberOfCardInDeck(monster))) return;
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        Objects.requireNonNull(assets).addCardToSideDeck(monster, deck);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addSpellToSideDeck(Spell spell, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, spell.getIsLimited(), deck.getNumberOfCardInDeck(spell))) return;
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        Objects.requireNonNull(assets).addCardToSideDeck(spell, deck);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addTrapToSideDeck(Trap trap, Deck deck, Matcher matcher) {
        if (!isValidNumberOfCardInDeck(matcher, trap.getIsLimited(), deck.getNumberOfCardInDeck(trap))) return;
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        Objects.requireNonNull(assets).addCardToSideDeck(trap, deck);
        view.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private boolean isValidNumberOfCardInDeck(Matcher matcher, boolean isLimited, int numberOfCardInDeck) {
        if (isLimited) {
            if (numberOfCardInDeck == 1) {
                view.showDynamicError(Error.CARD_LIMITED_IN_DECK, matcher);
                return false;
            }
        } else if (numberOfCardInDeck == 3) {
            view.showDynamicError(Error.EXCESSIVE_NUMBER_IN_DECK, matcher);
            return false;
        }
        return true;
    }

    public void showAllDecks() {
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        ArrayList<Deck> decks = Objects.requireNonNull(assets).getAllDecks();
        Comparator<Deck> comparator = Comparator.comparing(Deck::isActivated).reversed().thenComparing(Deck::getName);
        decks.sort(comparator);
        System.out.println("Decks:\n" +
                "Active deck:");
        if (decks.size() > 0) {
            for (int i = 0; i < decks.size(); i++) {
                Deck deck = decks.get(i);
                if (i == 0) {
                    if (deck.isActivated()) {
                        System.out.println(deck.getName() + ":" + " main deck " + deck.getMainCards().size() +
                                ",side deck " + deck.getSideCards().size() + "," + (deck.isValidDeck() ? "Valid" : "Invalid"));
                    }
                    System.out.println("Other decks:");
                }
                if (!deck.isActivated()) {
                    System.out.println(deck.getName() + ":" + " main deck " + deck.getMainCards().size() +
                            ",side deck " + deck.getSideCards().size() + "," + (deck.isValidDeck() ? "Valid" : "Invalid"));
                }
            }
        } else
            System.out.println("Other decks:");
    }

    public void showDeck(Matcher matcher, String deckType) {
        String deckName = matcher.group("deckName");
        if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        }
        List<Card> monsters = new ArrayList<>();
        List<Card> spellsAndTraps = new ArrayList<>();
        List<Card> cards;
        if (deckType.equals("Main")) {
            cards = Objects.requireNonNull(Assets.getAssetsByUsername(MenusManager.getInstance().
                    getLoggedInUser().getUsername())).getDeckByDeckName(deckName).getMainCards();
        } else {
            cards = Objects.requireNonNull(Assets.getAssetsByUsername(MenusManager.getInstance().
                    getLoggedInUser().getUsername())).getDeckByDeckName(deckName).getSideCards();
        }
        for (Card card : cards) {
            if (card.getCardType().equals(CardType.MONSTER))
                monsters.add(card);
            else
                spellsAndTraps.add(card);
        }
        Comparator<Card> comparator = Comparator.comparing(Card::getName);
        monsters.sort(comparator);
        spellsAndTraps.sort(comparator);
        System.out.println("Deck: " + deckName);
        System.out.println(deckType + " deck:");
        System.out.println("Monsters:");
        for (Card card : monsters)
            System.out.println(card.getName() + ": " + card.getDescription());
        System.out.println("Spell and Traps:");
        for (Card card : spellsAndTraps)
            System.out.println(card.getName() + ": " + card.getDescription());
    }

    public void showAllCards() {
        Map<Card, Integer> cardsMap = Objects.requireNonNull(Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername())).getAllUserCards();
        List<Card> cardsList = new ArrayList<>(cardsMap.keySet());
        Comparator<Card> comparator = Comparator.comparing(Card::getName);
        cardsList.sort(comparator);
        for (Card card : cardsList) {
            System.out.println(card.getName() + ": " + card.getDescription());
        }
    }

    public void showCard(Matcher matcher) {
        String cardName = matcher.group("cardName");
        Card card;
        if ((card = Card.getCardByName(cardName)) == null) {
            view.showError(Error.CARD_DOES_NOT_EXIST);
            return;
        }
        if (card.getCardType().equals(CardType.MONSTER)) {
            Monster monster = (Monster) card;
            System.out.println(monster);
        } else if (card.getCardType().equals(CardType.SPELL)) {
            Spell spell = (Spell) card;
            System.out.println(spell);
        } else {
            Trap trap = (Trap) card;
            System.out.println(trap);
        }
    }

    public void removeCardFromMainDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        String cardName = matcher.group("cardName");
        Deck deck;
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        } else if (!(deck = Objects.requireNonNull(assets).getDeckByDeckName(deckName)).containsMainCard(cardName)) {
            view.showDynamicError(Error.CARD_DOES_NOT_EXIST_IN_MAIN_DECK, matcher);
            return;
        }
        assets.removeCardFromMainDeck(Card.getCardByName(cardName), deck);
        view.showSuccessMessage(SuccessMessage.CARD_REMOVED);
    }

    public void removeCardFromSideDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        String cardName = matcher.group("cardName");
        Deck deck;
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        if (!doesDeckExist(deckName)) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        } else if (!(deck = Objects.requireNonNull(assets).getDeckByDeckName(deckName)).containsSideCard(cardName)) {
            view.showDynamicError(Error.CARD_DOES_NOT_EXIST_IN_SIDE_DECK, matcher);
            return;
        }
        assets.removeCardFromSideDeck(Card.getCardByName(cardName), deck);
        view.showSuccessMessage(SuccessMessage.CARD_REMOVED);
    }

    private boolean doesDeckExist(String deckName) {
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        return Objects.requireNonNull(assets).getDeckByDeckName(deckName) != null;
    }

    private boolean doesCardExistInUserCards(String cardName) {
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        HashMap<Card, Integer> userCards = Objects.requireNonNull(assets).getAllUserCards();
        for (Card card : userCards.keySet()) {
            if (card.getName().equals(cardName))
                if (userCards.get(card) > 0)
                    return true;
        }
        return false;
    }

}