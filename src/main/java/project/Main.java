package project;

import project.model.card.CardsDatabase;
import project.view.LoginMenuView;
import project.view.ProfileMenuView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        CardsDatabase.getInstance().readAndMakeCards();
        LoginMenuView.main(args);
    }
}
