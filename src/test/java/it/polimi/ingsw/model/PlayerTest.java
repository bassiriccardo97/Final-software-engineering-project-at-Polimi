package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


class PlayerTest {

    /**
     * Tests the run normal action of a player
     */
    @Test
    void run() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        try {
            Player p = new Player('b', "ciccia");
            p.setPlayerPosition(Board.getSquare(0));
            p.run(Board.getSquare(1));
            assertTrue(p.getPlayerPosition().getNum() == 1);
            p.run(Board.getSquare(5));
            assertTrue(p.getPlayerPosition().getNum() == 5);
            p.run(Board.getSpawnpoint('b'));    //exception
            assertTrue(p.getPlayerPosition().getNum() == 5);

        }catch (WrongSquareException ws){
            //empty
        }


    }

    /**
     * Tests the run frenetic action of a player
     */
    @Test
    void run4(){
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        Player p = new Player('b', "ciccia");

        try {
            p.setPlayerPosition(Board.getSquare(0));
            p.run4(Board.getSquare(10));
            assertTrue(p.getPlayerPosition().getNum() == 10);
            p.run4(Board.getSpawnpoint('b'));
            assertTrue(p.getPlayerPosition().getNum() == 2);
            p.run4(Board.getSquare(7));
            assertTrue(p.getPlayerPosition().getNum() == 7);
            p.run4(Board.getSpawnpoint('r'));                           //exception
        }catch (WrongSquareException ws){
            assertTrue(p.getPlayerPosition().getNum() == 7);
        }
    }
}