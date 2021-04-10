package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.model.card.ammotile.AmmoTile;
import it.polimi.ingsw.model.clientmodel.RealPlayerClientModel;
import it.polimi.ingsw.model.clientmodel.SquareClientModel;
import it.polimi.ingsw.model.clientmodel.TerminatorClientModel;
import it.polimi.ingsw.model.clientmodel.WeaponClient;
import it.polimi.ingsw.model.game.Board;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {

    private final int VERTICAL_SIZE = 7; //rows size
    private final int HORIZONTAL_SIZE = 14; //cols size
    private final int NUM_VERTICAL_SQUARE = 3;
    private final int NUM_HORIZONTAL_SQUARE = 4;
    private int mapNumber;
    private int skullsNumber;
    private String[][] tiles = new String[NUM_VERTICAL_SQUARE*VERTICAL_SIZE][NUM_HORIZONTAL_SQUARE*HORIZONTAL_SIZE];
    private ArrayList<WeaponClient> blueSpawnpoint;
    private ArrayList<WeaponClient> redSpawnpoint;
    private ArrayList<WeaponClient> yellowSpawnpoint;
    private ArrayList<RealPlayerClientModel> players;
    private boolean isTerminator;
    private TerminatorClientModel terminator;
    private ArrayList<SquareClientModel> squares = new ArrayList<>();

    public Map(int number, int skullsNumber) {
        setMapNumber(number);
        setSkullsNumber(skullsNumber);
        this.tiles = new String[NUM_VERTICAL_SQUARE*VERTICAL_SIZE][NUM_HORIZONTAL_SQUARE*HORIZONTAL_SIZE];
        buildSquares();
    }

    private void buildSquares() {

        //BUILD ALL SQUARES
        for (int i = 0; i < NUM_VERTICAL_SQUARE; i++) {
            for (int j = 0; j < NUM_HORIZONTAL_SQUARE; j++) {
                switch (mapNumber) {

                    //MAP NUMBER 1
                    case 1:
                        //Blue
                        if((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == 2)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[34m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[34m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[34m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[34m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[34m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[34m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[34m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[34m╝";
                        }
                        //Green
                        if((i == 0 && j == 3)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[32m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[32m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[32m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[32m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[32m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[32m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[32m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[32m╝";
                        }
                        //Yellow
                        if((i == 1 && (j == 3 || j == 2)) || (i == 2 && (j == 2 || j == 3))) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[33m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[33m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[33m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[33m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[33m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[33m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[33m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[33m╝";
                        }
                        //Red
                        if((i == 1 && (j == 0 || j == 1))) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[31m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[31m╝";
                        }
                        //White
                        if(i == 2 && j == 1) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[37m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[37m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[37m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[37m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[37m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[37m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[37m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[37m╝";
                        }
                        break;




                    //MAP NUMBER 2
                    case 2:
                        //Blue
                        if((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == 2)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[34m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[34m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[34m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[34m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[34m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[34m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[34m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[34m╝";
                        }
                        //Red
                        if((i == 1 && (j == 0 || j == 1))) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[31m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[31m╝";
                        }
                        if(i == 1 && j == 2) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[31m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[31m╝";
                        }
                        //White
                        if(i == 2 && (j == 1 || j == 2)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[37m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[37m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[37m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[37m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[37m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[37m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[37m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[37m╝";
                        }
                        //Yellow
                        if((j == 3 && (i == 1 || i == 2))) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[33m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[33m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[33m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[33m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[33m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[33m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[33m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[33m╝";
                        }
                        break;

                    //MAP NUMBER 3
                    case 3:
                        //Yellow
                        if((i == 1 && (j == 3 || j == 2)) || (i == 2 && (j == 2 || j == 3))) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[33m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[33m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[33m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[33m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[33m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[33m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[33m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[33m╝";
                        }
                        //Green
                        if((i == 0 && j == 3)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[32m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[32m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[32m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[32m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[32m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[32m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[32m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[32m╝";
                        }
                        //Blue
                        if((i == 0 && j == 1) || (i == 0 && j == 2)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[34m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[34m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[34m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[34m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[34m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[34m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[34m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[34m╝";
                        }
                        //White
                        if(i == 2 && (j == 0 || j == 1)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[37m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[37m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[37m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[37m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[37m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[37m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[37m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[37m╝";
                        }
                        //Red
                        if((i == 0 && j == 0) || (i == 1 && j == 0)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[31m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[31m╝";
                        }
                        //Purple
                        if(i == 1 && j == 1) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[35m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[35m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[35m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[35m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[35m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[35m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[35m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[35m╝";
                        }
                        break;

                    //MAP NUMBER 4
                    default:
                        //Purple
                        if(i == 1 && (j == 1 || j == 2)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[35m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[35m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[35m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[35m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[35m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[35m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[35m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[35m╝";
                        }
                        //Red
                        if((i == 0 && j == 0) || (i == 1 && j == 0)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[31m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[31m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[31m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[31m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[31m╝";
                        }
                        //Blue
                        if((i == 0 && j == 1) || (i == 0 && j == 2)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[34m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[34m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[34m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[34m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[34m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[34m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[34m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[34m╝";
                        }
                        //Yellow
                        if((j == 3 && (i == 1 || i == 2))) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[33m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[33m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[33m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[33m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[33m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[33m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[33m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[33m╝";
                        }
                        //White
                        if(i == 2 && (j == 0 || j == 1 || j == 2)) {
                            tiles[i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[37m╔";
                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[37m═";
                            }

                            tiles[i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[37m╗";

                            for (int r = 1; r < VERTICAL_SIZE - 1; r++) {
                                tiles[r + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE] = "\u001B[37m║";
                                for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                    tiles[r + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = " ";
                                }
                                tiles[r + i * VERTICAL_SIZE][(j + 1) * HORIZONTAL_SIZE - 1] = "\u001B[37m║";
                            }

                            tiles[VERTICAL_SIZE + i * VERTICAL_SIZE - 1][j * HORIZONTAL_SIZE] = "\u001B[37m╚";

                            for (int c = 1; c < HORIZONTAL_SIZE - 1; c++) {
                                tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][c + j * HORIZONTAL_SIZE] = "\u001B[37m═";
                            }

                            tiles[VERTICAL_SIZE - 1 + i * VERTICAL_SIZE][j * HORIZONTAL_SIZE + HORIZONTAL_SIZE - 1] = "\u001B[37m╝";
                        }
                }


            }
        }

        // SQUARE NUMBERS
        int squareNum = 0;
        for (int v = 1; v < 3*VERTICAL_SIZE; v += VERTICAL_SIZE) {
            for (int h = 1; h < 4*HORIZONTAL_SIZE; h += HORIZONTAL_SIZE){
                if(squareNum > 9) {
                    if(squareNum == 11) {
                        tiles[v][h] = "\u001B[43m ";
                        tiles[v][h + 1] = "\u001B[43m\u001B[30m1";
                        tiles[v][h + 2] = "\u001B[43m\u001B[30m1";
                        tiles[v][h + 3] = "\u001B[43m ";
                        tiles[v][h + 4] = "\u001B[0m ";
                    } else {
                        tiles[v][h + 1] = "1";
                        tiles[v][h + 2] = String.valueOf(squareNum % 10);
                    }
                } else {
                    if(squareNum == 2) {
                        tiles[v][h] = "\u001B[44m ";
                        tiles[v][h + 1] = "\u001B[44m\u001B[30m2";
                        tiles[v][h + 2] = "\u001B[44m ";
                        tiles[v][h + 3] = "\u001B[0m ";
                    } else if(squareNum == 4) {
                        tiles[v][h] = "\u001B[41m ";
                        tiles[v][h + 1] = "\u001B[41m\u001B[30m4";
                        tiles[v][h + 2] = "\u001B[41m ";
                        tiles[v][h + 3] = "\u001B[0m ";
                    } else
                        tiles [v][h + 1] = String.valueOf(squareNum);
                }
                squareNum++;
            }
        }

        //REMOVE SQUARES
        switch (mapNumber) {

            case 1:
                for (int r = 2*VERTICAL_SIZE; r < 3*VERTICAL_SIZE; r++) {
                    for (int c = 0; c < HORIZONTAL_SIZE; c++) {
                        tiles [r][c] = " ";
                    }
                }
                break;
            case 2:
                for (int r = 2*VERTICAL_SIZE; r < 3*VERTICAL_SIZE; r++) {
                    for (int c = 0; c < HORIZONTAL_SIZE; c++) {
                        tiles [r][c] = " ";
                    }
                }
                for (int r = 0; r < VERTICAL_SIZE; r++) {
                    for (int c = 3*HORIZONTAL_SIZE; c < 4*HORIZONTAL_SIZE; c++) {
                        tiles [r][c] = " ";
                    }
                }
                break;
            case 4:
                for (int r = 0; r < VERTICAL_SIZE; r++) {
                    for (int c = 3*HORIZONTAL_SIZE; c < 4*HORIZONTAL_SIZE; c++) {
                        tiles [r][c] = " ";
                    }
                }
                break;
            default:
                break;
        }


        //DOORS

        switch (mapNumber) {
            case 1:
                //Blue - Green
                for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                    if(h == 4 || h == 2*HORIZONTAL_SIZE + 4)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[34m╗";
                    if(h == HORIZONTAL_SIZE - 5 || h == 3*HORIZONTAL_SIZE -5 || h == 4*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[34m╔";
                    if(h == 3*HORIZONTAL_SIZE +4)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[32m╗";
                    if(h == 4*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[32m╔";
                    if((h > 4 && h < HORIZONTAL_SIZE - 5) || (h > 2*HORIZONTAL_SIZE + 4 && h < 3*HORIZONTAL_SIZE - 5) || (h > 3*HORIZONTAL_SIZE + 4 && h < 4*HORIZONTAL_SIZE - 5))
                        tiles[VERTICAL_SIZE - 1][h] = " ";
                }

                for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                    if(h == 2*HORIZONTAL_SIZE + 4 || h == 3*HORIZONTAL_SIZE +4)
                        tiles[VERTICAL_SIZE][h] = "\u001B[33m╝";
                    if(h == 4)
                        tiles[VERTICAL_SIZE][h] = "\u001B[31m╝";
                    if(h == 3*HORIZONTAL_SIZE -5 || h == 4*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE][h] = "\u001B[33m╚";
                    if(h == HORIZONTAL_SIZE - 5)
                        tiles[VERTICAL_SIZE][h] = "\u001B[31m╚";
                    if((h > 4 && h < HORIZONTAL_SIZE - 5) || (h > 2*HORIZONTAL_SIZE + 4 && h < 3*HORIZONTAL_SIZE - 5) || (h > 3*HORIZONTAL_SIZE + 4 && h < 4*HORIZONTAL_SIZE - 5))
                        tiles[VERTICAL_SIZE][h] = " ";
                }
                //White
                for(int h = HORIZONTAL_SIZE; h < 2*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4)
                        tiles[2*VERTICAL_SIZE][h] = "╝";
                    if(h == 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE][h] = "╚";
                    if(h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE][h] = " ";
                }
                //Red
                for(int h = HORIZONTAL_SIZE; h < 2*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4)
                        tiles[2*VERTICAL_SIZE - 1][h] = "\u001B[31m╗";
                    if(h == 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE - 1][h] = "\u001B[31m╔";
                    if(h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5)
                    tiles[2*VERTICAL_SIZE - 1][h] = " ";
                }
                //White
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = "╚";
                    if(v == 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = "╔";
                    if(v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = " ";
                }
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][2*HORIZONTAL_SIZE] = "\u001B[33m╝";
                    if(v == 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE] = "\u001B[33m╗";
                    if(v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE] = " ";
                }
                //Blue - Green
                for(int v = 0; v < VERTICAL_SIZE; v++) {
                    if(v == 2)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = "\u001B[34m╚";
                    if(v == VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = "\u001B[34m╔";
                    if(v > 2 && v < VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = " ";
                }
                for(int v = 0; v < VERTICAL_SIZE; v++) {
                    if(v == 2)
                        tiles[v][3*HORIZONTAL_SIZE] = "\u001B[32m╝";
                    if(v == VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE] = "\u001B[32m╗";
                    if(v > 2 && v < VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE] = " ";
                }
                break;
            case 2:
                //Blue - Green
                for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                    if(h == 4 || h == 2*HORIZONTAL_SIZE + 4)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[34m╗";
                    if(h == HORIZONTAL_SIZE - 5 || h == 3*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[34m╔";
                    if((h > 4 && h < HORIZONTAL_SIZE - 5) || (h > 2*HORIZONTAL_SIZE + 4 && h < 3*HORIZONTAL_SIZE - 5))
                        tiles[VERTICAL_SIZE - 1][h] = " ";
                }
                //Red
                for(int h = HORIZONTAL_SIZE; h < 2*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4)
                        tiles[2*VERTICAL_SIZE - 1][h] = "\u001B[31m╗";
                    if(h == 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE - 1][h] = "\u001B[31m╔";
                    if(h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE - 1][h] = " ";
                }
                for(int h = 0; h < 3*HORIZONTAL_SIZE; h++) {
                    if(h == 4)
                        tiles[VERTICAL_SIZE][h] = "\u001B[31m╝";
                    if(h == HORIZONTAL_SIZE - 5)
                        tiles[VERTICAL_SIZE][h] = "\u001B[31m╚";
                    if((h > 4 && h < HORIZONTAL_SIZE - 5) || (h > 2*HORIZONTAL_SIZE + 4 && h < 3*HORIZONTAL_SIZE -5))
                        tiles[VERTICAL_SIZE][h] = " ";
                    if (h == 2*HORIZONTAL_SIZE + 4)
                        tiles[VERTICAL_SIZE][h] = "\u001B[31m╝";
                    if (h == 3*HORIZONTAL_SIZE - 5)
                        tiles[VERTICAL_SIZE][h] = "\u001B[31m╚";
                }
                for(int v = VERTICAL_SIZE; v < 2*VERTICAL_SIZE; v++) {
                    if(v == VERTICAL_SIZE + 2)
                        tiles[v][3*HORIZONTAL_SIZE -1] = "\u001B[31m╚";
                    if(v == 2*VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE -1] = "\u001B[31m╔";
                    if ((v > VERTICAL_SIZE + 2 && v < 2*VERTICAL_SIZE - 3))
                        tiles[v][3*HORIZONTAL_SIZE -1] = " ";
                }
                //White
                for(int h = 0; h < 2*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4)
                        tiles[2*VERTICAL_SIZE][h] = "╝";
                    if(h == 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE][h] = "╚";
                    if((h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5))
                        tiles[2*VERTICAL_SIZE][h] = " ";
                }
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = "╚";
                    if(v == 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = "╔";
                    if(v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = " ";
                }
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][3*HORIZONTAL_SIZE -1] = "\u001B[37m╚";
                    if(v == 3*VERTICAL_SIZE - 3 || v == VERTICAL_SIZE + 2)
                        tiles[v][3*HORIZONTAL_SIZE -1] = "\u001B[37m╔";
                    if ((v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3))
                        tiles[v][3*HORIZONTAL_SIZE -1] = " ";
                }
                //Yellow
                for(int v = VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2 || v == VERTICAL_SIZE + 2)
                        tiles[v][3*HORIZONTAL_SIZE] = "\u001B[33m╝";
                    if(v == 2*VERTICAL_SIZE - 3 || v == 3*VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE] = "\u001B[33m╗";
                    if ((v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3) || (v > VERTICAL_SIZE + 2 && v < 2*VERTICAL_SIZE -3))
                        tiles[v][3*HORIZONTAL_SIZE] = " ";
                }
                break;
            case 3:
                //Blue - Green
                for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4 || h == 2*HORIZONTAL_SIZE + 4)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[34m╗";
                    if(h == 2*HORIZONTAL_SIZE - 5 || h == 3*HORIZONTAL_SIZE -5 || h == 4*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[34m╔";
                    if(h == 3*HORIZONTAL_SIZE +4)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[32m╗";
                    if(h == 4*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[32m╔";
                    if((h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5) || (h > 2*HORIZONTAL_SIZE + 4 && h < 3*HORIZONTAL_SIZE - 5) || (h > 3*HORIZONTAL_SIZE + 4 && h < 4*HORIZONTAL_SIZE - 5))
                        tiles[VERTICAL_SIZE - 1][h] = " ";
                }
                for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                    if(h == 2*HORIZONTAL_SIZE + 4 || h == 3*HORIZONTAL_SIZE +4)
                        tiles[VERTICAL_SIZE][h] = "\u001B[33m╝";
                    if(h == HORIZONTAL_SIZE + 4)
                        tiles[VERTICAL_SIZE][h] = "\u001B[35m╝";
                    if(h == 3*HORIZONTAL_SIZE -5 || h == 4*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE][h] = "\u001B[33m╚";
                    if(h == 2*HORIZONTAL_SIZE - 5)
                        tiles[VERTICAL_SIZE][h] = "\u001B[35m╚";
                    if((h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5) || (h > 2*HORIZONTAL_SIZE + 4 && h < 3*HORIZONTAL_SIZE - 5) || (h > 3*HORIZONTAL_SIZE + 4 && h < 4*HORIZONTAL_SIZE - 5))
                        tiles[VERTICAL_SIZE][h] = " ";
                }
                for(int v = 0; v < VERTICAL_SIZE; v++) {
                    if(v == 2)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = "\u001B[34m╚";
                    if(v == VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = "\u001B[34m╔";
                    if(v > 2 && v < VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = " ";
                }
                for(int v = 0; v < VERTICAL_SIZE; v++) {
                    if(v == 2)
                        tiles[v][3*HORIZONTAL_SIZE] = "\u001B[32m╝";
                    if(v == VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE] = "\u001B[32m╗";
                    if(v > 2 && v < VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE] = " ";
                }
                for(int v = 0; v < VERTICAL_SIZE; v++) {
                    if(v == 2)
                        tiles[v][HORIZONTAL_SIZE] = "\u001B[34m╝";
                    if(v == VERTICAL_SIZE - 3)
                        tiles[v][HORIZONTAL_SIZE] = "\u001B[34m╗";
                    if(v > 2 && v < VERTICAL_SIZE - 3)
                        tiles[v][HORIZONTAL_SIZE] = " ";
                }
                //Red
                for(int v = 0; v < 2*VERTICAL_SIZE; v++) {
                    if(v == 2)
                        tiles[v][HORIZONTAL_SIZE -1] = "\u001B[31m╚";
                    if(v == VERTICAL_SIZE - 3)
                        tiles[v][HORIZONTAL_SIZE -1] = "\u001B[31m╔";
                    if((v > 2 && v < VERTICAL_SIZE - 3))
                        tiles[v][HORIZONTAL_SIZE -1] = " ";
                }
                for(int h = 0; h < HORIZONTAL_SIZE; h++) {
                    if(h == 4)
                        tiles[2*VERTICAL_SIZE -1][h] = "\u001B[31m╗";
                    if(h == HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE -1][h] = "\u001B[31m╔";
                    if((h > 4 && h < HORIZONTAL_SIZE - 5))
                        tiles[2*VERTICAL_SIZE -1][h] = " ";
                }
                //White
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = "╚";
                    if(v == 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = "╔";
                    if(v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = " ";
                }
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][2*HORIZONTAL_SIZE] = "\u001B[33m╝";
                    if(v == 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE] = "\u001B[33m╗";
                    if(v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE] = " ";
                }
                for(int h = 0; h < 2*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4 || h == 4)
                        tiles[2*VERTICAL_SIZE][h] = "╝";
                    if(h == 2*HORIZONTAL_SIZE - 5 || h == HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE][h] = "╚";
                    if((h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5) || (h > 4 && h < HORIZONTAL_SIZE - 5))
                        tiles[2*VERTICAL_SIZE][h] = " ";
                }
                //Purple
                for(int h = HORIZONTAL_SIZE; h < 2*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4)
                        tiles[2*VERTICAL_SIZE -1][h] = "\u001B[35m╗";
                    if(h == 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE -1][h] = "\u001B[35m╔";
                    if(h > HORIZONTAL_SIZE+ 4 && h < 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE -1][h] = " ";
                }
                break;
            default:
                //Blue
                for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4 || h == 2*HORIZONTAL_SIZE + 4)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[34m╗";
                    if(h == 2*HORIZONTAL_SIZE - 5 || h == 3*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE - 1][h] = "\u001B[34m╔";
                    if((h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5) || (h > 2*HORIZONTAL_SIZE + 4 && h < 3*HORIZONTAL_SIZE - 5) /*|| (h > 3*HORIZONTAL_SIZE + 4 && h < 4*HORIZONTAL_SIZE - 5)*/)
                        tiles[VERTICAL_SIZE - 1][h] = " ";
                }
                for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                    if(h == 2*HORIZONTAL_SIZE + 4)
                        tiles[VERTICAL_SIZE][h] = "\u001B[35m╝";
                    if(h == HORIZONTAL_SIZE + 4)
                        tiles[VERTICAL_SIZE][h] = "\u001B[35m╝";
                    if(h == 3*HORIZONTAL_SIZE -5)
                        tiles[VERTICAL_SIZE][h] = "\u001B[35m╚";
                    if(h == 2*HORIZONTAL_SIZE - 5)
                        tiles[VERTICAL_SIZE][h] = "\u001B[35m╚";
                    if((h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5) || (h > 2*HORIZONTAL_SIZE + 4 && h < 3*HORIZONTAL_SIZE - 5))
                        tiles[VERTICAL_SIZE][h] = " ";
                }
                for(int v = 0; v < VERTICAL_SIZE; v++) {
                    if(v == 2)
                        tiles[v][HORIZONTAL_SIZE] = "\u001B[34m╝";
                    if(v == VERTICAL_SIZE - 3)
                        tiles[v][HORIZONTAL_SIZE] = "\u001B[34m╗";
                    if(v > 2 && v < VERTICAL_SIZE - 3)
                        tiles[v][HORIZONTAL_SIZE] = " ";
                }
                //Purple
                for(int h = HORIZONTAL_SIZE; h < 2*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4)
                        tiles[2*VERTICAL_SIZE -1][h] = "\u001B[35m╗";
                    if(h == 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE -1][h] = "\u001B[35m╔";
                    if(h > HORIZONTAL_SIZE+ 4 && h < 2*HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE -1][h] = " ";
                }
                for(int v = VERTICAL_SIZE; v < 2*VERTICAL_SIZE; v++) {
                    if(v == VERTICAL_SIZE + 2)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = "\u001B[35m╚";
                    if(v == 2*VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = "\u001B[35m╔";
                    if(v > VERTICAL_SIZE + 2 && v < 2*VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = " ";
                }
                //White
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = "╚";
                    if(v == 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = "╔";
                    if(v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE - 1] = " ";
                }
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][2*HORIZONTAL_SIZE] = "\u001B[33m╝";
                    if(v == 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE] = "\u001B[33m╗";
                    if(v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3)
                        tiles[v][2*HORIZONTAL_SIZE] = " ";
                }
                for(int v = 2*VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = "\u001B[37m╚";
                    if(v == 3*VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = "\u001B[37m╔";
                    if(v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE - 1] = " ";
                }
                for(int h = 0; h < 2*HORIZONTAL_SIZE; h++) {
                    if(h == HORIZONTAL_SIZE + 4 || h == 4)
                        tiles[2*VERTICAL_SIZE][h] = "╝";
                    if(h == 2*HORIZONTAL_SIZE - 5 || h == HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE][h] = "╚";
                    if((h > HORIZONTAL_SIZE + 4 && h < 2*HORIZONTAL_SIZE - 5) || (h > 4 && h < HORIZONTAL_SIZE - 5))
                        tiles[2*VERTICAL_SIZE][h] = " ";
                }
                //Red
                for(int v = 0; v < 2*VERTICAL_SIZE; v++) {
                    if(v == 2)
                        tiles[v][HORIZONTAL_SIZE -1] = "\u001B[31m╚";
                    if(v == VERTICAL_SIZE - 3)
                        tiles[v][HORIZONTAL_SIZE -1] = "\u001B[31m╔";
                    if((v > 2 && v < VERTICAL_SIZE - 3))
                        tiles[v][HORIZONTAL_SIZE -1] = " ";
                }
                for(int h = 0; h < HORIZONTAL_SIZE; h++) {
                    if(h == 4)
                        tiles[2*VERTICAL_SIZE -1][h] = "\u001B[31m╗";
                    if(h == HORIZONTAL_SIZE - 5)
                        tiles[2*VERTICAL_SIZE -1][h] = "\u001B[31m╔";
                    if((h > 4 && h < HORIZONTAL_SIZE - 5))
                        tiles[2*VERTICAL_SIZE -1][h] = " ";
                }
                //Yellow
                for(int v = VERTICAL_SIZE; v < 3*VERTICAL_SIZE; v++) {
                    if(v == 2*VERTICAL_SIZE + 2 || v == VERTICAL_SIZE + 2)
                        tiles[v][3*HORIZONTAL_SIZE] = "\u001B[33m╝";
                    if(v == 2*VERTICAL_SIZE - 3 || v == 3*VERTICAL_SIZE - 3)
                        tiles[v][3*HORIZONTAL_SIZE] = "\u001B[33m╗";
                    if ((v > 2*VERTICAL_SIZE + 2 && v < 3*VERTICAL_SIZE - 3) || (v > VERTICAL_SIZE + 2 && v < 2*VERTICAL_SIZE -3))
                        tiles[v][3*HORIZONTAL_SIZE] = " ";
                }

        }



        //ROOMS
        switch (mapNumber) {
            case 1:
                for(int v = 0; v < 3*VERTICAL_SIZE; v++) {
                    for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                        if((v > 1 && v < VERTICAL_SIZE - 1 && (h == HORIZONTAL_SIZE - 1 || h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE) || (v > 2*VERTICAL_SIZE + 1 && v < 3*VERTICAL_SIZE - 2 && (h == 3*HORIZONTAL_SIZE -1 || h == 3*HORIZONTAL_SIZE)))) {
                            tiles[v][h] = "╎";
                        }
                        //Blue
                        if(v == 1 && (h == HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[34m╚";
                        if(v == 1 && (h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[34m╝";
                        if((v == VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE - 1)))
                            tiles[v][h] = "\u001B[34m╔";
                        if((v == VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE)))
                            tiles[v][h] = "\u001B[34m╗";
                        //Yellow
                        if((v == 3*VERTICAL_SIZE - 2 && h == 3*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[33m╔";
                        if((v == 3*VERTICAL_SIZE - 2 && h == 3*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[33m╗";

                        if(v > VERTICAL_SIZE && v < 2*VERTICAL_SIZE - 1 && (h == HORIZONTAL_SIZE - 1 || h == HORIZONTAL_SIZE || h == 3*HORIZONTAL_SIZE || h == 3*HORIZONTAL_SIZE - 1)) {
                            tiles[v][h] = "╎";
                        }

                        //Red
                        if((v == VERTICAL_SIZE + 1 && h == HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[31m╚";
                        if((v == VERTICAL_SIZE + 1 && h == HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[31m╝";
                        if((v == 2*VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE - 1 || h == 3*HORIZONTAL_SIZE - 1)) || (v == 2*VERTICAL_SIZE - 1 && (h == 3*HORIZONTAL_SIZE - 2 || h == 4*HORIZONTAL_SIZE - 2 )))
                            tiles[v][h] = "\u001B[31m╔";
                        if((v == 2*VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE || h == 3*HORIZONTAL_SIZE)) || (v == 2*VERTICAL_SIZE - 1 && (h == 2*HORIZONTAL_SIZE + 1 || h == 3*HORIZONTAL_SIZE + 1)))
                            tiles[v][h] = "\u001B[31m╗";

                        //Yellow
                        if((v == VERTICAL_SIZE + 1 && h == 3*HORIZONTAL_SIZE - 1) || (v == 2*VERTICAL_SIZE && (h == 3*HORIZONTAL_SIZE - 2 || h == 4*HORIZONTAL_SIZE - 2 )) || (v == 2*VERTICAL_SIZE + 1&& h == 3*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[33m╚";
                        if((v == VERTICAL_SIZE + 1 && h == 3*HORIZONTAL_SIZE) || (v == 2*VERTICAL_SIZE && (h == 2*HORIZONTAL_SIZE + 1 || h == 3*HORIZONTAL_SIZE + 1)) || (v == 2*VERTICAL_SIZE + 1 && h == 3*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[33m╝";
                        if((v == 2*VERTICAL_SIZE - 2 && h == 3*HORIZONTAL_SIZE - 1) || (v == 2*VERTICAL_SIZE - 1 && (h == 3*HORIZONTAL_SIZE - 2 || h == 4*HORIZONTAL_SIZE - 2 )))
                            tiles[v][h] = "\u001B[33m╔";
                        if((v == 2*VERTICAL_SIZE - 2 && h == 3*HORIZONTAL_SIZE) || (v == 2*VERTICAL_SIZE - 1 && (h == 2*HORIZONTAL_SIZE + 1 || h == 3*HORIZONTAL_SIZE + 1)))
                            tiles[v][h] = "\u001B[33m╗";

                        if(((h > 2*HORIZONTAL_SIZE + 1 && h < 3*HORIZONTAL_SIZE - 2) || (h > 3*HORIZONTAL_SIZE + 1 && h < 4*HORIZONTAL_SIZE - 2)) && (v == 2*VERTICAL_SIZE - 1 || v == 2*VERTICAL_SIZE))
                            tiles[v][h] = "╌";


                    }
                }
                break;
            case 2:
                for(int v = 0; v < 3*VERTICAL_SIZE; v++) {
                    for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                        if((v > 1 && v < VERTICAL_SIZE - 1 && (h == HORIZONTAL_SIZE - 1 || h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE) || (v > VERTICAL_SIZE + 1 && v < 2*VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE -1 || h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE -1 || h == 2*HORIZONTAL_SIZE))|| (v > 2*VERTICAL_SIZE + 1 && v < 3*VERTICAL_SIZE - 2 && (h == 2*HORIZONTAL_SIZE -1 || h == 2*HORIZONTAL_SIZE)))) {
                            tiles[v][h] = "╎";
                        }
                        //Blue
                        if(v == 1 && (h == HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[34m╚";
                        if(v == 1 && (h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[34m╝";
                        if((v == VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE - 1)))
                            tiles[v][h] = "\u001B[34m╔";
                        if((v == VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE)))
                            tiles[v][h] = "\u001B[34m╗";
                        //Red
                        if(v == VERTICAL_SIZE + 1 && (h == HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[31m╚";
                        if(v == VERTICAL_SIZE + 1 && (h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[31m╝";
                        if(v == 2*VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE -1))
                            tiles[v][h] = "\u001B[31m╔";
                        if(v == 2*VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[31m╗";
                        //Grey
                        if(v == 2*VERTICAL_SIZE + 1 && h == 2*HORIZONTAL_SIZE - 1)
                            tiles[v][h] = "\u001B[37m╚";
                        if(v == 2*VERTICAL_SIZE + 1 && h == 2*HORIZONTAL_SIZE)
                            tiles[v][h] = "\u001B[37m╝";
                        if((v == 3*VERTICAL_SIZE - 2 && h == 2*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[37m╔";
                        if((v == 3*VERTICAL_SIZE - 2 && h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[37m╗";
                        //Yellow
                        if((v == 2*VERTICAL_SIZE && h == 4*HORIZONTAL_SIZE - 2))
                            tiles[v][h] = "\u001B[33m╚";
                        if((v == 2*VERTICAL_SIZE && h == 3*HORIZONTAL_SIZE + 1))
                            tiles[v][h] = "\u001B[33m╝";
                        if((v == 2*VERTICAL_SIZE - 1 && h == 4*HORIZONTAL_SIZE - 2 ))
                            tiles[v][h] = "\u001B[33m╔";
                        if((v == 2*VERTICAL_SIZE - 1 && h == 3*HORIZONTAL_SIZE + 1))
                            tiles[v][h] = "\u001B[33m╗";

                        if(((h > 3*HORIZONTAL_SIZE + 1 && h < 4*HORIZONTAL_SIZE - 2)) && (v == 2*VERTICAL_SIZE - 1 || v == 2*VERTICAL_SIZE))
                            tiles[v][h] = "╌";
                    }
                }
                break;
            case 3:
                for(int v = 0; v < 3*VERTICAL_SIZE; v++) {
                    for(int h = 0; h < 4*HORIZONTAL_SIZE; h++) {
                        if(v > 1 && v < VERTICAL_SIZE - 1 && (h == 2*HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE) || ((v > VERTICAL_SIZE + 1 && v < 2*VERTICAL_SIZE - 2) && (h == 3*HORIZONTAL_SIZE -1 || h == 3*HORIZONTAL_SIZE)) || ((v > 2*VERTICAL_SIZE + 1 && v < 3*VERTICAL_SIZE - 2) && (h == HORIZONTAL_SIZE -1 || h == HORIZONTAL_SIZE || h == 3*HORIZONTAL_SIZE -1 || h == 3*HORIZONTAL_SIZE))) {
                            tiles[v][h] = "╎";
                        }
                        //Blue
                        if(v == 1 && h == 2*HORIZONTAL_SIZE - 1)
                            tiles[v][h] = "\u001B[34m╚";
                        if(v == 1 && h == 2*HORIZONTAL_SIZE)
                            tiles[v][h] = "\u001B[34m╝";
                        if((v == VERTICAL_SIZE - 2 && h == 2*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[34m╔";
                        if((v == VERTICAL_SIZE - 2 && h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[34m╗";
                        //Yellow
                        if((v == VERTICAL_SIZE + 1 && h == 3*HORIZONTAL_SIZE - 1) || (v == 2*VERTICAL_SIZE && (h == 3*HORIZONTAL_SIZE - 2 || h == 4*HORIZONTAL_SIZE - 2 )) || (v == 2*VERTICAL_SIZE + 1&& h == 3*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[33m╚";
                        if((v == VERTICAL_SIZE + 1 && h == 3*HORIZONTAL_SIZE) || (v == 2*VERTICAL_SIZE && (h == 2*HORIZONTAL_SIZE + 1 || h == 3*HORIZONTAL_SIZE + 1)) || (v == 2*VERTICAL_SIZE + 1 && h == 3*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[33m╝";
                        if((v == 2*VERTICAL_SIZE - 2 && h == 3*HORIZONTAL_SIZE - 1) || (v == 2*VERTICAL_SIZE - 1 && (h == 3*HORIZONTAL_SIZE - 2 || h == 4*HORIZONTAL_SIZE - 2 )) || (v == 3 * VERTICAL_SIZE - 2 && h == 3* HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[33m╔";
                        if((v == 2*VERTICAL_SIZE - 2 && h == 3*HORIZONTAL_SIZE) || (v == 2*VERTICAL_SIZE - 1 && (h == 2*HORIZONTAL_SIZE + 1 || h == 3*HORIZONTAL_SIZE + 1)) || (v == 3*VERTICAL_SIZE - 2 && h == 3 * HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[33m╗";

                        if(((h > 2*HORIZONTAL_SIZE + 1 && h < 3*HORIZONTAL_SIZE - 2) || (h > 3*HORIZONTAL_SIZE + 1 && h < 4*HORIZONTAL_SIZE - 2)) && (v == 2*VERTICAL_SIZE - 1 || v == 2*VERTICAL_SIZE))
                            tiles[v][h] = "╌";
                        //Red
                        if ((v == VERTICAL_SIZE && h == HORIZONTAL_SIZE - 2))
                            tiles[v][h] = "\u001B[31m╚";
                        if ((v == VERTICAL_SIZE && h == 1))
                            tiles[v][h] = "\u001B[31m╝";
                        if ((v == VERTICAL_SIZE - 1 && h == HORIZONTAL_SIZE - 2))
                            tiles[v][h] = "\u001B[31m╔";
                        if ((v == VERTICAL_SIZE - 1 && h == 1))
                            tiles[v][h] = "\u001B[31m╗";

                        if (((h > 1 && h < HORIZONTAL_SIZE - 2)) && (v == VERTICAL_SIZE - 1 || v == VERTICAL_SIZE))
                            tiles[v][h] = "╌";
                        //White
                        if(v == 2*VERTICAL_SIZE + 1 && h == HORIZONTAL_SIZE - 1)
                            tiles[v][h] = "\u001B[37m╚";
                        if(v == 2*VERTICAL_SIZE + 1 && h == HORIZONTAL_SIZE)
                            tiles[v][h] = "\u001B[37m╝";
                        if((v == 3*VERTICAL_SIZE - 2 && h == HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[37m╔";
                        if((v == 3*VERTICAL_SIZE - 2 && h == HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[37m╗";
                    }
                }
                break;
            default:
                for(int v = 0; v < 3*VERTICAL_SIZE; v++) {
                    for (int h = 0; h < 4 * HORIZONTAL_SIZE; h++) {
                        if((v > 1 && v < VERTICAL_SIZE - 1 && (h == 2*HORIZONTAL_SIZE - 1 || h == 2*HORIZONTAL_SIZE) || (v > VERTICAL_SIZE + 1 && v < 2*VERTICAL_SIZE - 2 && (h == 2*HORIZONTAL_SIZE -1 || h == 2*HORIZONTAL_SIZE))|| (v > 2*VERTICAL_SIZE + 1 && v < 3*VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE -1 || h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE -1 || h == 2*HORIZONTAL_SIZE)))) {
                            tiles[v][h] = "╎";
                        }
                        //Blue
                        if(v == 1 && h == 2*HORIZONTAL_SIZE - 1)
                            tiles[v][h] = "\u001B[34m╚";
                        if(v == 1 && h == 2*HORIZONTAL_SIZE)
                            tiles[v][h] = "\u001B[34m╝";
                        if((v == VERTICAL_SIZE - 2 && h == 2*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[34m╔";
                        if((v == VERTICAL_SIZE - 2 && h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[34m╗";
                        //Yellow
                        if ((v == 2 * VERTICAL_SIZE && h == 4 * HORIZONTAL_SIZE - 2))
                            tiles[v][h] = "\u001B[33m╚";
                        if ((v == 2 * VERTICAL_SIZE && h == 3 * HORIZONTAL_SIZE + 1))
                            tiles[v][h] = "\u001B[33m╝";
                        if ((v == 2 * VERTICAL_SIZE - 1 && h == 4 * HORIZONTAL_SIZE - 2))
                            tiles[v][h] = "\u001B[33m╔";
                        if ((v == 2 * VERTICAL_SIZE - 1 && h == 3 * HORIZONTAL_SIZE + 1))
                            tiles[v][h] = "\u001B[33m╗";

                        if (((h > 3 * HORIZONTAL_SIZE + 1 && h < 4 * HORIZONTAL_SIZE - 2)) && (v == 2 * VERTICAL_SIZE - 1 || v == 2 * VERTICAL_SIZE))
                            tiles[v][h] = "╌";
                        //Red
                        if ((v == VERTICAL_SIZE && h == HORIZONTAL_SIZE - 2))
                            tiles[v][h] = "\u001B[31m╚";
                        if ((v == VERTICAL_SIZE && h == 1))
                            tiles[v][h] = "\u001B[31m╝";
                        if ((v == VERTICAL_SIZE - 1 && h == HORIZONTAL_SIZE - 2))
                            tiles[v][h] = "\u001B[31m╔";
                        if ((v == VERTICAL_SIZE - 1 && h == 1))
                            tiles[v][h] = "\u001B[31m╗";

                        if (((h > 1 && h < HORIZONTAL_SIZE - 2)) && (v == VERTICAL_SIZE - 1 || v == VERTICAL_SIZE))
                            tiles[v][h] = "╌";
                        //Purple
                        if(v == VERTICAL_SIZE + 1 && h == 2*HORIZONTAL_SIZE - 1)
                            tiles[v][h] = "\u001B[35m╚";
                        if(v == VERTICAL_SIZE + 1 && h == 2*HORIZONTAL_SIZE)
                            tiles[v][h] = "\u001B[35m╝";
                        if((v == 2 * VERTICAL_SIZE - 2 && h == 2*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[35m╔";
                        if((v == 2 * VERTICAL_SIZE - 2 && h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[35m╗";
                        //White
                        if(v == 2 * VERTICAL_SIZE + 1 && (h == HORIZONTAL_SIZE -1 || h == 2*HORIZONTAL_SIZE - 1))
                            tiles[v][h] = "\u001B[37m╚";
                        if(v == 2 * VERTICAL_SIZE + 1 && (h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE))
                            tiles[v][h] = "\u001B[37m╝";
                        if((v == 3 * VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE -1 || h == 2*HORIZONTAL_SIZE - 1)))
                            tiles[v][h] = "\u001B[37m╔";
                        if((v == 3 * VERTICAL_SIZE - 2 && (h == HORIZONTAL_SIZE || h == 2*HORIZONTAL_SIZE)))
                            tiles[v][h] = "\u001B[37m╗";

                    }
                }
        }


    }

    //METHODS

    public void addPlayer(RealPlayerClientModel p, int square) {
        int squareNum = 0;
        for (int v = 1; v < 3*VERTICAL_SIZE; v += VERTICAL_SIZE) {
            for (int h = 1; h < 4*HORIZONTAL_SIZE; h += HORIZONTAL_SIZE){
                if(squareNum == square) {
                    if(p.getColor() == 'b') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 2 ] = "\u001B[34m▉";
                    }
                    if(p.getColor() == 'e') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 4 ] = "\u001B[32m▉";
                    }
                    if(p.getColor() == 'g') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 6 ] = "\u001B[37m▉";
                    }
                    if(p.getColor() == 'v') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 8 ] = "\u001B[35m▉";
                    }
                    if(p.getColor() == 'y') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 10 ] = "\u001B[33m▉";
                    }

                }
                squareNum++;
            }
        }
    }

    public void addTerminator() {
        int squareNum = 0;
        for (int v = 1; v < 3*VERTICAL_SIZE; v += VERTICAL_SIZE) {
            for (int h = 1; h < 4*HORIZONTAL_SIZE; h += HORIZONTAL_SIZE){
                if(squareNum == terminator.getPlayerPosition()) {
                    if(terminator.getColor() == 'b') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 2 ] = "\u001B[34m▉";
                    }
                    if(terminator.getColor() == 'e') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 4 ] = "\u001B[32m▉";
                    }
                    if(terminator.getColor() == 'g') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 6 ] = "\u001B[37m▉";
                    }
                    if(terminator.getColor() == 'v') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 8 ] = "\u001B[35m▉";
                    }
                    if(terminator.getColor() == 'y') {
                        tiles[v + VERTICAL_SIZE/2 - 1][h + 10 ] = "\u001B[33m▉";
                    }

                }
                squareNum++;
            }
        }
    }

    public void removePlayer() {
        int squareNum = 0;
        for (int v = 1; v < 3*VERTICAL_SIZE; v += VERTICAL_SIZE) {
            for (int h = 1; h < 4*HORIZONTAL_SIZE; h += HORIZONTAL_SIZE){
                    tiles[v + VERTICAL_SIZE/2 - 1][h + 2 ] = " ";
                    tiles[v + VERTICAL_SIZE/2 - 1][h + 4 ] = " ";
                    tiles[v + VERTICAL_SIZE/2 - 1][h + 6 ] = " ";
                    tiles[v + VERTICAL_SIZE/2 - 1][h + 8 ] = " ";
                    tiles[v + VERTICAL_SIZE/2 - 1][h + 10 ] = " ";
                }
                squareNum++;
            }
        return;
    }

    public void addAmmoTile(int square, AmmoTile tile) {
        char[] ammo = ammoFromAmmoTile(tile);
        int squareNum = 0;
        for (int v = 1; v < 3*VERTICAL_SIZE; v += VERTICAL_SIZE) {
            for (int h = 1; h < 4*HORIZONTAL_SIZE; h += HORIZONTAL_SIZE){
                if(squareNum == square) {
                    if(tile.getPowerup()) {
                        tiles[v][h + HORIZONTAL_SIZE - 3] = "\u001B[37mP";
                        for(int i = 0; i < ammo.length; i++) {
                            if(ammo[i] == 'b')
                                tiles[v][h + HORIZONTAL_SIZE - (4 + i)] = "\u001B[34m▊";
                            if(ammo[i] == 'r')
                                tiles[v][h + HORIZONTAL_SIZE - (4 + i)] = "\u001B[31m▊";
                            if(ammo[i] == 'y')
                                tiles[v][h + HORIZONTAL_SIZE - (4 + i)] = "\u001B[33m▊";
                        }
                    } else {
                        for(int i = 0; i < ammo.length; i++) {
                            if (ammo[i] == 'b')
                                tiles[v][h + HORIZONTAL_SIZE - (3 + i)] = "\u001B[34m▊";
                            if (ammo[i] == 'r')
                                tiles[v][h + HORIZONTAL_SIZE - (3 + i)] = "\u001B[31m▊";
                            if (ammo[i] == 'y')
                                tiles[v][h + HORIZONTAL_SIZE - (3 + i)] = "\u001B[33m▊";
                        }
                    }
                    return;
                }
                squareNum++;
            }
        }
    }

    private char[] ammoFromAmmoTile(AmmoTile tile) {
        int count = 0;
        char[] ammo;
        if(tile.getPowerup())
            ammo = new char[2];
        else
            ammo = new char[3];
        int[] ammoNumbers = tile.getCubes();
        for(int i = 0; i < ammoNumbers.length; i++) {
            for(int j = 0; j < ammoNumbers[i]; j++) {
                if (i == 0) {
                    ammo[count] = 'b';
                } else if (i == 1) {
                    ammo[count] = 'r';
                } else if (i == 2) {
                    ammo[count] = 'y';
                }
                count++;
            }

        }
        return ammo;
    }

    public void removeAmmoTile(int square) {
        int squareNum = 0;
        for (int v = 1; v < 3*VERTICAL_SIZE; v += VERTICAL_SIZE) {
            for (int h = 1; h < 4*HORIZONTAL_SIZE; h += HORIZONTAL_SIZE){
                if(squareNum == square) {
                    tiles[v][h + HORIZONTAL_SIZE - 3] = " ";
                    tiles[v][h + HORIZONTAL_SIZE - 4] = " ";
                    tiles[v][h + HORIZONTAL_SIZE - 5] = " ";
                    return;
                }
                squareNum++;
            }
        }
    }

    public void wipeMap() {
        //WIPE AMMO
        for(int i = 0; i < 12; i++) {
            removeAmmoTile(i);
        }
        //WIPE PLAYERS
        removePlayer();
    }

    public void updateMap(int skulls) {
        wipeMap();
        skullsNumber = skulls;

        for (RealPlayerClientModel p: players) {
            if(p.getPlayerPosition() != null)
                addPlayer(p, p.getPlayerPosition());
        }
        if(isTerminator && terminator.getPlayerPosition() != null)
            addTerminator();
        for (int i = 0; i < 12; i++) {
            if(i != 2 && i != 4 && i != 11 && Board.getSquare(i).getAmmo() != null) {
                addAmmoTile(i, Board.getSquare(i).getAmmo());
            }
        }
        return;
    }

    public void plotMap() {
        int numVerticalSquare = getNumVerticalSquare();
        int numHorizontalSquare = getNumHorizontalSquare();
        int verticalSize = getVerticalSize();
        int horizontalSize = getHorizontalSize();
        System.out.print(Color.ANSI_WHITE.escape());
        for(int i = 0; i < 20; i++) {
            System.out.println("");
        }
        for (int r = 0; r < numVerticalSquare * verticalSize; r++) {
            System.out.println();
            System.out.flush();
            System.out.print(Color.ANSI_WHITE.escape());
            for (int c = 0; c < numHorizontalSquare * horizontalSize; c++) {
                System.out.print(getTiles()[r][c]);
                System.out.flush();
                System.out.print(Color.ANSI_WHITE.escape());
                System.out.flush();
            }
        }
    }


    //GETTER

    public int getVerticalSize() {
        return VERTICAL_SIZE;
    }

    public int getHorizontalSize() {
        return HORIZONTAL_SIZE;
    }

    public int getNumVerticalSquare() {
        return NUM_VERTICAL_SQUARE;
    }

    public int getNumHorizontalSquare() {
        return NUM_HORIZONTAL_SQUARE;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public String[][] getTiles() {
        return tiles;
    }

    public int getSkullsNumber() {
        return skullsNumber;
    }

    public ArrayList<RealPlayerClientModel> getPlayersModel() {
        return players;
    }

    public ArrayList<WeaponClient> getBlueSpawnpoint() {
        return blueSpawnpoint;
    }

    public ArrayList<WeaponClient> getRedSpawnpoint() {
        return redSpawnpoint;
    }

    public ArrayList<WeaponClient> getYellowSpawnpoint() {
        return yellowSpawnpoint;
    }

    public ArrayList<SquareClientModel> getSquares() {
        return squares;
    }

    public TerminatorClientModel getTerminator() {
        return terminator;
    }

    public ArrayList<RealPlayerClientModel> getPlayers() {
        return players;
    }

    //SETTER

    private void setMapNumber(int map){
        this.mapNumber = map;
    }

    public void setTerminator(boolean terminator) {
        isTerminator = terminator;
    }

    public void setSkullsNumber(int skullsNumber) {
        this.skullsNumber = skullsNumber;
    }

    public void setPlayersModel(ArrayList<RealPlayerClientModel> playersModel) {
        this.players = playersModel;
    }

    public void setBlueSpawnpoint(ArrayList<WeaponClient> blueSpawnpoint) {
        this.blueSpawnpoint = blueSpawnpoint;
    }

    public void setRedSpawnpoint(ArrayList<WeaponClient> redSpawnpoint) {
        this.redSpawnpoint = redSpawnpoint;
    }

    public void setYellowSpawnpoint(ArrayList<WeaponClient> yellowSpawnpoint) {
        this.yellowSpawnpoint = yellowSpawnpoint;
    }

    public void setTerminator(TerminatorClientModel terminator) {
        this.terminator = terminator;
    }
}
