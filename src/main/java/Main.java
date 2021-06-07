import model.card.CardsDatabase;
import view.MenusManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        CardsDatabase.getInstance().readAndMakeCards();
        MenusManager.getInstance().run();
    }
}
/*
خخخخخخخخ دا
 */
/*
user create -u erfan -p 1234 -n emj
user create -u mahdi -p 1234 -n mhdi
user login -u erfan -p 1234
menu enter shop
menu show-current
shop show --all
shop buy Horn Imp
shop buy Horn Imp
shop buy Dark Magician
shop buy Dark Magician
shop buy Battle Ox
shop buy Battle Ox
shop buy Yomi Ship
shop buy Yomi Ship
shop buy Baby Dragon
shop buy Baby Dragon
shop buy The Tricky
shop buy The Tricky
shop buy Trap Hole
shop buy Trap Hole
shop buy Mirror Force
shop buy Mirror Force
shop buy Monster Reborn
shop buy Monster Reborn
shop buy forest
shop buy Umiiruka
shop buy Harpie's Feather Duster
shop buy Harpie's Feather Duster
shop buy Exploder Dragon
shop buy Exploder Dragon
shop buy Beast King Barbaros
shop buy Beast King Barbaros
shop buy Beast King Barbaros
shop buy Gate Guardian
shop buy Gate Guardian
shop buy Gate Guardian
shop buy Man-Eater Bug
shop buy Man-Eater Bug
shop buy Crab Turtle
shop buy Crab Turtle
shop buy Black Pendant
shop buy Black Pendant
shop buy Magic Cylinder
shop buy Magic Cylinder
shop buy Battle warrior
shop buy Battle warrior
shop buy Spiral Serpent
shop buy Spiral Serpent
menu exit
menu enter deck
help
deck create erfan
deck add-card --card Horn Imp --deck erfan
deck add-card --card Horn Imp --deck erfan
deck add-card --card Dark Magician --deck erfan
deck add-card --card Dark Magician --deck erfan
deck add-card --card Battle Ox --deck erfan
deck add-card --card Battle Ox --deck erfan
deck add-card --card Yomi Ship --deck erfan
deck add-card --card Yomi Ship --deck erfan
deck add-card --card Baby Dragon --deck erfan
deck add-card --card Baby Dragon --deck erfan
deck add-card --card The Tricky --deck erfan
deck add-card --card The Tricky --deck erfan
deck add-card --card Trap Hole --deck erfan
deck add-card --card Trap Hole --deck erfan
deck add-card --card Mirror Force --deck erfan
deck add-card --card Mirror Force --deck erfan
deck add-card --card Monster Reborn --deck erfan
deck add-card --card Monster Reborn --deck erfan
deck add-card --card forest --deck erfan
deck add-card --card Umiiruka --deck erfan
deck add-card --card Harpie's Feather Duster --deck erfan
deck add-card --card Harpie's Feather Duster --deck erfan
deck add-card --card Exploder Dragon --deck erfan
deck add-card --card Exploder Dragon --deck erfan
deck add-card --card Beast King Barbaros --deck erfan
deck add-card --card Beast King Barbaros --deck erfan
deck add-card --card Beast King Barbaros --deck erfan
deck add-card --card Gate Guardian --deck erfan
deck add-card --card Gate Guardian --deck erfan
deck add-card --card Gate Guardian --deck erfan
deck add-card --card Man-Eater Bug --deck erfan
deck add-card --card Man-Eater Bug --deck erfan
deck add-card --card Crab Turtle --deck erfan
deck add-card --card Crab Turtle --deck erfan
deck add-card --card Black Pendant --deck erfan
deck add-card --card Black Pendant --deck erfan
deck add-card --card Magic Cylinder --deck erfan
deck add-card --card Magic Cylinder --deck erfan
deck add-card --card Battle warrior --deck erfan
deck add-card --card Battle warrior --deck erfan
deck add-card --card Spiral Serpent --deck erfan
deck add-card --card Spiral Serpent --deck erfan
deck set-activate erfan
menu exit
menu exit
user login -u mahdi -p 1234
menu enter shop
shop buy Horn Imp
shop buy Horn Imp
shop buy Dark Magician
shop buy Dark Magician
shop buy Battle Ox
shop buy Battle Ox
shop buy Yomi Ship
shop buy Yomi Ship
shop buy Baby Dragon
shop buy Baby Dragon
shop buy The Tricky
shop buy The Tricky
shop buy Trap Hole
shop buy Trap Hole
shop buy Mirror Force
shop buy Mirror Force
shop buy Monster Reborn
shop buy Monster Reborn
shop buy forest
shop buy Umiiruka
shop buy Harpie's Feather Duster
shop buy Harpie's Feather Duster
shop buy Exploder Dragon
shop buy Exploder Dragon
shop buy Beast King Barbaros
shop buy Beast King Barbaros
shop buy Beast King Barbaros
shop buy Gate Guardian
shop buy Gate Guardian
shop buy Gate Guardian
shop buy Man-Eater Bug
shop buy Man-Eater Bug
shop buy Crab Turtle
shop buy Crab Turtle
shop buy Black Pendant
shop buy Black Pendant
shop buy Magic Cylinder
shop buy Magic Cylinder
shop buy Battle warrior
shop buy Battle warrior
shop buy Spiral Serpent
shop buy Spiral Serpent
menu exit
menu enter deck
deck create mahdi
deck add-card --card Horn Imp --deck mahdi
deck add-card --card Horn Imp --deck mahdi
deck add-card --card Dark Magician --deck mahdi
deck add-card --card Dark Magician --deck mahdi
deck add-card --card Battle Ox --deck mahdi
deck add-card --card Battle Ox --deck mahdi
deck add-card --card Yomi Ship --deck mahdi
deck add-card --card Yomi Ship --deck mahdi
deck add-card --card Baby Dragon --deck mahdi
deck add-card --card Baby Dragon --deck mahdi
deck add-card --card The Tricky --deck mahdi
deck add-card --card The Tricky --deck mahdi
deck add-card --card Trap Hole --deck mahdi
deck add-card --card Trap Hole --deck mahdi
deck add-card --card Mirror Force --deck mahdi
deck add-card --card Mirror Force --deck mahdi
deck add-card --card Monster Reborn --deck mahdi
deck add-card --card Monster Reborn --deck mahdi
deck add-card --card forest --deck mahdi
deck add-card --card Umiiruka --deck mahdi
deck add-card --card Harpie's Feather Duster --deck mahdi
deck add-card --card Harpie's Feather Duster --deck mahdi
deck add-card --card Exploder Dragon --deck mahdi
deck add-card --card Exploder Dragon --deck mahdi
deck add-card --card Beast King Barbaros --deck mahdi
deck add-card --card Beast King Barbaros --deck mahdi
deck add-card --card Beast King Barbaros --deck mahdi
deck add-card --card Gate Guardian --deck mahdi
deck add-card --card Gate Guardian --deck mahdi
deck add-card --card Gate Guardian --deck mahdi
deck add-card --card Man-Eater Bug --deck mahdi
deck add-card --card Man-Eater Bug --deck mahdi
deck add-card --card Crab Turtle --deck mahdi
deck add-card --card Crab Turtle --deck mahdi
deck add-card --card Black Pendant --deck mahdi
deck add-card --card Black Pendant --deck mahdi
deck add-card --card Magic Cylinder --deck mahdi
deck add-card --card Magic Cylinder --deck mahdi
deck add-card --card Battle warrior --deck mahdi
deck add-card --card Battle warrior --deck mahdi
deck add-card --card Spiral Serpent --deck mahdi
deck add-card --card Spiral Serpent --deck mahdi
deck set-activate mahdi
menu exit
menu enter duel
duel new --s-p erfan -r 1

 */