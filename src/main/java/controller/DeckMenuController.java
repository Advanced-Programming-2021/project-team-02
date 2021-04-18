package controller;

import model.User;
import model.game.Duel;

import java.util.regex.Matcher;

public class DeckMenuController {
    int q = 0;
    private User loggedIInUser;
    private static final DeckMenuController instance;

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

    }

    public void deleteDeck(Matcher matcher) {

    }

    public void activateDeck(Matcher matcher) {

    }

    public void addCardToDeck(Matcher matcher) {

    }

    public void showAllDecks(Matcher matcher) {

    }

    public void showDeck(Matcher matcher) {

    }

    public void showAllCards(Matcher matcher) {

    }
}
