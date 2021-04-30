package controller;

import model.Assets;
import model.Deck;
import model.User;
import model.game.Duel;
import view.DuelMenuView;
import view.messages.Error;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

public class DuelMenuController {
    private static final DuelMenuController instance;
    private final DuelMenuView view = DuelMenuView.getInstance();
    private User loggedInUser;
    private Duel duel;

    static {
        instance = new DuelMenuController();
    }

    public static DuelMenuController getInstance() {
        return instance;
    }


    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void startDuelWithOtherPlayer(Matcher matcher) {
        if (!isPlayerValidToStartDuel(matcher.group("secondPlayerNickName"))) {
            view.showError(Error.PLAYER_DOES_NOT_EXIST);
        } else if (!areRoundsNumberValid(Integer.parseInt(matcher.group("roundNumber")))) {
            view.showError(Error.WRONG_ROUNDS_NUMBER);
        } else if (arePlayersDecksActive(matcher.group("secondPlayerNickName"))) {
            arePlayersDecksValid(matcher.group("secondPlayerNickName"));
        }
    }

    public void startDuelWithAI(Matcher matcher) {
        if (!areRoundsNumberValid(Integer.parseInt(matcher.group("roundNumber"))))
            view.showError(Error.WRONG_ROUNDS_NUMBER);
    }

    private boolean isPlayerValidToStartDuel(String username) {
        User user = User.getUserByUsername(username);
        return user != null;
    }

    public boolean areRoundsNumberValid(int roundsNumber) {
        return roundsNumber != 3 && roundsNumber != 1;
    }

    public boolean arePlayersDecksActive(String secondPlayerUserName) {
        if (!loggedInUser.getHasActiveDeck()) {
            view.showDynamicError(Error.INACTIVATED_DECK, loggedInUser.getUsername());
            return false;
        }
        User user = Objects.requireNonNull(User.getUserByUsername(secondPlayerUserName));
        if (!user.getHasActiveDeck()) {
            view.showDynamicError(Error.INACTIVATED_DECK, secondPlayerUserName);
            return false;
        }
        return true;
    }

    public void arePlayersDecksValid(String secondPlayerUsername) {
        List<Deck> getPlayersDecks = Objects.requireNonNull(Assets.getAssetsByUsername(loggedInUser.getUsername())).getAllDecks();
        for (Deck firstPlayerDeck : getPlayersDecks) {
            if (firstPlayerDeck.isActivated()) {
                if (!firstPlayerDeck.isValidDeck()) {
                    view.showDynamicError(Error.FORBIDDEN_DECK, loggedInUser.getUsername());
                }
            }
        }
        getPlayersDecks = Objects.requireNonNull(Assets.getAssetsByUsername(secondPlayerUsername)).getAllDecks();
        for (Deck secondPlayerDeck : getPlayersDecks) {
            if (secondPlayerDeck.isActivated()) {
                if (!secondPlayerDeck.isValidDeck()) {
                    view.showDynamicError(Error.FORBIDDEN_DECK, secondPlayerUsername);
                }
            }
        }
    }
}