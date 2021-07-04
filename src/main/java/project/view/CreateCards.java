package project.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import project.model.card.CardsDatabase;
import project.model.card.informationofcards.*;
import project.view.messages.CreateCardMessage;
import project.view.messages.PopUpMessage;

public class CreateCards {
    public MenuButton type;
    public MenuItem monster;
    public MenuItem spell;
    public MenuItem trap;
    public TextField enterCardName;
    public TextField attack;
    public TextField defense;
    public TextArea description;
    public ListView listView;
    public Button submit;
    public Label effect;
    public Label price;
    public TextField level;
    public Button calculatePrice;
    private String cardType = null;


    @FXML
    public void initialize() {
        attack.setVisible(false);
        defense.setVisible(false);
        level.setVisible(false);
    }

    public void changeToMonster(ActionEvent event) {
        type.setText("Monster");
        attack.setVisible(true);
        defense.setVisible(true);
        level.setVisible(true);
        cardType = "Monster";
        listView.getItems().clear();
        listView.getItems().addAll("Yomi Ship", "Man-Eater Bug", "Gate Guardian", "Beast King Barbaros", "Exploder Dragon", "The Tricky");
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void changeToSpell(ActionEvent event) {
        type.setText("Spell");
        attack.setVisible(false);
        defense.setVisible(false);
        level.setVisible(false);
        cardType = "Spell";
        listView.getItems().clear();
        listView.getItems().addAll("Advanced Ritual Art", "Black Pendant", "Sword of dark destruction", "Umiiruka", "Closed Forest"
                , "Forest", "Yami", "Dark Hole", "Harpie's Feather Duster", "Raigeki", "Pot of Greed", "Terraforming", "Monster Reborn");
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void changeToTrap(ActionEvent event) {
        type.setText("Trap");
        attack.setVisible(false);
        defense.setVisible(false);
        level.setVisible(false);
        cardType = "Trap";
        listView.getItems().clear();
        listView.getItems().addAll("Trap Hole", "Mirror Force", "Magic Cylinder", "Torrential Tribute", "Negate Attack");
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void createCard(ActionEvent event) {
        if (cardType == null) {
            new PopUpMessage(CreateCardMessage.FILL_THE_BLANKS.getAlertType(), CreateCardMessage.FILL_THE_BLANKS.getLabel());
        } else if (enterCardName.getText().length() == 0 || description.getText().length() == 0) {
            new PopUpMessage(CreateCardMessage.FILL_THE_BLANKS.getAlertType(), CreateCardMessage.FILL_THE_BLANKS.getLabel());
        } else if (cardType.equals("Monster") && (attack.getText().length() == 0 || defense.getText().length() == 0 || level.getText().length() == 0)) {
            new PopUpMessage(CreateCardMessage.FILL_THE_BLANKS.getAlertType(), CreateCardMessage.FILL_THE_BLANKS.getLabel());
        } else checkListViewAndMakeCard();
    }

    private void checkListViewAndMakeCard() {
        String effect = String.valueOf(listView.getSelectionModel().getSelectedItems());
        if (cardType.equals("Monster")) {
            if (effect.equals("No Effect")) {
                CardsDatabase.makeCardMonster(CardType.MONSTER, enterCardName.getText(), "ID", MonsterActionType.NORMAL,
                        null, Integer.parseInt(level.getText()), Attribute.DARK, description.getText(),
                        Integer.parseInt(attack.getText()), Integer.parseInt(defense.getText()), MonsterType.PYRO, Integer.parseInt(price.getText()));
            } else {
                CardsDatabase.makeCardMonster(CardType.MONSTER, enterCardName.getText(), "ID", MonsterActionType.NORMAL,
                        MonsterEffect.getMonsterEffectByName(effect), Integer.parseInt(level.getText()), Attribute.DARK, description.getText(),
                        Integer.parseInt(attack.getText()), Integer.parseInt(defense.getText()), MonsterType.PYRO, Integer.parseInt(price.getText()));
            }
        } else if (cardType.equals("Spell")) {
            CardsDatabase.makeCardSpell(CardType.SPELL, enterCardName.getText(), "ID", SpellEffect.getSpellByName(effect),
                    Attribute.DARK, description.getText(), SpellType.getSpellTypeByTypeName(effect),
                    false, Integer.parseInt(price.getText()));
        } else {
            CardsDatabase.makeTrapCard(CardType.TRAP, enterCardName.getText(), "ID", TrapEffect.getTrapEffectByName(effect),
                    Attribute.DARK, description.getText(), TrapType.getTrapTypeByTypeName(effect),
                    false, Integer.parseInt(price.getText()));
        }
        System.out.println(effect);
    }


    public void calculatePrice(ActionEvent event) {
        String effect = String.valueOf(listView.getSelectionModel().getSelectedItems());
        System.out.println(effect);
        if (cardType == null) {
            new PopUpMessage(CreateCardMessage.FILL_THE_BLANKS.getAlertType(), CreateCardMessage.FILL_THE_BLANKS.getLabel());
        } else if (cardType.equals("Monster")) {

        } else if (cardType.equals("Spell") && effect != null) {
            switch (effect) {
                case "[Advanced Ritual Art]":
                    price.setText("2000");
                case "Black Pendant":
                    price.setText("2250");
                case "Sword of dark destruction":
                    price.setText("2500");
                case "Umiiruka":
                    price.setText("2750");
                case "Closed Forest":
                    price.setText("3000");
                case "Forest":
                    price.setText("3250");
                case "Yami":
                    price.setText("3500");
                case "Dark Hole":
                    price.setText("3750");
                case "Harpie's Feather Duster":
                    price.setText("4000");
                case "Raigeki":
                    price.setText("4250");
                case "Pot of Greed":
                    price.setText("4500");
                case "Terraforming":
                    price.setText("5000");
                case "Monster Reborn":
                    price.setText("5250");
            }
        } else if (cardType.equals("Trap") && effect != null) {
            switch (effect) {
                case "Trap Hole":
                    price.setText("3000");
                case "Mirror Force":
                    price.setText("3500");
                case "Magic Cylinder":
                    price.setText("4000");
                case "Torrential Tribute":
                    price.setText("4500");
                case "Negate Attack":
                    price.setText("5000");
            }
        } else {
            new PopUpMessage(CreateCardMessage.SELECT_EFFECT.getAlertType(), CreateCardMessage.SELECT_EFFECT.getLabel());
        }
    }
}
