package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;

import java.io.Serializable;
import java.util.List;

public interface Effect {

    void applyOn(Player p1, List<Player> p2, List<GenericSquare> s) throws WrongValueException, WrongPlayerException, WrongSquareException;

}
