package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.game.squares.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenericSquareTest {

    /**
     * Adds some players to GenericSquares on the Board
     */
    @Test
    void addPlayer() {
        Square s = new Square(0, 4, false, true, false, true, false, 'b');
        Player p = new RealPlayer('b', "ciccia");
        Player p1 = new RealPlayer('r', "ciccia");
        s.addPlayer(p);
        s.addPlayer(p1);
        assertTrue(s.getPlayers().get(0) == p && s.getPlayers().get(1) == p1);
        SpawnpointSquare s1 = new SpawnpointSquare(0, 4, true, true, false, true, false, 'b');
        Player p2 = new RealPlayer('v', "ciccia");
        Player p3 = new RealPlayer('g', "ciccia");
        s1.addPlayer(p2);
        s1.addPlayer(p3);
        assertTrue(s1.getPlayers().get(0) == p2 && s1.getPlayers().get(1) == p3);
    }

    /**
     * Removes some players from GenericSquares on the Board
     */
    @Test
    void removePlayer() {
        Square s = new Square(0, 4, false, true, false, true, false, 'b');
        RealPlayer p = new RealPlayer('b', "ciccia");
        RealPlayer p1 = new RealPlayer('r', "ciccia");
        s.addPlayer(p);
        s.addPlayer(p1);
        s.removePlayer(p1);
        assertTrue(s.getPlayers().get(0) == p && s.getPlayers().size() == 1);
        SpawnpointSquare s1 = new SpawnpointSquare(0, 4, true, true, false, true, false, 'b');
        RealPlayer p2 = new RealPlayer('v', "ciccia");
        RealPlayer p3 = new RealPlayer('g', "ciccia");
        s1.addPlayer(p2);
        s1.addPlayer(p3);
        s1.removePlayer(p2);
        assertTrue(s1.getPlayers().get(0) == p3 && s1.getPlayers().size() == 1);
    }

    /**
     * Tests the adjacency of some GenericSquares
     */
    @Test
    void isAdjacent() {
        Square s = new Square(0, 0, false, false, true, true, false, 'r');
        Square s1 = new Square(1, 1, false, false, true, false, true, 'b');
        assertTrue(s1.isAdjacent(s) == 'o');
        SpawnpointSquare s2 = new SpawnpointSquare(6, 3, true, false, false, true, true, 'b');
        SpawnpointSquare s3 = new SpawnpointSquare(7, 4, true, true,  false, true, false,'y');
        assertTrue(s2.isAdjacent(s3)=='e'); //even ther's a door in beetween, they're adiacent
        assertTrue(s3.isAdjacent(s) == '\u0000'); //they're not adiacent
    }

    /**
     * Tests the reachability of some GenericSquares
     */
    @Test
    void isReachable() {
        Square s = new Square(0, 0, false, false, true, true, false, 'r');
        Square s1 = new Square(1, 1, false, false, true, false, true, 'b');
        assertTrue(s1.isReachable(s));
        SpawnpointSquare s2 = new SpawnpointSquare(6, 3, true, false, false, true, true, 'b');
        SpawnpointSquare s3 = new SpawnpointSquare(7, 4, true, true,  false, true, false,'y');
        assertTrue(!s3.isReachable(s2));
        SpawnpointSquare s4 = new SpawnpointSquare(7, 3, true, false, false, true, true, 'b');
        Square s5 = new Square(0, 0, false, false, true, true, false, 'r');
        assertTrue(!s4.isReachable(s5));

    }

    /**
     * Tests existing GenericSquares or not
     */
    @Test
    void checkExistingSquare() {
        Square s = new Square(0, 0, false, false, false, false, false, 't');
        try{
            GenericSquare.checkExistingSquare(s);
        }catch (WrongSquareException wSE){
            System.out.println("Exception caught");
        }
    }
}