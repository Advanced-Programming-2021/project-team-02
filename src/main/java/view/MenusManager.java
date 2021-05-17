package view;

import controller.DeckMenuController;
import controller.LoginMenuController;
import controller.MainMenuController;
import controller.ShopMenuController;
import controller.playgame.DuelGameController;
import controller.playgame.RoundGameController;
import model.User;
import view.gameview.GameView;
import view.input.Input;

public class MenusManager {
    private static MenusManager instance = null;
    private Menu currentMenu = Menu.LOGIN_MENU;
    private User loggedInUser = null;

    private MenusManager() {

    }

    public static MenusManager getInstance() {
        if (instance == null) instance = new MenusManager();
        return instance;
    }

    public void run() throws CloneNotSupportedException {
        String command;
        //initializeSingleton();
        while (currentMenu != Menu.EXIT) {
            command = Input.getInput();
            if (currentMenu.equals(Menu.LOGIN_MENU)) {
                LoginMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.MAIN_MENU)) {
                MainMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.PROFILE_MENU)) {
                ProfileMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.DECK_MENU)) {
                DeckMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.SHOP_MENU)) {
                ShopMenuView.getInstance().run(command);
            } else if (currentMenu.equals (Menu.DUEL_MENU)) {
                DuelMenuView.getInstance ().run (command);
            } else if (currentMenu.equals (Menu.SCOREBOARD_MENU)) {
                ScoreBoardView.getInstance ().run (command);
            } else if (currentMenu.equals(Menu.ONGOING_GAME)){
                GameView.getInstance().run(command);
            }
        }
    }

    public void changeMenu(Menu newMenu) {
        currentMenu = newMenu;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
//    private void initializeSingleton(){
//        DuelMenuView.getInstance();
//        DeckMenuController.getInstance();
//        LoginMenuView.getInstance();
//        LoginMenuController.getInstance();
//        MainMenuView.getInstance();
//        MainMenuController.getInstance();
//        ShopMenuView.getInstance();
//        ScoreBoardView.getInstance();
//        ShopMenuController.getInstance();
//        DuelGameController.getInstance();
//        GameView.getInstance();
//        RoundGameController.getInstance();
//    }
}
