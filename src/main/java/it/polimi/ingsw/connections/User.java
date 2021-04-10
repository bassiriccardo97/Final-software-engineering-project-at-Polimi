package it.polimi.ingsw.connections;

import it.polimi.ingsw.connections.socket.Connection;
import it.polimi.ingsw.model.player.RealPlayer;

import java.io.Serializable;

public class User implements Serializable {

    private String playerName;
    private char color;
    private boolean rmiConnection = false;
    private boolean guiInterface = false;
    private Connection connection;
    private boolean disconnected = false;
    private boolean isFirst;
    private boolean ready = false;
    private RealPlayer player;
    private boolean turnOver;

    /**
     * Create a user and sets if the client is using RMI or Socket connection, and also the type of interface
     *
     * @param rmiConnection     true if the client is using RMI, false if using Socket
     * @param isFirst           true if is the first client to join the server
     * @param gui               true if the client is using GUI interface, false if using CLI
     */

    public User(boolean rmiConnection, boolean isFirst, boolean gui) {
        this.rmiConnection = rmiConnection;
        this.isFirst = isFirst;
        this.guiInterface = gui;
    }

    //GETTER

    public boolean isGuiInterface() {
        return guiInterface;
    }

    public String getPlayerName() {
        return playerName;
    }

    public char getColor() {
        return color;
    }

    public boolean isRmiConnection() {
        return rmiConnection;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public boolean isReady() {
        return ready;
    }

    public RealPlayer getPlayer() {
        return player;
    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public boolean isTurnOver() {
        return turnOver;
    }

    //SETTER

    public void setGuiInterface(boolean gui) {
        guiInterface = gui;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public void setRmiConnection(boolean rmiConnection) {
        this.rmiConnection = rmiConnection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setPlayer(RealPlayer player) {
        this.player = player;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public void setTurnOver(boolean turnOver) {
        this.turnOver = turnOver;
    }

}
