package it.polimi.ingsw.controller;

import it.polimi.ingsw.connections.User;
import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.AlphaGame;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.player.Terminator;
import it.polimi.ingsw.connections.Server;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.*;
import static it.polimi.ingsw.model.game.AlphaGame.setFinalFrenzy;

public class GameController extends Server implements Runnable {

    private static final String CHOICESTRING = "Please, type your choice";
    private static final String ERRORSTRING = "Wrong choice, please try again";
    private static final String ACTIONDONESTRING = "Action done";
    private static final String TIMEOUT = "Time out!";
    private static final String DISABLE_ALL = "DISABLE_ALL";
    private static final String ENABLE_ALL_ACTION = "ENABLE_ALL_ACTION";
    private static final String DISABLE_ALL_ACTION = "DISABLE_ALL_ACTION";
    private static final String ENABLE_RELOAD = "ENABLE_RELOAD";
    private static final String DISABLE_RELOAD = "DISABLE_RELOAD";
    private static final String ENABLE_SELECT_POWERUP_HAND = "ENABLE_SELECT_POWERUP_HAND";
    private static final String DISABLE_SELECT_POWERUP_HAND = "DISABLE_SELECT_POWERUP_HAND";
    private static final String ENABLE_SELECT_PLAYER = "ENABLE_SELECT_PLAYER";
    private static final String DISABLE_SELECT_PLAYER = "DISABLE_SELECT_PLAYER";
    private static final String ENABLE_USE_PU = "ENABLE_USE_PU";
    private static final String ENABLE_END = "ENABLE_END";
    private static final String ONLY_NAME = "ONLY_NAME";
    private static final String ONLY_COLOR = "ONLY_COLOR";
    private static final String ENABLE_SQUARE = "ENABLE_SQUARE";
    private static final String DISABLE_SQUARE = "DISABLE_SQUARE";
    private boolean terminatorAction = false;
    private ArrayList<RealPlayer> players = new ArrayList<>();
    private TurnManagement tm = new TurnManagement();
    private CardsManagement cm = new CardsManagement();
    private int index;
    private Terminator temp;
    private AlphaGame game;

    /**
     * First settings at the beginning of the game; it creates all the players and, eventually calls the terminatorCreation method;
     * it also calls the methods which creates the client version of the model; in addition it calls the methods which deal with the
     * first turns and settings, the entire game turns management and finally it manages the frenetic actions and calls the endGame method
     */
    public void
    run() {
        messageToAll("Welcome to Adrenaline!");

        //RealPlayer creation -> add players for each connection

        for (User u : getClients()) {
            players.add(u.getPlayer());
        }

        ArrayList<Player> actionFigures = new ArrayList<>();
        actionFigures.addAll(players);
        messageToAll("Players created");


        //Board creation
        game = new AlphaGame(Server.getMapChoice(), actionFigures, Server.isTerminator(), Server.getSkullsNumber());
        messageToAll("Game created");

        //Creates the client version of the model
        createClientModel();
        Server.createMap();
        players.get(0).setTurn(true);
        sendModel();
        //updateClientModel();

        //Players setting && first turn
        //sendModel();
        messageToAll("Displaying board");       //Necessary for GUI, add boolean (only for gui)

        for (User u : getClients()) {
            if (u.isGuiInterface())
                messageTo(u.getColor(), DISABLE_ALL, false);
        }

        Terminator t = playerSetting(players, actionFigures);
        updateClientModel();

        //Turns management
        int i = gameShifts(actionFigures, t);

        if(i == 10) //not enough players (<=2)
            endGame(game);
        else{
            boolean exit;
            players.get(i).setTurn(false);
            respawn(true);
            players.get(i).setTurn(true);
            sendModelRMX();

            //Frenetic Actions
            setFinalFrenzy(true);
            if (i != 0) {                                           //need to know which is the player from whom Frenetic Actions start with
                int j;
                for (j = i; j < players.size(); j++) {
                    players.get(j).setFf2actions(true);
                    players.get(j).getPb().rotateSide();
                }
                for(j = 0 ; j !=i; j++)
                    players.get(j).getPb().rotateSide();
            } else {
                for (int j = i; j < players.size(); j++) {
                    players.get(j).getPb().rotateSide();
                }
            }
            for (int j = i, count = 0; count < players.size(); j++, count++) {  //doing a complete shift turn
                ExecutorService executor = Executors.newSingleThreadExecutor();
                players.get(j).setTurn(false);
                respawn(true);
                players.get(j).setTurn(true);
                //resetTimeout(j);
                getClientByColor(players.get(j).getColor()).setTurnOver(false);
                initShiftSetting(j, players);
                index = j;
                Thread frenetic = new Thread() {
                    public void run(){
                        Future<String> future = executor.submit(new Callable() {
                            @Override
                            public String call() throws Exception {
                                while (true) {
                                    tm.turnMenuFrenetic(players.get(index), actionFigures);
                                    powerupChoice(index, actionFigures, false);
                                    return messageTo(players.get(index).getColor(), "Waiting for the next turn...", false);
                                }
                            }
                        });
                        try {
                            future.get(getTurnTimer()*1000, TimeUnit.MILLISECONDS);
                        } catch (ExecutionException | InterruptedException | TimeoutException e) {
                            getClientByColor(players.get(index).getColor()).setTurnOver(true);
                            getClientByColor(players.get(index).getColor()).setDisconnected(true);
                            messageTo(players.get(index).getColor(), TIMEOUT, false);
                            future.cancel(true);
                        }
                    }
                };
                frenetic.start();
                try{
                    frenetic.join();
                }catch (InterruptedException e){}
                pingAll();
                sendModelRMX();
                exit = checkEnoughPlayers();
                if (!exit) {
                    messageToAll("There are only 2 players connected");
                    break;
                }
                Board.replaceWeaponGrabbed();
                Board.replaceAmmoGrabbed();
                if (j == players.size() - 1)
                    j = -1;
            }
            sendModelRMX();
            endGame(game);
        }
    }

