package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.card.powerup.PowerupDeck;
import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.card.weapon.WeaponDeck;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;

import java.io.Serializable;

public class PlayerHand implements Serializable {

    private WeaponDeck weapon;
    private PowerupDeck powerup;

    /**
     * Constructor of PlayerHand
     */
    PlayerHand() {
        this.powerup = new PowerupDeck();
        this.weapon = new WeaponDeck();
    }

    /**
     * Getter for WeaponDecl
     *
     * @return      a WeaponDeck, all the weapons the player has in his hand
     */
    public WeaponDeck getWeaponDeck() {
        return weapon;
    }

    /**
     * Getter for PowerupDeck
     *
     * @return      a PowerupDeck, all the powerups the player has in his hand
     */
    public PowerupDeck getPowerupDeck() {
        return powerup;
    }

    /**
     * Adds a Weapon card to the PlayerHand of the player from the WeaponDeck on the board
     *
     * @param wd    a WeaponDeck, from which the player draws his weapon
     * @param w     a String, the specific WeaponGUI name
     */
    public void drawWeapon(WeaponDeck wd, String w) {
        this.weapon.addWeapon(wd.getWeapon(w));
        weapon.getWeapon(w).setLoaded(true);
        wd.setWeapon(wd.getWeapons().indexOf(wd.getWeapon(w)), null);
    }

    /**
     * Adds a Weapon card to the PlayerHand of the player from a PowerupDeck
     *
     * @param pud   a PowerupDeck, from which the player draws his powerup
     */
    public void drawPowerup(PowerupDeck pud) {
        powerup.addPowerup(pud.getFirstPowerup());
        if (Board.getPowerups().getSize() == 0)
            Board.discardedToPowerup();
    }

    /**
     * Adds the first pulls the first card argument and adds it to the deck on the board, next to the relative Spawnpoint;
     * in addition, it adds the second card argument to the PlayerHand
     *
     * @param s         a Spawnpoint, which corresponds the player position
     * @param pickup    a String, which is the first card name (the one which is going to be added to the spawnpoint weapon deck)
     * @param discard   a Weapon, which is the second card (the one which is going to be added to the PlayerHand)
     */
    public void change(SpawnpointSquare s, String pickup, Weapon discard) {
        Weapon temp = s.getWeaponSpawnpoint().getWeapon(pickup);
        int i = s.getWeaponSpawnpoint().getWeapons().indexOf(temp);
        s.getWeaponSpawnpoint().getWeapons().set(i, discard);
        this.weapon.removeWeapon(discard);
        temp.setLoaded(true);
        this.weapon.addWeapon(temp);
    }

    /**
     * Discards a Powerup object from the PlayerHand and adds it at the bottom of the PowerupDeck on the board
     *
     * @param p         a Powerup, the one which is going to be added at the bottom of the PowerupDeck on the board
     */
    public void discard(Powerup p){
        Board.getDiscardedPowerups().addPowerup(p);
        powerup.removePowerup(p);
    }

    /**
     * Counts the number of powerups for each color
     *
     * @return      an array of three int, that contains the number of powerups blue, red and yellow
     */
    public int[] numPowerupForColor(){
        int[] numPC = new int[3];
        for (Powerup pu : this.getPowerupDeck().getPowerups()) {
            if (pu.getColor() == 'b')
                numPC[0]++;
            else if (pu.getColor() == 'r')
                numPC[1]++;
            else
                numPC[2]++;
        }
        return numPC;
    }

    /**
     * Counts the number of powerups of one color
     *
     * @param color     a char, the color
     * @return          an int, the amount of powerup of the specific color
     */
    public int numPowerupColor(char color){
        int count = 0;
        for(Powerup pu : this.getPowerupDeck().getPowerups()){
            if(pu.getColor() == color)
                count++;
        }
        return count;
    }

    /**
     * Checks if the player has a specific powerup of any color
     *
     * @param name          a String, the name of the powerup
     * @return              a boolean
     */
    public boolean hasPowerup(String name) {
        return (this.getPowerupDeck().getPowerups().contains(this.getPowerupDeck().getPowerup(name, 'b')) || this.getPowerupDeck().getPowerups().contains(this.getPowerupDeck().getPowerup(name, 'r')) || this.getPowerupDeck().getPowerups().contains(this.getPowerupDeck().getPowerup(name, 'y')));
    }

