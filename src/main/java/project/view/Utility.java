package project.view;

import animatefx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import project.controller.playgame.RoundGameController;
import project.view.gameview.GameView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Utility {
    private static BorderPane mainPane;
    private static Parent secondPane;
    private static final HashMap<String, Image> stringImageHashMap = new HashMap<>();
    private static DataFormat sideDeckGridPaneFormat = new DataFormat("SideGridPane");
    private static DataFormat mainDeckGridPaneFormat = new DataFormat("MainGridPane");
    private static DataFormat allCardsPaneFormat = new DataFormat("AllCardsPane");
    public static void openNewMenu(String url) throws IOException {
        Parent newSecondPane = FXMLLoader.load(Objects.requireNonNull(Utility.class.getResource(url)));
        mainPane.getChildren().remove(secondPane);
        mainPane.setCenter(newSecondPane);
        setSecondPane(newSecondPane);
        new FadeIn(newSecondPane).play();
    }

    public static void setMainPane(BorderPane mainPane) {
        Utility.mainPane = mainPane;
    }

    public static void setSecondPane(Parent secondPane) {
        Utility.secondPane = secondPane;
    }

    public static Parent getSecondPane() {
        return secondPane;
    }

    public void addImages() {
        stringImageHashMap.put("Alexandrite Dragon", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/AlexandriteDragon.jpg"))));
        stringImageHashMap.put("Axe Raider", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/AxeRaider.jpg"))));
        stringImageHashMap.put("Baby dragon", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/BabyDragon.jpg"))));
        stringImageHashMap.put("Battle OX", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/BattleOX.jpg"))));
        stringImageHashMap.put("Battle warrior", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/BattleWarrior.jpg"))));
        stringImageHashMap.put("Beast King Barbaros", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/BeastKingBarbaros.jpg"))));
        stringImageHashMap.put("Bitron", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/Bitron.jpg"))));
        stringImageHashMap.put("Blue-Eyes white dragon", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/BlueEyesWhiteDragon.jpg"))));
        stringImageHashMap.put("Crab Turtle", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/CrabTurtle.jpg"))));
        stringImageHashMap.put("Crawling dragon", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/CrawlingDragon.jpg"))));
        stringImageHashMap.put("Curtain of the dark ones", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/CurtainOfDarkOnes.jpg"))));
        stringImageHashMap.put("Dark Blade", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/DarkBlade.jpg"))));
        stringImageHashMap.put("Dark magician", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/DarkMagician.jpg"))));
        stringImageHashMap.put("Exploder Dragon", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/ExploderDragon.jpg"))));
        stringImageHashMap.put("Feral Imp", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/FeralImp.jpg"))));
        stringImageHashMap.put("Fireyarou", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/FireYarou.jpg"))));
        stringImageHashMap.put("Flame manipulator", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/FlameManipulator.jpg"))));
        stringImageHashMap.put("Gate Guardian", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/GateGuardian.jpg"))));
        stringImageHashMap.put("Haniwa", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/Haniwa.jpg"))));
        stringImageHashMap.put("Hero of the east", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/HeroOfTheEast.jpg"))));
        stringImageHashMap.put("Horn Imp", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/HornImp.jpg"))));
        stringImageHashMap.put("Leotron ", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/Leotron.jpg"))));
        stringImageHashMap.put("Man-Eater Bug", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/ManEaterBug.jpg"))));
        stringImageHashMap.put("Silver Fang", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/SilverFang.jpg"))));
        stringImageHashMap.put("Skull Guardian", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/SkullGuardian.jpg"))));
        stringImageHashMap.put("Slot Machine", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/SlotMachine.jpg"))));
        stringImageHashMap.put("Spiral Serpent", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/SpiralSerpent.jpg"))));
        stringImageHashMap.put("The Tricky", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/TheTricky.jpg"))));
        stringImageHashMap.put("Warrior Dai Grepher", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/WarriorDaiGrepher.jpg"))));
        stringImageHashMap.put("Wattaildragon", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/Wattaildragon.jpg"))));
        stringImageHashMap.put("Wattkid", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/Wattkid.jpg"))));
        stringImageHashMap.put("Yomi Ship", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Monsters/YomiShip.jpg"))));

        stringImageHashMap.put("Advanced Ritual Art", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/AdvancedRitualArt.jpg"))));
        stringImageHashMap.put("Black Pendant", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/BlackPendant.jpg"))));
        stringImageHashMap.put("Closed Forest", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/ClosedForest.jpg"))));
        stringImageHashMap.put("Dark Hole", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/DarkHole.jpg"))));
        stringImageHashMap.put("Forest", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/Forest.jpg"))));
        stringImageHashMap.put("Harpie's Feather Duster", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/hf.jpg"))));
        stringImageHashMap.put("Monster Reborn", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/MonsterReborn.jpg"))));
        stringImageHashMap.put("Pot of Greed", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/PotOfGreed.jpg"))));
        stringImageHashMap.put("Raigeki", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/Raigeki.jpg"))));
        stringImageHashMap.put("Sword of dark destruction", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/SwordOfDarkDestruction.jpg"))));
        stringImageHashMap.put("Terraforming", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/Terraforming.jpg"))));
        stringImageHashMap.put("Umiiruka", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/Umiruka.jpg"))));
        stringImageHashMap.put("Yami", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Spells/Yami.jpg"))));

        stringImageHashMap.put("Magic Cylinder", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Traps/MagicCylinder.jpg"))));
        stringImageHashMap.put("Mirror Force", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Traps/MirrorForce.jpg"))));
        stringImageHashMap.put("Negate Attack", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Traps/NegateAttack.jpg"))));
        stringImageHashMap.put("Torrential Tribute", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Traps/TorrentialTribute.jpg"))));
        stringImageHashMap.put("Trap Hole", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Traps/TrapHole.jpg"))));
        stringImageHashMap.put("Back Image", new Image(String.valueOf(getClass().getResource("/project/image/GamePictures/Card Back.png"))));
        stringImageHashMap.put("Card Back Set", new Image(String.valueOf(getClass().getResource("/project/image/GamePictures/Card Back Set.png"))));
        stringImageHashMap.put("Time Seal", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Traps/TimeSeal.jpg"))));
        stringImageHashMap.put("Call of The Haunted", new Image(String.valueOf(getClass().getResource("/project/image/ShopMenuPictures/Traps/CalloftheHunted.jpg"))));


        stringImageHashMap.put("NEW", new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/Picture.jpg"))));

    }
    public static Object openMenuAndReturnController(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(Utility.class.getResource(url));
        Parent newSecondPane = loader.load();
        mainPane.getChildren().remove(secondPane);
        mainPane.setCenter(newSecondPane);
        setSecondPane(newSecondPane);
        new FadeIn(newSecondPane).play();
        return loader.getController();
    }
    public HashMap<String, Image> getStringImageHashMap() {
        return stringImageHashMap;
    }

    public static DataFormat getAllCardsPaneFormat() {
        return allCardsPaneFormat;
    }

    public static DataFormat getMainDeckGridPaneFormat() {
        return mainDeckGridPaneFormat;
    }

    public static DataFormat getSideDeckGridPaneFormat() {
        return sideDeckGridPaneFormat;
    }
}
