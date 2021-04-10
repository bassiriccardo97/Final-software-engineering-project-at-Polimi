package it.polimi.ingsw.view.gui.login;

import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.connections.User;
import it.polimi.ingsw.view.gui.BoardGUI;
import it.polimi.ingsw.view.gui.MyApplication;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class NameChoice {

    private String username = null;
    private BorderPane borderPane = new BorderPane();
    private VBox vBoxTop = new VBox();
    private Text t1vb = new Text();
    private Text t2vb = new Text();
    private Text t3vb = new Text();
    private Text t4vb = new Text();
    private Text t5vb = new Text();
    private Text t6vb = new Text();
    private Text t7vb = new Text();
    private Text t8 = new Text();
    private Text t9 = new Text();
    private Text t10 = new Text();
    private Text t11 = new Text();
    private GridPane gridPaneCenter = new GridPane();
    private VBox vBoxGrid = new VBox();
    private Pane paneVb1 = new Pane();
    private Pane paneVb2 = new Pane();
    private TextField textField = new TextField();
    private VBox vBoxTop1 = new VBox();
    private BorderPane borderPaneVbGrid1 = new BorderPane();
    private BorderPane borderPaneGrid = new BorderPane();
    private Button button = new Button();
    private Pane pane3 = new Pane();
    private Pane pane4 = new Pane();

    public void NameChoice(){

        //Client.setConfirm(false);

        borderPane.setPrefSize(600, 500);
        borderPane.prefWidthProperty().bind(MyApplication.getPrimaryStage().widthProperty());
        borderPane.prefHeightProperty().bind(MyApplication.getPrimaryStage().heightProperty());

        setPlayerLogged();

        t1vb.setVisible(false);
        t2vb.setVisible(false);
        t3vb.setText("Welcome to Adrenaline!");
        t3vb.setFont(new Font(18));
        t3vb.setTextAlignment(TextAlignment.CENTER);
        t4vb.setVisible(false);
        t5vb.setVisible(false);
        t6vb.setVisible(false);
        t7vb.setText("Choose player name.");
        t7vb.setTextAlignment(TextAlignment.CENTER);
        vBoxTop.setAlignment(Pos.TOP_CENTER);
        vBoxTop.setPrefSize(600, 100);
        vBoxTop.getChildren().addAll(t1vb, t2vb, t3vb, t4vb, t5vb, t6vb, t7vb);
        borderPane.setTop(vBoxTop);
        BorderPane.setAlignment(vBoxTop, Pos.CENTER);

        t8.setTextAlignment(TextAlignment.CENTER);
        t8.wrappingWidthProperty().bind(textField.widthProperty());
        t9.setTextAlignment(TextAlignment.CENTER);
        t9.wrappingWidthProperty().bind(textField.widthProperty());
        t10.setTextAlignment(TextAlignment.CENTER);
        t10.wrappingWidthProperty().bind(textField.widthProperty());
        t11.setTextAlignment(TextAlignment.CENTER);
        t11.wrappingWidthProperty().bind(textField.widthProperty());
        vBoxTop1.setPrefSize(240, 110);
        vBoxTop1.setAlignment(Pos.TOP_CENTER);
        vBoxTop1.getChildren().addAll(t8, t9, t10, t11);
        borderPaneVbGrid1.setPrefSize(240, 120);
        borderPaneVbGrid1.setTop(vBoxTop1);
        BorderPane.setAlignment(vBoxTop1, Pos.CENTER);
        paneVb1.setPrefSize(240, 35);
        textField.setAlignment(Pos.TOP_CENTER);
        textField.setPromptText("Player name");
        textField.setPrefWidth(300);
        textField.addEventFilter(KeyEvent.KEY_PRESSED, submitNameWithEnter);
        paneVb2.setPrefSize(240, 25);
        vBoxGrid.getChildren().addAll(paneVb1, textField, paneVb2, borderPaneVbGrid1);
        vBoxGrid.setAlignment(Pos.CENTER);
        vBoxGrid.setMaxWidth(300);

        borderPaneGrid.setPrefSize(200, 200);
        borderPane.setMaxSize(200, 200);
        button.setText("Submit");
        button.addEventFilter(KeyEvent.KEY_PRESSED, submitNameWithEnter);
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, submitNameWithMouse);
        borderPaneGrid.setTop(button);
        BorderPane.setAlignment(button, Pos.CENTER);

        ColumnConstraints col = new ColumnConstraints();
        col.setMinWidth(10);
        col.setPrefWidth(100);
        col.setHgrow(Priority.SOMETIMES);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10);
        row1.setPrefHeight(30);
        row1.setVgrow(Priority.SOMETIMES);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(10);
        row2.setPrefHeight(30);
        row2.setVgrow(Priority.SOMETIMES);

        gridPaneCenter.getColumnConstraints().add(col);
        gridPaneCenter.getRowConstraints().addAll(row1, row2);
        gridPaneCenter.add(vBoxGrid, 0, 0);
        gridPaneCenter.add(borderPaneGrid, 0, 1);
        gridPaneCenter.setMaxWidth(300);
        GridPane.setValignment(vBoxGrid, VPos.TOP);

        borderPane.setCenter(gridPaneCenter);
        borderPane.getCenter().maxWidth(300);
        BorderPane.setAlignment(gridPaneCenter, Pos.CENTER);

        pane3.setPrefSize(150, 200);
        borderPane.setRight(pane3);
        BorderPane.setAlignment(pane3, Pos.CENTER);

        pane4.setPrefSize(150, 200);
        borderPane.setLeft(pane4);
        BorderPane.setAlignment(pane4, Pos.CENTER);

        MyApplication.getScene().setRoot(borderPane);
    }

    private void setPlayerLogged(){
        ArrayList<String> users = new ArrayList<>();
        if (MyApplication.getClient().getRmiConnection())
            users = MyApplication.getClient().getUsersRMI();
        else
            users = MyApplication.getClient().getUsersListTCP();
        //ArrayList<String> users = MyApplication.getClient().getUsersListTCP();
        if (!users.isEmpty() && users.get(0).charAt(users.get(0).length() - 1) != '\u0000') {
            t8.setText("Player " + users.get(0).substring(0, users.get(0).length() - 2) + " connected with color " + colorFromChar(users.get(0).charAt(users.get(0).length() - 1)));
            t8.setVisible(true);
        } else {
            t8.setVisible(false);
            t9.setVisible(false);
            t10.setVisible(false);
            t11.setVisible(false);
        }
        if (users.size() >= 2 && users.get(1).charAt(users.get(1).length() - 1) != '\u0000') {
            t9.setText("Player " + users.get(1).substring(0, users.get(1).length() - 2) + " connected with color " + colorFromChar(users.get(1).charAt(users.get(1).length() - 1)));
            t9.setVisible(true);
        } else {
            t9.setVisible(false);
            t10.setVisible(false);
            t11.setVisible(false);
        }
        if (users.size() >= 3 && users.get(2).charAt(users.get(2).length() - 1) != '\u0000') {
            t10.setText("Player " + users.get(2).substring(0, users.get(2).length() - 2) + " connected with color " + colorFromChar(users.get(2).charAt(users.get(2).length() - 1)));
            t10.setVisible(true);
        } else {
            t10.setVisible(false);
            t11.setVisible(false);
        }
        if (users.size() >= 4 && users.get(3).charAt(users.get(3).length() - 1) != '\u0000') {
            t11.setText("Player " + users.get(3).substring(0, users.get(3).length() - 2) + " connected with color " + colorFromChar(users.get(3).charAt(users.get(3).length() - 1)));
            t11.setVisible(true);
        } else {
            t11.setVisible(false);
        }
    }

    private String colorFromChar(char c){
        switch (c){
            case 'b':
                return "blue";
            case 'e':
                return "emerald";
            case 'g':
                return "grey";
            case 'v':
                return "violet";
            default:
                return "yellow";
        }
    }

    private EventHandler<KeyEvent> submitNameWithEnter = keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            try {
                if (MyApplication.getClient().getRmiConnection() && MyApplication.getClient().getStub().login(textField.getText(), true)) {     //sistemare aggiungendo socket
                    if (MyApplication.getClient().getStub().getGameStarted()) {
                        MyApplication.getClient().setUsername(textField.getText());
                        MyApplication.getClient().setCallbackClient(MyApplication.getClient().getStub().getCallbackClient(textField.getText()));
                        MyApplication.getClient().getModelCallback();
                        MyApplication.getClient().getGui().boardGUI();
                        return;
                    }else {
                        MyApplication.getClient().setUsername(textField.getText());
                        MyApplication.getClient().createReference(MyApplication.getClient().getStub());
                        new ColorChoice(textField.getText());
                    }
                } else if(!MyApplication.getClient().getRmiConnection()) {
                    Client.sendString(textField.getText());
                    MyApplication.getClient().setUsername(textField.getText());
                    if (!Client.getConfirm()) {
                        refusedNick();
                        return;
                    }
                    if(!Client.getConfirm())
                        refusedNick();
                } else
                    refusedNick();
            } catch (RemoteException e) {
                new Disconnected();
            }
        }
    };

    private EventHandler<MouseEvent> submitNameWithMouse = mouseEvent -> {
        try {
            if (MyApplication.getClient().getRmiConnection() && MyApplication.getClient().getStub().login(textField.getText(), true)) {     //sistemare aggiungendo socket
                if (MyApplication.getClient().getStub().getGameStarted()) {
                    MyApplication.getClient().setUsername(textField.getText());
                    MyApplication.getClient().setCallbackClient(MyApplication.getClient().getStub().getCallbackClient(textField.getText()));
                    MyApplication.getClient().getModelCallback();
                    MyApplication.getClient().getGui().boardGUI();
                    return;
                }else {
                    MyApplication.getClient().setUsername(textField.getText());
                    MyApplication.getClient().createReference(MyApplication.getClient().getStub());
                    new ColorChoice(textField.getText());
                }
            } else if(!MyApplication.getClient().getRmiConnection()) {
                Client.sendString(textField.getText());
                MyApplication.getClient().setUsername(textField.getText());
                if (!Client.getConfirm()){
                    refusedNick();
                    return;
                }
                if(!Client.getConfirm())
                    refusedNick();
            } else
                refusedNick();
        } catch (RemoteException e) {
            new Disconnected();
        }
    };

    private void refusedNick(){
        if (textField.getText().isEmpty() || textField.getText() == null)
            t7vb.setText("Player name not valid, choose another.");
        else
            t7vb.setText("Player name already taken or too long (10 char max), choose another.");
        textField.clear();
        textField.setPromptText("Player name");
        username = null;
        setPlayerLogged();
    }
}