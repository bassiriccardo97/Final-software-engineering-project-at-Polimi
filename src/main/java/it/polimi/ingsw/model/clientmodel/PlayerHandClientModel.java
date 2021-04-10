package it.polimi.ingsw.model.clientmodel;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerHandClientModel implements Serializable {

    private ArrayList<PowerupClient> powerups;
    private ArrayList<WeaponClient> weapons;

    //GETTER

    /**
     * Getter for the powerups in the hand
     *
     * @return      an ArrayList of PowerupClient
     */
    public ArrayList<PowerupClient> getPowerups() {
        return powerups;
    }

    /**
     * Getter for the weapons in the hand
     *
     * @return      an ArrayList of WeaponClient
     */
    public ArrayList<WeaponClient> getWeapons() {
        return weapons;
    }

    //SETTER

    /**
     * Setter for the powerups in the hand
     *
     * @param powerups      the list of powerups
     */
    public void setPowerups(ArrayList<PowerupClient> powerups) {
        this.powerups = powerups;
    }

    /**
     * Setter for the weapons in the hand
     *
     * @param weapons       the list of weapons in the hand
     */
    public void setWeapons(ArrayList<WeaponClient> weapons) {
        this.weapons = weapons;
    }

}