    //Specific methods
    /**
     * Deals with the first player settings and the first turn of each player
     *
     * @param players           an ArrayList of RealPlayer
     * @param actionFigures     an ArrayList of actionFigures
     * @return                  a Terminator, which is null if it has not been created
     */
    private Terminator playerSetting(ArrayList<RealPlayer> players, ArrayList<Player> actionFigures) {
        int i;
        boolean exit = true;
        Terminator t = null;
        for (i = 0; i < players.size(); i++) {
            setTimeout(false);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            System.out.println("player size: " + players.size());
            System.out.println(players.get(i).getName() + " disconnected: " + getClientByColor(players.get(i).getColor()).isDisconnected());
            if (players.get(i) == players.get(0) && !getClientByColor(players.get(0).getColor()).isDisconnected()){
                messageTo(players.get(0).getColor(), "Take care! You have " + getTurnTimer() / 60 + " minutes to complete your turn!", false);
                setTimeout(false);
                index = i;
                //Thread turn = new Thread() {
                //    public void run(){
                    Future<String> future = executor.submit(new Callable() {
                        @Override
                        public String call() throws Exception {
                            temp = positionSetting(index, actionFigures);
                            initShiftSetting(index, players);
                            tm.firstTurnManagement(players, actionFigures);
                            powerupChoice(index, actionFigures, true);
                            pingAll();

                            if (!afterTimeout)
                                return messageTo(players.get(index).getColor(), "Waiting for the next turn...", false);
                            return null;
                        }
                    });
                    try {
                        future.get(getTurnTimer()*1000, TimeUnit.MILLISECONDS);
                    } catch (ExecutionException  | InterruptedException | TimeoutException e) {
                        messageTo(players.get(0).getColor(), TIMEOUT, false);
                        setTimeout(true);
                        if(players.get(0).getPh().getPowerupDeck().getSize() == 2) {
                            players.get(0).getPh().discard(players.get(0).getPh().getPowerupDeck().getPowerups().get(0)); //discard the first
                            players.get(0).initPosition(players.get(0).getPh().getPowerupDeck().getPowerups().get(0).getName(), players.get(0).getPh().getPowerupDeck().getPowerups().get(0).getColor());
                        }
                        players.get(index).setTurn(false);
                        future.cancel(true);
                    }
                    sendModel();
                    //}
                //};
                //turn.start();
                //try{
                //    turn.join();
                //}catch (InterruptedException e){}
            }
            else if(!getClientByColor(players.get(i).getColor()).isDisconnected()){
                setTimeout(false);
                index = i;
                temp = t;
                messageTo(players.get(i).getColor(), "Take care! You have " + getTurnTimer() / 60000 + " minutes to complete your turn!", false);
                Thread turn = new Thread() {
                    public void run(){
                    Future<String> future = executor.submit(new Callable() {
                        @Override
                        public String call() throws Exception {
                            temp = positionSetting(index, actionFigures);
                            initShiftSetting(index, players);
                            typeOfTurn(index, actionFigures, temp);
                            powerupChoice(index, actionFigures, true);
                            pingAll();
                            if (!afterTimeout)
                                return messageTo(players.get(index).getColor(), "Waiting for the next turn...", false);
                            return null;
                        }
                    });
                    try {
                        future.get(getTurnTimer()*1000, TimeUnit.MILLISECONDS);
                    } catch (ExecutionException  | InterruptedException | TimeoutException e) {
                        setTimeout(true);
                        messageTo(players.get(index).getColor(), TIMEOUT, false);
                        if(players.get(index).getPlayerPosition() == null && players.get(index).getPh().getPowerupDeck().getSize() == 2){
                                players.get(index).getPh().discard(players.get(index).getPh().getPowerupDeck().getPowerups().get(index)); //discard the first
                                players.get(index).initPosition(players.get(index).getPh().getPowerupDeck().getPowerups().get(index).getName(), players.get(index).getPh().getPowerupDeck().getPowerups().get(index).getColor());
                            }
                        }
                        players.get(index).setTurn(false);
                        future.cancel(true);
                    }
                };
                turn.start();
                try{
                    turn.join();
                }catch (InterruptedException e){}
            }
            else{
                players.get(i).getPh().drawPowerup(Board.getPowerups());
                players.get(i).setPlayerPosition(Board.getSpawnpoint('b'));
            }
            pingAll();
            t = temp;
            players.get(i).setTurn(false);
            sendModel();
            executor.shutdown();
            exit = checkEnoughPlayers();
            if(!exit){
                messageToAll("There are only 2 players connected");
                break;
            }
            Board.replaceAmmoGrabbed();
            Board.replaceWeaponGrabbed();
        }
        if(!exit)
            endGame(game);
        return t;
    }