    /**
     * Checks if the player has at least a powerup which can be used anytime (a teleporter or a newton)
     *
     * @return          a boolean
     */
    public boolean hasAlwaysUsablePowerup() {
        for (Powerup pu : this.getPowerupDeck().getPowerups()) {
            if (pu.isAlwaysUsable())
                return true;
        }
        return false;
    }

    /**
     * Checks if the player has a specific powerup of a specific color
     *
     * @param name          a String, the name of the powerup
     * @param c             a char, the color of the powerup
     * @return              a boolean
     */
    public boolean hasPowerupColor(String name, String c) {
        if (!c.equals("blue") && !c.equals("yellow") && !c.equals("red"))
            return false;
        return (this.getPowerupDeck().getPowerups().contains(this.getPowerupDeck().getPowerup(name, c.charAt(0))));
    }

    /**
     * Checks if the player has a specific weapon
     *
     * @param weaponName        a String, the name of the weapon
     * @return                  a boolean
     */
    public boolean hasWeapon(String weaponName) {
        return this.getWeaponDeck().getWeapons().contains(this.getWeaponDeck().getWeapon(weaponName));
    }

    /**
     * Checks if the player has at least an unloaded weapon
     *
     * @return              a boolean
     */
    public boolean unloadedWeapons() {                   //returns true if the player has almost an unloaded weapon
        if (hasNoWeapons()) {
            for (Weapon w : this.getWeaponDeck().getWeapons())
                if (!w.getLoaded())
                    return true;
        }
        return false;
    }

    /**
     * Checks if the player has at least a loaded weapon
     *
     * @return              a boolean
     */
    public boolean loadedWeapons() {
        if (hasNoWeapons()) {
            for (Weapon w : this.getWeaponDeck().getWeapons())
                if (w.getLoaded())
                    return true;
        }
        return false;
    }

    /**
     * Checks if the player has no weapons (returns "false" in case of no weapon in the playerhand)
     *
     * @return          a boolean
     */
    public boolean hasNoWeapons() {
        if (this.getWeaponDeck().getSize() == 0)
            return false;
        return true;
    }

    /**
     * Checks if the player has a single powerup of a specific color
     *
     * @param color         a char, which represents the color
     * @return              a Powerup, which is the single powerup that the player has in his playerhand; otherwise the methods returns null
     */
    public Powerup singlePowerupColorDetector(char color){
        for(Powerup pu : this.getPowerupDeck().getPowerups()){
            if(pu.getColor()==color)
                return pu;
        }
        return null;
    }

    /**
     * Checks if the player has a single powerup of any color
     *
     * @param powerupName       a String, which is the name of the powerup
     * @return                  a Powerup, which is the single powerup that the player has in his playerhand; otherwise the methods returns null
     */
    public Powerup singlePowerupDetector(String powerupName){
        int count = 0;
        char color = '\u0000';
        for(Powerup pu : this.getPowerupDeck().getPowerups()){
            if(pu.getName().equals(powerupName)){
                color = pu.getColor();
                count++;
            }
        }
        if(count == 1)
            return this.getPowerupDeck().getPowerup(powerupName, color);
        else
            return null;
    }

    /**
     * Checks if the player has a specific single always-usable powerup
     *
     * @param powerupName       a String, which is the name of the specific powerup
     * @return                  a boolean
     */
    public boolean usablePowerupsDetector(String powerupName) {
        if (this.getPowerupDeck().getPowerups().contains(this.getPowerupDeck().getPowerup(powerupName, 'b'))) {
            if (this.getPowerupDeck().getPowerup(powerupName, 'b').isAlwaysUsable())
                return true;
        }
        if (this.getPowerupDeck().getPowerups().contains(this.getPowerupDeck().getPowerup(powerupName, 'r'))) {
            if (this.getPowerupDeck().getPowerup(powerupName, 'r').isAlwaysUsable())
                return true;
        }
        if (this.getPowerupDeck().getPowerups().contains(this.getPowerupDeck().getPowerup(powerupName, 'y'))) {
            if (this.getPowerupDeck().getPowerup(powerupName, 'y').isAlwaysUsable())
                return true;
        }
        return false;
    }


}