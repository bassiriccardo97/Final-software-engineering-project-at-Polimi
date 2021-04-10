package it.polimi.ingsw.model.card.powerup;

import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;

import java.io.Serializable;
import java.util.List;

public interface PowerupApplier {

    void exe(Player p, List<Player> players, List<GenericSquare> s) throws WrongValueException, WrongPlayerException, WrongSquareException;
}
