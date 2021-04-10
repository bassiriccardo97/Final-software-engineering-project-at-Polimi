package it.polimi.ingsw.model;

import java.util.*;

import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.card.weapon.WeaponDeck;
import it.polimi.ingsw.model.card.weapon.WeaponFactory;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.player.Terminator;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.game.AlphaGame.setPlayers;
import static org.junit.jupiter.api.Assertions.assertTrue;


class WeaponTest {

    /**
     * Test the effect of lock rifle
     */
    @Test
    void doEffectlockrifle() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl, false, 8);
        setPlayers(pl);
        WeaponFactory wf = new WeaponFactory("lock rifle");
        Weapon w = new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.getWeapons().add(w);
        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        RealPlayer victim1 = new RealPlayer('y', "ciccia1");
        victim.setPlayerPosition(Board.getSpawnpoint('b'));
        victim1.setPlayerPosition(Board.getSpawnpoint('b'));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        players.add(victim1);
        try{
            p.getPh().getWeaponDeck().getWeapon(w.getName()).doEffect("base", "opt1", null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }
        assertTrue(victim.getPb().countDamages() == 2 && victim.getPb().getMarkedDamages('b') == 1 && victim1.getPb().countDamages() == 0 && victim1.getPb().getMarkedDamages('b') == 1 );
    }

    /**
     * Test the effect of electroscythe
     */
    @Test
    void doEffectelectroscythe() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        setPlayers(pl);
        WeaponFactory wf = new WeaponFactory("electroscythe");
        Weapon w1 = new Weapon("electroscythe", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.getWeapons().add(w1);
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w1.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(0));
        Terminator t = new Terminator('y', Board.getSquare(0));
        t.setOwnerColor(victim.getColor());
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        players.add(t);
        try{
            p.getPh().getWeaponDeck().getWeapon(w1.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && t.getPb().countDamages() == 2);

        try{
            p.getPh().getWeaponDeck().getWeapon(w1.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3 && t.getPb().countDamages() == 3);
    }

    /**
     * Test the effect of tractor beam
     */
    @Test
    void doEffecttractorbeam() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        setPlayers(pl);
        WeaponFactory wf = new WeaponFactory("tractor beam");
        Weapon w2 = new Weapon("tractor beam", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.getWeapons().add(w2);
        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w2.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.add(Board.getSpawnpoint('b'));
        try {
            p.getPh().getWeaponDeck().getWeapon(w2.getName()).doEffect("base", null, null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }
        assertTrue(players.get(0).getPb().countDamages() == 1 && players.get(0).getPlayerPosition() == Board.getSpawnpoint('b'));

        players.get(0).setPlayerPosition(Board.getSquare(1));
        s.clear();

        try{
            p.getPh().getWeaponDeck().getWeapon(w2.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(players.get(0).getPb().countDamages() == 4 && players.get(0).getPlayerPosition() == p.getPlayerPosition());
    }

    /**
     * Test the effect of furnace
     */
    @Test
    void doEffectfurnace() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("furnace");
        Weapon w3 = new Weapon("furnace", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.addWeapon(w3);
        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w3.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('r'));
        Terminator t = new Terminator('g', Board.getSquare(5));
        t.setOwnerColor(victim.getColor());
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.add(victim.getPlayerPosition());
        try{
            p.getPh().getWeaponDeck().getWeapon(w3.getName()).doEffect("base", null, null, p, null, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 1 && t.getPb().countDamages() == 1);

        p.setPlayerPosition(Board.getSquare(0));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('r'));
        t = new Terminator('g', Board.getSpawnpoint('r'));
        t.setOwnerColor(victim.getColor());
        s.clear();
        s.add(Board.getSpawnpoint('r'));
        try{
            p.getPh().getWeaponDeck().getWeapon(w3.getName()).doEffect("alt", null, null, p, null, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 1 && victim.getPb().getMarkedDamages('b')==1 && t.getPb().countDamages() == 1 && t.getPb().getMarkedDamages('b')==1);

    }

    /**
     * Test the effect of heatseeker
     */
    @Test
    void doEffectheatseeker() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("heatseeker");
        Weapon w4 = new Weapon("heatseeker", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.addWeapon(w4);
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w4);
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w4.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(10));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        try{
            p.getPh().getWeaponDeck().getWeapon(w4.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3);
    }

    /**
     * Test the effect of hellion
     */
    @Test
    void doEffecthellion() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("hellion");
        Weapon w5 = new Weapon("hellion", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.addWeapon(w5);
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w5);
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w5.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('b'));
        Terminator t = new Terminator('e', Board.getSpawnpoint('b'));
        t.setOwnerColor(victim.getColor());
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        players.add(t);
        try{
            p.getPh().getWeaponDeck().getWeapon(w5.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().getMarkedDamages('b') == 1 && victim.getPb().countDamages() == 1 && t.getPb().getMarkedDamages('b') == 1);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('b'));
        t = new Terminator('e', Board.getSpawnpoint('b'));
        t.setOwnerColor(victim.getColor());
        players.clear();
        players.add(victim);
        players.add(t);

        try{
            p.getPh().getWeaponDeck().getWeapon(w5.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().getMarkedDamages('b') == 2 && victim.getPb().countDamages() == 1 && t.getPb().getMarkedDamages('b') == 2);
    }

    /**
     * Test the effect of whisper
     */
    @Test
    void doEffectwhisper() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("whisper");
        Weapon w6 = new Weapon("whisper", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.addWeapon(w6);
        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setPlayerPosition(Board.getSpawnpoint(2));
        p.getPh().drawWeapon(wd, w6.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);

        try{
            p.getPh().getWeaponDeck().getWeapon(w6.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().getMarkedDamages('b') == 1 && victim.getPb().countDamages() == 3);

        players.get(0).setPlayerPosition(Board.getSquare(7));
        try {
            p.getPh().getWeaponDeck().getWeapon(w6.getName()).doEffect("base", null, null, p, players, null); //"A choice of yours is wrong"
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }
    }

    /**
     * Test the effect of flamethrower
     */
    @Test
    void doEffectflamethrower() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("flamethrower");
        Weapon w7 = new Weapon("flamethrower", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.addWeapon(w7);
        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w7.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(1));
        Terminator t = new Terminator('e', Board.getSpawnpoint('b'));
        t.setOwnerColor(victim.getColor());
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        players.add(t);
        try{
            p.getPh().getWeaponDeck().getWeapon(w7.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 1 && t.getPb().countDamages() == 1);

        players.get(1).setPlayerPosition(Board.getSquare(5));
        try{
            p.getPh().getWeaponDeck().getWeapon(w7.getName()).doEffect("base", null, null, p, players, null); //"A choice of yours is wrong"
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 1 && t.getPb().countDamages() == 1);

        players.remove(1);

        try {
            p.getPh().getWeaponDeck().getWeapon(w7.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(1));
        t = new Terminator('e', Board.getSpawnpoint('b'));
        t.setOwnerColor(victim.getColor());
        RealPlayer p2 = new RealPlayer('v', "ciccia");
        p2.setPlayerPosition(Board.getSquare(1));
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.clear();
        s.add(victim.getPlayerPosition());
        s.add(t.getPlayerPosition());

        try {
            p.getPh().getWeaponDeck().getWeapon(w7.getName()).doEffect("alt", null, null, p, null, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && p2.getPb().countDamages() == 2 && t.getPb().countDamages() == 1);
    }

    /**
     * Test the effect of zx-2
     */
    @Test
    void doEffectzx2() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("zx-2");
        Weapon w8 = new Weapon("zx-2", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        wd.addWeapon(w8);
        RealPlayer p = new RealPlayer('b', "ciccia");
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w8.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('b'));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        try{
            p.getPh().getWeaponDeck().getWeapon(w8.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 1 && victim.getPb().getMarkedDamages('b') == 2);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(1));
        Terminator t = new Terminator('e', Board.getSpawnpoint('b'));
        t.setOwnerColor(victim.getColor());
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSpawnpoint('r'));
        players.clear();
        players.add(victim);
        players.add(t);
        players.add(victim2);
        try{
            p.getPh().getWeaponDeck().getWeapon(w8.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().getMarkedDamages('b') == 1 && victim2.getPb().getMarkedDamages('b') == 1 && t.getPb().getMarkedDamages('b') == 1);

        players.remove(t);
        try{
            p.getPh().getWeaponDeck().getWeapon(w8.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().getMarkedDamages('b') == 2 && victim2.getPb().getMarkedDamages('b') == 2);
    }

    /**
     * Test the effect of shotgun
     */
    @Test
    void doEffectshotgun() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("shotgun");
        Weapon w9 = new Weapon("shotgun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w9);
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w9.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(0));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        try{
            p.getPh().getWeaponDeck().getWeapon(w9.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3 && victim.getPlayerPosition() == Board.getSquare(0));

        s.add(Board.getSquare(1));
        try{
            p.getPh().getWeaponDeck().getWeapon(w9.getName()).doEffect("base", null, null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 6 && victim.getPlayerPosition() == Board.getSquare(1));

        s.clear();
        s.add(Board.getSpawnpoint('b'));
        try {
            p.getPh().getWeaponDeck().getWeapon(w9.getName()).doEffect("base", null, null, p, players, s); //A choice of yours is yours
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(1));
        players.clear();
        players.add(victim);
        s.clear();
        try {
            p.getPh().getWeaponDeck().getWeapon(w9.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('b'));
        players.clear();
        players.add(victim);
        s.clear();
        try{
            p.getPh().getWeaponDeck().getWeapon(w9.getName()).doEffect("alt", null, null, p, players, null); //A choice of yours is wrong
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }
    }

    /**
     * Test the effect of power glove
     */
    @Test
    void doEffectpowerglove() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("power glove");
        Weapon w10 = new Weapon("power glove", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w10);
        p.setPlayerPosition(Board.getSquare(0));
        p.getPh().drawWeapon(wd, w10.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(1));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        try {
            p.getPh().getWeaponDeck().getWeapon(w10.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(p.getPlayerPosition() == Board.getSquare(1) && victim.getPb().countDamages() == 1 && victim.getPb().getMarkedDamages('b') == 2);

        p.setPlayerPosition(Board.getSquare(0));
        s.add(Board.getSquare(1));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(1));
        players.clear();
        players.add(victim);
        s.add(Board.getSpawnpoint('b'));
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSpawnpoint('b'));
        players.add(victim2);
        try{
            p.getPh().getWeaponDeck().getWeapon(w10.getName()).doEffect("alt", null, null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(p.getPlayerPosition() == Board.getSpawnpoint('b') && victim.getPb().countDamages() == 2 && victim2.getPb().countDamages() == 2);

        p.setPlayerPosition(Board.getSquare(0));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('b'));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(1));
        s.add(Board.getSpawnpoint('b'));
        try{
            p.getPh().getWeaponDeck().getWeapon(w10.getName()).doEffect("alt", null, null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { System.out.println("ERROR");}

        assertTrue(p.getPlayerPosition() == Board.getSpawnpoint('b') && victim.getPb().countDamages() == 2);
    }

    /**
     * Test the effect of railgun
     */
    @Test
    void doEffectrailgun() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("railgun");
        Weapon w11 = new Weapon("railgun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w11);
        p.setPlayerPosition(Board.getSpawnpoint('r'));
        p.getPh().drawWeapon(wd, w11.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        try {
            p.getPh().getWeaponDeck().getWeapon(w11.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        players.clear();
        players.add(victim);
        try{
            p.getPh().getWeaponDeck().getWeapon(w11.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(7));
        players.clear();
        players.add(victim);
        players.add(victim2);
        s.clear();
        try {
            p.getPh().getWeaponDeck().getWeapon(w11.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim2.getPb().countDamages() == 2);
    }

    /**
     * Test the effect of shockwave
     */
    @Test
    void doEffectshockwave() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        RealPlayer b = new RealPlayer('b', "blue");
        RealPlayer e = new RealPlayer('e', "emerald");
        RealPlayer gr = new RealPlayer('g', "grey");
        RealPlayer v = new RealPlayer('v', "violet");
        RealPlayer y = new RealPlayer('y', "yellow");
        AlphaGame.getPlayers().add(b);
        AlphaGame.getPlayers().add(e);
        AlphaGame.getPlayers().add(gr);
        AlphaGame.getPlayers().add(v);
        AlphaGame.getPlayers().add(y);
        WeaponFactory wf = new WeaponFactory("shockwave");
        Weapon w12 = new Weapon("shockwave", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w12);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w12.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(7));
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(5));
        RealPlayer victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSquare(10));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        players.add(victim2);
        players.add(victim3);
        try{
            p.getPh().getWeaponDeck().getWeapon(w12.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException ex) { }

        assertTrue(victim.getPb().countDamages() == 1 && victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 1);

        victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(5));
        victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSquare(10));
        players.clear();
        players.add(victim2);
        players.add(victim3);
        try{
            p.getPh().getWeaponDeck().getWeapon(w12.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException ex) { }

        assertTrue(victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 1);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(7));
        victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(5));
        victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSquare(10));
        RealPlayer victim4 = new RealPlayer('b', "ciccia");
        RealPlayer victim5 = new RealPlayer('p', "ciccia");
        victim4.setPlayerPosition(Board.getSquare(1));
        victim5.setPlayerPosition(Board.getSquare(0));
        players.clear();
        try {
            p.getPh().getWeaponDeck().getWeapon(w12.getName()).doEffect("alt", null, null, p, null, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException ex) { }

        assertTrue(victim.getPb().countDamages() == 1 && victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 1 && victim4.getPb().countDamages() == 0 && victim5.getPb().countDamages() == 0);
    }

    /**
     * Test the effect of sledgehammer
     */
    @Test
    void doEffectsledgehammer() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("sledgehammer");
        Weapon w13 = new Weapon("sledgehammer", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w13);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w13.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        try {
            p.getPh().getWeaponDeck().getWeapon(w13.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        players.clear();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.add(Board.getSquare(10));
        try {
            p.getPh().getWeaponDeck().getWeapon(w13.getName()).doEffect("alt", null, null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3 && victim.getPlayerPosition() == Board.getSquare(10));

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        players.clear();
        players.add(victim);
        s.clear();
        try {
            p.getPh().getWeaponDeck().getWeapon(w13.getName()).doEffect("alt", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3 && victim.getPlayerPosition() == Board.getSquare(6));
    }

    /**
     * Test the effect of machine gun
     */
    @Test
    void doEffectmachinegun() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w14 = new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w14);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w14.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(7));
        RealPlayer victim2 = new RealPlayer('v', "ciccia1");
        victim2.setPlayerPosition(Board.getSquare(10));
        RealPlayer victim3 = new RealPlayer('g', "ciccia2");
        victim3.setPlayerPosition(Board.getSpawnpoint('y'));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        players.add(victim2);
        players.add(victim);
        try {
            p.getPh().getWeaponDeck().getWeapon(w14.getName()).doEffect("base", "opt1", null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) {
            System.out.println("ERROR");
        }

        assertTrue(victim.getPb().countDamages() == 2 && victim2.getPb().countDamages() == 1);

        players.clear();
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(7));
        victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(10));
        victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSpawnpoint('y'));
        players.add(victim);
        players.add(victim2);
        players.add(victim);
        players.add(victim3);
        try {
            p.getPh().getWeaponDeck().getWeapon(w14.getName()).doEffect("base", "opt1", "opt2", p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 1);
    }

    /**
     * Test the effect of thor
     */
    @Test
    void doEffectthor() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("t.h.o.r.");
        Weapon w15 = new Weapon("t.h.o.r.", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w15);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w15.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(7));
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(10));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        players.add(victim2);
        try {
            p.getPh().getWeaponDeck().getWeapon(w15.getName()).doEffect("base", "opt1", null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim2.getPb().countDamages() == 1);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(7));
        victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(10));
        RealPlayer victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        players.add(victim2);
        players.add(victim3);
        try {
            p.getPh().getWeaponDeck().getWeapon(w15.getName()).doEffect("base", "opt1", "opt2", p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 2);
    }

    /**
     * Test the effect of vortex cannon
     */
    @Test
    void doEffectvortexcannon() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("vortex cannon");
        Weapon w16 = new Weapon("vortex cannon", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w16);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w16.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.add(Board.getSquare(10));
        try{
            p.getPh().getWeaponDeck().getWeapon(w16.getName()).doEffect("base", null, null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim.getPlayerPosition() == Board.getSquare(10));

        players.clear();
        s.clear();
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(10));
        RealPlayer victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSquare(9));
        players.add(victim);
        players.add(victim2);
        players.add(victim3);
        s.add(Board.getSquare(10));
        try {
            p.getPh().getWeaponDeck().getWeapon(w16.getName()).doEffect("base", "opt1", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim.getPlayerPosition() == Board.getSquare(10) && victim2.getPlayerPosition() == Board.getSquare(10) && victim2.getPb().countDamages() == 1 && victim3.getPlayerPosition()==Board.getSquare(10) && victim3.getPb().countDamages()==1);
    }

    /**
     * Test the effect of plasma gun
     */
    @Test
    void doEffectPlasmaGun() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("plasma gun");
        Weapon w17 = new Weapon("plasma gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w17);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w17.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        try {
            p.getPh().getWeaponDeck().getWeapon(w17.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2);

        p.setPlayerPosition(Board.getSpawnpoint(2));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(6));
        try {
            p.getPh().getWeaponDeck().getWeapon(w17.getName()).doEffect("opt1", "base", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && p.getPlayerPosition() == Board.getSquare(6));

        p.setPlayerPosition(Board.getSquare(6));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(1));
        try {
            p.getPh().getWeaponDeck().getWeapon(w17.getName()).doEffect("base", "opt1", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && p.getPlayerPosition() == Board.getSquare(1));

        p.setPlayerPosition(Board.getSpawnpoint(2));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(6));
        try {
            p.getPh().getWeaponDeck().getWeapon(w17.getName()).doEffect("opt1", "base", "opt2", p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3 && p.getPlayerPosition() == Board.getSquare(6));

        p.setPlayerPosition(Board.getSquare(6));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSpawnpoint('b'));
        try {
            p.getPh().getWeaponDeck().getWeapon(w17.getName()).doEffect("base", "opt2", "opt1", p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3 && p.getPlayerPosition() == Board.getSpawnpoint('b'));
    }

    /**
     * Test the effect of grenade launcher
     */
    @Test
    void doEffectgrenadelauncher() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("grenade launcher");
        Weapon w18 = new Weapon("grenade launcher", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w18);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w18.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.add(Board.getSquare(10));
        try {
            p.getPh().getWeaponDeck().getWeapon(w18.getName()).doEffect("base", null, null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 1 && victim.getPlayerPosition() == Board.getSquare(10));

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        s.clear();
        try {
            p.getPh().getWeaponDeck().getWeapon(w18.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 1 && victim.getPlayerPosition() == Board.getSpawnpoint('y'));

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(10));
        RealPlayer victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSquare(10));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(10));
        try {
            p.getPh().getWeaponDeck().getWeapon(w18.getName()).doEffect("base", "opt1", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 1 && victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 1);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(10));
        victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSquare(10));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(10));
        s.add(Board.getSquare(10));
        try {
            p.getPh().getWeaponDeck().getWeapon(w18.getName()).doEffect("base", "opt1", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 1);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(10));
        victim2 = new RealPlayer('v', "ciccia1");
        victim2.setPlayerPosition(Board.getSquare(10));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(10));
        s.add(Board.getSpawnpoint('y'));
        try {
            p.getPh().getWeaponDeck().getWeapon(w18.getName()).doEffect("opt1", "base", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { System.out.println("ERROR");}

        assertTrue(victim.getPb().countDamages() == 2 && victim.getPlayerPosition() == Board.getSpawnpoint('y') && victim2.getPb().countDamages() == 1);
    }

    /**
     * Test the effect of rocket launcher
     */
    @Test
    void doEffectrocketlauncher() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("rocket launcher");
        Weapon w19 = new Weapon("rocket launcher", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w19);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w19.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<GenericSquare> s = new ArrayList<>();
        players.add(victim);
        s.add(Board.getSquare(10));
        try {
            p.getPh().getWeaponDeck().getWeapon(w19.getName()).doEffect("base", null, null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim.getPlayerPosition() == Board.getSquare(10));

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(7));
        s.add(Board.getSquare(10));
        try {
            p.getPh().getWeaponDeck().getWeapon(w19.getName()).doEffect("opt1", "base", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 2 && victim.getPlayerPosition() == Board.getSquare(10) && p.getPlayerPosition() == Board.getSquare(7));

        p.setPlayerPosition(Board.getSquare(6));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSpawnpoint('y'));
        RealPlayer victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(7));
        s.add(Board.getSquare(10));
        try {
            p.getPh().getWeaponDeck().getWeapon(w19.getName()).doEffect("opt1", "base", "opt2", p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3 && victim.getPlayerPosition() == Board.getSquare(10) && p.getPlayerPosition() == Board.getSquare(7) && victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 1);

        p.setPlayerPosition(Board.getSquare(6));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSpawnpoint('y'));
        victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSpawnpoint('y'));
        victim3 = new RealPlayer('g', "ciccia");
        victim3.setPlayerPosition(Board.getSpawnpoint('y'));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(10));
        s.add(Board.getSquare(7));
        try {
            p.getPh().getWeaponDeck().getWeapon(w19.getName()).doEffect("base", "opt2", "opt1", p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages() == 3 && victim.getPlayerPosition() == Board.getSquare(10) && p.getPlayerPosition() == Board.getSquare(7) && victim2.getPb().countDamages() == 1 && victim3.getPb().countDamages() == 1);
    }

    /**
     * Test the effect of cyberblade
     */
    @Test
    void doEffectcyberblade(){
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("cyberblade");
        Weapon w20 = new Weapon("cyberblade", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        WeaponDeck wd = new WeaponDeck();
        RealPlayer p = new RealPlayer('b', "ciccia");
        wd.addWeapon(w20);
        p.setPlayerPosition(Board.getSquare(6));
        p.getPh().drawWeapon(wd, w20.getName());
        RealPlayer victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        ArrayList<Player> players = new ArrayList<>();
        players.add(victim);
        try {
            p.getPh().getWeaponDeck().getWeapon(w20.getName()).doEffect("base", null, null, p, players, null);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages()==2);

        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        players.clear();
        players.add(victim);
        ArrayList<GenericSquare> s = new ArrayList<>();
        s.add(Board.getSquare(7));
        try {
            p.getPh().getWeaponDeck().getWeapon(w20.getName()).doEffect("base", "opt1", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages()==2 && p.getPlayerPosition()==Board.getSquare(7));

        p.setPlayerPosition(Board.getSquare(6));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(7));
        players.clear();
        players.add(victim);
        s.clear();
        s.add(Board.getSquare(7));
        try {
            p.getPh().getWeaponDeck().getWeapon(w20.getName()).doEffect("opt1", "base", null, p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages()==2 && p.getPlayerPosition()==Board.getSquare(7));

        p.setPlayerPosition(Board.getSquare(6));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        RealPlayer victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(7));
        players.clear();
        players.add(victim);
        players.add(victim2);
        s.clear();
        s.add(Board.getSquare(7));
        try {
            p.getPh().getWeaponDeck().getWeapon(w20.getName()).doEffect("base", "opt1", "opt2", p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages()==2 && victim2.getPb().countDamages()==2 && p.getPlayerPosition()==Board.getSquare(7));

        p.setPlayerPosition(Board.getSquare(6));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(6));
        victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(6));
        players.clear();
        players.add(victim);
        players.add(victim2);
        s.clear();
        s.add(Board.getSquare(7));
        try {
            p.getPh().getWeaponDeck().getWeapon(w20.getName()).doEffect("base", "opt2", "opt1", p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages()==2 && victim2.getPb().countDamages()==2 && p.getPlayerPosition()==Board.getSquare(7));

        p.setPlayerPosition(Board.getSquare(6));
        victim = new RealPlayer('y', "ciccia");
        victim.setPlayerPosition(Board.getSquare(7));
        victim2 = new RealPlayer('v', "ciccia");
        victim2.setPlayerPosition(Board.getSquare(7));
        players.clear();
        players.add(victim);
        players.add(victim2);
        s.clear();
        s.add(Board.getSquare(7));
        try {
            p.getPh().getWeaponDeck().getWeapon(w20.getName()).doEffect("opt1", "base", "opt2", p, players, s);
        }catch (WrongValueException | WrongPlayerException | WrongSquareException e) { }

        assertTrue(victim.getPb().countDamages()==2 && victim2.getPb().countDamages()==2 && p.getPlayerPosition()==Board.getSquare(7));
    }

    /**
     * Test the weapon payment (grab and an effect)
     */
    @Test
    void pay() {
        ArrayList<Player> pl = new ArrayList<>();
        AlphaGame g = new AlphaGame(1, pl,false, 8);
        WeaponFactory wf = new WeaponFactory("machine gun");
        Weapon w = new Weapon("t.h.o.r.", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions());
        RealPlayer p = new RealPlayer('b', "ciccia");
        int[] ammo = {3, 3, 3};
        p.getPb().grabAmmo(ammo);
        w.pay("base", p);

        assertTrue(p.getPb().getAmmo('b')==3 && p.getPb().getAmmo('r')==3 && p.getPb().getAmmo('y')==3);

    }

}