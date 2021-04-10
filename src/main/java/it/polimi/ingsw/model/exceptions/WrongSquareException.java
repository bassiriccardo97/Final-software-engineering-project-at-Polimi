package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.game.squares.GenericSquare;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WrongSquareException extends Exception {

    private GenericSquare error;

    public WrongSquareException(GenericSquare s){
        setError(s);
    }

    public GenericSquare getError() {
        return error;
    }

    private void setError(GenericSquare error) {
        this.error = error;
    }
}
