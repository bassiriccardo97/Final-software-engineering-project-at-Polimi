package it.polimi.ingsw.model.clientmodel;

import it.polimi.ingsw.view.cli.Map;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientModel implements Serializable {

    private ArrayList<RealPlayerClientModel> players;
    private TerminatorClientModel terminatorClientModel;
    private ArrayList<WeaponClient> allWeapons;
    private ArrayList<PowerupClient> allPowerups;
    private Map map;
    private boolean terminator;
    private int initSkulls;
    private int reaminingSkulls;
    private String[] killShotTrack;
    private String[] killShotTrackFF = new String[6];
    private int[] playersPoint = {0, 0, 0, 0, 0};
    private boolean discardedAmmo = false;
    private boolean ammo = true;
    private boolean powerup = true;
    private boolean weapon = true;
    private boolean finalFrenzy = false;
    private ArrayList<String> winners = new ArrayList<>();

    /**
     * Getter for one player in the ArrayList of players, according to its name
     *
     * @param name      a String
     * @return          a RealPlayerClientModel
     */
    public RealPlayerClientModel getPlayer(String name) {
        for(RealPlayerClientModel p: players) {
            if(p.getPlayerName().equals(name))
                return p;
        }
        return null;
    }

    //GETTER

    /**
     * Getter for the ArrayList of players
     *
     * @return      an ArrayList of RealPlayerClientModel
     */
    public ArrayList<RealPlayerClientModel> getPlayers() {
        return players;
    }

    /**
     * Getter for the map
     *
     * @return      the Map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Getter for terminator, that indicates whether the game has the terminator or not
     *
     * @return      a boolean
     */
    public boolean hasTerminator(){
        return terminator;
    }

    public ArrayList<WeaponClient> getAllWeapons() {
        return allWeapons;
    }

    public ArrayList<PowerupClient> getAllPowerups() {
        return allPowerups;
    }

    /**
     * Getter for a weapon in the list of all weapons, according to its name
     *
     * @param name  a String
     * @return      a WeaponClient
     */
    public WeaponClient getWeaponClient(String name){
        for (WeaponClient w : allWeapons)
            if (w.getName().equals(name))
                return w;
        return null;
    }

    /**
     * Getter for a powerup in the list of all powerupd, according to its name
     *
     * @param name      a String
     * @param color     a char
     * @return          a PowerupClient
     */
    public PowerupClient getPowerupClient(String name, char color){
        for (PowerupClient p : allPowerups)
            if (p.getName().equals(name) && p.getColor() == color)
                return p;
        return null;
    }

    /**
     * Getter for the number of initial skulls
     *
     * @return      an int
     */
    public int getInitSkulls() {
        return initSkulls;
    }

    /**
     * Getter for the number of remaining skulls
     *
     * @return      an int
     */
    public int getReaminingSkulls() {
        return reaminingSkulls;
    }

    /**
     * Getter for the killshot track
     *
     * @return      an array of String
     */
    public String[] getKillShotTrack() {
        return killShotTrack;
    }

    /**
     * Getter for the terminator
     *
     * @return      the TerminatorClientModel
     */
    public TerminatorClientModel getTerminatorClientModel() {
        return terminatorClientModel;
    }

    /**
     * Getter for discardedAmmo, that indicates whether the deck of discarded ammo has almost one ammo tile or is empty
     *
     * @return      a boolean
     */
    public boolean isDiscardedAmmo() {
        return discardedAmmo;
    }

    /**
     * Getter for ammo, that indicates whether the deck of ammo has almost one ammo tile or is empty
     *
     * @return      a boolean
     */
    public boolean isAmmo() {
        return ammo;
    }

    /**
     * Getter for powerup, that indicates whether the deck of powerup has almost one powerup or is empty
     *
     * @return      a boolean
     */
    public boolean isPowerup() {
        return powerup;
    }

    /**
     * Getter for weapon, that indicates whether the deck of weapon has almost one weapon or is empty
     *
     * @return      a boolean
     */
    public boolean isWeapon() {
        return weapon;
    }

    /**
     * Getter for the points of each player
     *
     * @return      an array of int
     */
    public int[] getPlayersPoint() {
        return playersPoint;
    }

    /**
     * Getter for the killshot track of the final frenzy
     *
     * @return      an array of String
     */
    public String[] getKillShotTrackFF() {
        return killShotTrackFF;
    }

    /**
     * Getter for finalFrenzy, that indicates whether final frenzy is started or not
     *
     * @return      a boolean
     */
    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * Getter for the ArrayList of winners
     *
     * @return      an ArrayList
     */
    public ArrayList<String> getWinners() {
        return winners;
    }

    //SETTER

    /**
     * Setter for the ArrayList of players
     *
     * @param players       the ArrayList of RealPlayerClientModel
     */
    public void setPlayers(ArrayList<RealPlayerClientModel> players) {
        this.players = players;
    }

    /**
     * Setter for the map
     *
     * @param map       the Map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Setter for terminator, whether there is the terminator or not
     *
     * @param t     a boolean
     */
    public void setTerminator(boolean t){
        terminator = t;
    }

    /**
     * Setter for the list of all the weapons
     *
     * @param allWeapons        an ArrayList of WeaponClient
     */
    public void setAllWeapons(ArrayList<WeaponClient> allWeapons) {
        this.allWeapons = allWeapons;
    }

    /**
     * Setter for the list of all the powerups
     *
     * @param allPowerups       an ArrayList of PowerupClient
     */
    public void setAllPowerups(ArrayList<PowerupClient> allPowerups) {
        this.allPowerups = allPowerups;
    }

    /**
     * Setter for the number of the initial skulls
     *
     * @param initSkulls        an int
     */
    public void setInitSkulls(int initSkulls) {
        this.initSkulls = initSkulls;
    }

    /**
     * Setter for the number of remaining skulls
     *
     * @param reaminingSkulls       an int
     */
    public void setReaminingSkulls(int reaminingSkulls) {
        this.reaminingSkulls = reaminingSkulls;
    }

    /**
     * Setter for the killshot track
     *
     * @param killShotTrack     an array of String
     */
    public void setKillShotTrack(String[] killShotTrack) {
        this.killShotTrack = killShotTrack;
    }

    /**
     * Setter for the terminator
     *
     * @param terminatorClientModel     the TerminatorClientModel
     */
    public void setTerminatorClientModel(TerminatorClientModel terminatorClientModel) {
        this.terminatorClientModel = terminatorClientModel;
    }

    /**
     * Setter for discardedAmmo, that indicates whether the deck of discarded ammo has almost one ammo tile or is empty
     *
     * @param discardedAmmo     a boolean
     */
    public void setDiscardedAmmo(boolean discardedAmmo) {
        this.discardedAmmo = discardedAmmo;
    }

    /**
     * Setter for ammo, that indicates whether the deck of ammo has almost one ammo tile or is empty
     *
     * @param ammo      a boolean
     */
    public void setAmmo(boolean ammo) {
        this.ammo = ammo;
    }

    /**
     * Setter for powerup, that indicates whether the deck of powerups has almost one powerup or is empty
     *
     * @param powerup       a boolean
     */
    public void setPowerup(boolean powerup) {
        this.powerup = powerup;
    }

    /**
     * Setter for weapon, that indicates whether the deck of weapons has almost one weapon or is empty
     *
     * @param weapon        a boolean
     */
    public void setWeapon(boolean weapon) {
        this.weapon = weapon;
    }

    /**
     * Setter for the points of each player
     *
     * @param playersPoint      an array of int
     */
    public void setPlayersPoint(int[] playersPoint) {
        this.playersPoint = playersPoint;
    }

    /**
     * Setter for the killshot track of final frenzy
     *
     * @param killShotTrackFF       an array of String
     */
    public void setKillShotTrackFF(String[] killShotTrackFF) {
        this.killShotTrackFF = killShotTrackFF;
    }

    /**
     * Setter for finalFrenzy, that indicates whether final frenzy is started or not
     *
     * @param finalFrenzy       a boolean
     */
    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    /**
     * Setter for the winners ArrayList
     *
     * @param winners       an ArrayList
     */
    public void setWinners(ArrayList<String> winners) {
        this.winners = winners;
    }
}
