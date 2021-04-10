package it.polimi.ingsw.model.card.powerup;

import it.polimi.ingsw.model.card.Deck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PowerupDeck implements Deck, Serializable {

    private static final Logger LOGGER = Logger.getLogger( PowerupDeck.class.getName() );
    private ArrayList<Powerup> powerups = new ArrayList<>();

    /**
     * Adds a Powerup card to the relative Deck
     *
     * @param p     a Powerup, which is the one which is going to be added to the PowerupDeck
     */
    public void addPowerup(Powerup p) {
        this.powerups.add(p);
    }

    /**
     * Removes a Powerup from the Deck
     * The int argument refers to the index of the chosen Powerup in the Deck
     *
     * @param index     an int, which is the index of the chosen Powerup
     */
    public void removePowerup(int index) {
        if (!this.powerups.isEmpty()) {
            this.powerups.remove(index);
        }
    }

    /**
     * Returns all the cards in the PowerupDeck
     *
     * @return      an ArrayList of Powerup, the one which contains all the powerups
     */
    public List<Powerup> getPowerups(){ return this.powerups; }

    /**
     * Removes a Powerup from the Deck
     * The Powerup parameter refers to the chosen Powerup in the Deck
     *
     * @param p     a Poweup, which is the one which has been chosen
     */
    public void removePowerup(Powerup p) {
        this.powerups.remove(p);
    }

    /**
     * Returns the correct Powerup in the Deck
     * The int argument is the index of the Powerup in the PowerupDeck
     *
     * @param index     an int, which is the index of the Powerup in the PowerupDeck
     * @return          a Powerup, which is the card chosen
     */
    public Powerup getPowerup(int index) {
        if (index < powerups.size() && index >= 0)
            return this.powerups.get(index);
        return null;
    }

    /**
     * Returns the correct Powerup in the Deck
     * The int argument is the index of the Powerup in the PowerupDeck
     *
     * @param powerupName   a String, which is the name of the chosen Powerup
     * @param color         a char
     * @return              a Powerup, which is the chosen card
     */
    public Powerup getPowerup(String powerupName, char color) {
        for (Powerup p : powerups){
            if(p.getName().equals(powerupName) && p.getColor()==color)
                return p;
        }
        LOGGER.log(Level.ALL, "There is not this card in this deck");
        return null;
    }

    /**
     * Returns the first Powerup in a PowerupDeck
     *
     * @return      a Powerup, which is the firs card of the PowerupDeck
     */
    public Powerup getFirstPowerup() {
        if (!this.powerups.isEmpty()) {
            Powerup temp = this.powerups.get(0);
            this.powerups.remove(0);
            return temp;
        }
        return null;
    }

    /**
     * Returns the size of the PowerupDeck
     *
     * @return      an int, which refers to the PowerupDeck size, so the number of the card
     */
    public int getSize() {
        return this.powerups.size();
    }

    /**
     * Shuffles al the cards in a PowerupDeck
     */
    public void shuffle() {
        Collections.shuffle(this.powerups);
    }

    //for testing
    public void show() {
        for (int i = 0; i < powerups.size(); i++) {
            System.out.println(powerups.get(i).getName() + "\n");
            powerups.get(i).description();
            System.out.println("\n\n");
        }
    }

}
