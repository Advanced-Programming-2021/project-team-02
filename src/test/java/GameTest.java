import controller.playgame.RoundGameController;
import model.User;
import model.card.Card;
import model.card.CardsDatabase;
import model.game.DuelPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import view.gameview.GameView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GameTest { //test doesnt work fuckingly . . .
//    private static  GameView view ;
//    private static  RoundGameController controller ;
//
//
//    @BeforeAll
//    static void init() throws IOException {
//        view = GameView.getInstance();
//        controller = RoundGameController.getInstance();
//        CardsDatabase.getInstance().readAndMakeCards();
//        User user1 = new User("ali", "1234", "ali");
//        User user2 = new User("erfan", "1234", "erfan");
//        DuelPlayer firstPlayer = new DuelPlayer("ali", null);
//        DuelPlayer secondPlayer = new DuelPlayer("erfan", null);
//        controller.setRoundInfo(firstPlayer,secondPlayer);
//        controller.addCardToFirstPlayerHand(Card.getCardByName("Yomi Ship"));
//        controller.addCardToFirstPlayerHand(Card.getCardByName("Horn Imp"));
//        controller.addCardToFirstPlayerHand(Card.getCardByName("Fireyarou"));
//        controller.addCardToSecondPlayerHand(Card.getCardByName("Man-Eater Bug"));
//
//    }
//    @Test
//    public void yomiShipTest(){
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        view.commandRecognition("select --hand 1");
//        view.showCard(controller.getSelectedCell().getCardInCell());
//        controller.summonMonster();
//        controller.changeTurn();
//        view.commandRecognition("select --hand 1");
//        view.showCard(controller.getSelectedCell().getCardInCell());
//        Assertions.assertEquals("Name: Yomi Ship\r\n" +
//                "Level: 3\r\n" +
//                "Type: Aqua/Effect\r\n" +
//                "ATK: 800\r\n" +
//                "DEF: 1400\n" +
//                "Description: If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card."+
//                "Name: Horn imp\r\n" +
//                "Level: 4\r\n" +
//                "Type: Fiend\r\n" +
//                "ATK: 1300\r\n" +
//                "DEF: 1000\r\n" +
//                "Description: A small fiend that dwells in the dark, its single horn makes it a formidable opponent.",outContent.toString());
//    }
}
