package it.polimi.ingsw.view.gui.login;

import it.polimi.ingsw.view.gui.MyApplication;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BoardAndSkullChoice {

    private BorderPane borderPane = new BorderPane();
    private Pane top = new Pane();
    private BorderPane bottom = new BorderPane();
    private VBox bottomVBox = new VBox();
    private Button submit = new Button("Submit");
    private Pane right = new Pane();
    private Pane left = new Pane();
    private BorderPane center = new BorderPane();
    private VBox centerVBox1 = new VBox();
    private Text boardChoice = new Text();
    private Pane pane1CenterVBox1 = new Pane();
    private HBox hBox1CenterVBox1 = new HBox();
    private RadioButton board1 = new RadioButton();
    private Pane pane1HBox1CenterVBox1 = new Pane();
    private RadioButton board2 = new RadioButton();
    private Pane pane2HBox1CenterVBox1 = new Pane();
    private RadioButton board3 = new RadioButton();
    private Pane pane3HBox1CenterVBox1 = new Pane();
    private RadioButton board4 = new RadioButton();
    private Pane pane2CenterVBox1 = new Pane();
    private VBox centerVBox2 = new VBox();
    private Text skullChoice = new Text();
    private Pane pane1CenterVBox2 = new Pane();
    private HBox hBox1CenterVBox2 = new HBox();
    private RadioButton skull5 = new RadioButton();
    private Pane pane1HBox1CenterVBox2 = new Pane();
    private RadioButton skull6 = new RadioButton();
    private Pane pane2HBox1CenterVBox2 = new Pane();
    private RadioButton skull7 = new RadioButton();
    private Pane pane3HBox1CenterVBox2 = new Pane();
    private RadioButton skull8 = new RadioButton();
    private Pane pane2CenterVBox2 = new Pane();
    private Pane paneCenter = new Pane();
    private ToggleGroup boards = new ToggleGroup();
    private ToggleGroup skulls = new ToggleGroup();

    public BoardAndSkullChoice(){
        borderPane.setPrefSize(600, 500);
        borderPane.setMinSize(600, 500);
        top.setPrefSize(600, 100);
        BorderPane.setAlignment(top, Pos.CENTER);
        borderPane.setTop(top);
        right.setPrefSize(100, 260);
        left.setPrefSize(100, 260);
        borderPane.setRight(right);
        BorderPane.setAlignment(right, Pos.CENTER);
        borderPane.setLeft(left);
        BorderPane.setAlignment(left, Pos.CENTER);

        bottom.setPrefSize(600, 140);
        borderPane.setBottom(bottom);
        BorderPane.setAlignment(bottom, Pos.CENTER);
        bottomVBox.setPrefSize(100, 140);
        bottomVBox.setAlignment(Pos.TOP_CENTER);
        bottomVBox.setLayoutX(250);
        submit.setTextAlignment(TextAlignment.CENTER);
        submit.setContentDisplay(ContentDisplay.CENTER);
        submit.setAlignment(Pos.CENTER);
        submit.addEventFilter(MouseEvent.MOUSE_CLICKED, submitWithMouse);
        submit.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        bottomVBox.getChildren().add(submit);
        bottom.setTop(bottomVBox);

        borderPane.setCenter(center);
        BorderPane.setAlignment(center, Pos.CENTER);
        center.setPrefSize(200, 200);
        centerVBox1.setAlignment(Pos.CENTER);
        centerVBox1.setPrefSize(400, 100);
        boardChoice.setText("Select the Board:");
        pane1CenterVBox1.setPrefSize(400, 25);
        hBox1CenterVBox1.setAlignment(Pos.CENTER);
        hBox1CenterVBox1.setPrefSize(400, 16);
        board1.setText("1");
        board1.setToggleGroup(boards);
        board1.addEventFilter(MouseEvent.MOUSE_CLICKED, submitWithMouse);
        board1.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        pane1HBox1CenterVBox1.setPrefSize(35, 16);
        board2.setText("2");
        board2.setToggleGroup(boards);
        board2.addEventFilter(MouseEvent.MOUSE_CLICKED, submitWithMouse);
        board2.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        pane2HBox1CenterVBox1.setPrefSize(35, 16);
        board3.setText("3");
        board3.setToggleGroup(boards);
        board3.addEventFilter(MouseEvent.MOUSE_CLICKED, submitWithMouse);
        board3.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        pane3HBox1CenterVBox1.setPrefSize(35, 16);
        board4.setText("4");
        board4.setToggleGroup(boards);
        board4.addEventFilter(MouseEvent.MOUSE_CLICKED, submitWithMouse);
        board4.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        hBox1CenterVBox1.getChildren().addAll(board1, pane1HBox1CenterVBox1, board2, pane2HBox1CenterVBox1, board3, pane3HBox1CenterVBox1, board4);
        pane2CenterVBox1.setPrefSize(400, 25);
        centerVBox1.getChildren().addAll(boardChoice, pane1CenterVBox1, hBox1CenterVBox1, pane2CenterVBox1);

        centerVBox2.setAlignment(Pos.CENTER);
        centerVBox2.setPrefSize(400, 100);
        skullChoice.setText("Select the number of Skulls:");
        pane1CenterVBox2.setPrefSize(400, 25);
        hBox1CenterVBox2.setAlignment(Pos.CENTER);
        hBox1CenterVBox2.setPrefSize(400, 16);
        skull5.setText("5");
        skull5.setToggleGroup(skulls);
        skull5.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        pane1HBox1CenterVBox2.setPrefSize(35, 16);
        skull6.setText("6");
        skull6.setToggleGroup(skulls);
        skull6.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        pane2HBox1CenterVBox2.setPrefSize(35, 16);
        skull7.setText("7");
        skull7.setToggleGroup(skulls);
        skull7.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        pane3HBox1CenterVBox2.setPrefSize(35, 16);
        skull8.setText("8");
        skull8.setToggleGroup(skulls);
        skull8.addEventFilter(KeyEvent.KEY_PRESSED, submitWithEnter);
        hBox1CenterVBox2.getChildren().addAll(skull5, pane1HBox1CenterVBox2, skull6, pane2HBox1CenterVBox2, skull7, pane3HBox1CenterVBox2, skull8);
        pane2CenterVBox2.setPrefSize(400, 25);
        centerVBox1.getChildren().addAll(skullChoice, pane1CenterVBox2, hBox1CenterVBox2, pane2CenterVBox2);

        paneCenter.setPrefSize(400, 100);
        center.setTop(centerVBox1);
        center.setCenter(paneCenter);
        center.setBottom(centerVBox2);
        MyApplication.getScene().setRoot(borderPane);
    }

    private EventHandler<MouseEvent> submitWithMouse = mouseEvent -> {
        if (boards.getSelectedToggle() != null && skulls.getSelectedToggle() != null){
            String j = null;
            try {
                for (int i = 1; i < 5; i++)
                    if (boards.getToggles().get(i - 1).isSelected()) {
                        j = String.valueOf(i);
                    }
                for (int i = 5; i < 9; i++)
                    if (skulls.getToggles().get(i - 5).isSelected())
                        j += String.valueOf(i);
                MyApplication.getClient().sendStringToServer(j);
            }catch (Exception e){
                //e.printStackTrace();
            }
        }
    };

    private EventHandler<KeyEvent> submitWithEnter = keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (boards.getSelectedToggle() != null && skulls.getSelectedToggle() != null) {
                String j = null;
                try {
                    for (int i = 1; i < 5; i++)
                        if (boards.getToggles().get(i - 1).isSelected()) {
                            j = String.valueOf(i);
                        }
                    for (int i = 5; i < 9; i++)
                        if (skulls.getToggles().get(i - 5).isSelected())
                            j += String.valueOf(i);
                    MyApplication.getClient().sendStringToServer(j);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    };

}