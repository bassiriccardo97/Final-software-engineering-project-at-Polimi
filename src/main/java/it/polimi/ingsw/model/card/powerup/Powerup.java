package it.polimi.ingsw.model.card.powerup;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.connections.Server;
import it.polimi.ingsw.model.card.Effect;
import it.polimi.ingsw.model.card.EffectManager;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.player.PlayerBoard;

import javax.sql.rowset.serial.SerialBlob;
import java.io.Serializable;
import java.util.List;

public class Powerup extends EffectManager implements Serializable {

    private String name;
    private char color;
    private boolean pay1;
    private boolean alwaysUsable;
    private boolean square;
    private transient Effect effect;
    private String description;
    private boolean victim;
    private transient PowerupApplier applier;

    /**
     * Constructor of Powerup
     *
     * @param name      a String parameter, which refers to the name of the card
     * @param color     a char parameter, which refers to the color of the card
     * @param pay1              if the powerup is a targeting scope
     * @param alwaysUsable      if it is always usable
     * @param applier           the applier of the powerup
     * @param description       the description of the effect
     * @param effect            the effect of the powerup
     * @param square            if the effect needs a sqaure
     * @param victim            if the effect needs a victim
     */
    public Powerup(String name, char color, boolean pay1, boolean alwaysUsable, boolean victim, boolean square, Effect effect, String description, PowerupApplier applier) {
        this.name = name;
        this.color = color;
        this.pay1 = pay1;
        this.alwaysUsable = alwaysUsable;
        this.victim = victim;
        this.square = square;
        this.effect = effect;
        this.description = description;
        this.applier = applier;
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
     * Getter for color
     *
     * @return      a char
     */
    public char getColor() {
        return color;
    }

    /**
     * Setter for color
     *
     * @param c     a char
     */
    public void setColor(char c) {
        color = c;
    }

    //for testing
    public void description(){
        System.out.println("\t" + description);
    }

    /**
     * Getter for the Powerup's description
     *
     * @return      a String, the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for pay
     *
     * @return      a boolean
     */
    public boolean getPay(){
        return pay1;
    }

    /**
     * Getter for effect
     *
     * @param n     a String, the name of the effect
     * @return      an Effect
     */
    public Effect getEffect(String n){
        return this.effect;
    }

    /**
     * Does the effect of the powerup
     *
     * @param p             a player, the one that uses the effect
     * @param players       a List of player, players targeted
     * @param s             a List of GenericSquare, squares targeted
     *
     * @throws WrongValueException  if something goes wrong with the values given
     */
    public void doEffect(Player p, List<Player> players, List<GenericSquare> s) throws WrongValueException{
        try {
            applier.exe(p, players, s);
        }catch (WrongPlayerException | WrongSquareException | WrongValueException w){
            throw new WrongValueException();
        }
    }

    /**
     * Getter for alwaysUsable, that indicates whether the Powerup is always usable or not
     *
     * @return      a boolean
     */
    public boolean isAlwaysUsable() {
        return alwaysUsable;
    }

    /**
     * Getter for victim that, indicates whether the Powerup's effect needs a victim or not
     *
     * @return      a boolean
     */
    public boolean isVictim() {
        return victim;
    }

    /**
     * Getter for sqaure, that indicates whether the Powerup's effect needs a square or not
     *
     * @return      a boolean
     */
    public boolean isSquare() {
        return square;
    }
}
