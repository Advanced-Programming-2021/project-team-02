package project.view;

import javafx.stage.Stage;
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
    private static final ShopMenuController controller = ShopMenuController.getInstance();
    private static Stage stage;

}