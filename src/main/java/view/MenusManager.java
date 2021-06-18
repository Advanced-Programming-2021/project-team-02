package view;

import controller.DeckMenuController;
import controller.LoginMenuController;
import controller.MainMenuController;
import controller.ShopMenuController;
import controller.playgame.BetweenRoundController;
import controller.playgame.DuelGameController;
import controller.playgame.RoundGameController;
import model.User;
import view.gameview.BetweenRoundView;
import view.gameview.GameView;
import view.input.Input;

import java.awt.*;

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

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void run() throws CloneNotSupportedException {
        String command;
        while (currentMenu != Menu.EXIT) {

            if (currentMenu.equals(Menu.LOGIN_MENU)) {
                command = Input.getInput();
                LoginMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.MAIN_MENU)) {
                command = Input.getInput();
                MainMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.PROFILE_MENU)) {
                command = Input.getInput();
                ProfileMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.DECK_MENU)) {
                command = Input.getInput();
                DeckMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.SHOP_MENU)) {
                command = Input.getInput();
                ShopMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.DUEL_MENU)) {
                command = Input.getInput();
                DuelMenuView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.SCOREBOARD_MENU)) {
                command = Input.getInput();
                ScoreBoardView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.ONGOING_GAME)) {
                command = Input.getInput();
                GameView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.BETWEEN_ROUNDS)) {
                command = Input.getInput();
                BetweenRoundView.getInstance().run(command);
            } else if (currentMenu.equals(Menu.ONGOING_GAME_WITH_AI)) {
                GameView.getInstance().runGameWithAi();
            }
        }
    }

    public void changeMenu(Menu newMenu) {
        currentMenu = newMenu;
        if (currentMenu == Menu.ONGOING_GAME_WITH_AI || currentMenu == Menu.ONGOING_GAME || currentMenu == Menu.DUEL_MENU)
            return;
        System.out.println(currentMenu.getValue());
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

}
