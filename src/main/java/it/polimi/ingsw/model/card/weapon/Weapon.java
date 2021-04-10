package it.polimi.ingsw.model.card.weapon;

import it.polimi.ingsw.model.card.Effect;
import it.polimi.ingsw.model.card.EffectManager;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import java.io.Serializable;
import java.util.List;

public class Weapon extends EffectManager implements Serializable {

    private String name;
    private transient WeaponApplier applier;
    private char[] cost; //{'\u0000', '\u0000', '\u0000'};      //max 3 letters of ammo color
    private char[] costOpt; //{'\u0000', '\u0000'};   //one letter of ammo color for 2 opt effects
    private char[] costAlt; //{'\u0000', '\u0000'};   //max 2 letters of ammo color
    private boolean loaded;
    private String baseDescription;
    private String opt1Description;
    private String opt2Description;
    private String altDescription;
    private transient Effect base;
    private transient Effect opt1;
    private boolean opt1Effect;
    private transient Effect opt2;
    private boolean opt2Effect;
    private transient Effect alt;
    private boolean altEffect;
    private int nTargetsBase;
    private int nTargetsOpt1;
    private int nTargetsOpt2;
    private int nTargetsAlt;
    private int nSquaresBase;
    private int nSquaresOpt1;
    private int nSquaresOpt2;
    private int nSquaresAlt;
    private int particularWeapon;
    private boolean notNecessarySquare;

    /**
     * Constructor of Weapon (its conditions, like "loaded" and "active", and its own effects)
     *
     * @param name          a String, which refers to the name of the card
     * @param booleans      an array of booleans
     * @param costs         a List of array of char
     * @param requestedNum  an array of int
     * @param applier       a WeaponApplier
     * @param effects       an array of Effects
     * @param descriptions  an array of Strings
     */
    public Weapon(String name, boolean[] booleans, List<char[]> costs, int[] requestedNum, WeaponApplier applier, Effect[] effects, String[] descriptions) {

        this.name = name;
        this.loaded = booleans[0];
        this.cost = costs.get(0);
        this.costOpt = costs.get(1);
        this.costAlt = costs.get(2);
        this.opt1Effect = booleans[1];
        this.opt2Effect = booleans[2];
        this.altEffect = booleans[3];
        this.notNecessarySquare = booleans[4];
        this.nTargetsBase = requestedNum[0];
        this.nTargetsOpt1 = requestedNum[1];
        this.nTargetsOpt2 = requestedNum[2];
        this.nTargetsAlt = requestedNum[3];
        this.nSquaresAlt = requestedNum[7];
        this.nSquaresBase = requestedNum[4];
        this.nSquaresOpt1 = requestedNum[5];
        this.nSquaresOpt2 = requestedNum[6];
        this.particularWeapon = requestedNum[8];
        this.applier = applier;
        this.base = effects[0];
        this.opt1 = effects[1];
        this.opt2 = effects[2];
        this.alt = effects[3];
        this.baseDescription = descriptions[0];
        this.opt1Description = descriptions[1];
        this.opt2Description = descriptions[2];
        this.altDescription = descriptions[3];
    }

    /**
     * Getter for loaded
     *
     * @return      a boolean
     */
    public boolean getLoaded() {
        return loaded;
    }

    /**
     * Getter for name
     *
     * @return      a String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for cost
     *
     * @return      an array of char
     */
    public char[] getCost() {
        return cost;
    }

    /**
     * Getter for costOpt
     *
     * @return      an array of char
     */
    public char[] getCostOpt() {
        return costOpt;
    }

    /**
     * Getter for costAlt
     *
     * @return      an array of char
     */
    public char[] getCostAlt() {
        return costAlt;
    }

    /**
     * Getter for the base effect description
     *
     * @return      a String
     */
    public String getBaseDescription() {
        return baseDescription;
    }

    /**
     * Getter for the first optional effect description
     *
     * @return      a String
     */
    public String getOpt1Description() {
        return opt1Description;
    }

    /**
     * Getter for the second optional effect description
     *
     * @return      a String
     */
    public String getOpt2Description() {
        return opt2Description;
    }

    /**
     * Getter for the alternative effect description
     *
     * @return      a String
     */
    public String getAltDescription() {
        return altDescription;
    }

    /**
     * Setter for loaded
     *
     * @param load      a boolean
     */
    public void setLoaded(boolean load) {
        loaded = load;
    }

    /**
     * Getter for an effect
     *
     * @param n     a String, the name of the Effect to get
     * @return      an Effect
     */
    public Effect getEffect(String n){
        switch (n){
            case "base":
                return this.base;
            case "alt":
                return this.alt;
            case "opt1":
                return this.opt1;
            case "opt2":
                return this.opt2;
            default:
                return null;
        }
    }

