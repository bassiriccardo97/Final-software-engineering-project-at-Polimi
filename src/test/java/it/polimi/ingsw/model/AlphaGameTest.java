package it.polimi.ingsw.model;

import java.util.*;

import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlphaGameTest {

    /**
     * Adds different players to the list of players in AlphaGame
     */
    @Test
    void addPlayer() {
        ArrayList<Player> p = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, p,false, 8);

        RealPlayer bob  = new RealPlayer('b', "ciccia");
        g.addPlayer(bob);
        RealPlayer jack = new RealPlayer('r', "ciccia");
        g.addPlayer(jack);

        assertTrue(AlphaGame.getPlayers().size() == 2);

    }

    /**
     * Resets the list of the players in AlphaGame
     */
    @Test
    void resetPlayers() {
        ArrayList<Player> p = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, p,false, 8);

        RealPlayer bob  = new RealPlayer('b', "ciccia");
        g.addPlayer(bob);
        RealPlayer jack = new RealPlayer('r', "ciccia");
        g.addPlayer(jack);
        g.resetPlayers();

        assertTrue(AlphaGame.getPlayers().size() == 0);

    }

    /**
     * Tests the win of a player
     */
    @Test
    void getWinner() {
        ArrayList<Player> p = new ArrayList<>();
        RealPlayer p1 = new RealPlayer('b', "ciccia");
        RealPlayer p2 = new RealPlayer('e', "ciccia");
        RealPlayer p3 = new RealPlayer('y', "ciccia");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        AlphaGame g = new AlphaGame( 1, p,false, 5);
        AlphaGame.resetScore();
        try {
            PlayerBoard.giveNDamages(p1, p2, 2);      // b 2 damages to e
            PlayerBoard.giveNDamages(p2, p3, 3);      // e 3 damages to y
            PlayerBoard.giveNDamages(p1, p3, 4);      // b 4 damages to y
            PlayerBoard.giveNDamages(p3, p1, 5);      // y 5 damages to b
            PlayerBoard.giveNDamages(p3, p2, 6);      // y 6 damages to e
            PlayerBoard.giveNDamages(p2, p1, 7);      // e 7 damages to b -> e overkills b -> ee -> b marks e
            p1.getPb().updateKillshotRMX();
            p1.getPb().afterDeath();
            p1.getPb().setAlive();
            PlayerBoard.giveNDamages(p3, p2, 3);      // y 3 damages to e -> y kills e -> y
            p2.getPb().updateKillshotRMX();
            p2.getPb().afterDeath();
            p2.getPb().setAlive();
            PlayerBoard.giveNDamages(p1, p3, 9);      // b 9 damages to y -> b overkills y -> bb -> y marks b
            p3.getPb().updateKillshotRMX();
            p3.getPb().afterDeath();
            p3.getPb().setAlive();
            PlayerBoard.giveNDamages(p1, p2, 3);      // b 3 damages to e -> mark to damage
            PlayerBoard.giveNDamages(p1, p3, 4);      // b 4 damages to y
            PlayerBoard.giveNDamages(p2, p1, 5);      // e 5 damages to b
            PlayerBoard.giveNDamages(p2, p3, 6);      // e 6 damages to y
            PlayerBoard.giveNDamages(p3, p1, 7);      // y 7 damages to b -> mark to damage -> y overkills b -> yy -> b marks y
            p1.getPb().updateKillshotRMX();
            p1.getPb().afterDeath();
            p1.getPb().setAlive();
            PlayerBoard.giveNDamages(p3, p2, 6);
            //p2.getPb().updateKillshotRMX();
            //p2.getPb().afterDeath();
            //p2.getPb().setAlive();
        } catch (WrongPlayerException e) {
            e.printStackTrace();
        }

        Player winner = g.getWinner();
        assertTrue(winner.getColor() == 'y' );      //29, 32, 35 or 36 ??
    }

    /**
     * Tests the win of a player with an overkill
     */
    @Test
    void getWinner1(){

        ArrayList<Player> p = new ArrayList<>();
        RealPlayer p1 = new RealPlayer('b', "ciccia");
        RealPlayer p2 = new RealPlayer('e', "ciccia");
        RealPlayer p3 = new RealPlayer('y', "ciccia");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        AlphaGame g = new AlphaGame( 1, p,false, 5);

        AlphaGame.resetScore();
        try {
            PlayerBoard.giveNDamages(p1, p2, 2);      // b 2 damages to e
            PlayerBoard.giveNDamages(p2, p3, 3);      // e 3 damages to y
            PlayerBoard.giveNDamages(p1, p3, 4);      // b 4 damages to y
            PlayerBoard.giveNDamages(p3, p1, 5);      // y 5 damages to b
            PlayerBoard.giveNDamages(p3, p2, 6);      // y 6 damages to e
            PlayerBoard.giveNDamages(p2, p1, 7);      // e 7 damages to b -> e overkills b -> ee -> b marks e
            p1.getPb().updateKillshotRMX();
            p1.getPb().afterDeath();
            p1.getPb().setAlive();
        }catch (Exception e){
            e.printStackTrace();
        }

        Player winner = g.getWinner();
        assertTrue(winner.getColor() == 'e');
    }

    /**
     * Tests the win of a player
     */
    @Test
    void getWinner2(){
        ArrayList<Player> p = new ArrayList<>();
        RealPlayer p1 = new RealPlayer('b', "ciccia");
        RealPlayer p2 = new RealPlayer('e', "ciccia");
        RealPlayer p3 = new RealPlayer('y', "ciccia");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        AlphaGame g = new AlphaGame( 1, p,false, 5);


        AlphaGame.resetScore();
        try {
            PlayerBoard.giveNDamages(p1, p2, 2);      // b 2 damages to e --> b = 1 + 6 + 8 = 15
            PlayerBoard.giveNDamages(p2, p3, 3);      // e 3 damages to y --> e = 1 + 6 = 7
            PlayerBoard.giveNDamages(p1, p3, 4);      // b 4 damages to y
            PlayerBoard.giveNDamages(p3, p1, 5);      // y 5 damages to b --> y = 1 + 8 + 8 = 17
            PlayerBoard.giveNDamages(p3, p2, 6);      // y 6 damages to e
        }catch (Exception e){
            e.printStackTrace();
        }

        Player winner = g.getWinner();
        assertTrue(winner.getColor() == 'y');

    }

    /**
     * Tests the win of a player with an overkill
     */
    @Test
    void getWinner3(){
        ArrayList<Player> p = new ArrayList<>();
        RealPlayer p1 = new RealPlayer('b', "ciccia");
        RealPlayer p2 = new RealPlayer('e', "ciccia");
        RealPlayer p3 = new RealPlayer('y', "ciccia");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        AlphaGame g = new AlphaGame( 1, p,false, 5);

        AlphaGame.resetScore();

        try {
            PlayerBoard.giveNDamages(p1, p2, 1);
            PlayerBoard.giveNMarks(p1, p2, 2);
        }catch (Exception e){
            e.printStackTrace();
        }

        Player winner = g.getWinner();

        assertTrue(winner.getColor() == 'b');
    }

    @Test
    void getWinner4(){
        ArrayList<Player> p = new ArrayList<>();
        RealPlayer p1 = new RealPlayer('b', "ciccia");
        RealPlayer p2 = new RealPlayer('e', "ciccia");
        RealPlayer p3 = new RealPlayer('v', "ciccia");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        AlphaGame g = new AlphaGame( 1, p,false, 5);

        AlphaGame.resetScore();

        try {
            PlayerBoard.giveNDamages(p2, p1, 2);
            PlayerBoard.giveNMarks(p2, p1, 1);
            PlayerBoard.giveNDamages(p3, p1, 3);
            PlayerBoard.giveNDamages(p1, p2, 1);
            PlayerBoard.giveNMarks(p1, p2, 2);
        }catch (Exception e){
            e.printStackTrace();
        }

        Player winner = g.getWinner();

        for (int i : AlphaGame.getPlayersPoint())
            System.out.println(i);
    }

    /**
     * Tests the update of the scores of the players in game
     */
    @Test
    void getWinner5(){
        ArrayList<Player> p = new ArrayList<>();
        RealPlayer p1 = new RealPlayer('b', "ciccia");
        RealPlayer p2 = new RealPlayer('e', "ciccia");
        RealPlayer p3 = new RealPlayer('g', "ciccia");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        AlphaGame g = new AlphaGame( 1, p,false, 5);

        AlphaGame.resetScore();

        try {
            PlayerBoard.giveNDamages(p3, p2, 1);
            PlayerBoard.giveNMarks(p3, p2, 2);
            PlayerBoard.giveNDamages(p1, p3, 3);
            PlayerBoard.giveNMarks(p1, p3, 1);
            PlayerBoard.giveNDamages(p2, p1, 2);
        }catch (Exception e){
            e.printStackTrace();
        }

        Player winner = g.getWinner();

        for (int i : AlphaGame.getPlayersPoint())
            System.out.println(i);
    }
}