package it.polimi.ingsw.model.game.squares;

import it.polimi.ingsw.model.card.ammotile.AmmoTile;

public class Square extends GenericSquare {

    private AmmoTile ammo = new AmmoTile();

    /**
     * This constructor sets the number identifying the Square, the room to which it belongs,
     * the presence of a door for each side and the color of the room.
     *
     * @param num       int identifying the cell
     * @param room      int specifing the room of belonging
     * @param sp        Boolean value for the spawnpoint
     * @param n         Boolean value for the north side door
     * @param e         Boolean value for the east side door
     * @param s         Boolean value for the south side door
     * @param o         Boolean value for the west side door
     * @param color     char for the color of the room
     */
    public Square(int num, int room, boolean sp, boolean n, boolean e, boolean s, boolean o, char color) {
        super(num, room, sp, n, e, s, o, color);
    }

    /**
     * This method sets an AmmoTile in the specified Square
     *
     * @param a     an AmmoTile object, which is the one present on the Square
     */
    public void setAmmo(AmmoTile a){
        this.ammo=a;
    }

    /**
     * This method returns the AmmoTile present on the specified Square
     *
     * @return      an AmmoTile object, which is the one present on the Square
     */
    public AmmoTile getAmmo() {
        return ammo;
    }

}
