package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connections.Client;
import it.polimi.ingsw.model.clientmodel.PowerupClient;
import it.polimi.ingsw.model.clientmodel.WeaponClient;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ShowCard{

    private WeaponClient weaponClient;
    private BoardGUI boardGUI;
    private BorderPane root = new BorderPane();
    private HBox top = new HBox();
    private Pane p1 = new Pane();
    private VBox bottom = new VBox();
    private Text t1 = new Text();
    private Pane pane1 = new Pane();
    private Text t2 = new Text();
    private Pane pane2 = new Pane();
    private Text t3 = new Text();
    private Pane pane3 = new Pane();
    private Button back = new Button();
    private StackPane center = new StackPane();
    private Pane p2 = new Pane();

    /**
     * Constructor for the ShowCard scene
     *
     * @param name          a String, the name of the card to show
     * @param boardGUI      the instance of the BoardGUI from which the show card request comes
     */
    public ShowCard(String name, BoardGUI boardGUI){
        root.setStyle("-fx-background-color: grey");
        top.setStyle("-fx-background-color: grey");
        p1.setStyle("-fx-background-color: grey");
        bottom.setStyle("-fx-background-color: grey");
        pane1.setStyle("-fx-background-color: grey");
        pane2.setStyle("-fx-background-color: grey");
        pane3.setStyle("-fx-background-color: grey");
        center.setStyle("-fx-background-color: grey");
        p2.setStyle("-fx-background-color: grey");
        this.boardGUI = boardGUI;
        root.setMinSize(1000, 700);
        back.setPrefWidth(100);
        back.setText("Back");
        back.addEventFilter(MouseEvent.MOUSE_CLICKED, backToBoard);
        back.setTextAlignment(TextAlignment.CENTER);
        p1.setPrefSize(50, 100);
        top.setAlignment(Pos.CENTER_LEFT);
        p2.setPrefSize(1000, 100);
        top.getChildren().addAll(p1, back, p2);
        root.setTop(top);
        BorderPane.setAlignment(top, Pos.CENTER_LEFT);
        StackPane.setAlignment(back, Pos.TOP_LEFT);
        ImageView imageView = new ImageView(new Image("cards/" + name + ".png"));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(300);
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);
        root.setCenter(center);
        BorderPane.setAlignment(imageView, Pos.CENTER);
        bottom.setMaxSize(800, 300);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
        BorderPane.setAlignment(bottom, Pos.CENTER);
        weaponClient = Client.getModel().getWeaponClient(name);
        if (weaponClient != null) {
            t1.setTextAlignment(TextAlignment.CENTER);
            t1.setWrappingWidth(800);
            t2.setTextAlignment(TextAlignment.CENTER);
            t2.setWrappingWidth(800);
            t3.setTextAlignment(TextAlignment.CENTER);
            t3.setWrappingWidth(800);
            t1.setText("BASE\n" + weaponClient.getBaseDescription());
            if (weaponClient.isAltEffect()) {
                t2.setText("ALTERNATIVE\n" + weaponClient.getAltDescription());
            }else if (weaponClient.isOpt1Effect() && weaponClient.isOpt2Effect()){
                t2.setText("OPTIONAL 1\n" + weaponClient.getOpt1Description());
                t3.setText("OPTIONAL 2\n" + weaponClient.getOpt2Description());
            }else if (weaponClient.isOpt1Effect() && !weaponClient.isOpt2Effect()){
                t2.setText("OPTIONAL\n" + weaponClient.getOpt1Description());
            }
        } else {
            PowerupClient powerupClient = Client.getModel().getPowerupClient(name.substring(0, name.length() - 2), name.charAt(name.length() - 1));
            t1.setText(powerupClient.getDescription());
            t1.setWrappingWidth(800);
        }
        pane1.setPrefSize(800, 30);
        pane2.setPrefSize(800, 30);
        pane3.setPrefSize(800, 30);
        bottom.getChildren().addAll(t1, pane1, t2, pane2, t3, pane3);
        MyApplication.getScene().setRoot(root);
    }

    /**
     * EventHandler for the request of turning back to the BoardGUI scene
     */
    private EventHandler<MouseEvent> backToBoard = mouseEvent -> {
        boardGUI.setRefresh(false);
        boardGUI.setShowCard(false);
        if (boardGUI.isRefresh()) {
            boardGUI.refreshBoard();
        }else {
            MyApplication.getScene().setRoot(boardGUI.getRoot());
        }
    };
}
