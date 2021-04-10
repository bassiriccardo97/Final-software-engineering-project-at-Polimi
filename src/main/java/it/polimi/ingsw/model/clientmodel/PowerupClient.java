package it.polimi.ingsw.model.clientmodel;

import java.io.Serializable;

public class PowerupClient implements Serializable {

    private String name;
    private char color;
    private String description;
    private boolean pay;


    //GETTER

    /**
     * Getter for the powerup's name
     *
     * @return      a String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the powerup's color
     *
     * @return      a char
     */
    public char getColor() {
        return color;
    }

    /**
     * Getter for the powerup's description
     *
     * @return      a String
     */
    public String getDescription() {
        return description;
    }

    public boolean isPay() {
        return pay;
    }

    //SETTER

    /**
     * Setter for the powerup's name
     *
     * @param name      the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the powerup's color
     *
     * @param color     the color
     */
    public void setColor(char color) {
        this.color = color;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    /**
     * Setter for the powerup's description
     *
     * @param description       the description
     */
    public void setDescription(String description) {
        this.description = description;
    }


}
