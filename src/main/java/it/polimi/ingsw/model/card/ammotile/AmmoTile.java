package it.polimi.ingsw.model.card.ammotile;

import java.io.Serializable;

public class AmmoTile implements Serializable {

    private int[] cubes = new int[3];
    private boolean powerup;


    //for testing
    public AmmoTile(){

    }

    /**
     * Constructor of AmmoTile
     *
     * @param blue      an int, the number of blue ammo
     * @param red       an int, the number of red ammo
     * @param yellow    an int, the number of yellow ammo
     * @param p         a boolean, whether to draw a Powerup or not
     */
    public AmmoTile(int blue, int red, int yellow, boolean p){
        cubes[0] = blue;
        cubes[1] = red;
        cubes[2] = yellow;
        powerup = p;
    }

    /**
     * Getter of cubes
     *
     * @return      an array of int
     */
    public int[] getCubes() {
        return cubes;
    }

    /**Getter of powerup
     *
     * @return      a boolean
     */
    public boolean getPowerup() {
        return powerup;
    }

    /**
     * Setter of cubes
     *
     * @param cubes     an array of int
     */
    public void setCubes(int[] cubes) {
        this.cubes[0] = cubes[0];
        this.cubes[1] = cubes[1];
        this.cubes[2] = cubes[2];
    }

    /**
     * Setter of powerup
     *
     * @param p     a boolean
     */
    public void setPowerup(boolean p) {
        powerup = p;
    }

}