    /**
     * Does the chosen effects (the player p "attacks" the players and on squares selected)
     *
     * @param e1            a String, the first effect
     * @param e2            a String, the second effect
     * @param e3            a String, the third effect
     * @param p             a player, the one who attacks
     * @param players       a List of player, the targeted players
     * @param s             a List of GenericSquare, the targeted squares
     *
     * @throws WrongValueException  if something goes wrong with the values
     * @throws WrongSquareException if the square choice is wrong
     * @throws WrongPlayerException if the player victim choice is wrong
     */
    public void doEffect(String e1, String e2, String e3, Player p, List<Player> players, List<GenericSquare> s) throws WrongSquareException, WrongPlayerException, WrongValueException {
        try {
            Effect eff1;
            Effect eff2 = null;
            Effect eff3 = null;
            boolean b = false;
            if (e1.equals("opt1") || (e2!=null && e1.equals("opt2") && e2.equals("base")))
                b = true;
            eff1 = getEffect(e1);
            if (e2 != null)
                eff2 = getEffect(e2);
            if (e3 != null)
                eff3 = getEffect(e3);
            applier.exe(p, players, s, eff1, eff2, eff3, b);
        }catch (WrongValueException v){
            throw new WrongValueException();
        }catch (WrongPlayerException pl){
            throw new WrongPlayerException(pl.getError());
        }catch (WrongSquareException sq){
            throw new WrongSquareException(sq.getError());
        }
    }

    /**
     * Pays the cost of the weapon in order to make the player able to use the specific effect
     *
     * @param eff       the name of the effect
     * @param p         the player that has to pay to use the specific effect
     */
    public void pay(String eff, RealPlayer p) {
        char[] tempCost = new char[1];
        switch (eff){
            case "alt":
                p.getPb().payAmmo(costAlt);
                break;
            case "opt1":
                tempCost[0] = costOpt[0];
                p.getPb().payAmmo(tempCost);
                break;
            default:
                if (!eff.equals("base")) {
                    tempCost[0] = costOpt[1];
                    p.getPb().payAmmo(tempCost);
                }
        }
    }

    /**
     * Getter for opt1Effect, that indicates whether the Weapon has the first optional effect or not
     *
     * @return      a boolean
     */
    public boolean isOpt1Effect() {
        return opt1Effect;
    }

    /**
     * Getter for opt2Effect, that indicates whether the Weapon has the second optional effect or not
     *
     * @return      a boolean
     */
    public boolean isOpt2Effect() {
        return opt2Effect;
    }

    /**
     * Getter for altEffect, that indicates whether the Weapon has the alternative effect or not
     *
     * @return      a boolean
     */
    public boolean isAltEffect() {
        return altEffect;
    }

    /**
     * Getter for the number of the targets that the base effect needs
     *
     * @return      an int
     */
    public int getnTargetsBase() {
        return nTargetsBase;
    }

    /**
     * Getter for the number of the targets that the first optional effect needs
     *
     * @return      an int
     */
    public int getnTargetsOpt1() {
        return nTargetsOpt1;
    }

    /**
     * Getter for the number of the targets that the second optional effect needs
     *
     * @return      an int
     */
    public int getnTargetsOpt2() {
        return nTargetsOpt2;
    }

    /**
     * Getter for the number of the targets that the alternative effect needs
     *
     * @return      an int
     */
    public int getnTargetsAlt() {
        return nTargetsAlt;
    }

    /**
     * Getter for the number of the squares that the base effect needs
     *
     * @return      an int
     */
    public int getnSquaresBase() {
        return nSquaresBase;
    }

    /**
     * Getter for the number of the squares that the first optional effect needs
     *
     * @return      an int
     */
    public int getnSquaresOpt1() {
        return nSquaresOpt1;
    }

    /**
     * Getter for the number of the squares that the second optional effect needs
     *
     * @return      an int
     */
    public int getnSquaresOpt2() {
        return nSquaresOpt2;
    }

    /**
     * Getter for the number of the squares that the alternative effect needs
     *
     * @return      an int
     */
    public int getnSquaresAlt() {
        return nSquaresAlt;
    }

    /**
     * Getter for notNecessarySquare, that indicates if the Weapon doesn't need squares for the effects
     *
     * @return      a boolean
     */
    public boolean isNotNecessarySquare() {
        return notNecessarySquare;
    }

    /**
     * Getter for particularWeapon
     *
     * @return      an int
     */
    public int getParticularWeapon() {
        return particularWeapon;
    }
}