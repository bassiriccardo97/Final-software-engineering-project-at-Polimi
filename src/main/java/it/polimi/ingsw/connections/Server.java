package it.polimi.ingsw.connections;

import it.polimi.ingsw.connections.rmi.Callback;
import it.polimi.ingsw.connections.rmi.ClientReference;
import  it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.connections.rmi.Loggable;
import it.polimi.ingsw.connections.socket.*;
import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.card.powerup.PowerupDeck;
import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.clientmodel.*;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.game.squares.Square;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.view.cli.Map;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;

public class Server implements Loggable {



    //private static final Logger LOGGER = Logger.getLogger( Server.class.getName() );
    private ServerSocket serverSocket;
    private static int PORT;// = 1234;
    private static int RMIPORT;// = 1235;
    private static boolean isFirst = true;
    private static boolean gameRunning = false;
    private static boolean terminator = false;
    private static boolean updateTerminator = false;
    private static char terminatorColor;
    private static char terminatorSpawnpoint = '\u0000';
    private static int skullsNumber = 0;
    private static int mapChoice = 0;
    private static Map map;
    private static ArrayList<User> clients = new ArrayList<>();
    private static ArrayList<ClientReference> clientRMI = new ArrayList<>();
    private static String rmiReply = null;
    private static ClientModel clientModel = new ClientModel();
    private static ArrayList<String> winners = new ArrayList<>();
    private static int lobbyDelay;
    private static int turnTimer;
    private static boolean timeout;
    private static int replyTimer = 20;
    public static boolean afterTimeout;

    public static void main (String[] args) throws IOException {
        System.out.println("Please insert lobbyDelay in seconds:");
        Scanner scanner = new Scanner(System.in);
        lobbyDelay = scanner.nextInt();
        System.out.println("Please insert turnTimer in seconds:");
        turnTimer = scanner.nextInt();
        Server server = new Server();
        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.setPropertiesValue("server");
        PORT = Integer.parseInt(propertiesReader.getSocketPort());
        RMIPORT = Integer.parseInt(propertiesReader.getRmiPort());
        //lobbyDelay = Integer.parseInt(propertiesReader.getLobbyDelay());
        //turnTimer = Integer.parseInt(propertiesReader.getTurnTimer());
        server.startServer(server);
    }

    private void startServer(Server server) {
        //initColors();
        ExecutorService executor = Executors.newCachedThreadPool();

        //SOCKET
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            //LOGGER.log(Level.ALL, e.getMessage());
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server started!");

        LobbyWait lobbyWait = null;
        executor.submit(lobbyWait = new LobbyWait());
        //CheckConnection checkConnection = null;
        //executor.submit(checkConnection = new CheckConnection(this));
        SocketHandler socket;
        executor.submit(socket = new SocketHandler(this));

        //RMI
        try {
            Loggable stubLogin = (Loggable) UnicastRemoteObject.exportObject(server , PORT + 1);
            Registry registry = LocateRegistry.createRegistry(PORT + 1);
            registry.bind("Loggable", stubLogin);
            System.out.println("RMI ready");
            //initColors();
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            //e.printStackTrace();
        }

        executor.shutdown();
    }

    //PING METHODS

    /**
     * Pings the user either way he has RMI or Socket connection
     *
     * @param u     User to ping
     * @return      true if able to reach the client, false otherwise
     */

    public boolean ping(User u) {
        if(u.isRmiConnection()) {
            if (!u.isTurnOver())
                return pingRMI(getClientReference(u.getPlayerName()).getClientCallback());
        } else if(!u.isRmiConnection()) {
            if(!u.isTurnOver())
                return u.getConnection().ping();
        }
        return false;
    }

    public void pingAll() {
        for(User u: clients) {
            if(u.isRmiConnection()) {
                if(!u.isTurnOver() && !u.getPlayer().getTurn())
                    pingRMI(getClientReference(u.getPlayerName()).getClientCallback());
            } else if(!u.isRmiConnection()) {
                if(!u.isTurnOver() && !u.getPlayer().getTurn())
                    u.getConnection().ping();
            }
        }
    }

