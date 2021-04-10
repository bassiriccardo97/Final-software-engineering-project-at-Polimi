package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.Board;

import java.io.Serializable;

public class PlayerBoard implements Serializable {

    private char[] damages = new char[12];
    private int[] markedDamages = new int[5];
    private int[] ammoBox = new int[3];
    private int[] secretAmmo = new int[3];
    private int side = 1;
    private boolean dead = false;
    private boolean overkill = false;
    private int[] pointsToAssignSide1 = {8, 6, 4, 2, 1, 1};
    private int[] pointsToAssignSide2 = {2, 1, 1, 1};
    private int ability = 0;
    private boolean doubleKill;
    private boolean noMorePoints;
    private int nDeath = 0;
    private boolean lastPoint = true;

    /**
     * Constructor of PlayerBoardGUI
     */
    public PlayerBoard() {
        secretAmmo[0] = 2;
        secretAmmo[1] = 2;
        secretAmmo[2] = 2;
        ammoBox[0] = 1;
        ammoBox[1] = 1;
        ammoBox[2] = 1;
        this.defaultDamages();
        this.defaultMarkedDamages();
    }

    /**
     * Getter for a single damage
     *
     * @param i     an int, the index of the damage to get
     * @return      a char, the color of the damage
     */
    public char getDamage(int i){
        try {
            this.validDamageIndex(i);
            return this.damages[i];
        }catch (WrongValueException wv){
            wv.wrong();
        }
        return '\u0000';
    }

    /**
     * Getter for the array of damages
     *
     * @return      an array of char, in which each char represents the color of the player that made that damage
     */
    public char[] getDamages() {
        return damages;
    }

    /**
     * Getter for the array of marks
     *
     * @return      an array of int, in which each int represents the number of marks from one player, ordered by color (alphabetically)
     */
    public int[] getMarkedDamages() {
        return markedDamages;
    }

    /**
     * Checks if the index is valid: it has to be between 0 and 11
     *
     * @param i                         an int
     * @throws WrongValueException      if the index is not valid
     */
    private void validDamageIndex(int i) throws WrongValueException {
        if(i < 0 || i >= 12)
            throw new WrongValueException();
    }

    /**
     * Sets all damages to default value \u0000
     */
    private void defaultDamages(){
        for(int i = 0; i < 12; i++)
            this.damages[i] = '\u0000';
    }

