package it.polimi.ingsw.connections.socket;

import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.connections.Server;
import it.polimi.ingsw.connections.User;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketHandler extends Server implements Runnable {

    private Server server;

    public SocketHandler (Server server) {
        this.server = server;
    }

    /**
     * Handles all incoming socket connections and accept them if the game isn't already full or started, or in case it's
     * already started and there are disconnected users makes them log back in
     */

    public void run() {
        Connection connection = null;
        User client = null;
        while(true) {
            try {
                Socket clientSocket = server.getServerSocket().accept();
                System.out.println("Socket connection added");
                if (!isGameRunning() || getClients().size() > 4)
                    client = new User(false, server.isFirst(), false);
                ExecutorService executor = Executors.newCachedThreadPool();
                connection = new Connection(clientSocket, client);
                executor.submit(connection);
                if(!isGameRunning() && getClients().size() > 4) {
                    connection.sendMessage("The game is already full",false);
                    connection.sendMessage("Closing connection",false);
                    connection.closeConnection();
                    break;
                } else if(isGameRunning() && !checkIfSomeoneDisconnected()) {
                    connection.sendMessage("The game is already started and nobody is disconnected", false);
                }else if(isGameRunning() && checkIfSomeoneDisconnected()) {
                    connection.logBack();
                }else {
                    client.setConnection(connection);
                    server.getClients().add(client);

                    setFirst(false);
                    System.out.print("Number of connections: " + server.getClients().size());
                    System.out.println(" ");
                }

            } catch(IOException e) {
                break;
            }
        }
    }


}
