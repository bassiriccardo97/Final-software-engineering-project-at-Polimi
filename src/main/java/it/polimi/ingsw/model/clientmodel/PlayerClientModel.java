package it.polimi.ingsw.model.clientmodel;

import java.io.Serializable;

public class PlayerClientModel implements Serializable {

    private char color;
    private Integer playerPosition;
    private PlayerBoardClientModel playerBoard;
    private boolean firstTurn;
    private int score;
    private boolean fFAction = false;

    //GETTER

    /**
     * Getter for the color of the player
     *
     * @return      a char
     */
    public char getColor() {
        return color;
    }

    /**
     * Getter for the position of the player, as the number of the square
     *
     * @return      an Integer
     */
    public Integer getPlayerPosition() {
        return playerPosition;
    }

    /**
     * Getter for the player board
     *
     * @return      the PlayerBoardClientModel
     */
    public PlayerBoardClientModel getPlayerBoard() {
        return playerBoard;
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public int getScore() {
        return score;
    }

    public boolean isfFAction() {
        return fFAction;
    }

    //SETTER

    /**
     * Setter for the score of the player
     *
     * @param score     the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Setter for the color of the player
     *
     * @param color     the color
     */
    public void setColor(char color) {
        this.color = color;
    }

    /**
     * Setter for the player position, as the number of the square
     *
     * @param playerPosition        the player position
     */
    public void setPlayerPosition(Integer playerPosition) {
        this.playerPosition = playerPosition;
    }

    /**
     * Setter for the player board
     *
     * @param playerBoard       the player board
     */
    public void setPlayerBoard(PlayerBoardClientModel playerBoard) {
        this.playerBoard = playerBoard;
    }

    /**
     * Setter for the first turn
     *
     * @param firstTurn     first turn flag
     */
    public void setFirstTurn(boolean firstTurn) {
        this.firstTurn = firstTurn;
    }

    public void setfFAction(boolean fFAction) {
        this.fFAction = fFAction;
    }
}
