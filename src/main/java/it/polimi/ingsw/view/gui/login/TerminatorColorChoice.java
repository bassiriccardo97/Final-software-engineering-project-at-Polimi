package it.polimi.ingsw.view.gui.login;

import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.connections.User;
import it.polimi.ingsw.view.gui.MyApplication;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class TerminatorColorChoice {

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
    private VBox vBoxTop1 = new VBox();
    private BorderPane borderPaneVbGrid1 = new BorderPane();
    private BorderPane borderPaneGrid = new BorderPane();
    private Button button = new Button();
    private Pane pane3 = new Pane();
    private Pane pane4 = new Pane();
    private RadioButton blue = new RadioButton();
    private RadioButton emerald = new RadioButton();
    private RadioButton grey = new RadioButton();
    private RadioButton violet = new RadioButton();
    private RadioButton yellow = new RadioButton();
    private Pane pane1VBoxTop = new Pane();
    private HBox hBoxVBoxTop = new HBox();
    private Pane pane1HBoxVBoxTop = new Pane();
    private Pane pane2HBoxVBoxTop = new Pane();
    private Pane pane3HBoxVBoxTop = new Pane();
    private Pane pane4HBoxVBoxTop = new Pane();
    private ToggleGroup color = new ToggleGroup();


    public TerminatorColorChoice(){
        borderPane.setPrefSize(600, 500);

        t1vb.setVisible(false);
        t2vb.setVisible(false);
        t3vb.setText("Welcome to Adrenaline!");
        t3vb.setFont(new Font(18));
        t3vb.setTextAlignment(TextAlignment.CENTER);
        t4vb.setVisible(false);
        t5vb.setVisible(false);
        t6vb.setVisible(false);
        t7vb.setText("Choose Terminator color.");
        t7vb.setTextAlignment(TextAlignment.CENTER);
        pane1VBoxTop.setPrefSize(400, 25);
        hBoxVBoxTop.setAlignment(Pos.CENTER);
        hBoxVBoxTop.setPrefSize(400, 16);
        blue.setText("Blue");
        blue.setToggleGroup(color);
        blue.addEventFilter(KeyEvent.KEY_PRESSED, submitColorWithEnter);
        checkAvailable(blue, "blue");
        pane1HBoxVBoxTop.setPrefSize(35, 16);
        emerald.setText("Emerald");
        emerald.setToggleGroup(color);
        emerald.addEventFilter(KeyEvent.KEY_PRESSED, submitColorWithEnter);
        checkAvailable(emerald, "emerald");
        pane2HBoxVBoxTop.setPrefSize(35, 16);
        grey.setText("Grey");
        grey.setToggleGroup(color);
        grey.addEventFilter(KeyEvent.KEY_PRESSED, submitColorWithEnter);
        checkAvailable(grey, "grey");
        pane3HBoxVBoxTop.setPrefSize(35, 16);
        violet.setText("Violet");
        violet.setToggleGroup(color);
        violet.addEventFilter(KeyEvent.KEY_PRESSED, submitColorWithEnter);
        checkAvailable(violet, "violet");
        pane4HBoxVBoxTop.setPrefSize(35, 16);
        yellow.setText("Yellow");
        yellow.setToggleGroup(color);
        yellow.addEventFilter(KeyEvent.KEY_PRESSED, submitColorWithEnter);
        checkAvailable(yellow, "yellow");
        hBoxVBoxTop.getChildren().addAll(blue, pane1HBoxVBoxTop, emerald, pane2HBoxVBoxTop, grey, pane3HBoxVBoxTop, violet, pane4HBoxVBoxTop, yellow);
        vBoxTop.setAlignment(Pos.TOP_CENTER);
        vBoxTop.setPrefSize(570, 200);
        vBoxTop.getChildren().addAll(t1vb, t2vb, t3vb, t4vb, t5vb, t6vb, t7vb, pane1VBoxTop, hBoxVBoxTop);
        borderPane.setTop(vBoxTop);
        BorderPane.setAlignment(vBoxTop, Pos.CENTER);

        t8.setTextAlignment(TextAlignment.CENTER);
        t9.setTextAlignment(TextAlignment.CENTER);
        t10.setTextAlignment(TextAlignment.CENTER);
        t11.setTextAlignment(TextAlignment.CENTER);
        vBoxTop1.setPrefSize(240, 110);
        vBoxTop1.setAlignment(Pos.TOP_CENTER);
        vBoxTop1.getChildren().addAll(t8, t9, t10, t11);
        borderPaneVbGrid1.setPrefSize(240, 120);
        borderPaneVbGrid1.setTop(vBoxTop1);
        BorderPane.setAlignment(vBoxTop1, Pos.CENTER);
        paneVb1.setPrefSize(240, 35);
        paneVb2.setPrefSize(240, 25);
        vBoxGrid.getChildren().addAll(paneVb1, paneVb2, borderPaneVbGrid1);

        borderPaneGrid.setPrefSize(300, 200);
        button.setText("Submit");
        button.addEventFilter(KeyEvent.KEY_PRESSED, submitColorWithEnter);
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, submitColorWithMouse);
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
        GridPane.setValignment(vBoxGrid, VPos.TOP);

        borderPane.setCenter(gridPaneCenter);
        BorderPane.setAlignment(gridPaneCenter, Pos.CENTER);

        pane3.setPrefSize(150, 200);
        borderPane.setRight(pane3);
        BorderPane.setAlignment(pane3, Pos.CENTER);

        pane4.setPrefSize(150, 200);
        borderPane.setLeft(pane4);
        BorderPane.setAlignment(pane4, Pos.CENTER);

        MyApplication.getScene().setRoot(borderPane);
    }

    private void checkAvailable(RadioButton rb, String col){
        if (MyApplication.getClient().getRmiConnection()) {
            try {
                if (!MyApplication.getClient().getStub().availableColors().contains(col))
                    rb.setDisable(true);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }else
            if (!Client.getAvailableColors().contains(col))
                rb.setDisable(true);
    }

    private EventHandler<KeyEvent> submitColorWithEnter = keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER && color.getSelectedToggle() != null) {
            String col;
            if (color.getSelectedToggle().equals(blue))
                col = "blue";
            else if (color.getSelectedToggle().equals(emerald))
                col = "emerald";
            else if (color.getSelectedToggle().equals(grey))
                col = "grey";
            else if (color.getSelectedToggle().equals(violet))
                col = "violet";
            else
                col = "yellow";
            MyApplication.getClient().sendStringToServer(col);
        }
    };

    private EventHandler<MouseEvent> submitColorWithMouse = mouseEvent -> {
        if (color.getSelectedToggle() != null) {
            String col;
            if (color.getSelectedToggle().equals(blue))
                col = "blue";
            else if (color.getSelectedToggle().equals(emerald))
                col = "emerald";
            else if (color.getSelectedToggle().equals(grey))
                col = "grey";
            else if (color.getSelectedToggle().equals(violet))
                col = "violet";
            else
                col = "yellow";
            MyApplication.getClient().sendStringToServer(col);
        }
    };
}
