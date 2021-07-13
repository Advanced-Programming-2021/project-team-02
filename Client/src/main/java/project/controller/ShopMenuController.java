package project.controller;

import project.model.Assets;
import project.model.Shop;
import project.model.card.Card;
import project.view.messages.ShopMenuMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ShopMenuController {
    private static ShopMenuController instance = null;

    private ShopMenuController() {
    }

    public static ShopMenuController getInstance() {
        if (instance == null) instance = new ShopMenuController();
        return instance;
    }

    public ShopMenuMessage buyCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        if (Objects.requireNonNull(assets).getCoin() < Shop.getInstance().getCards().get(cardName)) {
            return ShopMenuMessage.NOT_ENOUGH_MONEY;
        }
        String result = "";
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        try {
            dataOutputStream.writeUTF("shop buy <" + cardName + ">" + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (result) {
            case "success":
                Objects.requireNonNull(assets).decreaseCoin(Shop.getInstance().getCards().get(cardName));
                assets.addBoughtCard(card);
                ControllerManager.getInstance().getLastShopData();
                return ShopMenuMessage.CARD_ADDED;
            case "not enough cards":
                return ShopMenuMessage.NOT_ENOUGH_CARD;
            case "forbidden card":
                return ShopMenuMessage.FORBIDDEN_CARD;
        }


        return ShopMenuMessage.CARD_ADDED;
    }
}
