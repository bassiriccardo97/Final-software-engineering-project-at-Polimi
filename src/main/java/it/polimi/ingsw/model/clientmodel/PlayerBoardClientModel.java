package it.polimi.ingsw.model.clientmodel;

import java.io.Serializable;

public class PlayerBoardClientModel implements Serializable {

    private char[] damages = new char[12];
    private int[] markedDamages = new int[5];
    private int[] ammoBox = new int[3];
    private int[] pointsToAssignSide1 = {8, 6, 4, 2, 1, 1};
    private int[] pointsToAssignSide2 = {2, 1, 1, 1};
    private int nDeath;
    private int side = 1;

    //GETTER

    /**
     * Getter for the damages of the player
     *
     * @return      an array of char
     */
    public char[] getDamages() {
        return damages;
    }

    /**
     * Getter for the marks of the player
     *
     * @return      an array of int
     */
    public int[] getMarkedDamages() {
        return markedDamages;
    }

    /**
     * Getter for the ammo box of the player
     *
     * @return      an array of int
     */
    public int[] getAmmoBox() {
        return ammoBox;
    }

    /**
     * Getter for the the side of the player board
     *
     * @return      an int
     */
    public int getSide(){
        return side;
    }

    /**
     * Getter for the number of deaths of the player
     *
     * @return      an int
     */
    public int getnDeath() {
        return nDeath;
    }

    /**
     * Getter for the points to assign according to the side 1 of the player board
     *
     * @return      an array of int
     */
    public int[] getPointsToAssignSide1() {
        return pointsToAssignSide1;
    }

    public int[] getPointsToAssignSide2() {
        return pointsToAssignSide2;
    }

    //SETTER

    /**
     * Setter for the side of the player board
     *
     * @param n     the side's number
     */
    public void setSide(int n){
        side = n;
    }

    /**
     * Setter for the damages of the player
     *
     * @param damages       the damages
     */
    public void setDamages(char[] damages) {
        this.damages = damages;
    }

    /**
     * Setter for the marks of the player
     *
     * @param markedDamages     the marks
     */
    public void setMarkedDamages(int[] markedDamages) {
        this.markedDamages = markedDamages;
    }

    /**
     * Setter for the ammo box of the player
     *
     * @param ammoBox       numbers of ammo for each color (blue, red, yellow)
     */
    public void setAmmoBox(int[] ammoBox) {
        this.ammoBox = ammoBox;
    }

    /**
     * Setter for the number of death of the player
     *
     * @param nDeath        the number of death
     */
    public void setnDeath(int nDeath) {
        this.nDeath = nDeath;
    }

    /**
     * Setter for the points to assign according to side 1 of the player board
     *
     * @param pointsToAssignSide1       the available points to assign
     */
    public void setPointsToAssignSide1(int[] pointsToAssignSide1) {
        this.pointsToAssignSide1 = pointsToAssignSide1;
    }

    /**
     * Setter for the points to assign according to side 2 of the player board
     *
     * @param pointsToAssignSide2       the points to assign
     */
    public void setPointsToAssignSide2(int[] pointsToAssignSide2) {
        this.pointsToAssignSide2 = pointsToAssignSide2;
    }

}
