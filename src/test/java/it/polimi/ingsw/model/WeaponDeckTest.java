package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.card.weapon.WeaponDeck;
import it.polimi.ingsw.model.card.weapon.WeaponFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeaponDeckTest {

    /**
     * Adds some weapons to the WeaponDeck of a player
     */
    @Test
    void addWeapon() {
        WeaponDeck wd = new WeaponDeck();

        assertTrue(wd.getSize() == 0);
        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w1 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("lock rifle");
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.addWeapon(w1);
        wd.addWeapon(w2);

        assertTrue(wd.getSize() == 2);

    }

    /**
     * Removes some weapons from the WeaponDeck of a player
     */
    @Test
    void removeWeapon() {
        WeaponDeck wd = new WeaponDeck();
        int size = wd.getSize();

        assertTrue(size == 0);

        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w1 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("lock rifle");
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.addWeapon(w1);
        wd.addWeapon(w2);
        wd.removeWeapon(0);

        assertTrue(wd.getSize() == 1);

        wd.removeWeapon(0);
        wd.removeWeapon(0);

        assertTrue(wd.getSize() == 0);              //removeWeapon on empty ArrayList

    }

    /**
     * Removes some weapons from the WeaponDeck of a player
     */
    @Test
    void removeWeapon1() {
        WeaponDeck wd = new WeaponDeck();

        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w1 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("lock rifle");
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.addWeapon(w1);
        wd.addWeapon(w2);
        wd.removeWeapon(w1);

        assertTrue(wd.getWeapon(w1.getName()) == null);       //remove weapon

        wf = new WeaponFactory("t.h.o.r.");
        Weapon w3 = new Weapon("t.h.o.r.", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.removeWeapon(w3);

        assertTrue(wd.getWeapon(w3.getName()) == null && wd.getSize() == 1);  //no problems with Weapons not in the Deck

    }

    /**
     * Gets a weapon of a player, giving numbers (indexes)
     */
    @Test
    void getWeapon() {
        WeaponDeck wd = new WeaponDeck();

        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w1 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("lock rifle");
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.addWeapon(w1);
        wd.addWeapon(w2);

        assertTrue(wd.getWeapon(0) == w1);

        assertTrue(wd.getWeapon(2) == null);            //index over the ArrayList size

        assertTrue(wd.getWeapon(-1) == null);           //index value below 0
    }

    /**
     * Gets a weapon of a player, giving the names
     */
    @Test
    void getWeapon1() {
        WeaponDeck wd = new WeaponDeck();

        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w1 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("lock rifle");
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.addWeapon(w1);
        wd.addWeapon(w2);

        assertTrue(wd.getWeapon(w1.getName()) == w1);

        wf = new WeaponFactory("vortex cannon");
        Weapon w3 = new Weapon("vortex cannon", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());

        assertTrue(wd.getWeapon(w3.getName()) == null);               //weapon not in the Deck
    }

    /**
     * Gets the first weapon of a player
     */
    @Test
    void getFirstWeapon() {
        WeaponDeck wd = new WeaponDeck();
        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w1 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("lock rifle");
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.addWeapon(w1);
        wd.addWeapon(w2);

        assertTrue(wd.getWeapon(0) == w1);

        wd.getFirstWeapon();

        assertTrue(wd.getSize() == 1);                  //check if card removed from the top of the Deck

        assertTrue(wd.getWeapon(0) == w2);          //check if card on top if the card below the removed

        wd.removeWeapon(0);

        assertTrue(wd.getFirstWeapon() == null);

    }

    /**
     * Returns the amount of weapons of a player
     */
    @Test
    void getSize() {
        WeaponDeck wd = new WeaponDeck();

        assertTrue(wd.getSize() == 0);

        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w1 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("lock rifle");
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.addWeapon(w1);
        wd.addWeapon(w2);

        assertTrue(wd.getSize() == 2);

        wd.removeWeapon(0);
        wd.removeWeapon(0);
        wd.removeWeapon(0);

        assertTrue(wd.getSize() == 0);
    }

    /**
     * Tests the shuffling of the WeaponDeck of a player
     */
    @Test
    void shuffle() {
        WeaponDeck wd = new WeaponDeck();
        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w1 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("lock rifle");
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("heatseeker");
        Weapon w3 = new Weapon("heatseeker", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("whisper");
        Weapon w4 = new Weapon("whisper", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("hellion");
        Weapon w5 = new Weapon("hellion", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("zx-2");
        Weapon w6 = new Weapon("zx-2", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wd.addWeapon(w1);
        wd.addWeapon(w2);
        wd.addWeapon(w3);
        wd.addWeapon(w4);
        wd.addWeapon(w5);
        wd.addWeapon(w6);
        for (int i = 0; i < wd.getSize(); i++) {
            System.out.print(wd.getWeapon(i).getName());
            System.out.print("\n");
        }
        System.out.print("\n");

        wd.shuffle();

        for (int i = 0; i < wd.getSize(); i++) {
            System.out.print(wd.getWeapon(i).getName());
            System.out.print("\n");
        }
    }
}