package project.controller;

import project.controller.playgame.DuelGameController;
import project.model.User;
import project.model.game.Duel;
//import project.view.MenusManager;
import project.view.messages.StartDuelMessage;

import java.util.Objects;

public class DuelMenuController {
    private static DuelMenuController instance = null;
    private Duel duel;

    private DuelMenuController() {
    }

    public static DuelMenuController getInstance() {
        if (instance == null)
            instance = new DuelMenuController();
        return instance;
    }


    public StartDuelMessage startDuelWithOtherPlayer(String secondPlayerUsername, int roundNumber) {
        StartDuelMessage message = null;
        if (!isPlayerValidToStartDuel(secondPlayerUsername)) {
            return StartDuelMessage.INVALID_USER_TO_PLAY_WITH;
        } else if ((message = arePlayersDecksActive(secondPlayerUsername)) == StartDuelMessage.SUCCESS) {
            if ((message = arePlayersDecksValid(secondPlayerUsername)) == StartDuelMessage.SUCCESS) {
                try {
                    duel = new Duel(MainMenuController.getInstance().getLoggedInUser().getUsername(), secondPlayerUsername, roundNumber, false);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                DuelGameController.getInstance().startDuel(duel);
                return StartDuelMessage.SUCCESS;
            }
        }
        return message;
    }

    public StartDuelMessage startDuelWithAI(int roundNumber) {
        if (arePlayersDecksActive("ai") == StartDuelMessage.SUCCESS) {
            if (arePlayersDecksValid("ai") == StartDuelMessage.SUCCESS) {
                try {
                    duel = new Duel(MainMenuController.getInstance().getLoggedInUser().getUsername(), "ai", roundNumber, true);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                DuelGameController.getInstance().startDuel(duel);
            } else return StartDuelMessage.INVALID_DECK;
        } else return StartDuelMessage.INACTIVE_DECK;
        return StartDuelMessage.SUCCESS;
    }

    private boolean isPlayerValidToStartDuel(String username) {
        User user = User.getUserByUsername(username);
        return user != null && !user.getUsername().equals(MainMenuController.getInstance().getLoggedInUser().getUsername());
    }

    public boolean areRoundsNumberValid(int roundsNumber) {
        return (roundsNumber == 3 || roundsNumber == 1);
    }

    public StartDuelMessage arePlayersDecksActive(String secondPlayerUserName) {
        if (!MainMenuController.getInstance().getLoggedInUser().getHasActiveDeck()) {
            return StartDuelMessage.YOUR_INACTIVE_DECK;
        }
        User user = Objects.requireNonNull(User.getUserByUsername(secondPlayerUserName));
        if (!user.getHasActiveDeck())
            return StartDuelMessage.OPPONENT_INACTIVE_DECK;
        return StartDuelMessage.SUCCESS;
    }

    public StartDuelMessage arePlayersDecksValid(String secondPlayerUsername) {
        StartDuelMessage message;
        if (!Objects.requireNonNull(User.getActiveDeckByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).isValidDeck()) {
            //view.showDynamicErrorForInactiveDeck(Error.FORBIDDEN_DECK, MainMenuController.getInstance().getLoggedInUser().getUsername());
            message = StartDuelMessage.YOUR_INVALID_DECK;
            return message;
        } else if (!Objects.requireNonNull(User.getActiveDeckByUsername(secondPlayerUsername)).isValidDeck()) {
            //view.showDynamicErrorForInactiveDeck(Error.FORBIDDEN_DECK, secondPlayerUsername);
            message = StartDuelMessage.OPPONENT_INVALID_DECK;
            return message;
        }
        return StartDuelMessage.SUCCESS;
    }

}