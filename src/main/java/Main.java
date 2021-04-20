import model.card.CardsDatabase;
import view.MenusManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CardsDatabase.getInstance().readAndMakeCards();
        MenusManager.getInstance().run();
    }
}