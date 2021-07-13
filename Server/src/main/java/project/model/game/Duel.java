package project.model.game;

import project.model.User;

import java.util.ArrayList;
import java.util.Objects;

public class Duel {
    private int numberOfRounds;
    private DuelPlayer player1;
    private DuelPlayer player2;
    private ArrayList<String> winnersList = new ArrayList<>();
    private int[] lifePointsPlayer1;
    private int[] lifePointsPlayer2;
    private DuelPlayer winner;
    private DuelPlayer loser;
    private int currentRound;
    private boolean isWithAi = false;

    public Duel(String username1, String username2, int numberOfRounds, boolean isWithAi) throws CloneNotSupportedException {
        User user1 = User.getUserByUsername(username1);
        User user2 = User.getUserByUsername(username2);
        this.numberOfRounds = numberOfRounds;
        lifePointsPlayer1 = new int[numberOfRounds];
        lifePointsPlayer2 = new int[numberOfRounds];
        setDuelPlayers(Objects.requireNonNull(user1), Objects.requireNonNull(user2));
        this.isWithAi = isWithAi;
    }


    public boolean isWithAi() {
        return isWithAi;
    }

    public void setDuelPlayers(User user1, User user2) throws CloneNotSupportedException {
        player1 = new DuelPlayer(user1.getNickname(), Objects.requireNonNull(User.getActiveDeckByUsername(user1.getUsername())).copy());
        player2 = new DuelPlayer(user2.getNickname(), Objects.requireNonNull(User.getActiveDeckByUsername(user2.getUsername())).copy());
    }

    public DuelPlayer getPlayer1() {
        return player1;
    }

    public DuelPlayer getPlayer2() {
        return player2;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }


    public void addLifePointOfPlayer1(int lifePointsPlayer1) {
        this.lifePointsPlayer1[currentRound - 1] = lifePointsPlayer1;
    }


    public void addLifePointOfPlayer2(int lifePointsPlayer2) {
        this.lifePointsPlayer2[currentRound - 1] = lifePointsPlayer2;
    }

    public int maximumNumberOfLifePointsPlayer1() {
        int n = lifePointsPlayer1.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (lifePointsPlayer1[j] < lifePointsPlayer1[j + 1]) {
                    int temp = lifePointsPlayer1[j];
                    lifePointsPlayer1[j] = lifePointsPlayer1[j + 1];
                    lifePointsPlayer1[j + 1] = temp;
                }
        return lifePointsPlayer1[0];
    }

    public int maximumNumberOfLifePointsPlayer2() {
        int n = lifePointsPlayer2.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (lifePointsPlayer2[j] < lifePointsPlayer2[j + 1]) {
                    int temp = lifePointsPlayer2[j];
                    lifePointsPlayer2[j] = lifePointsPlayer2[j + 1];
                    lifePointsPlayer2[j + 1] = temp;
                }
        return lifePointsPlayer2[0];
    }

    public ArrayList<String> getWinners() {
        return this.winnersList;
    }

    public void setWinners(String winner) {
        this.winnersList.add(winner);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void setWinnerAndLoser(DuelPlayer winner, DuelPlayer loser) {
        this.winner = winner;
        this.loser = loser;
    }

    public DuelPlayer getWinner() {
        return winner;
    }

    public DuelPlayer getLoser() {
        return loser;
    }

    public void finishRound() {
        try {
            player1.setPlayDeck(Objects.requireNonNull(User.getActiveDeckByNickName(player1.getNickname())).copy());
            player2.setPlayDeck(Objects.requireNonNull(User.getActiveDeckByNickName(player2.getNickname())).copy());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        player1.setPlayerBoard(new PlayerBoard());
        player2.setPlayerBoard(new PlayerBoard());
    }
}