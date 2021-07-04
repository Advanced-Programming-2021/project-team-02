package project.model.game;

import project.model.Avatar;
import project.model.Deck;
import project.model.User;

import java.util.Objects;

public class DuelPlayer {
    private String nickname;
    private int lifePoint;
    private Deck playDeck;
    private PlayerBoard playerBoard;
    private Avatar avatar;

    public DuelPlayer(String nickname, Deck deck) {
        setNickname(nickname);
        playerBoard = new PlayerBoard();
        lifePoint = 8000;
        setPlayDeck(deck);
        avatar = Objects.requireNonNull(User.getUserByNickName(nickname)).getAvatar();
    }

    public Deck getPlayDeck() {
        return playDeck;
    }

    public void setPlayDeck(Deck playDeck) {
        this.playDeck = playDeck;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public String getNickname() {
        return nickname;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void decreaseLP(int number) {
        lifePoint -= number;
        if (lifePoint < 0)
            lifePoint = 0;
    }

    public void increaseLP(int amount) {
        lifePoint += amount;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public Avatar getAvatar() {
        return avatar;
    }
}