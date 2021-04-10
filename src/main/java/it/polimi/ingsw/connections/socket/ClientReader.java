package it.polimi.ingsw.connections.socket;

import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.connections.Server;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientReader implements Runnable {

    private PrintWriter socketOut; // = null
    private Client client; // = null
    private String fromGui;

    public ClientReader(PrintWriter socketOut) {
        this.socketOut = socketOut;
        //this.client = client;
    }

    public void run() {
        if(!Client.getGuiInterface()) {
            Scanner stdin = new Scanner(System.in);
            boolean flag = true;
            while (flag) {
                String inputLine = stdin.nextLine();
                if (inputLine.equals("QUIT")) {
                    System.out.println("Connection Stopped");
                    Client.stopConnection();
                    Thread.currentThread().interrupt();
                } else                                              //Turn check to be added
                    socketOut.println(inputLine);                   //\n check to be added
                    socketOut.flush();

            }
        } else {

            socketOut.println();
            socketOut.flush();
        }

    }


}