    /**
     * Sets the initial position of the player, according to the user choices
     * @param i                 an int
     * @param actionFigures     an ArrayList of actionFigures
     * @return                  a Terminator, which is null if it has not been created
     */
    private Terminator positionSetting(int i, ArrayList<Player> actionFigures) {
        if(afterTimeout){
            afterTimeout = false;
            return null;
        }
        players.get(i).setTurn(true);
        players.get(i).initPlayer(); //draw 2 powerups
        sendModelRMX();
        String powerupName;
        String powerupColor = null;
        if(i==0 && Server.isTerminator()){
            //Terminator creation
            temp = terminatorCreation(actionFigures);
            Server.setUpdateTerminator(true);
        }
        if (players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getName().equals(players.get(i).getPh().getPowerupDeck().getPowerups().get(1).getName()) && players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getColor() != players.get(i).getPh().getPowerupDeck().getPowerups().get(1).getColor()) {
            powerupName = players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getName();
            if (getClientByColor(players.get(i).getColor()).isGuiInterface())
                messageTo(players.get(i).getColor(), ONLY_COLOR, false);
            powerupColor = choicePowerupColor(players.get(i));
        } else {
            if (players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getName().equals(players.get(i).getPh().getPowerupDeck().getPowerups().get(1).getName()) && players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getColor() == players.get(i).getPh().getPowerupDeck().getPowerups().get(1).getColor()) {
                messageTo(players.get(i).getColor(), "You had two equals " + cm.identifyColor(players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getColor()) + " " + players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getName(), false);
                powerupName = players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getName();
                powerupColor = cm.identifyColor(players.get(i).getPh().getPowerupDeck().getPowerups().get(0).getColor());
            } else {
                if (getClientByColor(players.get(i).getColor()).isGuiInterface())
                    messageTo(players.get(i).getColor(), ONLY_NAME, false);
                powerupName = choicePowerupName(players.get(i));
                for (Powerup pu : players.get(i).getPh().getPowerupDeck().getPowerups()) {
                    if (pu.getName().equals(powerupName)) {
                        powerupColor = cm.identifyColor(pu.getColor());
                    }
                }
            }
        }
        if (!getClientByColor(players.get(i).getColor()).isGuiInterface())
            messageTo(players.get(i).getColor(), "Your position has been set: you are in the " + powerupColor + " spawnpoint", false);
        players.get(i).initPosition(powerupName, powerupColor.charAt(0));
        powerupChoiceToOther(players.get(i), powerupName, powerupColor);

        //if(!getClientByColor(players.get(i).getColor()).isGuiInterface())
        //    screamToOthers(players.get(i), messageTo(players.get(i).getColor(), "Scare your enemies before starting the game! Shout at them with your personal battle scream!", true));
        if (!getClientByColor(players.get(i).getColor()).isGuiInterface())
            messageTo(players.get(i).getColor(), "Waiting for the game to start...", false);
        sendModel();
        return temp;
    }

    /**
     * Method which is eventually called in case of a Terminator mode-game; it creates the terminator and adds it to actionFigures
     *
     * @param actionFigures     an ArrayList of Player
     * @return                  a Terminator
     */
    private Terminator terminatorCreation(ArrayList<Player> actionFigures) {
        Terminator t = null;
        if (Server.isTerminator()) {
            setTerminatorSpawn(players.get(0));
            sendModel();
            t = new Terminator(Server.getTerminatorColor(), Board.getSpawnpoint(Server.getTerminatorSpawnpoint()));
            t.setInitColor(Server.getTerminatorColor());
            AlphaGame.setTerminatorColor(Server.getTerminatorColor());
            actionFigures.add(t);
            messageToAll("Terminator created");
            setTerminatorAction(false);
            for (RealPlayer rp : players)
                rp.setTerminator(false);
        }
        return t;
    }

