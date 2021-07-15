package project.controller;

import com.google.gson.Gson;
import project.ServerMainController;
import project.model.Assets;
import project.model.Shop;
import project.model.card.Card;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class ShopController {
    private static ShopController instance = null;

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (instance == null) instance = new ShopController();
        return instance;
    }

    public String buyCard(String cardName, String username, String token) {
        System.out.println(cardName);
        LinkedHashMap<String, Integer> cardsLinkedToNumber = Shop.getInstance().getCardsWithNumberOfThem();
        LinkedHashMap<String, Integer> cardsWithPrices = Shop.getInstance().getCardsWithPrices();
        if (cardsLinkedToNumber.get(cardName) == 0)
            return "not enough cards";
        else if (cardsLinkedToNumber.get(cardName) == -1)
            return "forbidden card!";

        synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
            if (cardsLinkedToNumber.get(cardName) != 0 && cardsLinkedToNumber.get(cardName) != -1) {
                Assets assets = Assets.getAssetsByUsername(username);
                Objects.requireNonNull(assets).decreaseCoin(cardsWithPrices.get(cardName));
                assets.addBoughtCard(Card.getCardByName(cardName));
                cardsLinkedToNumber.replace(cardName, cardsLinkedToNumber.get(cardName) - 1);
                HashMap<String, DataOutputStream> map = ServerMainController.getDataTransferForShopCards();
                sendShopDataAndBuyerAssetsToRelatedClients(token, map, cardsLinkedToNumber);
                return "success";
            } else {
                if (cardsLinkedToNumber.get(cardName) == 0)
                    return "not enough cards";
                else if (cardsLinkedToNumber.get(cardName) == -1)
                    return "forbidden card!";
            }
        }
        return "error!";
    }

    public String sellCard(String cardName, String username, String token) {
        LinkedHashMap<String, Integer> cardsLinkedToNumber = Shop.getInstance().getCardsWithNumberOfThem();
        HashMap<String, DataOutputStream> map = ServerMainController.getDataTransferForShopCards();
        synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
            cardsLinkedToNumber.replace(cardName, cardsLinkedToNumber.get(cardName) + 1);
        }
        Assets assets = Assets.getAssetsByUsername(username);
        Objects.requireNonNull(assets).sellCard(cardName);
        sendShopDataAndBuyerAssetsToRelatedClients(token, map, cardsLinkedToNumber);

        return "success";
    }

    private void sendShopDataAndBuyerAssetsToRelatedClients(String token, HashMap<String, DataOutputStream> map, LinkedHashMap<String, Integer> cardsLinkedToNumber) {
        try {
            for (String s : map.keySet()) {
                map.get(s).writeUTF(new Gson().toJson(Shop.getInstance().getCardsWithNumberOfThem()));
                map.get(s).flush();
            }
            ServerMainController.getDataTransferForAssets().get(token).writeUTF(new Gson().toJson(Assets.getAssetsByUsername(ServerMainController.getLoggedInUsers().get(token).getUsername())));
            ServerMainController.getDataTransferForAssets().get(token).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
