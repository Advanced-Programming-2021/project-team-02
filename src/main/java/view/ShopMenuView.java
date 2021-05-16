package view;

import controller.DeckMenuController;
import controller.ShopMenuController;
import model.Assets;
import model.Shop;
import model.card.Card;
import view.input.Regex;
import view.messages.Error;

import java.util.Locale;
import java.util.Objects;
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
        if ((matcher = Regex.getMatcher(Regex.MENU_ENTER, command)).matches()) {
            if (matcher.group ("menuName").toLowerCase(Locale.ROOT).equals ("shop"))
                showDynamicError (Error.BEING_ON_CURRENT_MENU);
            else showError(Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher(Regex.MENU_EXIT, command).matches()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if (Regex.getMatcher(Regex.MENU_SHOW_CURRENT, command).matches()) {
            showCurrentMenu();
        } else if ((matcher = Regex.getMatcher(Regex.SHOP_BUY, command)).matches()) {
            controller.buyCard(matcher);
        } else if (Regex.getMatcher(Regex.SHOP_SHOW_ALL, command).matches()) {
            showAllCards();
        } else if ((matcher = Regex.getMatcher(Regex.CARD_SHOW, command)).matches()) {
            DeckMenuController.getInstance ().showCard (matcher);
        } else if ((matcher = Regex.getMatcher(Regex.CHEAT_INCREASE_MONEY, command)).matches()) {
            Objects.requireNonNull (Assets.getAssetsByUsername (controller.getLoggedInUser ().getUsername ())).increaseCoin (Integer.parseInt (matcher.group ("moneyAmount")));
        } else if (Regex.getMatcher(Regex.COMMAND_HELP, command).matches()) {
            help ();
        } else showError(Error.INVALID_COMMAND);
    }

    public void showAllCards() {
        for (Card card : Shop.getCards().keySet())
            System.out.println(card.getName() + ":" + Shop.getCards().get(card));
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showDynamicError(Error error) {
        System.out.printf (error.getValue (), Menu.SHOP_MENU.getValue ());
    }

    public void showCurrentMenu() {
        System.out.println("Shop Menu");
    }

    public void help() {
        System.out.println ("menu show-current\n" +
                "card show <cardName>\n" +
                "shop show --all\n" +
                "shop buy <cardName>\n" +
                "menu exit\n" +
                "help");
    }
}