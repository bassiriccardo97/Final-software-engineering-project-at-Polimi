package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.model.clientmodel.PlayerClientModel;
import it.polimi.ingsw.model.clientmodel.RealPlayerClientModel;
import it.polimi.ingsw.model.clientmodel.WeaponClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class BoardGUI {

    private boolean showCard;
    private boolean refresh;
    //private boolean shoot = false;
    private boolean onlyName = false;
    private boolean onlyColor = false;
    private boolean weaponSelected = false;
    private boolean spawn;
    private boolean selectPowerupHand = false;
    private boolean selectWeaponHand = false;
    //private boolean chooseEffect;
    private String weaponChosen = "NONE";
    private static final double HEIGH_OWN = 200;
    private static final double HEIGH_OTHER = 110;
    private StackPane root;
    private TextArea instructions = new TextArea();
    private HBox pb1HB;
    private HBox pb2HB;
    private HBox pb3HB;
    private HBox pb4HB;
    private HBox player;
    private ScrollPane scrollPlayer;
    private ArrayList<ImageView> powerups;
    private ArrayList<ImageView> weapons;
    private ArrayList<StackPane> stackPanesPU;
    private ArrayList<StackPane> stackPanesW;
    private ArrayList<StackPane> squares;

    private Button end;
    private Button cont;
    private Button s0B;
    private Button s1B;
    private Button s2B;
    private Button s3B;
    private Button s4B;
    private Button s5B;
    private Button s6B;
    private Button s7B;
    private Button s8B;
    private Button s9B;
    private Button s10B;
    private Button s11B;
    private Button bw1;
    private Button bw2;
    private Button bw3;
    private Button rw1;
    private Button rw2;
    private Button rw3;
    private Button yw1;
    private Button yw2;
    private Button yw3;
    private Button ter;      //player terminator
    private Button pl1;
    private Button pl2;
    private Button pl3;
    private Button pl4;
    private Button p1B;
    private Button p2B;
    private Button p3B;
    private Button p4B;
    private Button ownW1B;
    private Button ownW2B;
    private Button ownW3B;
    private Button powerup;
    private Button run;
    private Button runAndGrab;
    private Button shootButton;
    private Button reload;
    private Button R1RS;
    private Button R2RS;
    private Button R2G;
    private Button R3G;
    private Button R4;
    private Button terB;        //card terminator
    private Button ammoOrBlue;
    private Button powerupOrRed;
    private Button bothOrYellow;
    private Button empty;
    private Button base;
    private Button opt1;
    private Button opt2;
    private Button alt;

    /**
     * Constructor for the BoardGUI scene
     */
    public BoardGUI(){
        configRoot();
        disableAll();
        MyApplication.getScene().setRoot(root);
    }

    /**
     * Configures the root Parent of the scene
     */
    private void configRoot(){
        root = new StackPane();
        pb1HB = new HBox();
        pb2HB = new HBox();
        pb3HB = new HBox();
        pb4HB = new HBox();
        player = new HBox();
        powerups = new ArrayList<>();
        weapons = new ArrayList<>();
        stackPanesPU = new ArrayList<>();
        stackPanesW = new ArrayList<>();
        scrollPlayer = new ScrollPane();
        squares = new ArrayList<>();
        HBox bottom = new HBox();
        SplitPane boardAndPB = new SplitPane();
        StackPane boardAndButton = new StackPane();
        AnchorPane buttons = new AnchorPane();
        StackPane sp1 = new StackPane();
        StackPane sp3 = new StackPane();
        HBox topHBox = new HBox();
        SplitPane otherPB = new SplitPane();
        ScrollPane pb1 = new ScrollPane();
        ScrollPane pb2 = new ScrollPane();
        ScrollPane pb3 = new ScrollPane();
        ScrollPane pb4 = new ScrollPane();
        end = new Button("End");
        end.setDisable(true);
        cont = new Button("Continue");
        cont.setDisable(true);
        s0B = new Button();
        s1B = new Button();
        s2B = new Button();
        s3B = new Button();
        s4B = new Button();
        s5B = new Button();
        s6B = new Button();
        s7B = new Button();
        s8B = new Button();
        s9B = new Button();
        s10B = new Button();
        s11B = new Button();
        bw1 = new Button();
        bw2 = new Button();
        bw3 = new Button();
        rw1 = new Button();
        rw2 = new Button();
        rw3 = new Button();
        yw1 = new Button();
        yw2 = new Button();
        yw3 = new Button();
        ter = new Button();      //player terminator
        pl1 = new Button();
        pl2 = new Button();
        pl3 = new Button();
        pl4 = new Button();
        p1B = new Button();
        p2B = new Button();
        p3B = new Button();
        p4B = new Button();
        ownW1B = new Button();
        ownW2B = new Button();
        ownW3B = new Button();
        powerup = new Button("Use PU");
        powerup.setDisable(true);
        run = new Button("Run");
        run.setDisable(true);
        runAndGrab = new Button("R&G");
        runAndGrab.setDisable(true);
        shootButton = new Button("Shoot");
        shootButton.setDisable(true);
        reload = new Button("Reload");
        reload.setDisable(true);
        R1RS = new Button("R1RS");
        R1RS.setDisable(true);
        R2RS = new Button("R2RS");
        R2RS.setDisable(true);
        R2G = new Button("R2G");
        R2G.setDisable(true);
        R3G = new Button("R3G");
        R3G.setDisable(true);
        R4 = new Button("Run 4");
        R4.setDisable(true);
        ammoOrBlue = new Button();
        ammoOrBlue.setDisable(true);
        powerupOrRed = new Button();
        powerupOrRed.setDisable(true);
        bothOrYellow = new Button();
        bothOrYellow.setDisable(true);
        terB = new Button();

        root.setStyle("-fx-background-color: grey");
        boardAndPB.setStyle("-fx-background-color: grey");
        boardAndButton.setStyle("-fx-background-color: grey");
        sp1.setStyle("-fx-background-color: grey");
        sp3.setStyle("-fx-background-color: grey");
        otherPB.setStyle("-fx-background-color: grey");
        pb1.setStyle("-fx-background-color: grey");
        pb2.setStyle("-fx-background-color: grey");
        pb3.setStyle("-fx-background-color: grey");
        pb4.setStyle("-fx-background-color: grey");
        pb1HB.setStyle("-fx-background-color: grey");
        pb2HB.setStyle("-fx-background-color: grey");
        pb3HB.setStyle("-fx-background-color: grey");
        pb4HB.setStyle("-fx-background-color: grey");
        scrollPlayer.setStyle("-fx-background-color: grey");
        player.setStyle("-fx-background-color: grey");

        boardAndPB.setOrientation(Orientation.VERTICAL);
        ImageView board = new ImageView(new Image("board/board" + Client.getModel().getMap().getMapNumber() + ".png"));
        board.setPreserveRatio(true);
        board.setFitHeight(500);
        boardAndButton.getChildren().add(board);
        topHBox.getChildren().add(boardAndButton);
        topHBox.setMaxHeight(500);
        topHBox.setMinHeight(500);
        sp1.getChildren().add(topHBox);
        StackPane.setAlignment(topHBox, Pos.TOP_LEFT);
        scrollPlayer.setContent(player);
        scrollPlayer.setFitToHeight(true);
        scrollPlayer.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPlayer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPlayer.setMaxHeight(HEIGH_OWN);
        scrollPlayer.setMinHeight(HEIGH_OWN);
        boardAndPB.getItems().addAll(sp1, scrollPlayer, sp3);
        player.setMinHeight(200);
        boardAndPB.setDividerPositions(0.5f, 0.85f);
        boardAndPB.setPrefSize(MyApplication.getPrimaryStage().getWidth(), MyApplication.getPrimaryStage().getHeight());
        root.getChildren().add(boardAndPB);
        otherPB.setOrientation(Orientation.VERTICAL);
        otherPB.setMinWidth(630);
        topHBox.getChildren().add(otherPB);
        pb1.setContent(pb1HB);
        pb1.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pb1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pb1.setFitToHeight(true);
        pb1.setFitToWidth(true);
        pb2.setContent(pb2HB);
        pb2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pb2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pb2.setFitToHeight(true);
        pb2.setFitToWidth(true);
        pb3.setContent(pb3HB);
        pb3.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pb3.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pb3.setFitToHeight(true);
        pb3.setFitToWidth(true);
        pb4.setContent(pb4HB);
        pb4.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pb4.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pb4.setFitToHeight(true);
        pb4.setFitToWidth(true);
        pb1.setMaxHeight(120);
        pb1.setMinHeight(120);
        pb2.setMaxHeight(120);
        pb2.setMinHeight(120);
        pb3.setMaxHeight(120);
        pb3.setMinHeight(120);
        pb4.setMaxHeight(120);
        pb4.setMinHeight(120);
        otherPB.getItems().addAll(pb1, pb2, pb3, pb4);
        otherPB.setDividerPositions(0.25f, 0.5f, 0.75f);
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(cont, instructions, end);
        sp3.getChildren().add(bottom);
        sp3.setMaxHeight(80);
        sp3.setMinHeight(80);
        instructions.setWrapText(true);
        instructions.setEditable(false);
        instructions.setStyle("-fx-text-alignment: center");
        instructions.setStyle("-fx-content-display: center");
        instructions.setStyle("-fx-text-origin: center");
        instructions.setMaxHeight(80);
        end.setTextAlignment(TextAlignment.CENTER);
        end.setPrefWidth(100);
        end.setId("end");
        end.addEventFilter(MouseEvent.MOUSE_CLICKED, endAction);
        cont.setTextAlignment(TextAlignment.CENTER);
        cont.setPrefWidth(100);
        cont.setId("continue");
        cont.addEventFilter(MouseEvent.MOUSE_CLICKED, continueAction);
        boardAndButton.setMaxHeight(500);
        boardAndButton.getChildren().add(buttons);
        setButtonsAndSkullBoard(buttons);
        setPlayer();
        setPB();
    }

    /**
     * Configures all the Buttons and images on the board
     *
     * @param ap        the AnchorPane on which the board is setted
     */
    private void setButtonsAndSkullBoard(AnchorPane ap){
        StackPane b1 = new StackPane();
        StackPane b2 = new StackPane();
        StackPane b3 = new StackPane();
        StackPane r1 = new StackPane();
        StackPane r2 = new StackPane();
        StackPane r3 = new StackPane();
        StackPane y1 = new StackPane();
        StackPane y2 = new StackPane();
        StackPane y3 = new StackPane();
        StackPane s0 = new StackPane();
        StackPane s1 = new StackPane();
        StackPane s2 = new StackPane();
        StackPane s3 = new StackPane();
        StackPane s4 = new StackPane();
        StackPane s5 = new StackPane();
        StackPane s6 = new StackPane();
        StackPane s7 = new StackPane();
        StackPane s8 = new StackPane();
        StackPane s9 = new StackPane();
        StackPane s10 = new StackPane();
        StackPane s11 = new StackPane();
        GridPane skulls = new GridPane();
        GridPane killFF = new GridPane();
        ImageView powerupRear = new ImageView(new Image("cards/powerupRear.png"));
        ImageView weaponRear = new ImageView(new Image("cards/weaponRear.png"));
        ImageView ammo = new ImageView(new Image("ammo/AD_ammo_rear.png"));
        ImageView discardedAmmo = new ImageView(new Image("ammo/AD_ammo_rear.png"));

        powerupRear.setVisible(false);
        weaponRear.setVisible(false);
        ammo.setVisible(false);
        discardedAmmo.setVisible(false);
        if (Client.getModel().isWeapon()){
            weaponRear.setLayoutY(138);
            weaponRear.setLayoutX(575);
            weaponRear.setVisible(true);
            weaponRear.setPreserveRatio(true);
            weaponRear.setFitHeight(105);
        }
        if (Client.getModel().isPowerup()) {
            powerupRear.setLayoutY(30);
            powerupRear.setLayoutX(591);
            powerupRear.setVisible(true);
            powerupRear.setPreserveRatio(true);
            powerupRear.setFitHeight(71);
        }
        if (Client.getModel().isAmmo()){
            ammo.setLayoutY(450);
            ammo.setLayoutX(20);
            ammo.setVisible(true);
            ammo.setPreserveRatio(true);
            ammo.setFitHeight(50);
        }
        if (Client.getModel().isDiscardedAmmo()){
            discardedAmmo.setLayoutY(450);
            discardedAmmo.setLayoutX(80);
            discardedAmmo.setVisible(true);
            discardedAmmo.setPreserveRatio(true);
            discardedAmmo.setFitHeight(50);
        }

        ap.getChildren().addAll(powerupRear, weaponRear, ammo, discardedAmmo);

        setSpawnButton(b1, b2, b3, r1, r2, r3, y1, y2, y3);
        ap.getChildren().addAll(b1, b2, b3, r1, r2, r3, y1, y2, y3);
        ap.setMaxHeight(500);

        squares.add(s0);
        squares.add(s1);
        squares.add(s2);
        squares.add(s3);
        squares.add(s4);
        squares.add(s5);
        squares.add(s6);
        squares.add(s7);
        squares.add(s8);
        squares.add(s9);
        squares.add(s10);
        squares.add(s11);
        setSquareButton(squares);
        ap.getChildren().addAll(s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11);

        RowConstraints r = new RowConstraints();
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(27.5);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setMinWidth(27.5);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setMinWidth(27.5);
        ColumnConstraints c4 = new ColumnConstraints();
        c4.setMinWidth(27.5);
        ColumnConstraints c5 = new ColumnConstraints();
        c5.setMinWidth(27.5);
        ColumnConstraints c6 = new ColumnConstraints();
        c6.setMinWidth(27.5);
        ColumnConstraints c7 = new ColumnConstraints();
        c7.setMinWidth(27.5);
        ColumnConstraints c8 = new ColumnConstraints();
        c8.setMinWidth(27.5);
        skulls.getRowConstraints().add(r);
        skulls.getColumnConstraints().addAll(c1, c2, c3, c4, c5, c6, c7, c8);

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(10);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setMinWidth(10);
        killFF.getRowConstraints().addAll(row1, row2);
        killFF.getColumnConstraints().addAll(col1, col2, col3);

        setSkullAndKillshot(skulls, killFF);
        ap.getChildren().addAll(skulls, killFF);
        skulls.setLayoutX(53);
        skulls.setLayoutY(32);
        skulls.setAlignment(Pos.CENTER);
        killFF.setLayoutY(28);
        killFF.setLayoutX(270);
        killFF.setAlignment(Pos.CENTER);
    }

    /**
     * Configures the remaining skulls and the killshots of the game on the board
     *
     * @param skullGrid     the GridPane of skulls and killshots
     * @param killFF        the GridPane of killshots of the final frenzy
     */
    private void setSkullAndKillshot(GridPane skullGrid, GridPane killFF) {
        int m = Client.getModel().getInitSkulls();        //n initial skulls
        int n = Client.getModel().getReaminingSkulls();        //n skulls remaining
        String[] killshot = Client.getModel().getKillShotTrack();     //killshots
        String[] killshotFF = Client.getModel().getKillShotTrackFF();
        int j = 0;
        for (int i = 8 - m; i < 8; i++){
            if (i < 8 - n && killshot[j] != null){
                ImageView drop = new ImageView(new Image("board/" + killshot[j] + "Drop.png"));
                drop.setPreserveRatio(true);
                drop.setFitHeight(30);
                drop.setFitWidth(20);
                skullGrid.add(drop, i, 0);
                j++;
            } else {
                ImageView skull = new ImageView(new Image("board/skull.png"));
                skull.setPreserveRatio(true);
                skull.setFitHeight(30);
                skullGrid.add(skull, i, 0);
            }
        }
        for (int i = 0; i < killshotFF.length && killshotFF[i] != null; i++){
            ImageView drop = new ImageView(new Image("board/" + killshotFF[i] + "Drop.png"));
            drop.setPreserveRatio(true);
            drop.setFitWidth(10);
            killFF.add(drop, i % 3, i % 2);
        }
    }

    /**
     * Configures all the Buttons of the squares on the board
     *
     * @param s     an ArrayLists of StackPane on which Buttons lay
     */
    private void setSquareButton(ArrayList<StackPane> s){
        GridPane s0G = new GridPane();
        GridPane s1G = new GridPane();
        GridPane s2G = new GridPane();
        GridPane s3G = new GridPane();
        GridPane s4G = new GridPane();
        GridPane s5G = new GridPane();
        GridPane s6G = new GridPane();
        GridPane s7G = new GridPane();
        GridPane s8G = new GridPane();
        GridPane s9G = new GridPane();
        GridPane s10G = new GridPane();
        GridPane s11G = new GridPane();
        ArrayList<GridPane> squares = new ArrayList<>();

        rowAndColumnsSquare(s0G);
        rowAndColumnsSquare(s1G);
        rowAndColumnsSquare(s2G);
        rowAndColumnsSquare(s3G);
        rowAndColumnsSquare(s4G);
        rowAndColumnsSquare(s5G);
        rowAndColumnsSquare(s6G);
        rowAndColumnsSquare(s7G);
        rowAndColumnsSquare(s8G);
        rowAndColumnsSquare(s9G);
        rowAndColumnsSquare(s10G);
        rowAndColumnsSquare(s11G);

        squares.add(s0G);
        squares.add(s1G);
        squares.add(s2G);
        squares.add(s3G);
        squares.add(s4G);
        squares.add(s5G);
        squares.add(s6G);
        squares.add(s7G);
        squares.add(s8G);
        squares.add(s9G);
        squares.add(s10G);
        squares.add(s11G);
        setPositions(squares);
        s.get(0).getChildren().add(s0G);
        s.get(1).getChildren().add(s1G);
        s.get(2).getChildren().add(s2G);
        s.get(3).getChildren().add(s3G);
        s.get(4).getChildren().add(s4G);
        s.get(5).getChildren().add(s5G);
        s.get(6).getChildren().add(s6G);
        s.get(7).getChildren().add(s7G);
        s.get(8).getChildren().add(s8G);
        s.get(9).getChildren().add(s9G);
        s.get(10).getChildren().add(s10G);
        s.get(11).getChildren().add(s11G);

        squares.remove(11);
        squares.remove(4);
        squares.remove(2);
        setAmmoTile(squares);

        s0B.setPrefSize(100, 100);
        s.get(0).setLayoutX(110);
        s.get(0).setLayoutY(110);
        s0B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s0B.setStyle("-fx-background-color: transparent");
        s0B.setId("0");
        if (!Client.getModel().getMap().getSquares().get(0).isExisting())
            s0B.setDisable(true);
        s.get(0).getChildren().add(s0B);
        s1B.setPrefSize(100, 100);
        s.get(1).setLayoutX(220);
        s.get(1).setLayoutY(110);
        s1B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s1B.setStyle("-fx-background-color: transparent");
        s1B.setId("1");
        if (!Client.getModel().getMap().getSquares().get(1).isExisting())
            s1B.setDisable(true);
        s.get(1).getChildren().add(s1B);
        s2B.setPrefSize(100, 100);
        s.get(2).setLayoutX(330);
        s.get(2).setLayoutY(110);
        s2B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s2B.setStyle("-fx-background-color: transparent");
        s2B.setId("2");
        s.get(2).getChildren().add(s2B);
        s3B.setPrefSize(100, 100);
        s.get(3).setLayoutX(450);
        s.get(3).setLayoutY(110);
        s3B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s3B.setStyle("-fx-background-color: transparent");
        s3B.setId("3");
        if (!Client.getModel().getMap().getSquares().get(2).isExisting())
            s3B.setDisable(true);
        s.get(3).getChildren().add(s3B);
        s4B.setPrefSize(100, 100);
        s.get(4).setLayoutX(110);
        s.get(4).setLayoutY(230);
        s4B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s4B.setStyle("-fx-background-color: transparent");
        s4B.setId("4");
        s.get(4).getChildren().add(s4B);
        s5B.setPrefSize(100, 100);
        s.get(5).setLayoutX(220);
        s.get(5).setLayoutY(230);
        s5B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s5B.setStyle("-fx-background-color: transparent");
        s5B.setId("5");
        if (!Client.getModel().getMap().getSquares().get(3).isExisting())
            s5B.setDisable(true);
        s.get(5).getChildren().add(s5B);
        s6B.setPrefSize(100, 100);
        s.get(6).setLayoutX(340);
        s.get(6).setLayoutY(230);
        s6B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s6B.setStyle("-fx-background-color: transparent");
        s6B.setId("6");
        if (!Client.getModel().getMap().getSquares().get(4).isExisting())
            s6B.setDisable(true);
        s.get(6).getChildren().add(s6B);
        s7B.setPrefSize(100, 100);
        s.get(7).setLayoutX(450);
        s.get(7).setLayoutY(230);
        s7B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s7B.setStyle("-fx-background-color: transparent");
        s7B.setId("7");
        if (!Client.getModel().getMap().getSquares().get(5).isExisting())
            s7B.setDisable(true);
        s.get(7).getChildren().add(s7B);
        s8B.setPrefSize(100, 100);
        s.get(8).setLayoutX(110);
        s.get(8).setLayoutY(350);
        s8B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s8B.setStyle("-fx-background-color: transparent");
        s8B.setId("8");
        if (!Client.getModel().getMap().getSquares().get(6).isExisting())
            s8B.setDisable(true);
        s.get(8).getChildren().add(s8B);
        s9B.setPrefSize(100, 100);
        s.get(9).setLayoutX(220);
        s.get(9).setLayoutY(350);
        s9B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s9B.setStyle("-fx-background-color: transparent");
        s9B.setId("9");
        if (!Client.getModel().getMap().getSquares().get(7).isExisting())
            s9B.setDisable(true);
        s.get(9).getChildren().add(s9B);
        s10B.setPrefSize(100, 110);
        s.get(10).setLayoutX(340);
        s.get(10).setLayoutY(340);
        s10B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s10B.setStyle("-fx-background-color: transparent");
        s10B.setId("10");
        if (!Client.getModel().getMap().getSquares().get(8).isExisting())
            s10B.setDisable(true);
        s.get(10).getChildren().add(s10B);
        s11B.setPrefSize(100, 110);
        s.get(11).setLayoutX(440);
        s.get(11).setLayoutY(340);
        s11B.addEventFilter(MouseEvent.MOUSE_CLICKED, sendSquare);
        s11B.setStyle("-fx-background-color: transparent");
        s11B.setId("11");
        s.get(11).getChildren().add(s11B);
    }

    /**
     * Configures the images of the action figures of the player on each square
     *
     * @param g     the GridPane that contains all the possible action figures in one square
     */
    private void rowAndColumnsSquare(GridPane g){
        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        RowConstraints r3 = new RowConstraints();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ImageView blue = new ImageView(new Image("board/bPawn.png"));
        ImageView emerald = new ImageView(new Image("board/ePawn.png"));
        ImageView grey = new ImageView(new Image("board/gPawn.png"));
        ImageView violet = new ImageView(new Image("board/vPawn.png"));
        ImageView yellow = new ImageView(new Image("board/yPawn.png"));

        r1.setMinHeight(20);
        r2.setMinHeight(20);
        r3.setMinHeight(20);
        c1.setMinWidth(20);
        c2.setMinWidth(20);
        c3.setMinWidth(20);
        g.getColumnConstraints().addAll(c1, c2, c3);
        g.getRowConstraints().addAll(r1, r2, r3);
        g.add(blue, 0, 0);
        blue.setPreserveRatio(true);
        blue.setFitHeight(30);
        blue.setVisible(false);
        g.add(emerald, 2, 0);
        emerald.setPreserveRatio(true);
        emerald.setFitHeight(30);
        emerald.setVisible(false);
        g.add(grey, 1, 1);
        grey.setPreserveRatio(true);
        grey.setFitHeight(30);
        grey.setVisible(false);
        g.add(violet, 0, 2);
        violet.setPreserveRatio(true);
        violet.setFitHeight(30);
        violet.setVisible(false);
        g.add(yellow, 2, 2);
        yellow.setPreserveRatio(true);
        yellow.setFitHeight(30);
        yellow.setVisible(false);
    }

    /**
     * Sets the action figure's image visible on each squares, according to the position of each player on the board
     *
     * @param squares       the ArrayList of GridPane, each containing the action figure images
     */
    private void setPositions(ArrayList<GridPane> squares){
        for (RealPlayerClientModel p : Client.getModel().getPlayers()) {
            if (p.getPlayerPosition() != null) {
                int i;
                switch (p.getColor()) {
                    case 'b':
                        i = 0;
                        break;
                    case 'e':
                        i = 1;
                        break;
                    case 'g':
                        i = 2;
                        break;
                    case 'v':
                        i = 3;
                        break;
                    default:
                        i = 4;
                }
                squares.get(p.getPlayerPosition()).getChildren().get(i).setVisible(true);
            }
        }
        if (Client.getModel().hasTerminator()) {
            if (Client.getModel().getTerminatorClientModel().getPlayerPosition() != null) {
                int i;
                switch (Client.getModel().getTerminatorClientModel().getColor()) {
                    case 'b':
                        i = 0;
                        break;
                    case 'e':
                        i = 1;
                        break;
                    case 'g':
                        i = 2;
                        break;
                    case 'v':
                        i = 3;
                        break;
                    default:
                        i = 4;
                }
                squares.get(Client.getModel().getTerminatorClientModel().getPlayerPosition()).getChildren().get(i).setVisible(true);
            }
        }
    }

    /**
     * Sets the ammo tile image on each square that has an ammo tile
     *
     * @param squares       the ArrayList of GridPane for each square
     */
    private void setAmmoTile(ArrayList<GridPane> squares){
        ImageView a0 = new ImageView();
        ImageView a1 = new ImageView();
        ImageView a3 = new ImageView();
        ImageView a5 = new ImageView();
        ImageView a6 = new ImageView();
        ImageView a7 = new ImageView();
        ImageView a8 = new ImageView();
        ImageView a9 = new ImageView();
        ImageView a10 = new ImageView();
        ArrayList<ImageView> ammo = new ArrayList<>();
        
        ammo.add(a0);
        ammo.add(a1);
        ammo.add(a3);
        ammo.add(a5);
        ammo.add(a6);
        ammo.add(a7);
        ammo.add(a8);
        ammo.add(a9);
        ammo.add(a10);

        for (int i = 0; i < squares.size(); i++)
            if (Client.getModel().getMap().getSquares().get(i).isExisting() && !Client.getModel().getMap().getSquares().get(i).getAmmoTile().equals("NO_IMAGE")){
                ammo.get(i).setImage(new Image(Client.getModel().getMap().getSquares().get(i).getAmmoTile())); 
                ammo.get(i).setPreserveRatio(true);
                ammo.get(i).setFitHeight(30);
                squares.get(i).add(ammo.get(i), 0, 1);
            }

    }

    /**
     * Configures all the Buttons for each spawn's weapon
     *
     * @param b1    blue spawn first weapon's StackPane
     * @param b2    blue spawn second weapon's StackPane
     * @param b3    blue spawn third weapon's StackPane
     * @param r1    red spawn first weapon's StackPane
     * @param r2    red spawn second weapon's StackPane
     * @param r3    red spawn third weapon's StackPane
     * @param y1    yellow spawn first weapon's StackPane
     * @param y2    yellow spawn second weapon's StackPane
     * @param y3    yellow spawn third weapon's StackPane
     */
    private void setSpawnButton(StackPane b1, StackPane b2, StackPane b3, StackPane r1, StackPane r2, StackPane r3, StackPane y1, StackPane y2, StackPane y3){
        setSpawn(b1, b2, b3, 'b');
        setSpawn(r1, r2, r3, 'r');
        setSpawn(y1, y2, y3, 'y');

        bw1.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getBlueSpawnpoint().get(0) != null) {
            bw1.setId(Client.getModel().getMap().getBlueSpawnpoint().get(0).getName());
            bw1.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            bw1.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        bw2.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getBlueSpawnpoint().get(1) != null) {
            bw2.setId(Client.getModel().getMap().getBlueSpawnpoint().get(1).getName());
            bw2.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            bw2.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        bw3.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getBlueSpawnpoint().get(2) != null) {
            bw3.setId(Client.getModel().getMap().getBlueSpawnpoint().get(2).getName());
            bw3.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            bw3.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        rw1.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getRedSpawnpoint().get(0) != null) {
            rw1.setId(Client.getModel().getMap().getRedSpawnpoint().get(0).getName());
            rw1.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            rw1.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        rw2.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getRedSpawnpoint().get(1) != null) {
            rw2.setId(Client.getModel().getMap().getRedSpawnpoint().get(1).getName());
            rw2.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            rw2.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        rw3.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getRedSpawnpoint().get(2) != null) {
            rw3.setId(Client.getModel().getMap().getRedSpawnpoint().get(2).getName());
            rw3.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            rw3.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        yw1.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getYellowSpawnpoint().get(0) != null) {
            yw1.setId(Client.getModel().getMap().getYellowSpawnpoint().get(0).getName());
            yw1.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            yw1.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        yw2.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getYellowSpawnpoint().get(1) != null) {
            yw2.setId(Client.getModel().getMap().getYellowSpawnpoint().get(1).getName());
            yw2.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            yw2.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        yw3.setStyle("-fx-background-color: transparent");
        if (Client.getModel().getMap().getYellowSpawnpoint().get(2) != null) {
            yw3.setId(Client.getModel().getMap().getYellowSpawnpoint().get(2).getName());
            yw3.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            yw3.addEventFilter(MouseEvent.MOUSE_CLICKED, selectWSpawn);
        }
        b1.setLayoutX(350);
        b1.setLayoutY(10);
        b1.setPrefSize(60, 91);
        b1.setMaxSize(60, 91);
        b1.getChildren().add(bw1);
        b2.setLayoutX(421);
        b2.setLayoutY(10);
        b2.setPrefSize(60, 91);
        b2.setMaxSize(60, 91);
        b2.getChildren().add(bw2);
        b3.setLayoutX(493);
        b3.setLayoutY(10);
        b3.setPrefSize(60, 91);
        b3.setMaxSize(60, 91);
        b3.getChildren().add(bw3);
        r1.setLayoutY(174);
        r1.setPrefSize(92, 60);
        r1.setMaxSize(92, 60);
        r1.getChildren().add(rw1);
        r2.setLayoutY(245);
        r2.setPrefSize(92, 60);
        r2.setMaxSize(92, 60);
        r2.getChildren().add(rw2);
        r3.setLayoutY(317);
        r3.setPrefSize(92, 60);
        r3.setMaxSize(92, 60);
        r3.getChildren().add(rw3);
        y1.setLayoutX(570);
        y1.setLayoutY(275);
        y1.setPrefSize(90, 60);
        y1.setMaxSize(90, 60);
        y1.getChildren().add(yw1);
        y2.setLayoutX(570);
        y2.setLayoutY(346);
        y2.setPrefSize(90, 60);
        y2.setMaxSize(90, 60);
        y2.getChildren().add(yw2);
        y3.setLayoutX(570);
        y3.setLayoutY(418);
        y3.setPrefSize(90, 60);
        y3.setMaxSize(90, 60);
        y3.getChildren().add(yw3);

        bw1.setPrefSize(60, 91);
        bw1.setMaxSize(60, 91);
        bw2.setPrefSize(60, 91);
        bw2.setMaxSize(60, 91);
        bw3.setPrefSize(60, 91);
        bw3.setMaxSize(60, 91);
        rw1.setPrefSize(92, 60);
        rw1.setMaxSize(92, 60);
        rw2.setPrefSize(92, 60);
        rw2.setMaxSize(92, 60);
        rw3.setPrefSize(92, 60);
        rw3.setMaxSize(92, 60);
        yw1.setPrefSize(90, 60);
        yw1.setMaxSize(90, 60);
        yw2.setPrefSize(90, 60);
        yw2.setMaxSize(90, 60);
        yw3.setPrefSize(90, 60);
        yw3.setMaxSize(90, 60);
    }

    /**
     * Configures the images of each weapon in the spawnpoint according to the color of the spawnpoint
     *
     * @param sp1       the StackPane of the first weapon in the spawn
     * @param sp2       the StackPane of the second weapon in the spawn
     * @param sp3       the StackPane of the third weapon in the spawn
     * @param color     the color of the spawnpoint
     */
    private void setSpawn(StackPane sp1, StackPane sp2, StackPane sp3, char color){
        ArrayList<WeaponClient> temp;
        if (color == 'b')
            temp = Client.getModel().getMap().getBlueSpawnpoint();
        else if (color == 'r')
            temp = Client.getModel().getMap().getRedSpawnpoint();
        else
            temp = Client.getModel().getMap().getYellowSpawnpoint();
        if (!temp.isEmpty() && temp.get(0) != null){
            ImageView w = new ImageView();
            w.setImage(new Image("cards/" + temp.get(0).getName() + ".png"));
            w.setPreserveRatio(true);
            setDimensions(w, color);
            setOrientation(w, color);
            sp1.getChildren().add(w);
        }
        if (temp.size() >= 2 && temp.get(1) != null){
            ImageView w = new ImageView();
            w.setImage(new Image("cards/" + temp.get(1).getName() + ".png"));
            w.setPreserveRatio(true);
            setDimensions(w, color);
            setOrientation(w, color);
            sp2.getChildren().add(w);
        }
        if (temp.size() == 3 && temp.get(2) != null){
                ImageView w = new ImageView();
                w.setImage(new Image("cards/" + temp.get(2).getName() + ".png"));
                w.setPreserveRatio(true);
                setDimensions(w, color);
                setOrientation(w, color);
                sp3.getChildren().add(w);
            }
    }

    /**
     * Configures the orientation of the ImageView of the weapon in the spawn according to the color of the spawn
     *
     * @param imageView     the ImageView of the weapon
     * @param c             the color of the spawnpoint
     */
    private void setOrientation(ImageView imageView, char c){
        if (c == 'r')
            imageView.setRotate(90);
        else if (c == 'y')
            imageView.setRotate(-90);
    }

    /**
     * Configures the dimensions of the ImageView of the weapon in the spawn according to the color of the spawn
     *
     * @param imageView     the ImageView of the weapon
     * @param c             the color of the spawnpoint
     */
    private void setDimensions(ImageView imageView, char c){
        if (c == 'b') {
            imageView.setFitWidth(60);
            imageView.setFitHeight(91);
        }else if (c == 'r'){
            imageView.setFitWidth(60);
            imageView.setFitHeight(92);
        }else {
            imageView.setFitWidth(60);
            imageView.setFitHeight(90);
        }
    }

    /**
     * Configures all the playerboards of the other users in their slots
     */
    private void setPB(){
        Pane p3 = new Pane();
        Pane p4 = new Pane();
        int i = 1;
        for (RealPlayerClientModel p : Client.getModel().getPlayers()){
            if (!p.getPlayerName().equals(MyApplication.getClient().getUsername())){
                setPlayerBoard(p, i);
                i++;
            }
        }
        if (Client.getModel().hasTerminator()) {
            setTerminatorPB(i);
        }
        if (i == 3){
            p3.setPrefSize(650, HEIGH_OTHER);
            p3.setStyle("-fx-background-color: grey");
            pb3HB.setFillHeight(true);
            pb3HB.getChildren().add(p3);
            i++;
        }
        if (i == 4){
            p4.setPrefSize(650, HEIGH_OTHER);
            p4.setStyle("-fx-background-color: grey");
            pb4HB.setFillHeight(true);
            pb4HB.getChildren().add(p4);
            i++;
        }
    }

    /**
     * Sets the terminator playerboard if there is
     *
     * @param i     the slot of the playerboard
     */
    private void setTerminatorPB(int i){
        HBox temp;
        //Pane pane = new Pane();
        StackPane pbSp = new StackPane();
        ImageView pb = new ImageView();
        HBox damages = new HBox();
        VBox marksVbox = new VBox();
        HBox marks = new HBox();
        HBox skullDeath = new HBox();
        int index;
        switch (Client.getModel().getTerminatorClientModel().getColor()){
            case 'b':
                index = 0;
                break;
            case 'e':
                index = 1;
                break;
            case 'g':
                index = 2;
                break;
            case 'v':
                index = 3;
                break;
            default:
                index = 4;
        }
        Text space = new Text("        Score: " + Client.getModel().getPlayersPoint()[index] + "       ");
        space.setFont(new Font(15));
        switch (i) {
            case 1:
                temp = pb1HB;
                break;
            case 2:
                temp = pb2HB;
                break;
            case 3:
                temp = pb3HB;
                break;
            default:
                temp = pb4HB;
        }
        temp.setAlignment(Pos.CENTER);
        temp.getChildren().addAll(space, pbSp);
        ter.setStyle("-fx-background-color: transparent");
        ter.setId(convertColor(Client.getModel().getTerminatorClientModel().getColor()));
        ter.setPrefSize(HEIGH_OTHER*4, HEIGH_OTHER);
        ter.addEventFilter(MouseEvent.MOUSE_CLICKED, sendPlayer);
        setDamages(Client.getModel().getTerminatorClientModel(), damages, 1);
        setMarks(Client.getModel().getTerminatorClientModel(), marks, 1);
        marksVbox.getChildren().add(marks);
        if (Client.getModel().getTerminatorClientModel().getPlayerBoard().getSide() == 1)
            setSkullDeath(Client.getModel().getTerminatorClientModel(), skullDeath, 1);
        pbSp.getChildren().addAll(pb, damages, marksVbox, skullDeath, ter);
        StackPane.setAlignment(marksVbox, Pos.TOP_LEFT);
        setImagePB(Client.getModel().getTerminatorClientModel(), pb);
    }

    /**
     * Sets the playerboard for the player
     *
     * @param p     the player
     * @param i     the slot for the playerboard
     */
    private void setPlayerBoard(RealPlayerClientModel p, int i) {
        HBox temp;
        StackPane w1Sp = new StackPane();
        StackPane w2Sp = new StackPane();
        StackPane w3Sp = new StackPane();
        ImageView p1 = new ImageView();
        ImageView p2 = new ImageView();
        ImageView p3 = new ImageView();
        ImageView w1 = new ImageView();
        ImageView w2 = new ImageView();
        ImageView w3 = new ImageView();
        ImageView t = new ImageView();
        StackPane pbSp = new StackPane();
        Button pl;
        HBox damages = new HBox();
        VBox marksVbox = new VBox();
        HBox marks = new HBox();
        HBox skullDeath = new HBox();
        ImageView pb = new ImageView();
        GridPane ammoBox = new GridPane();
        ImageView first = new ImageView(new Image("board/firstPlayerMarkFront.jpg"));
        ImageView actionFF = new ImageView();
        Text name = new Text();
        switch (i) {
            case 1:
                temp = pb1HB;
                pl = pl1;
                break;
            case 2:
                temp = pb2HB;
                pl = pl2;
                break;
            case 3:
                temp = pb3HB;
                pl = pl3;
                break;
            default:
                temp = pb4HB;
                pl = pl4;
        }
        String s = "  " + p.getPlayerName();
        for (int j = 0; j < 10; j++)
            if (j >= p.getPlayerName().length())
                s += "  ";
        temp.setAlignment(Pos.CENTER_LEFT);
        name.setText(s);
        name.setTextAlignment(TextAlignment.CENTER);
        name.setFont(new Font(15));
        if (p.isDisconnected())
            name.setFill(Color.rgb(167, 49, 23));
        else if (p.getYourTurn())
            name.setFill(Color.rgb(0, 204, 102));
        temp.getChildren().addAll(name, first, t, pbSp, ammoBox, p1, p2, p3, w1Sp, w2Sp, w3Sp);
        if (p.getTerminator()) {
            t.setImage(new Image("cards/terminator.png"));
            t.setFitHeight(HEIGH_OTHER);
            t.setPreserveRatio(true);
        }
        first.setFitHeight(HEIGH_OTHER);
        first.setPreserveRatio(true);
        if (!p.isFirst()) {
            first.setVisible(false);
        }
        ammoBox.setAlignment(Pos.CENTER);
        setOtherPU(p, p1, p2, p3);
        ArrayList<ImageView> tmpW = new ArrayList<>();
        tmpW.add(w1);
        tmpW.add(w2);
        tmpW.add(w3);
        ArrayList<StackPane> tmpSP = new ArrayList<>();
        tmpSP.add(w1Sp);
        tmpSP.add(w2Sp);
        tmpSP.add(w3Sp);
        setW(p, tmpW, tmpSP);
        setDamages(p, damages, 1);
        setMarks(p, marks, 1);
        marksVbox.getChildren().add(marks);
        if (p.getPlayerBoard().getSide() == 1)
            setSkullDeath(p, skullDeath, 1);
        pb.setFitWidth(HEIGH_OTHER*4);
        pl.setId(convertColor(p.getColor()));
        pl.addEventFilter(MouseEvent.MOUSE_CLICKED, sendPlayer);
        pl.setPrefSize(HEIGH_OTHER*4, HEIGH_OTHER);
        pl.setStyle("-fx-background-color: transparent");
        if (Client.getModel().isFinalFrenzy()){
            actionFF.setImage(new Image("board/" + p.getColor() + "ActionFF.png"));
            actionFF.setFitHeight(HEIGH_OTHER);
            actionFF.setPreserveRatio(true);
        }
        pbSp.getChildren().addAll(pb, actionFF, damages, marksVbox, skullDeath, pl);
        StackPane.setAlignment(marksVbox, Pos.TOP_LEFT);
        StackPane.setAlignment(actionFF, Pos.CENTER_LEFT);
        setImagePB(p, pb);
        setAmmoBox(p, ammoBox);
    }

    /**
     * Sets the damages of the player on its playerboard
     *
     * @param pl        the player
     * @param hb        the box of the damages
     * @param j         the factor used to multiply the dimensions
     */
    private void setDamages(PlayerClientModel pl, HBox hb, double j){
        Pane p = new Pane();
        GridPane g = new GridPane();
        RowConstraints r = new RowConstraints();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        ColumnConstraints c5 = new ColumnConstraints();
        ColumnConstraints c6 = new ColumnConstraints();
        ColumnConstraints c7 = new ColumnConstraints();
        ColumnConstraints c8 = new ColumnConstraints();
        ColumnConstraints c9 = new ColumnConstraints();
        ColumnConstraints c10 = new ColumnConstraints();
        ColumnConstraints c11 = new ColumnConstraints();
        ColumnConstraints c12 = new ColumnConstraints();
        ArrayList<ImageView> dam = new ArrayList<>();

        hb.setAlignment(Pos.CENTER_LEFT);
        g.setAlignment(Pos.CENTER);
        if (Client.getModel().isFinalFrenzy()) {
            p.setPrefSize(45 * j, HEIGH_OTHER * j);
        } else {
            p.setPrefSize(42 * j, HEIGH_OTHER * j);
        }
        g.getRowConstraints().add(r);
        c1.setMinWidth(25*j);
        c2.setMinWidth(25*j);
        c3.setMinWidth(25.5*j);
        c4.setMinWidth(25.5*j);
        c5.setMinWidth(25.5*j);
        c6.setMinWidth(25*j);
        c7.setMinWidth(25*j);
        c8.setMinWidth(25*j);
        c9.setMinWidth(24.5*j);
        c10.setMinWidth(24.5*j);
        c11.setMinWidth(25*j);
        c12.setMinWidth(25*j);
        g.getColumnConstraints().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12);
        for (int i = 0; i < pl.getPlayerBoard().getDamages().length; i++) {
            if (pl.getPlayerBoard().getDamages()[i] != '\u0000') {
                dam.add(new ImageView(new Image("board/" + pl.getPlayerBoard().getDamages()[i] + "Drop.png")));
                dam.get(i).setPreserveRatio(true);
                dam.get(i).setFitHeight(25*j);
            }
        }
        for (int i = 0; i < dam.size(); i++)
            g.add(dam.get(i), i, 0);
        hb.getChildren().addAll(p, g);
    }

    /**
     * Sets the marks of the player on its playerboard
     *
     * @param pl        the player
     * @param hb        the box for the marks
     * @param j         the factor used to multiply the dimensions
     */
    private void setMarks(PlayerClientModel pl, HBox hb, double j){
        Pane p = new Pane();
        GridPane g = new GridPane();
        RowConstraints r = new RowConstraints();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        ColumnConstraints c5 = new ColumnConstraints();
        StackPane bl = new StackPane();
        StackPane em = new StackPane();
        StackPane gr = new StackPane();
        StackPane vi = new StackPane();
        StackPane ye = new StackPane();
        ImageView blue = new ImageView(new Image("board/bDrop.png"));
        ImageView emerald = new ImageView(new Image("board/eDrop.png"));
        ImageView grey = new ImageView(new Image("board/gDrop.png"));
        ImageView violet = new ImageView(new Image("board/vDrop.png"));
        ImageView yellow = new ImageView(new Image("board/yDrop.png"));
        Text t1 = new Text();
        Text t2 = new Text();
        Text t3 = new Text();
        Text t4 = new Text();
        Text t5 = new Text();

        //p.setPrefSize(HEIGH_OTHER*2*j + 20*j, 35*j);
        p.setPrefSize(HEIGH_OTHER*2*j + 20*j, 25*j);
        g.getRowConstraints().add(r);
        g.getColumnConstraints().addAll(c1, c2, c3, c4, c5);
        g.add(bl, 0, 0);
        g.add(em, 1, 0);
        g.add(gr, 2, 0);
        g.add(vi, 3, 0);
        g.add(ye, 4, 0);
        blue.setPreserveRatio(true);
        blue.setFitHeight(25*j);
        emerald.setPreserveRatio(true);
        emerald.setFitHeight(25*j);
        grey.setPreserveRatio(true);
        grey.setFitHeight(25*j);
        violet.setPreserveRatio(true);
        violet.setFitHeight(25*j);
        yellow.setPreserveRatio(true);
        yellow.setFitHeight(25*j);
        t1.setText("" + pl.getPlayerBoard().getMarkedDamages()[0]);
        t1.setTextAlignment(TextAlignment.CENTER);
        t1.setFont(new Font(13*j));
        t2.setText("" + pl.getPlayerBoard().getMarkedDamages()[1]);
        t2.setTextAlignment(TextAlignment.CENTER);
        t2.setFont(new Font(13*j));
        t3.setText("" + pl.getPlayerBoard().getMarkedDamages()[2]);
        t3.setTextAlignment(TextAlignment.CENTER);
        t3.setFont(new Font(13*j));
        t4.setText("" + pl.getPlayerBoard().getMarkedDamages()[3]);
        t4.setTextAlignment(TextAlignment.CENTER);
        t4.setFont(new Font(13*j));
        t5.setText("" + pl.getPlayerBoard().getMarkedDamages()[4]);
        t5.setTextAlignment(TextAlignment.CENTER);
        t5.setFont(new Font(13*j));
        bl.setAlignment(Pos.CENTER);
        bl.getChildren().addAll(blue, t1);
        bl.setMaxHeight(25*j);
        em.setAlignment(Pos.CENTER);
        em.getChildren().addAll(emerald, t2);
        em.setMaxHeight(25*j);
        gr.setAlignment(Pos.CENTER);
        gr.getChildren().addAll(grey, t3);
        gr.setMaxHeight(25*j);
        vi.setAlignment(Pos.CENTER);
        vi.getChildren().addAll(violet, t4);
        vi.setMaxHeight(25*j);
        ye.setAlignment(Pos.CENTER);
        ye.getChildren().addAll(yellow, t5);
        ye.setMaxHeight(25*j);
        hb.getChildren().addAll(p, bl, em, gr, vi, ye);
        if (t1.getText().equals("0"))
            bl.setVisible(false);
        if (t2.getText().equals("0"))
            em.setVisible(false);
        if (t3.getText().equals("0"))
            gr.setVisible(false);
        if (t4.getText().equals("0"))
            vi.setVisible(false);
        if (t5.getText().equals("0"))
            ye.setVisible(false);
        hb.setAlignment(Pos.TOP_LEFT);
        g.setAlignment(Pos.BOTTOM_LEFT);
    }

    /**
     * Sets the skulls on the playerboard for the player
     *
     * @param pl        the player
     * @param hb        the box for the skulls
     * @param j         the factor used to multiply dimensions
     */
    private void setSkullDeath(PlayerClientModel pl, HBox hb, double j){
        Pane p = new Pane();
        GridPane g = new GridPane();
        RowConstraints r = new RowConstraints();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        ColumnConstraints c5 = new ColumnConstraints();
        ColumnConstraints c6 = new ColumnConstraints();
        ImageView s1 = new ImageView(new Image("board/skull.png"));
        ImageView s2 = new ImageView(new Image("board/skull.png"));
        ImageView s3 = new ImageView(new Image("board/skull.png"));
        ImageView s4 = new ImageView(new Image("board/skull.png"));
        ImageView s5 = new ImageView(new Image("board/skull.png"));
        ImageView s6 = new ImageView(new Image("board/skull.png"));

        p.setPrefSize(HEIGH_OTHER*j - 15*j, HEIGH_OTHER*j - 10*j);
        g.getRowConstraints().add(r);
        g.getColumnConstraints().addAll(c1, c2, c3, c4, c5, c6);
        c1.setMinWidth(24*j);
        c2.setMinWidth(23*j);
        c3.setMinWidth(23*j);
        c4.setMinWidth(23*j);
        c5.setMinWidth(23*j);
        c6.setMinWidth(23*j);
        s1.setFitHeight(25*j);
        s1.setPreserveRatio(true);
        if (pl.getPlayerBoard().getnDeath() < 1)
            s1.setVisible(false);
        g.add(s1, 0, 0);
        s2.setFitHeight(25*j);
        s2.setPreserveRatio(true);
        if (pl.getPlayerBoard().getnDeath() < 2)
            s2.setVisible(false);
        g.add(s2, 1, 0);
        s3.setFitHeight(25*j);
        s3.setPreserveRatio(true);
        if (pl.getPlayerBoard().getnDeath() < 3)
            s3.setVisible(false);
        g.add(s3, 2, 0);
        s4.setFitHeight(25*j);
        s4.setPreserveRatio(true);
        if (pl.getPlayerBoard().getnDeath() < 4)
            s4.setVisible(false);
        g.add(s4, 3, 0);
        s5.setFitHeight(25*j);
        s5.setPreserveRatio(true);
        if (pl.getPlayerBoard().getnDeath() < 5)
            s5.setVisible(false);
        g.add(s5, 4, 0);
        s6.setFitHeight(25*j);
        s6.setPreserveRatio(true);
        if (pl.getPlayerBoard().getnDeath() < 6)
            s6.setVisible(false);
        g.add(s6, 5, 0);
        hb.getChildren().addAll(p, g);
        g.setAlignment(Pos.BOTTOM_LEFT);
        hb.setAlignment(Pos.TOP_LEFT);
        hb.setMaxHeight(HEIGH_OTHER*j - 10*j);
    }

    /**
     * Converts the char into the color string
     *
     * @param c     the color
     * @return      the color as a String
     */
    private String convertColor(char c){
        String s;
        switch (c){
            case 'b':
                s = "blue";
                break;
            case 'e':
                s = "emerald";
                break;
            case 'g':
                s = "grey";
                break;
            case 'v':
                s = "violet";
                break;
            default:
                s = "yellow";
        }
        return s;
    }

    /**
     * Sets the ammo box for the player
     *
     * @param p             the player
     * @param ammoBox       the box for the ammo
     */
    private void setAmmoBox(RealPlayerClientModel p, GridPane ammoBox){
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHalignment(HPos.CENTER);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHalignment(HPos.CENTER);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setHalignment(HPos.CENTER);
        RowConstraints r1 = new RowConstraints();
        r1.setValignment(VPos.CENTER);
        r1.setMinHeight(30);
        RowConstraints r2 = new RowConstraints();
        r2.setMinHeight(30);
        r2.setValignment(VPos.CENTER);
        RowConstraints r3 = new RowConstraints();
        r3.setMinHeight(30);
        r3.setValignment(VPos.CENTER);
        ammoBox.getColumnConstraints().addAll(c1, c2, c3);
        ammoBox.getRowConstraints().addAll(r1, r2, r3);
        ammoBox.setMinHeight(HEIGH_OTHER);
        if (p.getPlayerName().equals(MyApplication.getClient().getUsername()))
            ammoBox.setMinHeight(HEIGH_OWN);
        for (int j = 0; j < p.getPlayerBoard().getAmmoBox()[0]; j++) {
            ImageView b = new ImageView(new Image("board/blueAmmo.png"));
            b.setPreserveRatio(true);
            if (p.getPlayerName().equals(MyApplication.getClient().getUsername()))
                b.setFitHeight(40);
            else
                b.setFitHeight(20);
            ammoBox.add(b, j, 0);
        }
        for (int j = 0; j < p.getPlayerBoard().getAmmoBox()[1]; j++) {
            ImageView r = new ImageView(new Image("board/redAmmo.png"));
            r.setPreserveRatio(true);
            if (p.getPlayerName().equals(MyApplication.getClient().getUsername()))
                r.setFitHeight(40);
            else
                r.setFitHeight(20);
            ammoBox.add(r, j, 1);
        }
        for (int j = 0; j < p.getPlayerBoard().getAmmoBox()[2]; j++) {
            ImageView y = new ImageView(new Image("board/yellowAmmo.png"));
            y.setPreserveRatio(true);
            if (p.getPlayerName().equals(MyApplication.getClient().getUsername()))
                y.setFitHeight(40);
            else
                y.setFitHeight(20);
            ammoBox.add(y, j, 2);
        }
    }

    /**
     * Sets the images of the powerups of the other players
     *
     * @param p     the player
     * @param p1    the firs powerup image
     * @param p2    the second powerup image
     * @param p3    the third powerup image
     */
    private void setOtherPU(RealPlayerClientModel p, ImageView p1, ImageView p2, ImageView p3){
        if (!p.getPlayerHand().getPowerups().isEmpty()) {
            p1.setImage(new Image("cards/powerupRear.png"));
            p1.setFitHeight(HEIGH_OTHER);
            p1.setPreserveRatio(true);
        }else {
            p1.setImage(new Image("cards/powerupSlot.png"));
            p1.setFitHeight(HEIGH_OTHER);
            p1.setPreserveRatio(true);
        }
        if (p.getPlayerHand().getPowerups().size() >= 2) {
            p2.setImage(new Image("cards/powerupRear.png"));
            p2.setFitHeight(HEIGH_OTHER);
            p2.setPreserveRatio(true);
        }else {
            p2.setImage(new Image("cards/powerupSlot.png"));
            p2.setFitHeight(HEIGH_OTHER);
            p2.setPreserveRatio(true);
        }
        if (p.getPlayerHand().getPowerups().size() == 3) {
            p3.setImage(new Image("cards/powerupRear.png"));
            p3.setFitHeight(HEIGH_OTHER);
            p3.setPreserveRatio(true);
        }else {
            p3.setImage(new Image("cards/powerupSlot.png"));
            p3.setFitHeight(HEIGH_OTHER);
            p3.setPreserveRatio(true);
        }
    }

    /**
     * Sets the powerups images owned by the client
     *
     * @param p     the player
     * @param pu    the list of powerup images
     * @param pSp   the list of panes where the images lay
     */
    private void setOwnPU(RealPlayerClientModel p, ArrayList<ImageView> pu, ArrayList<StackPane> pSp){
        if (!p.getPlayerHand().getPowerups().isEmpty()) {
            pu.get(0).setImage(new Image("cards/" + p.getPlayerHand().getPowerups().get(0).getName() + " " + p.getPlayerHand().getPowerups().get(0).getColor() + ".png"));
            pu.get(0).setFitHeight(HEIGH_OTHER);
            pu.get(0).setPreserveRatio(true);
            pSp.get(0).getChildren().clear();
            pSp.get(0).getChildren().addAll(pu.get(0), p1B);
            p1B.setPrefSize(HEIGH_OWN*2/3, HEIGH_OWN);
            p1B.setStyle("-fx-background-color: transparent");
            p1B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectPU);
            p1B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            p1B.setId(p.getPlayerHand().getPowerups().get(0).getName() + " " + p.getPlayerHand().getPowerups().get(0).getColor());
        }else {
            pu.get(0).setImage(new Image("cards/powerupSlot.png"));
            pu.get(0).setFitHeight(HEIGH_OTHER);
            pu.get(0).setPreserveRatio(true);
            pSp.get(0).getChildren().clear();
            pSp.get(0).getChildren().add(pu.get(0));
        }
        if (p.getPlayerHand().getPowerups().size() >= 2) {
            pu.get(1).setImage(new Image("cards/" + p.getPlayerHand().getPowerups().get(1).getName() + " " + p.getPlayerHand().getPowerups().get(1).getColor() + ".png"));
            pu.get(1).setFitHeight(HEIGH_OTHER);
            pu.get(1).setPreserveRatio(true);
            pSp.get(1).getChildren().clear();
            pSp.get(1).getChildren().addAll(pu.get(1), p2B);
            p2B.setPrefSize(HEIGH_OWN*2/3, HEIGH_OWN);
            p2B.setStyle("-fx-background-color: transparent");
            p2B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectPU);
            p2B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            p2B.setId(p.getPlayerHand().getPowerups().get(1).getName() + " " + p.getPlayerHand().getPowerups().get(1).getColor());
        }else {
            pu.get(1).setImage(new Image("cards/powerupSlot.png"));
            pu.get(1).setFitHeight(HEIGH_OTHER);
            pu.get(1).setPreserveRatio(true);
            pSp.get(1).getChildren().clear();
            pSp.get(1).getChildren().add(pu.get(1));
        }
        if (p.getPlayerHand().getPowerups().size() == 3) {
            pu.get(2).setImage(new Image("cards/" + p.getPlayerHand().getPowerups().get(2).getName() + " " + p.getPlayerHand().getPowerups().get(2).getColor() + ".png"));
            pu.get(2).setFitHeight(HEIGH_OTHER);
            pu.get(2).setPreserveRatio(true);
            pSp.get(2).getChildren().clear();
            pSp.get(2).getChildren().addAll(pu.get(2), p3B);
            p3B.setPrefSize(HEIGH_OWN*2/3, HEIGH_OWN);
            p3B.setStyle("-fx-background-color: transparent");
            p3B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectPU);
            p3B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            p3B.setId(p.getPlayerHand().getPowerups().get(2).getName() + " " + p.getPlayerHand().getPowerups().get(2).getColor());
        }else {
            pu.get(2).setImage(new Image("cards/powerupSlot.png"));
            pu.get(2).setFitHeight(HEIGH_OTHER);
            pu.get(2).setPreserveRatio(true);
            pSp.get(2).getChildren().clear();
            pSp.get(2).getChildren().add(pu.get(2));
        }
        if (p.getPlayerHand().getPowerups().size() == 4){
            pu.get(3).setImage(new Image("cards/" + p.getPlayerHand().getPowerups().get(3).getName() + " " + p.getPlayerHand().getPowerups().get(3).getColor() + ".png"));
            pu.get(3).setPreserveRatio(true);
            pu.get(3).setFitHeight(HEIGH_OTHER);
            pSp.get(3).getChildren().clear();
            pSp.get(3).getChildren().addAll(pu.get(3), p4B);
            p4B.setPrefSize(HEIGH_OWN*2/3, HEIGH_OWN);
            p4B.setStyle("-fx-background-color: transparent");
            p4B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectPU);
            p4B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
            p3B.setId(p.getPlayerHand().getPowerups().get(3).getName() + " " + p.getPlayerHand().getPowerups().get(3).getColor());
        }
    }

    /**
     * Sets the images of the weapons for the player
     *
     * @param p     the player
     * @param w     the list of weapons' images
     * @param wSp   the list of pane where images lay
     */
    private void setW(RealPlayerClientModel p, ArrayList<ImageView> w, ArrayList<StackPane> wSp){
        Button w1B = new Button();
        Button w2B = new Button();
        Button w3B = new Button();
        if (p.getPlayerName().equals(MyApplication.getClient().getUsername())){
            w1B = ownW1B;
            w2B = ownW2B;
            w3B = ownW3B;
        }
        if (!p.getPlayerHand().getWeapons().isEmpty()) {
            w.get(0).setImage(new Image("cards/"+ p.getPlayerHand().getWeapons().get(0).getName() + ".png"));
            w.get(0).setFitHeight(HEIGH_OTHER);
            w.get(0).setPreserveRatio(true);
            w1B.setStyle("-fx-background-color: transparent");
            if (!p.getPlayerHand().getWeapons().get(0).isLoaded()) {
                w1B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
                w1B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectW);
                if (p.getPlayerName().equals(MyApplication.getClient().getUsername()))
                    w.get(0).setOpacity(0.5);
            }else {
                if (!p.getPlayerName().equals(MyApplication.getClient().getUsername())) {
                    w.get(0).setImage(new Image("cards/weaponRear.png"));
                    w.get(0).setFitHeight(HEIGH_OTHER);
                    w.get(0).setPreserveRatio(true);
                }else {
                    w1B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
                    w1B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectW);
                }
            }
            wSp.get(0).getChildren().addAll(w.get(0), w1B);
            if (p.getPlayerName().equals(MyApplication.getClient().getUsername())) {
                w1B.setPrefSize(HEIGH_OWN * 2 / 3, HEIGH_OWN);
                if (p.getPlayerHand().getWeapons().get(0).getName().equals(weaponChosen) && (p.getPlayerHand().getWeapons().get(0).isOpt1Effect() || p.getPlayerHand().getWeapons().get(0).isAltEffect())){
                    effectsButton(Client.getModel().getWeaponClient(p.getPlayerHand().getWeapons().get(0).getName()), wSp.get(0));
                }
            } else
                w1B.setPrefSize(HEIGH_OTHER*2/3, HEIGH_OTHER);
            w1B.setId(p.getPlayerHand().getWeapons().get(0).getName());
        }else {
            w.get(0).setImage(new Image("cards/weaponSlot.png"));
            w.get(0).setFitHeight(HEIGH_OTHER);
            w.get(0).setPreserveRatio(true);
            wSp.get(0).getChildren().clear();
            wSp.get(0).getChildren().add(w.get(0));
        }
        if (p.getPlayerHand().getWeapons().size() >= 2) {
            w.get(1).setImage(new Image("cards/" + p.getPlayerHand().getWeapons().get(1).getName() + ".png"));
            w.get(1).setFitHeight(HEIGH_OTHER);
            w.get(1).setPreserveRatio(true);
            w2B.setStyle("-fx-background-color: transparent");
            if (!p.getPlayerHand().getWeapons().get(1).isLoaded()) {
                if (p.getPlayerName().equals(MyApplication.getClient().getUsername()))
                    w.get(1).setOpacity(0.5);
                w2B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
                w2B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectW);
            } else {
                if (!p.getPlayerName().equals(MyApplication.getClient().getUsername())){
                    w.get(1).setImage(new Image("cards/weaponRear.png"));
                    w.get(1).setFitHeight(HEIGH_OTHER);
                    w.get(1).setPreserveRatio(true);
                } else {
                    w2B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
                    w2B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectW);
                }
            }
            wSp.get(1).getChildren().addAll(w.get(1), w2B);
            if (p.getPlayerName().equals(MyApplication.getClient().getUsername())) {
                w2B.setPrefSize(HEIGH_OWN * 2 / 3, HEIGH_OWN);
                if (p.getPlayerHand().getWeapons().get(1).getName().equals(weaponChosen) && (p.getPlayerHand().getWeapons().get(1).isOpt1Effect() || p.getPlayerHand().getWeapons().get(1).isAltEffect())){
                    effectsButton(Client.getModel().getWeaponClient(p.getPlayerHand().getWeapons().get(1).getName()), wSp.get(1));
                }
            } else
                w2B.setPrefSize(HEIGH_OTHER*2/3, HEIGH_OTHER);
            w2B.setId(p.getPlayerHand().getWeapons().get(1).getName());
        }else {
            w.get(1).setImage(new Image("cards/weaponSlot.png"));
            w.get(1).setFitHeight(HEIGH_OTHER);
            w.get(1).setPreserveRatio(true);
            wSp.get(1).getChildren().clear();
            wSp.get(1).getChildren().add(w.get(1));
        }
        if (p.getPlayerHand().getWeapons().size() == 3) {
            w.get(2).setImage(new Image("cards/" + p.getPlayerHand().getWeapons().get(2).getName() + ".png"));
            w.get(2).setFitHeight(HEIGH_OTHER);
            w.get(2).setPreserveRatio(true);
            w3B.setStyle("-fx-background-color: transparent");
            if (!p.getPlayerHand().getWeapons().get(2).isLoaded()) {
                if (p.getPlayerName().equals(MyApplication.getClient().getUsername()))
                    w.get(2).setOpacity(0.5);
                w3B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
                w3B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectW);
            } else {
                if (!p.getPlayerName().equals(MyApplication.getClient().getUsername())){
                    w.get(2).setImage(new Image("cards/weaponRear.png"));
                    w.get(2).setFitHeight(HEIGH_OTHER);
                    w.get(2).setPreserveRatio(true);
                } else {
                    w3B.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
                    w3B.addEventFilter(MouseEvent.MOUSE_CLICKED, selectW);
                }
            }
            wSp.get(2).getChildren().addAll(w.get(2), w3B);
            if (p.getPlayerName().equals(MyApplication.getClient().getUsername())) {
                w3B.setPrefSize(HEIGH_OWN * 2 / 3, HEIGH_OWN);
                if (p.getPlayerHand().getWeapons().get(2).getName().equals(weaponChosen) && (p.getPlayerHand().getWeapons().get(2).isOpt1Effect() || p.getPlayerHand().getWeapons().get(2).isAltEffect())){
                    effectsButton(Client.getModel().getWeaponClient(p.getPlayerHand().getWeapons().get(2).getName()), wSp.get(2));
                }
            } else
                w3B.setPrefSize(HEIGH_OTHER*2/3, HEIGH_OTHER);
            w3B.setId(p.getPlayerHand().getWeapons().get(2).getName());
        }else {
            w.get(2).setImage(new Image("cards/weaponSlot.png"));
            w.get(2).setFitHeight(HEIGH_OTHER);
            w.get(2).setPreserveRatio(true);
            wSp.get(2).getChildren().clear();
            wSp.get(2).getChildren().add(w.get(2));
        }
    }

    private void effectsButton(WeaponClient w, StackPane wSp){
        VBox cardButtons = new VBox();
        empty = new Button();
        base = new Button();
        alt = new Button();
        opt1 = new Button();
        opt2 = new Button();

        wSp.getChildren().add(cardButtons);
        cardButtons.setAlignment(Pos.CENTER);
        StackPane.setAlignment(cardButtons, Pos.CENTER);
        empty.setPrefSize(HEIGH_OWN * 2 / 3, 85);
        empty.setId(w.getName());
        empty.setStyle("-fx-background-color: transparent");
        empty.addEventFilter(MouseEvent.MOUSE_CLICKED, show);
        empty.addEventFilter(MouseEvent.MOUSE_CLICKED, selectW);
        cardButtons.getChildren().addAll(empty, base);
        StackPane.setAlignment(empty, Pos.CENTER);
        StackPane.setAlignment(base, Pos.CENTER);
        base.addEventFilter(MouseEvent.MOUSE_CLICKED, sendEffect);
        opt1.addEventFilter(MouseEvent.MOUSE_CLICKED, sendEffect);
        opt2.addEventFilter(MouseEvent.MOUSE_CLICKED, sendEffect);
        alt.addEventFilter(MouseEvent.MOUSE_CLICKED, sendEffect);
        base.setPrefSize(HEIGH_OWN * 2 / 3 - 20, 50);
        base.setOpacity(0.4);
        opt1.setDisable(true);
        opt2.setDisable(true);
        alt.setDisable(true);
        base.setId("1");
        opt1.setId("2");
        opt2.setId("3");
        alt.setId("4");
        if (w.isAltEffect()) {
            alt.setDisable(false);
            alt.setPrefSize(HEIGH_OWN * 2 / 3 - 20, 60);
            alt.setOpacity(0.4);
            cardButtons.getChildren().add(alt);
            StackPane.setAlignment(alt, Pos.CENTER);
        }else if (w.isOpt1Effect() && w.isOpt2Effect()){
            opt1.setDisable(false);
            opt1.setPrefSize(HEIGH_OWN / 3 - 15, 60);
            opt1.setLayoutX(0);
            opt1.setOpacity(0.4);
            opt2.setDisable(false);
            opt2.setPrefSize(HEIGH_OWN / 3 - 15, 60);
            opt2.setOpacity(0.4);
            HBox opts = new HBox(opt1, opt2);
            opts.setAlignment(Pos.CENTER);
            cardButtons.getChildren().add(opts);
            StackPane.setAlignment(opts, Pos.CENTER);
        }else if (w.isOpt1Effect() && !w.isOpt2Effect()){
            opt1.setDisable(false);
            opt1.setPrefSize(HEIGH_OWN * 2 / 3 - 40, 60);
            opt1.setOpacity(0.4);
            cardButtons.getChildren().add(opt1);
            StackPane.setAlignment(opt1, Pos.CENTER);
        }
        if (opt1.isDisabled() && opt2.isDisabled() && alt.isDisabled())
            base.setPrefSize(HEIGH_OWN * 2 / 3 - 10, HEIGH_OWN / 2 + 10);
        try {
            if (!Client.getModel().getPlayer(MyApplication.getClient().getUsername()).getYourTurn()){
                base.setVisible(false);
                opt1.setVisible(false);
                opt2.setVisible(false);
                alt.setVisible(false);
            }
        }catch (Exception e){
            //e.printStackTrace();
        }
    }

    private void setImagePB(PlayerClientModel p, ImageView pb){
        if (p.getPlayerBoard().getSide() == 1)
            pb.setImage(new Image("board/playerBoardFront" + p.getColor() + ".jpg"));
        else
            pb.setImage(new Image("board/playerBoardRear" + p.getColor() + ".jpg"));
        pb.setPreserveRatio(true);
        pb.setFitHeight(HEIGH_OTHER);
        if (Client.getModel().getPlayer(MyApplication.getClient().getUsername()).getColor() != p.getColor())
            pb.setFitWidth(HEIGH_OTHER*4);
    }

    private void setPlayer(){
        ImageView firstMarker = new ImageView();
        StackPane terminatorSp = new StackPane();
        ImageView terminator = new ImageView();
        GridPane actions = new GridPane();
        ImageView playerBoard = new ImageView();
        GridPane payMethod = new GridPane();
        GridPane ammo = new GridPane();
        ImageView w1 = new ImageView();
        ImageView w2 = new ImageView();
        ImageView w3 = new ImageView();
        ImageView p1 = new ImageView();
        ImageView p2 = new ImageView();
        ImageView p3 = new ImageView();
        ImageView p4 = new ImageView();
        StackPane w1Sp = new StackPane();
        StackPane w2Sp = new StackPane();
        StackPane w3Sp = new StackPane();
        StackPane p1Sp = new StackPane();
        StackPane p2Sp = new StackPane();
        StackPane p3Sp = new StackPane();
        StackPane p4Sp = new StackPane();
        StackPane pbSp = new StackPane();
        HBox damages = new HBox();
        VBox marksVbox = new VBox();
        HBox marks = new HBox();
        HBox skullDeath = new HBox();
        ImageView actionFF = new ImageView();
        Text turn = new Text("   It's your turn!   ");
        Text empty = new Text();
        Text score = new Text();
        VBox turnScore = new VBox(turn, empty, score);
        RealPlayerClientModel p = Client.getModel().getPlayer(MyApplication.getClient().getUsername());
        int points;
        switch (p.getColor()){
            case 'b':
                points = 0;
                break;
            case 'e':
                points = 1;
                break;
            case 'g':
                points = 2;
                break;
            case 'v':
                points = 3;
                break;
            default:
                points = 4;

        }
        score.setText("Score: " + Client.getModel().getPlayersPoint()[points]);
        turn.setTextAlignment(TextAlignment.CENTER);
        turn.setFont(new Font(18));
        if (!p.getYourTurn())
            turn.setVisible(false);
        setImagePB(p, playerBoard);
        weapons.clear();
        weapons.add(w1);
        weapons.add(w2);
        weapons.add(w3);
        powerups.clear();
        powerups.add(p1);
        powerups.add(p2);
        powerups.add(p3);
        powerups.add(p4);
        stackPanesW.clear();
        stackPanesW.add(w1Sp);
        stackPanesW.add(w2Sp);
        stackPanesW.add(w3Sp);
        stackPanesPU.clear();
        stackPanesPU.add(p1Sp);
        stackPanesPU.add(p2Sp);
        stackPanesPU.add(p3Sp);
        stackPanesPU.add(p4Sp);
        for (StackPane s : stackPanesW)
            s.setStyle("-fx-background-color: grey");
        for (StackPane s : stackPanesPU)
            s.setStyle("-fx-background-color: grey");
        setW(p, weapons, stackPanesW);
        setOwnPU(p, powerups, stackPanesPU);
        setAmmoBox(p, ammo);
        ammo.setAlignment(Pos.CENTER);
        setActions(actions);
        setPayMethod(payMethod);
        if (p.getTerminator()){
            terminator.setImage(new Image("cards/terminator.png"));
            terminator.setFitHeight(HEIGH_OWN);
            terminator.setPreserveRatio(true);
            terB.setPrefSize(HEIGH_OWN * 2 / 3, HEIGH_OWN);
            terB.setStyle("-fx-background-color: transparent");
            terB.setId("terminator");
            terB.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            //terB.setOpacity(0.3);
            terminatorSp.getChildren().addAll(terminator, terB);
        }
        score.setTextAlignment(TextAlignment.CENTER);
        score.setFont(new Font(18));
        setDamages(p, damages, 1.825);
        setMarks(p, marks, 1.825);
        marksVbox.getChildren().add(marks);
        if (p.getPlayerBoard().getSide() == 1)
            setSkullDeath(p, skullDeath, 1.84);
        if (Client.getModel().isFinalFrenzy()){
            actionFF.setImage(new Image("board/" + p.getColor() + "ActionFF.png"));
            actionFF.setPreserveRatio(true);
            actionFF.setFitHeight(HEIGH_OWN);
        }
        pbSp.getChildren().addAll(playerBoard, actionFF, damages, marksVbox, skullDeath);
        StackPane.setAlignment(marksVbox, Pos.TOP_LEFT);
        StackPane.setAlignment(actionFF, Pos.CENTER_LEFT);
        if (p.isFirst())
            firstMarker.setImage(new Image("board/firstPlayerMarkFront.jpg"));
        player.getChildren().addAll(turnScore, firstMarker, terminatorSp, actions, pbSp, payMethod, ammo, p1Sp, p2Sp, p3Sp, p4Sp, w1Sp, w2Sp, w3Sp);
        turnScore.setAlignment(Pos.CENTER);
        player.setAlignment(Pos.CENTER);
        firstMarker.setPreserveRatio(true);
        firstMarker.setFitHeight(HEIGH_OWN);
        terminator.setPreserveRatio(true);
        terminator.setFitHeight(HEIGH_OWN);
        playerBoard.setFitHeight(HEIGH_OWN);
        w1.setFitHeight(HEIGH_OWN);
        w2.setFitHeight(HEIGH_OWN);
        w3.setFitHeight(HEIGH_OWN);
        p1.setFitHeight(HEIGH_OWN);
        p2.setFitHeight(HEIGH_OWN);
        p3.setFitHeight(HEIGH_OWN);
        if (!Client.getModel().getPlayer(MyApplication.getClient().getUsername()).isFirst())
            firstMarker.setVisible(false);
        scrollPlayer.setContent(player);
        scrollPlayer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPlayer.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void setPayMethod(GridPane g){
        ColumnConstraints c = new ColumnConstraints();
        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        RowConstraints r3 = new RowConstraints();

        ammoOrBlue.setText("Ammo");
        powerupOrRed.setText("Powerup");
        bothOrYellow.setText("Both");
        g.setMinHeight(HEIGH_OWN);
        g.setAlignment(Pos.CENTER);
        g.getColumnConstraints().add(c);
        g.getRowConstraints().addAll(r1, r2, r3);
        ammoOrBlue.setPrefWidth(100);
        ammoOrBlue.setId("1");
        ammoOrBlue.addEventFilter(MouseEvent.MOUSE_CLICKED, payChoice);
        ammoOrBlue.setTextAlignment(TextAlignment.CENTER);
        g.add(ammoOrBlue, 0, 0);
        powerupOrRed.setPrefWidth(100);
        powerupOrRed.setId("2");
        powerupOrRed.addEventFilter(MouseEvent.MOUSE_CLICKED, payChoice);
        powerupOrRed.setTextAlignment(TextAlignment.CENTER);
        g.add(powerupOrRed, 0, 1);
        bothOrYellow.setPrefWidth(100);
        bothOrYellow.setId("3");
        bothOrYellow.addEventFilter(MouseEvent.MOUSE_CLICKED, payChoice);
        bothOrYellow.setTextAlignment(TextAlignment.CENTER);
        g.add(bothOrYellow, 0, 2);
    }

    private void setActions(GridPane g){
        ColumnConstraints c = new ColumnConstraints();
        RowConstraints r0 = new RowConstraints();
        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        RowConstraints r3 = new RowConstraints();
        RowConstraints r4 = new RowConstraints();
        RowConstraints r5 = new RowConstraints();

        g.setMinHeight(HEIGH_OWN);
        g.setAlignment(Pos.CENTER);
        g.getColumnConstraints().add(c);
        g.getRowConstraints().addAll(r0, r1, r2, r3, r4, r5);
        if (!Client.getModel().isFinalFrenzy()) {
            g.add(powerup, 0, 0);
            g.add(run, 0, 1);
            g.add(runAndGrab, 0, 2);
            g.add(shootButton, 0, 3);
            g.add(reload, 0, 4);
            powerup.setId("4");
            powerup.setPrefWidth(100);
            powerup.setTextAlignment(TextAlignment.CENTER);
            powerup.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            run.setId("1");
            run.setPrefWidth(100);
            run.setTextAlignment(TextAlignment.CENTER);
            run.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            runAndGrab.setId("2");
            runAndGrab.setPrefWidth(100);
            runAndGrab.setTextAlignment(TextAlignment.CENTER);
            runAndGrab.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            shootButton.setId("3");
            shootButton.setPrefWidth(100);
            shootButton.setTextAlignment(TextAlignment.CENTER);
            shootButton.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            reload.setId("1");
            reload.setPrefWidth(100);
            reload.setTextAlignment(TextAlignment.CENTER);
            reload.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
        }else {
            g.add(powerup, 0, 0);
            g.add(R1RS, 0, 1);
            g.add(R4, 0, 2);
            g.add(R2G, 0, 3);
            g.add(R2RS, 0, 4);
            g.add(R3G, 0, 5);
            powerup.setId("4");
            powerup.setPrefWidth(100);
            powerup.setTextAlignment(TextAlignment.CENTER);
            powerup.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            R1RS.setId("3");
            R1RS.setPrefWidth(100);
            R1RS.setTextAlignment(TextAlignment.CENTER);
            R1RS.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            R4.setId("1");
            R4.setPrefWidth(100);
            R4.setTextAlignment(TextAlignment.CENTER);
            R4.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            R2G.setId("2");
            R2G.setPrefWidth(100);
            R2G.setTextAlignment(TextAlignment.CENTER);
            R2G.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            R3G.setId("1");
            R3G.setPrefWidth(100);
            R3G.setTextAlignment(TextAlignment.CENTER);
            R3G.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
            R2RS.setId("3");
            R2RS.setPrefWidth(100);
            R2RS.setTextAlignment(TextAlignment.CENTER);
            R2RS.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
        }
    }

    public StackPane getRoot(){
        return root;
    }

    public void refreshBoard(){
        refresh = true;
        try {
            configRoot();
            MyApplication.getScene().setRoot(root);
        }catch (Exception e){

        }
        if (MyApplication.getClient().isRefreshGUI()){
            configRoot();
            MyApplication.getScene().setRoot(root);
            MyApplication.getClient().setRefreshGUI(false);
        }
        refresh = false;
    }

    public boolean isShowCard() {
        return showCard;
    }

    public void setShowCard(boolean showCard) {
        this.showCard = showCard;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void sendString(String s) {
        if (Client.getModel().getPlayer(MyApplication.getClient().getUsername()).getYourTurn()) {
            MyApplication.getClient().sendStringToServer(s);
        }
    }

    public void appendText(String t){
        try {
            instructions.clear();
            instructions.appendText(t);
        }catch (Exception e){
            //empty
        }
    }

    public void enableAllAction(){
        run.setDisable(false);
        runAndGrab.setDisable(false);
        shootButton.setDisable(false);
        if (Client.getModel().hasTerminator() && Client.getModel().getPlayer(MyApplication.getClient().getUsername()).getTerminator())
            terB.setVisible(true);
    }

    public void disableAllAction(){
        run.setDisable(true);
        runAndGrab.setDisable(true);
        shootButton.setDisable(true);
        if (Client.getModel().hasTerminator() && Client.getModel().getPlayer(MyApplication.getClient().getUsername()).getTerminator())
            terB.setVisible(false);
    }

    public void disableShoot(){
        shootButton.setDisable(true);
    }

    public void enableUsePU(){
        powerup.setDisable(false);
    }

    public void disableUsePU(){
        powerup.setDisable(true);
    }

    public void enableAmmoChoice(){
        ammoOrBlue.setText("Blue");
        ammoOrBlue.setId("blue");
        ammoOrBlue.setDisable(false);
        powerupOrRed.setText("Red");
        powerupOrRed.setId("red");
        powerupOrRed.setDisable(false);
        bothOrYellow.setText("Yellow");
        bothOrYellow.setId("yellow");
        bothOrYellow.setDisable(false);
    }

    public void enableAllPaymentChoice(){
        ammoOrBlue.setDisable(false);
        powerupOrRed.setDisable(false);
        bothOrYellow.setDisable(false);
    }

    public void disableAllPaymentChoice(){
        disablePayWithAmmo();
        disablePayWithPowerup();
        disablePayWithBoth();
    }

    public void disablePayWithAmmo(){
        ammoOrBlue.setDisable(true);
    }

    public void enablePayWithAmmo(){
        ammoOrBlue.setDisable(false);
    }

    public void disablePayWithPowerup(){
        powerupOrRed.setDisable(true);
    }

    public void enablePayWithPowerup(){
        powerupOrRed.setDisable(false);
    }

    public void disablePayWithBoth(){
        bothOrYellow.setDisable(true);
    }

    public void enablePayWithBoth(){
        bothOrYellow.setDisable(false);
    }

    public void enableReload(){
        reload.setDisable(false);
        weaponSelected = false;
    }

    public void disableReload(){
        reload.setDisable(true);
    }

    public void enableSquare(){
        s0B.setDisable(false);
        s1B.setDisable(false);
        s2B.setDisable(false);
        s3B.setDisable(false);
        s4B.setDisable(false);
        s5B.setDisable(false);
        s6B.setDisable(false);
        s7B.setDisable(false);
        s8B.setDisable(false);
        s9B.setDisable(false);
        s10B.setDisable(false);
        s11B.setDisable(false);
    }

    public void disableSquare(){
        s0B.setDisable(true);
        s1B.setDisable(true);
        s2B.setDisable(true);
        s3B.setDisable(true);
        s4B.setDisable(true);
        s5B.setDisable(true);
        s6B.setDisable(true);
        s7B.setDisable(true);
        s8B.setDisable(true);
        s9B.setDisable(true);
        s10B.setDisable(true);
        s11B.setDisable(true);
    }

    public void enableSelectPowerupHand(){
        selectPowerupHand = true;
    }

    public void disableSelectPowerupHand(){
        selectPowerupHand = false;
    }

    public void enableSelectWeaponHand(){
        selectWeaponHand = true;
    }

    public void disableSelectWeaponHand(){
        selectWeaponHand = false;
    }

    public void enableSpawn(){
        spawn = true;
    }

    public void disableSpawn(){
        spawn = false;
    }

    public void enableSelectPlayer(){
        ter.setDisable(false);
        pl1.setDisable(false);
        pl2.setDisable(false);
        pl3.setDisable(false);
        pl4.setDisable(false);
    }

    public void disableSelectPlayer(){
        ter.setDisable(true);
        pl1.setDisable(true);
        pl2.setDisable(true);
        pl3.setDisable(true);
        pl4.setDisable(true);
    }

    public void enableEnd(){
        end.setDisable(false);
    }

    public void disableEnd(){
        end.setDisable(true);
    }

    public void enableContinue(){
        cont.setDisable(false);
    }

    public void disableContinue(){
        cont.setDisable(true);
    }

    public void disableAll(){
        disableChooseEffect();
        disableAllPaymentChoice();
        disableAllAction();
        disableUsePU();
        disableSelectPowerupHand();
        disableSpawn();
        disableSelectWeaponHand();
        disableSelectPlayer();
        disableSquare();
        disableReload();
        disableEnd();
        disableContinue();
        if (Client.getModel().isFinalFrenzy()){
            disableFreneticBeforeFirst();
            disableFreneticAfterFirst();
        }
    }

    public void onlyName(){
        onlyName = true;
    }

    public void onlyColor(){
        onlyColor = true;
    }

    public void enableChooseEffect(String s){
        weaponChosen = s;
        player.getChildren().clear();
        setPlayer();
    }

    public void disableChooseEffect(){
        weaponChosen = "NONE";
        player.getChildren().clear();
        setPlayer();
    }

    public void enableFreneticBeforeFirst(){
        R1RS.setDisable(false);
        R4.setDisable(false);
        R2G.setDisable(false);
    }

    public void disableFreneticBeforeFirst(){
        R1RS.setDisable(true);
        R4.setDisable(true);
        R2G.setDisable(true);
    }

    public void enableFreneticAfterFirst(){
        R2RS.setDisable(false);
        R3G.setDisable(false);
    }

    public void disableFreneticAfterFirst(){
        R2RS.setDisable(true);
        R3G.setDisable(true);
    }

    public void showWinners(){
        BorderPane borderPane = new BorderPane();
        Scene s = new Scene(borderPane, 400, 300);
        Text gameOver = new Text("Game Over!\n Winners:");
        Text w1 = new Text();
        Text w2 = new Text();
        Text w3 = new Text();
        Text w4 = new Text();
        Text w5 = new Text();
        VBox winners = new VBox(w1, w2, w3, w4, w5);
        Button exit = new Button("Exit");
        Pane paneTop = new Pane();
        Pane paneBottom = new Pane();
        VBox top = new VBox(paneTop, gameOver);
        VBox bottom = new VBox(exit, paneBottom);

        paneTop.setPrefHeight(20);
        paneBottom.setPrefHeight(20);
        gameOver.setTextAlignment(TextAlignment.CENTER);
        borderPane.setTop(top);
        top.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(top, Pos.CENTER);
        borderPane.setCenter(winners);
        BorderPane.setAlignment(winners, Pos.CENTER);
        winners.setAlignment(Pos.CENTER);
        borderPane.setBottom(bottom);
        bottom.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(bottom, Pos.TOP_CENTER);
        exit.setTextAlignment(TextAlignment.CENTER);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        ArrayList<String> temp = Client.getModel().getWinners();
        //System.out.println("size arraylist winners " + temp.size());
        if (!temp.isEmpty())
            w1.setText(temp.get(0));
        if (temp.size() >= 2)
            w2.setText(temp.get(1));
        if (temp.size() >= 3)
            w3.setText(temp.get(2));
        if (temp.size() >= 4)
            w4.setText(temp.get(3));
        if (temp.size() >= 5)
            w4.setText(temp.get(4));
        MyApplication.getPrimaryStage().setScene(s);
        MyApplication.getPrimaryStage().setFullScreen(false);
        MyApplication.getPrimaryStage().setMaxWidth(400);
        MyApplication.getPrimaryStage().setMaxHeight(300);
    }

    private EventHandler<MouseEvent> selectWSpawn = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && spawn){
            Button b = (Button)mouseEvent.getSource();
            sendString(b.getId());
        }
    };

    private EventHandler<MouseEvent> show = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            Button b = (Button) mouseEvent.getSource();
            showCard = true;
            new ShowCard(b.getId(), this);
        }
    };

    private EventHandler<MouseEvent> selectW = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && !weaponSelected && selectWeaponHand){
            Button b = (Button)mouseEvent.getSource();
            sendString(b.getId());
            weaponChosen = b.getId();
            weaponSelected = false;
        }
    };

    private EventHandler<MouseEvent> selectPU = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && selectPowerupHand) {
            Button b = (Button) mouseEvent.getSource();
            String color;
            switch (b.getId().charAt(b.getId().length() - 1)) {
                case 'b':
                    color = "blue";
                    break;
                case 'r':
                    color = "red";
                    break;
                default:
                    color = "yellow";
            }
            if (onlyName) {
                sendString(b.getId().substring(0, b.getId().length() - 2));
                onlyName = false;
            } else if (onlyColor) {
                sendString(color);
                onlyColor = false;
            } else{
                sendString(b.getId().substring(0, b.getId().length() - 2));
                sendString(color);
            }
        }
    };

    private EventHandler<MouseEvent> sendSquare = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            Button b = (Button) mouseEvent.getSource();
            sendString(b.getId());
        }
    };

    private EventHandler<MouseEvent> action = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            Button b = (Button) mouseEvent.getSource();
            sendString(b.getId());
        }
    };

    private EventHandler<MouseEvent> endAction = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            Button b = (Button) mouseEvent.getSource();
            sendString(b.getId());
            if (weaponSelected) {
                weaponSelected = false;
                player.getChildren().clear();
                setPlayer();
            }
        }
    };

    private EventHandler<MouseEvent> continueAction = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            Button b = (Button) mouseEvent.getSource();
            sendString(b.getId());
        }
    };

    private EventHandler<MouseEvent> payChoice = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            Button b = (Button) mouseEvent.getSource();
            sendString(b.getId());
            if (b.getId().equals("blue") || b.getId().equals("red") || b.getId().equals("yellow")) {
                player.getChildren().clear();
                setPlayer();
            }
        }
    };

    private EventHandler<MouseEvent> sendPlayer = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            Button b = (Button) mouseEvent.getSource();
            sendString(b.getId());
        }
    };

    private EventHandler<MouseEvent> sendEffect = mouseEvent -> {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            Button b = (Button) mouseEvent.getSource();
            sendString(b.getId());
        }
    };
}