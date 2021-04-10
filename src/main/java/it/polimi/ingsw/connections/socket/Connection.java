package it.polimi.ingsw.connections.socket;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.model.card.ammotile.AmmoTile;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.cli.Color;
import it.polimi.ingsw.view.cli.Map;
import it.polimi.ingsw.connections.Server;
import it.polimi.ingsw.connections.User;
import it.polimi.ingsw.model.player.RealPlayer;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.*;

public class Connection extends Server implements Runnable, Serializable {

    private static final String ERRORSTRING = "Wrong choice, please try again";
    private static final String CHOICESTRING = "Please, type your choice:";
    private static final String TRYAGAIN = "Please, try again: ";
    private static final int NAMELENGTH = 10;
    private static final String CNV = "Choice not valid";
    private static final String CONFIRM = "CONFIRM";
    private static final String REFUSE = "REFUSED";

    private Socket clientSocket;
    //private static Server server;
    private String playerName;
    private char color;
    private boolean yourTurn = false;
    private boolean guiInterface = false;
    private Scanner input;
    private PrintWriter out;
    private User user;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean turnOver = false;
    private boolean colorSended;


    public Connection(Socket clientSocket, User user) {
        this.clientSocket = clientSocket;
        this.user = user;
    }

    /**
     * Handles login phase for TCP client and after that keeps listening for client replies
     */

