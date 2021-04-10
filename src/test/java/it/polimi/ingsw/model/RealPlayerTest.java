package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.ammotile.AmmoTile;
import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.card.powerup.PowerupDeck;
import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.card.weapon.WeaponFactory;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RealPlayerTest {

    /**
     * Tests the update of the score of a player
     */
    @Test
    void updateScore() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setTurn(true);
        p.updateScore(3);
        assertTrue(p.getScore()==3);

        p = new RealPlayer('b', "ciccia");
        p.updateScore(-1);
        assertTrue(p.getScore()==0);
    }

    /**
     * Tests the grab action of a player (
     */
    @Test
    void grab() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        Square s = new Square(0, 4, false, true, false, true, false, 'b');
        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setPlayerPosition(s);
        p.getPh().getPowerupDeck().getPowerups().clear();
        p.setTurn(true);
        AmmoTile a = new AmmoTile();
        PowerupDeck pud = new PowerupDeck();
        int[] cubes = new int[3];
        cubes[0] = 2;
        cubes[2] = 1;
        char[] c1 = {'b', 'b', 'b'};
        char[] c2 = {'r', 'r', 'r'};
        char[] c3 = {'y', 'y', 'y'};
        p.getPb().payAmmo(c1);
        p.getPb().payAmmo(c2);
        p.getPb().payAmmo(c3);
        a.setCubes(cubes);
        a.setPowerup(false);
        s.setAmmo(a);
        try{
            p.grab(p.getPlayerPosition(), pud);
        }catch (WrongSquareException e){}
    }

    /**
     * Tests the reload action of a player
     */
    @Test
    void reload() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setTurn(true);
        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        w.setLoaded(false);
        p.getPh().getWeaponDeck().addWeapon(w);
        p.reload(w);
        assertTrue(p.getPh().getWeaponDeck().getWeapon(w.getName()).getLoaded());
    }

    /**
     * Tests the respawn of a player (setting of his position)
     */
    @Test
    void initPosition() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        RealPlayer p = new RealPlayer('b', "ciccia");
        p.initPlayer();

        assertTrue(p.getPh().getPowerupDeck().getSize() == 2);

        Powerup pu = p.getPh().getPowerupDeck().getPowerups().get(0);
        p.initPosition(p.getPh().getPowerupDeck().getPowerups().get(0).getName(), p.getPh().getPowerupDeck().getPowerups().get(0).getColor() );

        assertTrue(p.getPh().getPowerupDeck().getSize() == 1 && Board.getSpawnpoint(p.getPlayerPosition().getNum()).getColor() == pu.getColor());
    }

    /**
     * Tests the check of the ammo resources compared to a cost
     */
    @Test
    void checkAmmoResources() {
        ArrayList<Player> array = new ArrayList<>();
        AlphaGame game = new AlphaGame(1, array, false, 5);
        RealPlayer rp = new RealPlayer('b', "rp");
        rp.setPlayerPosition(Board.getSpawnpoint('b'));
        array.add(rp);
        char[] cost = new char[3];
        cost[0] = 'b';
        cost[1] = 'r';

        assertTrue(rp.checkAmmoResources(cost));

        cost[0] = 'b';
        cost[1] = 'b';

        assertFalse(rp.checkAmmoResources(cost));
    }

    /**
     * Tests the check of the powerup resources compared to a cost
     */
    @Test
    void checkPowerupResources() {
        ArrayList<Player> array = new ArrayList<>();
        AlphaGame game = new AlphaGame(1, array, false, 5);
        RealPlayer rp = new RealPlayer('b', "rp");
        rp.setPlayerPosition(Board.getSpawnpoint('b'));
        array.add(rp);
        char[] cost = new char[3];
        cost[0] = 'b';
        cost[1] = 'r';
        Powerup p = new Powerup("tagback grenade", 'b', false, false, false, false, null, null, null);
        Powerup p1 = new Powerup("tagback grenade", 'r', false, false, false, false, null, null, null);
        Powerup p2 = new Powerup("tagback grenade", 'y', false, false, false, false, null, null, null);
        rp.getPh().getPowerupDeck().addPowerup(p);
        rp.getPh().getPowerupDeck().addPowerup(p1);
        rp.getPh().getPowerupDeck().addPowerup(p2);

        assertTrue(rp.checkAmmoResources(cost));

        cost[0] = 'b';
        cost[1] = 'b';

        assertFalse(rp.checkAmmoResources(cost));

    }

    /**
     * Tests the check of the ammo and powerup resources compared to a cost
     */
    @Test
    void checkAmmoPowerupResources() {
        ArrayList<Player> array = new ArrayList<>();
        AlphaGame game = new AlphaGame(1, array, false, 5);
        RealPlayer rp = new RealPlayer('b', "rp");
        rp.setPlayerPosition(Board.getSpawnpoint('b'));
        array.add(rp);
        char[] cost = new char[3];
        cost[0] = 'b';
        cost[1] = 'r';

        assertFalse(rp.checkAmmoPowerupResources(cost));

        Powerup p = new Powerup("tagback grenade", 'b', false, false, false, false, null, null, null);
        Powerup p1 = new Powerup("tagback grenade", 'r', false, false, false, false, null, null, null);
        Powerup p2 = new Powerup("tagback grenade", 'y', false, false, false, false, null, null, null);
        rp.getPh().getPowerupDeck().addPowerup(p);
        rp.getPh().getPowerupDeck().addPowerup(p1);
        rp.getPh().getPowerupDeck().addPowerup(p2);

        assertTrue(rp.checkAmmoPowerupResources(cost));

        cost[0] = 'b';
        cost[1] = 'b';

        assertTrue(rp.checkAmmoPowerupResources(cost));
    }

    /**
     * Tests the check of the ammo and powerup resources compared to a weapon cost
     */
    @Test
    void checkAllResources() {
        ArrayList<Player> array = new ArrayList<>();
        AlphaGame game = new AlphaGame(1, array, false, 5);
        RealPlayer rp = new RealPlayer('b', "rp");
        rp.setPlayerPosition(Board.getSpawnpoint('b'));
        array.add(rp);
        WeaponFactory wf = new WeaponFactory("zx-2");
        Weapon w = new Weapon("zx-2", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        w.setLoaded(false);
        rp.getPh().getWeaponDeck().getWeapons().add(w);

        assertTrue(rp.checkAllResources());

        wf = new WeaponFactory("heatseeker");
        Weapon w1 = new Weapon("heatseeker", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());

        w.setLoaded(true);
        w1.setLoaded(false);
        rp.getPh().getWeaponDeck().getWeapons().add(w1);

        assertFalse(rp.checkAllResources());


        w.setLoaded(false);
        w1.setLoaded(false);

        assertTrue(rp.checkAllResources());

        Powerup p2 = new Powerup("tagback grenade", 'r', false, false, false, false, null, null, null);
        rp.getPh().getPowerupDeck().getPowerups().add(p2);
        w.setLoaded(true);
        w1.setLoaded(false);

        assertTrue(rp.checkAllResources());

    }
}
