package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.card.weapon.WeaponFactory;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpawnpointSquareTest {

    /**
     * Adds some weapons to the SpawnPoint
     */
    @Test
    void addSpawnpointWeapon() {
        SpawnpointSquare s = new SpawnpointSquare(0, 0, true, false, false, true, true, 'b');
        WeaponFactory wf = new WeaponFactory("lock rifle");
        Weapon w1 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("machine gun");
        Weapon w2 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("whisper");
        Weapon w3 = new Weapon("whisper", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        s.addSpawnpointWeapon(w1);
        s.addSpawnpointWeapon(w2);
        s.addSpawnpointWeapon(w3);
        assertTrue(s.getWeaponSpawnpoint().getWeapon(w1.getName()) == w1 && s.getWeaponSpawnpoint().getWeapon(w2.getName()) == w2 && s.getWeaponSpawnpoint().getWeapon(w3.getName()) == w3);
    }

    /**
     * Removes some weapons to the SpawnPoint
     */
    @Test
    void removeWeapon() {
        SpawnpointSquare s = new SpawnpointSquare(0, 0, true, false, false, true,true, 'b');
        WeaponFactory wf = new WeaponFactory("lock rifle");
        Weapon w1 = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("machine gun");
        Weapon w2 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("whisper");
        Weapon w3 = new Weapon("whisper", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("t.h.o.r.");
        Weapon w4 = new Weapon("t.h.o.r.", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        wf = new WeaponFactory("vortex cannon");
        Weapon w5 = new Weapon("vortex cannon", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        s.addSpawnpointWeapon(w1);
        s.addSpawnpointWeapon(w2);
        s.addSpawnpointWeapon(w3);
        s.removeWeapon(w2);

        assertTrue(s.getWeaponSpawnpoint().getWeapon(0) == w1 && s.getWeaponSpawnpoint().getWeapon(1) == w3);

        s.removeWeapon(w4);
        s.removeWeapon(w5);
    }
}