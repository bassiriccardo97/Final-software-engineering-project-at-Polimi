package it.polimi.ingsw.model;

import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerBoardTest {

    /**
     * Gets the amount of damages of a player, giving a number linked with the color (in alphabetical order)
     */
    @Test
    void getDamage() {
        PlayerBoard pb = new PlayerBoard();
        char c = pb.getDamage(0);

        assertTrue(c=='\u0000');

        pb.addDamage('b');
        c = pb.getDamage(0);

        assertTrue(c=='b');

        c = pb.getDamage(15);       //exception

        assertTrue(c=='\u0000');
    }

    /**
     * Counts the amount of damages of a players
     */
    @Test
    void countDamages() {
        PlayerBoard pb = new PlayerBoard();

        int r = pb.countDamages();

        assertTrue(r==0);

        for(int i = 0; i < 8; i++)
            pb.addDamage('b');
        r = pb.countDamages();

        assertTrue(r==8);
    }

    /**
     * Adds some damages to a player
     */
    @Test
    void addDamage() {
        PlayerBoard pb = new PlayerBoard();
        pb.addDamage('b');
        char c = pb.getDamage(0);

        assertTrue(c=='b');

        pb.addDamage('f');      //exception --> return \u0000
        c = pb.getDamage(1);

        assertTrue(c=='\u0000');

        pb.addDamage('y');
        c = pb.getDamage(1);

        assertTrue(c=='y');
    }

    /**
     * Gets the amount of marks of a player giving the color
     */
    @Test
    void getMarkedDamages() {
        PlayerBoard pb = new PlayerBoard();
        int r = pb.getMarkedDamages('b');

        assertTrue(r==0);

        pb.addMarkedDamage('g');
        r = pb.getMarkedDamages('g');

        assertTrue(r==1);
    }

    /**
     * Adds some marks to a player
     */
    @Test
    void addMarkedDamage() {
        PlayerBoard pb = new PlayerBoard();
        pb.addMarkedDamage('b');
        pb.addMarkedDamage('b');
        int r = pb.getMarkedDamages('b');

        assertTrue(r==2);

        pb.addMarkedDamage('g');
        r = pb.getMarkedDamages('g');

        assertTrue(r==1);

        pb.addMarkedDamage('b');        //exception
        r = pb.getMarkedDamages('b');

        assertTrue(r==3);

        pb.addMarkedDamage('y');        //exception
        r = pb.getMarkedDamages('y');

        assertTrue(r==1);

        r = pb.getMarkedDamages('f');       //exception

        assertTrue(r==-1);
    }

    /**
     * Returns the amount of the ammo in the AmmoBox of a player
     */
    @Test
    void getAmmo() {
        PlayerBoard pb = new PlayerBoard();
        int[] ammo = {3, 3, 3};
        pb.grabAmmo(ammo);
        int r = pb.getAmmo('b');

        assertTrue(r==3);

        pb.addAmmo('b');            //exception
        r = pb.getAmmo('b');

        assertTrue(r==3);

        r = pb.getAmmo('k');        //exception

        assertTrue(r==-1);

    }

    /**
     * Adds some ammo in the AmmoBox of a player
     */
    @Test
    void addAmmo() {
        PlayerBoard pb = new PlayerBoard();
        int[] ammo = {3, 3, 3};
        pb.grabAmmo(ammo);
        int r = pb.getAmmo('b');

        assertTrue(r==3);

        pb.addAmmo('b');        //exception
        r = pb.getAmmo('b');

        assertTrue(r==3);

        pb.addAmmo('w');        //exception
        char[] c = {'b', 'b'};
        pb.payAmmo(c);
        pb.addAmmo('b');
        r = pb.getAmmo('b');

        assertTrue(r==2);
    }

    /**
     * Tests if a player has been marked or not
     */
    @Test
    void isMarked() {
        PlayerBoard pb = new PlayerBoard();
        boolean r = pb.isMarked();

        assertTrue(!r);

        pb.addMarkedDamage('b');
        r = pb.isMarked();

        assertTrue(r);
    }

    /**
     * Tests of afterDeath, updating the partial scores
     */
    @Test
    void afterDeath(){
        AlphaGame game = new AlphaGame(1, new ArrayList<Player>(), false, 5);
        RealPlayer b = new RealPlayer('b', "blue");
        RealPlayer e = new RealPlayer('e', "emerald");
        RealPlayer g = new RealPlayer('g', "grey");
        RealPlayer v = new RealPlayer('v', "violet");
        RealPlayer y = new RealPlayer('y', "yellow");
        AlphaGame.getPlayers().add(b);
        AlphaGame.getPlayers().add(e);
        AlphaGame.getPlayers().add(g);
        AlphaGame.getPlayers().add(v);
        AlphaGame.getPlayers().add(y);
        PlayerBoard pb = new PlayerBoard();
        AlphaGame.resetScore();
        //System.out.println("initial point of b: " + AlphaGame.getPlayersPoint()[0]);
        pb.addDamage('b');
        pb.addDamage('b');
        pb.addDamage('e');
        pb.addDamage('g');
        pb.addDamage('g');
        pb.addDamage('g');
        pb.addDamage('b');
        pb.addDamage('y');
        pb.addDamage('e');
        pb.addDamage('y');
        pb.addDamage('g');
        pb.afterDeath();            // 0b: 3, 1e: 2, 2g: 4, 3v: 0, 4y: 2  --> b = 6 + 1, e = 4, g = 8, v = 0, y = 2
        //for (int i = 0; i < 5; i++)
        //    System.out.println(AlphaGame.getPlayersPoint()[i]);
        int r = AlphaGame.getPlayersPoint()[0];

        assertTrue(r == 7);

        r = AlphaGame.getPlayersPoint()[1];

        assertTrue(r == 4);

        r = AlphaGame.getPlayersPoint()[2];

        //System.out.println(r);
        assertTrue(r == 8);

        r = AlphaGame.getPlayersPoint()[3];

        assertTrue(r == 0);

        r = AlphaGame.getPlayersPoint()[4];

        assertTrue(r == 2);

        //AlphaGame.resetScore();
        pb.addDamage('y');
        pb.addDamage('g');
        pb.addDamage('e');
        pb.addDamage('g');
        pb.addDamage('b');
        pb.addDamage('g');
        pb.addDamage('b');
        pb.addDamage('y');
        pb.addDamage('e');
        pb.addDamage('b');
        pb.addDamage('v');
        pb.addDamage('v');
        pb.afterDeath();            // 0b: 3, 1e: 2, 2g: 3, 3v: 2, 4y: 2 --> b = 4, e = 1, g = 6, v = 1, y = 2 + 1
        r = AlphaGame.getPlayersPoint()[0];

        //System.out.println(r);
        assertTrue(r == 11);     //11

        r = AlphaGame.getPlayersPoint()[1];

        //System.out.println(r);
        assertTrue(r == 5);     //5

        r = AlphaGame.getPlayersPoint()[2];

        //System.out.println(r);
        assertTrue(r == 14);    //14

        r = AlphaGame.getPlayersPoint()[3];

        //System.out.println(r);
        assertTrue(r == 1);     //1

        r = AlphaGame.getPlayersPoint()[4];

        //System.out.println(r);
        assertTrue(r == 5);     //5
    }

    /**
     * Pays a cost decreasing the amount of ammo in the AmmoBox
     */
    @Test
    void payAmmo() {
        char[] cost = new char[3];
        cost[0]='b';
        cost[1]='b';
        cost[2]='r';
        RealPlayer p = new RealPlayer('b', "ciccia");
        int[] ammo = {3, 3, 3};
        p.getPb().grabAmmo(ammo);
        p.getPb().payAmmo(cost);

        assertTrue(p.getPb().getAmmo('b')==1 && p.getPb().getAmmo('r')==2 && p.getPb().getAmmo('y')==3);
    }
}