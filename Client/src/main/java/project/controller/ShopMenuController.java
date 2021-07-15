package project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import project.model.Assets;
import project.model.Shop;
import project.model.card.Card;
import project.view.ShopMenuView;
import project.view.messages.ShopMenuMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Objects;

public class ShopMenuController {
    private static ShopMenuController instance = null;
    private ShopMenuView view;
    private Socket shopSocket;
    private DataOutputStream shopDataOutPutStream;
    private DataInputStream shopDataInputStream;
    private Socket assetsSocket;
    private DataOutputStream assetsDataOutputStream;
    private DataInputStream assetsDataInputStream;

    private ShopMenuController() {
    }

    public static ShopMenuController getInstance() {
        if (instance == null) instance = new ShopMenuController();
        return instance;
    }

    public void initializeNetWorkForTransferShopData() {
        try {
            shopSocket = new Socket("localhost", 8000);
            shopDataInputStream = new DataInputStream(shopSocket.getInputStream());
            shopDataOutPutStream = new DataOutputStream(shopSocket.getOutputStream());
            shopDataOutPutStream.writeUTF("data_transfer_shop " + MainMenuController.getInstance().getLoggedInUserToken());
            shopDataOutPutStream.flush();

            assetsSocket = new Socket("localhost", 8000);
            assetsDataInputStream = new DataInputStream(assetsSocket.getInputStream());
            assetsDataOutputStream = new DataOutputStream(assetsSocket.getOutputStream());
            assetsDataOutputStream.writeUTF("data_transfer_asset " + MainMenuController.getInstance().getLoggedInUserToken());
            assetsDataOutputStream.flush();
            startReceiverThreadForAssets(assetsDataInputStream);
            startReceiverThreadForShop(shopDataInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReceiverThreadForShop(DataInputStream dataInputStream) {
        new Thread(() -> {
            try {
                while (true) {
                    String in = dataInputStream.readUTF();
                    if (in.equals("close"))
                        break;
                    LinkedHashMap<String, Integer> mainMap = Shop.getInstance().getCardsWithNumberOfThem();
                    LinkedHashMap<String, Integer> map = new Gson().fromJson(in, new TypeToken<LinkedHashMap<String, Integer>>() {
                    }.getType());
                    for (String s : map.keySet()) {
                        mainMap.replace(s, map.get(s));
                    }
                    Platform.runLater(view::setCards);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            shopDataInputStream.close();
            shopDataOutPutStream.close();
            shopSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReceiverThreadForAssets(DataInputStream dataInputStream) {
        new Thread(() -> {
            try {
                while (true) {
                    String in = dataInputStream.readUTF();
                    if (in.equals("close"))
                        break;
                    Assets assets = new Gson().fromJson(in, Assets.class);
                    MainMenuController.getInstance().updateLoggedInAsset(assets);
                    ShopMenuView shopMenuView = ShopMenuController.getInstance().getView();
                    Platform.runLater(shopMenuView::setCards);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            assetsDataOutputStream.close();
            assetsDataInputStream.close();
            assetsSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ShopMenuMessage buyCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        if (Objects.requireNonNull(assets).getCoin() < Shop.getInstance().getCardsWithPrices().get(cardName)) {
            return ShopMenuMessage.NOT_ENOUGH_MONEY;
        }
        String result = "";
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        try {
            dataOutputStream.writeUTF("shop buy <" + cardName + "> " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("result : " + result);

        switch (result) {
            case "success":
                Objects.requireNonNull(assets).decreaseCoin(Shop.getInstance().getCardsWithPrices().get(cardName));
                assets.addBoughtCard(card);
                return ShopMenuMessage.CARD_ADDED;
            case "not enough cards":
                return ShopMenuMessage.NOT_ENOUGH_CARD;
            case "forbidden card":
                return ShopMenuMessage.FORBIDDEN_CARD;
        }


        return ShopMenuMessage.CARD_ADDED;
    }

    public ShopMenuMessage sellCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        Assets assets = MainMenuController.getInstance().getLoggedInUserAssets();
        String result = "";
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        try {
            dataOutputStream.writeUTF("shop sell <" + cardName + "> " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("result : " + result);

        switch (result) {
            case "success":
                assets.sellCard(cardName);
                return ShopMenuMessage.SUCCESS;
            case "not enough cards":
                return ShopMenuMessage.NOT_ENOUGH_CARD;
        }


        return ShopMenuMessage.CARD_ADDED;
    }

    public ShopMenuView getView() {
        return view;
    }

    public void setView(ShopMenuView view) {
        this.view = view;
    }

    public void closeShop() {
        try {
            ControllerManager.getInstance().getDataOutputStream().writeUTF("shop close " + MainMenuController.getInstance().getLoggedInUserToken());
            ControllerManager.getInstance().getDataOutputStream().flush();
            String result = ControllerManager.getInstance().getDataInputStream().readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
