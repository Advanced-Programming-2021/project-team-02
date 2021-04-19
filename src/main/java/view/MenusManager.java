package view;

import model.User;
import view.*;
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


    public void run() {
        String command;
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
                DuelMenuView.getInstance ().run ();
            } else if (currentMenu.equals (Menu.SCOREBOARD_MENU)) {
                ScoreBoardView.getInstance ().run (command);
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
}
