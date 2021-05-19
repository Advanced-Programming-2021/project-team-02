package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User implements Comparable<User> {
    private static final ArrayList<User> allUsers;
    private String username;
    private String password;
    private String nickname;
    private boolean hasActiveDeck;
    private int score;

    {
        hasActiveDeck = false;
    }

    static {
        allUsers = new ArrayList<>();
    }

    public User(String username, String password, String nickname) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        new Assets(username);
        allUsers.add(this);
    }

    public void activatedDeck() {
        hasActiveDeck = true;
    }

    public void deactivatedDeck() {
        hasActiveDeck = false;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getHasActiveDeck() {
        return hasActiveDeck;
    }

    public static Deck getActiveDeck(String username) {
        List<Deck> decks = Objects.requireNonNull(Objects.requireNonNull(Assets.getAssetsByUsername(username)).getAllDecks());
        for (Deck deck : decks) {
            if (deck.isActivated()) return deck;
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void changeNickname(String newNickname) {
        setNickname(newNickname);
    }

    public void changePassword(String newPassword) {
        setPassword(newPassword);
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers)
            if (user.username.equals(username)) return user;
        return null;
    }

    public static User getUserByNickName(String nickname) {
        for (User user : allUsers) {
            if (user.nickname.equals(nickname)) return user;
        }
        return null;
    }

    @Override
    public int compareTo(User user) {
        if (score != user.score) return user.score - score;
        return nickname.compareTo(user.nickname);
    }

    public static ArrayList<User> sortAllUsers() {
        Collections.sort(allUsers);
        return allUsers;
    }

    @Override
    public String toString() {
        return "User: " +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'';
    }

    public void addScoreOfGame(int score) {

    }

    public void increaseScore(int score) {
        this.score += score;
    }
}
