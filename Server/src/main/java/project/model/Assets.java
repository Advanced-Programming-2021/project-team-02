package project.model;

import project.model.card.Card;

import java.util.*;

public class Assets {
    private static HashMap<String, Assets> allAssets;

    static {
        allAssets = new HashMap<>();
    }

    private final HashMap<String, Integer> allUserCards;
    private final ArrayList<Deck> allDecks;
    private String username;
    private int coin;

    {
    }

    {
        coin = 25000;
    }

    public Assets(String username) {
        setUsername(username);
        allDecks = new ArrayList<>();
        allUserCards = new HashMap<>();
        allAssets.put(username, this);
    }

    public static Assets getAssetsByUsername(String username) {
        for (String key : allAssets.keySet())
            if (key.equals(username)) return allAssets.get(key);
        return null;
    }
    public void sellCard(String card) {
        for (String cardsOfUser : allUserCards.keySet()) {
            if (cardsOfUser.equals(card)) {
                allUserCards.replace(cardsOfUser, allUserCards.get(cardsOfUser) - 1);
                coin += Shop.getInstance().getCardsWithPrices().get(card);
                return;
            }
        }
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

    public HashMap<String, Integer> getAllUserCards() {
        return allUserCards;
    }

    public ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

    public int getCoin() {
        return coin;
    }

    public void createDeck(String name) {
        this.allDecks.add(new Deck(name));
    }

    public void deleteDeck(String name) {
        System.out.println("HI" + name);
        Deck deck = getDeckByDeckName(name);
        if (deck.isActivated())
            Objects.requireNonNull(User.getUserByUsername(username)).deactivatedDeck();
        allDecks.remove(deck);
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
    }

    public void addCardToSideDeck(Card card, Deck deck) {
        deck.addCardToSideDeck(card);
    }
//TODO
//    public DeckMenuMessage removeCardFromMainDeck(int index, Deck deck) {
//        return deck.removeCardFromMainDeck(index);
////        try {
////            PrintWriter printWriter = new PrintWriter("assets.json");
////            printWriter.print("");
////            Writer writer = null;
////            try {
////                writer = Files.newBufferedWriter(Paths.get("assets.json"));
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            gson.toJson(allAssets, writer);
////            try {
////                assert writer != null;
////                writer.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        }
//    }
//TODO
//    public DeckMenuMessage removeCardFromSideDeck(int index, Deck deck) {
//        return deck.removeCardFromSideDeck(index);
////        try {
////            PrintWriter printWriter = new PrintWriter("assets.json");
////            printWriter.print("");
////            Writer writer = null;
////            try {
////                writer = Files.newBufferedWriter(Paths.get("assets.json"));
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            gson.toJson(allAssets, writer);
////            try {
////                assert writer != null;
////                writer.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        }
//    }

    public void decreaseCoin(int amount) {
        coin -= amount;
    }

    public void addBoughtCard(Card card) {
        for (String cardsOfUser : allUserCards.keySet()) {
            if (cardsOfUser.equals(card.getName())) {
                allUserCards.replace(cardsOfUser, allUserCards.get(cardsOfUser) + 1);
                return;
            }
        }
        allUserCards.put(card.getName(), 1);
    }

    public int getNumberOfCards(Card card) {
        for (String cardOfUser : allUserCards.keySet()) {
            if (cardOfUser.equals(card.getName())) {
                return allUserCards.get(cardOfUser);
            }
        }
        return 0;
    }
}
