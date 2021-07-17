package project.controller;

import com.google.gson.Gson;
import project.ServerMainController;
import project.model.Assets;
import project.model.User;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DeckMenuController {
    private static DeckMenuController instance = null;

    private DeckMenuController() {

    }

    public static DeckMenuController getInstance() {
        if (instance == null) {
            instance = new DeckMenuController();
        }
        return instance;
    }

    public String createDeck(String deckName, String token) {
        User user = ServerMainController.getLoggedInUsers().get(token);
        String username = user.getUsername();
        Assets assets = Assets.getAssetsByUsername(username);
        if (assets.getDeckByDeckName(deckName) != null) {
            return "exists";
        }
        assets.createDeck(deckName);


        return updateDeckAssets(token, username);
    }

    public String deleteDeck(String deckName, String token) {
        User user = ServerMainController.getLoggedInUsers().get(token);
        String username = user.getUsername();


        Assets assets = Assets.getAssetsByUsername(username);
        if (assets.getDeckByDeckName(deckName) == null) {
            return "not_exists";
        }
        assets.deleteDeck(deckName);

        return updateDeckAssets(token, username);
    }

    public String activateDeck(String deckName,String token) {
        User user = ServerMainController.getLoggedInUsers().get(token);
        String username = user.getUsername();


        Assets assets = Assets.getAssetsByUsername(username);
        if (assets.getDeckByDeckName(deckName) == null) {
            return "not_exists";
        }
        assets.activateDeck(deckName);

        return updateDeckAssets(token, username);
    }

    private String updateDeckAssets(String token, String username) {
        DataOutputStream dataOutputStream = ServerMainController.getDeckDataTransfer().get(token);
        System.out.println(token);
        try {
            String gson = new Gson().toJson(Assets.getAssetsByUsername(username));
            System.out.println(gson);
            dataOutputStream.writeUTF(gson);
            dataOutputStream.flush();
        } catch (IOException e) {
            return "failed";
        }
        return "success";
    }
}
