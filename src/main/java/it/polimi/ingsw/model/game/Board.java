package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.ammotile.AmmoDeck;
import it.polimi.ingsw.model.card.ammotile.AmmoTile;
import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.card.powerup.PowerupDeck;
import it.polimi.ingsw.model.card.powerup.PowerupFactory;
import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.card.weapon.WeaponDeck;
import it.polimi.ingsw.model.card.weapon.WeaponFactory;
import it.polimi.ingsw.model.exceptions.EndGameException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.game.squares.Square;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Board {

    private static ArrayList<Square> squares = new ArrayList<>();
    private static ArrayList<SpawnpointSquare> spawnpointSquare = new ArrayList<>();
    private static WeaponDeck weapons = new WeaponDeck();
    private static PowerupDeck powerups = new PowerupDeck();
    private static PowerupDeck discardedPowerups = new PowerupDeck();
    private static AmmoDeck ammoDeck = new AmmoDeck();
    private static AmmoDeck discardedAmmo = new AmmoDeck();
    private static int killShotTrack;
    private static String[] killShotTrackRMX;
    private static String[] killShotFF = new String[6];

    /**
     * Constructor for the board, getting the squares and the number of the skulls form the board factory class
     *
     * @param s             the ArrayList of squares
     * @param sp            the ArrayList of spawnpoints
     * @param killShot      the number of skulls
     */
    public Board(ArrayList<Square> s, ArrayList<SpawnpointSquare> sp, int killShot) {
        try {
            this.validSkull(killShot);
            //this.validBoard(n);
            setKillShotTrack(killShot);
            setKillShotTrackRMX(killShot);
            //this.boardNum = n;
            initWeapon();
            initPowerup();
            initAmmo();
            squares = s;
            spawnpointSquare = sp;
            setWeaponSpawnpoint();
            setAmmoSquare();
        }catch (WrongValueException wv){
            //empty
        }
    }

    /**
     * Sets the weapon in the spawnpoints
     */
    private void setWeaponSpawnpoint(){
        for (SpawnpointSquare t : spawnpointSquare)
            for (int i = 0; i < 3; i++)
                t.addSpawnpointWeapon(weapons.getFirstWeapon());
    }

    /**
     * Sets the ammo in the squares
     */
    private void setAmmoSquare(){
        for (Square t : squares) {
            if (t.getRoom() != -1) {
                t.setAmmo(ammoDeck.getFirstAmmo());
            }
        }
    }

    /**
     * Sets the dimension of the array of String for the killshot
     *
     * @param n     the length of the killshot track (the skulls number)
     */
    private static void setKillShotTrackRMX(int n){
        killShotTrackRMX = new String[n];
    }

    /**
     * Getter for the killshot track
     *
     * @return      an array of String, the killshot track
     */
    public static String[] getKillShotTrackRMX(){
        return killShotTrackRMX;
    }

    /**
     * Saves the killshot in the killshot track
     *
     * @param s     the killshot (kill or overkill)
     */
    public static void saveKillShot(String s){
        for (int i = 0; i < killShotTrackRMX.length; i++)
            if (killShotTrackRMX[i] == null) {
                killShotTrackRMX[i] = s;
                return;
            }
    }

    /**
     * Getter for the killshot track of the final frenzy
     *
     * @return      an array of String
     */
    public static String[] getKillShotFF(){
        return killShotFF;
    }

    /**
     * Saves the killshot in the killshot track of the final frenzy
     *
     * @param s     the killshot (kill or overkill)
     */
    public static void saveKillShotFF(String s){
        for (int i = 0; i < 6; i++){
            if (killShotFF[i] == null){
                killShotFF[i] = s;
                return;
            }
        }
    }

    /**
     * Getter for weapons
     *
     * @return      a WeaponDeck
     */
    public static WeaponDeck getWeapons() {
        return weapons;
    }

    /**
     * Getter for powerups
     *
     * @return      a PowerupDeck
     */
    public static PowerupDeck getPowerups() {
        return powerups;
    }

    /**
     * Getter for ammoDeck
     *
     * @return      an AmmoDeck
     */
    public static AmmoDeck getAmmoDeck() {
        return ammoDeck;
    }

    /**
     * Getter for discardedAmmo
     *
     * @return      an AmmoDeck
     */
    public static AmmoDeck getDiscardedAmmo() {
        return discardedAmmo;
    }

    /**
     * Getter for the deck of discarded powerups
     *
     * @return      the deck of discarded powerups
     */
    public static PowerupDeck getDiscardedPowerups(){
        return discardedPowerups;
    }

    /**
     * Takes the deck of discarded powerups, shuffles it and set the deck as the deck of powerups
     */
    public static void discardedToPowerup(){
        powerups.getPowerups().addAll(discardedPowerups.getPowerups());
        discardedPowerups.getPowerups().clear();
        powerups.shuffle();
    }

    /**
     * Getter for squares
     *
     * @return      an ArrayList of Square
     */
    public static List<Square> getSquares(){
        return squares;
    }

    /**
     * Getter for spawnpointSquare
     *
     * @return      an ArrayList of SpawnpointSquare
     */
    public static List<SpawnpointSquare> getSpawnpointSquares(){
        return spawnpointSquare;
    }

    /**
     * Setter for killShotTrack
     *
     * @param n     an int
     */
    private static void setKillShotTrack(int n){
        killShotTrack = n;
    }

    /**
     * Getter for killShotTrack
     *
     * @return      an int
     */
    public static int getKillShotTrack() {
        return killShotTrack;
    }

    /**
     * Removes one skull from the KillshotTrack of the BoardGUI if the number of skull is greater or equal to 0
     * Otherwise the game is over and throws the EndGameException.
     */
    public static void removeSkull(){
        try {
            killShotTrack--;
            positiveSkull();
        }catch (EndGameException eg){
            //empty
        }
    }

    /**
     * Returns the SpawnpointSquare of the given color
     *
     * @param c     a char representing the color of the spawnpoint we want to get
     * @return      the spawnpoint of the color given
     */
    public static SpawnpointSquare getSpawnpoint(char c){
        try {
            validSpawnColor(c);
            switch (c) {
                case 'b':
                    return spawnpointSquare.get(0);
                case 'r':
                    return spawnpointSquare.get(1);
                default:
                    return spawnpointSquare.get(2);
            }
        }catch (WrongValueException wv){ wv.wrong(); }
        return null;
    }

    /**
     * If the given int is a valid index returns the SpawnpointSquare corresponding to the index in the BoardGUI map
     * Otherwise, the method throws the WrongValueException
     *
     * @param n     int representing the index of the wanted spawnpoint in the board
     * @return      the SpawnpointSquare corresponding to the index given
     */
    public static SpawnpointSquare getSpawnpoint(int n){
        try {
            validSpawnNum(n);
            switch (n){
                case 2:
                    return spawnpointSquare.get(0);
                case 4:
                    return spawnpointSquare.get(1);
                default:
                    return spawnpointSquare.get(2);
            }
        }catch (WrongValueException wv){ wv.wrong(); }
        return null;
    }

    /**
     * Returns the Square in the position of the given index, if this value is a valid Square number
     *
     * @param n     a int correspoing to the index of the square
     * @return      the Square specified by the index
     */
    public static Square getSquare(int n){
        try {
            validSquareNum(n);
            switch (n){
                case 0:
                    return squares.get(0);
                case 1:
                    return squares.get(1);
                case 3:
                    return squares.get(2);
                case 5:
                    return squares.get(3);
                case 6:
                    return squares.get(4);
                case 7:
                    return squares.get(5);
                case 8:
                    return squares.get(6);
                case 9:
                    return squares.get(7);
                default:
                    return squares.get(8);
            }
        }catch (WrongValueException wv){
            wv.wrong();
        }
        return null;
    }

    /**
     * Checks if the second GenericSquare is visible by the first GenericSquare
     *
     * @param s1    a GenericSquare
     * @param s2    a GenericSquare
     * @return      an int that is the distance between the two squares or -1 if the second square is not visible by the first one
     */
    public static int isVisibleRun(GenericSquare s1, GenericSquare s2){
        ArrayList<GenericSquare> fifo1= new ArrayList<>();
        ArrayList<GenericSquare> fifo2 = new ArrayList<>();
        ArrayList<GenericSquare> fifo3 = new ArrayList<>();
        if (s1.getNum() == s2.getNum()) {
            return 0;
        }
        searchAndAddReachableRun(s1, fifo1);
        for (GenericSquare t : fifo1)
            if(t.getNum() == s2.getNum())
                return 1;
        for(GenericSquare t : fifo1)
            searchAndAddReachableRun(t, fifo2);
        for (GenericSquare t : fifo2)
            if(t.getNum() == s2.getNum())
                return 2;
        for(GenericSquare t : fifo2)
            searchAndAddReachableRun(t, fifo3);
        for (GenericSquare t : fifo3)
            if(t.getNum() == s2.getNum())
                return 3;
        return -1;
    }

    /**
     * Searches reachable squares considering the GenericSquare and adds them to the ArrayList
     *
     * @param t         a GenericSquare
     * @param temp      an ArrayList of GenericSquare
     */
    private static void searchAndAddReachableRun(GenericSquare t, List<GenericSquare> temp){
        for(int i = 0; i < 9; i++)
            if (t.isReachable(squares.get(i)))
                temp.add(squares.get(i));
        for (int i = 0; i < 3; i++)
            if(t.isReachable(spawnpointSquare.get(i)))
                temp.add(spawnpointSquare.get(i));
    }

    /**
     * Checks if one generic square is visible (shoot visibility) from another generic square
     *
     * @param s1        the generic square from which check the visibility
     * @param s2        the generic square that can be visible or not by the first
     * @return          the distance (max 3) between the two square if the second is visible by the first, -1 otherwise
     */
    public static int isVisibleShoot(GenericSquare s1, GenericSquare s2){
        if (s1.getRoom() == s2.getRoom()) {                 //if same room
            if (abs(s1.getNum() - s2.getNum()) == 4)
                return 1;
            else if (abs(s1.getNum() - s2.getNum()) == 3 || abs(s1.getNum() - s2.getNum()) == 5)
                return 2;
            return abs(s1.getNum() - s2.getNum());
        }
        ArrayList<GenericSquare> fifo1 = new ArrayList<>();
        ArrayList<GenericSquare> fifo2 = new ArrayList<>();
        ArrayList<GenericSquare> fifo3 = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            if (s1.isReachable(squares.get(i)) && s1.getRoom() != squares.get(i).getRoom()) {
                fifo1.add(squares.get(i));
            }
        for (int i = 0; i < 3; i++)
            if (s1.isReachable(spawnpointSquare.get(i)) && s1.getRoom() != spawnpointSquare.get(i).getRoom()) {
                fifo1.add(spawnpointSquare.get(i));
            }
        if (fifo1.isEmpty())
            return -1;
        if (fifo1.contains(s2))
            return 1;
        for (GenericSquare t : fifo1)
            searchAndAddReachableShoot(t, fifo2);
        if (fifo2.contains(s2))
            return 2;
        for (GenericSquare t : fifo2)
            searchAndAddReachableShoot(t, fifo3);
        if (fifo3.contains(s2))
            return 3;
        return -1;
    }

    /**
     * Searches all the generic squares that are reachable from a generic square
     *
     * @param t         the starting generic square
     * @param temp      the list of all generic square reachable from the starting one
     */
    private static void searchAndAddReachableShoot(GenericSquare t, List<GenericSquare> temp){
        for (int i = 0; i < 9; i++)
            if (t.getRoom() == squares.get(i).getRoom() && squares.get(i).getNum() != t.getNum() && t.isReachable(squares.get(i))) {
                temp.add(squares.get(i));
            }
        for (int i = 0; i < 3; i++)
            if (t.getRoom() == spawnpointSquare.get(i).getRoom() && spawnpointSquare.get(i).getNum() != t.getNum() && t.isReachable(spawnpointSquare.get(i))) {
                temp.add(spawnpointSquare.get(i));
            }
    }

    /**
     * Checks if the second GenericSquare is in a fixed cardinal direction and visible by the first GenericSquare
     *
     * @param s1    a GenericSquare
     * @param s2    a GenericSquare
     * @return      a boolean
     */
    public static boolean fixedDirectionReachable(GenericSquare s1, GenericSquare s2){
        return (isVisibleRun(s1, s2) != -1 && genericFixedDirection(s1, s2) != -1);
    }

    /**
     * Checks if the second GenericSquare is in a fixed cardinal direction considering the first one
     *
     * @param s1    a GenericSquare
     * @param s2    a GenericSquare
     * @return      an int for the distance, -1 if not in a fixed cardinal direction
     */
    public static int genericFixedDirection(GenericSquare s1, GenericSquare s2){
        try {
            GenericSquare.checkExistingSquare(s1);
            GenericSquare.checkExistingSquare(s2);
            int n = s1.getNum();
            int m = s2.getNum();
            if (n == m)
                return 0;
            else if (m == n+4 || m == n+8 || m == n-4 || m == n-8)
                return abs(m-n)/4;
            else if ((n < 4 && m < 4) || (n >= 4 && n < 8 && m >= 4 && m < 8) || (n >= 8 && m < 12))
                return abs(m-n);
        }catch (WrongSquareException ws){
            //empty
        }
        return -1;
    }

    /**
     * Searches all the squares of a room
     *
     * @param n     an int, the number of the room
     * @return      an ArrayList of GenericSquare
     */
    public static ArrayList<GenericSquare> squareOfARoom(int n){
        ArrayList<GenericSquare> temp = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            if (spawnpointSquare.get(i).getRoom() == n) {
                temp.add(spawnpointSquare.get(i));
            }
        for (int i = 0; i < 9; i++)
            if (squares.get(i).getRoom() == n) {
                temp.add(squares.get(i));
            }
        return temp;
    }

    /**
     * Replaces all the weapons grabbed from the spawnpoints with weapons from the weapons' deck
     */
    public static void replaceWeaponGrabbed(){
        for (SpawnpointSquare s : spawnpointSquare)
            for (int i = 0; i < 3; i++){
                if (s.getWeaponSpawnpoint().getWeapon(i) == null && !weapons.getWeapons().isEmpty())
                    s.getWeaponSpawnpoint().setWeapon(i, weapons.getFirstWeapon());
            }
    }

    /**
     * Replaces all the ammo tile grabbed from the squares with ammo from the ammo's deck
     */
    public static void replaceAmmoGrabbed(){
        for (Square s : squares)
            if (s.getAmmo() == null && s.getRoom() != -1)
                s.setAmmo(ammoDeck.getFirstAmmo());
    }

    /**
     * Initializes all the weapons
     */
    private void initWeapon(){
        WeaponFactory wf = new WeaponFactory("lock rifle");
        weapons.addWeapon(new Weapon("lock rifle", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("electroscythe");
        weapons.addWeapon(new Weapon("electroscythe", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("machine gun");
        weapons.addWeapon(new Weapon("machine gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("tractor beam");
        weapons.addWeapon(new Weapon("tractor beam", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("t.h.o.r.");
        weapons.addWeapon(new Weapon("t.h.o.r.", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("vortex cannon");
        weapons.addWeapon(new Weapon("vortex cannon", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("furnace");
        weapons.addWeapon(new Weapon("furnace", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("plasma gun");
        weapons.addWeapon(new Weapon("plasma gun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("heatseeker");
        weapons.addWeapon(new Weapon("heatseeker", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("whisper");
        weapons.addWeapon(new Weapon("whisper", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("hellion");
        weapons.addWeapon(new Weapon("hellion", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("flamethrower");
        weapons.addWeapon(new Weapon("flamethrower", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("zx-2");
        weapons.addWeapon(new Weapon("zx-2", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("grenade launcher");
        weapons.addWeapon(new Weapon("grenade launcher", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("shotgun");
        weapons.addWeapon(new Weapon("shotgun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("rocket launcher");
        weapons.addWeapon(new Weapon("rocket launcher", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("power glove");
        weapons.addWeapon(new Weapon("power glove", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("railgun");
        weapons.addWeapon(new Weapon("railgun", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("shockwave");
        weapons.addWeapon(new Weapon("shockwave", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("cyberblade");
        weapons.addWeapon(new Weapon("cyberblade", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        wf = new WeaponFactory("sledgehammer");
        weapons.addWeapon(new Weapon("sledgehammer", wf.getBooleans(), wf.getCosts(), wf.getRequestedNum(), wf.getApplier(), wf.getEffects(), wf.getDescriptions()));
        weapons.shuffle();
    }

    /**
     * Initializes all the powerups
     */
    private void initPowerup(){
        String ts = "targeting scope";
        String n = "newton";
        String tg = "tagback grenade";
        String t = "teleporter";
        addTwoPowerups(ts, 'b');
        addTwoPowerups(ts, 'r');
        addTwoPowerups(ts, 'y');
        addTwoPowerups(n, 'b');
        addTwoPowerups(n, 'r');
        addTwoPowerups(n, 'y');
        addTwoPowerups(tg, 'b');
        addTwoPowerups(tg, 'r');
        addTwoPowerups(tg, 'y');
        addTwoPowerups(t, 'b');
        addTwoPowerups(t, 'r');
        addTwoPowerups(t, 'y');
        powerups.shuffle();
    }

    /**
     * Adds two powerups to the powerups deck
     *
     * @param n     a String, the name of the powerup
     * @param c     a char, the color of the powerup
     */
    private void addTwoPowerups(String n, char c){
        PowerupFactory pf = new PowerupFactory(n);
        powerups.addPowerup(new Powerup(n, c, pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier()));
        powerups.addPowerup(new Powerup(n, c, pf.isPay1(), pf.isAlwaysUsable(), pf.isVictim(), pf.isSquare(), pf.getEffect(), pf.getDescription(), pf.getApplier()));
    }

    /**
     * Initializes all the ammo tile, adds them to the ammo deck and shuffles it
     */
    private void initAmmo(){
        createNAmmo(3, 2, 0, 1, false);
        createNAmmo(3, 0, 2, 1, false);
        createNAmmo(3, 2, 1, 0, false);
        createNAmmo(3, 0, 1, 2, false);
        createNAmmo(3, 1, 0, 2, false);
        createNAmmo(3, 1, 2, 0, false);
        createNAmmo(2, 0, 0, 2, true);
        createNAmmo(2, 0, 2, 0, true);
        createNAmmo(2, 2, 0, 0, true);
        createNAmmo(4, 0, 1, 1, true);
        createNAmmo(4, 1, 0, 1, true);
        createNAmmo(4, 1, 1, 0, true);
        ammoDeck.shuffle();
    }

    /**
     * Creates a number of ammo tile identical
     *
     * @param num       the number of ammo tile to create
     * @param b         the number of blue ammo in the ammo tile
     * @param r         the number of red ammo in the ammo tile
     * @param y         the number of yellow ammo in the ammo tile
     * @param p         a flag that indicates whether the ammo tile allows the player to draw a powerup or not
     */
    private void createNAmmo(int num, int b, int r, int y, boolean p){
        for (int i = 0; i < num; i++)
            ammoDeck.addAmmoTile(new AmmoTile(b, r, y, p));
    }

    /**
     * Takes the deck of discarded ammo, shuffles it and sets it as deck of ammo
     */
    public static void discardedToAmmo(){
        ammoDeck.getAmmo().addAll(discardedAmmo.getAmmo());
        discardedAmmo.getAmmo().clear();
        ammoDeck.shuffle();
    }

    /**
     * Checks if a color is valid for a Spawnpoint
     *
     * @param c                         a char for the color
     * @throws WrongValueException      if the color is not valid
     */
    private static void validSpawnColor(char c) throws WrongValueException {
        if(!(c=='b' || c=='r' || c=='y'))
            throw new WrongValueException();
    }

    /**
     * Checks if a number is valid for a Spawnpoint
     *
     * @param n                         an int
     * @throws WrongValueException      if the number is not valid
     */
    private static void validSpawnNum(int n) throws WrongValueException {
        if(!(n==2 || n==4 || n==11))
            throw new WrongValueException();
    }

    /**
     * Checks if a number is valid for a Square
     *
     * @param n                         an int
     * @throws WrongValueException      if the number is not valid
     */
    private static void validSquareNum(int n) throws WrongValueException {
        if (!(n==0 || n==1 || n==3 || n==5 || n==6 || n==7 || n==8 || n==9 || n==10))
            throw new WrongValueException();
    }

    /**
     * Checks if a number is valid as number of skulls
     *
     * @param n                         an int
     * @throws WrongValueException      if the number is not valid
     */
    private void validSkull(int n) throws WrongValueException {
        if (n < 5 || n > 8)
            throw new WrongValueException();
    }

    /**
     * Checks if the skulls on the board are finished
     *
     * @throws EndGameException     if the skulls are finished
     */
    private static void positiveSkull() throws EndGameException {
        if (killShotTrack <= 0)
            throw new EndGameException();
    }

}
