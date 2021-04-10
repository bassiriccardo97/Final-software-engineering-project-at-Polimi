package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.view.gui.login.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class MyApplication extends Application {

    private static Stage primaryStage;
    private static Scene scene;
    private static Client client;
    private BoardGUI boardGUI;

    /**
     * Setter for the client on which the Application runs
     *
     * @param c     the Client
     */
    public static void setClient(Client c){
        client = c;
    }

    /**
     * The launch method for the Application
     */
    public void run(){
        Application.launch(getClass());
    }

    /**
     * Start method of the Application
     *
     * @param stage     the primary Stage
     */
    @Override
    public void start(Stage stage){
        try {
            setPrimaryStage(stage);
            Group root = new Group();
            Scene s = new Scene(root);
            setScene(s);
            primaryStage.setOnCloseRequest(e -> closeclient());
            primaryStage.setTitle("ADRENALINE");
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            new InterfaceChoice();
            primaryStage.show();
        }catch (Exception e){

        }
    }

    /**
     * Method to close the client if the user wants to exit the Application
     */
    private static void closeclient() {
        if (client.getUsername() == null)       //controllare che non venga aggiunto il client o nel caso rimuoverlo
            System.exit(0);
        if(!client.getRmiConnection()) {
            Client.stopConnection();
            Client.sendString("QUIT");
        } else {
            try {
                client.getStub().removeClient(client.getUsername());        //set disconnected
            } catch (RemoteException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * Setter for the primary Stage
     *
     * @param stage     a Stage
     */
    public static void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    /**
     * Setter for the Scene of the Stage
     *
     * @param s     a Scene
     */
    public static void setScene(Scene s){
        scene = s;
    }

    /**
     * Getter for the primary Stage
     *
     * @return      the primary Stage
     */
    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    /**
     * Getter for the Scene of the primary Stage
     *
     * @return      a Scene
     */
    public static Scene getScene(){
        return scene;
    }

    /**
     * Getter for the Client on which the Application runs
     *
     * @return      the Client on which javaFX runs
     */
    public static Client getClient(){
        return client;
    }

    /**
     * Starts the scene BoardGUI
     */
    public void boardGUI(){
        boardGUI = new BoardGUI();
        Client.setGuiDisplayed(true);
    }

    /**
     * Getter for the BoardGUI instance
     *
     * @return      the BoardGUI
     */
    public BoardGUI getBoardGUI(){
        return boardGUI;
    }

    /**
     * Starts the ColorChoice scene for the login
     *
     * @param name      a String, the username
     */
    public void colorChoice(String name){
        new ColorChoice(name);
    }

    /**
     * Starts the NameChoice scene for the login
     */
    public void nameChoice(){
        NameChoice nameChoice = new NameChoice();
        nameChoice.NameChoice();

    }

    /**
     * Starts the GameComplete scene for the login, if there is already the maximum number of players for the game
     */
    public void gameComplete(){
        new GameComplete();
    }

    /**
     * Starts the boardAndSkullsChoice scene for the login
     */
    public void boardAndSkullChoice(){
        new BoardAndSkullChoice();
    }

    /**
     * Starts the TerminatorChoice scene if the user chooses to play in terminator-mode
     */
    public void terminatorChoice(){
        new TerminatorChoice();
    }

    /**
     * Starts the WaitingGameStart scene for the login
     */
    public void waitGameToStart(){
        new WaitingGameStart();
    }

    /**
     * Starts the disconnected scene
     */
    public void disconnected(){
        new Disconnected();
    }
}