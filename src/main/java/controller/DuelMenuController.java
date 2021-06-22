package controller;

import controller.playgame.DuelGameController;
import model.User;
import model.game.Duel;
import view.DuelMenuView;
import view.MenusManager;
import view.messages.Error;

import java.util.Objects;

public class DuelMenuController {
    private static DuelMenuController instance = null;
    private final DuelMenuView view = DuelMenuView.getInstance();
    private Duel duel;

    private DuelMenuController() {
    }

    public static DuelMenuController getInstance() {
        if (instance == null)
            instance = new DuelMenuController();
        return instance;
    }


    public void startDuelWithOtherPlayer(String secondPlayerUsername, int roundNumber) throws CloneNotSupportedException {
        if (!isPlayerValidToStartDuel(secondPlayerUsername)) {
            Error.showError(Error.PLAYER_DOES_NOT_EXIST);
        } else if (!areRoundsNumberValid(roundNumber)) {
            Error.showError(Error.WRONG_ROUNDS_NUMBER);
        } else if (arePlayersDecksActive(secondPlayerUsername)) {
            if (arePlayersDecksValid(secondPlayerUsername)) {
                duel = new Duel(MenusManager.getInstance().getLoggedInUser().getUsername(), secondPlayerUsername, roundNumber, false);
                DuelGameController.getInstance().startDuel(duel);
            }
        }
    }

    public void startDuelWithAI(int roundNumber) throws CloneNotSupportedException {
        if (!areRoundsNumberValid(roundNumber)) {
            Error.showError(Error.WRONG_ROUNDS_NUMBER);
        } else if (arePlayersDecksActive("ai")) {
            if (arePlayersDecksValid("ai")) {
                duel = new Duel(MenusManager.getInstance().getLoggedInUser().getUsername(), "ai", roundNumber, true);
                DuelGameController.getInstance().startDuel(duel);
            }
        }
    }

    private boolean isPlayerValidToStartDuel(String username) {
        User user = User.getUserByUsername(username);
        return user != null;
    }

    public boolean areRoundsNumberValid(int roundsNumber) {
        return (roundsNumber == 3 || roundsNumber == 1);
    }

    public boolean arePlayersDecksActive(String secondPlayerUserName) {
        if (!MenusManager.getInstance().getLoggedInUser().getHasActiveDeck()) {
            view.showDynamicErrorForInactiveDeck(Error.INACTIVATED_DECK, MenusManager.getInstance().getLoggedInUser().getUsername());
            return false;
        }
        User user = Objects.requireNonNull(User.getUserByUsername(secondPlayerUserName));
        if (!user.getHasActiveDeck()) {
            view.showDynamicErrorForInactiveDeck(Error.INACTIVATED_DECK, secondPlayerUserName);
            return false;
        }
        return true;
    }

    public boolean arePlayersDecksValid(String secondPlayerUsername) {
        if (!Objects.requireNonNull(User.getActiveDeckByUsername(MenusManager.getInstance().getLoggedInUser().getUsername())).isValidDeck()) {
            view.showDynamicErrorForInactiveDeck(Error.FORBIDDEN_DECK, MenusManager.getInstance().getLoggedInUser().getUsername());
            return false;
        } else if (!Objects.requireNonNull(User.getActiveDeckByUsername(secondPlayerUsername)).isValidDeck()) {
            view.showDynamicErrorForInactiveDeck(Error.FORBIDDEN_DECK, secondPlayerUsername);
            return false;
        }
        return true;
    }

}