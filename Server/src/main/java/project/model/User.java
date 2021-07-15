package project.model;

import project.controller.MainMenuController;
import project.controller.ScoreboardData;
import project.model.card.Monster;
import project.model.card.informationofcards.MonsterActionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User implements Comparable<User> {
    private static final ArrayList<User> allUsers;

    static {

        allUsers = new ArrayList<>();
        User user = new User("ali", "ali", "ali");
        user.score = 1000;
        new ScoreboardData(user.nickname, user.score, false);
        User user1 = new User("mmd", "mmd", "mmd");
        user1.score = 2000;
        new ScoreboardData(user1.nickname, user1.score, false);
        User ai = new User("ai", "", "ai");
        Assets assets = Assets.getAssetsByUsername("ai");
        Objects.requireNonNull(assets).createDeck("aiDeck");
        Deck deck = assets.getDeckByDeckName("aiDeck");
        ArrayList<Monster> allMonsters = Monster.getAllMonsters();
        for (int i = 0; i < 3; i++) {
            for (Monster monster : allMonsters) {
                if (monster.getMonsterActionType() == MonsterActionType.NORMAL && monster.getLevel() <= 4)
                    assets.addCardToMainDeck(monster, deck);
            }
        }
        assets.activateDeck("aiDeck");
    }

    private String username;
    private String password;
    private String nickname;
    private boolean hasActiveDeck;
    private int score;
    private URL avatar;

    {

        hasActiveDeck = false;
        setAvatarURL(Avatar.AVATAR_1.getUrl());
    }

    public User(String username, String password, String nickname) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        new Assets(username);
        allUsers.add(this);
    }

    public static Deck getActiveDeckByUsername(String username) {
        List<Deck> decks = Objects.requireNonNull(Objects.requireNonNull(Assets.getAssetsByUsername(username)).getAllDecks());
        for (Deck deck : decks) {
            if (deck.isActivated()) return deck;
        }
        return null;
    }

    public static Deck getActiveDeckByNickName(String nickname) {
        List<Deck> decks = Objects.requireNonNull(Objects.requireNonNull(Assets.getAssetsByUsername(getUserByNickName(nickname).getUsername())).getAllDecks());
        for (Deck deck : decks) {
            if (deck.isActivated()) return deck;
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static User getUserByUsername(String username) {
        synchronized (allUsers) {
            for (User user : allUsers)
                if (user.username.equals(username)) return user;
            return null;
        }
    }

    public static User getUserByNickName(String nickname) {
        for (User user : allUsers) {
            if (user.nickname.equals(nickname)) return user;
        }
        return null;
    }

    public static ArrayList<User> sortAllUsers() {
        ArrayList<User> usersWithoutAi = new ArrayList<>();
        for (User allUser : allUsers) {
            if (allUser.getUsername().equals("ai"))
                continue;
            usersWithoutAi.add(allUser);
        }
        Collections.sort(usersWithoutAi);
        return usersWithoutAi;
    }

    public URL getAvatarURL() {
        return avatar;
    }

    public void setAvatarURL(URL avatar) {
        this.avatar = avatar;
    }

    public void activatedDeck() {
        hasActiveDeck = true;
    }

    public void deactivatedDeck() {
        hasActiveDeck = false;
    }

    public String getNickname() {
        return nickname;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
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

    public void changeNickname(String newNickname) {
        setNickname(newNickname);
    }

    public void changePassword(String newPassword) {
        setPassword(newPassword);
    }

    @Override
    public int compareTo(User user) {
        if (score != user.score) return user.score - score;
        return nickname.compareTo(user.nickname);
    }

    @Override
    public String toString() {
        return "User: " +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'';
    }

    public void increaseScore(int score) {
        this.score += score;
        ScoreboardData.changeScore(nickname, this.score);
        MainMenuController.getInstance().sendScoreboardDate();
    }

}
