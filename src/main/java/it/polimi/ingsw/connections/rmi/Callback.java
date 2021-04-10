package it.polimi.ingsw.connections.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote {

    void callback(String string) throws RemoteException;

    void boardGUI() throws RemoteException;

    void getModelCallback() throws RemoteException;

    void refreshCLI() throws RemoteException;

    boolean waitRefresh() throws RemoteException;

    boolean pingRMI() throws RemoteException;
}
