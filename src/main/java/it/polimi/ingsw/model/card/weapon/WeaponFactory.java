package it.polimi.ingsw.model.card.weapon;

import it.polimi.ingsw.model.card.Effect;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.game.squares.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.model.player.RealPlayer;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.card.EffectManager.*;

public class WeaponFactory {

    private WeaponApplier applier;
    private char[] cost = {'\u0000', '\u0000', '\u0000'};      //max 3 letters of ammo color
    private char[] costOpt = {'\u0000', '\u0000'};   //one letter of ammo color for 2 opt effects
    private char[] costAlt = {'\u0000', '\u0000'};   //max 2 letters of ammo color
    private boolean[] booleans = new boolean[5];
    private Effect[] effects = new Effect[4];       //base, opt1, opt2, alt
    private int[] requestedNum = new int[9];
    private String[] descriptions = new String[4];
    private ArrayList<char[]> costs = new ArrayList<>();

    /**
     * Factory constructor for Weapon
     *
     * @param name      a String, the name of the Weapon
     */
    public WeaponFactory(String name){

        setBaseEffect(name);
        setApplier(name);
        setDescriptions(name);
        booleans[0] = false; //loaded
        requestedNum[8] = 0; //particular weapons
        booleans[4] = false; //potential not-necessary square
        switch (name){
            case "lock rifle":
                cost[0] = 'b';
                cost[1] = 'b';
                costOpt[0] = 'r';
                setOpt1Effect(name);
                booleans[1] = true; //opt1Effect
                booleans[2] = false; //opt2Effect
                booleans[3] = false; //altEffect
                requestedNum[0] = 1; //nTargetsBase
                requestedNum[3] = 0; //nTargetsAlt
                requestedNum[1] = 1; //nTargetsOpt1
                requestedNum[2] = 0; //nTargetsOpt2
                requestedNum[4] = 0; //nSquaresBase
                requestedNum[5] = 0; //nSquaresOpt1
                requestedNum[6] = 0; //nSquaresOpt2
                requestedNum[7] = 0; //nSquaresAlt
                break;
            case "electroscythe":
                cost[0] = 'b';
                costAlt[0] = 'b';
                costAlt[1] = 'r';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 0;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "machine gun":
                cost[0] = 'b';
                cost[1] = 'r';
                costOpt[0] = 'y';
                costOpt[1] = 'b';
                setOpt1Effect(name);
                setOpt2Effect(name);
                booleans[1] = true;
                booleans[2] = true;
                booleans[3] = false;
                requestedNum[0] = 2;
                requestedNum[3] = 0;
                requestedNum[1] = 1;
                requestedNum[2] = 1;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "tractor beam":
                cost[0] = 'b';
                costAlt[0] = 'r';
                costAlt[1] = 'y';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 1;
                requestedNum[3] = 1;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 1;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "t.h.o.r.":
                cost[0] = 'b';
                cost[1] = 'r';
                costOpt[0] = 'b';
                costOpt[1] = 'b';
                setOpt1Effect(name);
                setOpt2Effect(name);
                booleans[1] = true;
                booleans[2] = true;
                booleans[3] = false;
                requestedNum[0] = 1;
                requestedNum[3] = 0;
                requestedNum[1] = 1;
                requestedNum[2] = 1;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "vortex cannon":
                cost[0] = 'r';
                cost[1] = 'b';
                costOpt[0] ='r';
                setOpt1Effect(name);
                booleans[1] = true;
                booleans[2] = false;
                booleans[3] = false;
                requestedNum[0] = 1;
                requestedNum[3] = 0;
                requestedNum[1] = 2;
                requestedNum[2] = 0;
                requestedNum[4] = 1;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "plasma gun":
                cost[0] = 'b';
                cost[1] = 'y';
                costOpt[1] = 'b';
                setOpt1Effect(name);
                setOpt2Effect(name);
                booleans[1] = true;
                booleans[2] = true;
                booleans[3] = false;
                requestedNum[0] = 1;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 1;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                requestedNum[8] = 0;
                break;
            case "furnace":
                cost[0] = 'r';
                cost[1] = 'b';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 0;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 1;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 1;
                break;
            case "heatseeker":
                cost[0] = 'r';
                cost[1] = 'r';
                cost[2] = 'y';
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = false;
                requestedNum[0] = 1;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "whisper":
                cost[0] = 'b';
                cost[1] = 'b';
                cost[2] = 'y';
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = false;
                requestedNum[0] = 1;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "hellion":
                cost[0] = 'r';
                cost[1] = 'y';
                costAlt[0] = 'r';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 1;
                requestedNum[3] = 1;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "flamethrower":
                cost[0] = 'r';
                costAlt[0] = 'y';
                costAlt[1] = 'y';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 2;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 2;
                break;
            case "zx-2":
                cost[0] = 'y';
                cost[1] = 'r';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 1;
                requestedNum[3] = 3;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "grenade launcher":
                cost[0] = 'r';
                costOpt[0] = 'r';
                setOpt1Effect(name);
                booleans[1] = true;
                booleans[2] = false;
                booleans[3] = false;
                booleans[4] = true;
                requestedNum[0] = 1;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 1;
                requestedNum[5] = 1;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                requestedNum[8] = 2;
                break;
            case "shotgun":
                cost[0] = 'y';
                cost[1] = 'y';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                booleans[4] = true;
                requestedNum[0] = 1;
                requestedNum[3] = 1;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 1;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "rocket launcher":
                cost[0] = 'r';
                cost[1] = 'r';
                costOpt[0] = 'b';
                costOpt[1] = 'y';
                setOpt1Effect(name);
                setOpt2Effect(name);
                booleans[1] = true;
                booleans[2] = true;
                booleans[3] = false;
                booleans[4] = false;
                requestedNum[0] = 1;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 1;
                requestedNum[5] = 1;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                requestedNum[8] = 1;
                break;
            case "power glove":
                cost[0] = 'y';
                cost[1] = 'b';
                costAlt[0] = 'b';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 1;
                requestedNum[3] = 2;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 2;
                break;
            case "railgun":
                cost[0] = 'y';
                cost[1] = 'y';
                cost[2] = 'b';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 1;
                requestedNum[3] = 2;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "shockwave":
                cost[0] = 'y';
                costAlt[0] = 'y';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 3;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                break;
            case "cyberblade":
                cost[0] = 'y';
                cost[1] = 'r';
                costOpt[1] = 'y';
                setOpt1Effect(name);
                setOpt2Effect(name);
                booleans[1] = true;
                booleans[2] = true;
                booleans[3] = false;
                requestedNum[0] = 1;
                requestedNum[3] = 0;
                requestedNum[1] = 0;
                requestedNum[2] = 1;
                requestedNum[4] = 0;
                requestedNum[5] = 1;
                requestedNum[6] = 0;
                requestedNum[7] = 0;
                requestedNum[8] = 1;
                break;
            default:        //sledgehammer
                cost[0] = 'y';
                costAlt[0] = 'r';
                setAltEffect(name);
                booleans[1] = false;
                booleans[2] = false;
                booleans[3] = true;
                requestedNum[0] = 1;
                requestedNum[3] = 1;
                requestedNum[1] = 0;
                requestedNum[2] = 0;
                requestedNum[4] = 0;
                requestedNum[5] = 0;
                requestedNum[6] = 0;
                requestedNum[7] = 1;
        }
        costs.add(cost);
        costs.add(costOpt);
        costs.add(costAlt);
    }
    
