package project.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import project.controller.CreateCardMenuController;
import project.model.Music;
import project.model.card.Card;
import project.model.card.CardsDatabase;
import project.model.gui.Icon;
import project.view.messages.CreateCardMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    public ListView listViewForTypes;
    private String cardType = null;
    CreateCardMenuController createCardMenuController;


    @FXML
    public void initialize() {
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());

        createCardMenuController = CreateCardMenuController.getInstance();
        attack.setVisible(false);
        defense.setVisible(false);
        level.setVisible(false);
    }

    public void changeToMonster() {
        type.setText("Monster");
        attack.setVisible(true);
        defense.setVisible(true);
        level.setVisible(true);
        cardType = "Monster";
        listView.getItems().clear();
        listViewForTypes.getItems().clear();
        listViewForTypes.getItems().addAll("Beast", "Pyro", "Fiend", "Aqua", "Rock", "Insect", "Machine", "Fairy"
        , "Beast-Warrior", "Cyberse", "Thunder", "Dragon", "Warrior", "Spellcaster", "Sea Serpent");
        listView.getItems().addAll("Yomi Ship", "Man-Eater Bug", "Gate Guardian", "Beast King Barbaros", "Exploder Dragon", "The Tricky", "No Effect");
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void changeToSpell() {
        type.setText("Spell");
        attack.setVisible(false);
        defense.setVisible(false);
        level.setVisible(false);
        cardType = "Spell";
        listView.getItems().clear();
        listViewForTypes.getItems().clear();
        listViewForTypes.getItems().addAll("Continuous", "Equip", "Field", "Quick-play", "Counter", "Ritual", "Normal");
        listView.getItems().addAll("Advanced Ritual Art", "Black Pendant", "Sword of dark destruction", "Umiiruka", "Closed Forest"
                , "Forest", "Yami", "Dark Hole", "Harpie's Feather Duster", "Raigeki", "Pot of Greed", "Terraforming", "Monster Reborn");
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void changeToTrap() {
        type.setText("Trap");
        attack.setVisible(false);
        defense.setVisible(false);
        level.setVisible(false);
        cardType = "Trap";
        listView.getItems().clear();
        listViewForTypes.getItems().clear();
        listViewForTypes.getItems().addAll("Normal", "Continuous", "Counter");
        listView.getItems().addAll("Trap Hole", "Mirror Force", "Magic Cylinder", "Torrential Tribute", "Negate Attack");
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void createCard() {
        String effect = String.valueOf(listView.getSelectionModel().getSelectedItems());
        String replacementForEffect = effect.substring(1, effect.length() - 1);
        System.out.println(replacementForEffect + "    : 1");
        if (cardType == null) {
            new PopUpMessage(CreateCardMessage.SELECT_TYPE.getAlertType(), CreateCardMessage.SELECT_TYPE.getLabel());
        } else if (enterCardName.getText().length() == 0 || description.getText().length() == 0) {
            new PopUpMessage(CreateCardMessage.FILL_THE_BLANKS.getAlertType(), CreateCardMessage.FILL_THE_BLANKS.getLabel());
        } else if (cardType.equals("Monster") && (attack.getText().length() == 0 || defense.getText().length() == 0 || level.getText().length() == 0)) {
            new PopUpMessage(CreateCardMessage.FILL_THE_BLANKS.getAlertType(), CreateCardMessage.FILL_THE_BLANKS.getLabel());
//        } else if (cardType.equals("Monster")) {
//            try {
//                if ((Integer.parseInt(attack.getText()) < 1000 || Integer.parseInt(defense.getText()) < 1000
//                        || Integer.parseInt(level.getText()) < 2)) {
//                    new PopUpMessage(CreateCardMessage.PAY_ATTENTION_TO_MINIMUMS.getAlertType(), CreateCardMessage.PAY_ATTENTION_TO_MINIMUMS.getLabel());
//                }
//            } catch (Exception e) {
//                new PopUpMessage(CreateCardMessage.YOU_SHOULD_ENTER_INTEGER.getAlertType(),
//                        CreateCardMessage.YOU_SHOULD_ENTER_INTEGER.getLabel());
//            }
//        } else if (replacementForEffect.length() == 0) {
            new PopUpMessage(CreateCardMessage.SELECT_EFFECT.getAlertType(), CreateCardMessage.SELECT_EFFECT.getLabel());
        } else checkListViewAndMakeCard();
        System.out.println(replacementForEffect + "    : 2");
    }

    private void checkListViewAndMakeCard() {
        // effect bracket dare : solve
        // manfi boodane attack o defense o level :
        // khali boodan effect : solve
        String effect = String.valueOf(listView.getSelectionModel().getSelectedItems());
        String replacementForEffect = effect.substring(1, effect.length() - 1);
        System.out.println(replacementForEffect + "    : ");
        if (cardType.equals("Monster")) {
            System.out.println("Monster");
            createCardMenuController.makeMonster(replacementForEffect, enterCardName.getText(), (level.getText()), description.getText(),
                    (attack.getText()), (defense.getText()), (price.getText()));
            new PopUpMessage(CreateCardMessage.CARD_CREATED.getAlertType(), CreateCardMessage.CARD_CREATED.getLabel());
        } else if (cardType.equals("Spell")) {
            createCardMenuController.makeSpell(replacementForEffect, enterCardName.getText(),
                    description.getText(), (price.getText()));
            new PopUpMessage(CreateCardMessage.CARD_CREATED.getAlertType(), CreateCardMessage.CARD_CREATED.getLabel());
        } else {
            createCardMenuController.makeTrap(replacementForEffect, enterCardName.getText(),
                    description.getText(), (price.getText()));
            new PopUpMessage(CreateCardMessage.CARD_CREATED.getAlertType(), CreateCardMessage.CARD_CREATED.getLabel());
        }
        System.out.println(replacementForEffect);
    }


    public void calculatePrice() {
        String effect = String.valueOf(listView.getSelectionModel().getSelectedItems());
        if (cardType == null) {
            new PopUpMessage(CreateCardMessage.SELECT_TYPE.getAlertType(), CreateCardMessage.SELECT_TYPE.getLabel());
        } else if (cardType.equals("Monster")) {
            if (attack.getText().length() == 0 && defense.getText().length() == 0 && level.getText().length() == 0) {
                new PopUpMessage(CreateCardMessage.FILL_THE_BLANKS.getAlertType(), CreateCardMessage.FILL_THE_BLANKS.getLabel());
            } else {
                int cardPrice = 0;
                if (level.getText().length() != 0) {
                    if (Integer.parseInt(level.getText()) > 8) {
                        cardPrice += (Integer.parseInt(level.getText()) - 8) * 5000 + (Integer.parseInt(level.getText())) * 1200;
                    } else if (Integer.parseInt(level.getText()) <= 8) {
                        cardPrice += (Integer.parseInt(level.getText())) * 1200;
                    }
                }
                if (attack.getText().length() != 0) {
                    if (Integer.parseInt(attack.getText()) > 3500) {
                        cardPrice += (Integer.parseInt(attack.getText()) - 3500) / 100 * 1000 + (Integer.parseInt(attack.getText()) - 2500) / 100 * 500;
                    } else if (Integer.parseInt(attack.getText()) <= 3500 && Integer.parseInt(attack.getText()) > 2500) {
                        cardPrice += (Integer.parseInt(attack.getText()) - 2500) / 100 * 500 + 200;
                    } else if (Integer.parseInt(attack.getText()) <= 2500) {
                        cardPrice += 200;
                    }
                }
                if (defense.getText().length() != 0) {
                    if (Integer.parseInt(defense.getText()) > 3500) {
                        cardPrice += (Integer.parseInt(defense.getText()) - 3500) / 100 * 1000 + (Integer.parseInt(defense.getText()) - 2500) / 100 * 500;
                    } else if (Integer.parseInt(defense.getText()) <= 3500 && Integer.parseInt(attack.getText()) > 2500) {
                        cardPrice += (Integer.parseInt(defense.getText()) - 2500) / 100 * 500 + 200;
                    } else if (Integer.parseInt(defense.getText()) <= 2500) {
                        cardPrice += 200;
                    }
                }
                if (!effect.equals("[No Effect]")) {
                    cardPrice += 2000;
                }
                price.setText(String.valueOf(cardPrice));
            }
        } else if (cardType.equals("Spell") && effect != null) {
            switch (effect) {
                case "[Advanced Ritual Art]":
                    price.setText("2000");
                    break;
                case "[Black Pendant]":
                    price.setText("2250");
                    break;
                case "[Sword of dark destruction]":
                    price.setText("2500");
                    break;
                case "[Umiiruka]":
                    price.setText("2750");
                    break;
                case "[Closed Forest]":
                    price.setText("3000");
                    break;
                case "[Forest]":
                    price.setText("3250");
                    break;
                case "[Yami]":
                    price.setText("3500");
                    break;
                case "[Dark Hole]":
                    price.setText("3750");
                    break;
                case "[Harpie's Feather Duster]":
                    price.setText("4000");
                    break;
                case "[Raigeki]":
                    price.setText("4250");
                    break;
                case "[Pot of Greed]":
                    price.setText("4500");
                    break;
                case "[Terraforming]":
                    price.setText("5000");
                    break;
                case "[Monster Reborn]":
                    price.setText("5250");
                    break;
                default:
                    new PopUpMessage(CreateCardMessage.SELECT_EFFECT.getAlertType(), CreateCardMessage.SELECT_EFFECT.getLabel());
                    break;
            }
        } else if (cardType.equals("Trap") && effect != null) {
            switch (effect) {
                case "[Trap Hole]":
                    price.setText("3000");
                    break;
                case "[Mirror Force]":
                    price.setText("3500");
                    break;
                case "[Magic Cylinder]":
                    price.setText("4000");
                    break;
                case "[Torrential Tribute]":
                    price.setText("4500");
                    break;
                case "[Negate Attack]":
                    price.setText("5000");
                    break;
                default:
                    new PopUpMessage(CreateCardMessage.SELECT_EFFECT.getAlertType(), CreateCardMessage.SELECT_EFFECT.getLabel());
                    break;
            }
        }
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.nextTrack(playPauseMusicButton, muteUnmuteButton);
    }

    public void playPauseMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.playPauseMusic(playPauseMusicButton);
    }

    public void muteUnmuteMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.muteUnmuteMusic(muteUnmuteButton);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
//        ArrayList<Card> arrayList = CardsDatabase.getAllCards();
//        for (Card card : arrayList) {
//            System.out.println(card.getName() + " : ");
//        }
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }
}
