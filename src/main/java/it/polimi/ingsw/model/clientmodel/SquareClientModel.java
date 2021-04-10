package it.polimi.ingsw.model.clientmodel;

import java.io.Serializable;

public class SquareClientModel implements Serializable {

    private int num;
    private String ammoTile;
    private boolean existing;

    /**
     * Constructor
     *
     * @param num           the square number
     * @param ammoTile      the ammo tile in the square
     * @param existing      the existing square flag
     */
    public SquareClientModel(int num, String ammoTile, boolean existing) {
        this.num = num;
        this.ammoTile = ammoTile;
        this.existing = existing;
    }

    /**
     * Getter for the num of a square
     *
     * @return      the num
     */
    public int getNum() {
        return num;
    }

    /**
     * Setter for the num of a square
     *
     * @param num   the nume
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Getter for the ammo tile in the square
     *
     * @return      a String, the ammo tile type
     */
    public String getAmmoTile() {
        return ammoTile;
    }

    /**
     * Setter for the ammo tile in the square
     *
     * @param ammoTile      the ammo tile type
     */
    public void setAmmoTile(String ammoTile) {
        this.ammoTile = ammoTile;
    }

    /**
     * Getter for the existing square flag
     *
     * @return      a boolean, that indicates whether the square exists or not in the map
     */
    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }
}
