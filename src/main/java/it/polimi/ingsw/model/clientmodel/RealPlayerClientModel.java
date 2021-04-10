package it.polimi.ingsw.model.clientmodel;

import it.polimi.ingsw.model.game.squares.GenericSquare;

import java.io.Serializable;

public class RealPlayerClientModel extends PlayerClientModel implements Serializable {

    private String playerName;
    private PlayerHandClientModel playerHand;
    private boolean yourTurn;
    private boolean disconnected;
    private boolean terminator;
    private boolean first = false;


    /**
     * Default constructor that calls super-class' default constructor
     */
    public RealPlayerClientModel(){
        super();
    }

    //GETTER

    /**
     * Getter for the player's name
     *
     * @return      a String
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Getter for the disconnected flag
     *
     * @return      the disconnected flag, that indicates whether the player is disconnected or not
     */
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * Getter for the player's hand
     *
     * @return      the PlayerHandClientModel
     */
    public PlayerHandClientModel getPlayerHand() {
        return playerHand;
    }

    /**
     * Getter for the turn flag, that indicates whether it is the turn of the player or not
     *
     * @return      a boolean
     */
    public boolean getYourTurn() {
        return yourTurn;
    }

    /**
     * Getter for terminator flag, that indicates whether the player has the terminator card in the hand or not
     *
     * @return      a boolean
     */
    public boolean getTerminator(){
        return terminator;
    }

    /**
     * Getter for first player flag, that indicates whether the player is the first or not
     *
     * @return      a boolean
     */
    public boolean isFirst() {
        return first;
    }

    //SETTER

    /**
     * Setter for the player's name
     *
     * @param playerName        the name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Setter for the player's hand
     *
     * @param playerHand        the player hand
     */
    public void setPlayerHand(PlayerHandClientModel playerHand) {
        this.playerHand = playerHand;
    }

    /**
     * Setter for the turn flag
     *
     * @param yourTurn      the turn flag
     */
    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    /**
     * Setter for the disconnected flag
     *
     * @param disconnected      true if player disconnected, false otherwise
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    /**
     * Setter for the terminator flag
     *
     * @param t     the terminator flag
     */
    public void setTerminator(boolean t){
        terminator = t;
    }

    /**
     * Setter for the first player flag
     *
     * @param first     the first player flag
     */
    public void setFirst(boolean first) {
        this.first = first;
    }
}
