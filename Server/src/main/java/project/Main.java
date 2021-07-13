package project;

import project.model.card.CardsDatabase;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CardsDatabase.getInstance().readAndMakeCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerMainController.run();

    }


}
