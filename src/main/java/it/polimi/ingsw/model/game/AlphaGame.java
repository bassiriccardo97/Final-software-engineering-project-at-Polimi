package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.model.player.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class AlphaGame {

    private static ArrayList<Player> players;
    private boolean terminator;
    private int skulls;
    private static Board map;
    private int numOfKills = 0;
    private static int[] playersPoint = {0, 0, 0, 0, 0};
    private static char terminatorColor;
    private static boolean finalFrenzy = false;

    /**
     * Constructor of Alphagame
     *
     * @param boardNum          chosen board
     * @param p                 the ArrayList of players
     * @param terminator        if there is the terminator
     * @param skulls            number of skulls
     */
    public AlphaGame(int boardNum, ArrayList<Player> p, boolean terminator, int skulls) {
        setMap(boardNum, skulls);
        setPlayers(p);
        this.terminator = terminator;
        this.skulls = skulls;
        resetScore();
    }

    /**
     * Getter of terminator
     *
     * @return      a boolean
     */
    public boolean getTerminator() {
        return terminator;
    }

    /**
     * Getter for the color of the terminator
     *
     * @return      the color of the terminator, '\u0000' if there isn't the terminator
     */
    public static char getTerminatorColor() {
        return terminatorColor;
    }

    /**
     * Setter for the terminator color
     *
     * @param terminatorColor       ghe terminator color
     */
    public static void setTerminatorColor(char terminatorColor) {
        AlphaGame.terminatorColor = terminatorColor;
    }

    /**
     * Getter for the final frenzy flag
     *
     * @return      final frenzy flag, true if final frenzy is started, false otherwise
     */
    public static boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * Setter for the final frenzy flag
     *
     * @param finalFrenzy       true when final frenzy is started
     */
    public static void setFinalFrenzy(boolean finalFrenzy) {
        AlphaGame.finalFrenzy = finalFrenzy;
    }

    /**
     * Getter of map
     *
     * @return      a BoardGUI
     */
    public static Board getMap() {
        return map;
    }

    /**
     * Getter of players
     *
     * @return      an ArrayList of RealPlayer
     */
    public static List<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for one player according to the color
     *
     * @param c     a player color
     * @return      the player of the color parameter
     */
    public static Player getPlayerForColor(char c){
        for (Player p : players)
            if (p.getColor() == c)
                return p;
        return null;
    }

    /**
     * Setter for the ArrayList of players
     *
     * @param p     the list of players
     */
    public static void setPlayers(ArrayList<Player> p){
        players = p;
    }

    /**
     * Setter of terminator
     *
     * @param terminator    a boolean
     */
    public void setTerminator(boolean terminator) {
        this.terminator = terminator;
    }

    /**
     * Setter of map
     *
     * @param m     an int, which is the chosen number map
     * @param n     an int, which is the chosen killshots number
     */
    private static void setMap(int m, int n) {
        BoardFactory bf = new BoardFactory(m);
        map = new Board(bf.getSquares(), bf.getSpawnpointSquares(), n);
    }

    /**
     * Adds the player given as argument in the ArrayList of Players of the current game
     *
     * @param p     a player, the one which has to be added to the game
     */
    public void addPlayer(RealPlayer p) {
        players.add(p);
    }

    /**
     * Clears the ArrayList containing the list of Players in the current game
     *
     */
    public void resetPlayers() {
        players.clear();
    }

    /**
     * Getter of playersPoint
     *
     * @return      an array of int
     */
    public static int[] getPlayersPoint(){
        return playersPoint;
    }

    /**
     * Setter of playersPoint
     *
     * @param c     a char, the color of one player
     * @param s     an int, which is the amount of the points
     */
    public static void addPlayersPoint(char c, int s){
        switch (c){
            case 'b':
                playersPoint[0]+=s;
                break;
            case 'e':
                playersPoint[1]+=s;
                break;
            case 'g':
                playersPoint[2]+=s;
                break;
            case 'v':
                playersPoint[3]+=s;
                break;
            default:
                playersPoint[4]+=s;
        }
    }

    /**
     * Determines the winner
     *
     * @return      a RealPlayer
     */
    public Player getWinner(){
        System.out.println("dentro get winner");
        System.out.println("lunghezza arraylist player " + players.size());
        for (Player p : players) {
            p.getPb().lastPointEvenIfNotDead();
            numOfKills += p.getPb().getnDeath();
        }
        System.out.println("calculate score for player");
        int[][] killShotScore = calculateScore();
        PlayerBoard.sort(killShotScore);
        char[] firstKill = new char[5];
        System.out.println("first kills");
        firstKill(firstKill);
        System.out.println("adds points for killshots");
        addLastPoint(killShotScore, firstKill);
        Player winner = getPlayers().get(0);
        Player temp = winner;
        System.out.println("in getWinner " + winner.getColor() + " ha punti " + getPlayersPoint()[PlayerBoard.convertPlayerCharToInt(winner.getColor())]);
        int maxTemp = getPlayersPoint()[PlayerBoard.convertPlayerCharToInt(winner.getColor())];
        for (int i = 0; i < 5; i++) {
            System.out.println("player " + i + " ha punti " + getPlayersPoint()[i]);
            if (i != PlayerBoard.convertPlayerCharToInt(temp.getColor())) {
                if (playersPoint[i] > maxTemp) {
                    maxTemp = playersPoint[i];
                    winner = getPlayerForColor(PlayerBoard.convertPlayerIntToChar(i));
                } else if (playersPoint[i] == maxTemp) {
                    if (winner == null) {
                        if (killShotForPlayer(PlayerBoard.convertPlayerIntToChar(i)) != 0)
                            winner = getPlayerForColor(PlayerBoard.convertPlayerIntToChar(i));
                    } else {
                        if (killShotForPlayer(PlayerBoard.convertPlayerIntToChar(i)) == 0 && killShotForPlayer(winner.getColor()) == 0)
                            winner = null;
                        else {
                            if (killShotForPlayer(PlayerBoard.convertPlayerIntToChar(i)) != 0 && killShotForPlayer(winner.getColor()) == 0)
                                winner = getPlayerForColor(PlayerBoard.convertPlayerIntToChar(i));
                            else if (killShotForPlayer(PlayerBoard.convertPlayerIntToChar(i)) != 0 && killShotForPlayer(winner.getColor()) != 0) {
                                for (int j = 0; j < 5; j++) {
                                    if (firstKill[j] == PlayerBoard.convertPlayerIntToChar(playersPoint[i]))
                                        winner = getPlayerForColor(PlayerBoard.convertPlayerIntToChar(playersPoint[i]));
                                    else if (firstKill[j] == winner.getColor())
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return winner;
    }

    /**
     * Adds the last points according to the killshot track
     *
     * @param a     the array with players in the first row and their scores in the second row
     * @param b     the array that contains the order of the kilshots for the players
     */
    private void addLastPoint(int[][] a, char[] b){
        int point = 8;
        for (int i = 0; i < 4; i++) {
            if (a[0][i] > 0) {
                if (a[0][i] != a[0][i + 1]) {
                    addPlayersPoint(PlayerBoard.convertPlayerIntToChar(a[1][i]), point);
                    if (point == 2)
                        point = 1;
                    else if (point > 2)
                        point -= 2;
                } else {
                    char[] same = sameKillShotScore(a, a[0][i]);
                    for (int s = 0; s < 5; s++)
                        if (a[0][s] == a[0][i] && s != i)
                            a[0][s] = -1;
                    a[0][i] = -1;
                    for (int j = 0; j < 5; j++)
                        for (int k = 0; k < 5; k++) {
                            if (b[j] == same[k] && same[k] != '\u0000') {
                                addPlayersPoint(same[k], point);
                                if (point == 2)
                                    point = 1;
                                else if (point > 2)
                                    point -= 2;
                                same[i] = '\u0000';
                            }
                        }
                }
            }
        }
    }

    /**
     * Searches the players with same score and inserts their color into an array
     *
     * @param a         the array of players and their scores
     * @param score     the score
     * @return          the array of players with same score
     */
    private char[] sameKillShotScore(int[][] a, int score){
        char[] t = new char[5];
        for (int i = 0, j = 0; i < skulls; i++) {
            if (a[0][i] == score) {
                t[j] = PlayerBoard.convertPlayerIntToChar(a[1][i]);
                j++;
            }
        }
        return t;
    }

    /**
     * Puts player colors in one array according to the order of the killshots
     *
     * @param c     the array in which insert player colors
     */
    private void firstKill(char[] c) {
        for (int i = 0; i < numOfKills; i++)
            for (int j = 0; j < 5; j++) {
                if (c[j] == '\u0000') {
                    c[j] = Board.getKillShotTrackRMX()[i].charAt(0);
                    break;
                }
                if (c[j] == Board.getKillShotTrackRMX()[i].charAt(0) && Board.getKillShotTrackRMX()[i] != null)
                    break;
            }
    }

    /**
     * Calculates the score of each player according to their killshots
     *
     * @return      a two-dimensions array that contains in the first row numbers that refer to players and in the second row on the same column the points of the player
     */
    private int[][] calculateScore(){
        int[][] t = new int[2][5];
        for (int i = 0; i < 5; i++) {
            t[0][i] = killShotForPlayer(PlayerBoard.convertPlayerIntToChar(i));
            t[1][i] = i;
        }
        return t;
    }

    /**
     * Calculates the number of killshots for one player
     *
     * @param c     the player color
     * @return      the number of killshot
     */
    private int killShotForPlayer(char c){
        int count = 0;
        System.out.println("killshot normali");
        for (String s : Board.getKillShotTrackRMX()) {
            if (s != null && s.charAt(0) == c)
                count += s.length();
        }
        System.out.println("killshot frenzy");
        for (String s : Board.getKillShotFF()) {
            if (s != null && s.charAt(0) == c)
                count += s.length();
        }
        System.out.println("finito killshot per player " + c);
        return count;
    }

    /**
     * Sets the score of all players to 0
     */
    public static void resetScore(){           //for testing
        for (int i = 0; i < 5; i++)
            playersPoint[i] = 0;
    }
}