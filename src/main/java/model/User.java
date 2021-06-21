package model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.card.Card;
import model.card.Card;
import model.card.Monster;
import model.card.informationofcards.MonsterActionType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class User implements Comparable<User> {
    private static ArrayList<User> allUsers;
    private String username;
    private String password;
    private String nickname;
    private boolean hasActiveDeck;
    private int score;
    static FileWriter fileWriter;
    static Gson gson = new Gson();

    {
        try {
            fileWriter = new FileWriter("user.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        hasActiveDeck = false;
    }

    static {
        allUsers = new ArrayList<>();
        new User();
    }

    public User(String username, String password, String nickname) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        new Assets(username);
        allUsers.add(this);
        User.jsonUsers();
        Assets.jsonAssets();
    }

    private User() {
        this.username = "ai";
        this.password = "";
        this.nickname = "ai";
        allUsers.add(this);
        Assets assets = new Assets("ai");
        assets.createDeck("aiDeck");
        Deck deck = assets.getDeckByDeckName("aiDeck");
        ArrayList<Monster> allMonsters = Monster.getAllMonsters();
        for (int i = 0; i < 3; i++) {
            for (Monster monster : allMonsters) {
                if (monster.getMonsterActionType() == MonsterActionType.NORMAL && monster.getLevel() <= 4)
                    deck.addCardToMainDeck(Card.getCardByName(monster.getName()));
            }
        }
        deck.setActivated(true);
        this.activatedDeck();
    }

    public void activatedDeck() {
        hasActiveDeck = true;
        try {
            PrintWriter printWriter = new PrintWriter("user.json");
            printWriter.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("user.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(allUsers, writer);
        try {
            assert writer != null;
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            PrintWriter printWriter = new PrintWriter("user.json");
            printWriter.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("user.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(allUsers, writer);
        try {
            assert writer != null;
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(String newPassword) {
        setPassword(newPassword);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("user.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert printWriter != null;
        printWriter.print("");
        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("user.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(allUsers, writer);
        try {
            assert writer != null;
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        ArrayList<User> usersWithoutAi = new ArrayList<>();
        for (User allUser : allUsers) {
            if (allUser.getUsername().equals("ai"))
                continue;
            usersWithoutAi.add(allUser);
        }
        Collections.sort(usersWithoutAi);
        return usersWithoutAi;
    }

    @Override
    public String toString() {
        return "User: " +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'';
    }


    public static void jsonUsers() {
        try {
            PrintWriter printWriter = new PrintWriter("user.json");
            printWriter.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("user.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(allUsers, writer);
        try {
            assert writer != null;
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fromJson() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("user.json")));
            allUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void changeUsername(String newUsername){
        setUsername(newUsername);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("user.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert printWriter != null;
        printWriter.print("");
        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("user.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(allUsers, writer);
        try {
            assert writer != null;
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
