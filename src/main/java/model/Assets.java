package model;

import com.google.gson.Gson;
import model.card.Card;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Assets {
    private String username;
    private int coin;
    private final HashMap<Card, Integer> allUserCards;
    private final ArrayList<Deck> allDecks;
    private static final HashMap<String, Assets> allAssets;
    static Writer writer;
    static Gson gson = new Gson();

    {
        try {
            writer = Files.newBufferedWriter(Paths.get("assets.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        allAssets = new HashMap<>();
    }

    {
        coin = 10000000;
    }

    public Assets(String username) {
        setUsername(username);
        allDecks = new ArrayList<>();
        allUserCards = new HashMap<>();
        allAssets.put(username, this);
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public void increaseCoin(int coin) {
        this.coin += coin;
    }

    public Deck getDeckByDeckName(String name) {
        for (Deck deck : allDecks)
            if (deck.getName().equals(name)) return deck;
        return null;
    }

    public HashMap<Card, Integer> getAllUserCards() {
        return allUserCards;
    }

    public ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

    public int getCoin() {
        return coin;
    }

    public static Assets getAssetsByUsername(String username) {
        for (String key : allAssets.keySet())
            if (key.equals(username)) return allAssets.get(key);
        return null;
    }

    public void createDeck(String name) {
        this.allDecks.add(new Deck(name));
    }

    public void deleteDeck(String name) {
        Deck deck = getDeckByDeckName(name);
        if (deck.isActivated())
            Objects.requireNonNull(User.getUserByUsername(username)).deactivatedDeck();
        for (Card card : allUserCards.keySet()) {
            for (Card mainCard : deck.getMainCards()) {
                if (card.getName().equals(mainCard.getName()))
                    allUserCards.replace(card, allUserCards.get(card) + 1);
                for (Card sideCard : deck.getSideCards()) {
                    if (card.getName().equals(sideCard.getName()))
                        allUserCards.replace(card, allUserCards.get(card) + 1);
                }
            }
        }
        allDecks.remove(deck);
        HashMap<String, Assets> allAssets = Assets.allAssets;
        try {

            PrintWriter printWriter = new PrintWriter("assets.json");
            printWriter.print("");
            Writer writer = null;
            try {
                writer = Files.newBufferedWriter(Paths.get("assets.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson.toJson(allAssets, writer);
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void activateDeck(String deckName) {
        for (Deck oneOfDecks : allDecks) {
            if (oneOfDecks.isActivated()) {
                oneOfDecks.setActivated(false);
            }
        }
        Deck deck = getDeckByDeckName(deckName);
        deck.setActivated(true);
        Objects.requireNonNull(User.getUserByUsername(username)).activatedDeck();
    }

    public void addCardToMainDeck(Card card, Deck deck) {
        deck.addCardToMainDeck(card);
        HashMap<String, Assets> allAssets = Assets.allAssets;
        try {
            PrintWriter printWriter = new PrintWriter("assets.json");
            printWriter.print("");
            Writer writer = null;
            try {
                writer = Files.newBufferedWriter(Paths.get("assets.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson.toJson(allAssets, writer);
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addCardToSideDeck(Card card, Deck deck) {
        deck.addCardToSideDeck(card);
        HashMap<String, Assets> allAssets = Assets.allAssets;
        try {
            PrintWriter printWriter = new PrintWriter("assets.json");
            printWriter.print("");
            Writer writer = null;
            try {
                writer = Files.newBufferedWriter(Paths.get("assets.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson.toJson(allAssets, writer);
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeCardFromMainDeck(Card card, Deck deck) {
        deck.removeCardFromMainDeck(card);
        HashMap<String, Assets> allAssets = Assets.allAssets;
        try {
            PrintWriter printWriter = new PrintWriter("assets.json");
            printWriter.print("");
            Writer writer = null;
            try {
                writer = Files.newBufferedWriter(Paths.get("assets.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson.toJson(allAssets, writer);
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeCardFromSideDeck(Card card, Deck deck) {
        deck.removeCardFromSideDeck(card);
        HashMap<String, Assets> allAssets = Assets.allAssets;
        try {
            PrintWriter printWriter = new PrintWriter("assets.json");
            printWriter.print("");
            Writer writer = null;
            try {
                writer = Files.newBufferedWriter(Paths.get("assets.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson.toJson(allAssets, writer);
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void decreaseCoin(int amount) {
        coin -= amount;
    }

    public void addCard(Card card) {
        for (Card cardsOfUser : allUserCards.keySet()) {
            if (cardsOfUser.getName().equals(card.getName())) {
                allUserCards.replace(card, allUserCards.get(card) + 1);
                return;
            }
        }
        allUserCards.put(card, 1);
        HashMap<String, Assets> allAssets = Assets.allAssets;
        try {
            PrintWriter printWriter = new PrintWriter("assets.json");
            printWriter.print("");
            Writer writer = null;
            try {
                writer = Files.newBufferedWriter(Paths.get("assets.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson.toJson(allAssets, writer);
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfCards(Card card) {
        for (Card cardOfUser : allUserCards.keySet()) {
            if (cardOfUser.getName().equals(card.getName())) {
                return allUserCards.get(cardOfUser);
            }
        }
        return 0;
    }

    public static void jsonAssets() {
        try {
            gson.toJson(allAssets, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