    //METHODS

    public boolean startGame() {
        System.out.println("The game has started");
        messageToAll("The game has started");
        this.gameRunning = true;
        ExecutorService executor = Executors.newCachedThreadPool();
        GameController controller = new GameController();
        executor.submit(controller);
        return true;
    }

    public static int getLobbyDelay() {
        return lobbyDelay;
    }

    public static int getTurnTimer() {
        return turnTimer;
    }

    //CHECK

    public boolean colorAlreadyTaken(char c) {
        for (User u: clients) {
            if (c == u.getColor()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkReadyPlayers() {
        for (User u: clients) {
            if(!u.isReady()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfCompleteSocket() {
        if (terminatorColor != '\u0000')
            return clients.size() > 4;
        else
            return clients.size() > 5;
    }

    public boolean checkIfSomeoneDisconnected() {
        for(User u: clients) {
            if(u.isDisconnected())
                return true;
        }
        return false;
    }

    //MESSAGES

    public void messageToAll(String s) {
        for(User u: clients) {
            if(!u.isDisconnected()) {
                if (!u.isRmiConnection())
                    u.getConnection().sendMessage(s, false);
                else {
                    sendMessageRMI(s, getClientReference(u.getPlayerName()).getClientCallback());
                }
            }
        }
        return;
    }

    /**
     * Sends a message to the client of the color given and if needed and answer waits for the client reply
     *
     * @param color         color of the user to send the message to
     * @param s             the string to send
     * @param needAnswer    true if the servers needs an answer
     * @return              return the string sent by the client, null if not needed
     */

    public String messageTo(char color, String s, boolean needAnswer) {
        if (getClientByColor(color).isTurnOver())
            return null;
        if(needAnswer) {
            for(User u: clients) {
                if(!ping(u))
                    u.setDisconnected(true);
                else
                    u.setDisconnected(false);
            }
        }
        for (User u: clients) {
            if(u.getColor() == color) {
                if(!u.isDisconnected()) {
                    if (!ping(u)) {
                        u.setDisconnected(true);
                        return null;
                    }
                    if (!u.isRmiConnection()) {
                        String line = null;
                        line = u.getConnection().sendMessage(s, needAnswer);
                        return line;
                    } else {
                        if (needAnswer) {
                            sendMessageRMI(s, getClientReference(getClientByColor(color).getPlayerName()).getClientCallback());
                            String line = null;
                            boolean tempDisconnected = u.isDisconnected();
                            while(line == null && u.getPlayer().getTurn() && !u.isTurnOver()) {

                                if(!u.isDisconnected() && u.getPlayer().getTurn() && tempDisconnected) {
                                    sendMessageRMI(s, getClientReference(getClientByColor(color).getPlayerName()).getClientCallback());
                                }
                                line = waitRmiReply(u);
                                if(u.isDisconnected())
                                    tempDisconnected = true;
                                else
                                    tempDisconnected = false;
                            }
                            if(u.isTurnOver())
                                return "TIMED_OUT";
                            if(line != null)
                                return line;
                        } else {
                            sendMessageRMI(s, getClientReference(getClientByColor(color).getPlayerName()).getClientCallback());
                        }
                        return null;

                    }
                } else {
                    while(u.getPlayer().getTurn() && u.isDisconnected()) {
                        System.out.print("");
                    }
                    return null;
                }
            }
        }
        return "Client not found";
    }

    public void sendMessageRMI(String s, Callback client) {
        try {
            callback(s, client);
        } catch(RemoteException e) {
            //e.printStackTrace();
        }
    }

    private String waitRmiReply(User u) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable() {
            @Override
            public String call() throws Exception {
                String temp;
                while (rmiReply == null && u.getPlayer().getTurn()) {
                    System.out.print("");
                }
                temp = rmiReply;
                setRmiReply(null);
                return temp;
            }
        });
        try {
            String temp2 = future.get(replyTimer*1000, TimeUnit.MILLISECONDS);
            return temp2;
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            future.cancel(true);
        }
        executor.shutdown();
        return null;
    }

    public void refreshCLiAll() {
        for(User u: clients) {
            if(!u.isGuiInterface())
                refreshCLI(u.getColor());
        }
    }

    public void refreshCLI(char color) {
        for(User u: getClients()) {
            if(u.getColor() == color) {
                if(!u.isRmiConnection()) {
                    u.getConnection().refreshCLI();
                } else if(u.isRmiConnection()) {
                    try {
                        clearCliRMI(getClientReference(getClientByColor(color).getPlayerName()).getClientCallback());
                    } catch (RemoteException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
    }

    //---------------------------------------------------------------------------------------

    //RMI DISCONNECTIONS

    public boolean pingRMI(Callback client) {
        try {
            if(client.pingRMI())
                getClientByCallback(client).setDisconnected(false);
            return true;
        } catch(RemoteException e) {
            getClientByCallback(client).setDisconnected(true);
            return false;
        }

    }

    //RMI MESSAGES

    @Override
    public void reply(String line) throws RemoteException {
        rmiReply = line;
        return;
    }

    @Override
    public void clearCliRMI(Callback client) throws RemoteException {
        client.refreshCLI();
    }

    @Override
    public void callback(String string, Callback client) throws RemoteException {
        client.callback(string);
    }

    //RMI

    @Override
    public boolean login(String nick, boolean guiInterface) throws RemoteException {
        if (gameRunning){
            //System.out.println("game running " + nick + " trying to reconnect");
            for (User u : clients)
                if (u.getPlayerName().equals(nick) && u.isDisconnected()) {
                    u.setDisconnected(false);
                    return true;
                }
            return false;
        }
        if (nick.length() == 0 || nick.length() > 10)
            return false;
        for (User u : clients)
            if (u.getPlayerName().equals(nick)) {
                return false;
            }
        User client = new User(true, isFirst, guiInterface);
        client.setPlayerName(nick);
        //client.setRmiConnection(true);
        //add to clients list
        clients.add(client);
        isFirst = false;
        System.out.println(nick + " is logging...");
        return true;
    }

    @Override
    public void removeClient(String name) throws RemoteException {
        for (User u: clients) {
            if(name.equals(u.getPlayerName())) {
                System.out.println("Player " + u.getPlayerName() + " disconnected");
                u.setDisconnected(true);
                return;
            }
        }
    }

    @Override
    public void createPlayer(String nick) throws RemoteException {
        User c = getClient(nick);
        RealPlayer t = new RealPlayer(c.getColor(), c.getPlayerName());
        c.setPlayer(t);
        System.out.println("player " + c.getColor() + " created");
        c.setReady(true);
        for (int i = 0; i < getAvailableColors().size(); i++) {
            if (getAvailableColors().get(i).charAt(0) == c.getColor()) {
                //availableColors.remove(i);
                System.out.println(nick + " is logged: player " + c.getColor() + " created!");

                return;
            }
        }
    }

    @Override
    public void addClient(String name, Callback callbackClient) throws RemoteException {
        for(ClientReference c: clientRMI) {
            if(c.getUsername().equals(name)) {
                c.setClientCallback(callbackClient);
                return;
            }
        }
        clientRMI.add(new ClientReference(name, callbackClient));
    }

    @Override
    public Callback getCallbackClient(String name) throws RemoteException {
        for (ClientReference cr : clientRMI)
            if (cr.getUsername().equals(name))
                return cr.getClientCallback();
        return null;
    }

    @Override
    public void sendModel(Callback client) throws RemoteException {
        client.getModelCallback();
    }

    //RMI GETTER

    @Override
    public boolean isTurn(String username) throws RemoteException {
        return getClient(username).getPlayer().getTurn();
    }

    @Override
    public List<String> availableColors() throws RemoteException {
        return getAvailableColors();
    }

    @Override
    public boolean isFirstPlayer(String nick) throws RemoteException{
        return getClient(nick).isFirst();
    }

    @Override
    public boolean getGameRunning() throws RemoteException {
        return gameRunning;
    }

    @Override
    public boolean getGameStarted() throws RemoteException {
        return gameRunning;
    }

    @Override
    public int getBoard() {
        return mapChoice;
    }

    @Override
    public User getUser (String name) {
        return getClient(name);
    }

    @Override
    public ArrayList<String> getUsersRMI() throws RemoteException {
        ArrayList<String> users = new ArrayList<>();
        for (User u : clients)
            users.add(u.getPlayerName() + " " + u.getColor());
        return users;
    }

    @Override
    public Map getMapRMI() throws RemoteException{
        return map;
    }

    @Override
    public int getPortClient() throws RemoteException {
        RMIPORT++;
        return RMIPORT;
    }

    //RMI GET CARDS


    @Override
    public int getInitSkulls() throws RemoteException {
        return skullsNumber;
    }

    @Override
    public int getSkulls() throws RemoteException {
        return Board.getKillShotTrack();
    }

    @Override
    public String[] getKillshotTrack() throws RemoteException {
        return Board.getKillShotTrackRMX();
    }

    @Override
    public boolean getTerminator() throws RemoteException {
        return terminator;
    }

    @Override
    public ClientModel getModel() {
        return clientModel;
    }

    //RMI SETTER

    @Override
    public void setTerminatorColor(String color) throws RemoteException{
        for (int i = 0; i < getAvailableColors().size(); i++)
            if (getAvailableColors().get(i).equals(color)){
                //availableColors.remove(i);
                terminatorColor = color.charAt(0);
                terminator = true;
                System.out.println("Terminator color set to: " + color);
            }
    }

    @Override
    public void setSkulls(int n) throws RemoteException {
        skullsNumber = n;
    }

    @Override
    public void setColor(String name, String color) throws RemoteException {
        getClient(name).setColor(color.charAt(0));
        getClient(name).setColor(color.charAt(0));
    }

    @Override
    public void setBoard(int board) throws RemoteException{
        mapChoice = board;
        //Server.createMap();
    }

    //RMI CHECK

    @Override
    public boolean checkIfComplete() throws RemoteException {
        return clients.size() > 5;
    }

    //----------------------------------------------------------------------------------------

    //MAP METHODS

    public static void createMap() {
        map = new Map(mapChoice, skullsNumber);
        map.setTerminator(terminator);
        map.setPlayersModel(getClientModel().getPlayers());
    }

    public static void updateMap() {
        map.setPlayersModel(getClientModel().getPlayers());
        if(terminator)
            map.setTerminator(getClientModel().getTerminatorClientModel());
        map.updateMap(Board.getKillShotTrack());
        clientModel.setMap(map);
    }

    //CLIENT MODEL METHODS

    public void createClientModel() {        //sistemare creazione mazzi carte

        ArrayList<RealPlayerClientModel> players = new ArrayList<>();
        for(User u: clients) {
            RealPlayerClientModel p = new RealPlayerClientModel();
            p.setPlayerName(u.getPlayerName());
            p.setColor(u.getColor());
            if(u.getPlayer().getPlayerPosition() == null)
                p.setPlayerPosition(null);
            else
                p.setPlayerPosition(u.getPlayer().getPlayerPosition().getNum());
            p.setPlayerBoard(new PlayerBoardClientModel());
            p.setPlayerHand(new PlayerHandClientModel());
            p.getPlayerBoard().setAmmoBox(u.getPlayer().getPb().getAmmoBox());
            p.getPlayerBoard().setDamages(u.getPlayer().getPb().getDamages());
            p.getPlayerBoard().setMarkedDamages(u.getPlayer().getPb().getMarkedDamages());
            p.getPlayerBoard().setPointsToAssignSide1(u.getPlayer().getPb().getPointsToAssignSide1());
            p.getPlayerBoard().setPointsToAssignSide2(u.getPlayer().getPb().getPointsToAssignSide2());
            if (u.isFirst())
                p.setFirst(true);
            p.setFirstTurn(true);

            p.setYourTurn(u.getPlayer().getTurn());

            players.add(p);
        }

        clientModel.setPlayers(players);

        clientModel.setTerminator(terminator);
        clientModel.setInitSkulls(skullsNumber);
        clientModel.setReaminingSkulls(skullsNumber);
        clientModel.setKillShotTrack(Board.getKillShotTrackRMX());


        //Add Terminator
        if(terminator) {
            TerminatorClientModel terminatorClient = new TerminatorClientModel();
            //terminatorClient.setColor(AlphaGame.getPlayerForColor(terminatorColor).getColor());
            terminatorClient.setColor(terminatorColor);
            terminatorClient.setPlayerBoard(new PlayerBoardClientModel());
            //terminatorClient.getPlayerBoard().setDamages(AlphaGame.getPlayerForColor(terminatorColor).getPb().getDamages());
            //terminatorClient.getPlayerBoard().setAmmoBox(AlphaGame.getPlayerForColor(terminatorColor).getPb().getAmmoBox());
            //terminatorClient.getPlayerBoard().setMarkedDamages(AlphaGame.getPlayerForColor(terminatorColor).getPb().getMarkedDamages());
            //if(AlphaGame.getPlayerForColor(terminatorColor).getPlayerPosition() == null)
                terminatorClient.setPlayerPosition(null);
            //else
            //    terminatorClient.setPlayerPosition(AlphaGame.getPlayerForColor(terminatorColor).getPlayerPosition().getNum());
            clientModel.setTerminatorClientModel(terminatorClient);
        }
        //Add list of all weapons
        ArrayList<WeaponClient> allWeapons = new ArrayList<>();
        for(Weapon w: Board.getWeapons().getWeapons()) {
            allWeapons.add(setWeaponClient(w));
        }
        for(Weapon w: Board.getSpawnpoint('b').getWeaponSpawnpoint().getWeapons()) {
            allWeapons.add(setWeaponClient(w));
        }
        for(Weapon w: Board.getSpawnpoint('r').getWeaponSpawnpoint().getWeapons()) {
            allWeapons.add(setWeaponClient(w));
        }
        for(Weapon w: Board.getSpawnpoint('y').getWeaponSpawnpoint().getWeapons()) {
            allWeapons.add(setWeaponClient(w));
        }
        clientModel.setAllWeapons(allWeapons);
        //Add list of all powerups
        ArrayList<PowerupClient> allPowerups = new ArrayList<>();
        for (Powerup pu : Board.getPowerups().getPowerups()) {
            PowerupClient puc = setPowerupClient(pu);
            if (!allPowerups.contains(puc))
                allPowerups.add(puc);
        }
        clientModel.setAllPowerups(allPowerups);
        //Set killshot track
        clientModel.setKillShotTrack(Board.getKillShotTrackRMX());
        //Score
        clientModel.setPlayersPoint(AlphaGame.getPlayersPoint());
    }

    public static void setUpdateTerminator(boolean update){
        updateTerminator = update;
    }

    public void updateClientModel() {        //sistemare update terminatorClient
        if (!winners.isEmpty())
            clientModel.setWinners(winners);
        updateMap();
        clientModel.setMap(map);
        if (clientModel.getMap().getSquares().isEmpty())
            createSquareClient();
        //clientModel.setTerminator(terminator);
        for(User u: clients) {
            RealPlayerClientModel p = clientModel.getPlayer(u.getPlayerName());
            if(u.getPlayer().getPlayerPosition() != null) {
                p.setPlayerPosition(u.getPlayer().getPlayerPosition().getNum());
                p.setFirstTurn(false);
            }
            p.setDisconnected(u.isDisconnected());
            p.setScore(u.getPlayer().getScore());
            p.getPlayerBoard().setAmmoBox(u.getPlayer().getPb().getAmmoBox());
            p.getPlayerBoard().setMarkedDamages(u.getPlayer().getPb().getMarkedDamages());
            p.getPlayerBoard().setDamages(u.getPlayer().getPb().getDamages());
            p.getPlayerBoard().setMarkedDamages(u.getPlayer().getPb().getMarkedDamages());
            p.getPlayerBoard().setSide(u.getPlayer().getPb().getSide());
            p.getPlayerBoard().setPointsToAssignSide1(u.getPlayer().getPb().getPointsToAssignSide1());
            p.getPlayerBoard().setPointsToAssignSide2(u.getPlayer().getPb().getPointsToAssignSide2());
            p.setTerminator(u.getPlayer().getTerminator());
            p.getPlayerBoard().setnDeath(u.getPlayer().getPb().getnDeath());

            ArrayList<WeaponClient> weapons = new ArrayList<>();
            for(Weapon w: u.getPlayer().getPh().getWeaponDeck().getWeapons()) {
                weapons.add(setWeaponClient(w));
            }
            p.getPlayerHand().setWeapons(weapons);

            ArrayList<PowerupClient> powerups = new ArrayList<>();
            for(Powerup powerup: u.getPlayer().getPh().getPowerupDeck().getPowerups()) {
                PowerupClient pu = new PowerupClient();
                pu.setName(powerup.getName());
                pu.setColor(powerup.getColor());
                powerups.add(pu);
            }
            p.getPlayerHand().setPowerups(powerups);
            p.setYourTurn(u.getPlayer().getTurn());

        }

        //Set killshot track
        clientModel.setKillShotTrack(Board.getKillShotTrackRMX());

        //Score
        clientModel.setPlayersPoint(AlphaGame.getPlayersPoint());

        //Terminator
        if(terminator && updateTerminator) {
            clientModel.getTerminatorClientModel().getPlayerBoard().setDamages(AlphaGame.getPlayerForColor(terminatorColor).getPb().getDamages());
            clientModel.getTerminatorClientModel().getPlayerBoard().setAmmoBox(AlphaGame.getPlayerForColor(terminatorColor).getPb().getAmmoBox());
            clientModel.getTerminatorClientModel().getPlayerBoard().setMarkedDamages(AlphaGame.getPlayerForColor(terminatorColor).getPb().getMarkedDamages());
            if(AlphaGame.getPlayerForColor(terminatorColor).getPlayerPosition() == null)
                clientModel.getTerminatorClientModel().setPlayerPosition(null);
            else
                clientModel.getTerminatorClientModel().setPlayerPosition(AlphaGame.getPlayerForColor(terminatorColor).getPlayerPosition().getNum());
        }

        clientModel.setReaminingSkulls(Board.getKillShotTrack());
        clientModel.setKillShotTrack(Board.getKillShotTrackRMX());
        clientModel.setKillShotTrackFF(Board.getKillShotFF());
        clientModel.setFinalFrenzy(AlphaGame.isFinalFrenzy());

        ArrayList<WeaponClient> blueSpawnpointWeapons = new ArrayList<>();
        ArrayList<WeaponClient> redSpawnpointWeapons = new ArrayList<>();
        ArrayList<WeaponClient> yellowSpawnpointWeapons = new ArrayList<>();

        for(Weapon w: Board.getSpawnpoint('b').getWeaponSpawnpoint().getWeapons()) {
            if (w != null)
                blueSpawnpointWeapons.add(setWeaponClient(w));
            else
                blueSpawnpointWeapons.add(null);
        }
        clientModel.getMap().setBlueSpawnpoint(blueSpawnpointWeapons);


        for(Weapon w: Board.getSpawnpoint('r').getWeaponSpawnpoint().getWeapons()) {
            if (w != null)
                redSpawnpointWeapons.add(setWeaponClient(w));
            else
                redSpawnpointWeapons.add(null);
        }
        clientModel.getMap().setRedSpawnpoint(redSpawnpointWeapons);

        for(Weapon w: Board.getSpawnpoint('y').getWeaponSpawnpoint().getWeapons()) {
            if (w != null)
                yellowSpawnpointWeapons.add(setWeaponClient(w));
            else
                yellowSpawnpointWeapons.add(null);         }
        clientModel.getMap().setYellowSpawnpoint(yellowSpawnpointWeapons);

        setAmmoTiles();

        if (Board.getPowerups().getPowerups().isEmpty())
            clientModel.setPowerup(false);
        if (Board.getWeapons().getWeapons().isEmpty())
            clientModel.setWeapon(false);
        if (Board.getAmmoDeck().getAmmo().isEmpty())
            clientModel.setAmmo(false);
        else
            clientModel.setAmmo(true);
        if (Board.getDiscardedAmmo().getAmmo().isEmpty())
            clientModel.setDiscardedAmmo(false);
        else
            clientModel.setDiscardedAmmo(true);
    }

    private static void createSquareClient(){
        for (Square s : Board.getSquares()) {
            if (s.getRoom() != -1) {
                String ammo;
                if (s.getAmmo().getPowerup())
                    ammo = "ammo/AD_ammo_" + s.getAmmo().getCubes()[0] + s.getAmmo().getCubes()[1] + s.getAmmo().getCubes()[2] + "p.png";
                else
                    ammo = "ammo/AD_ammo_" + s.getAmmo().getCubes()[0] + s.getAmmo().getCubes()[1] + s.getAmmo().getCubes()[2] + ".png";
                clientModel.getMap().getSquares().add(new SquareClientModel(s.getNum(), ammo, true));
            }else
                clientModel.getMap().getSquares().add(new SquareClientModel(s.getNum(), null, false));
        }
    }

    private static void setAmmoTiles(){
        String ammo;
        for (int i = 0; i < 9; i++){
            Square s = Board.getSquares().get(i);
            if (s.getRoom() != -1){
                if (s.getAmmo() == null)
                    ammo = "NO_IMAGE";
                else {
                    if (s.getAmmo().getPowerup())
                        ammo = "ammo/AD_ammo_" + s.getAmmo().getCubes()[0] + s.getAmmo().getCubes()[1] + s.getAmmo().getCubes()[2] + "p.png";
                    else
                        ammo = "ammo/AD_ammo_" + s.getAmmo().getCubes()[0] + s.getAmmo().getCubes()[1] + s.getAmmo().getCubes()[2] + ".png";
                }
                clientModel.getMap().getSquares().get(i).setAmmoTile(ammo);
            }
        }
    }

    private static WeaponClient setWeaponClient(Weapon w) {
        WeaponClient weapon = new WeaponClient();
        weapon.setName(w.getName());
        weapon.setCost(w.getCost());
        weapon.setCostOpt(w.getCostOpt());
        weapon.setCostAlt(w.getCostAlt());
        weapon.setLoaded(w.getLoaded());
        weapon.setBaseDescription(w.getBaseDescription());
        if (w.isOpt1Effect())
            weapon.setOpt1Description(w.getOpt1Description());
        if (w.isOpt2Effect())
            weapon.setOpt2Description(w.getOpt2Description());
        if (w.isAltEffect())
            weapon.setAltDescription(w.getAltDescription());
        weapon.setOpt1Effect(w.isOpt1Effect());
        weapon.setOpt2Effect(w.isOpt2Effect());
        weapon.setAltEffect(w.isAltEffect());
        return weapon;
    }

    private static PowerupClient setPowerupClient(Powerup p){
        PowerupClient powerup = new PowerupClient();
        powerup.setColor(p.getColor());
        powerup.setName(p.getName());
        powerup.setDescription(p.getDescription());
        powerup.setPay(p.getPay());
        return powerup;
    }

    public void sendModel(){
        updateClientModel();
        for(User u: getClients()) {
            if (!u.isDisconnected()) {
                if (!u.isRmiConnection()) {
                    u.getConnection().sendModel();
                    if (u.isGuiInterface())
                        messageTo(u.getColor(), "DISABLE_ALL", false);
                } else {
                    try {
                        sendModel(getClientReference(u.getPlayerName()).getClientCallback());
                        if (u.isGuiInterface())
                            messageTo(u.getColor(), "DISABLE_ALL", false);
                    } catch (RemoteException e) {
                        u.setDisconnected(true);
                        //e.printStackTrace();
                    }
                }
            }
        }
    }

    public void waitRefresh(Callback client) {
        try {
            while(!client.waitRefresh()) {

            }
        } catch (RemoteException e) {
            //e.printStackTrace();
        }
    }

    public void sendModelRMX() {
        sendModel();
        if (getTurnClient().isGuiInterface()) {
            if (getTurnClient().isRmiConnection())
                waitRefresh(getClientReference(getTurnClient().getPlayerName()).getClientCallback());
            else if (!getTurnClient().isRmiConnection()) {
                String reply = "WAIT";
                while (reply.equals("WAIT") && !reply.equals("CONTINUE"))
                    reply = messageTo(getTurnClient().getColor(), "CONTINUE", true);
            }
        }
    }

    //GET CLIENT

    public User getClient (String name) {
        for (User u: clients) {
            if(name.equals(u.getPlayerName())) {
                return u;
            }
        }
        return null;
    }

    public User getClientBySocket (Connection connection) {
        for (User u: clients) {
            if(connection == u.getConnection()) {
                return u;
            }
        }
        return null;
    }

    public User getClientByColor (char color) {
        for (User u: clients) {
            if(u.getColor() == color) {
                return u;
            }
        }
        return null;
    }

    public ClientReference getClientReference(String nickname){
        for (ClientReference c : clientRMI)
            if (c.getUsername().equals(nickname))
                return c;
        return null;
    }

    public User getTurnClient() {
        for(User u: clients) {
            if(u.getPlayer().getTurn())
                return u;
        }
        return null;
    }

    public User getClientByCallback(Callback client) {
        for(ClientReference c: clientRMI) {
            if(client == c.getClientCallback())
                return getClient(c.getUsername());
        }
        return null;
    }

    //GETTER

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public ArrayList<User> getClients() {
        return clients;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public static boolean isTerminator() {
        return terminator;
    }

    public static char getTerminatorSpawnpoint() {
        return terminatorSpawnpoint;
    }

    public static char getTerminatorColor() {
        return terminatorColor;
    }

    public static int getMapChoice() {
        return mapChoice;
    }

    public static Map getMap() {
        return map;
    }

    public static int getSkullsNumber() {
        return skullsNumber;
    }

    public static ClientModel getClientModel() {
        return clientModel;
    }

    public ArrayList<String> getAvailableColors() {
        ArrayList<String> colors = new ArrayList<>();
        if(!colorAlreadyTaken('b'))
            colors.add("blue");
        if(!colorAlreadyTaken('e'))
            colors.add("emerald");
        if(!colorAlreadyTaken('g'))
            colors.add("grey");
        if(!colorAlreadyTaken('v'))
            colors.add("violet");
        if(!colorAlreadyTaken('y'))
            colors.add("yellow");
        return colors;
    }

    public static int getReplyTimer() {
        return replyTimer;
    }

    //SETTER

    public static void setTerminator(boolean t) {
        terminator = t;
    }

    public static void setTerminatorColor(char terminatorColor) {
        Server.terminatorColor = terminatorColor;
    }

    public static void setTerminatorSpawnpoint(char terminatorSpawnpoint) {
        Server.terminatorSpawnpoint = terminatorSpawnpoint;
    }

    public static void setSkullsNumber(int skullsNumber) {
        Server.skullsNumber = skullsNumber;
    }

    public static void setMapChoice(int mapChoice) {
        Server.mapChoice = mapChoice;
    }

    public static void setMap(Map map) {
        Server.map = map;
    }

    public static void setClientModel(ClientModel clientModel) {
        Server.clientModel = clientModel;
    }

    public void setReady(String name) {
        getClient(name).setReady(true);
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public static void setWinners(ArrayList<String> w) {
        winners = w;
    }

    public static void setRmiReply(String rmiReply) {
        Server.rmiReply = rmiReply;
    }

    public static void setTimeout(boolean timeout) {
        Server.timeout = timeout;
    }

}