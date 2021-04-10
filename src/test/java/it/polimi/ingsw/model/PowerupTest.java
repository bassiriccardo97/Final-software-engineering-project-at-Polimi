package it.polimi.ingsw.model;

import java.util.*;

import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.card.powerup.PowerupFactory;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class PowerupTest {

    /**
     * Test the effect of teleporter
     */
    @Test
    void doEffectteleporter() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        PowerupFactory pf = new PowerupFactory("teleporter");
        Powerup p1 = new Powerup("teleporter", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        RealPlayer p = new RealPlayer('y', "ciccia");
        p.setPlayerPosition(Board.getSquare(0));
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.add(Board.getSquare(10));
        try {
            p1.doEffect(p, null, s);
        }catch (WrongValueException wVE) { }

        assertTrue(p.getPlayerPosition() == Board.getSquare(10));
    }

    /**
     * Test the effect of newton
     */
    @Test
    void doEffecnewton() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        PowerupFactory pf = new PowerupFactory("newton");
        Powerup p2 = new Powerup("newton", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        RealPlayer p = new RealPlayer('y', "ciccia");
        p.setPlayerPosition(Board.getSquare(1));
        RealPlayer victim = new RealPlayer('b', "ciccia");
        victim.setPlayerPosition(Board.getSquare(9));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        GenericSquare s1 = Board.getSpawnpoint('y');
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.clear();
        s.add(s1);
        try{
            p2.doEffect(p, players, s);
        }catch (WrongValueException wVE) { }

        assertTrue(victim.getPlayerPosition() == Board.getSpawnpoint('y'));

        players.get(0).setPlayerPosition(Board.getSquare(10));
        try{
            p2.doEffect(p, players, s);
        }catch (WrongValueException wVE) { }

        assertTrue(victim.getPlayerPosition() == Board.getSpawnpoint('y'));

        players.get(0).setPlayerPosition(Board.getSquare(0));
        s.clear();
        s1 = Board.getSpawnpoint('b');
        s.add(s1);
        try{
            p2.doEffect(p, players, s);
        }catch (WrongValueException wVE) { }

        assertTrue(victim.getPlayerPosition() == Board.getSpawnpoint('b'));

        players.get(0).setPlayerPosition(Board.getSpawnpoint('b'));
        s.clear();
        s1 = Board.getSquare(0);
        s.add(s1);
        try {
            p2.doEffect(p, players, s);
        }catch (WrongValueException wVE) { }

        assertTrue(victim.getPlayerPosition() == Board.getSquare(0));
    }

    /**
     * Test the effect of tagback grenade
     */
    @Test
    void doEffecttagbackgrenade() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        PowerupFactory pf = new PowerupFactory("tagback grenade");
        Powerup p3 = new Powerup("tagback grenade", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        RealPlayer p = new RealPlayer('y', "ciccia");
        p.setPlayerPosition(Board.getSquare(1));
        RealPlayer victim = new RealPlayer('b', "ciccia");
        victim.setPlayerPosition(Board.getSquare(0));
        p.getPh().getPowerupDeck().getPowerups().add(p3);
        p.getPb().addDamage('b');
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        try {
            p.getPh().getPowerupDeck().getPowerup("tagback grenade", 'b').doEffect(p, players, null);
        }catch (WrongValueException wVE) { }

        assertTrue(victim.getPb().getMarkedDamages('y') == 1);
    }

    /**
     * Test the effect of targeting scope
     */
    @Test
    void doEffecttargetingscope(){
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);

        PowerupFactory pf = new PowerupFactory("targeting scope");
        Powerup p4 = new Powerup("targeting scope", 'b', pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier());
        RealPlayer p = new RealPlayer('y', "ciccia");
        p.setPlayerPosition(Board.getSquare(0));
        RealPlayer victim = new RealPlayer('b', "ciccia");
        victim.setPlayerPosition(Board.getSquare(1));
        victim.getPb().addDamage(p.getColor());
        victim.setJustDamaged(true);
        ArrayList<Player> players = new ArrayList<>();
        players.clear();
        players.add(victim);
        try {
            p4.doEffect(p, players, null);
        }catch (WrongValueException wVE) { }

        assertTrue(victim.getPb().countDamages() == 2);
    }

}