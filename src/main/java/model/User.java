package model;

import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class User extends ArrayList<User> implements Comparable<User> {
    private static final ArrayList<User> allUsers;
    private String username;
    private String password;
    private String nickname;
    private boolean hasActiveDeck;
    private int score;
    static FileWriter fileWriter;
    static Writer writer;
    static Gson gson = new Gson();

    {
        try {
            writer = Files.newBufferedWriter(Paths.get("user.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
    }

    public User(String username, String password, String nickname) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        new Assets(username);
        allUsers.add(this);
        jsonUsers();
        Assets.jsonAssets();
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
        try {
            PrintWriter printWriter = new PrintWriter("user.json");
            printWriter.print("");
            ArrayList<User> userArrayList = User.getAllUsers();
            try {
                fileWriter.write(new Gson().toJson(userArrayList));
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(String newPassword) {
        setPassword(newPassword);
        try {
            PrintWriter printWriter = new PrintWriter("user.json");
            printWriter.print("");
            ArrayList<User> arrayList = User.getAllUsers();
            try {
                fileWriter.write(new Gson().toJson(arrayList));
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
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

    public static void jsonUsers() {
        try {
            Gson gson = new Gson();
            Writer writer = Files.newBufferedWriter(Paths.get("user.json"));
            gson.toJson(allUsers.get(allUsers.size() - 1) , writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(allUsers, writer);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (User user : allUsers) {
            System.out.println(user.username);
        }
    }

//    public static void fromJson() {
//        try {
//            String json = new String(Files.readAllBytes(Paths.get("user.json")));
//           allUsers = new Gson().fromJson(json, new TypeToken<User>(){}.getType());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void increaseScore(int score) {
        this.score += score;
    }
}
