package it.polimi.ingsw.model.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EndGameException extends Exception {

    private static final Logger LOGGER = Logger.getLogger( EndGameException.class.getName() );

    public void end(){
        LOGGER.log(Level.ALL, "game over");
    }
}
