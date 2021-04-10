package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.player.Terminator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


class TerminatorTest {

    /**
     * Tests the shoot of the Terminator
     */
    @Test
    void shoot() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        RealPlayer owner = new RealPlayer('r', "ciccia");
        RealPlayer victim = new RealPlayer('y', "ciccia");
        Terminator t = new Terminator('b', Board.getSpawnpoint('b'));
        t.setOwnerColor(owner.getColor());
        victim.setPlayerPosition(Board.getSquare(1));
        owner.setPlayerPosition(Board.getSquare(0));
        try{
            t.shoot(victim);
        }catch (WrongPlayerException wPE){ }

        assertTrue(victim.getPb().getDamage(0) == 'b');

        t = new Terminator('b', Board.getSquare(0));
        t.setOwnerColor(owner.getColor());
        victim.setPlayerPosition(Board.getSquare(10));
        try{
            t.shoot(victim);
        }catch (WrongPlayerException wPE){ }

        assertTrue(victim.getPb().countDamages() == 1);

        try{
            t.shoot(owner);
        }catch (WrongPlayerException wPE){ }
    }

}