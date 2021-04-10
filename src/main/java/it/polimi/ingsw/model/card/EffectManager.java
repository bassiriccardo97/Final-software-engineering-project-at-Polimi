package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;

import java.util.List;

public abstract class EffectManager {

    /**
     * Getter for the effects
     *
     * @param e     a String, the name of the effect
     * @return      an Effect
     */
    public abstract Effect getEffect(String e);

    /**
     * Checks if all the players are visible (shoot visibility) by one square
     *
     * @param s                         a GenericSquare
     * @param p                         an ArrayList of player
     * @throws WrongPlayerException     if any player is not visible
     */
    public static void checkPlayersVisibilityShoot(GenericSquare s, List<Player> p) throws WrongPlayerException {
        for (Player t : p)
            if (Board.isVisibleShoot(s, t.getPlayerPosition()) == -1)
                throw new WrongPlayerException(t);
    }

    /**
     * Checks if the second GenericSquare is visible (run visibility) by the first one and if the distance is between the given parameters
     *
     * @param s1                        a GenericSquare
     * @param s2                        a GenericSquare
     * @param n                         an int, the maximum distance allowed
     * @param within                    an int, the minimum distance allowed, -1 if does not care
     * @throws WrongSquareException     if the second square is not visible or exceed the distance edges
     */
    public static void checkDistanceVisibleRun(GenericSquare s1, GenericSquare s2, int n, int within) throws WrongSquareException {
        int m = Board.isVisibleRun(s1, s2);
        if (within >= 0 && m >= within && m <= n)
            return;
        if (within < 0 && m == n)
            return;
        throw new WrongSquareException(s2);
    }

    /**
     * Checks if the second GenericSquare is visible (shoot visibility) by the first one and if the distance is between the given parameters
     *
     * @param s1                        a GenericSquare
     * @param s2                        a GenericSquare
     * @param n                         an int, the maximum distance allowed
     * @param within                    an int, the minimum distance allowed, -1 if does not care
     * @throws WrongSquareException     if the second square is not visible or exceed the distance edges
     */
    public static void checkDistanceVisibleShoot(GenericSquare s1, GenericSquare s2, int n, int within) throws WrongSquareException {
        int m = Board.isVisibleShoot(s1, s2);
        if (within >= 0 && m >= within && m <= n)
            return;
        if (within < 0 && m == n)
            return;
        throw new WrongSquareException(s2);
    }

    /**
     * Checks if the second GenericSquare is in a fixed cardinal direction considering the first one and if it is visible (run visibility)
     *
     * @param s1                        a GenericSquare
     * @param s2                        a GenericSquare
     * @throws WrongSquareException     if the second square is not visible by the first or if it is not in a fixed cardinal direction
     */
    public static void checkFixedDirectionReachable(GenericSquare s1, GenericSquare s2) throws WrongSquareException {
        if (!Board.fixedDirectionReachable(s1, s2))
            throw new WrongSquareException(s2);
    }

}
