package view;

import com.sun.xml.internal.ws.api.server.LazyMOMProvider;
import controller.DeckMenuController;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.regex.Matcher;

public class DeckMenuView {
    private static DeckMenuView instance = null;
    private static final DeckMenuController controller = DeckMenuController.getInstance();

    private DeckMenuView() {
    }

    public static DeckMenuView getInstance() {
        if (instance == null)
            instance = new DeckMenuView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.DECK_CREATE, command)).matches()) {
            controller.createDeck(matcher);
        } else if ((matcher = Regex.getMatcher(Regex.DECK_DELETE, command)).matches()) {
            controller.deleteDeck(matcher);
        } else if ((matcher = Regex.getMatcher(Regex.DECK_SET_ACTIVATE, command)).matches()) {
            controller.activateDeck(matcher);
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_ADD_CARD_TO_MAIN_DECK, command)) != null) {
            controller.addCardToMainDeck(matcher);
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_ADD_CARD_TO_SIDE_DECK, command)) != null) {
            controller.addCardToSideDeck(matcher);
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_REMOVE_CARD_MAIN_DECK, command)) != null) {
            controller.removeCardFromMainDeck(matcher);
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_REMOVE_CARD_SIDE_DECK, command)) != null) {
            controller.removeCardFromSideDeck(matcher);
        } else if (Regex.getMatcher(Regex.DECK_SHOW_ALL_DECKS, command).matches()) {
            controller.showAllDecks();
        } else if ((matcher = Regex.getMatcher(Regex.DECK_SHOW_MAIN_DECK, command)).matches()) {
            controller.showDeck(matcher, "Main");
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_SHOW_SIDE_DECK, command)) != null) {
            controller.showDeck(matcher, "Side");
        } else if (Regex.getMatcher(Regex.DECK_SHOW_ALL_CARDS, command).matches()) {
            controller.showAllCards();
        }else if ((matcher = Regex.getMatcher(Regex.CARD_SHOW, command)).matches()){
            controller.showCard(matcher);
        }else {
            showError(Error.INVALID_COMMAND);
        }
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showSuccessMessage(SuccessMessage message) {
        System.out.println(message.getValue());
    }

    public void showDynamicError(Error error, Matcher matcher) {
        if (error.equals(Error.DECK_EXIST)) {
            System.out.printf(Error.DECK_EXIST.getValue(), matcher.group("deckName"));
        } else if (error.equals(Error.DECK_NOT_EXIST)) {
            System.out.printf(Error.DECK_NOT_EXIST.getValue(), matcher.group("deckName"));
        } else if (error.equals(Error.INCORRECT_CARD_NAME)) {
            System.out.printf(Error.INCORRECT_CARD_NAME.getValue(), matcher.group("cardName"));
        }
    }
}