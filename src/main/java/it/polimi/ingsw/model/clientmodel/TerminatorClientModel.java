package it.polimi.ingsw.model.clientmodel;

import java.io.Serializable;

public class TerminatorClientModel extends PlayerClientModel implements Serializable {

    /**
     * Default constructor that calls the super-class' default constructor
     */
    public TerminatorClientModel(){
        super();
    }

    /**
     * Getter for the player board
     *
     * @return      the PlayerBoardClientModel
     */
    @Override
    public PlayerBoardClientModel getPlayerBoard() {
        return super.getPlayerBoard();
    }
}
