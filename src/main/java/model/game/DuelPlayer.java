package model.game;

import model.Deck;
import model.User;

public class DuelPlayer {
    private String nickname;
    private int lifePoint;
    private Deck playDeck;
    private PlayerBoard playerBoard;

    public DuelPlayer(String nickname, Deck deck) {
        setNickname(nickname);
        playerBoard = new PlayerBoard();
        lifePoint = 8000;
        setPlayDeck(deck);
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private void setPlayDeck(Deck playDeck) {
        this.playDeck = playDeck;
    }

    public Deck getPlayDeck() {
        return playDeck;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public String getNickname() {
        return nickname;
    }
}