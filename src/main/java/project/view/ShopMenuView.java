package project.view;

import project.controller.DeckMenuController;
import project.controller.MainMenuController;
import project.controller.ShopMenuController;
import project.model.Assets;
import project.model.Shop;
import project.model.card.Card;
import project.view.input.Regex;
import project.view.messages.Error;

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
            if (matcher.group ("menuName").toLowerCase(Locale.ROOT).equals ("shop")) {}
//                showDynamicError (Error.BEING_ON_CURRENT_MENU);
            else Error.showError(Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher(Regex.MENU_EXIT, command).matches()) {
//            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if (Regex.getMatcher(Regex.MENU_SHOW_CURRENT, command).matches()) {
//            showCurrentMenu();
        } else if ((matcher = Regex.getMatcher(Regex.SHOP_BUY, command)).matches()) {
            controller.buyCard(matcher.group ("cardName"));
        } else if (Regex.getMatcher(Regex.SHOP_SHOW_ALL, command).matches()) {
            showAllCards();
        } else if ((matcher = Regex.getMatcher(Regex.CARD_SHOW, command)).matches()) {
            DeckMenuController.getInstance ().showCard (matcher.group ("cardName"));
        } else if (Regex.getMatcher(Regex.SHOP_SHOW_MY_CARDS, command).matches()) {
            showMyCards();
        } else if ((matcher = Regex.getMatcher(Regex.CHEAT_INCREASE_MONEY, command)).matches()) {
            Objects.requireNonNull (Assets.getAssetsByUsername (controller.getLoggedInUser ().getUsername ())).increaseCoin (Integer.parseInt (matcher.group ("moneyAmount")));
        } else if (Regex.getMatcher(Regex.COMMAND_HELP, command).matches()) {
//            help ();
        } else Error.showError(Error.INVALID_COMMAND);
    }

    public void showMyCards() {
        System.out.println ("<cardName>:<number>");
        Assets assets = Assets.getAssetsByUsername (MainMenuController.getInstance ().getLoggedInUser ().getUsername ());
        assert assets != null;
        for (Card card : assets.getAllUserCards ().keySet ())
            System.out.println (card.getName () + ":" + assets.getAllUserCards ().get (card));
    }

    public void showAllCards() {
        for (Card card : Shop.getCards().keySet())
            System.out.println(card.getName() + ":" + Shop.getCards().get(card));
    }
}