    /**
     * Asks the first player to choose the position of the Terminator
     *
     * @param p         the player which is asked to place the Terminator
     */
    private void setTerminatorSpawn(RealPlayer p){
        String col;
        if (!getClientByColor(p.getColor()).isGuiInterface())
            col = messageTo(p.getColor(), "Please select the Terminator spawnpoint square (2, 4, 11)", true);
        else {
            messageTo(p.getColor(), ENABLE_SQUARE, false);
            col = messageTo(p.getColor(), "Please select the Terminator spawnpoint square", true);
        }
        while (!col.equals("2") && !col.equals("4") && !col.equals("11")){
            if(getClientByColor(p.getColor()).isTurnOver())
                return;
            col = messageTo(p.getColor(), ERRORSTRING, true);

        }
        if (col.equals("2"))
            Server.setTerminatorSpawnpoint('b');
        else if (col.equals("4"))
            Server.setTerminatorSpawnpoint('r');
        else
            Server.setTerminatorSpawnpoint('y');
        if (getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_SQUARE, false);
    }

    /**
     * Manages all the "normal" turns of the users
     *
     * @param actionFigures     an ArrayList of Player
     * @param t                 a Terminator
     * @return                  an int, which is the index of the last player before the beginning of the frenetic actions; if it returns '10' it means that there aren't enough players
     *                          (minus or equals to 2) to continue the game
     */
    private int gameShifts(ArrayList<Player> actionFigures, Terminator t) {
        int i = 0;
        boolean exit = true;
        for (; Board.getKillShotTrack() > 0; i++) {
            //resetTimeout(i);
            //pingAll();
            if(getClientByColor(players.get(i).getColor()).isTurnOver()){
                getClientByColor(players.get(i).getColor()).setTurnOver(false);
                getClientByColor(players.get(i).getColor()).setDisconnected(false);
            }
            if(!getClientByColor(players.get(i).getColor()).isDisconnected()) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                initShiftSetting(i, players);
                players.get(i).setTurn(false); //before possible respawns
                respawn(false);
                players.get(i).setTurn(true);  //after possible respawns
                sendModelRMX();
                if (getClientByColor(players.get(i).getColor()).isGuiInterface())
                    messageTo(players.get(i).getColor(), DISABLE_RELOAD, false);
                index = i;
                temp = t;
                Thread turn = new Thread() {
                    @Override
                    public void run() {
                    Future < String > future = executor.submit(new Callable() {
                        @Override
                        public String call() throws Exception {
                            typeOfTurn(index, actionFigures, temp);
                            powerupChoice(index, actionFigures, false);
                            if(!afterTimeout)
                                return messageTo(players.get(index).getColor(), "Waiting for the next turn...", false);
                            return null;
                        }
                    });
                    try {
                        future.get(getTurnTimer()*1000, TimeUnit.MILLISECONDS);
                    } catch (ExecutionException | InterruptedException | TimeoutException e) {
                        afterTimeout = true;
                        messageTo(players.get(index).getColor(), TIMEOUT, false);
                        getClientByColor(players.get(index).getColor()).setTurnOver(true);
                        getClientByColor(players.get(index).getColor()).setDisconnected(true);
                        future.cancel(true);
                    }
                    executor.shutdown();
                    }
                };
                turn.start();
                try{
                    turn.join();
                }catch (InterruptedException e){}
            }
            pingAll();
            if (i == players.size() - 1) {
                i = -1;     //i=-1 -> i++ -> i=0 -> first player's turn
            }
            Board.replaceAmmoGrabbed();
            Board.replaceWeaponGrabbed();
            exit = checkEnoughPlayers();
            if(!exit)
                break;
            setTimeout(false);
            sendModelRMX();
        }
        if(!exit)
            return 10; //indicates that there aren't enough player to continue the game (<=2)
        return i;
    }

    /**
     * Manages all the settings at the beginning of each turn
     *
     * @param i             an int
     * @param players       an ArrayList of RealPlayer
     */
    private void initShiftSetting(int i, ArrayList<RealPlayer> players) {
        if (i == 0) {
            players.get(players.size() - 1).setTurn(false);
            players.get(i).setTurn(true);
            sendModelRMX();
        } else {
            players.get(i - 1).setTurn(false);
            players.get(i).setTurn(true);
            sendModelRMX();
        }

        players.get(i).getPb().setDoubleKill(false);
        players.get(i).getPb().setNoMorePoints(false);

    }

    /**
     * Manages the actions of a player in Terminator mode
     *
     * @param i                 an int
     * @param actionFigures     an ArrayList of Player
     * @param t                 a Terminator
     */
    private void playingWithTerminator(int i, ArrayList<Player> actionFigures, Terminator t) {
        players.get(i).setTerminator(true);
        if (i == 0)
            players.get(players.size() - 1).setTerminator(false);
        else
            players.get(i - 1).setTerminator(false);
        setTerminatorAction(false);
        sendModelRMX();
        t.setOwnerColor(players.get(i).getColor());
        if(getClientByColor(players.get(i).getColor()).isGuiInterface())
            messageTo(players.get(i).getColor(), ENABLE_ALL_ACTION, false);
        int count = 0;
        while (count < 3) {
            if (t.getPb().isDead()) {
                messageTo(t.getOwnerColor(), "Terminator died! It has to respawn", false);
                t.respawn();
                t.getPb().setAlive();
                sendModelRMX();
                messageToAll("Terminator died! It has to respawn");
            }
            int actionChoice;
            if (!terminatorAction) {
                actionChoice = terminatorActionNotDoneYet(i, count, actionFigures, t);
                if (actionChoice == 0)
                    count--;
            } else {
                actionChoice = actionsInTurnManagement(i, actionFigures);
                sendModelRMX();
                if (actionChoice == 0)
                    count--;
            }
            count++;
        }
        if(getClientByColor(players.get(i).getColor()).isGuiInterface())
            messageTo(players.get(i).getColor(), DISABLE_ALL_ACTION, false);
        players.get(i).setTerminator(false);
    }

    /**
     * Manages the actions of a player in Terminator mode when a terminator action has not been done yet
     *
     * @param i                 an int
     * @param count             an int
     * @param actionFigures     an ArrayList of Player
     * @param t                 a Terminator
     * @return                  an int
     */
    private int terminatorActionNotDoneYet(int i, int count, ArrayList<Player> actionFigures, Terminator t) {
        int actionChoice;
        int choice = 0;
        int flag = 0;
        String choice2;
        if (count <= 1) {
            if (!getClientByColor(players.get(i).getColor()).isGuiInterface()) {
                messageTo(players.get(i).getColor(), "If you want, you can do a normal action", false);
                tm.turnOnlyMenu(players.get(i), false);
                messageTo(players.get(i).getColor(), "\nOtherwise, you can do a terminator action typing 'terminator'", false);
            }
            else
                tm.turnOnlyMenu(players.get(i), false);
            while(true){
                if (!getClientByColor(players.get(i).getColor()).isGuiInterface())
                    choice2 = messageTo(players.get(i).getColor(), CHOICESTRING, true).toLowerCase();
                else
                    choice2 = messageTo(players.get(i).getColor(), "Choose an action", true);
                if (choice2.equals("terminator")) {
                    messageTo(players.get(i).getColor(), "Terminator action selected", false);
                    terminatorAction(i, t);
                    sendModelRMX();
                    return 1;
                }
                else{
                    while(true){
                        try{
                            choice = tryParse(choice2);
                            break;
                        }catch (WrongValueException wVE){
                            if(getClients().get(i).isTurnOver())
                                return -1;
                            messageTo(players.get(i).getColor(), ERRORSTRING, false);
                            flag = 1;
                            break;
                        }
                    }
                    if(!tm.choiceControl(players.get(i), choice, false)){
                        if(flag != 1)
                            messageTo(players.get(i).getColor(), ERRORSTRING, false);
                    }
                    else{
                        actionChoice = tm.turnManagement(players.get(i), actionFigures, false, choice);
                        if (actionChoice == 0) {
                            messageTo(players.get(i).getColor(), ERRORSTRING, false);
                            return 0;
                        } else {
                            sendModelRMX();
                            if (!getClientByColor(players.get(i).getColor()).isGuiInterface())
                                messageTo(players.get(i).getColor(), ACTIONDONESTRING, false);
                            return 1;
                        }
                    }
                }
            }
        } else {
            sendModelRMX();
            powerupChoice(i, actionFigures, false);
            terminatorAction(i, t);
            powerupChoice(i, actionFigures, false);
            return 1;
        }
    }

    /**
     * Manages a terminator action, calling the specific methods
     * @param i             an int
     * @param t             a Terminator
     */
    private void terminatorAction(int i, Terminator t) {
        int done;
        while (true) {
            if (getClientByColor(players.get(i).getColor()).isGuiInterface()) {
                messageTo(players.get(i).getColor(), DISABLE_ALL, false);
                messageTo(players.get(i).getColor(), ENABLE_SQUARE, false);
                messageTo(players.get(i).getColor(), ENABLE_SELECT_PLAYER, false);
            }
            done = tm.turnManagement(t, players);
            if (done != 0) {
                setTerminatorAction(true);
                if (!getClientByColor(players.get(i).getColor()).isGuiInterface())
                    messageTo(players.get(i).getColor(), ACTIONDONESTRING, false);
                if (getClientByColor(players.get(i).getColor()).isGuiInterface()) {
                    messageTo(players.get(i).getColor(), DISABLE_SQUARE, false);
                    messageTo(players.get(i).getColor(), DISABLE_SELECT_PLAYER, false);
                }
                break;
            } else
                messageTo(t.getOwnerColor(), ERRORSTRING, false);
        }
    }

    /**
     * Manages the actions of a player in a not-Terminator mode
     *
     * @param i                 an int
     * @param actionFigures     an ArrayList of Player
     */
    private void playingWithoutTerminator(int i, ArrayList<Player> actionFigures) {
        int choice;
        int count = 0;
        while (count < 2) {
            choice = actionsInTurnManagement(i, actionFigures);
            if (choice == 0)
                count--;
            if (!getClientByColor(players.get(i).getColor()).isGuiInterface())
                messageTo(players.get(i).getColor(), ACTIONDONESTRING, false);
            sendModelRMX();
            count++;
        }
    }

    /**
     * Manages eventually a "powerup action"
     *
     * @param i                 an int
     * @param actionFigures     an ArrayList of Player
     * @param firstTurn         a boolean
     */
    private void powerupChoice(int i, List<Player> actionFigures, boolean firstTurn) {
        String choice;
        int num;
        if(afterTimeout){
            afterTimeout = false;
            return;
        }
        while(players.get(i).getPh().hasAlwaysUsablePowerup() && (!firstTurn || (firstTurn && ((!players.get(i).getPh().hasPowerup("newton") || (players.get(i).getPh().hasPowerup("newton") && (players.get(i).getPh().hasPowerup("teleporter")|| Server.isTerminator()))))))) {
            players.get(i).setTurn(true);
            sendModelRMX()
            ;
            while (true) {
                if(getClientByColor(players.get(i).getColor()).isGuiInterface()){
                    messageTo(players.get(i).getColor(), ENABLE_USE_PU, false);
                    messageTo(players.get(i).getColor(), ENABLE_END, false);
                    choice = messageTo(players.get(i).getColor(), "If you want, you can use one of your powerup; otherwise press End", true);
                }
                else
                    choice = messageTo(players.get(i).getColor(), "If you want to use one of your powerup type '4'; otherwise type 'end'", true);
                if (!choice.equals("end")) {
                    try {
                        num = tryParse(choice);
                        if (num == 4)
                            break;
                        else{
                            if (!getClientByColor(players.get(i).getColor()).isGuiInterface())
                                messageTo(players.get(i).getColor(), ERRORSTRING, false);
                        }
                    } catch (WrongValueException wVE) {
                        if(getClients().get(i).isTurnOver())
                            return;
                        if (!getClientByColor(players.get(i).getColor()).isGuiInterface())
                            messageTo(players.get(i).getColor(), ERRORSTRING, false);
                    }
                } else
                    return;
            }
            cm.usablePowerup(players.get(i), actionFigures);
            sendModelRMX();
        }
    }

    /**
     * Manages the case in which a player dies and he has to respawn
     *
     * @param finalFrenzy       a boolean
     */
    private void respawn(boolean finalFrenzy){
        for (int j = 0; j < players.size(); j++) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            int amountPowerup = players.get(j).getPh().getPowerupDeck().getSize();
            if (players.get(j).getPb().isDead()) {
                index = j;
                players.get(j).getPb().updateKillshotRMX();
                players.get(j).getPb().afterDeath();
                players.get(j).setTurn(true);
                setTimeout(false);
                sendModelRMX();
                Future<String> future = executor.submit(new Callable() {
                    @Override
                    public String call() throws Exception {
                        tm.respawnManagement(players.get(index));
                        return messageTo(players.get(index).getColor(), "", false);
                    }
                });
                try {
                    future.get(30000, TimeUnit.MILLISECONDS);
                } catch (ExecutionException | InterruptedException | TimeoutException e) {
                    setTimeout(true);
                    char rndSpawnpoint = randomSpawnpointColor();
                    players.get(index).setPlayerPosition(Board.getSpawnpoint(rndSpawnpoint));
                    if(players.get(index).getPh().getPowerupDeck().getSize()>amountPowerup)
                        players.get(index).getPh().discard(players.get(index).getPh().getPowerupDeck().getPowerups().get(0));
                    messageTo(players.get(0).getColor(), TIMEOUT, false);
                    future.cancel(true);
                }
                executor.shutdown();
                if(finalFrenzy)
                    players.get(index).getPb().rotateSide();
                players.get(index).setTurn(false);
                sendModel();
            }
        }
    }

    /**
     * Manages the end of the game, showing the winners and all the scores
     *
     * @param game      an AlphaGame
     */
    private void endGame(AlphaGame game){
        messageToAll("Game Over");

        ArrayList<String> winners = new ArrayList<>();
        Player winner = game.getWinner(); //-> it is necessary to update partial scores in case of no kills

        if (Board.getKillShotTrack() == Server.getSkullsNumber()) {
            messageToAll("There have been no kills at all! Shame on you!");
            int max = 0;
            for (RealPlayer p : players) {
                messageToAll(p.getName() + "'s scores: " + AlphaGame.getPlayersPoint()[PlayerBoard.convertPlayerCharToInt(p.getColor())]);
                if (AlphaGame.getPlayersPoint()[PlayerBoard.convertPlayerCharToInt(p.getColor())] > max)
                    max = AlphaGame.getPlayersPoint()[PlayerBoard.convertPlayerCharToInt(p.getColor())];
            }
            if (winner == null){
                messageToAll("Winners:");
                for (RealPlayer p : players) {
                    if (AlphaGame.getPlayersPoint()[PlayerBoard.convertPlayerCharToInt(p.getColor())] == max) {
                        winners.add(p.getName() + ", the " + cm.identifyColor(p.getColor()) + " player");
                    }
                }
            }
            else
                winners.add("The winner is " + winner.getName() + ", the " + cm.identifyColor(winner.getColor()) + " player");
        }
        else {
            if (winner == null) {
                int max = 0;
                for (int i = 0; i < AlphaGame.getPlayersPoint().length; i++) {
                    if (AlphaGame.getPlayersPoint()[i] > max)
                        max = AlphaGame.getPlayersPoint()[i];
                }
                for (int i = 0; i < AlphaGame.getPlayersPoint().length; i++) {
                    if (AlphaGame.getPlayersPoint()[i] == max)
                        if (PlayerBoard.convertPlayerIntToChar(i) == getTerminatorColor())
                            winners.add("" + temp.getName());
                        else
                            winners.add(""+ getClientByColor(PlayerBoard.convertPlayerIntToChar(i)).getPlayerName());
                }
            } else
                winners.add("The winner is " + winner.getName());
        }
        Server.setWinners(winners);
        updateClientModel();
        for(RealPlayer p : players){
            if(!getClientByColor(p.getColor()).isGuiInterface())
                for(String s : winners)
                    messageTo(p.getColor(), s, false);
        }
        /*for (Player p : players) {
            User u = getClientByColor(p.getColor());
            if (u.isGuiInterface())
                messageTo(p.getColor(), "SHOW_WINNERS", false);
        }
        Server.setWinners(winners);
        updateClientModel();*/
        //System.out.println(getClientModel().getWinners());
        for (RealPlayer p : players) {
            User u = getClientByColor(p.getColor());
            if (u.isGuiInterface()) {
                if (u.isRmiConnection()) {
                    try {
                        sendModel(getCallbackClient(u.getPlayerName()));
                    } catch (RemoteException e) {
                    }
                }else
                    u.getConnection().sendModel();
                messageTo(p.getColor(), "SHOW_WINNERS", false);
            }
        }
    }

    //General management methods
    /**
     * Setter of terminatorAction
     *
     * @param done      a boolean
     */
    private void setTerminatorAction(boolean done) {
        terminatorAction = done;
    }

    /**
     * Asks the player to choose the color of the powerup he wants to discard, because he has two equals powerups of different color
     *
     * @param p             the player which is asked to choose the color
     * @return              a String, the color chosen by the player
     */
    private String choicePowerupColor(RealPlayer p) { //the two powerups have the same name
        boolean end = false;
        String powerupColor = null;
        if(getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), ENABLE_SELECT_POWERUP_HAND, false);
        }
        while (!end) {
            if (!getClientByColor(p.getColor()).isGuiInterface())
                powerupColor = messageTo(p.getColor(), "Choose the specific color of the powerup which you want to discard; your position will be set according to its color and you'll keep the other one", true).toLowerCase();
            else
                powerupColor = messageTo(p.getColor(), "Choose the powerup which you want to discard; your position will be set according to its color and you'll keep the other one", true).toLowerCase();
            String powerupName = p.getPh().getPowerupDeck().getPowerups().get(0).getName();
            if (p.getPh().hasPowerupColor(powerupName, powerupColor))
                end = true;
            else {
                if (!getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), ERRORSTRING, false);
            }
        }
        if(getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
        return powerupColor;
    }

    /**
     * Asks the player to choose the name of the powerup he wants to discard, because he has two different powerups
     *
     * @param p             the player which is asked to choose the color
     * @return              a String, the color chosen by the player
     */
    private String choicePowerupName(RealPlayer p) {
        String powerupName = null;
        boolean end = false;
        if(getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), ENABLE_SELECT_POWERUP_HAND, false);
        }
        while (!end) {
            if (!getClientByColor(p.getColor()).isGuiInterface())
                powerupName = messageTo(p.getColor(), "Choose the Powerup name you want to discard, in order to set your position according to its color; you'll keep the other one", true).toLowerCase();
            else
                powerupName = messageTo(p.getColor(), "Choose the Powerup you want to discard, in order to set your position according to its color; you'll keep the other one", true).toLowerCase();
            if (p.getPh().hasPowerup(powerupName))
                end = true;
            else{
                if(getClientByColor(p.getColor()).isTurnOver())
                    return null;
                if (!getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), ERRORSTRING, false);
            }
        }
        if(getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
        return powerupName;
    }

    /**
     * Returns the name of a User
     *
     * @param p             the player which is managed by the requested user
     * @return              the User
     */
    private String getUserName(RealPlayer p) {
        int i;
        for (i = 0; i < getClients().size(); i++) {
            if (getClients().get(i).getPlayer() == p)
                break;
        }
        return getClients().get(i).getPlayerName();
    }

    /**
     * Notifies the other player which powerup has been discarded by the current one
     *
     * @param p                         the player which has discarded a powerup
     * @param discardName               the name of the discarded powerup
     * @param discardColor              the color of the discarded powerup
     */
    private void powerupChoiceToOther(RealPlayer p, String discardName, String discardColor) {
        String name = getUserName(p);
        for (User u : getClients()) {
            if (u.getPlayer() != p)
                messageTo(u.getColor(), name + " has discarded a " + discardColor + " " + discardName, false);
        }

    }

    /**
     * Manages which type of turn (calling the specific method: terminator-mode or not); in addition, it eventually calls the specific reload method
     *
     * @param i                     an int, the index of the player
     * @param actionFigures         an ArrayList of Player, all the other player in the game (Terminator included)
     * @param t                     the Terminator
     */
    private void typeOfTurn(int i, ArrayList<Player> actionFigures, Terminator t){
        afterTimeout = false;
        if (Server.isTerminator() && !getClientByColor(players.get(i).getColor()).isDisconnected())
            playingWithTerminator(i, actionFigures, t);
        else if (!getClientByColor(players.get(i).getColor()).isDisconnected() && !Server.isTerminator()){
            playingWithoutTerminator(i, actionFigures);
        }
        if (players.get(i).getPh().unloadedWeapons() && players.get(i).checkAllResources()){
            if(getClientByColor(players.get(i).getColor()).isGuiInterface())
                messageTo(players.get(i).getColor(), ENABLE_RELOAD, false);
            tm.reloadChoiceManagement(players.get(i), false);
        }
    }

    /**
     * Calls the specific method in order to give the player the possibility to do his action
     *
     * @param i                         an int, the index of the player
     * @param actionFigures             an ArrayList of Player, all the other players in the game (Terminator included)
     * @return                          an int, which indicates if the action was successful or not
     */
    private int actionsInTurnManagement(int i, ArrayList<Player> actionFigures){
        int actionChoice;
        if(getClientByColor(players.get(i).getColor()).isGuiInterface())
            messageTo(players.get(i).getColor(), ENABLE_ALL_ACTION, false);
        actionChoice = tm.turnManagement(players.get(i), actionFigures, false, 0);
        if(getClientByColor(players.get(i).getColor()).isGuiInterface())
            messageTo(players.get(i).getColor(), DISABLE_ALL_ACTION, false);
        return actionChoice;
    }

    /**
     * Chooses randomly an int in 0,1,2 range, in order to choose a specific SpawnPointSquare color (colors are in alphabetical order)
     *
     * @return              a char, the color of the chosen SpawnPointSquare
     */
    private char randomSpawnpointColor(){
        int rnd = new Random().nextInt(2);
        switch (rnd){
            case 0:
                return 'b';
            case 1:
                return 'r';
            default:
                return 'y';
        }
    }

    /**
     * Checks if the answer given by the user is effectively an int, converting the answer into an Integer
     *
     * @param text                      a String, which represents the answer
     * @return                          an Integer, which is the converted String
     * @throws WrongValueException      if the answer doesn't represent an int
     */
    static Integer tryParse(String text) throws WrongValueException {
        try {
            return Integer.parseInt(text,10);
        } catch (NumberFormatException e) {
            throw new WrongValueException();
        }
    }

    /**
     * Checks if there are more than 2 connected users in game
     *
     * @return                  a boolean
     */
    public boolean checkEnoughPlayers(){
        int count = 0;
        for(User u : getClients())
            if(!u.isDisconnected() || (u.isDisconnected() && u.isTurnOver()))
                count++;
        if(count > 2)
            return true;
        return false;
    }

}