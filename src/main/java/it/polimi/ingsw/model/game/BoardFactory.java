package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.game.squares.Square;

import java.util.ArrayList;

public class BoardFactory {

    private ArrayList<Square> squares = new ArrayList<>();
    private ArrayList<SpawnpointSquare> spawnpointSquares = new ArrayList<>();

    /**
     * Constructor for the board factory
     *
     * @param n     the number of the board to construct
     */
    public BoardFactory(int n) {
        switch (n) {
            case 1:
                board1();
                break;
            case 2:
                board2();
                break;
            case 3:
                board3();
                break;
            default:
                board4();
        }
    }

    /**
     * Sets the board 1
     */
    private void board1(){
        Square s = new Square(0, 1, false, false, true, true, false, 'b');
        squares.add(s);
        s = new Square(1, 1, false, false, true, false, true, 'b');
        squares.add(s);
        SpawnpointSquare sp = new SpawnpointSquare(2, 1, true, false, true, true, true, 'b');
        spawnpointSquares.add(sp);
        s = new Square(3, 2, false, false, false, true, true, 'g');
        squares.add(s);
        sp = new SpawnpointSquare(4, 3, true, true, true, false, false, 'r');
        spawnpointSquares.add(sp);
        s = new Square(5, 3, false, false, false, true, true, 'r');
        squares.add(s);
        s = new Square(6, 4, false, true, true, true, false, 'y');
        squares.add(s);
        s = new Square(7, 4, false, true, false, true, true, 'y');
        squares.add(s);
        s = new Square(8, -1, false, false, false, false, false, '\u0000');
        squares.add(s);
        s = new Square(9, 5, false, true, true, false, false, 'w');
        squares.add(s);
        s = new Square(10, 4, false, true, true, false, true, 'y');
        squares.add(s);
        sp = new SpawnpointSquare(11, 4, true, true, false, false, true, 'y');
        spawnpointSquares.add(sp);
    }

    /**
     * Sets the board 2
     */
    private void board2(){
        Square s = new Square(0, 1, false, false, true, true, false, 'b');
        squares.add(s);
        s = new Square(1, 1, false, false, true, false, true, 'b');
        squares.add(s);
        SpawnpointSquare sp = new SpawnpointSquare(2, 1, true, false, false, true, true, 'b');
        spawnpointSquares.add(sp);
        s = new Square(3, -1, false, false, false, false, false, '\u0000');
        squares.add(s);
        sp = new SpawnpointSquare(4, 2, true, true, true, false, false, 'r');
        spawnpointSquares.add(sp);
        s = new Square(5, 2, false, false, true, true, true, 'r');
        squares.add(s);
        s = new Square(6, 2, false, true, true, false, true, 'r');
        squares.add(s);
        s = new Square(7, 3, false, false, false, true, true, 'y');
        squares.add(s);
        s = new Square(8, -1, false, false, false, false, false, '\u0000');
        squares.add(s);
        s = new Square(9, 4, false, true, true, false, false, 'w');
        squares.add(s);
        s = new Square(10, 4, false, false, true, false, true, 'w');
        squares.add(s);
        sp = new SpawnpointSquare(11, 3, true, true, false, false, true, 'y');
        spawnpointSquares.add(sp);
    }

    /**
     * Sets the board 3
     */
    private void board3(){
        Square s = new Square(0, 1, false, false, true, true, false, 'r');
        squares.add(s);
        s = new Square(1, 2, false, false, true, true, true, 'b');
        squares.add(s);
        SpawnpointSquare sp = new SpawnpointSquare(2, 2, true, false, true, true, true, 'b');
        spawnpointSquares.add(sp);
        s = new Square(3, 3, false, false, false, true, true, 'g');
        squares.add(s);
        sp = new SpawnpointSquare(4, 1, true, true, false, true, true, 'r');
        spawnpointSquares.add(sp);
        s = new Square(5, 4, false, true, false, true, false, 'v');
        squares.add(s);
        s = new Square(6, 5, false, true, true, true, false, 'y');
        squares.add(s);
        s = new Square(7, 5, false, true, false, true, true, 'y');
        squares.add(s);
        s = new Square(8, 6, false, true, true, false, false, 'w');
        squares.add(s);
        s = new Square(9, 6, false, true, true, false, true, 'w');
        squares.add(s);
        s = new Square(10, 5, false, true, true, false, true, 'y');
        squares.add(s);
        sp = new SpawnpointSquare(11, 5, true, true, false, false, true, 'y');
        spawnpointSquares.add(sp);}

    /**
     * Sets the board 4
     */
    private void board4(){
        Square s = new Square(0, 1, false, false, true, true, false, 'r');
        squares.add(s);
        s = new Square(1, 2, false, false, true, true, true, 'b');
        squares.add(s);
        SpawnpointSquare sp = new SpawnpointSquare(2, 2, true, false, false, true, true, 'b');
        spawnpointSquares.add(sp);
        s = new Square(3, -1, false, false, false, false, false, '\u0000');
        squares.add(s);
        sp = new SpawnpointSquare(4, 1, true, true, false, true, false, 'r');
        spawnpointSquares.add(sp);
        s = new Square(5, 3, false, true, true, true, false, 'v');
        squares.add(s);
        s = new Square(6, 4, false, true, true, true, true, 'v');
        squares.add(s);
        s = new Square(7, 5, false, false, false, true, true, 'y');
        squares.add(s);
        s = new Square(8, 6, false, true, true, false, false, 'w');
        squares.add(s);
        s = new Square(9, 6, false, true, true, false, true, 'w');
        squares.add(s);
        s = new Square(10, 6, false, false, true, false, true, 'w');
        squares.add(s);
        sp = new SpawnpointSquare(11, 5, true, true, false, false, true, 'y');
        spawnpointSquares.add(sp);
    }

    /**
     * Getter for the list of squares in the board
     *
     * @return      an ArrayList of Square
     */
    public ArrayList<Square> getSquares() {
        return squares;
    }

    /**
     * Getter for the list of spawnpoints in the board
     *
     * @return      an ArrayList of SpawnpointSquare
     */
    public ArrayList<SpawnpointSquare> getSpawnpointSquares() {
        return spawnpointSquares;
    }
}