    public void run() {
        try {

            input = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream());
            //ping da inserire

            setInterface();
            // check if game is already started and nobody disconnected-> disconnect
            if(!isGameRunning()) {
                if (!checkIfCompleteSocket() && guiInterface) {
                    sendMessage("USERS", false);
                    Gson gson = new Gson();
                    String jsonUsers = gson.toJson(getUserList());
                    sendMessage(jsonUsers, false);
                    sendMessage("PLAYERNAME", false);
                    choosePlayerName();
                } else if (checkIfCompleteSocket() && guiInterface) {
                    sendMessage("COMPLETE", false);
                }
                if (!guiInterface)
                    choosePlayerName();
                if (guiInterface)
                    sendMessage("COLOR_CHOICE", false);
                chooseColor();
                user.setReady(true);
                sendMessage("User ready for the game: " + user.isReady(), false);

                clearCLI();
                sendMessage("Waiting for the Game to start", false);
            } else if(isGameRunning() && checkIfSomeoneDisconnected()) {
                choosePlayerName();
                getClient(playerName).setDisconnected(false);
                getClient(playerName).setConnection(this);
            }

            while(true) {

            }

        } catch (SocketTimeoutException e){
            System.out.println("Client timed out");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setInterface() {
        try {
            input = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream());
            String line = sendMessage("INTERFACE", true);
            if(line.equals("GUI")) {
                guiInterface = true;
            } else if (line.equals("CLI")){
                guiInterface = false;
            }
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return;
    }

    public void choosePlayerName() {
        if(!guiInterface) {
            clearCLI();
            out.println(" ");
        }
        if(!isGameRunning()) {
            while (true) {
                if (guiInterface && !getUserList().isEmpty()) {
                    sendMessage("USERS", false);
                    Gson gson = new Gson();
                    String jsonUsers = gson.toJson(getUserList());
                    sendMessage(jsonUsers, false);
                }
                if (!guiInterface)
                    sendMessage("Choose player name:", false);
                String line = getSocketMessage();
                while (line == null)
                    line = getSocketMessage();
                if (isValidName(line) && line.length() != 0 && line.length() <= NAMELENGTH) {
                    playerName = line;
                    user.setPlayerName(line);
                    user.setGuiInterface(guiInterface);
                    System.out.println(getClient(playerName).getPlayerName());
                    System.out.println("User added: " + playerName);
                    if (!guiInterface) {
                        sendMessage("PLAYERNAME", false);
                        sendMessage(getPlayerName(), false);
                        sendMessage("Player name added", false);
                    } else {
                        sendMessage(CONFIRM, false);
                    }
                    break;
                } else if (line.length() == 0 || line.length() > 10) {
                    if (!guiInterface) {
                        clearCLI();
                        sendMessage("Username not valid or too long", false);
                        sendMessage(TRYAGAIN, false);
                    } else {
                        sendMessage(REFUSE, false);
                        sendMessage("USERS", false);
                        Gson gson = new Gson();
                        String jsonUsers = gson.toJson(getUserList());
                        sendMessage(jsonUsers, false);
                    }
                } else {
                    if (!guiInterface) {
                        clearCLI();
                        sendMessage("Username already taken", false);
                        sendMessage(TRYAGAIN, false);
                    } else {
                        sendMessage(REFUSE, false);
                        sendMessage("USERS", false);
                        Gson gson = new Gson();
                        String jsonUsers = gson.toJson(getUserList());
                        sendMessage(jsonUsers, false);
                    }
                }
            }
        } else if(isGameRunning() && checkIfSomeoneDisconnected()) {
            while (true) {
                if (guiInterface && !getUserList().isEmpty()) {
                    sendMessage("USERS", false);
                    Gson gson = new Gson();
                    String jsonUsers = gson.toJson(getUserList());
                    sendMessage(jsonUsers, false);
                }
                if (!guiInterface)
                    sendMessage("Choose player name:", false);
                String line = getSocketMessage();
                while (line == null)
                    line = getSocketMessage();
                if (isValidName(line) && line.length() != 0 && line.length() <= NAMELENGTH && isValidDisconnected(line)) {
                    playerName = line;
                    user.setGuiInterface(guiInterface);
                    System.out.println(getClient(playerName).getPlayerName());
                    System.out.println("User added: " + playerName);
                    if (!guiInterface) {
                        sendMessage("PLAYERNAME", false);
                        sendMessage(getPlayerName(), false);
                        sendMessage("Player name added", false);
                    } else {
                        sendMessage(CONFIRM, false);
                    }
                    break;
                } else if (line.length() == 0 || line.length() > 10) {
                    if (!guiInterface) {
                        clearCLI();
                        sendMessage("Username not valid or too long", false);
                        sendMessage(TRYAGAIN, false);
                    } else {
                        sendMessage(REFUSE, false);
                        sendMessage("USERS", false);
                        Gson gson = new Gson();
                        String jsonUsers = gson.toJson(getUserList());
                        sendMessage(jsonUsers, false);
                    }
                } else {
                    if (!guiInterface) {
                        clearCLI();
                        sendMessage("Username already taken", false);
                        sendMessage(TRYAGAIN, false);
                    } else {
                        sendMessage(REFUSE, false);
                        sendMessage("USERS", false);
                        Gson gson = new Gson();
                        String jsonUsers = gson.toJson(getUserList());
                        sendMessage(jsonUsers, false);
                    }
                }
            }
        }
    }

    private boolean isValidDisconnected(String name) {
        for(User u: getClients()){
            if(u.getPlayerName().equals(name))
                if(u.isDisconnected())
                    return true;
                else
                    return false;
        }
        return false;
    }

    private void chooseColor() {
        if(!guiInterface)
            clearCLI();
        while (true) {
            if(!guiInterface) {
                out.print("Choose color between: ");
                out.flush();
                if (!colorAlreadyTaken('b')) {
                    out.print("blue ");
                }
                if (!colorAlreadyTaken('e')) {
                    out.print("emerald ");
                }
                if (!colorAlreadyTaken('g')) {
                    out.print("grey ");
                }
                if (!colorAlreadyTaken('v')) {
                    out.print("violet ");
                }
                if (!colorAlreadyTaken('y')) {
                    out.print("yellow ");
                }
                out.println(" ");
                out.flush();
            } else {
                out.println("COLOR");
                out.flush();
                Gson gson = new Gson();
                String jsonString = gson.toJson(getAvailableColors());
                //System.out.println(getAvailableColors());
                //System.out.println(jsonString);
                out.println(jsonString);
                out.flush();
            }
            //if (!guiInterface)
            sendMessage(CHOICESTRING, false);
            String line = getSocketMessage();
            //System.out.println(line);
            if (isValidColor(line) && !colorAlreadyTaken(line.charAt(0))) {
                System.out.println(colorAlreadyTaken(line.charAt(0)));
                color = line.charAt(0);
                user.setColor(line.charAt(0));
                if(!guiInterface) {
                    sendMessage(line + " selected", false);
                }
                RealPlayer t = new RealPlayer(color, playerName);
                User c = getClient(playerName);
                System.out.println("player created " + t.getName() + " " + t.getColor());
                c.setPlayer(t);
                c.setColor(line.charAt(0));
                if(guiInterface) {
                    sendMessage("WAIT_START", false);
                }
                break;
            } else if (isValidColor(line) && colorAlreadyTaken(line.charAt(0))) {
                if(!guiInterface) {
                    sendMessage("Color already taken", false);
                    sendMessage(TRYAGAIN, false);
                } else {
                    out.println("COLOR");
                    out.flush();
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(getAvailableColors());
                    //System.out.println(getAvailableColors());
                    //System.out.println(jsonString);
                    out.println(jsonString);
                    out.flush();
                    sendMessage("WRONG", false);
                }
            } else {
                if(!guiInterface) {
                    sendMessage("Color not valid", false);
                    sendMessage(TRYAGAIN, false);
                } else {
                    out.println("COLOR");
                    out.flush();
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(getAvailableColors());
                    //System.out.println(getAvailableColors());
                    //System.out.println(jsonString);
                    out.println(jsonString);
                    out.flush();
                    sendMessage("WRONG", false);
                }
            }

        }
    }

    public void logBack() {
        if(!guiInterface) {
            clearCLI();
            out.println(" ");
        }
        while (true) {
            if(guiInterface && !getUserList().isEmpty()) {
                sendMessage("USERS", false);
                Gson gson = new Gson();
                String jsonUsers = gson.toJson(getUserList());
                sendMessage(jsonUsers, false);
            }
            if(!guiInterface)
                sendMessage("Choose player name to log back in the Game:", false);
            String line = getSocketMessage();
            while (line == null)
                line = getSocketMessage();
            if (isValidName(line) && line.length() != 0 && line.length() <= NAMELENGTH && (getClient(line) != null && getClient(line).isDisconnected())) {
                playerName = line;
                user.setGuiInterface(guiInterface);
                user.setConnection(this);
                System.out.println("User logged back: " + playerName);
                if(!guiInterface) {
                    sendMessage("PLAYERNAME", false);
                    sendMessage(getPlayerName(),false);
                    sendMessage("Player name added", false);
                } else {
                    sendMessage(CONFIRM, false);
                }
                break;
            } else if (line.length() == 0 || line.length() > 10) {
                if(!guiInterface) {
                    clearCLI();
                    sendMessage("Username not valid or too long", false);
                    sendMessage(TRYAGAIN, false);
                } else {
                    sendMessage(REFUSE, false);
                    sendMessage("USERS", false);
                    Gson gson = new Gson();
                    String jsonUsers = gson.toJson(getUserList());
                    sendMessage(jsonUsers, false);
                }
            } else if((getClient(line) != null && getClient(line).isDisconnected())){
                if(!guiInterface) {
                    clearCLI();
                    sendMessage("The player is not disconnected", false);
                    sendMessage(TRYAGAIN, false);
                } else {
                    sendMessage(REFUSE, false);
                    sendMessage("USERS", false);
                    Gson gson = new Gson();
                    String jsonUsers = gson.toJson(getUserList());
                    sendMessage(jsonUsers, false);
                }
            }
        }
    }

    public void setColorSended(boolean colorSended){
        this.colorSended = colorSended;
    }

    public void closeConnection() {
        Thread.currentThread().interrupt();
    }

    //PING

    public synchronized boolean ping() {
        if (colorSended)
            return true;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Boolean> future = executor.submit(new Callable() {
            @Override
            public Boolean call() throws Exception {
                String line = sendMessage("PING" , true);
                //System.out.println(line + " " + user.getPlayerName());
                if(line.equals("PONG"))
                    return true;
                return false;
            }
        });

        try {
            future.get(4000, TimeUnit.MILLISECONDS);
            return true;
        } catch (ExecutionException e) {
            System.err.println(e.getMessage());
        } catch (TimeoutException e) {
            if(isGameRunning())
                getClient(playerName).setDisconnected(true);
            System.out.println("User " + playerName + " disconnected");
            future.cancel(true);
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return true;
    }

    //MESSAGE METHODS

    private void clearCLI() {
        out.println("\033[H\033[2J");
    }

    public void refreshCLI() {
        out.println("REFRESH");
        out.flush();
    }

    /**
     * Sends a message and if an answer is needed waits for the client to responds and return the string
     *
     * @param s             string to send to the player
     * @param needAnswer    true if the client has to reply
     * @return              return the string the player sends, null if no answer needed
     */

    public String sendMessage(String s, boolean needAnswer) {
        try {
            Scanner input = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            out.println(s);
            out.flush();
            String line = null;
            if(needAnswer) {
                if(getMapChoice() != 0)
                    clientSocket.setSoTimeout(getReplyTimer()*1000);
                line = input.nextLine();
                while(line.length() == 0 && user.getPlayer().getTurn() || user.isTurnOver()) {
                    if(line.length() == 0) {
                        out.println("Choice not valid");
                        out.flush();
                    }
                    if(getMapChoice() != 0)
                        clientSocket.setSoTimeout(getReplyTimer()*1000);
                    line = input.nextLine();
                }
            }
            return line;
        } catch(IOException | NoSuchElementException e) {
            user.setDisconnected(true);
        }
        return "Failed to get answer";
    }

    public String getSocketMessage() throws NoSuchElementException {
        String line = input.nextLine();
        //QUIT removes the closed connection from clients list on server
        if(line.equals("QUIT")) {
            removeConnection(this);
            Thread.currentThread().interrupt();
            return null;
        }
        return line;
    }

    private void removeConnection(Connection connection) {
        getClients().remove(getClientBySocket(connection));
        return;
    }

    //MODEL

    /**
     * Sends the client version of the model to the client
     */

    public synchronized void sendModel() {
        out.println("MODEL");
        out.flush();
        Gson gson = new Gson();
        String jsonInString = gson.toJson(getClientModel());
        out.println(jsonInString);
        out.flush();
        out.println("UPDATE");
        out.flush();
        if (user.getPlayer().getTurn()) {
            String reply = "WAIT";
            while (reply.equals("WAIT") && !reply.equals("CONTINUE"))
                reply = messageTo(getTurnClient().getColor(), "CONTINUE", true);
        }
    }

    private ArrayList<String> getUserList() {
        ArrayList<String> tempUsers = new ArrayList<>();
        for (User u: getClients()) {
            if (u.getPlayerName() == null)
                break;
            tempUsers.add(u.getPlayerName() + " " + u.getColor());
        }
        return tempUsers;
    }

    //VALID

    private boolean isValidName(String s) {
        for (User u : getClients()) {
            if (s.equals(u.getPlayerName())) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidColor(String s) {
        return !(!s.equals("blue") && !s.equals("violet") && !s.equals("yellow") && !s.equals("grey") && !s.equals("emerald"));
    }

    //SETTER

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public void setGuiInterface(boolean guiInterface) {
        this.guiInterface = guiInterface;
    }

    public void setTurnOver(boolean turnOver) {
        this.turnOver = turnOver;
    }

    //GETTER

    public String getPlayerName() {
        return playerName;
    }

    public char getColor() {
        return color;
    }

    public Scanner getInput() {
        return input;
    }

    public PrintWriter getOut() {
        return out;
    }

    public boolean isTurnOver() {
        return turnOver;
    }
}