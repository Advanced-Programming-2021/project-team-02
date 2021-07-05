package project.model.card.informationofcards;

public enum MonsterEffect {
    COMMAND_KNIGHT_EFFECT,
    YOMI_SHIP_EFFECT,
    SUIJIN_EFFECT,
    MAN_EATER_BUG_EFFECT,
    GATE_GUARDIAN_EFFECT,
    SCANNER_EFFECT,
    MARSHMALLON_EFFECT,
    BEAST_KING_BARBAROS_EFFECT,
    TEXCHANGER_EFFECT,
    THE_CALCULATOR_EFFECT,
    MIRAGE_DRAGON_EFFECT,
    HERALD_OF_CREATION_EFFECT,
    EXPLODER_DRAGON_EFFECT,
    TERRATIGER_THE_EMPOWERED_WARRIOR_EFFECT,
    THE_TRICKY_EFFECT,
    NONE;

    public static MonsterEffect getMonsterEffectByName(String monsterName) {
        switch (monsterName) {
//            case "Slot Machine":
//            case "Haniwa":
//            case "Battle OX":
//            case "Axe Raider":
//            case "Horn Imp":
//            case "Silver Fang":
//            case "Fireyarou":
//            case "Curtain of the dark ones":
//            case "Dark magician":
//            case "Feral Imp":
//            case "Wattkid":
//            case "Baby dragon":
//            case "Hero of the east":
//            case "Battle warrior":
//            case "Crawling dragon":
//            case "Flame manipulator":
//            case "Blue-Eyes white dragon":
//            case "Crab Turtle":
//            case "Skull Guardian":
//            case "Bitron":
//            case "Leotron ":
//            case "Alexandrite Dragon":
//            case "Warrior Dai Grepher":
//            case "Dark Blade":
//            case "Wattaildragon":
//            case "Spiral Serpent":
//                return NONE;
            case "Yomi Ship":
                return YOMI_SHIP_EFFECT;
//            case "Suijin":
//                return SUIJIN_EFFECT;
            case "Man-Eater Bug":
                return MAN_EATER_BUG_EFFECT;
            case "Gate Guardian":
                return GATE_GUARDIAN_EFFECT;
//            case "Scanner":
//                return SCANNER_EFFECT;
//            case "Marshmallon":
//                return MARSHMALLON_EFFECT;
            case "Beast King Barbaros":
                return BEAST_KING_BARBAROS_EFFECT;
//            case "Texchanger":
//                return TEXCHANGER_EFFECT;
//            case "The Calculator":
//                return THE_CALCULATOR_EFFECT;
//            case "Mirage Dragon":
//                return MIRAGE_DRAGON_EFFECT;
//            case "Herald of Creation":
//                return HERALD_OF_CREATION_EFFECT;
            case "Exploder Dragon":
                return EXPLODER_DRAGON_EFFECT;
//            case "Terratiger, the Empowered Warrior":
//                return TERRATIGER_THE_EMPOWERED_WARRIOR_EFFECT;
            case "The Tricky":
                return THE_TRICKY_EFFECT;
//            case "Command knight":
//                return COMMAND_KNIGHT_EFFECT;
            default:
                return NONE;
        }

    }
    /*







Wattkid
Baby dragon
Hero of the east
Battle warrior
Crawling dragon
Flame manipulator
Blue-Eyes white dragon
Crab Turtle
Skull Guardian
Slot Machine
Haniwa
Man-Eater Bug
Gate Guardian
Scanner
Bitron
Marshmallon
Beast King Barbaros
Texchanger
Leotron
The Calculator
Alexandrite Dragon
Mirage Dragon
Herald of Creation
Exploder Dragon
Warrior Dai Grepher
Dark Blade
Wattaildragon
Terratiger, the Empowered Warrior
The Tricky
Spiral Serpent

     */
}
