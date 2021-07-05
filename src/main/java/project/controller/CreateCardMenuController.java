package project.controller;

import javafx.scene.image.Image;
import project.model.Assets;
import project.model.User;
import project.model.card.CardsDatabase;
import project.model.card.informationofcards.*;
import project.view.Utility;

import java.util.Objects;

public class CreateCardMenuController {
    private static CreateCardMenuController instance = null;
    private User user = MainMenuController.getInstance().getLoggedInUser();

    private CreateCardMenuController() {
    }

    public static CreateCardMenuController getInstance() {
        if (instance == null) instance = new CreateCardMenuController();
        return instance;
    }

    public void makeMonster(String replacementForEffect, String enterCardName, String level, String description, String attack
    , String defense, String price) {
        if (replacementForEffect.equals("No Effect")) {
            CardsDatabase.makeCardMonster(CardType.MONSTER, enterCardName, "MADE", MonsterActionType.NORMAL,
                    MonsterEffect.NONE, Integer.parseInt(level), Attribute.DARK, description,
                    Integer.parseInt(attack), Integer.parseInt(defense), MonsterType.PYRO, Integer.parseInt(price));
        } else {
            CardsDatabase.makeCardMonster(CardType.MONSTER, enterCardName, "MADE", MonsterActionType.NORMAL,
                    MonsterEffect.getMonsterEffectByName(replacementForEffect), Integer.parseInt(level), Attribute.DARK, description,
                    Integer.parseInt(attack), Integer.parseInt(defense), MonsterType.PYRO, Integer.parseInt(price));
        }
        Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).decreaseCoin(Integer.parseInt(price)/10);
        Utility utility = new Utility();
        utility.getStringImageHashMap().put(enterCardName, new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/Picture.jpg"))));
    }

    public void makeSpell(String replacementForEffect, String enterCardName, String description, String price) {
        CardsDatabase.makeCardSpell(CardType.SPELL, enterCardName, "MADE", SpellEffect.getSpellByName(replacementForEffect),
                Attribute.DARK, description, SpellType.getSpellTypeByTypeName(replacementForEffect),
                false, Integer.parseInt(price));
        Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).decreaseCoin(Integer.parseInt(price)/10);
        Utility utility = new Utility();
        utility.getStringImageHashMap().put(enterCardName, new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/Picture.jpg"))));
        System.out.println(enterCardName + "    :   CCC");
    }

    public void makeTrap(String replacementForEffect, String enterCardName, String description, String price) {
        CardsDatabase.makeTrapCard(CardType.TRAP, enterCardName, "MADE", TrapEffect.getTrapEffectByName(replacementForEffect),
                Attribute.DARK, description, TrapType.getTrapTypeByTypeName(replacementForEffect),
                false, Integer.parseInt(price));
        Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).decreaseCoin(Integer.parseInt(price)/10);
        Utility utility = new Utility();
        utility.getStringImageHashMap().put(enterCardName, new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/Picture.jpg"))));
    }

}
