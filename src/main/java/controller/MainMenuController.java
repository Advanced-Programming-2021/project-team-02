package controller;

import model.User;
import view.MainMenuView;
import view.Menu;
import view.MenusManager;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.Locale;

public class MainMenuController {
    private static User loggedInUser;
    private static MainMenuController instance = null;
    private final MainMenuView view = MainMenuView.getInstance ();

    private MainMenuController (){}

    public static MainMenuController getInstance() {
        if (instance == null) instance = new MainMenuController ();
        return instance;
    }

    public void setLoggedInUser(User loggedInUser) {
        MainMenuController.loggedInUser = loggedInUser;
    }

    public void menuEnter(String menuName) {
        menuName = menuName.toLowerCase(Locale.ROOT);
        switch (menuName) {
            case "login":
                SuccessMessage.showSuccessMessage (SuccessMessage.LOGOUT);
                MenusManager.getInstance ().setLoggedInUser (null);
                MenusManager.getInstance ().changeMenu (Menu.LOGIN_MENU);
                break;
            case "deck":
                MenusManager.getInstance ().changeMenu (Menu.DECK_MENU);
                break;
            case "scoreboard":
                MenusManager.getInstance ().changeMenu (Menu.SCOREBOARD_MENU);
                break;
            case "shop":
                ShopMenuController.getInstance ().setLoggedInUser (loggedInUser);
                MenusManager.getInstance ().changeMenu (Menu.SHOP_MENU);
                break;
            case "profile":
                ProfileMenuController.getInstance ().setLoggedInUser (MainMenuController.getInstance ().getLoggedInUser ());
                MenusManager.getInstance ().changeMenu (Menu.PROFILE_MENU);
                break;
            case "duel":
                MenusManager.getInstance ().changeMenu (Menu.DUEL_MENU);
                break;
            default: Error.showError (Error.INVALID_COMMAND);
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}