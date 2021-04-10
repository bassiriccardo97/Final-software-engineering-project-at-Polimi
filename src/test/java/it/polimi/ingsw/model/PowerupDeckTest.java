package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.card.powerup.PowerupDeck;
import it.polimi.ingsw.model.card.powerup.PowerupFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PowerupDeckTest {

    /**
     * Adds some powerups to the PowerupDeck of a player
     */
    @Test
    void addPowerup() {
        PowerupDeck pd = new PowerupDeck();

        assertTrue(pd.getSize() == 0);

        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.addPowerup(p1);
        pd.addPowerup(p2);

        assertTrue(pd.getSize() == 2);
    }

    /**
     * Removes some powerups to the PowerupDeck of a player
     */
    @Test
    void removePowerup() {
        PowerupDeck pd = new PowerupDeck();

        assertTrue(pd.getSize() == 0);

        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.addPowerup(p1);
        pd.addPowerup(p2);
        pd.removePowerup(0);

        assertTrue(pd.getSize() == 1);

        pd.removePowerup(0);
        pd.removePowerup(0);

        assertTrue(pd.getSize() == 0);
    }

    /**
     * Removes some powerups to the PowerupDeck of a player
     */
    @Test
    void removePowerup1() {
        PowerupDeck pd = new PowerupDeck();

        assertTrue(pd.getSize() == 0);

        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.addPowerup(p1);
        pd.addPowerup(p2);
        pd.removePowerup(p1);

        assertTrue(pd.getPowerup(p1.getName(), 'r') == null);

        pf = new PowerupFactory("targeting scope");
        Powerup p3 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.removePowerup(p3);

        assertTrue(pd.getPowerup(p3.getName(), 'b') == null && pd.getSize() == 1);


    }

    /**
     * Gets some powerups of a player giving numbers (indexes)
     */
    @Test
    void getPowerup() {
        PowerupDeck pd = new PowerupDeck();

        assertTrue(pd.getSize() == 0);

        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.addPowerup(p1);
        pd.addPowerup(p2);

        assertTrue(pd.getPowerup(0) == p1);

        assertTrue(pd.getPowerup(2) == null);            //null if index over the ArrayList size

        assertTrue(pd.getPowerup(-1) == null);          //null if index below 0
    }

    /**
     * Gets some powerups of a player giving names and colors
     */
    @Test
    void getPowerup1() {
        PowerupDeck pd = new PowerupDeck();

        assertTrue(pd.getSize() == 0);

        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.addPowerup(p1);
        pd.addPowerup(p2);

        assertTrue(pd.getPowerup(p1.getName(), 'r') == p1);

        pf = new PowerupFactory("targeting scope");
        Powerup p3 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());

        assertTrue(pd.getPowerup(p3.getName(), 'b') == null);                  //return null if the Powerup is not in the Deck

    }

    /**
     * Gets the first powerup of a player
     */
    @Test
    void getFirstPowerup() {
        PowerupDeck pd = new PowerupDeck();

        assertTrue(pd.getSize() == 0);

        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.addPowerup(p1);
        pd.addPowerup(p2);

        assertTrue(pd.getPowerup(0) == p1);

        pd.getFirstPowerup();

        assertTrue(pd.getSize() == 1);                  //output: card removed from the Deck

        assertTrue(pd.getPowerup(0) == p2);     //output: card below the card removed is now on top

        pd.removePowerup(0);

        assertTrue(pd.getFirstPowerup() == null);

    }

    /**
     * Gets the amount of the powerup of a player
     */
    @Test
    void getSize() {
        PowerupDeck pd = new PowerupDeck();

        assertTrue(pd.getSize() == 0);

        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.addPowerup(p1);
        pd.addPowerup(p2);

        assertTrue(pd.getSize() == 2);

        pd.removePowerup(0);
        pd.removePowerup(0);
        pd.removePowerup(0);

        assertTrue(pd.getSize() == 0);

    }

    /**
     * Tests the shuffling of a PowerupDeck
     */
    @Test
    void shuffle() {
        PowerupDeck pd = new PowerupDeck();
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("tagback grenade");
        Powerup p3 = new Powerup("tagback grenade", 'y', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("newton");
        Powerup p4 = new Powerup("newton", 'y', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("targeting scope");
        Powerup p5 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("tagback grenade");
        Powerup p6 = new Powerup("tagback grenade", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pd.addPowerup(p1);
        pd.addPowerup(p2);
        pd.addPowerup(p3);
        pd.addPowerup(p4);
        pd.addPowerup(p5);
        pd.addPowerup(p6);

        for (int i = 0; i < pd.getSize(); i++) {
            System.out.print(pd.getPowerup(i).getName());
            System.out.print("\t\t");
            System.out.print(pd.getPowerup(i).getColor());
            System.out.print("\n");
        }
        System.out.print("Shuffle: \n");

        pd.shuffle();

        for (int i = 0; i < pd.getSize(); i++) {
            System.out.print(pd.getPowerup(i).getName());
            System.out.print("\t\t");
            System.out.print(pd.getPowerup(i).getColor());
            System.out.print("\n");
        }
    }
}