package controller.playgame;

import model.card.Card;
import model.card.informationofcards.CardType;
import model.card.informationofcards.MonsterEffect;
import model.game.DuelPlayer;
import model.game.board.Cell;
import model.game.board.*;
import model.game.board.Zone;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

public class CardsEffects {

    public static void effect(DuelPlayer starter, DuelPlayer getAttack, Matcher matcher) {
        Card attackerCard = Objects.requireNonNull(Card.getCardByName(matcher.group(1)));
        Card defenseCard = Objects.requireNonNull(Card.getCardByName(matcher.group(1)));
        if (attackerCard.getCardType().equals(CardType.MONSTER)) {
            if (defenseCard.getCardType().equals(CardType.MONSTER)) {
                if (MonsterEffect.getMonsterEffectByName(matcher.group(2)).equals(MonsterEffect.YOMI_SHIP_EFFECT)) {
                    YomiShip(starter, getAttack, matcher);
                }
            } else if (defenseCard.getCardType().equals(CardType.SPELL)) {

            } else if (defenseCard.getCardType().equals(CardType.TRAP)) {

            }
        } else if (attackerCard.getCardType().equals(CardType.SPELL)) {
            if (defenseCard.getCardType().equals(CardType.SPELL)) {

            } else if (defenseCard.getCardType().equals(CardType.TRAP)) {

            }
        } else if (attackerCard.getCardType().equals(CardType.TRAP)) {
            if (defenseCard.getCardType().equals(CardType.SPELL)) {

            } else if (defenseCard.getCardType().equals(CardType.TRAP)) {

            }
        }
    }

    public static void YomiShip(DuelPlayer starter, DuelPlayer getAttack, Matcher matcher) {
        for (int i = 0; i < 5; i++) {
            Cell cell = starter.getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (cell.getCardInCell().equals(Card.getCardByName(matcher.group(1)))) {
                starter.getPlayerBoard().removeMonsterFromBoard(i);
            }
            Cell cell1 = getAttack.getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (cell1.getCardInCell().equals(Card.getCardByName(matcher.group(2)))) {
                getAttack.getPlayerBoard().removeMonsterFromBoard(i);
            }
        }
        // arrayList get empty
    }
}