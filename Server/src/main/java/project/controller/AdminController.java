package project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import project.ServerMainController;
import project.model.Shop;

import java.io.IOException;
import java.util.HashMap;

public class AdminController {
    private static AdminController instance = null;

    public static AdminController getInstance() {
        if (instance == null)
            instance = new AdminController();
        return instance;
    }


    public void updateAdminData(String gson) {
        synchronized (ServerMainController.getDataTransferForShopCards()) {
            for (String s : ServerMainController.getDataTransferForShopCards().keySet()) {
                try {
                    ServerMainController.getDataTransferForShopCards().get(s).writeUTF(gson);
                    ServerMainController.getDataTransferForShopCards().get(s).flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                System.out.println("sent for admin? ");
                ServerMainController.getAdminOutput().writeUTF(gson);
                ServerMainController.getAdminOutput().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HashMap<String, Integer> map = new Gson().fromJson(gson, new TypeToken<HashMap<String, Integer>>() {
            }.getType());
            for (String s : map.keySet()) {
                Shop.getInstance().getCardsWithNumberOfThem().replace(s, map.get(s));
            }
        }
    }
}
