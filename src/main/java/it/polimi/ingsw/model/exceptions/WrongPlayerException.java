package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.player.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WrongPlayerException extends Exception {

    private Player error;

    public WrongPlayerException(Player p){
        setError(p);
    }

    public Player getError() {
        return error;
    }

    private void setError(Player error) {
        this.error = error;
    }
}
