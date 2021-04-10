package it.polimi.ingsw.view.gui.login;

import it.polimi.ingsw.view.gui.MyApplication;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class WaitingGameStart {

    private BorderPane borderPane = new BorderPane();
    private Text wait = new Text();
    private Pane pane = new Pane();
    private static Text timer = new Text();
    private VBox vBox = new VBox(wait, pane, timer);

    public WaitingGameStart(){

        wait.setText("Please wait for the game to start...");
        wait.setFont(new Font(18));
        wait.setTextAlignment(TextAlignment.CENTER);
        pane.setPrefHeight(100);
        timer.setTextAlignment(TextAlignment.CENTER);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);
        BorderPane.setAlignment(vBox, Pos.CENTER);

        MyApplication.getScene().setRoot(borderPane);
    }

    public static void setTimer(String s){
        if (s.equals("RESET"))
            timer.setText("Resetting the timer");
        else
            timer.setText("Game is ready to start, setting a " + s.substring(6) + " seconds timer");
    }

}