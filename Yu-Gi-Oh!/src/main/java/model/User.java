package model;

import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;

import java.util.ArrayList;
import java.util.Collections;

public class User implements Comparable<User> {
    private static final ArrayList<User> allUsers;
    private String username;
    private String password;
    private String nickname;
    private boolean hasActiveDeck;
    private int score;
    private boolean isUserLoggedIn;

    {
        hasActiveDeck = false;
        isUserLoggedIn = false;
    }

    static {
        allUsers = new ArrayList<> ();
    }

    public User(String username, String password, String nickname) {
        setUsername (username);
        setNickname (nickname);
        setPassword (password);
        new Assets (username);
        allUsers.add (this);
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

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
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

    public boolean getHasActiveDeck() {
        return true;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void changeNickname(String newNickname) {
        setNickname (newNickname);
    }

    public void changePassword(String newPassword) {
        setPassword (newPassword);
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers)
            if (user.username.equals (username)) return user;
        return null;
    }

    @Override
    public int compareTo(User user) {
        if (score != user.score) return user.score - score;
        return nickname.compareTo (user.nickname);
    }

    public static ArrayList<User> sortAllUsers() {
        Collections.sort (allUsers);
        return allUsers;
    }
}
