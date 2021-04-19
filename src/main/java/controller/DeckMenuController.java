package controller;

import model.Assets;
import model.Deck;
import model.User;
import model.game.Duel;
import view.DeckMenuView;
import view.MenusManager;
import view.messages.Error;
import view.messages.SuccessMessage;

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
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        assert assets != null;
        if (assets.getDeckByDeckName(deckName) != null) {
            view.showDynamicError(Error.DECK_EXIST, matcher);
            return;
        }
        view.showSuccessMessage(SuccessMessage.DECK_CREATED);
        assets.createDeck(deckName);
    }

    public void deleteDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        assert assets != null;
        if (assets.getDeckByDeckName(deckName) == null) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        }
        view.showSuccessMessage(SuccessMessage.DECK_DELETED);
        assets.deleteDeck(deckName);
    }

    public void activateDeck(Matcher matcher) {
        Assets assets = Assets.getAssetsByUsername(MenusManager.getInstance().getLoggedInUser().getUsername());
        assert assets != null;
        String deckName = matcher.group("deckName");
        if (assets.getDeckByDeckName(deckName) == null) {
            view.showDynamicError(Error.DECK_NOT_EXIST, matcher);
            return;
        }
        assets.activateDeck(deckName);
        view.showSuccessMessage(SuccessMessage.DECK_ACTIVATED);
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
