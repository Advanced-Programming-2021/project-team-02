package model.game;

import model.User;

import java.util.Objects;

public class Duel {
    private int numberOfRounds;
    private DuelPlayer player1;
    private DuelPlayer player2;
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
}