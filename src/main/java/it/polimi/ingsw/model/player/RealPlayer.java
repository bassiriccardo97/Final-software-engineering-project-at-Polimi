package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.powerup.PowerupDeck;
import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.game.squares.Square;

import java.io.Serializable;

public class RealPlayer extends Player implements Serializable {

    //private String name;
    private boolean ff2actions;
    private boolean terminator;
    private boolean turn;
    private PlayerHand ph ;

    /**
     * Constructor of RealPlayer
     *
     * @param c     a char, which is the color of the player
     * @param name  the name of the player
     */
    public RealPlayer(char c, String name) {
        super(c, name);
        //this.name = name;
        ph = new PlayerHand();
        terminator = false;
        ff2actions = false;
        turn = false;
    }

    /**
     * Getter for ff2actions
     *
     * @return      a boolean
     */
    public boolean getFf2actions(){ return ff2actions; }

    /**
     * Setter for ff2actions, which indicates if the player has started his frenetic actions before the starting one
     *
     * @param ff2       a boolean
     */
    public void setFf2actions(boolean ff2) { this.ff2actions = ff2; }

    /**
     * Setter for terminator, which indicates if the player has the Terminator card
     *
     * @param b     a boolean
     */
    public void setTerminator(boolean b){ terminator = b; }

    /**
     * Getter for terminator
     *
     * @return      a boolean
     */
    public boolean getTerminator(){
        return terminator;
    }

    /**
     * Getter for ph
     *
     * @return      a PlayerHand
     */
    public PlayerHand getPh() {
        return ph;
    }

    /**
     * Adds all the cubes of the AmmoTile that the player has grabbed to his AmmoBox;
     * in addition, if the AmmoTile depicts a Powerup, the player can draw one
     *
     * @param s     a Square, which is the one which represents the PlayerPosition
     * @param pud   a PowerupDeck, from which the player draw his Powerup
     * @throws WrongSquareException     if the square has nothing to grab
     */
    public void grab(GenericSquare s, PowerupDeck pud) throws WrongSquareException {
        Square t = (Square)s;
        if (t.getAmmo() == null)
            throw new WrongSquareException(t);
        this.getPb().grabAmmo(t.getAmmo().getCubes());
        Board.getDiscardedAmmo().addAmmoTile(t.getAmmo());
        if (t.getAmmo().getPowerup() && this.getPh().getPowerupDeck().getSize()<3)
            getPh().drawPowerup(pud);
        if (Board.getAmmoDeck().getSize() == 0)
            Board.discardedToAmmo();
        t.setAmmo(null);
    }

    /**
     * It calls the specific methods which allow the player to grab a weapon, according to the amount of the weapon in the playerhand
     *
     * @param pickup        a String, the name of the weapon the player wants to grab
     * @param discard       a String, the name of the weapon the player eventually wants to discard
     */
    public void grab(String pickup, String discard){
        SpawnpointSquare temp = (SpawnpointSquare) this.getPlayerPosition();
        if (this.getPh().getWeaponDeck().getSize() < 3)
            this.getPh().drawWeapon(temp.getWeaponSpawnpoint(), pickup);
        else
            this.getPh().change(temp, pickup, this.getPh().getWeaponDeck().getWeapon(discard));
    }

    /**
     * This method sets a Weapon as "loaded"
     *
     * @param w     a Weapon, which is the one that has to be set as loaded
     */
    public void reload(Weapon w){
        w.setLoaded(true);
    }

    /**
     * Makes the player draw two powerups at the beginning of the game
     */
    public void initPlayer(){
        this.getPh().drawPowerup(Board.getPowerups());
        this.getPh().drawPowerup(Board.getPowerups());
    }

    /**
     * Makes the player discard one powerup and sets the initial position
     *
     * @param powerupName       a String, the name of the powerup which has to be discarded
     * @param powerupColor      a char, the color of the powerup which has to be discarded
     */
    public void initPosition(String powerupName, char powerupColor){
        setPlayerPosition(Board.getSpawnpoint(powerupColor));
        this.getPh().discard(ph.getPowerupDeck().getPowerup(powerupName, powerupColor));
    }

    /**
     * It allows player to draw a powerup and sets the alive attribute
     */
    public void respawn(){
        ph.drawPowerup(Board.getPowerups());
        this.getPb().setAlive();
    }

    /**
     * Setter for turn
     *
     * @param t     a boolean
     */
    public void setTurn(boolean t){
        turn = t;
    }

    /**
     * Getter for turn
     *
     * @return      a boolean
     */
    public boolean getTurn(){ return turn; }

    /**
     * Checks if the player has enough ammo to pay all the cost
     *
     * @param cost          an array of char, which represents the cost
     * @return              a boolean
     */
    public boolean checkAmmoResources(char[] cost) {
        int[] costInt = this.getPb().convert(cost);
        for (int i = 0; i < costInt.length; i++) {
            if (costInt[i] > this.getPb().getAmmoBox()[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the player has enough powerups to pay all the cost
     *
     * @param cost          an array of char, which represents the cost
     * @return              a boolean
     */
    public boolean checkPowerupResources(char[] cost) {
        int[] costInt = this.getPb().convert(cost);
        int[] numPowerupCol = this.getPh().numPowerupForColor();
        for (int i = 0; i < costInt.length; i++) {
            if (numPowerupCol[i] < costInt[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the player has enough ammo and powerups to pay all the cost; it returns "true" only if the player can pay with both ammo and powerups
     *
     * @param cost          an array of char, which represents the cost
     * @return              a boolean
     */
    public boolean checkAmmoPowerupResources(char[] cost) {
        if (singleCost(cost))
            return false;
        if (this.getPh().getPowerupDeck().getSize() == 0)
            return false;

        int[] convCost = this.getPb().convert(cost);
        int[] pupNum = this.getPh().numPowerupForColor();
        int[] ammo = this.getPb().getAmmoBox();

        for (int i = 0; i < convCost.length; i++)
            if (convCost[i] == 0)
                pupNum[i] = 0;

        for (int i = 0; i < convCost.length; i++)
            if (ammo[i] + pupNum[i] < convCost[i])
                return false;

        for (int i = 0; i < convCost.length; i++)
            if (pupNum[i] > 0 && convCost[i] > 0)
                return true;

        return false;
    }

    /**
     * Checks if the player has enough ammo and powerups to pay at least the cost of an unloaded weapon he has, in order to reload it; it returns "true" if the player can pay all the entire cost (even if a part of it can be covered totally with ammo or powerups)
     *
     * @return              a boolean
     */
    public boolean checkAllResources(){
        int[] numPowerup = this.getPh().numPowerupForColor();
        for(Weapon w : this.getPh().getWeaponDeck().getWeapons()){
            int[] costConv = this.getPb().convert(w.getCost());
            if(!w.getLoaded() && (costConv[0] <= (this.getPb().getAmmoBox()[0] + numPowerup[0]) && costConv[1] <= (this.getPb().getAmmoBox()[1] + numPowerup[1]) && costConv[2] <= (this.getPb().getAmmoBox()[2] + numPowerup[2])))
                return true;
        }
        return false;
    }

    /**
     * Checks if the amount of the cost is unit
     *
     * @param cost          an array of char, which represents the cost
     * @return              a boolean
     */
    private boolean singleCost(char[] cost){
        int count = 0;
        for (char c : cost)
            if (c != '\u0000')
                count++;
        return (count == 1);
    }
}
