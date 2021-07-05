package project.model;

import project.model.card.Card;
import project.view.messages.DeckMenuMessage;

import java.util.*;

public class Assets {
    private static HashMap<String, Assets> allAssets;
    //private static Writer writer;
    //private static Gson gson = new Gson();

    static {
        allAssets = new HashMap<>();
    }

    private final HashMap<Card, Integer> allUserCards;
    private final ArrayList<Deck> allDecks;
    private String username;
    private int coin;

    {
//        try {
//            writer = Files.newBufferedWriter(Paths.get("assets.json"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

    public static Assets getAssetsByUsername(String username) {
        for (String key : allAssets.keySet())
            if (key.equals(username)) return allAssets.get(key);
        return null;
    }

//    public static void jsonAssets() {
//        try {
//            gson.toJson(allAssets, writer);
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void fromJson() {
//        Reader reader = null;
//        try {
//            reader = Files.newBufferedReader(Paths.get("assets.json"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assert reader != null;
//        allAssets = gson.fromJson(reader, HashMap.class);
//        try {
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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

    public void createDeck(String name) {
        this.allDecks.add(new Deck(name));
//        try {
//            PrintWriter printWriter = new PrintWriter("assets.json");
//            printWriter.print("");
//            Writer writer = null;
//            try {
//                writer = Files.newBufferedWriter(Paths.get("assets.json"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            gson.toJson(allAssets, writer);
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void deleteDeck(String name) {
        System.out.println("HI" + name);
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
//        try {
//
//            PrintWriter printWriter = new PrintWriter("assets.json");
//            printWriter.print("");
//            Writer writer = null;
//            try {
//                writer = Files.newBufferedWriter(Paths.get("assets.json"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            gson.toJson(allAssets, writer);
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
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
//        try {
//            PrintWriter printWriter = new PrintWriter("assets.json");
//            printWriter.print("");
//            Writer writer = null;
//            try {
//                writer = Files.newBufferedWriter(Paths.get("assets.json"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            gson.toJson(allAssets, writer);
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void addCardToSideDeck(Card card, Deck deck) {
        deck.addCardToSideDeck(card);
//        try {
//            PrintWriter printWriter = new PrintWriter("assets.json");
//            printWriter.print("");
//            Writer writer = null;
//            try {
//                writer = Files.newBufferedWriter(Paths.get("assets.json"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            gson.toJson(allAssets, writer);
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public DeckMenuMessage removeCardFromMainDeck(int index, Deck deck) {
        return deck.removeCardFromMainDeck(index);
//        try {
//            PrintWriter printWriter = new PrintWriter("assets.json");
//            printWriter.print("");
//            Writer writer = null;
//            try {
//                writer = Files.newBufferedWriter(Paths.get("assets.json"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            gson.toJson(allAssets, writer);
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public DeckMenuMessage removeCardFromSideDeck(int index, Deck deck) {
        return deck.removeCardFromSideDeck(index);
//        try {
//            PrintWriter printWriter = new PrintWriter("assets.json");
//            printWriter.print("");
//            Writer writer = null;
//            try {
//                writer = Files.newBufferedWriter(Paths.get("assets.json"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            gson.toJson(allAssets, writer);
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void decreaseCoin(int amount) {
        coin -= amount;
    }

    public void addCard(Card card) {
        for (Card cardsOfUser : allUserCards.keySet()) {
            if (cardsOfUser.getName().equals(card.getName())) {
                allUserCards.replace(cardsOfUser, allUserCards.get(cardsOfUser) + 1);
                return;
            }
        }
        allUserCards.put(card, 1);
//        try {
//            PrintWriter printWriter = new PrintWriter("assets.json");
//            printWriter.print("");
//            Writer writer = null;
//            try {
//                writer = Files.newBufferedWriter(Paths.get("assets.json"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            gson.toJson(allAssets, writer);
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public int getNumberOfCards(Card card) {
        for (Card cardOfUser : allUserCards.keySet()) {
            if (cardOfUser.getName().equals(card.getName())) {
                return allUserCards.get(cardOfUser);
            }
        }
        return 0;
    }
}
