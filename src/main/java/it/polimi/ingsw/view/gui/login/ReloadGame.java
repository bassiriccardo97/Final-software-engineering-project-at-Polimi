package it.polimi.ingsw.view.gui.login;

import it.polimi.ingsw.view.gui.MyApplication;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.rmi.RemoteException;

public class ReloadGame {

    private BorderPane borderPane = new BorderPane();
    private Pane pane1 = new Pane();
    private Pane pane2 = new Pane();
    private Pane pane3 = new Pane();
    private Pane pane4 = new Pane();
    private Pane pane5 = new Pane();
    private Button reload = new Button();
    private VBox vBox = new VBox();
    private Text t1 = new Text();
    private Text t2 = new Text();
    private Text t3 = new Text();
    private Text t4 = new Text();
    private Text t5 = new Text();
    private Text t6 = new Text();
    private Text t7 = new Text();
    private Text t8 = new Text();

    public ReloadGame() {

        borderPane.setPrefSize(600, 500);
        borderPane.setMinHeight(borderPane.getPrefHeight());
        borderPane.setMinWidth(borderPane.getPrefWidth());

        pane1.setPrefSize(180, 200);
        pane2.setPrefSize(180, 200);
        BorderPane.setAlignment(pane1, Pos.CENTER);
        BorderPane.setAlignment(pane2, Pos.CENTER);
        borderPane.setLeft(pane1);
        borderPane.setRight(pane2);

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefSize(570, 400);
        BorderPane.setAlignment(vBox, Pos.CENTER);
        t3.setText("Welcome to Adrenaline!");
        t3.setTextAlignment(TextAlignment.CENTER);
        t3.setFont(new Font(18));
        t7.setText("Oops! There are to many players, sorry." + "\n" + "Try to reload again or exit the game");
        t7.setTextAlignment(TextAlignment.CENTER);
        pane4.setPrefSize(600, 50);
        reload.setText("Reload");
        reload.setDefaultButton(true);
        reload.addEventFilter(KeyEvent.KEY_PRESSED, reloadWithEnter);
        reload.addEventFilter(MouseEvent.MOUSE_CLICKED, reloadWithMouse);
        reload.setTextAlignment(TextAlignment.CENTER);
        pane5.setPrefSize(600, 100);
        t8.setText("Try to starting the game...");
        t8.setTextAlignment(TextAlignment.CENTER);
        t8.setVisible(true);
        vBox.getChildren().addAll(t1, t2, t3, t4, t5, t6, t7, pane4, reload, pane5, t8);
        borderPane.setTop(vBox);

        pane3.setPrefSize(600, 150);
        BorderPane.setAlignment(pane3, Pos.CENTER);
        borderPane.setBottom(pane3);

        borderPane.prefHeightProperty().bind(MyApplication.getScene().heightProperty());
        borderPane.prefWidthProperty().bind(MyApplication.getScene().widthProperty());
        MyApplication.getScene().setRoot(borderPane);
    }

    public BorderPane getBorderPane(){
        return borderPane;
    }

    private EventHandler<KeyEvent> reloadWithEnter = keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            try {
                if (!MyApplication.getClient().getStub().checkIfComplete()) {        //add for tcp
                    NameChoice nameChoice = new NameChoice();
                    nameChoice.NameChoice();
                }else {
                    GameComplete gameComplete = new GameComplete();
                    MyApplication.getScene().setRoot(gameComplete.getBorderPane());
                }
            } catch (RemoteException e) {
                //e.printStackTrace();
            }
        }
    };

    private EventHandler<MouseEvent> reloadWithMouse = mouseEvent -> {
        try {
            if (!MyApplication.getClient().getStub().checkIfComplete()) {       //add for tcp
                NameChoice nameChoice = new NameChoice();
                nameChoice.NameChoice();
            }else {
                GameComplete gameComplete = new GameComplete();
                MyApplication.getScene().setRoot(gameComplete.getBorderPane());
            }
        } catch (RemoteException e) {
            //e.printStackTrace();
        }
    };
}
