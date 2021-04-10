package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;

import java.io.Serializable;
import java.util.ArrayList;

public class Terminator extends Player implements Serializable {

    private char ownerColor;
    private char initColor;

    /**
     * Constructor of Terminator
     *
     * @param c     a char for the Terminator color
     * @param s     a SpawnpointSquare for the Terminator position
     */
    public Terminator(char c, GenericSquare s) {
        super(c, "Terminator");
        setPlayerPosition(s);
    }

    /**
     * Setter of ownerColor
     *
     * @param color     a char, which is the RealPlayer's color (the one who can use the Terminator)
     */
    public void setOwnerColor(char color) {
        this.ownerColor = color;
    }

    /**
     * Getter of ownerColor
     *
     * @return      a char, which is the color of the owner of the Terminator
     */
    public char getOwnerColor() { return this.ownerColor; }

    /**
     * Adds a damage to the Terminator's victim, if its position is visible
     *
     * @param p     a RealPlayer, the victim of the Terminator attack
     * @throws WrongPlayerException     if the target is wrong
     */
    public void shoot(RealPlayer p) throws WrongPlayerException {
        if (this.ownerColor != p.getColor() && this.getColor() != p.getColor() && Board.isVisibleShoot(this.getPlayerPosition(), p.getPlayerPosition()) != -1)
            PlayerBoard.giveNDamages(this, p, 1);
        else
            throw new WrongPlayerException(p);
    }

    /**
     * Setter of initColor
     *
     * @param c     a char, the initial SpawnpointSquare where the Terminator is placed
     */
    public void setInitColor(char c) { initColor=c; }

    /**
     * Sets the position of the Terminator according to the initial color; in addition, in addition, it sets the alive attribute
     */
    public void respawn(){
        setPlayerPosition(Board.getSpawnpoint(initColor));
        this.getPb().setAlive();
    }

    /**
     * Indicates if there is at least a RealPlayer which can be seen by the Terminator
     *
     * @param players       an ArrayList or RealPlayer, which contains all the RealPlayer in the game
     * @return              a boolean, which is true is there is at leat a seen player
     */
    public boolean isSomeoneVisibleTerminator(ArrayList<RealPlayer> players){
        for(RealPlayer rp : players){
            if (rp.getPlayerPosition() != null && Board.isVisibleShoot(this.getPlayerPosition(), rp.getPlayerPosition()) != -1 && rp.getColor() != this.getOwnerColor())
                return true;
        }
        return false;
    }

}
