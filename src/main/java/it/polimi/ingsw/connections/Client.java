package it.polimi.ingsw.connections;

import com.google.gson.Gson;
import it.polimi.ingsw.connections.rmi.Callback;
import it.polimi.ingsw.connections.rmi.Loggable;
import it.polimi.ingsw.connections.socket.ClientReader;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

import it.polimi.ingsw.model.clientmodel.ClientModel;
import it.polimi.ingsw.model.clientmodel.PlayerClientModel;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.MyApplication;
import it.polimi.ingsw.view.gui.login.WaitingGameStart;
import javafx.application.Platform;

import static java.lang.System.out;

public class Client implements Callback, Serializable {

    private static final String ERRORSTRING = "Wrong choice, please try again";
    private static final String CHOICESTRING = "Please, type your choice:";

    private static String ip;// = "127.0.0.1"; //192.168.43.38
    private static int port;// =1234;
    private static int RMIPortServer;// = 1235;
    private static boolean guiInterface = false;
    private static boolean rmiConnection;
    private static Socket clientSocket;
    private static PrintWriter socketOut;
    private static Scanner socketIn;
    private static Scanner stdin = new Scanner(System.in);
    private static String username;
    private static ArrayList<String> availableColors;
    private static ArrayList<String> usersList = new ArrayList<>();
    private static MyApplication gui;
    private static CLI cli;
    private static ClientModel model;
    private static boolean guiDisplayed;
    private Loggable stub;
    private Callback callbackClient;
    private static boolean refreshGUI = false;
    private static boolean confirm = false;

    //gui define
    private static final String DISABLE_ALL = "DISABLE_ALL";
    private static final String ENABLE_ALL_ACTION = "ENABLE_ALL_ACTION";
    private static final String DISABLE_ALL_ACTION = "DISABLE_ALL_ACTION";
    private static final String ENABLE_RELOAD = "ENABLE_RELOAD";
    private static final String DISABLE_RELOAD = "DISABLE_RELOAD";
    private static final String ENABLE_SQUARE = "ENABLE_SQUARE";
    private static final String DISABLE_SQUARE = "DISABLE_SQUARE";
    private static final String ENABLE_SELECT_PLAYER = "ENABLE_SELECT_PLAYER";
    private static final String DISABLE_SELECT_PLAYER = "DISABLE_SELECT_PLAYER";
    private static final String ENABLE_SELECT_WEAPON_HAND = "ENABLE_SELECT_WEAPON_HAND";
    private static final String DISABLE_SELECT_WEAPON_HAND = "DISABLE_SELECT_WEAPON_HAND";
    private static final String ENABLE_SPAWN = "ENABLE_SPAWN";
    private static final String DISABLE_SPAWN = "DISABLE_SPAWN";
    private static final String ENABLE_USE_PU = "ENABLE_USE_PU";
    private static final String DISABLE_USE_PU = "DISABLE_USE_PU";
    private static final String ENABLE_SELECT_POWERUP_HAND = "ENABLE_SELECT_POWERUP_HAND";
    private static final String DISABLE_SELECT_POWERUP_HAND = "DISABLE_SELECT_POWERUP_HAND";
    private static final String ENABLE_ALL_PAYMENT_CHOICE = "ENABLE_ALL_PAYMENT_CHOICE";
    private static final String DISABLE_ALL_PAYMENT_CHOICE = "DISABLE_ALL_PAYMENT_CHOICE";
    private static final String DISABLE_PAY_WITH_AMMO = "DISABLE_PAY_WITH_AMMO";
    private static final String DISABLE_PAY_WITH_POWERUP = "DISABLE_PAY_WITH_POWERUP";
    private static final String DISABLE_PAY_WITH_BOTH = "DISABLE_PAY_WITH_BOTH";
    private static final String ENABLE_AMMO_CHOICE = "ENABLE_AMMO_CHOICE";
    private static final String DISABLE_SHOOT = "DISABLE_SHOOT";
    private static final String ENABLE_END = "ENABLE_END";
    private static final String DISABLE_END = "DISABLE_END";
    private static final String ONLY_NAME = "ONLY_NAME";
    private static final String ONLY_COLOR = "ONLY_COLOR";
    private static final String ENABLE_CONTINUE = "ENABLE_CONTINUE";
    private static final String DISABLE_CONTINUE = "DISABLE_CONTINUE";
    private static final String ENABLE_CHOOSE_EFFECT = "ENABLE_CHOOSE_EFFECT";
    private static final String DISABLE_CHOOSE_EFFECT = "DISABLE_CHOOSE_EFFECT";
    private static final String ENABLE_FRENETIC_BEFORE_FIRST = "ENABLE_FRENETIC_BEFORE_FIRST";
    private static final String DISABLE_FRENETIC_BEFORE_FIRST = "DISABLE_FRENETIC_BEFORE_FIRST";
    private static final String ENABLE_FRENETIC_AFTER_FIRST = "ENABLE_FRENETIC_AFTER_FIRST";
    private static final String DISABLE_FRENETIC_AFTER_FIRST = "DISABLE_AFTER_BEFORE_FIRST";
    private static final String ENABLE_PAY_WITH_AMMO = "ENABLE_PAY_WITH_AMMO";
    private static final String ENABLE_PAY_WITH_POWERUP = "ENABLE_PAY_WITH_POWERUP";
    private static final String ENABLE_PAY_WITH_BOTH = "ENABLE_PAY_WITH_BOTH";


