package it.polimi.ingsw.model.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WrongValueException extends Exception {

    private static final Logger LOGGER = Logger.getLogger( WrongSquareException.class.getName() );

    public void wrong(){ LOGGER.log(Level.ALL, "Wrong Value"); }
}
