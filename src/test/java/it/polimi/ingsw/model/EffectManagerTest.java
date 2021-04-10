package it.polimi.ingsw.model;

import java.util.ArrayList;

import it.polimi.ingsw.model.card.EffectManager;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectManagerTest {

    /**
     * Tests the check of the visibility of many players by a single one
     */
    @Test
    void checkPlayersVisibility() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        RealPlayer p1 = new RealPlayer('b', "ciccia");
        RealPlayer p2 = new RealPlayer('e', "ciccia");
        p1.setPlayerPosition(Board.getSquare(10));
        p2.setPlayerPosition(Board.getSquare(0));
        ArrayList<Player> players = new ArrayList<>();
        players.add(p2);
        try{
            EffectManager.checkPlayersVisibilityShoot(p1.getPlayerPosition(), players);
        } catch (WrongPlayerException wPE){
            //empty
        }
    }

    /**
     * Tests the check of the visibility of many players by a single one giving a specific distance
     */
    @Test
    void checkDistanceVisible() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        RealPlayer p1 = new RealPlayer('b', "ciccia");
        p1.setPlayerPosition(Board.getSpawnpoint('b'));
        try{
            EffectManager.checkDistanceVisibleShoot(p1.getPlayerPosition(), Board.getSquare(10), 1, 2);
        } catch (WrongSquareException wSE){
            //empty
        }
    }

    /**
     * Adds many damages to some players
     */
    @Test
    void giveNDamages() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        RealPlayer p1 = new RealPlayer('b', "ciccia");
        RealPlayer p2 = new RealPlayer('y', "ciccia");
        p1.setPlayerPosition(Board.getSquare(0));
        p2.setPlayerPosition(Board.getSquare(1));
        try {
            PlayerBoard.giveNDamages(p1, p2, 3);
        } catch (WrongPlayerException e) {
            e.printStackTrace();
        }

        assertTrue(p2.getPb().countDamages()==3 && p2.getJustDamaged());
    }
}