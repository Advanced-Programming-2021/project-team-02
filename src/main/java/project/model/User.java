package project.model;

import project.model.card.Monster;
import project.model.card.informationofcards.MonsterActionType;

import java.util.*;

public class User implements Comparable<User> {
    //static FileWriter fileWriter;
    //static Gson gson = new Gson();
    private static final ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
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

    {
//        try {
//            fileWriter = new FileWriter("user.json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        hasActiveDeck = false;
    }

    public User(String username, String password, String nickname) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        new Assets(username);
        allUsers.add(this);
        //User.jsonUsers();
        //Assets.jsonAssets();
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

//    public static void jsonUsers() {
//        try {
//            PrintWriter printWriter = new PrintWriter("user.json");
//            printWriter.print("");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Writer writer = null;
//        try {
//            writer = Files.newBufferedWriter(Paths.get("user.json"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gson.toJson(allUsers, writer);
//        try {
//            assert writer != null;
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void fromJson() {
//        try {
//            String json = new String(Files.readAllBytes(Paths.get("user.json")));
//            allUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
//            }.getType());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void activatedDeck() {
        hasActiveDeck = true;
//        try {
//            PrintWriter printWriter = new PrintWriter("user.json");
//            printWriter.print("");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Writer writer = null;
//        try {
//            writer = Files.newBufferedWriter(Paths.get("user.json"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gson.toJson(allUsers, writer);
//        try {
//            assert writer != null;
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
//        try {
//            PrintWriter printWriter = new PrintWriter("user.json");
//            printWriter.print("");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Writer writer = null;
//        try {
//            writer = Files.newBufferedWriter(Paths.get("user.json"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gson.toJson(allUsers, writer);
//        try {
//            assert writer != null;
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void changePassword(String newPassword) {
        setPassword(newPassword);
//        PrintWriter printWriter = null;
//        try {
//            printWriter = new PrintWriter("user.json");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        assert printWriter != null;
//        printWriter.print("");
//        Writer writer = null;
//        try {
//            writer = Files.newBufferedWriter(Paths.get("user.json"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gson.toJson(allUsers, writer);
//        try {
//            assert writer != null;
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
    }

    public void changeUsername(String newUsername) {
        setUsername(newUsername);
//        PrintWriter printWriter = null;
//        try {
//            printWriter = new PrintWriter("user.json");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        assert printWriter != null;
//        printWriter.print("");
//        Writer writer = null;
//        try {
//            writer = Files.newBufferedWriter(Paths.get("user.json"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gson.toJson(allUsers, writer);
//        try {
//            assert writer != null;
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
