package it.polimi.ingsw.model;

import java.util.*;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.game.squares.Square;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    /**
     * Removes a skull from the killShotTrack
     */
    @Test
    void removeSkull() {
        ArrayList<Player> p = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, p,false, 8);

        Board.removeSkull();
        int r = Board.getKillShotTrack();

        assertTrue(r==7);

        Board.removeSkull();
        Board.removeSkull();
        Board.removeSkull();

        r = Board.getKillShotTrack();

        assertTrue(r==4);

        Board.removeSkull();
        Board.removeSkull();
        Board.removeSkull();
        Board.removeSkull();
        Board.removeSkull();            //output: End of the game!
        r = Board.getKillShotTrack();

        assertTrue(r==-1);
    }

    /**
     * Tests the getter of the SpawnPoints, giving the color
     */
    @Test
    void getSpawnpoint() {
        ArrayList<Player> p = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, p,false, 8);

        int r = Board.getSpawnpoint('b').getNum();

        assertTrue(r==2);

        r = Board.getSpawnpoint('r').getNum();

        assertTrue(r==4);

        r = Board.getSpawnpoint('y').getNum();

        assertTrue(r==11);

        SpawnpointSquare s = Board.getSpawnpoint('h');       //output: wrong value
    }

    /**
     * Tests the getter of the SpawnPoints, giving the number
     */
    @Test
    void getSpawnpoint1() {
        ArrayList<Player> p = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, p,false, 8);

        int r = Board.getSpawnpoint(2).getNum();

        assertTrue(r==2);

        r = Board.getSpawnpoint(4).getNum();

        assertTrue(r==4);

        r = Board.getSpawnpoint(11).getNum();

        assertTrue(r==11);

        SpawnpointSquare s = Board.getSpawnpoint(5);       //output: wrong value
    }

    /**
     * Tests the getter of the Square, giving the number
     */
    @Test
    void getSquare() {
        int r = Board.getSquare(0).getNum();

        assertTrue(r==0);

        r = Board.getSquare(6).getNum();

        assertTrue(r==6);

        r = Board.getSquare(10).getNum();

        assertTrue(r==10);

        Square s = Board.getSquare(2);       //output: wrong value
    }

    /**
     * Tests the visibility in case of run
     */
    @Test
    void isVisibleRun(){
        int r = Board.isVisibleRun(Board.getSquare(0), Board.getSquare(1));

        assertTrue(r==1);

        r = Board.isVisibleRun(Board.getSquare(1), Board.getSpawnpoint('b'));

        assertTrue(r==1);

        r = Board.isVisibleRun(Board.getSpawnpoint('b'), Board.getSquare(3));

        assertTrue(r==1);

        r = Board.isVisibleRun(Board.getSquare(3), Board.getSquare(7));

        assertTrue(r==1);

        r = Board.isVisibleRun(Board.getSpawnpoint('b'), Board.getSquare(7));

        assertTrue(r==2);

        r = Board.isVisibleRun(Board.getSpawnpoint('r'), Board.getSquare(5));

        assertTrue(r==1);

        r = Board.isVisibleRun(Board.getSquare(9), Board.getSpawnpoint('y'));

        assertTrue(r==2);

        r = Board.isVisibleRun(Board.getSquare(5), Board.getSquare(6));

        assertTrue(r==3);

        r = Board.isVisibleRun(Board.getSquare(5), Board.getSquare(9));

        assertTrue(r==1);

        r = Board.isVisibleRun(Board.getSpawnpoint('b'), Board.getSquare(10));

        assertTrue(r==2);

        r = Board.isVisibleRun(Board.getSquare(8), Board.getSquare(9));

        assertTrue(r==-1);

        r = Board.isVisibleRun(Board.getSquare(5), Board.getSquare(8));

        assertTrue(r==-1);

        r = Board.isVisibleRun(Board.getSquare(0), Board.getSquare(10));

        assertTrue(r==-1);

        r = Board.isVisibleRun(Board.getSquare(0), Board.getSquare(0));

        assertTrue(r==0);

        r = Board.isVisibleRun(Board.getSquare(5), Board.getSpawnpoint('y'));

        assertTrue(r == 3);

        Square s1 = new Square(1, 1, false, false, true, false, true, 'b');
        Square s2 = new Square(0, 1, false, false, true, true, false, 'b');
        r = Board.isVisibleRun(s1, s2);

        assertTrue(r==1);
    }

    /**
     * Tests the visibility in case of shoot
     */
    @Test
    void isVisibleShoot(){
        int r = Board.isVisibleShoot(Board.getSquare(9), Board.getSpawnpoint('y'));
        assertTrue(r == 2);
    }

    /**
     * Tests an eventual fixed direction which links two GenericSquares
     */
    @Test
    void fixedDirectionReachable(){
        ArrayList<Player> p = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, p,false, 8);

        boolean r = Board.fixedDirectionReachable(Board.getSpawnpoint('b'), Board.getSquare(0));

        assertTrue(r);

        r = Board.fixedDirectionReachable(Board.getSquare(3), Board.getSpawnpoint('y'));

        assertTrue(r);

        r = Board.fixedDirectionReachable(Board.getSquare(0), Board.getSquare(5));

        assertTrue(!r);

        r = Board.fixedDirectionReachable(Board.getSquare(9), Board.getSpawnpoint('y'));

        assertTrue(r);

        r = Board.fixedDirectionReachable(Board.getSquare(10), Board.getSpawnpoint('b'));

        //assertTrue(!r);
    }

    /**
     * Tests an eventual fixed direction which links two GenericSquares, showing the numbers of Genericsquares between
     */
    @Test
    void genericFixedDirection(){
        int r = Board.genericFixedDirection(Board.getSpawnpoint('b'), Board.getSquare(0));

        assertTrue(r == 2);

        r = Board.genericFixedDirection(Board.getSquare(1), Board.getSquare(9));

        assertTrue(r == 2);

        r = Board.genericFixedDirection(Board.getSpawnpoint(4), Board.getSquare(7));

        assertTrue(r == 3);

        r = Board.genericFixedDirection(Board.getSquare(10), Board.getSpawnpoint(2));

        assertTrue(r == 2);

        r = Board.genericFixedDirection(Board.getSquare(3), Board.getSquare(6));

        assertTrue(r == -1);

        r = Board.genericFixedDirection(Board.getSpawnpoint('r'), Board.getSquare(9));

        assertTrue(r==-1);
    }

    /**
     * Tests all the squares belonging to a room
     */
    @Test
    void squareOfARoom(){
        ArrayList<GenericSquare> g = Board.squareOfARoom(1);
        int r = g.get(0).getNum();

        assertTrue(r==2);

        r = g.get(2).getNum();

        assertTrue(r==1);

        g = Board.squareOfARoom(2);
        r = g.get(0).getNum();

        assertTrue(r==3);

        g = Board.squareOfARoom(5);
        r = g.get(0).getNum();

        assertTrue(r==9);

        g = Board.squareOfARoom(4);
        r = g.get(3).getNum();

        assertTrue(r==10);
    }
}