package it.polimi.ingsw.model.card.ammotile;

import it.polimi.ingsw.model.card.Deck;
import it.polimi.ingsw.model.card.ammotile.AmmoTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AmmoDeck implements Deck {

    private ArrayList<AmmoTile> ammo = new ArrayList<>();

    /**
     * Getter for the list of ammo in the Deck
     *
     * @return      an ArrayList of AmmoTile
     */
    public List<AmmoTile> getAmmo(){ return this.ammo; }

    /**
     * Adds an AmmoTile to the bottom of the Deck
     *
     * @param a     an AmmoTile, which has to be added in the Deck
     */
    public void addAmmoTile(AmmoTile a) {
        this.ammo.add(a);
    }

    /**
     * Removes the AmmoTile placed in the position specified by the argument from the Deck
     *
     * @param index     an int that specifies the index value of the AmmoTile to remove
     */
    public void removeAmmoTile(int index) {
        if (!this.ammo.isEmpty())
            this.ammo.remove(index);
    }

    /**
     * Returns and then removes the AmmoTile on top of the Deck
     *
     * @return      an AmmoTile, the one on the top of the Deck
     */
    public AmmoTile getFirstAmmo() {
        if (!this.ammo.isEmpty()) {
            AmmoTile temp = this.ammo.get(0);
            this.ammo.remove(0);
            return temp;
        }
        return null;
    }

    /**
     * Returns how many AmmoTile there are in the Deck
     *
     * @return      an int, the AmmoDeck size
     */
    public int getSize() {
        return this.ammo.size();
    }

    /**
     * Shuffles the AmmoDeck
     */
    public void shuffle() {
        Collections.shuffle(this.ammo);
    }

}
