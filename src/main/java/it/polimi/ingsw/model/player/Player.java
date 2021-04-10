package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.exceptions.WrongSquareException;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String name;
    private GenericSquare playerPosition = null;
    private char color;
    private PlayerBoard pb = new PlayerBoard();
    private boolean justDamaged = false;
    private int score;

    /**
     * Constructor for player
     *
     * @param c     a char, the color of the player
     * @param name  the name of the player
     */
    public Player(char c, String name){
        this.name = name;
        color = c;
        score = 0;
    }

    /**
     * Setter for playerPosition
     *
     * @param position      a GenericSquare, the position of the player
     */
    public void setPlayerPosition(GenericSquare position) {
        //GenericSquare temp = p.getPlayerPosition();
        if (this.getPlayerPosition() != null)
            this.getPlayerPosition().removePlayer(this);
        //p.setPlayerPosition2(position);
        this.playerPosition = position;
        position.getPlayers().add(this);
        //if(temp!=null)
            //temp.removePlayer(p);
    }

    /**
     * Getter for playerPosition
     *
     * @return      a GenericSquare, the position of the player
     */
    public GenericSquare getPlayerPosition() {
        return playerPosition;
    }

    /**
     * Getter for color
     *
     * @return      a char, the color of the player
     */
    public char getColor() {
        return color;
    }

        /**
     * Getter for score
     *
     * @return      an int which is the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the player score
     * The int argument represents the partial score which has to be added to the previous player score
     *
     * @param partialScore      an int, which is the partial score that the previous one has to be increased with
     */
    public void updateScore(int partialScore) {
        if (partialScore > 0)
            this.score += partialScore;
    }

    /**
     * Getter for pb
     *
     * @return      a PlayerBoard
     */
    public PlayerBoard getPb() {
        return pb;
    }

    /**
     * Setter for justDamaged
     *
     * @param b     a boolean
     */
    public void setJustDamaged(boolean b){
        justDamaged = b;
    }

    /**
     * Updates the player position with the argument GenericSquare, but only if the new position is reachable (run visibility: 0 to 3 squares)
     *
     * @param s     an GenericSquare object, which is the position that has to be set
     * @throws WrongSquareException     if the square is not reachable with a run action
     */
    public void run (GenericSquare s) throws WrongSquareException {
        if (Board.isVisibleRun(this.playerPosition, s) != -1) {
            //this.playerPosition.removePlayer(this);
            //setPlayerPosition(s, this);
            this.setPlayerPosition(s);
        }else
            throw new WrongSquareException(s);
    }

    /**
     * Updates the player position with the argument GenericSquare, but only if the new position is reachable (run visibility: 0 to 4 squares)
     *
     * @param s     an GenericSquare object, which is the position that has to be set
     * @throws WrongSquareException     if the square is not reachable with a run action
     */
    public void run4(GenericSquare s) throws WrongSquareException {
        ArrayList<GenericSquare> adj = new ArrayList<>();
        for (GenericSquare t : Board.getSquares())
            if (t.isReachable(s))
                adj.add(t);
        for (GenericSquare t : Board.getSpawnpointSquares())
            if (t.isReachable(s))
                adj.add(t);
        for (GenericSquare t : adj)
            if (Board.isVisibleRun(this.getPlayerPosition(), t) != -1) {
                getPlayerPosition().removePlayer(this);
                //setPlayerPosition(s, this);
                this.setPlayerPosition(s);
                return;
            }
        throw new WrongSquareException(s);
    }

    /**
     * Getter for justDamaged
     *
     * @return      a boolean
     */
    public boolean getJustDamaged() { return justDamaged; }

    /**
     * Getter for name
     *
     * @return      a String
     */
    public String getName(){
        return name;
    }

}