    /**
     * Counts the damages on the player board
     *
     * @return      numbers of damages
     */
    public int countDamages(){
        int count = 0;
        for (int i = 0; i < 12; i++) {
            if (this.getDamage(i) != '\u0000') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * Counts the damages of a single color on the player board
     *
     * @param c     a char, the color
     * @return      an int, the number of damages
     */
    private int countDamagesForColor(char c){
        int count = 0;
        for (int i = 0; i < 12; i++)
            if (damages[i] == c)
                count++;
        return count;
    }

    /**
     * Getter for the last damage
     *
     * @return      a char, the color of the last damage
     */
    public char getLastDamage(){
        return damages[countDamages() - 1];
    }

    /**
     * Adds a damage of the color of the player that shoots
     *
     * @param c     shooting player's color
     */
    public void addDamage(char c){
        try {
            this.validPlayer(c);
            if (countDamages() < 12) {
                this.damages[this.countDamages()] = c;
                if (countDamages() == 3)
                    ability = 1;
                else if (countDamages() == 6)
                    ability = 2;
                if (countDamages() == 11) {
                    dead = true;
                    nDeath++;
                    Board.removeSkull();
                    boolean terminator = false;
                    if (c == AlphaGame.getTerminatorColor())
                        terminator = true;
                    if (terminator){
                        Terminator t = (Terminator)AlphaGame.getPlayerForColor(c);
                        if (!AlphaGame.getPlayerForColor(t.getOwnerColor()).getPb().isNoMorePoints()){
                            if (AlphaGame.getPlayerForColor(t.getOwnerColor()).getPb().isDoubleKill()) {
                                AlphaGame.addPlayersPoint(t.getOwnerColor(), 1);
                                AlphaGame.getPlayerForColor(t.getOwnerColor()).getPb().setNoMorePoints(true);
                            }else {
                                AlphaGame.getPlayerForColor(t.getOwnerColor()).getPb().setDoubleKill(true);
                            }
                        }
                    }else {
                        if (!AlphaGame.getPlayerForColor(c).getPb().isNoMorePoints()) {
                            if (AlphaGame.getPlayerForColor(c).getPb().isDoubleKill()) {
                                AlphaGame.addPlayersPoint(c, 1);
                                AlphaGame.getPlayerForColor(c).getPb().setNoMorePoints(true);
                            } else
                                AlphaGame.getPlayerForColor(c).getPb().setDoubleKill(true);
                        }
                    }
                }
                if (countDamages() == 12)
                    overkill = true;
            }
        }catch (WrongValueException wv){
            //empty
        }
    }

    /**
     * Gives a specific number of damages, which are of the first player's color, to the second player
     *
     * @param p1    a player
     * @param p2    a player
     * @param n     an int, the number of damages to assign
     * @throws WrongPlayerException     if the target choice is wrong
     */
    public static void giveNDamages(Player p1, Player p2, int n) throws WrongPlayerException{
        for (int i = 0; i < n; i++) {
            if (p2 == p1)
                throw new WrongPlayerException(p2);
            p2.getPb().addDamage(p1.getColor());
            if (i == n - 1){
                if (p2.getPb().getMarkedDamages(p1.getColor()) != 0) {
                    p2.getPb().markToDamage(p1.getColor());
                }
                if (p2.getPb().getOverkill()) {
                    p1.getPb().addMarkedDamage(p2.getColor());
                    //Board.saveKillShot(String.valueOf(p1.getColor()) + String.valueOf(p1.getColor()));
                    //Board.removeSkull();
                }/*}else if (p2.getPb().isDead()){
                    Board.saveKillShot(String.valueOf(p1.getColor()));
                    //Board.removeSkull();
                }*/
            }
        }
        p2.setJustDamaged(true);
    }

    /**
     * Saves the killshot on the board, like a String composed of one (kill) or two (overkill) characters that stand for the color of the player that made the kill
     */
    public void updateKillshotRMX() {
        if (!AlphaGame.isFinalFrenzy()) {
            if (overkill) {
                Board.saveKillShot(String.valueOf(getLastDamage()) + String.valueOf(getLastDamage()));
            } else {
                Board.saveKillShot(String.valueOf(getLastDamage()));
            }
        }else {
            if (overkill) {
                Board.saveKillShotFF(String.valueOf(getLastDamage()) + String.valueOf(getLastDamage()));
            } else {
                Board.saveKillShotFF(String.valueOf(getLastDamage()));
            }
        }
    }

    /**
     * Getter for the doubleKill boolean, that indicates whether the player has already killed someone or not
     *
     * @return      the doubleKill boolean
     */
    public boolean isDoubleKill() {
        return doubleKill;
    }

    /**
     * Setter for the doubleKill boolean, that indicates whether the player has already killed someone or not
     *
     * @param doubleKill        a boolean
     */
    public void setDoubleKill(boolean doubleKill) {
        this.doubleKill = doubleKill;
    }

    /**
     * Getter for a boolean that indicates whether the player can take an additional point for a kill (in case of doublekill) or he can't because has already taken the point
     *
     * @return      a boolean
     */
    public boolean isNoMorePoints() {
        return noMorePoints;
    }

    /**
     * Setter for a boolean that indicates whether the player can take and additional point for a kill (in case of doublekill) or not
     *
     * @param noMorePoints      a boolean
     */
    public void setNoMorePoints(boolean noMorePoints) {
        this.noMorePoints = noMorePoints;
    }

    /**
     * Getter for overkill
     *
     * @return      a boolean
     */
    private boolean getOverkill(){
        return overkill;
    }

    /**
     * Getter for ability
     *
     * @return      an int
     */
    public int getAbility(){
        return ability;
    }

    /**
     * Checks if the player is dead
     *
     * @return      a boolean
     */
    public boolean isDead(){
        return dead;
    }

    /**
     * Getter for the array that contains the points to assign to each player that has done at least one damage to the owner of the playerboard
     *
     * @return      an array of int, where each int represent an amount of points (-1 if that points must not be assigned)
     */
    private int[] getPointsToAssign(){
        if (side == 1)
            return pointsToAssignSide1;
        return pointsToAssignSide2;
    }

    /**
     * Calculates the score of each player and sets the player alive again if the game is not over
     */
    public void afterDeath(){
        if (isDead()) {
            ability = 0;
            updatePlayersPoint();
            if (side == 1)
                updatePointsToAssignSide1();
            //setAlive();
            defaultDamages();
        }
    }

    /**
     * It assigns the last points to each player that made at least one damage to the owner of the playerboard, even if he's not dead
     */
    public void lastPointEvenIfNotDead(){
        if (lastPoint) {
            updatePlayersPoint();
            lastPoint = false;
        }
        //defaultDamages();
    }

    /**
     * After the player's death, updates the points that can be given to the players
     */
    private void updatePointsToAssignSide1(){
        if (getSide() == 1) {
            for (int i = 0; i < 6; i++) {
                if (pointsToAssignSide1[i] > 0) {
                    pointsToAssignSide1[i] = -1;
                    return;
                }
            }
        }
    }

    /**
     * Getter for an int that stands for the number of deaths of the owner of the playerboard
     *
     * @return      an int
     */
    public int getnDeath(){
        return nDeath;
    }

    /**
     * Return the index of the player point in his playerboard
     *
     * @return      an int
     */
    private int pointIndex(){
        for (int i = 0; i < getPointsToAssign().length; i++)
            if (getPointsToAssign()[i] != -1)
                return i;
        return -1;
    }

    /**
     * Calculates the score of each player
     */
    private void updatePlayersPoint(){
        int[][] damagesForPlayer = damagesForPlayer();
        sort(damagesForPlayer);
        char[] damageOrder = firstDamages();
        if (getSide() == 1 && damageOrder[0] != '\u0000')
            AlphaGame.addPlayersPoint(damageOrder[0], 1);
        int i = pointIndex();
        for (int p = 0; p < 4; p++)
            i = pointAdderAndTieSolver(damagesForPlayer, damageOrder, p, i);

    }

    /**
     * Sorts the second row (points), maintaining the correspondence with the first row (players)
     *
     * @param a     the sorted array
     */
    public static void sort(int[][] a){
        for (int i = 0 ; i < 5; i++) {
            for (int j = 0 ; j < 4; j++) {
                if (a[0][j] < a[0][j + 1]) {
                    int swap1 = a[0][j];
                    int swap2 = a[1][j];
                    a[0][j] = a[0][j + 1];
                    a[1][j] = a[1][j + 1];
                    a[0][j + 1] = swap1;
                    a[1][j + 1] = swap2;
                }
            }
        }
    }

    /**
     * Creates the two-dimensions array with a number (corresponding a player) on the first row and the number of damages corresponding the player above
     *
     * @return      a two-dimensions array with players and their number of damages on the playerboard
     */
    private int[][] damagesForPlayer(){
        int[][] t = new int[2][5];
        for (int i = 0; i < 5; i++) {
            t[1][i] = i;
            t[0][i] = countDamagesForColor(convertPlayerIntToChar(i));
        }
        return t;
    }

    /**
     * Method that updates the points of each player according to their damages on the playerboard
     *
     * @param a     each player with its damages done
     * @param b     the order of the damages for player
     * @param p     an index that indicates for how many players points have been updated
     * @param i     index that indicates how many points needs to be added according to the pointsToAssign array
     * @return      the new index for the points to assign
     */
    private int pointAdderAndTieSolver(int[][] a, char[] b, int p, int i){
        char pl;
        if (a[0][p] != a[0][p + 1]) {
            if (a[1][p] != -1) {
                pl = convertPlayerIntToChar(a[1][p]);
                AlphaGame.addPlayersPoint(pl, getPointsToAssign()[i]);
                for (int j = 0; j < 5; j++)
                    if (b[j] == pl)
                        b[j] = '\u0000';
                if (i < 5)
                    i++;
            }
        } else {
            char[] sameDamage = sameDamage(a[0][p], a);
            for (int j = 0; j < 5; j++)
                for (int k = 0; k < 5; k++) {
                    if (sameDamage[k] == b[j] && b[j] != '\u0000') {
                        AlphaGame.addPlayersPoint(b[j], getPointsToAssign()[i]);
                        int t = 0;
                        for (; t < 5; t++)
                            if (a[1][t] == convertPlayerCharToInt(b[j])) {
                                a[1][t] = -1;
                                break;
                            }
                        b[j] = '\u0000';
                        if (i < 5)
                            i++;
                    }
                }
        }
        return i;
    }

    /**
     * Searches players that have same number of damages done and puts them into an array of char (stands for the color of the players)
     *
     * @param n     the number of damages done
     * @param a     the two-dimensions array with players and their number of damages done
     * @return      an array of char
     */
    private char[] sameDamage(int n, int[][] a){
        char[] t = new char[5];
        int j = 0;
        for (int i = 0; i < 5; i++)
            if (a[0][i] == n) {
                t[j] = convertPlayerIntToChar(a[1][i]);
                j++;
            }
        return t;
    }

    /**
     * Converts a number which identifies a player, into a char that stands for the color of the player
     *
     * @param n     the number assigned to a precise player
     * @return      the color of the player
     */
    public static char convertPlayerIntToChar(int n){
        switch (n){
            case 0:
                return 'b';
            case 1:
                return 'e';
            case 2:
                return 'g';
            case 3:
                return 'v';
            default:
                return 'y';
        }
    }

    /**
     * Converts a char that stands for the color of one player, into a number that stands for the player
     *
     * @param c     the color of the player
     * @return      the int that identifies the player
     */
    public static int convertPlayerCharToInt(char c){
        switch (c){
            case 'b':
                return 0;
            case 'e':
                return 1;
            case 'g':
                return 2;
            case'v':
                return 3;
            default:
                return 4;
        }
    }

    /**
     * Searches for the first damages of different color, for each color present on the playerboard, and puts them into an array of char following the order
     *
     * @return      the order of different color damages
     */
    private char[] firstDamages(){
        char[] temp = new char[5];
        boolean ended = false;
        temp[0] = getDamage(0);
        for (int i = 0; i < countDamages() && !ended; i++)
            for (int j = 0; j < 5; j++) {
                if (temp[j] == getDamage(i))
                    break;
                else if (temp[j] == '\u0000') {
                    temp[j] = getDamage(i);
                    if (j == 4)
                        ended = true;
                    break;
                }
            }
        return temp;
    }

    /**
     * Puts the marked damages of one color as damages on the player board
     *
     * @param c     a char, the color
     */
    public void markToDamage(char c){
        if (getMarkedDamages(c) > 0) {
            int j = getMarkedDamages(c);
            for (int i = 0; i < j /*&& countDamages() < 12*/; i++) {
                addDamage(c);
                removeMarkedDamage(c);
            }
        }
    }

    /**
     * Sets dead and overkill to false, as the player is alive
     */
    public void setAlive(){
        dead = false;
        overkill = false;
    }

    /**
     * Checks if the color is valid for a player: b, e, g, v, y
     *
     * @param c                         a char, the color
     * @throws WrongValueException      if the color is not valid
     */
    private void validPlayer(char c) throws WrongValueException {
        if(!(c == 'b' || c == 'e' || c == 'g' || c == 'v' || c == 'y'))
            throw new WrongValueException();
    }

    /**
     * Takes the number of marked damages of color c
     *
     * @param c     the color of the player
     * @return      numbers of damages of color c
     */
    public int getMarkedDamages(char c) {
        try {
            this.validPlayer(c);
            switch (c){
                case 'b':
                    return this.markedDamages[0];
                case 'e':
                    return this.markedDamages[1];
                case 'g':
                    return this.markedDamages[2];
                case 'v':
                    return this.markedDamages[3];
                default:
                    return this.markedDamages[4];
            }
        }catch (WrongValueException wv){
            //empty
        }
        return -1;
    }

    /**
     * Adds a marked damage of the player of color c
     *
     * @param c     player's color
     */
    public void addMarkedDamage(char c){
        try {
            if (this.countMarkedDamages() >= 12)
                throw new WrongValueException();
            switch (c){
                case 'b':
                    if (markedDamages[0] < 3)
                        this.markedDamages[0]++;
                    break;
                case 'e':
                    if (markedDamages[1] < 3)
                        this.markedDamages[1]++;
                    break;
                case 'g':
                    if (markedDamages[2] < 3)
                        this.markedDamages[2]++;
                    break;
                case 'v':
                    if (markedDamages[3] < 3)
                        this.markedDamages[3]++;
                    break;
                default:
                    if (markedDamages[4] < 3)
                        this.markedDamages[4]++;
            }
        }catch (WrongValueException wv){
            //empty
        }
    }

    /**
     * Removes one mark of one color, if there are
     *
     * @param c     the color of the mark to remove
     */
    private void removeMarkedDamage(char c){
        try {
            if (this.countMarkedDamages() == 0)
                throw new WrongValueException();
            switch (c){
                case 'b':
                    this.markedDamages[0]--;
                    break;
                case 'e':
                    this.markedDamages[1]--;
                    break;
                case 'g':
                    this.markedDamages[2]--;
                    break;
                case 'v':
                    this.markedDamages[3]--;
                    break;
                default:
                    this.markedDamages[4]--;
            }
        }catch (WrongValueException wv){
            //empty
        }
    }

    /**
     * Gives a number of marks of the first player's color to the second player
     *
     * @param p1    a player
     * @param p2    a player
     * @param n     an int, the number of marks
     */
    public static void giveNMarks(Player p1, Player p2, int n){
        for (int i = 0; i < n; i++){
            p2.getPb().addMarkedDamage(p1.getColor());
        }
    }

    /**
     * Counts the total number of marked damages
     *
     * @return      number of damages
     */
    public int countMarkedDamages() {        //count the total number of marked damages
        int count = 0;
        count += this.getMarkedDamages('b');
        count += this.getMarkedDamages('e');
        count += this.getMarkedDamages('g');
        count += this.getMarkedDamages('v');
        count += this.getMarkedDamages('y');
        return count;
    }

    /**
     * Sets marked damages to 0
     */
    private void defaultMarkedDamages() {        //set marked damages of each player (blue, emerald, grey, violet, yellow) to 0
        this.markedDamages[0] = 0;
        this.markedDamages[1] = 0;
        this.markedDamages[2] = 0;
        this.markedDamages[3] = 0;
        this.markedDamages[4] = 0;
    }

    /**
     * Returns the number of ammoes of a specific color
     *
     * @param c     ammo color
     * @return      number of ammo of color c
     */
    public int getAmmo(char c){
        try{
            this.validAmmoColor(c);
            switch (c){
                case 'b':
                    return this.ammoBox[0];
                case 'r':
                    return this.ammoBox[1];
                default:
                    return this.ammoBox[2];
            }
        }catch (WrongValueException wv){
           //empty
        }
        return -1;
    }

    /**
     * Checks if the color is valid for an ammo: b, r, y
     *
     * @param c                         a char, the color
     * @throws WrongValueException      if the color is not valid
     */
    private void validAmmoColor(char c) throws WrongValueException {
        if(c == 'b' || c == 'r' || c == 'y')
            return;
        throw new WrongValueException();
    }

    /**
     * Adds one ammo of a specific color if there are less then 3
     *
     * @param c     a char (ammo color)
     */
    public void addAmmo(char c){
        try{
            this.validAmmoColor(c);
            if (this.getAmmo(c) < 3)
                switch (c){
                    case 'b':
                        this.ammoBox[0]++;
                        break;
                    case 'r':
                        this.ammoBox[1]++;
                        break;
                    default:
                        this.ammoBox[2]++;
                }
        }catch (WrongValueException wv){
            //empty
        }
    }

    /**
     * Getter for side
     *
     * @return      an int
     */
    public int getSide() {
        return side;
    }

    /**
     * Set side to 2
     */
    public void rotateSide() {     //set player board to side 1
        if (countDamages() == 0) {
            ability = 0;
            side = 2;
        }
    }

    /**
     * Checks if the player is marked (has almost one marked damage)
     *
     * @return      true if marked, false otherwise
     */
    public boolean isMarked() {
        return this.countMarkedDamages() != 0;
    }

    /**
     * Getter of ammobox
     *
     * @return      an array of int
     */
    public int[] getAmmoBox(){
        return ammoBox;
    }

    /**
     * Checks if the player has enough ammo in order to pay
     *
     * @param cost                      an array of char, stands for the cost to pay
     * @return                          an array of int, the cost converted in number of ammo for color
     * @throws WrongValueException      if the player doesn't have enough ammo
     */
    private int[] checkEnoughAmmo(char[] cost) throws WrongValueException {
        int[] costInt = convert(cost);
        for(int i = 0; i < cost.length; i++){
            if(costInt[i] > this.getAmmoBox()[i])
                throw new WrongValueException();
        }
        return costInt;
    }

    /**
     * Converts the cost (as an array of char) into an array of int: number of ammo to pay for color
     *
     * @param cost      an array of char, the cost to convert
     * @return          an array of int, the converted cost
     */
    public int[] convert(char[] cost){
        int[] t = new int[3];
        for (char c : cost)
            if (c != '\u0000')
                switch (c){
                    case 'b':
                        t[0]++;
                        break;
                    case 'r':
                        t[1]++;
                        break;
                    default:
                        t[2]++;
                }
        return t;
    }

    /**
     * Getter for the array of points to assign for the side 1 of the playerboard
     *
     * @return      an array of int
     */
    public int[] getPointsToAssignSide1() {
        return pointsToAssignSide1;
    }

    /**
     * Getter for the array of points to assign for the side 2 of the playerboard
     *
     * @return      an array of int
     */
    public int[] getPointsToAssignSide2() {
        return pointsToAssignSide2;
    }

    /**
     * Makes the player pay ammo if he has enough ammo
     *
     * @param cost      the cost to pay
     */
    public void payAmmo(char[] cost){
        int[] costInt = convert(cost);
        for (int i = 0; i < this.ammoBox.length; i++)
            if (costInt[i] != '\u0000')
                this.ammoBox[i] -= costInt[i];
    }

    /**
     * Adds the number of ammo for color grabbed
     *
     * @param ammo      an array of int, the ammo grabbed
     */
    public void grabAmmo(int[] ammo){
        addNAmmo('b', ammo[0]);
        addNAmmo('r', ammo[1]);
        addNAmmo('y', ammo[2]);
    }

    /**
     * Adds a number of ammo of one color
     *
     * @param c     the color of the ammo
     * @param n     the number of ammo
     */
    private void addNAmmo(char c, int n){
        for (int i = 0; i < n; i++)
            addAmmo(c);
    }
}