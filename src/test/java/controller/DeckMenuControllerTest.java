package controller;

import model.Assets;
import model.Deck;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class DeckMenuControllerTest {

    @BeforeEach
    void setThings() {
        User user = new User("test", "1234567", "test1");
    }

    @Test
    void getInstance() {

    }

    @Test
    void createDeck() {
    }

    @Test
    void deleteDeck() {
    }

    @Test
    void activateDeck() {
    }

    @Test
    void addCardToMainDeck() {
    }

    @Test
    void addCardToSideDeck() {
    }

    @Test
    void showAllDecks() {
    }

    @Test
    void showDeck() {
    }

    @Test
    void showAllCards() {
    }

    @Test
    void showCard() {
    }

    @Test
    void removeCardFromMainDeck() {
    }

    @Test
    void removeCardFromSideDeck() {
    }
}