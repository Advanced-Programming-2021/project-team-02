package project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import project.model.Assets;
import project.model.Shop;
import project.model.User;
import project.view.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ControllerManager {
    private static ControllerManager instance = null;
    private Socket reqSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket scoreBoardDataTransferSocket;
    private DataInputStream dataTransferScoreboardInputStream;
    private DataOutputStream dataTransferScoreboardOutputStream;

    private ControllerManager() {

    }

    public static ControllerManager getInstance() {
        if (instance == null)
            instance = new ControllerManager();
        return instance;
    }

    public void setReqSocket(Socket socket) {
        try {
            this.reqSocket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("request");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public User askForLoggedInUser() {
        try {
            dataOutputStream.writeUTF("ask user " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            String gson = dataInputStream.readUTF();
            System.out.println(gson);
            User user = new Gson().fromJson(gson, User.class);
            System.out.println(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Assets getAUserAssets(String username) {
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        try {
            System.out.println(username + " to get data of asset");
            dataOutputStream.writeUTF("ask asset " + username);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        Assets assets = null;
        try {
            String gson = dataInputStream.readUTF();
            System.out.println(gson);
            assets = new Gson().fromJson(gson, Assets.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assets;
    }

    public void getLastShopData() {
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        try {

            dataOutputStream.writeUTF("ask shop");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        LinkedHashMap<String, Integer> cards = null;
        try {
            String gson = dataInputStream.readUTF();
            System.out.println(gson);
            cards = new Gson().fromJson(gson, new TypeToken<LinkedHashMap<String, Integer>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinkedHashMap<String, Integer> shopCards = (LinkedHashMap<String, Integer>) Shop.getInstance().getCardsWithNumberOfThem();
        for (String card : shopCards.keySet()) {
            shopCards.replace(card, cards.get(card));
        }
    }

    public void getLastProfileData() {
        try {
            dataOutputStream.writeUTF("ask user " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            String gson = dataInputStream.readUTF();
            System.out.println(gson);
            User user = new Gson().fromJson(gson, User.class);
            MainMenuController.getInstance().setLoggedInUser(user);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setScoreBoardSocket(Socket socket, String token) {
        scoreBoardDataTransferSocket = socket;
        try {
            dataTransferScoreboardOutputStream = new DataOutputStream(socket.getOutputStream());
            dataTransferScoreboardInputStream = new DataInputStream(socket.getInputStream());
            dataTransferScoreboardOutputStream.writeUTF("data_transfer_scoreboard " + token);
            dataTransferScoreboardOutputStream.flush();
            startThreadOfScoreboardDataTransfer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startThreadOfScoreboardDataTransfer() {
        new Thread(() -> {
            try {
                while (true) {
                    String in = dataTransferScoreboardInputStream.readUTF();
//                    if (in.matches("shop .+")) {
//                        in = in.replaceFirst("shop ", "");
//                        LinkedHashMap<String, Integer> mainMap = Shop.getInstance().getCardsWithNumberOfThem();
//                        LinkedHashMap<String, Integer> map = new Gson().fromJson(in, new TypeToken<LinkedHashMap<String, Integer>>() {
//                        }.getType());
//                        for (String s : map.keySet()) {
//                            mainMap.replace(s, map.get(s));
//                        }
//                        ShopMenuView shopMenuView = ShopMenuController.getInstance().getView();
//                        Platform.runLater(shopMenuView::setCards);
//                    } else
//                        if (in.matches("asset .+")) {
//                        in = in.replaceFirst("asset ", "");
//                        Assets assets = new Gson().fromJson(in, Assets.class);
//                        MainMenuController.getInstance().updateLoggedInAsset(assets);
//                        ShopMenuView shopMenuView = ShopMenuController.getInstance().getView();
//                        Platform.runLater(shopMenuView::setCards);
//                    } else
                    if (in.matches(".+")) {
                        in = in.replaceFirst("scoreboard ", "");
                        ArrayList<ScoreboardData> arrayList = new Gson().fromJson(in, new TypeToken<ArrayList<ScoreboardData>>() {
                        }.getType());
                        ScoreboardData.setDataArrayList(arrayList);
                        ScoreBoardView view = MainMenuController.getInstance().getScoreBoard();
                        if (view != null)
                            Platform.runLater(view::showBoard);
                    }
//                    } else if (in.matches("profile .+")) {
//                        in = in.replaceFirst("profile ", "");
//                        User user = new Gson().fromJson(in, User.class);
//                        MainMenuController.getInstance().setLoggedInUser(user);
//                        ProfileMenuView profileMenuView = MainMenuController.getInstance().getProfileMenuView();
//                        if (profileMenuView != null)
//                            Platform.runLater(profileMenuView::setProfileData);
//                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
