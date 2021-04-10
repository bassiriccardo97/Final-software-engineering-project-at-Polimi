package it.polimi.ingsw.model.card.weapon;

import it.polimi.ingsw.model.card.Effect;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;

import java.util.List;

public interface WeaponApplier {

    void exe(Player p, List<Player> players, List<GenericSquare> s, Effect e1, Effect e2, Effect e3, boolean b) throws WrongValueException, WrongPlayerException, WrongSquareException;
}
