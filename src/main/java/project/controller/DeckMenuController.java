package project.controller;

import project.model.Deck;
import project.model.card.Monster;
import project.model.card.Spell;
import project.model.card.Trap;
import project.view.DeckMenuView;
import project.view.messages.SuccessMessage;

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

    public void createDeck(String deckName) {
        if (doesDeckExist(deckName)) {
//            view.showDynamicError(Error.DECK_EXIST, deckName);
            return;
        }
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        SuccessMessage.showSuccessMessage(SuccessMessage.DECK_CREATED);
//        Objects.requireNonNull(assets).createDeck(deckName);
    }

    public void deleteDeck(String deckName) {
        if (!doesDeckExist(deckName)) {
//            view.showDynamicError(Error.DECK_NOT_EXIST, deckName);
            return;
        }
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        SuccessMessage.showSuccessMessage(SuccessMessage.DECK_DELETED);
//        Objects.requireNonNull(assets).deleteDeck(deckName);
    }

    public void activateDeck(String deckName) {
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        if (!doesDeckExist(deckName)) {
//            view.showDynamicError(Error.DECK_NOT_EXIST, deckName);
            return;
        }
//        Objects.requireNonNull(assets).activateDeck(deckName);
        SuccessMessage.showSuccessMessage(SuccessMessage.DECK_ACTIVATED);
    }

    public void addCardToMainDeck(String deckName, String cardName) {
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance()
//                .getLoggedInUser().getUsername());
//        if (!isValidDeckToAddCard(cardName, deckName)) return;
//        Deck deck = Objects.requireNonNull(assets).getDeckByDeckName(deckName);
//        if (deck.isMainFull()) {
//          Error.showError(Error.MAIN_DECK_IS_FULL);
//          return;
//        }
//        Card card = Card.getCardByName(cardName);
//        if (assets.getNumberOfCards(card) == deck.getNumberOfCardInDeck(card)) {
//            Error.showError(Error.DONT_HAVE_ENOUGH_OF_THIS_CARD);
//            return;
//        }
//
//        if (Objects.requireNonNull(card).getCardType().equals(CardType.MONSTER))
//            addMonsterToMainDeck((Monster) card, deck, deckName, cardName);
//        else if (card.getCardType().equals(CardType.SPELL))
//            addSpellToMainDeck((Spell) card, deck, deckName, cardName);
//        else
//            addTrapToMainDeck((Trap) card, deck, deckName, cardName);
    }

    public void addCardToSideDeck(String deckName, String cardName) {
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        if (!isValidDeckToAddCard(cardName, deckName)) return;
//        Deck deck = Objects.requireNonNull(assets).getDeckByDeckName(deckName);
//        if (deck.isSideFull()) {
//            Error.showError(Error.MAIN_DECK_IS_FULL);
//            return;
//        }
//        Card card = Card.getCardByName(cardName);
//        if (assets.getNumberOfCards(card) == deck.getNumberOfCardInDeck(card)) {
//            Error.showError(Error.DONT_HAVE_ENOUGH_OF_THIS_CARD);
//            return;
//        }
//        if (Objects.requireNonNull(card).getCardType().equals(CardType.MONSTER)) {
//            addMonsterToSideDeck((Monster) card, deck, deckName, cardName);
//        } else if (card.getCardType().equals(CardType.SPELL)) {
//            addSpellToSideDeck((Spell) card, deck, deckName, cardName);
//        } else {
//            addTrapToSideDeck((Trap) card, deck, deckName, cardName);
//        }
    }

    private boolean isValidDeckToAddCard(String cardName, String deckName) {
        if (!doesCardExistInUserCards(cardName)) {
//            view.showDynamicError(Error.INCORRECT_CARD_NAME, cardName);
            return false;
        } else if (!doesDeckExist(deckName)) {
//            view.showDynamicError(Error.DECK_NOT_EXIST, deckName);
            return false;
        }
        return true;
    }

    private void addMonsterToMainDeck(Monster monster, Deck deck, String deckName, String cardName) {
//        if (!isValidNumberOfCardInDeck(deckName, cardName, false, deck.getNumberOfCardInDeck(monster))) return;
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        Objects.requireNonNull(assets).addCardToMainDeck(monster, deck);
//        SuccessMessage.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addSpellToMainDeck(Spell spell, Deck deck, String deckName, String cardName) {
//        if (!isValidNumberOfCardInDeck(deckName, cardName, spell.getIsLimited(), deck.getNumberOfCardInDeck(spell))) return;
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        Objects.requireNonNull(assets).addCardToMainDeck(spell, deck);
//        SuccessMessage.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addTrapToMainDeck(Trap trap, Deck deck, String deckName, String cardName) {
//        if (!isValidNumberOfCardInDeck(deckName, cardName, trap.getIsLimited(), deck.getNumberOfCardInDeck(trap))) return;
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        Objects.requireNonNull(assets).addCardToMainDeck(trap, deck);
//        SuccessMessage.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addMonsterToSideDeck(Monster monster, Deck deck, String deckName, String cardName) {
//        if (!isValidNumberOfCardInDeck(deckName, cardName, false, deck.getNumberOfCardInDeck(monster))) return;
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        Objects.requireNonNull(assets).addCardToSideDeck(monster, deck);
//        SuccessMessage.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addSpellToSideDeck(Spell spell, Deck deck, String deckName, String cardName) {
//        if (!isValidNumberOfCardInDeck(deckName, cardName, spell.getIsLimited(), deck.getNumberOfCardInDeck(spell))) return;
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        Objects.requireNonNull(assets).addCardToSideDeck(spell, deck);
//        SuccessMessage.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private void addTrapToSideDeck(Trap trap, Deck deck, String deckName, String cardName) {
//        if (!isValidNumberOfCardInDeck(deckName, cardName, trap.getIsLimited(), deck.getNumberOfCardInDeck(trap))) return;
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        Objects.requireNonNull(assets).addCardToSideDeck(trap, deck);
//        SuccessMessage.showSuccessMessage(SuccessMessage.CARD_ADDED_TO_THE_DECK);
    }

    private boolean isValidNumberOfCardInDeck(String deckName, String cardName, boolean isLimited, int numberOfCardInDeck) {
        if (isLimited) {
            if (numberOfCardInDeck == 1) {
//                view.showDynamicError(Error.CARD_LIMITED_IN_DECK, cardName);
                return false;
            }
        } else if (numberOfCardInDeck == 3) {
//            view.showDynamicErrorWithTwoStrings (Error.EXCESSIVE_NUMBER_IN_DECK, cardName, deckName);
            return false;
        }
        return true;
    }

    public void showAllDecks() {
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        ArrayList<Deck> decks = Objects.requireNonNull(assets).getAllDecks();
//        Comparator<Deck> comparator = Comparator.comparing(Deck::isActivated).reversed().thenComparing(Deck::getName);
//        decks.sort(comparator);
//        System.out.println("Decks:\n" +
//                "Active deck:");
//        if (decks.size() > 0) {
//            for (int i = 0; i < decks.size(); i++) {
//                Deck deck = decks.get(i);
//                if (i == 0) {
//                    if (deck.isActivated()) {
//                        System.out.println(deck.getName() + ":" + " main deck " + deck.getMainCards().size() +
//                                ",side deck " + deck.getSideCards().size() + "," + (deck.isValidDeck() ? "Valid" : "Invalid"));
//                    }
//                    System.out.println("Other decks:");
//                }
//                if (!deck.isActivated()) {
//                    System.out.println(deck.getName() + ":" + " main deck " + deck.getMainCards().size() +
//                            ",side deck " + deck.getSideCards().size() + "," + (deck.isValidDeck() ? "Valid" : "Invalid"));
//                }
//            }
//        } else
//            System.out.println("Other decks:");
    }

    public void showDeck(String deckName, String deckType) {
//        if (!doesDeckExist(deckName)) {
//            project.view.showDynamicError(Error.DECK_NOT_EXIST, deckName);
//            return;
//        }
//        List<Card> monsters = new ArrayList<>();
//        List<Card> spellsAndTraps = new ArrayList<>();
//        List<Card> cards;
//        if (deckType.equals("project.Main")) {
//            cards = Objects.requireNonNull(Assets.getAssetsByUsername(MenusManager.getInstance().
//                    getLoggedInUser().getUsername())).getDeckByDeckName(deckName).getMainCards();
//        } else {
//            cards = Objects.requireNonNull(Assets.getAssetsByUsername(MenusManager.getInstance().
//                    getLoggedInUser().getUsername())).getDeckByDeckName(deckName).getSideCards();
//        }
//        for (Card card : cards) {
//            if (card.getCardType().equals(CardType.MONSTER))
//                monsters.add(card);
//            else
//                spellsAndTraps.add(card);
//        }
//        Comparator<Card> comparator = Comparator.comparing(Card::getName);
//        monsters.sort(comparator);
//        spellsAndTraps.sort(comparator);
//        System.out.println("Deck: " + deckName);
//        System.out.println(deckType + " deck:");
//        System.out.println("Monsters:");
//        for (Card card : monsters)
//            System.out.println(card.getName() + ": " + card.getDescription());
//        System.out.println("Spell and Traps:");
//        for (Card card : spellsAndTraps)
//            System.out.println(card.getName() + ": " + card.getDescription());
    }

    public void showAllCards() {
//        Map<Card, Integer> cardsMap = Objects.requireNonNull(Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername())).getAllUserCards();
//        List<Card> cardsList = new ArrayList<>(cardsMap.keySet());
//        Comparator<Card> comparator = Comparator.comparing(Card::getName);
//        cardsList.sort(comparator);
//        for (Card card : cardsList) {
//            System.out.println(card.getName() + ": " + card.getDescription());
//        }
    }

    public void showCard(String cardName) {
//        Card card = Card.getCardByName(cardName);
//        if (card == null) {
//            Error.showError(Error.CARD_DOES_NOT_EXIST);
//            return;
//        }
//        project.view.checkTypeOfCardAndPrintIt(card);
//    }
//
//    public void removeCardFromMainDeck(String deckName, String cardName) {
//        Deck deck;
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        if (!doesDeckExist(deckName)) {
//            project.view.showDynamicError(Error.DECK_NOT_EXIST, deckName);
//            return;
//        } else if (!(deck = Objects.requireNonNull(assets).getDeckByDeckName(deckName)).containsMainCard(cardName)) {
//            project.view.showDynamicError(Error.CARD_DOES_NOT_EXIST_IN_MAIN_DECK, cardName);
//            return;
//        }
//        assets.removeCardFromMainDeck(Card.getCardByName(cardName), deck);
//        SuccessMessage.showSuccessMessage(SuccessMessage.CARD_REMOVED);
    }

    public void removeCardFromSideDeck(String deckName, String cardName) {
//        Deck deck;
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        if (!doesDeckExist(deckName)) {
//            project.view.showDynamicError(Error.DECK_NOT_EXIST, deckName);
//            return;
//        } else if (!(deck = Objects.requireNonNull(assets).getDeckByDeckName(deckName)).containsSideCard(cardName)) {
//            project.view.showDynamicError(Error.CARD_DOES_NOT_EXIST_IN_SIDE_DECK, cardName);
//            return;
//        }
//        assets.removeCardFromSideDeck(Card.getCardByName(cardName), deck);
//        SuccessMessage.showSuccessMessage(SuccessMessage.CARD_REMOVED);
    }

    private boolean doesDeckExist(String deckName) {
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        return Objects.requireNonNull(assets).getDeckByDeckName(deckName) != null;
        return true;
    }

    private boolean doesCardExistInUserCards(String cardName) {
//        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
//        HashMap<Card, Integer> userCards = Objects.requireNonNull(assets).getAllUserCards();
//        for (Card card : userCards.keySet()) {
//            if (card.getName().equalsIgnoreCase(cardName))
//                if (userCards.get(card) > 0)
//                    return true;
//        }
        return false;
    }

}