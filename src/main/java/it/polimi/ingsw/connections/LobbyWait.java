package it.polimi.ingsw.connections;

import com.google.gson.Gson;
import it.polimi.ingsw.connections.Server;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LobbyWait extends Server implements Runnable {

    private static final String CNV = "Choice not valid";
    private static final String ERRORSTRING = "Wrong choice, please try again";

    public static Timer timer;

    /**
     * During the login phase keeps checking if the game is able to start depending on the number of player connected
     * and if they are ready. Starts a timer as soon as at least 3 players are connected and ready and reset the timer
     * if someone joins or disconnects
     *
     */

    public void run() {
        System.out.println("Lobby started: Waiting for enough players...");
        try {
            while (!isGameRunning()) {
                Thread.sleep(500);
                if (getClients().size() >= 3 && checkReadyPlayers()) {
                    System.out.println("Game is ready to start, setting a " + getLobbyDelay() + " seconds timer");
                    for (User u : getClients()) {
                        if (u.isGuiInterface())
                            messageTo(u.getColor(), "TIMER " + getLobbyDelay(), false);
                        else
                            messageTo(u.getColor(), "Game is ready to start, setting a " + getLobbyDelay() + " seconds timer", false);
                    }

                    startTimer(getLobbyDelay());
                    while (true) {
                        if (getClients().size() == 5 && checkReadyPlayers()) {
                            Server.setTerminator(false);
                            timer.cancel();
                            gameSettings(getClients().get(0));
                            startGame();
                            break;
                        } else if (checkResetTimer()) {
                            if(getClients().size() < 3)
                                System.out.println("Not enough players, resetting the " + getLobbyDelay() + "timer");
                            System.out.println("Resetting the " + getLobbyDelay() + "timer");
                            for (User u : getClients()) {
                                if (u.isGuiInterface())
                                    messageTo(u.getColor(), "RESET", false);
                                else {
                                    if(getClients().size() < 3)
                                        messageTo(u.getColor(), "Not enough players, resetting the " + getLobbyDelay() + " timer", false);
                                    else
                                        messageTo(u.getColor(), "Resetting the " + getLobbyDelay() + " timer", false);
                                }
                            }
                            timer.cancel();
                            break;
                        } else if (isGameRunning()) {
                            break;
                        }
                    }
                }
            }
            System.out.println("Game started, closing lobby...");
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    //GAME SETTINGS

    /**
     * Asks the User which game setting to play the game with
     *
     * @param u     first user in the list of connected players
     */

    private void gameSettings(User u) {
        u.getPlayer().setTurn(true);
        if(getClients().size() < 5) {
            chooseTerminator(u);
        }
        chooseMap(u);
        if (!u.isGuiInterface()) {
            chooseSkullNumber(u);
        }
        u.getPlayer().setTurn(false);
    }

    /**
     * The user decides to play with or without the terminator. Also the terminator color
     *
     * @param u     first user in the list of connected players
     */

    private void chooseTerminator(User u){
        if (u.isGuiInterface()){
            if (!u.isRmiConnection()){
                messageTo(u.getColor(), "COLOR", false);
                u.getConnection().setColorSended(true);
                Gson gson = new Gson();
                String jsonString = gson.toJson(getAvailableColors());
                messageTo(u.getColor(), jsonString, false);
                u.getConnection().setColorSended(false);
            }
            String choice = messageTo(u.getColor(), "TERMINATOR_CHOICE", true);
            if (choice.equals("1")) {
                setTerminator(true);
                choice = messageTo(u.getColor(), "terminator color", true);
                setTerminatorColor(choice.charAt(0));
            } else
                setTerminator(false);
        }else {
            clearCLI(u);
            while (true) {
                String choice = messageTo(u.getColor(), "If you want to play in Terminator-mode, please press 1; otherwise, press 0", true);
                if (isValidTerminator(choice) && choice.length() != 0) {
                    if (choice.equals("0")) {
                        setTerminator(false);
                        clearCLI(u);
                        messageToAll("Playing without Terminator");
                    } else {
                        Server.setTerminator(true);
                        while (true) {
                            String availableColors = "";
                            for (String s : getAvailableColors())
                                availableColors += s + " ";
                            String color = messageTo(u.getColor(), "Playing in Terminator-mode, choose terminator color: " + availableColors, true);
                            if (isValidColor(color) && !colorAlreadyTaken(color.charAt(0)) && color.length() != 0) {
                                setTerminatorColor(color.charAt(0));
                                clearCLI(u);
                                messageToAll("Terminator color set to: " + Server.getTerminatorColor());
                                clearCLI(u);
                                break;
                            } else if (colorAlreadyTaken(color.charAt(0))) {
                                clearCLI(u);
                                messageTo(u.getColor(), "Color already taken", false);
                            } else {
                                clearCLI(u);
                                messageTo(u.getColor(), ERRORSTRING, false);
                            }
                        }
                    }
                    break;
                } else {
                    clearCLI(u);
                    messageTo(u.getColor(), CNV, false);
                }
            }
        }
    }

    /**
     * The user decides the number of skulls in the killshot track for the game
     *
     * @param u     first user in the list of connected players
     */

    private void chooseSkullNumber(User u) {
        clearCLI(u);
        while(true) {
            String line = messageTo(u.getColor(), "Choose the number of Skulls (5 to 8) for this game:", true);
            if (isValidSkulls(line) && line.length() != 0) {
                Server.setSkullsNumber(Integer.parseInt(line));
                messageToAll("Number of Skulls set to: " + getSkullsNumber());
                break;
            } else {
                messageTo(u.getColor(), CNV, false);
                messageTo(u.getColor(), ERRORSTRING, true);
            }
        }
    }

    /**
     * The user decides the Board to play with
     *
     * @param u     first user in the list of connected players
     */

    private void chooseMap(User u) {
        if (u.isGuiInterface()){
            String line = messageTo(u.getColor(), "BOARD_AND_SKULLS_CHOICE", true);
            System.out.println("scelta board e skull: " + line);
            int map = Integer.parseInt("" + line.charAt(0));
            int skulls = Integer.parseInt("" + line.charAt(1));
            System.out.println("dopo i parse int");
            setMapChoice(map);
            setSkullsNumber(skulls);
        }else {
            while (true) {
                clearCLI(u);
                String choice = messageTo(u.getColor(), "Choose which map you want to play: 1, 2, 3, or 4:", true);
                if (isValidMap(choice) && choice.length() != 0) {
                    Server.setMapChoice(Integer.parseInt(choice));
                    messageToAll("Playing with Map number: " + Server.getMapChoice());
                    break;
                } else {
                    clearCLI(u);
                    messageTo(u.getColor(), ERRORSTRING, false);
                }
            }
        }
    }

    /**
     * Escape command to clear the cli of the User u
     *
     * @param u     the user that has to clear the CLI interface
     */

    private void clearCLI(User u) {
        messageTo(u.getColor(), "\033[H\033[2J", false);
    }

    //CHECK

    private boolean isValidTerminator(String choice){
        return !(!choice.equals("1") && !choice.equals("0"));
    }

    private static boolean isValidSkulls(String n) {
        return !(!n.equals("5") && !n.equals("6") && !n.equals("7") && !n.equals("8"));
    }

    private boolean isValidColor(String s) {
        return !(!s.equals("blue") && !s.equals("violet") && !s.equals("yellow") && !s.equals("grey") && !s.equals("emerald"));
    }

    private boolean isValidMap(String choice) {
        return !(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4"));
    }

    private boolean checkResetTimer() {
        return (getClients().size() < 3 || !checkReadyPlayers());
    }

    //TIMER

    /**
     * Starts a timer, as soon it ends the game is created after the user decides the game settings
     *
     * @param delay     delay of the timer
     */

    private void startTimer(int delay) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                gameSettings(getClients().get(0));
                startGame();
            }
        };
        createTimer();
        timer.schedule(timerTask, delay*1000);
    }

    private static void createTimer(){
        timer = new Timer();
    }

}