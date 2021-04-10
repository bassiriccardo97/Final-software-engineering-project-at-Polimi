package it.polimi.ingsw.view.gui.login;

import it.polimi.ingsw.view.gui.MyApplication;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TerminatorChoice {

    private BorderPane borderPane = new BorderPane();
    private GridPane gridPane = new GridPane();
    private Button yes = new Button();
    private Button no = new Button();
    private Pane pane1 = new Pane();
    private Pane pane2 = new Pane();
    private Pane pane3 = new Pane();
    private VBox vBox = new VBox();
    private Text t1 = new Text();
    private Text t2 = new Text();
    private Text t3 = new Text();
    private Text t4 = new Text();
    private Text t5 = new Text();
    private Text t6 = new Text();
    private Text t7 = new Text();

    public TerminatorChoice() {

        yes.setText("Yes");
        yes.setTextAlignment(TextAlignment.CENTER);
        yes.setContentDisplay(ContentDisplay.CENTER);
        yes.setPrefWidth(50);
        yes.setDefaultButton(true);
        yes.addEventFilter(MouseEvent.MOUSE_CLICKED, yesWithMouse);
        yes.addEventFilter(KeyEvent.KEY_PRESSED, yesWithEnter);

        no.setText("No");
        no.setTextAlignment(TextAlignment.CENTER);
        no.setContentDisplay(ContentDisplay.CENTER);
        no.setPrefWidth(50);
        no.addEventFilter(MouseEvent.MOUSE_CLICKED, noWithMouse);
        no.addEventFilter(KeyEvent.KEY_PRESSED, noWithEnter);

        borderPane.setPrefSize(600, 500);
        borderPane.setMinHeight(borderPane.getPrefHeight());
        borderPane.setMinWidth(borderPane.getPrefWidth());

        gridPane.setPrefSize(70, 140);
        gridPane.setMaxWidth(140);
        gridPane.setMaxHeight(70);
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(10);
        col1.setPrefWidth(100);
        col1.setHgrow(Priority.SOMETIMES);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(10);
        col2.setPrefWidth(100);
        col2.setHgrow(Priority.SOMETIMES);
        RowConstraints row = new RowConstraints();
        row.setMinHeight(10);
        row.setPrefHeight(30);
        row.setVgrow(Priority.SOMETIMES);
        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.getRowConstraints().add(row);
        GridPane.setConstraints(yes, 1, 0);
        GridPane.setConstraints(no, 0, 0);
        GridPane.setHalignment(yes, HPos.CENTER);
        GridPane.setHalignment(no, HPos.CENTER);
        gridPane.getChildren().addAll(yes, no);
        borderPane.setCenter(gridPane);

        pane1.setPrefSize(180, 200);
        pane2.setPrefSize(180, 200);
        BorderPane.setAlignment(pane1, Pos.CENTER);
        BorderPane.setAlignment(pane2, Pos.CENTER);
        borderPane.setLeft(pane1);
        borderPane.setRight(pane2);

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefSize(570, 100);
        BorderPane.setAlignment(vBox, Pos.CENTER);
        t3.setText("Welcome to Adrenaline!");
        t3.setTextAlignment(TextAlignment.CENTER);
        t3.setFont(new Font(18));
        t7.setText("Do you want to play in Terminator-mode?");
        t7.setTextAlignment(TextAlignment.CENTER);
        vBox.getChildren().addAll(t1, t2, t3, t4, t5, t6, t7);
        borderPane.setTop(vBox);

        pane3.setPrefSize(600, 150);
        BorderPane.setAlignment(pane3, Pos.CENTER);
        borderPane.setBottom(pane3);

        borderPane.prefHeightProperty().bind(MyApplication.getScene().heightProperty());
        borderPane.prefWidthProperty().bind(MyApplication.getScene().widthProperty());
        MyApplication.getScene().setRoot(borderPane);
    }

    private EventHandler<MouseEvent> yesWithMouse = mouseEvent -> {
        MyApplication.getClient().sendStringToServer("1");
        new TerminatorColorChoice();
    };

    private EventHandler<MouseEvent> noWithMouse = mouseEvent -> {
        MyApplication.getClient().sendStringToServer("0");
    };

    private EventHandler<KeyEvent> yesWithEnter = keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            MyApplication.getClient().sendStringToServer("1");
            new TerminatorColorChoice();
        }
    };

    private EventHandler<KeyEvent> noWithEnter = keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            MyApplication.getClient().sendStringToServer("0");
        }
    };
}
