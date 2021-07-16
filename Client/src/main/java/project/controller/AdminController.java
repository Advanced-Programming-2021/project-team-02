package project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import project.model.Shop;
import project.view.AdminView;
import project.view.messages.AdminPanelMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;

public class AdminController {
    private static AdminController instance;
    private Socket dataSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private AdminView view;

    public static AdminController getInstance() {
        if (instance == null)
            instance = new AdminController();
        return instance;
    }

    public AdminView getView() {
        return view;
    }

    public void setView(AdminView view) {
        this.view = view;
    }

    public AdminPanelMessage initializeNetworkForAdmin() {
        try {
            dataSocket = new Socket("localhost", 8000);
            dataInputStream = new DataInputStream(dataSocket.getInputStream());
            dataOutputStream = new DataOutputStream(dataSocket.getOutputStream());
            dataOutputStream.writeUTF("admin");
            String result = dataInputStream.readUTF();
            if (result.equals("success")) {
                startThread();
                return AdminPanelMessage.SUCCESS;
            } else return AdminPanelMessage.ADMIN_ALREADY_LOGGED_IN;
        } catch (IOException e) {
            return AdminPanelMessage.ERROR_OCCURRED;
        }
    }

    private void startThread() {
        new Thread(() -> {
            while (true) {
                try {
                    String in = dataInputStream.readUTF();
                    if (in.equals("close"))
                        break;
                    synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
                        LinkedHashMap<String, Integer> mainMap = Shop.getInstance().getCardsWithNumberOfThem();
                        LinkedHashMap<String, Integer> map = new Gson().fromJson(in, new TypeToken<LinkedHashMap<String, Integer>>() {
                        }.getType());
                        System.out.println("updated : " + in);
                        for (String s : map.keySet()) {
                            mainMap.replace(s, map.get(s));
                        }
                        Platform.runLater(view::setCards);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                dataOutputStream.close();
                dataInputStream.close();
                dataSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public AdminPanelMessage increaseCardInventory(String cardName) {
        synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
            Shop.getInstance().getCardsWithNumberOfThem().replace(cardName, Shop.getInstance().getCardsWithNumberOfThem().get(cardName) + 1);
            System.out.println(Shop.getInstance().getCardsWithNumberOfThem().get(cardName));
            try {
                String gson = new Gson().toJson(Shop.getInstance().getCardsWithNumberOfThem());
                dataOutputStream.writeUTF(gson);
                dataOutputStream.flush();
                return AdminPanelMessage.INCREASED_SUCCESSFULLY;
            } catch (IOException e) {
                return AdminPanelMessage.ERROR_OCCURRED;
            }
        }
    }

    public AdminPanelMessage decreaseCardInventory(String cardName) {
        synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
            if (Shop.getInstance().getCardsWithNumberOfThem().get(cardName) == 0)
                return AdminPanelMessage.NOTHING_TO_DECREASE;
            else {
                Shop.getInstance().getCardsWithNumberOfThem().replace(cardName, Shop.getInstance().getCardsWithNumberOfThem().get(cardName) - 1);
                try {
                    dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().getCardsWithNumberOfThem()));
                    dataOutputStream.flush();
                    return AdminPanelMessage.DECREASED_SUCCESSFULLY;
                } catch (IOException e) {
                    return AdminPanelMessage.ERROR_OCCURRED;
                }
            }
        }
    }

    public AdminPanelMessage forbidCard(String cardName) {
        synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
            Shop.getInstance().getCardsWithNumberOfThem().replace(cardName, -1);
            try {
                dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().getCardsWithNumberOfThem()));
                dataOutputStream.flush();
                return AdminPanelMessage.FORBID_CARD_SUCCESSFULLY;
            } catch (IOException e) {
                return AdminPanelMessage.ERROR_OCCURRED;
            }
        }
    }

    public AdminPanelMessage availableCard(String cardName) {
        synchronized (Shop.getInstance().getCardsWithNumberOfThem()) {
            Shop.getInstance().getCardsWithNumberOfThem().replace(cardName, 0);
            try {
                dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().getCardsWithNumberOfThem()));
                dataOutputStream.flush();
                return AdminPanelMessage.MADE_CARD_AVAILABLE;
            } catch (IOException e) {
                return AdminPanelMessage.ERROR_OCCURRED;
            }
        }
    }

    public void close() {
        try {
            dataOutputStream.writeUTF("close");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

