package project.controller;

import com.google.gson.Gson;
import project.ServerMainController;
import project.model.Assets;
import project.model.Shop;
import project.model.User;
import project.model.card.Card;

import java.io.IOException;
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
                sendShopDataAndBuyerAssetsToRelatedClients(token);
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
        synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
            Shop.getInstance().getCardsWithNumberOfThem().replace(cardName, Shop.getInstance().getCardsWithNumberOfThem().get(cardName) + 1);
        }
        Assets assets = Assets.getAssetsByUsername(username);
        Objects.requireNonNull(assets).sellCard(cardName);
        sendShopDataAndBuyerAssetsToRelatedClients(token);

        return "success";
    }

    private void sendShopDataAndBuyerAssetsToRelatedClients(String token) {
        try {
            synchronized (ServerMainController.getDataTransferForShopCards()) {
                for (String s : ServerMainController.getDataTransferForShopCards().keySet()) {
                    System.out.println("send shop for : " + ServerMainController.getLoggedInUsers().get(s));
                    ServerMainController.getDataTransferForShopCards().get(s).writeUTF(new Gson().toJson(Shop.getInstance().getCardsWithNumberOfThem()));
                    ServerMainController.getDataTransferForShopCards().get(s).flush();
                }
                if (ServerMainController.isIsAdminLoggedIn()) {
                    ServerMainController.getAdminOutput().writeUTF(new Gson().toJson(Shop.getInstance().getCardsWithNumberOfThem()));
                    ServerMainController.getAdminOutput().flush();
                }
            }
            User user = ServerMainController.getLoggedInUsers().get(token);
            String username = user.getUsername();
            Assets assets = Assets.getAssetsByUsername(username);
            synchronized (ServerMainController.getDataTransferForAssetsInShop()) {
                ServerMainController.getDataTransferForAssetsInShop().get(token).writeUTF(new Gson().toJson(assets));
                ServerMainController.getDataTransferForAssetsInShop().get(token).flush();
//                for (String s : ServerMainController.getLoggedInUsers().keySet()) {
//                    if (ServerMainController.getLoggedInUsers().get(s).getNickname().equals(user.getNickname())) {
//                        if (ServerMainController.getDataTransferForAssetsInShop().containsKey(s)) {
//                            ServerMainController.getDataTransferForAssetsInShop().get(s).writeUTF(new Gson().toJson(assets));
//                        }
//                    }
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
