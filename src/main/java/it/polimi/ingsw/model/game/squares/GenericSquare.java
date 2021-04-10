package it.polimi.ingsw.model.game.squares;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.exceptions.WrongSquareException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericSquare implements Serializable {

    private int num;
    private int room;
    private boolean sp;
    private boolean[] doors = new boolean[4];
    private char color;
    private ArrayList<Player> players = new ArrayList<>();

    /**
     * Constructor of GenericSquare
     *
     * @param n         an int, the number of the square
     * @param r         an int, the number of the room
     * @param spawn     a boolean, if the square is a spawnpoint
     * @param nord      a boolean, if the nord door is open
     * @param est       a boolean, if the est door is open
     * @param sud       a boolean, if the sud door is open
     * @param ovest     a boolean, if the ovest door is open
     * @param c         a char, the color of the square
     */
    GenericSquare(int n, int r, boolean spawn, boolean nord, boolean est, boolean sud, boolean ovest, char c){
        num = n;
        room = r;
        sp = spawn;
        doors[0] = nord;
        doors[1] = est;
        doors[2] = sud;
        doors[3] = ovest;
        color = c;
    }

    /**
     * Getter for num
     *
     * @return      an int, the number of the square
     */
    public int getNum() {
        return num;
    }

    /**
     * Getter for room
     *
     * @return      an int, the number of the room
     */
    public int getRoom() {
        return room;
    }

    /**
     * Getter for color
     *
     * @return      a char, the color of the square
     */
    public char getColor(){
        return color;
    }

    /**
     * Getter for doors
     *
     * @return      an array of boolean, which contains where it is possible to pass (true) or not (false)
     */
    private boolean[] getDoors() {
        return doors;
    }

    /**
     * Getter for players
     *
     * @return      a List of player which are placed on the square
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Checks if a GenericSquare is spawnpoint or not
     *
     * @return      a boolean
     */
    public boolean isSpawnpoint(){ return sp; }

    /**
     * Adds the given player to the ArrayList of players that are inside the cell
     *
     * @param p     a player that moved into the Square
     */
    public void addPlayer(Player p){
        this.players.add(p);
    }

    /**
     * Removes the given player from the ArrayList of players that are inside the cell
     *
     * @param p     a player that moved from the cell
     */
    public void removePlayer(Player p){
        this.players.remove(p);
    }

    /**
     * Given an GenericSquare as argument, returns if the cell is reachable from the current one. according to the adjacency of
     * the two cells and the presence of a wall or a door between the two.
     *
     * @param s     the GenericSquare which is under the reachability analysis
     * @return      a boolean (true if reachable, false otherwise)
     */
    public boolean isReachable(GenericSquare s) {
        char c = this.isAdjacent(s);
        if (c != '\u0000')
            if (c == 'n') {
                return this.getDoors()[0];
            } else if (c == 'e') {
                return this.getDoors()[1];
            } else if (c == 's'){
                return this.getDoors()[2];
            }else {
                return this.getDoors()[3];
            }
        return false;
    }

    /**
     * Given an GenericSquare as argument, returns a boolean true if it is directly adjacent to the current cell
     *
     * @param       s the GenericSquare we want to verify the adjacency to the current cell
     * @return      a char, which points out at which cardinal direction is the cell adjacent to the current one
     */
    public char isAdjacent(GenericSquare s) {
        try {
            checkExistingSquare(s);
            if (s.num == this.num + 1 && (this.num != 3 && this.num != 7 && this.num != 11))
                return 'e';
            else if (s.num == this.num - 1 && (this.num != 0 && this.num != 4 && this.num != 8))
                    return 'o';
            else {
                if (this.num >= 0 && this.num <= 7 && s.num == this.num + 4)
                    return 's';
                else if (this.num <= 11 && this.num >= 4 && s.num == this.num - 4)
                    return 'n';
            }
        } catch (WrongSquareException ws) {
            //empty
        }
        return '\u0000';
    }

    /**
     * Checks if a GenericSquare exists in the board
     *
     * @param s                         a GenericSquare
     * @throws WrongSquareException     if the square does not exists
     */
    public static void checkExistingSquare(GenericSquare s) throws WrongSquareException {
        if (s.getRoom() == -1)
            throw new WrongSquareException(s);
    }

}
