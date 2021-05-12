package model.game;

import model.User;

import java.util.ArrayList;
import java.util.Objects;

public class Duel {
    private int numberOfRounds;
    private DuelPlayer player1;
    private DuelPlayer player2;
    private ArrayList<String> winner = new ArrayList<>();
    private int[] lifePointsPlayer1;
    private int[] lifePointsPlayer2;

    public Duel(String username1, String username2, int numberOfRounds) throws CloneNotSupportedException {
        User user1 = User.getUserByUsername(username1);
        User user2 = User.getUserByUsername(username2);
        this.numberOfRounds = numberOfRounds;
        lifePointsPlayer1 = new int[numberOfRounds];
        lifePointsPlayer2 = new int[numberOfRounds];
        setDuelPlayers(Objects.requireNonNull(user1), Objects.requireNonNull(user2));
    }

    public void saveLifePoints(int lifePointPlayer1, int lifePointPlayer2) {

    }

    public void setDuelPlayers(User user1, User user2) throws CloneNotSupportedException {
        player1 = new DuelPlayer(user1.getNickname(), Objects.requireNonNull(User.getActiveDeck(user1.getUsername())).copy());
        player2 = new DuelPlayer(user2.getNickname(), Objects.requireNonNull(User.getActiveDeck(user2.getUsername())).copy());
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

    public int[] getLifePointsPlayer1() {
        return lifePointsPlayer1;
    }

    public void setLifePointsPlayer1(int[] lifePointsPlayer1) {
        this.lifePointsPlayer1 = lifePointsPlayer1;
    }

    public int[] getLifePointsPlayer2() {
        return lifePointsPlayer2;
    }

    public void setLifePointsPlayer2(int[] lifePointsPlayer2) {
        this.lifePointsPlayer2 = lifePointsPlayer2;
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

    public ArrayList<String> getWinner() {
        return this.winner;
    }

    public void setWinner(String winner) {
        this.winner.add(winner);
    }
}