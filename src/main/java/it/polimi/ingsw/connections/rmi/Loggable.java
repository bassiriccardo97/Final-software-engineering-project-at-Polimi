package it.polimi.ingsw.connections.rmi;

import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.connections.User;
import it.polimi.ingsw.model.clientmodel.ClientModel;
import it.polimi.ingsw.view.cli.Map;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface Loggable extends Remote {

    boolean login(String nick, boolean guiInterface) throws RemoteException;

    //CREATE

    void createPlayer(String nick) throws RemoteException;

    //void createGame() throws RemoteException;

    //CHECK

    boolean checkIfComplete() throws RemoteException;

    List<String> availableColors() throws RemoteException;

    boolean isFirstPlayer(String nick) throws RemoteException;

    boolean isTurn(String username) throws RemoteException;

    //MESSAGE

    void callback(String string, Callback client) throws RemoteException;

    void sendModel(Callback client) throws RemoteException;

    void clearCliRMI(Callback client) throws RemoteException;

    void reply(String line) throws RemoteException;

    //boolean firstRMI() throws RemoteException;

    //REGISTER

    void addClient(String name, Callback client) throws RemoteException;

    void removeClient(String name) throws RemoteException;

    //GETTER

    int getPortClient() throws RemoteException;

    boolean getGameStarted() throws RemoteException;

    ArrayList<String> getUsersRMI() throws RemoteException;

    User getUser(String name) throws RemoteException;

    int getBoard() throws RemoteException;

    Map getMapRMI() throws RemoteException;

    boolean getGameRunning() throws RemoteException;

    //ArrayList<String> getSpawnpointWeapons(char color) throws RemoteException;

    //ArrayList<String> weaponHand(String name) throws RemoteException;

    //ArrayList<String> powerupHand(String name) throws RemoteException;

    //ArrayList<String> weaponHandDescription(String playerName, String cardName) throws RemoteException;

    //ArrayList<String> weaponBoardDescription(String name) throws RemoteException;

    //String powerupHandDescription(String playerName, String cardName) throws RemoteException;

    boolean getTerminator() throws RemoteException;

    ClientModel getModel() throws RemoteException;

    //SETTER

    void setColor(String name, String color) throws RemoteException;

    void setReady(String name) throws RemoteException;

    void setTerminatorColor(String color) throws RemoteException;

    //void setTerminatorSquare(String color) throws RemoteException;

    void setSkulls(int n) throws RemoteException;

    void setBoard(int n) throws RemoteException;

    //ArrayList<String> getWeaponHand(String name) throws RemoteException;

    //ArrayList<String> getPowerupHand(String name) throws RemoteException;

    //ArrayList<String> getAmmoBoard() throws RemoteException;

    int getInitSkulls() throws RemoteException;

    int getSkulls() throws RemoteException;

    String[] getKillshotTrack() throws RemoteException;

    Callback getCallbackClient(String name) throws RemoteException;
}
