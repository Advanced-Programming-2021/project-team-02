package view;

import controller.ShopMenuController;
import model.Shop;
import model.card.Card;
import view.input.Input;
import view.input.Regex;
import view.messages.Error;

import java.util.regex.Matcher;

public class ShopMenuView {
    private static ShopMenuView instance = null;
    private static final ShopMenuController controller = ShopMenuController.getInstance();

    private ShopMenuView() {

    }

    public static ShopMenuView getInstance() {
        if (instance == null) instance = new ShopMenuView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if (Regex.getMatcher(Regex.MENU_ENTER, command).matches()) {
            showError(Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher(Regex.MENU_EXIT, command).matches()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if (Regex.getMatcher(Regex.MENU_SHOW_CURRENT, command).matches()) {
            showCurrentMenu();
        } else if ((matcher = Regex.getMatcher(Regex.SHOP_BUY, command)).matches()) {
            controller.buyCard(matcher);
        } else if (Regex.getMatcher(Regex.SHOP_SHOW_ALL, command).matches()) {
            showAllCards();
        } else showError(Error.INVALID_COMMAND);
    }

    public void showAllCards() {
        for (Card card : Shop.getCards().keySet())
            System.out.println(card.getName() + ":" + Shop.getCards().get(card));
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showCurrentMenu() {
        System.out.println("Shop Menu");
    }
}