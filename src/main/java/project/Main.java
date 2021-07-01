package project;

import project.model.card.CardsDatabase;
import project.view.MenusManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        CardsDatabase.getInstance().readAndMakeCards();
        MenusManager.getInstance().run();
    }
}
