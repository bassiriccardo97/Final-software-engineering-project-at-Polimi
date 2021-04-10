package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.card.powerup.PowerupDeck;
import it.polimi.ingsw.model.card.powerup.PowerupFactory;
import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.card.weapon.WeaponDeck;
import it.polimi.ingsw.model.card.weapon.WeaponFactory;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerHand;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerHandTest {

    /**
     * Tests the drawing of many weapons
     */
    @Test
    void drawWeapon() {
        SpawnpointSquare s = new SpawnpointSquare(0, 0, true, true, true, true, true, 'r');
        RealPlayer p = new RealPlayer('b', "p");
        PlayerHand ph = p.getPh();
        WeaponFactory wf = new WeaponFactory("lock rifle");
        Weapon w1 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("electroscythe");
        Weapon w2 = new Weapon("electroscythe", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("machine gun");
        Weapon w3 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("tractor beam");
        Weapon w4 = new Weapon("tractor beam", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.getWeapons().add(w1);
        wd.getWeapons().add(w2);
        wd.getWeapons().add(w3);
        ph.drawWeapon(wd, w1.getName());
        //assertTrue(p.getPh().getWeaponDeck().getWeapon(w1.getName()) == w1);
        ph.drawWeapon(wd, w2.getName());
        //assertTrue(p.getPh().getWeaponDeck().getWeapon(w2.getName()) == w2);
        ph.drawWeapon(wd, w3.getName());
        //assertTrue(p.getPh().getWeaponDeck().getWeapon(w3.getName()) == w3);
        wd.getWeapons().add(w4);
        ph.drawWeapon(wd, w4.getName());

    }

    /**
     * Tests the drawing of many weapons
     */
    @Test
    void drawPowerup() {
        SpawnpointSquare s = new SpawnpointSquare(0, 0, true, true, true, true, true, 'r');
        RealPlayer p = new RealPlayer('b', "p");
        p.getPh().getPowerupDeck().getPowerups().clear();
        PowerupFactory pf = new PowerupFactory("newton");
        Powerup p1 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("targeting scope");
        Powerup p2 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("tagback grenade");
        Powerup p3 = new Powerup("tagback grenade", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        pf = new PowerupFactory("teleporter");
        Powerup p4 = new Powerup("teleporter", 'y', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        PowerupDeck pud = new PowerupDeck();
        pud.getPowerups().add(p1);
        pud.getPowerups().add(p2);
        pud.getPowerups().add(p3);
        pud.getPowerups().add(p4);
        p.getPh().drawPowerup(pud);
        assertTrue(p.getPh().getPowerupDeck().getPowerup(p1.getName(), p1.getColor()) == p1);
        pud.getPowerup(p1.getName(), p1.getColor());
        assertTrue(pud.getPowerup(p2.getName(), p2.getColor()) == p2);
        p.getPh().drawPowerup(pud);
        p.getPh().drawPowerup(pud);
        p.getPh().drawPowerup(pud);
    }

    /**
     * Tests the discarding and the picking up of some weapons
     */
    @Test
    void change() {
        SpawnpointSquare s = new SpawnpointSquare(0, 0, true, true, true, true, true, 'r');
        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon choice = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        Weapon w2 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        RealPlayer p = new RealPlayer('b', "p");
        p.getPh().getWeaponDeck().addWeapon(choice);
        wf = new WeaponFactory("tractor beam");
        Weapon choice2 = new Weapon("tractor beam", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        s.getWeaponSpawnpoint().addWeapon(choice2);
        p.getPh().change(s, choice2.getName(), choice);
        assertTrue(p.getPh().getWeaponDeck().getWeapon(choice2.getName()) == choice2 && s.getWeaponSpawnpoint().getWeapon(choice.getName()) == choice);
    }

    /**
     * Tests the discarding of some weapons
     */
    @Test
    void discard() {
        SpawnpointSquare s = new SpawnpointSquare(0, 0, true, true, true, true, true, 'r');
        RealPlayer p = new RealPlayer('b', "ciccia");
        PowerupDeck pud = new PowerupDeck();
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().addPowerup(p1);
        p.getPh().discard(p1);
        assertTrue(p.getPh().getPowerupDeck().getSize() == 0);
        assertTrue(Board.getDiscardedPowerups().getPowerups().contains(p1));
    }

    /**
     * Gets the amount for color of the powerup a player has in his PlayerBoard
     */
    @Test
    void numPowerupForColor() {
        RealPlayer p = new RealPlayer('b', "p");
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        Powerup p2 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());

        p.getPh().getPowerupDeck().addPowerup(p1);
        p.getPh().getPowerupDeck().addPowerup(p2);

        assertTrue(p.getPh().numPowerupForColor()[0] == 1 && p.getPh().numPowerupForColor()[1] == 1 && p.getPh().numPowerupForColor()[2] == 0);

    }

    /**
     * Gets the amount of a specific color of the powerup a player has in his PlayerBoard
     */
    @Test
    void numPowerupColor() {
        RealPlayer p = new RealPlayer('b', "p");
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        Powerup p2 = new Powerup("targeting scope", 'r', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());

        p.getPh().getPowerupDeck().addPowerup(p1);
        p.getPh().getPowerupDeck().addPowerup(p2);

        assertTrue(p.getPh().numPowerupColor('b') == 1 && p.getPh().numPowerupColor('r') == 1 && p.getPh().numPowerupColor('y') == 0 && p.getPh().numPowerupColor('q') == 0);

    }

    /**
     * Tests if a player has a specific powerup
     */
    @Test
    void hasPowerup() {
        RealPlayer p = new RealPlayer('b', "p");
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().getPowerups().add(p1);

        assertTrue(p.getPh().hasPowerup("targeting scope"));
        assertFalse(p.getPh().hasPowerup("newton"));
    }

    /**
     * Tests if a player has an always usable powerup
     */
    @Test
    void hasAlwaysUsablePowerup() {
        RealPlayer p = new RealPlayer('b', "p");
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().getPowerups().add(p1);

        assertFalse(p.getPh().hasAlwaysUsablePowerup());

        pf = new PowerupFactory("teleporter");
        Powerup p2 = new Powerup("teleporter", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().getPowerups().add(p2);

        assertTrue(p.getPh().hasAlwaysUsablePowerup());
    }

    /**
     * Tests if a player has a specific powerup of a specific color
     */
    @Test
    void hasPowerupColor() {
        RealPlayer p = new RealPlayer('b', "p");
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().getPowerups().add(p1);

        assertFalse(p.getPh().hasPowerupColor("targeting scope", "red"));
        assertFalse(p.getPh().hasPowerupColor("newton", "blue"));
        assertTrue(p.getPh().hasPowerupColor("targeting scope", "blue"));
    }

    /**
     * Tests if a player has at least an unloaded weapon
     */
    @Test
    void unloadedWeapons() {
        ArrayList<Player> array = new ArrayList<>();
        AlphaGame game = new AlphaGame(1, array, false, 5);
        RealPlayer rp = new RealPlayer('b', "rp");
        rp.setPlayerPosition(Board.getSpawnpoint('b'));
        array.add(rp);
        WeaponFactory wf = new WeaponFactory("zx-2");
        Weapon w = new Weapon("zx-2", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("heatseeker");
        Weapon w1 = new Weapon("heatseeker", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        rp.getPh().getWeaponDeck().getWeapons().add(w);
        rp.getPh().getWeaponDeck().getWeapons().add(w1);

        w.setLoaded(false);
        w1.setLoaded(true);

        assertTrue(rp.getPh().unloadedWeapons());

        w.setLoaded(true);
        w1.setLoaded(true);

        assertFalse(rp.getPh().unloadedWeapons());

    }

    /**
     * Tests if a player has at least a loaded weapon
     */
    @Test
    void loadedWeapons() {
        ArrayList<Player> array = new ArrayList<>();
        AlphaGame game = new AlphaGame(1, array, false, 5);
        RealPlayer rp = new RealPlayer('b', "rp");
        rp.setPlayerPosition(Board.getSpawnpoint('b'));
        array.add(rp);
        WeaponFactory wf = new WeaponFactory("zx-2");
        Weapon w = new Weapon("zx-2", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("heatseeker");
        Weapon w1 = new Weapon("heatseeker", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        rp.getPh().getWeaponDeck().getWeapons().add(w);
        rp.getPh().getWeaponDeck().getWeapons().add(w1);

        w.setLoaded(false);
        w1.setLoaded(true);

        assertTrue(rp.getPh().loadedWeapons());

        w.setLoaded(false);
        w1.setLoaded(false);

        assertFalse(rp.getPh().loadedWeapons());

    }

    /**
     * Tests if a player has a single powerup
     */
    @Test
    void singlePowerupDetector() {
        RealPlayer p = new RealPlayer('b', "p");
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().getPowerups().add(p1);

        Powerup pu = p.getPh().singlePowerupDetector("targeting scope");
        Powerup pu1 = p.getPh().singlePowerupDetector("newton");

        assertTrue(pu == p1 && pu1 == null);

    }

    /**
     * Tests if a player has a single powerup of a specific color
     */
    @Test
    void singlePowerupColorDetector() {
        RealPlayer p = new RealPlayer('b', "p");
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().getPowerups().add(p1);

        Powerup pu = p.getPh().singlePowerupColorDetector('b');
        Powerup pu1 = p.getPh().singlePowerupColorDetector('r');

        assertTrue(pu == p1 && pu1 == null);
    }

    /**
     * Tests if a player has a single always usable powerup
     */
    @Test
    void usablePowerupDetector(){
        RealPlayer p = new RealPlayer('b', "p");
        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p1 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().getPowerups().add(p1);

        assertFalse(p.getPh().usablePowerupsDetector("newton"));

        pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        p.getPh().getPowerupDeck().getPowerups().add(p2);

        assertTrue(p.getPh().usablePowerupsDetector("newton"));
    }


}