    public Client () throws RemoteException {
        guiInterface = false;
        rmiConnection = false;
        gui = new MyApplication();
        MyApplication.setClient(this);
        cli = new CLI();
    }

    public static void main(String[] args) {
        try {
            System.out.println("Client started");
            System.out.println("Please insert server's ip:");
            Scanner scanner = new Scanner(System.in);
            String serverIp = scanner.nextLine();
            Client client = new Client();
            setIp(serverIp);
            PropertiesReader propertiesReader = new PropertiesReader();
            propertiesReader.setPropertiesValue("client");
            //propertiesReader.setIp(serverIp);
            //Properties config = new Properties();
            //OutputStream configOut = new FileOutputStream("config/clientConfig.properties");
            //config.setProperty("ip", "127.0.0.1");
            //config.store(configOut, null);
            //InputStream configIn = new FileInputStream("config/clientConfig.properties");
            //config.load(configIn);
            //setIp(propertiesReader.getIp());
            setPort(Integer.parseInt(propertiesReader.getSocketPort()));
            setRMIPortServer(Integer.parseInt(propertiesReader.getRmiPort()));
            gui.run();
            if (!guiInterface) {
                splashPage();                               // da spostare in start client chiamati da gui
                client.chooseConnectionType();              //
                if (!rmiConnection) {
                    startClient();
                } else {
                    client.startClientRMIforCLI();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


    //CLI METHODS

    private static void splashPage() {
        clearCLI();
        System.out.println(" ");
        System.out.println(" █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗ █████╗ ██╗     ██╗███╗   ██╗ █████╗ \n" +
                "██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██╔══██╗██║     ██║████╗  ██║██╔══██╗\n" +
                "███████║██║  ██║██████╔╝█████╗  ██╔██╗ ██║███████║██║     ██║██╔██╗ ██║███████║\n" +
                "██╔══██║██║  ██║██╔══██╗██╔══╝  ██║╚██╗██║██╔══██║██║     ██║██║╚██╗██║██╔══██║\n" +
                "██║  ██║██████╔╝██║  ██║███████╗██║ ╚████║██║  ██║███████╗██║██║ ╚████║██║  ██║\n" +
                "╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝");
        System.out.println(" ");
        System.out.println("Press Enter to continue:");
        System.out.println("\n\n\n");
        System.out.println("Type QUIT to close the game at any time");
        stdin.nextLine();
    }

    private static void clearCLI() {
        System.out.print("\033[H\033[2J");
    }

    //CHOICE

    private void chooseConnectionType() {
        clearCLI();
        System.out.println(" ");
        System.out.println("Choose connection type between:");
        System.out.println(" ");
        System.out.println("    1 - Socket ");
        System.out.println("    2 - RMI ");
        System.out.println(" ");
        System.out.println(CHOICESTRING);
        String line;
        while(true) {
            line = stdin.nextLine();
            if(isValidChoice(line)) {
                if (line.equals("1")) {
                    System.out.println("Socket communication chosen");
                    clearCLI();
                } else {
                    System.out.println("RMI communication chosen");
                    rmiConnection = true;
                    clearCLI();
                }
                return;
            } else {
                System.err.println(ERRORSTRING);
            }
        }
    }

    //CHECK

    private boolean isValidChoice(String s) {
        return !(!s.equals("1") && !s.equals("2"));
    }

    //SOCKET

    public static void startClient() {
        try {
            clientSocket = new Socket(ip, port);
            try {
                clientSocket.setTcpNoDelay(true);
            } catch (SocketException e) {
                //e.printStackTrace();
            }
            socketIn = new Scanner(clientSocket.getInputStream());
            socketOut = new PrintWriter(clientSocket.getOutputStream());
            stdin = new Scanner(System.in);

            //ping

            //System.out.println("Input and output streams established");
            //System.out.println(socketIn.nextLine());
            String socketLine; // = null
            try {
                ClientReader clientRead = new ClientReader(socketOut);
                Thread thread1 = new Thread(clientRead);
                thread1.start();
                while (clientSocket.isConnected()) {
                    socketLine = readMessageSocket();
                    if (socketLine != null)
                        System.out.println(socketLine);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Connection closed");
                System.exit(0);
            } finally {
                stopConnection();
            }
        }catch (Exception e) {
            System.err.println("\n\nProbably server is down or you are disconnected, try to restart and login after a while\n\n");
            System.exit(0);
        }
    }

    public void startClientSocketForGUI(){
        try {
            clientSocket = new Socket(ip, port);
            try {
                clientSocket.setTcpNoDelay(true);
            } catch (SocketException e) {
                //e.printStackTrace();
            }
            socketIn = new Scanner(clientSocket.getInputStream());
            socketOut = new PrintWriter(clientSocket.getOutputStream());
            setGuiInterface(true);
            sendInterface();
            sendUsers();
            sendName();
            Thread thread = new Thread() {
                public void run() {
                    while(true) {
                        String line = null;
                        try {
                            line = readMessageSocket();
                        } catch (Exception e) {
                            System.err.println("\n\nProbably server is down or you are disconnected, try to restart and login after a while\n\n");
                            System.exit(0);
                        }
                        System.out.println(line);
                    }
                }
            };
            thread.start();
            //sendGameSetting();
        }catch (Exception e){
            //e.printStackTrace();
            System.err.println("\n\nProbably server is down or you are disconnected, try to restart and login after a while\n\n");
            System.exit(0);
        }
    }

    public static void stopConnection() {
        try {
            sendString("QUIT");
            stdin.close();
            socketIn.close();
            socketOut.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static synchronized String readMessageSocket() throws Exception {
        Gson gson = new Gson();
        String socketLine;
        socketLine = socketIn.nextLine();
        //System.out.println(socketLine);
        //System.out.println("GUI DISPLAYED: " + guiDisplayed);
        if (socketLine.equals("MODEL")) {
            socketLine = socketIn.nextLine();
            model = gson.fromJson(socketLine, ClientModel.class);
            return null;
        } else if(socketLine.equals("PING")) {
            //System.out.println("RICEVUTO PING");
            socketOut.println("PONG");
            socketOut.flush();
            //System.out.println("MANDATO IL PONG");
            return null;
        } else if (socketLine.equals("REFRESH")) {
            cli.plotCLI();
        } else if (socketLine.equals("INTERFACE")) {
            if (guiInterface) {
                socketOut.println("GUI");
                socketOut.flush();
            } else {
                socketOut.println("CLI");
                socketOut.flush();
            }
        } else if (socketLine.equals("UPDATE")) {
            if (!guiInterface) {
                cli.setMap(model.getMap());
                cli.setPlayers(model.getPlayers());
                cli.setTerminator(model.hasTerminator());
                cli.setTerminator(model.getTerminatorClientModel());
                cli.setKillShotTrack(model.getKillShotTrack());
                cli.setPlayersPoint(model.getPlayersPoint());
                cli.plotCLI();
            } else {
                if (guiDisplayed) {
                    if (!gui.getBoardGUI().isRefresh()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!gui.getBoardGUI().isShowCard()) {
                                    gui.getBoardGUI().refreshBoard();
                                } else {
                                    gui.getBoardGUI().setRefresh(true);
                                }
                            }
                        });
                    } else
                        refreshGUI = true;
                }
            }
            return null;
        } else if (socketLine.equals("CONTINUE")) {
            if (guiInterface && guiDisplayed) {
                if (!(gui.getBoardGUI().isRefresh() && refreshGUI))
                    sendString("CONTINUE");
                else
                    sendString("WAIT");
            } else {
                //System.out.println(socketLine);
                sendString("CONTINUE");
            }
            //}else if (guiInterface){
        } else if (socketLine.equals("COLOR_CHOICE")){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.colorChoice(username);
                }
            });
        } else if(socketLine.equals("COLOR")) {
            //System.out.println(socketLine);
            String colors = socketIn.nextLine();
            //System.out.println(colors);
            //System.out.println(socketLine);
            availableColors = gson.fromJson(colors, ArrayList.class);
            return null;
        } else if (socketLine.equals("PLAYERNAME")) {
            if (!guiInterface) {
                username = socketIn.nextLine();
                cli.setPlayerName(username);
            }
            else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.nameChoice();
                    }
                });
            }
            return null;
        } else if (socketLine.equals("USERS")) {
            //System.out.println("ricevo users");
            String line = socketIn.nextLine();
            //System.out.println("dopo la next line");
            //System.out.println(line);
            if(line != null)
                usersList = gson.fromJson(line, ArrayList.class);
            return null;
        }else if (socketLine.equals("COMPLETE")) {
            if (guiInterface)
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.gameComplete();
                    }
                });
            gui.gameComplete();
        }else if (socketLine.equals("GAME_SETTING")){
            System.out.println(socketLine);
            if (guiInterface)
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.terminatorChoice();
                    }
                });
        } else if (socketLine.equals("CONFIRM")) {
            //System.out.println("ho la conferma");
            confirm = true;
        }else if (socketLine.equals("REFUSED")) {
            confirm = false;
        } else if (socketLine.equals("WAIT_START")) {
            if (guiInterface)
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.waitGameToStart();
                    }
                });
        } else if (socketLine.contains("WRONG")){
            if (guiInterface)
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.colorChoice(username);
                    }
                });
        } else if (!guiDisplayed && (socketLine.contains("TIMER") || socketLine.equals("RESET"))) {
            String s = socketLine;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    WaitingGameStart.setTimer(s);
                }
            });
            return null;
        }else if (!guiDisplayed && socketLine.equals("TERMINATOR_CHOICE")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.terminatorChoice();
                }
            });
            return null;
        }else if (!guiDisplayed && socketLine.equals("BOARD_AND_SKULLS_CHOICE")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.boardAndSkullChoice();
                }
            });
            return null;
        }else if (socketLine.equals("Displaying board") && guiInterface) {
            gui.boardGUI();
            guiDisplayed = true;
            return null;
        }
        else if (socketLine.equals("DISCONNECTED")){
            if (!guiInterface){
                System.out.println("You are disconnected, wait for new connection choiche...");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.run();
                    }
                });
                //gui.run();
                return null;
            }else {
                guiHandler(socketLine);
                return null;
            }
        }
        else if (guiDisplayed) {
            guiHandler(socketLine);
            return null;
        }
        else {
            //System.out.println("STO RITORNANDO: " + socketLine);
            return socketLine;
        }
        return null;
    }

    public static boolean getConfirm(){
        boolean booleanToReturn = false;
        if (confirm) {
            booleanToReturn = true;
            confirm = false;
        }
        return booleanToReturn;
    }

    public static void setConfirm(boolean b){
        confirm = b;
    }

    //-------------------------------------------------------------------------

    //RMI

    private void initCallback(){
        try {
            callbackClient = (Callback) UnicastRemoteObject.exportObject(this, stub.getPortClient());
        }catch (Exception e){
            //e.printStackTrace();
        }
    }

    public void setCallbackClient(Callback callbackClient){
        this.callbackClient = callbackClient;
    }

    public Loggable getStub(){
        return stub;
    }

    private void startClientRMIforCLI() {
        //out.println("Hello from Client!");
        boolean active = true;
        try {
            Registry registry = LocateRegistry.getRegistry(ip, RMIPortServer);
            stub = (Loggable) registry.lookup("Loggable");
            initCallback();
            while (active) {
                //Game not started yet
                if(!stub.getGameRunning()) {
                    if (!stub.checkIfComplete()) {
                        boolean login = chooseNickname();
                        while (!login) {
                            System.out.println("already taken!");
                            if (stub.checkIfComplete()) {
                                out.println("Ooops! There are too many players, sorry!");
                                return;
                            }
                            login = chooseNickname();
                        }
                        if (stub.checkIfComplete()) {
                            out.println("Ooops! There are too many players, sorry!");
                            return;
                        }

                        login = chooseColor();
                        while (!login) {
                            if (stub.checkIfComplete()) {
                                out.println("Ooops! There are too many players, sorry!");
                                return;
                            }
                            login = chooseColor();
                        }
                        stub.setReady(username);
                        //out.println("User ready");
                        active = false;
                    } else
                        return;
                } else if(stub.getGameRunning()) {      //Game already started, log back in
                    boolean login = chooseNickname();
                    while (!login) {
                        System.out.println("already taken!");
                        if (stub.checkIfComplete()) {
                            out.println("Ooops! There are too many players, sorry!");
                            return;
                        }
                        login = chooseNickname();
                    }
                    if (stub.checkIfComplete()) {
                        out.println("Ooops! There are too many players, sorry!");
                        return;
                    }

                    while (!login) {
                        if (stub.checkIfComplete()) {
                            out.println("Ooops! There are too many players, sorry!");
                            return;
                        }
                    }
                    stub.setReady(username);
                    stub.getModel();
                    active = false;
                }
            }

            //Replies to the server
            while(true) {
                String line = readRMI();
                if (stub.isTurn(username)) {
                    stub.reply(line);
                }
            }

        } catch (Exception e) {
            System.err.println("\n\nProbably server is down or you are disconnected, try to restart and login after a while\n\n");
            System.exit(0);
            //e.printStackTrace();
        }
    }

    public void startClientRMIforGUI() {
        try {
            setGuiInterface(true);
            Registry registry = LocateRegistry.getRegistry(ip, RMIPortServer);
            stub = (Loggable) registry.lookup("Loggable");
            //initCallback();
            //stub.addClient(username, callbackClient);
        }catch (Exception e){
            System.err.println("\n\nProbably server is down or you are disconnected, try to restart and login after a while\n\n");
            System.exit(0);
        }
    }

    public void createReference(Loggable stub) {
        initCallback();
        try {
            stub.addClient(username, callbackClient);
        } catch (RemoteException e) {
            //e.printStackTrace();
        }

    }

    private boolean chooseNickname() throws Exception{
        out.println("Choose a nickname, please:");
        String nick = readRMI();
        if (nick.length() == 0)
            out.print("Nickname not valid or ");
        Boolean logged = stub.login(nick, false);
        username = nick;
        if (logged == true) {
            stub.addClient(username, callbackClient);
        }
        return logged;
    }

    private boolean chooseColor() throws Exception{
        out.print("Choose a color between: ");
        for (String s : stub.availableColors())
            out.print(s + " ");
        out.println();
        String color = readRMI();
        for (String s : stub.availableColors())
            if (color.equals(s)){
                out.println(color + " selected");
                stub.setColor(username, s);
                stub.createPlayer(username);
                if (!stub.isFirstPlayer(username))
                    stub.setReady(username);
                out.println("User ready");
                return true;
            }
        out.println("Color not valid");
        return false;
    }

    private String readRMI() {
        String line = stdin.nextLine();
        if(line.equals("QUIT")) {
            try {
                stub.removeClient(username);
            } catch (RemoteException e) {
                //e.printStackTrace();
            }
        }
        return line;
    }

    //RMI CALLBACK

    @Override
    public boolean pingRMI() {
        return true;
    }

    @Override
    public void callback(String string) throws RemoteException {
        if (string == null)
            return;
        if (!guiInterface) {
            System.out.println(string);
            return;
        } else {
            System.out.println(string);
            if (!guiDisplayed && (string.contains("TIMER") || string.equals("RESET"))) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        WaitingGameStart.setTimer(string);
                    }
                });
                return;
            }
            if (!guiDisplayed && string.equals("TERMINATOR_CHOICE")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.terminatorChoice();
                    }
                });
                return;
            }
            if (!guiDisplayed && string.equals("BOARD_AND_SKULLS_CHOICE")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.boardAndSkullChoice();
                    }
                });
                return;
            }
            if (!guiDisplayed && !string.equals("Displaying board"))
                return;
            if (string.equals("Displaying board")) {
                gui.boardGUI();
                guiDisplayed = true;
                return;
            }
            guiHandler(string);
        }
    }

    public static void guiHandler(String string){
        if (string.equals(ENABLE_ALL_ACTION)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableAllAction();
                }
            });
            return;
        } else if (string.equals(ENABLE_RELOAD)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableReload();
                }
            });
            return;
        } else if (string.equals(DISABLE_ALL_ACTION)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableAllAction();
                }
            });
            return;
        } else if (string.equals(DISABLE_RELOAD)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableReload();
                }
            });
            return;
        } else if (string.equals(ENABLE_SQUARE)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableSquare();
                }
            });
            return;
        } else if (string.equals(DISABLE_SQUARE)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableSquare();
                }
            });
            return;
        } else if (string.equals(ENABLE_SELECT_PLAYER)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableSelectPlayer();
                }
            });
            return;
        } else if (string.equals(DISABLE_SELECT_PLAYER)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableSelectPlayer();
                }
            });
            return;
        } else if (string.equals(ENABLE_SELECT_WEAPON_HAND)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableSelectWeaponHand();
                }
            });
            return;
        } else if (string.equals(DISABLE_SELECT_WEAPON_HAND)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableSelectWeaponHand();
                }
            });
            return;
        } else if (string.contains(ENABLE_CHOOSE_EFFECT)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableChooseEffect(string.substring(21));
                }
            });
            return;
        } else if (string.equals(DISABLE_CHOOSE_EFFECT)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableChooseEffect();
                }
            });
            return;
        } else if (string.equals(ENABLE_SPAWN)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableSpawn();
                }
            });
            return;
        } else if (string.equals(DISABLE_SPAWN)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableSpawn();
                }
            });
            return;
        } else if (string.equals(ENABLE_USE_PU)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableUsePU();
                }
            });
            return;
        } else if (string.equals(DISABLE_USE_PU)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableUsePU();
                }
            });
            return;
        } else if (string.equals(ENABLE_SELECT_POWERUP_HAND)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableSelectPowerupHand();
                }
            });
            return;
        } else if (string.equals(DISABLE_SELECT_POWERUP_HAND)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableSelectPowerupHand();
                }
            });
            return;
        } else if (string.equals(ENABLE_ALL_PAYMENT_CHOICE)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableAllPaymentChoice();
                }
            });
            return;
        } else if (string.equals(DISABLE_ALL_PAYMENT_CHOICE)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableAllPaymentChoice();
                }
            });
            return;
        } else if (string.equals(ENABLE_PAY_WITH_AMMO)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enablePayWithAmmo();
                }
            });
            return;
        } else if (string.equals(DISABLE_PAY_WITH_AMMO)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disablePayWithAmmo();
                }
            });
            return;
        } else if (string.equals(ENABLE_PAY_WITH_POWERUP)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enablePayWithPowerup();
                }
            });
            return;
        } else if (string.equals(DISABLE_PAY_WITH_POWERUP)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disablePayWithPowerup();
                }
            });
            return;
        } else if (string.equals(ENABLE_PAY_WITH_BOTH)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enablePayWithBoth();
                }
            });
            return;
        } else if (string.equals(DISABLE_PAY_WITH_BOTH)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disablePayWithBoth();
                }
            });
            return;
        } else if (string.equals(ENABLE_AMMO_CHOICE)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableAmmoChoice();
                }
            });
            return;
        } else if (string.equals(DISABLE_SHOOT)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableShoot();
                }
            });
            return;
        } else if (string.equals(DISABLE_ALL)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableAll();
                }
            });
            return;
        } else if (string.equals(ENABLE_END)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableEnd();
                }
            });
            return;
        } else if (string.equals(DISABLE_END)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableEnd();
                }
            });
            return;
        } else if (string.equals(ENABLE_CONTINUE)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableContinue();
                }
            });
            return;
        } else if (string.equals(DISABLE_CONTINUE)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().disableContinue();
                }
            });
            return;
        } else if (string.equals(ONLY_NAME)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().onlyName();
                }
            });
            return;
        } else if (string.equals(ONLY_COLOR)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().onlyColor();
                }
            });
            return;
        } else if (string.equals(ENABLE_FRENETIC_BEFORE_FIRST)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableFreneticBeforeFirst();
                }
            });
            return;
        } else if (string.equals(ENABLE_FRENETIC_AFTER_FIRST)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().enableFreneticAfterFirst();
                }
            });
            return;
        } else if (string.equals("SHOW_WINNERS")) {
            //System.out.println("stampa winners da model su client");
            //System.out.println(model.getWinners());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.getBoardGUI().showWinners();
                }
            });
            return;
        } else if (string.equals("DISCONNECTED")){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.disconnected();
                }
            });
        }
        if (gui.getBoardGUI() != null)
            gui.getBoardGUI().appendText(string + "\n");
    }

    @Override
    public void boardGUI() throws RemoteException {
        gui.boardGUI();
    }

    @Override
    public void getModelCallback() {
        try {
            setModel(getStub().getModel());
            //System.out.println("RICEVUTO CLIENT MODEL RMI");
            /*for(PlayerClientModel p: model.getPlayers()) {
                System.out.println(p.getColor() + " " + p.getPlayerPosition());
            }*/
            //System.out.println("DOPO STAMPE RICEZIONE MODEL");
            if(!getGuiInterface()) {
                cli.setMap(model.getMap());
                cli.setPlayers(model.getPlayers());
                cli.setPlayerName(username);
                cli.setTerminator(model.hasTerminator());
                cli.setTerminator(model.getTerminatorClientModel());
                cli.setKillShotTrack(model.getKillShotTrack());
                cli.setPlayersPoint(model.getPlayersPoint());
                cli.plotCLI();
            }else
            if (guiDisplayed) {
                if (!gui.getBoardGUI().isRefresh()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!gui.getBoardGUI().isShowCard()) {
                                //System.out.println("PRIMA REFRESH GUI");
                                gui.getBoardGUI().refreshBoard();
                                //System.out.println("DOPO REFRESH GUI");
                            } else {
                                gui.getBoardGUI().setRefresh(true);
                                //System.out.println("ELSE REFRESH GUI");
                            }
                        }
                    });
                }else
                    refreshGUI = true;
            }
        } catch (RemoteException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean waitRefresh() throws RemoteException {
        boolean ok;
        if (!(gui.getBoardGUI().isRefresh() && refreshGUI))
            ok = true;
        else
            ok = false;
        return ok;
    }

    public boolean isRefreshGUI() {
        return refreshGUI;
    }

    public void setRefreshGUI(boolean refreshGUI) {
        this.refreshGUI = refreshGUI;
    }

    @Override
    public void refreshCLI() {
        cli.plotCLI();
    }

    //------------------------------------------------------------------------------------

    //GUI METHODS

    public MyApplication getGui(){
        return gui;
    }

    public ArrayList<String> getUsersRMI() {
        //if(rmiConnection){
        try {
            return stub.getUsersRMI();
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
        //}
        return new ArrayList<>();
    }

    public ArrayList<String> getUsersListTCP(){
        //for (String s : usersList)
        //    System.out.println(s);
        return usersList;
    }

    public static void sendString(String s) {
        socketOut.println(s);
        socketOut.flush();
        return;
    }

    public static void sendInterface() throws Exception{
        readMessageSocket();
    }

    public static void sendUsers() throws Exception{
        readMessageSocket();
    }

    public static void sendName() throws Exception{
        readMessageSocket();
    }

    /*public static void sendGameSetting(){
        readMessageSocket();
    }*/

    /*public boolean getConfirm() {
        String socketLine;
        socketLine = readMessageSocket();
        while (socketLine == null)
            socketLine = readMessageSocket();
        System.out.println("LINEA DI CONFERMA" + socketLine);
        if(socketLine.contains("Confirm")) {
            //System.out.println(socketLine);
            if (socketLine.length() > 8)
                username = socketLine.substring(8);
            return true;
        } else if (socketLine.equals("Refused")) {
            //System.out.println(socketLine);
            return false;
        }
        return false;
    }*/

    /*public boolean getGameSetting(){
        String line;
        line = readMessageSocket();
        while (line == null)
            line = readMessageSocket();
        if (line.equals("GAME_SETTING"))
            return true;
        return false;
    }*/

    public void sendStringToServer(String s){
        if (rmiConnection){
            System.out.println("string to server: " + s);
            try {
                getStub().reply(s);
            } catch (RemoteException e) {
                if(guiInterface) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gui.disconnected();
                        }
                    });
                } else if(!guiInterface)
                    System.out.println("You are disconnected from the server");
                    System.out.println("Wait for the internet connection to restore or reopen the client to login");
                //e.printStackTrace();
            }
        }else
            sendString(s);
    }

    //GETTER

    public boolean getRmiConnection(){
        return rmiConnection;
    }

    public static boolean getGuiInterface(){
        return guiInterface;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getUsername(){
        return username;
    }

    public static ClientModel getModel() {
        return model;
    }

    public static ArrayList<String> getAvailableColors() {
        return availableColors;
    }

    //SETTER

    public void setGuiInterface(boolean b){
        guiInterface = b;
    }

    public void setRmiConnection(boolean b){
        rmiConnection = b;
    }

    public void setUsername(String name){
        username = name;
    }

    public static void setModel(ClientModel model) {
        Client.model = model;
    }

    public static void setIp(String ip) {
        Client.ip = ip;
    }

    public static void setPort(int port) {
        Client.port = port;
    }

    public static void setRMIPortServer(int RMIPortServer) {
        Client.RMIPortServer = RMIPortServer;
    }

    public static void setAvailableColors(ArrayList<String> availableColors) {
        Client.availableColors = availableColors;
    }

    public static void setGuiDisplayed(boolean b){
        guiDisplayed = b;
    }

}