    /**
     * Sets the base effect for each weapon
     *
     * @param name      a String, the name of the weapon
     */
    private void setBaseEffect(String name){
        Effect oneShotVisibleSomePlayer = (p1, victims, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victims);
            for(Player p: victims){
                //p.getPb().addDamage(p1.getColor());
                PlayerBoard.giveNDamages(p1, p, 1);
            }
        };

        Effect twoShotMarkVisible = (p1, victim, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victim);
            PlayerBoard.giveNDamages(p1, victim.get(0), 2);
            victim.get(0).getPb().addMarkedDamage(p1.getColor());
        };

        Effect twoShotVisible = (p1, victim, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victim);
            PlayerBoard.giveNDamages(p1, victim.get(0), 2);
        };

        Effect threeShotNotVisible = (p1, victim, s) -> {
            checkPlayersNotVisibility(p1.getPlayerPosition(), victim);
            PlayerBoard.giveNDamages(p1, victim.get(0), 3);
        };

        Effect shootAllPlayersSameSquare = (p1, victim, s) -> {
            if(p1.getPlayerPosition().getPlayers().size() == 1)
                throw new WrongSquareException(p1.getPlayerPosition());
            for (Player p : p1.getPlayerPosition().getPlayers())
                if (p != p1) {
                    //p.getPb().addDamage(p1.getColor());
                    PlayerBoard.giveNDamages(p1, p, 1);
                }
        };

        Effect shootAllPlayersAnotherVisibleRoom = (p1, victim, s) -> {
            checkVisibleRoom(p1.getPlayerPosition(), s.get(0));
            int count = 0;
            for (GenericSquare t : Board.squareOfARoom(s.get(0).getRoom())){
                for (Player p : t.getPlayers()){
                    count++;
                    PlayerBoard.giveNDamages(p1, p, 1);
                    //p.getPb().addDamage(p1.getColor());
                }
            }
            if(count==0){
                throw new WrongValueException();
            }
        };

        Effect move012ShotVisible = (p1, victim, s) -> {
            checkDistanceVisibleShoot(p1.getPlayerPosition(), s.get(0), 2, 0);
            //victim.get(0).setPlayerPosition(s.get(0), victim.get(0));
            victim.get(0).setPlayerPosition(s.get(0));
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
        };

        Effect twoMarkVisibleSameSquare = (p1, victim, s) -> {
            checkSameSquare(p1, victim.get(0));
            PlayerBoard.giveNDamages(p1, victim.get(0), 2);
        };

        Effect shootTwoMarksVisible = (p1, victim, s) -> {
            if(victim.size()>1) //it shouldn't happen, but added anyway
                throw new WrongValueException();
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victim);
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
            PlayerBoard.giveNMarks(p1, victim.get(0), 2);
        };

        Effect shootOnePlayerAlmostOneDistanceMarkAllPlayers = (p1, victim, s) -> {
            checkDistanceVisibleShoot(p1.getPlayerPosition(), victim.get(0).getPlayerPosition(), 3, 1);
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
            for(Player p : victim.get(0).getPlayerPosition().getPlayers())
                p.getPb().addMarkedDamage(p1.getColor());
        };

        Effect threeShotMarkVisibleTwoDistance = (p1, victim, s) -> {
            checkDistanceVisibleShoot(p1.getPlayerPosition(), victim.get(0).getPlayerPosition(), 3, 2);
            PlayerBoard.giveNDamages(p1, victim.get(0), 3);
            victim.get(0).getPb().addMarkedDamage(p1.getColor());
        };

        Effect threeShotSameSquareRun = (p1, victim, s) -> {
            checkSameSquare(p1, victim.get(0));
            if (s == null || s.isEmpty())
                PlayerBoard.giveNDamages(p1, victim.get(0), 3);
            else {
                checkDistanceVisibleRun(victim.get(0).getPlayerPosition(), s.get(0), 1, 0);
                PlayerBoard.giveNDamages(p1, victim.get(0), 3);
                victim.get(0).run(s.get(0));
            }
        };

        Effect threeDamageOnePlayerOneDirection = (p1, victim, s) -> {
            checkGenericFixedDirection(p1.getPlayerPosition(), victim.get(0).getPlayerPosition());
            PlayerBoard.giveNDamages(p1, victim.get(0), 3);
        };

        Effect twoShotSameSquare = (p1, victim, s) -> {
            checkSameSquare(p1, victim.get(0));
            PlayerBoard.giveNDamages(p1, victim.get(0), 2);
        };

        Effect movePlayerInVortexTwoShot = (p1, victim, s) -> {
            if (Board.isVisibleShoot(p1.getPlayerPosition(), s.get(0))>=1){
                if (Board.isVisibleRun(victim.get(0).getPlayerPosition(), s.get(0)) <= 1 && Board.isVisibleRun(victim.get(0).getPlayerPosition(), s.get(0)) != -1) {
                    victim.get(0).run(s.get(0));
                    PlayerBoard.giveNDamages(p1, victim.get(0), 2);
                }
            }
        };

        Effect oneShotTwoSquaresOneDistance = (p1, victim, s) -> {
            checkDistanceNotVisibleFixedDirection(p1.getPlayerPosition(), victim.get(0).getPlayerPosition(), 1);
            if(victim.size() == 2){
                checkDistanceNotVisibleFixedDirection(p1.getPlayerPosition(), victim.get(1).getPlayerPosition(), 2);
                checkDistanceNotVisibleFixedDirection(victim.get(0).getPlayerPosition(), victim.get(1).getPlayerPosition(), 1);
                PlayerBoard.giveNDamages(p1, victim.get(0), 1);
                PlayerBoard.giveNDamages(p1, victim.get(1), 1);
            }
            else if(victim.size() == 1){
                PlayerBoard.giveNDamages(p1, victim.get(0), 1);
            }
        };

        Effect oneShotVisibleMove1 = (p1, victim, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victim);
            if (s != null){
                if(s.get(0).isAdjacent(victim.get(0).getPlayerPosition())!='\u0000')
                    victim.get(0).run(s.get(0));
                else
                    throw new WrongSquareException(s.get(0));
            }
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
        };

        Effect twoShotVisibleMove1 = (p1, victim, s) -> {
            if(Board.isVisibleShoot(p1.getPlayerPosition(), victim.get(0).getPlayerPosition()) != -1){
                if (s != null) {
                    if (Board.isVisibleRun(victim.get(0).getPlayerPosition(), s.get(0)) == 1) {
                        PlayerBoard.giveNDamages(p1, victim.get(0), 2);
                        victim.get(0).run(s.get(0));
                    }
                }
                else
                    PlayerBoard.giveNDamages(p1, victim.get(0), 2);
            }
        };

        Effect oneShotSomePlayersAtOneDistanceDifferentSquares = (p1, victim, s) -> {
            allDifferentPositions(victim);
            allDifferentPlayers(victim);
            for(Player p : victim){
                checkAdjacentSquares(p1.getPlayerPosition(), p.getPlayerPosition(), p);
            }
            for(Player p : victim){
                PlayerBoard.giveNDamages(p1, p, 1);
            }
        };

        Effect run1OneShotTwoMarksAtOneDistance = (p1, victim, s) -> {
            checkDistanceVisibleShoot(p1.getPlayerPosition(), victim.get(0).getPlayerPosition(), 1, -1);
            p1.run(victim.get(0).getPlayerPosition());
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
            PlayerBoard.giveNMarks(p1, victim.get(0), 2);
        };

        switch (name) {
            case "lock rifle":
                effects[0] = twoShotMarkVisible;
                break;
            case "electroscythe":
                effects[0] = shootAllPlayersSameSquare;
                break;
            case "machine gun":
                effects[0] = oneShotVisibleSomePlayer;
                break;
            case "tractor beam":
                effects[0] = move012ShotVisible;
                break;
            case "vortex cannon":
                effects[0] = movePlayerInVortexTwoShot;
                break;
            case "furnace":
                effects[0] = shootAllPlayersAnotherVisibleRoom;
                break;
            case "heatseeker":
                effects[0] = threeShotNotVisible;
                break;
            case "whisper":
                effects[0] = threeShotMarkVisibleTwoDistance;
                break;
            case "hellion":
                effects[0] = shootOnePlayerAlmostOneDistanceMarkAllPlayers;
                break;
            case "flamethrower":
                effects[0] = oneShotTwoSquaresOneDistance;
                break;
            case "zx-2":
                effects[0] = shootTwoMarksVisible;
                break;
            case "grenade launcher":
                effects[0] = oneShotVisibleMove1;
                break;
            case "shotgun":
                effects[0] = threeShotSameSquareRun;
                break;
            case "rocket launcher":
                effects[0] = twoShotVisibleMove1;
                break;
            case "power glove":
                effects[0] = run1OneShotTwoMarksAtOneDistance;
                break;
            case "railgun":
                effects[0] = threeDamageOnePlayerOneDirection;
                break;
            case "shockwave":
                effects[0] = oneShotSomePlayersAtOneDistanceDifferentSquares;
                break;
            case "cyberblade":
                effects[0] = twoShotSameSquare;
                break;
            case "sledgehammer":
                effects[0] = twoMarkVisibleSameSquare;
                break;
            default:        //thor, plasma gun
                effects[0] = twoShotVisible;
        }
    }

    /**
     * Sets the opt1 effect for each weapon
     *
     * @param name      a String, the name of the weapon
     */
    private void setOpt1Effect(String name){

        Effect oneShotVisibleSomePlayer = (p1, victims, s) -> {
            for(Player p: victims){
                PlayerBoard.giveNDamages(p1, p, 1);
            }
        };

        Effect markVisible = (p1, victim, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victim);
            victim.get(0).getPb().addMarkedDamage(p1.getColor());
        };

        Effect run12 = (p1, victim, s) -> {
            checkDistanceVisibleRun(p1.getPlayerPosition(), s.get(0), 2, 1);
            p1.run(s.get(0));
        };

        Effect movePlayerInVortexOneShot = (p1, victim, s) -> {
            if (Board.isVisibleShoot(p1.getPlayerPosition(), s.get(0))>=1){
                for(Player p: victim) {
                    if (Board.isVisibleRun(p.getPlayerPosition(), s.get(0)) <= 1 && Board.isVisibleRun(victim.get(0).getPlayerPosition(), s.get(0)) != -1) {
                        p.run(s.get(0));
                        PlayerBoard.giveNDamages(p1, p, 1);
                    }
                }
            }
        };

        Effect oneShotVisibleSquareAllPlayers = (p1, victim, s) -> {
            checkSize(s, 2);
            checkDistanceVisibleShoot(p1.getPlayerPosition(), s.get(0), 3, 0);
            if(s.size()==2){
                checkDistanceVisibleRun(p1.getPlayerPosition(), s.get(0), 1, 0);
            }
            for (Player p : s.get(0).getPlayers()){
                PlayerBoard.giveNDamages(p1, p, 1);
            }
            if(s.size() == 2)
                p1.run(s.get(1));
        };

        switch (name) {
            case "lock rifle":
                effects[1] = markVisible;
                break;
            case "machine gun":
                effects[1] = oneShotVisibleSomePlayer;
                break;
            case "t.h.o.r.":
                effects[1] = oneShotVisibleSomePlayer;
                break;
            case "vortex cannon":
                effects[1] = movePlayerInVortexOneShot;
                break;
            case "plasma gun":
                effects[1] = run12;
                break;
            case "grenade launcher":
                effects[1] = oneShotVisibleSquareAllPlayers;
                break;
            case "rocket launcher":
                effects[1] = run12;
                break;
            default:
                effects[1] = run12;
        }
    }

    /**
     * Sets the opt2 effect for each weapon
     *
     * @param name      a String, the name of the weapon
     */
    private void setOpt2Effect(String name){
        Effect oneShotVisibleSomePlayer = (p1, victims, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victims);
            for(Player p: victims){
                PlayerBoard.giveNDamages(p1, p, 1);
                //p.getPb().addDamage(p1.getColor());
            }
        };

        Effect twoShotVisible = (p1, victims, s) -> {
            PlayerBoard.giveNDamages(p1, victims.get(0), 2);
        };

        /*Effect threeShotVisible = (p1, victim, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victim);
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
        };*/

        Effect twoShotSameSquare = (p1, victim, s) -> {
            checkSameSquare(p1, victim.get(0));
            PlayerBoard.giveNDamages(p1, victim.get(0), 2);
        };

        Effect twoShotVisibleMove1 = (p1, victim, s) -> {
            for(Player p : s.get(0).getPlayers()){
                PlayerBoard.giveNDamages(p1, p, 1);
            }
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
        };

        switch (name) {
            case "machine gun":
                effects[2] = oneShotVisibleSomePlayer;
                break;
            case "t.h.o.r.":
                effects[2] = twoShotVisible;
                break;
            case "plasma gun":
                effects[2] = oneShotVisibleSomePlayer;
                break;
            case "rocket launcher":
                effects[2] = twoShotVisibleMove1;
                break;
            default:
                effects[2] = twoShotSameSquare;
        }
    }

    /**
     * Sets the alt effect for each weapon
     *
     * @param name      a String, the name of the weapon
     */
    private void setAltEffect(String name){

        Effect twoShotAllPlayersSameSquare = (p1, victim, s) -> {
            for (Player p : p1.getPlayerPosition().getPlayers()){
                if (p != p1) {
                    PlayerBoard.giveNDamages(p1, p, 2);
                }
            }
        };

        Effect shootMarkAllPlayersOneDistance = (p1, victim, s) -> {
            checkDistanceVisibleShoot(p1.getPlayerPosition(), s.get(0), 1, -1);
            int count = 0;
            for(Player p : s.get(0).getPlayers()){
                count++;
                PlayerBoard.giveNDamages(p1, p, 1);
                p.getPb().addMarkedDamage(p1.getColor());
                //p.getPb().addDamage(p1.getColor());
            }
            if(count==0)
                throw new WrongValueException();
        };

        Effect move012ToSameSquareThreeShots = (p1, victim, s) -> {
            checkDistanceVisibleShoot(p1.getPlayerPosition(), p1.getPlayerPosition(), 2, 0);
            //victim.get(0).setPlayerPosition(p1.getPlayerPosition(), victim.get(0));
            victim.get(0).setPlayerPosition(p1.getPlayerPosition());
            PlayerBoard.giveNDamages(p1, victim.get(0), 3);
        };

        Effect threeShotSameSquareMove012CertainDirection = (p1, victim, s) -> {
            checkSameSquare(p1, victim.get(0));
            if (s != null) {
                checkDistanceVisibleShoot(p1.getPlayerPosition(), s.get(0), 2, 0);
                checkFixedDirectionReachable(p1.getPlayerPosition(), s.get(0));
            }
            PlayerBoard.giveNDamages(p1, victim.get(0), 3);
            if (s != null){
                victim.get(0).run(s.get(0));
            }
        };

        Effect shootOnePlayerAlmostOneDistanceTwoMarkAllPlayers = (p1, victim, s) -> {
            checkDistanceVisibleShoot(p1.getPlayerPosition(), victim.get(0).getPlayerPosition(), 3, 1);
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
            for(Player p : victim.get(0).getPlayerPosition().getPlayers()){
                PlayerBoard.giveNMarks(p1, p, 2);
            }
        };

        Effect twoDamage12PlayerOneDirection = (p1, victim, s) ->{
            checkGenericFixedDirection(p1.getPlayerPosition(), victim.get(0).getPlayerPosition());
            if (victim.size() == 2) {
                checkGenericFixedDirection(p1.getPlayerPosition(), victim.get(1).getPlayerPosition());
                checkGenericFixedDirection(victim.get(0).getPlayerPosition(), victim.get(1).getPlayerPosition());
                PlayerBoard.giveNDamages(p1, victim.get(0), 2);
                PlayerBoard.giveNDamages(p1, victim.get(1), 2);
            }else
                PlayerBoard.giveNDamages(p1, victim.get(0), 2);
        };

        Effect markSomeVisiblePlayers = (p1, victim, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victim);
            allDifferentPlayers(victim);
            countPlayersBetween(victim, 0, 4);
            for(Player p : victim)
                p.getPb().addMarkedDamage(p1.getColor());
        };

        Effect twoShotTwoSquaresOneDistance= (p1, victim, s) -> {
            checkDistanceNotVisibleFixedDirection(p1.getPlayerPosition(), s.get(0), 1);
            if (s.size() == 2){
                checkDistanceNotVisibleFixedDirection(p1.getPlayerPosition(), s.get(1), 2);
                checkDistanceNotVisibleFixedDirection(s.get(0), s.get(1), 1);
                for (Player p : s.get(1).getPlayers())
                    PlayerBoard.giveNDamages(p1, p, 1);
            }
            for (Player p : s.get(0).getPlayers())
                PlayerBoard.giveNDamages(p1, p, 2);
        };

        Effect twoShotOneDistance = (p1, victim, s) -> {
            checkDistanceNotVisibleFixedDirection(p1.getPlayerPosition(), victim.get(0).getPlayerPosition(), 1);
            PlayerBoard.giveNDamages(p1, victim.get(0), 2);
        };

        Effect oneShotAllPlayersAtOneDistance = (p1, victim, s) -> {
            ArrayList<GenericSquare> onedist = new ArrayList<>();
            for(Square s1 : Board.getSquares()){
                if(p1.getPlayerPosition().isAdjacent(s1)!='\u0000')
                    onedist.add(s1);
            }
            for(SpawnpointSquare s2 : Board.getSpawnpointSquares()){
                if(p1.getPlayerPosition().isAdjacent(s2)!='\u0000')
                    onedist.add(s2);
            }
            if(onedist.isEmpty())
                throw new WrongValueException();
            for(GenericSquare s1 : onedist){
                for(Player p: s1.getPlayers()){
                    PlayerBoard.giveNDamages(p1, p, 1);
                }
            }
        };

        Effect run1TwoShots = (p1, victim, s) -> {
            if (Board.isVisibleRun(p1.getPlayerPosition(), s.get(0)) == 1) {
                if(victim.size() > s.size()) //it shouldn't happen, but added anyway
                    throw new WrongValueException();
                if (victim.size() == 2) {
                    if (Board.isVisibleRun(s.get(0), s.get(1)) != 1)
                        throw new WrongValueException();
                    if (!s.get(0).getPlayers().contains(victim.get(0)) || !s.get(1).getPlayers().contains(victim.get(1)))
                        throw new WrongValueException();
                } else if (victim.size() == 1 && !s.get(0).getPlayers().contains(victim.get(0)) && !s.get(1).getPlayers().contains(victim.get(0)))
                    throw new WrongValueException();

                if (s.size() == 2) {
                    if (victim.size() == 2) {
                        if (!Board.fixedDirectionReachable(s.get(0), s.get(1)))
                            throw new WrongValueException();
                        p1.run(s.get(0));
                        PlayerBoard.giveNDamages(p1, victim.get(0), 2);
                        p1.run(s.get(1));
                        PlayerBoard.giveNDamages(p1, victim.get(1), 2);
                    } else if (victim.size() == 1) {
                        if (!Board.fixedDirectionReachable(s.get(0), s.get(1)))
                            throw new WrongValueException();
                        if (!s.get(0).getPlayers().contains(victim.get(0)) && !s.get(1).getPlayers().contains(victim.get(0)))
                            throw new WrongValueException();
                        p1.run(s.get(0));
                        if(victim.get(0).getPlayerPosition() == s.get(0))
                            PlayerBoard.giveNDamages(p1, victim.get(0), 2);
                        else{
                            p1.run(s.get(1));
                            PlayerBoard.giveNDamages(p1, victim.get(0), 2);
                        }
                    }
                } else if (s.size() == 1) {
                    if (victim.size() == 1) {
                        if (!s.get(0).getPlayers().contains(victim.get(0)))
                            throw new WrongValueException();
                        PlayerBoard.giveNDamages(p1, victim.get(0), 2);
                    }
                    p1.run(s.get(0));
                }
            }
            else
                throw new WrongValueException();
        };

        switch (name) {
            case "electroscythe":
                effects[3] = twoShotAllPlayersSameSquare;
                break;
            case "tractor beam":
                effects[3] = move012ToSameSquareThreeShots;
                break;
            case "furnace":
                effects[3] = shootMarkAllPlayersOneDistance;
                break;
            case "hellion":
                effects[3] = shootOnePlayerAlmostOneDistanceTwoMarkAllPlayers;
                break;
            case "flamethrower":
                effects[3] = twoShotTwoSquaresOneDistance;
                break;
            case "zx-2":
                effects[3] = markSomeVisiblePlayers;
                break;
            case "shotgun":
                effects[3] = twoShotOneDistance;
                break;
            case "power glove":
                effects[3] = run1TwoShots;
                break;
            case "railgun":
                effects[3] = twoDamage12PlayerOneDirection;
                break;
            case "shockwave":
                effects[3] = oneShotAllPlayersAtOneDistance;
                break;
            default:        //Sledgehammer
                effects[3] = threeShotSameSquareMove012CertainDirection;
        }
    }

    /**
     * Sets the applier for each weapon
     *
     * @param name      a String, the name of the weapon
     */
    private void setApplier(String name){

        WeaponApplier noConditionsBaseAlt = (p, victims, s, e1, e2, e3, b) -> {
            e1.applyOn(p, victims, s);
        };

        /*WeaponApplier noConditionsOpt = (p, victims, s, e1, e2, e3, b) -> {
            if (e1 != null){
                e1.applyOn(p, victims, s);
            }
            if (e2 != null) {
                e2.applyOn(p, victims, s);
            }
            if (e3 != null) {
                e3.applyOn(p, victims, s);
            }
        };*/

        WeaponApplier lockRifle = (p, victims, s, e1, e2, e3, b) -> {
            allDifferentPlayers(victims);
            if(e2 != null && victims.size() < 2)
                throw new WrongValueException();
            ArrayList<Player> temp = new ArrayList<>();
            temp.add(victims.get(0));
            e1.applyOn(p, temp, null);
            if(e2 != null){
                temp.clear();
                temp.add(victims.get(1));
                e2.applyOn(p, temp, null);
            }
        };

        WeaponApplier cyberBlade = (p, victims, s, e1, e2, e3, b) -> {
            ArrayList<Player> temp = new ArrayList<>();
            if(victims.size()>2)
                throw new WrongValueException();
            allDifferentPlayers(victims);
            if(b){
                if(e2 != null && victims.get(0).getPlayerPosition() != s.get(0))
                    throw new WrongValueException();
                if(e3 != null && victims.get(1).getPlayerPosition() != s.get(0))
                    throw new WrongValueException();
                e1.applyOn(p, victims, s);
                if(e2!=null) {
                    temp.add(victims.get(0));
                    e2.applyOn(p, temp, s);
                }
                if(e3!=null) {
                    temp.clear();
                    temp.add(victims.get(1));
                    e3.applyOn(p, temp, s);
                }
            }
            else{
                if(victims.get(0).getPlayerPosition() != p.getPlayerPosition())
                    throw new WrongValueException();
                if(victims.size() > 1 && victims.get(1).getPlayerPosition()!=s.get(0) && victims.get(1).getPlayerPosition()!=p.getPlayerPosition())
                    throw new WrongValueException();
                temp.add(victims.get(0));
                e1.applyOn(p, victims, s);
                temp.clear();
                if(victims.size() > 1)
                    temp.add(victims.get(1));
                if(e2!=null) {
                    e2.applyOn(p, temp, s);
                }
                if(e3!=null)
                    e3.applyOn(p, temp, s);
            }
        };

        WeaponApplier rocketLauncher = (p, victims, s, e1, e2, e3, b) -> {
            checkSize(s, 2);
            ArrayList<GenericSquare> temp = new ArrayList<>();
            GenericSquare t = victims.get(0).getPlayerPosition();
            checkPlayersVisibilityShoot(p.getPlayerPosition(), victims);                            //base check
            if(s.size()==2 && s.get(0).isAdjacent(victims.get(0).getPlayerPosition())== '\u0000')
                throw new WrongSquareException(s.get(0));
            //1 victim & 1 square for base, 1 square for opt1, nothing for opt2
            if(b){ //opt1 && base || opt1 && base && opt2
                checkDistanceVisibleRun(p.getPlayerPosition(), s.get(0), 2, 1);             //opt1 check
                temp.add(s.get(0));
                e1.applyOn(p, victims, temp);      //opt1
                temp.clear();
                temp.add(s.get(1));
                e2.applyOn(p, victims, temp);
                temp.clear();
                if(e3!=null){
                    temp.add(t);
                    e3.applyOn(p, victims, temp);
                }
            }
            else{ //base && opt2 || base && opt2 && opt1 || base && opt1
                if(s.size()==2){
                    checkDistanceVisibleRun(p.getPlayerPosition(), s.get(1), 2, 1); //opt1 checks
                    if(e3 == null) { //base opt1
                        temp.add(s.get(0));
                        e1.applyOn(p, victims, temp);                  //base
                        temp.clear();
                        e2.applyOn(p, null, temp);
                    }
                    else{   //base opt2 opt1
                        temp.add(s.get(0));
                        e1.applyOn(p, victims, temp);                  //base
                        temp.clear();
                        temp.add(t);
                        e2.applyOn(p, victims, temp);                   //opt2
                        temp.clear();
                        temp.add(s.get(1));
                        e3.applyOn(p, victims, temp);              //opt1
                        temp.clear();
                    }
                }
                else{
                    if(s.size()==1) {       //base, base opt2
                        temp.add(s.get(0));
                        temp.add(s.get(0));
                        e1.applyOn(p, victims, temp);                  //base
                        temp.clear();
                        if (e2 != null) {
                            temp.add(t);
                            e2.applyOn(p, victims, temp);               //opt2
                        }
                    }
                }
            }
        };

        WeaponApplier vortexCannon = (p, victims, s, e1, e2, e3, b) -> {
            allDifferentPlayers(victims);
            if (victims.size() > 3) //it shouldn't happened, but added anyway
                throw new WrongValueException();
            if(s.isEmpty()){ //it shouldn't happened, but added anyway
                throw new WrongValueException();
            }
            ArrayList<Player> temp = new ArrayList<>();
            temp.add(victims.get(0));
            e1.applyOn(p, temp, s);
            if(e2!=null){
                temp.remove(0);
                temp.add(victims.get(1));
                if(victims.size()==3)
                    temp.add(victims.get(2));
                e2.applyOn(p, temp, s);
            }
        };

        WeaponApplier grenadeLauncher = (p, victims, s, e1, e2, e3, b) -> {
            if(victims.size()>1)
                throw new WrongValueException();
            if(s == null){
                checkPlayersVisibilityShoot(p.getPlayerPosition(), victims);
                e1.applyOn(p, victims, null);
            }
            else {
                checkSize(s, 2);
                ArrayList<GenericSquare> temp = new ArrayList<>();
                if(s.size()==2){
                    temp.add(s.get(0));
                    e1.applyOn(p, victims, temp); //base effect
                    temp.clear();
                    temp.add(s.get(1));
                    e2.applyOn(p, victims, temp);
                }
                else{
                    if(e2 != null && s.size() == 1) {
                        if (b) {
                            e1.applyOn(p, victims, s);
                            e2.applyOn(p, victims, null);
                        } else {
                            e1.applyOn(p, victims, null);
                            e2.applyOn(p, victims, s);
                        }
                    }
                    else if(e2 == null)
                        e1.applyOn(p, victims, s);
                }
            }
        };

        WeaponApplier machineGun = (p, victims, s, e1, e2, e3, b) -> {
            ArrayList<Player> temp = new ArrayList<>();
            checkPlayersVisibilityShoot(p.getPlayerPosition(), victims);
            if (e2 == null && e3 == null) { //base
                if (victims.size() > 2) {
                    throw new WrongValueException();
                }
                allDifferentPlayers(victims);
                e1.applyOn(p, victims, s); //1 or 2 targets
            }
            else {
                if (e2 != null && e3 == null){ //base and opt1
                    if(victims.size() > 3)
                        throw new WrongValueException();
                    temp.add(victims.get(0));
                    if(victims.size() == 3){
                        if (victims.get(0) == victims.get(1)) //same player 2 times for base
                            throw new WrongValueException();
                        if(victims.get(2) != victims.get(0) && victims.get(2) != victims.get(1))
                            throw new WrongValueException();
                        temp.add(victims.get(1));
                        e1.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(2));
                        e2.applyOn(p, temp, s);

                    }else if(victims.size()==2) {
                        e1.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(1));
                        e2.applyOn(p, temp, s);
                    } else                              //it shouldn't happen, but added anyway
                        throw new WrongValueException();
                }
                else if (e2!=null){ //base, opt1, opt2
                    temp.add(victims.get(0));
                    if(victims.size() == 3){
                        e1.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(1));
                        e2.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(2));
                        e3.applyOn(p, temp, s);
                    }
                    else if(victims.size() == 4) {
                        if (victims.get(0) == victims.get(1)) //1 base, 1 opt1, 2 opt2 -> NO
                            throw new WrongValueException();
                        if(victims.get(2) == victims.get(3))
                            throw new WrongValueException();
                        temp.add(victims.get(1));   //2 base, 1 opt, 1 opt2
                        e1.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(2));
                        e2.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(3));
                        e3.applyOn(p, temp, s);
                    }
                    else if (victims.size()==5){
                        ArrayList<Player> prova = new ArrayList<>();
                        temp.add(victims.get(0));
                        prova.add(victims.get(0));
                        prova.add(victims.get(1));
                        if(prova.get(0)!=victims.get(2) && prova.get(1)!=victims.get(2))
                            throw new WrongValueException();
                        if(prova.get(0)!=victims.get(3) && prova.get(0)!=victims.get(4) && prova.get(1)!=victims.get(3) && prova.get(1)!=victims.get(4))
                            throw new WrongValueException();
                        if(victims.get(2) == victims.get(3) && victims.get(2) == victims.get(4))
                            throw new WrongValueException();
                        temp.add(victims.get(1));
                        e1.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(2));
                        e2.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(3));
                        temp.add(victims.get(4));
                        e3.applyOn(p, temp, s);
                    }
                }
                else{   //base, opt2
                    if(victims.size()==2){
                        temp.add(victims.get(0));
                        e1.applyOn(p, temp, s);
                        temp.clear();
                        temp.add(victims.get(1));
                        e3.applyOn(p, victims, s);
                    }
                    else if(victims.size()==3){
                        ArrayList<Player> prova = new ArrayList<>();
                        prova.add(victims.get(0));
                        prova.add(victims.get(1));
                        if(prova.get(0)!=victims.get(2) && prova.get(1)!=victims.get(2)) {
                            throw new WrongValueException();
                        }
                        else{
                            temp.add(victims.get(0));
                            e1.applyOn(p, temp, s);
                            temp.clear();
                            temp.add(victims.get(1));
                            temp.add(victims.get(2));
                            e3.applyOn(p, victims, s);
                        }
                    }
                    else if(victims.size()==4){
                        temp.add(victims.get(0));
                        temp.add(victims.get(1));
                        e1.applyOn(p, victims, s);
                        temp.clear();
                        temp.add(victims.get(2));
                        temp.add(victims.get(3));
                        e3.applyOn(p, victims, s);
                    }
                }
            }
        };

        WeaponApplier thor = (p, victims, s, e1, e2, e3, b) -> {
            allDifferentPlayers(victims);
            ArrayList<Player> temp = new ArrayList<>(victims);
            temp.add(victims.get(0));
            checkPlayersVisibilityShoot(p.getPlayerPosition(), temp);
            if(e2!=null) {
                temp.clear();
                temp.add(victims.get(1));
                checkPlayersVisibilityShoot(victims.get(0).getPlayerPosition(), temp);
            }
            if(e3!=null) {
                temp.clear();
                temp.add(victims.get(2));
                checkPlayersVisibilityShoot(victims.get(1).getPlayerPosition(), temp);
            }
            temp.clear();
            temp.add(victims.get(0));
            e1.applyOn(p, temp, s);
            if(e2!=null){
                temp.clear();
                temp.add(victims.get(1));
                e2.applyOn(p, temp, s);
            }
            if(e3!=null){               //base, opt1, opt2
                temp.clear();
                temp.add(victims.get(2));
                e3.applyOn(p, temp, s);
            }
        };

        WeaponApplier plasmaGun = (p, victims, s, e1, e2, e3, b) -> {
            if(victims.size()>1)
                throw new WrongValueException();
            if (e1 != null){
                e1.applyOn(p, victims, s);
            }
            if (e2 != null) {
                e2.applyOn(p, victims, s);
            }
            if (e3 != null) {
                e3.applyOn(p, victims, s);
            }


        };

        switch (name){
            case "lock rifle":
                applier = lockRifle;
                break;
            case "machine gun":
                applier = machineGun;
                break;
            case "t.h.o.r.":
                applier = thor;
                break;
            case "vortex cannon":
                applier = vortexCannon;
                break;
            case "plasma gun":
                applier = plasmaGun;
                break;
            case "grenade launcher":
                applier = grenadeLauncher;
                break;
            case "rocket launcher":
                applier = rocketLauncher;
                break;
            case "cyberblade":
                applier = cyberBlade;
                break;
            default:
                applier = noConditionsBaseAlt;
        }

    }

    /**
     * Checks if all players are in different squares
     *
     * @param p                         a List of player
     * @throws WrongPlayerException     if two or more players are in the same square
     */
    private void allDifferentPositions(List<Player> p) throws WrongPlayerException {
        allDifferentPlayers(p);
        ArrayList<GenericSquare> t = new ArrayList<>();
        for(Player p1 : p)
            t.add(p1.getPlayerPosition());
        for(int i=0; i<p.size()-1; i++){
            for(int j=i+1; j<p.size(); j++) {
                if (p.get(i).getPlayerPosition() == p.get(j).getPlayerPosition()){
                    throw new WrongPlayerException(p.get(j));
                }
            }
        }
    }

    /**
     * Checks if two players are in the same square
     *
     * @param p1                        a player
     * @param p2                        a player
     * @throws WrongPlayerException     if the two players are not in the same square
     */
    private void checkSameSquare(Player p1, Player p2) throws WrongPlayerException {
        if (p1.getPlayerPosition() != p2.getPlayerPosition())
            throw new WrongPlayerException(p2);
    }

    /**
     * Checks if the second square is in a fixed cardinal direction considering the first
     *
     * @param s1                        a GenericSquare
     * @param s2                        a GenericSquare
     * @throws WrongSquareException     if the second player is not in a fixed cardinal direction considering the first one
     */
    private void checkGenericFixedDirection(GenericSquare s1, GenericSquare s2) throws WrongSquareException {
        if (Board.genericFixedDirection(s1, s2) == -1)
            throw new WrongSquareException(s2);
    }

    /**
     * Checks if the number of players is in a specific range (between two parameters)
     *
     * @param p                         a List of player
     * @param n                         an int, the minimum number of players
     * @param m                         an int, the maximum number of players
     * @throws WrongValueException      if the number of player is not in the specific range
     */
    private void countPlayersBetween(List<Player> p, int n, int m) throws WrongValueException {
        if (!(n <= p.size() && m >= p.size()))
            throw new WrongValueException();
    }

    /**
     * Checks if the second GenericSquare has a certain distance and is not visible by the first one
     *
     * @param s1                        a GenericSquare
     * @param s2                        a GenericSquare
     * @param n                         an int, the distance
     * @throws WrongSquareException     if the second player is visible by the first one or has not distance n
     */
    private void checkDistanceNotVisibleFixedDirection(GenericSquare s1, GenericSquare s2, int n) throws WrongSquareException {
        if (Board.genericFixedDirection(s1, s2) != n)
            throw new WrongSquareException(s2);
    }

    /**
     * Checks if the victimPosition is adjacent to the one of the player who's attacking
     *
     * @param s1                        a GenericSquare (attacker's position)
     * @param s2                        a GenericSquare (victim's position)
     * @param p                         a player (the victim)
     * @throws WrongPlayerException     if the two squares are not adjacent
     */
    private void checkAdjacentSquares(GenericSquare s1, GenericSquare s2, Player p) throws WrongPlayerException {
        if(s1.isAdjacent(s2)=='\u0000')
            throw new WrongPlayerException(p);
    }

    /**
     * Checks if a list of squares contains too many elements
     *
     * @param s                         a List of GenericSquares
     * @param max                       an int, the max size
     * @throws WrongValueException      if the list contains too many elements
     */
    private void checkSize(List<GenericSquare> s, int max) throws WrongValueException {
        if(s.size()>max)
            throw new WrongValueException();
    }

    /**
     * Checks if all the players are visible by a GenericSquare
     *
     * @param s                         a GenericSquare
     * @param p                         a List of player
     * @throws WrongPlayerException     if any player is not visible
     */
    private void checkPlayersNotVisibility(GenericSquare s, List<Player> p) throws WrongPlayerException {
        for (Player t : p)
            if (Board.isVisibleShoot(s, t.getPlayerPosition()) != -1)
                throw new WrongPlayerException(t);
    }

    /**
     * Checks if the second GenericSquare belongs to a room that is visible by the first GenericSquare
     *
     * @param s1                        a GenericSquare
     * @param s2                        a GenericSquare
     * @throws WrongSquareException     if s2 does not belong to a room visible by s1
     */
    private void checkVisibleRoom(GenericSquare s1, GenericSquare s2) throws WrongSquareException {
        if (s1.getRoom() == s2.getRoom())
            throw new WrongSquareException(s2);
        for (GenericSquare t : Board.squareOfARoom(s2.getRoom()))
            if (Board.isVisibleShoot(s1, t) != -1)
                return;
        throw new WrongSquareException(s2);
    }

    /**
     * Checks if all players are different one another
     *
     * @param p                         a List of player
     * @throws WrongPlayerException     if a player is present in the list twice
     */
    private void allDifferentPlayers(List<Player> p) throws WrongPlayerException {
        if(p.size()==1)
            return;
        ArrayList<Player> t = new ArrayList<>();
        for (Player s : p)
            if (!t.contains(s))
                t.add(s);
            else
                throw new WrongPlayerException(s);
    }

    /**
     * Sets the description of the effects for each Weapon
     *
     * @param name      a String, the name of the Weapon
     */
    private void setDescriptions(String name){
        switch (name){
            case "lock rifle":
                descriptions[0] = "Deal 2 damage and 1 mark to 1 target you can see.";
                descriptions[1] = "with second lock: Deal 1 mark to a different target you can see.";
                break;
            case "electroscythe":
                descriptions[0] = "Deal 1 damage to every other player on your square.";
                descriptions[3] = "reaper mode: Deal 2 damage to every other player on your square.";
                break;
            case "machine gun":
                descriptions[0] = "Choose 1 or 2 targets you can see and deal 1 damage to each.";
                descriptions[1] = "with focus shot: Deal 1 additional damage to one of those targets.";
                descriptions[2] = "with turret tripod: Deal 1 additional damage to the other of those targets and/or deal 1 damage to a different target you can see.";
                break;
            case "tractor beam":
                descriptions[0] = "Move a target 0, 1, or 2 squares to a square you can see, and give it 1 damage.";
                descriptions[3] = "punisher mode: Choose a target 0, 1, or 2 moves away from you. Move the target to your square and deal 3 damage to it.";
                break;
            case "t.h.o.r.":
                descriptions[0] = "Deal 2 damage to 1 target you can see.";
                descriptions[1] = "with chain reaction: Deal 1 damage to a second target that your first target can see.";
                descriptions[2] = "with high voltage: Deal 2 damage to a third target that your second target can see. You cannot use this effect unless you first use the chain reaction.";
                break;
            case "vortex cannon":
                descriptions[0] = "Choose a square you can see, but not your square. Call it \"the vortex\". Choose a target on the vortex or 1 move away from it. Move it onto the vortex and give it 2 damage.";
                descriptions[1] = "with black hole: Choose up to 2 other targets on the vortex or 1 move away from it. Move them onto the vortex and give them each 1 damage.";
                break;
            case "plasma gun":
                descriptions[0] = "Deal 2 damage to 1 target you can see.";
                descriptions[1] = "with phase glide: Move 1 or 2 squares. This effect can be used either before or after the basic effect.";
                descriptions[2] = "with charged shot: Deal 1 additional damage to your target.";
                break;
            case "furnace":
                descriptions[0] = "Choose a room you can see, but not the room you are in. Deal 1 damage to everyone in that room.";
                descriptions[3] = "cozy fire mode: Choose a square exactly one move away. Deal 1 damage and 1 mark to everyone on that square.";
                break;
            case "heatseeker":
                descriptions[0] = "Choose 1 target you cannot see and deal 3 damage to it.";
                break;
            case "whisper":
                descriptions[0] = "Deal 3 damage and 1 mark to 1 target you can see. Your target must be at least 2 moves away from you.";
                break;
            case "hellion":
                descriptions[0] = "Deal 1 damage to 1 target you can see at least 1 move away. Then give 1 mark to that target and everyone else on that square.";
                descriptions[3] = "nano-tracer mode: Deal 1 damage to 1 target you can see at least 1 move away. Then give 2 marks to that target and everyone else on that square.";
                break;
            case "flamethrower":
                descriptions[0] = "Choose a square 1 move away and possibly a second square 1 more move away in the same direction. On each square, you may choose 1 target and give it 1 damage.";
                descriptions[3] = "barbecue mode: Choose 2 squares as above. Deal 2 damage to everyone on the first square and 1 damage to everyone on the second square.";
                break;
            case "zx-2":
                descriptions[0] = "Deal 1 damage and 2 marks to 1 target you can see.";
                descriptions[3] = "scanner mode: Choose up to 3 targets you can see and deal 1 mark to each.";
                break;
            case "grenade launcher":
                descriptions[0] = "Deal 1 damage to 1 target you can see. Then you may move the target 1 square.";
                descriptions[1] = "with extra grenade: Deal 1 damage to every player on a square you can see. You can use this before or after the basic effect's move.";
                break;
            case "shotgun":
                descriptions[0] = "Deal 3 damage to 1 target on your square. If you want, you may then move the target 1 square.";
                descriptions[3] = "long barrel mode: Deal 2 damage to 1 target on any square exactly one move away.";
                break;
            case "rocket launcher":
                descriptions[0] = "Deal 2 damage to 1 target you can see that is not on your square. Then you may move the target 1 square.";
                descriptions[1] = "with rocket jump: Move 1 or 2 squares. This effect can be used either before or after the basic effect.";
                descriptions[2] = "with fragmenting warhead: During the basic effect, deal 1 damage to every player on your target's original square – including the target, even if you move it.";
                break;
            case "power glove":
                descriptions[0] = "Choose 1 target on any square exactly 1 move away. Move onto that square and give the target 1 damage and 2 marks.";
                descriptions[3] = "rocket fist mode: Choose a square exactly 1 move away. Move onto that square. You may deal 2 damage to 1 target there. If you want, you may move 1 more square in that same direction (but only if it is a legal move). You may deal 2 damage to 1 target there, as well.";
                break;
            case "railgun":
                descriptions[0] = "Choose a cardinal direction and 1 target in that direction. Deal 3 damage to it.";
                descriptions[3] = "piercing mode: Choose a cardinal direction and 1 or 2 targets in that direction. Deal 2 damage to each.";
                break;
            case "shockwave":
                descriptions[0] = "Choose up to 3 targets on different squares, each exactly 1 move away. Deal 1 damage to each target.";
                descriptions[3] = "tsunami mode: Deal 1 damage to all targets that are exactly 1 move away.";
                break;
            case "cyberblade":
                descriptions[0] = "Deal 2 damage to 1 target on your square.";
                descriptions[1] = "with shadowstep: Move 1 square before or after the basic effect.";
                descriptions[2] = "with slice and dice: Deal 2 damage to a different target on your square. The shadowstep may be used before or after this effect.";
                break;
            default:        //sledgehammer
                descriptions[0] = "Deal 2 damage to 1 target on your square.";
                descriptions[3] = "pulverize mode: Deal 3 damage to 1 target on your square, then move that target 0, 1, or 2 squares in one direction.";
        }
    }

    //GETTER
    /**
     * Getter for the effects' applier
     *
     * @return      the WeaponApplier
     */
    public WeaponApplier getApplier() {
        return applier;
    }

    /**
     * Getter for all the booleans of the Weapon
     *
     * @return      an array of boolean, which contains several attributes of the weapons which are set in the factory (like which effects has the weapon, the attribute justGrabbed, Loaded,...)
     */
    public boolean[] getBooleans() {
        return booleans;
    }

    /**
     * Getter for the effects of the Weapon
     *
     * @return      an array of Effect
     */
    public Effect[] getEffects() {
        return effects;
    }

    /**
     * Getter for getRequestedNum
     *
     * @return      an array of int, which contains several attributes of the weapons which are set in the factory (like the amount of needed victims and squares)
     */
    public int[] getRequestedNum() {
        return requestedNum;
    }

    /**
     * Getter for the descriptions of each effect
     *
     * @return      an array of String
     */
    public String[] getDescriptions() {
        return descriptions;
    }

    /**
     * Getter for all the costs of the Weapon
     *
     * @return      a List of array of char
     */
    public List<char[]> getCosts() {
        return costs;
    }

}
