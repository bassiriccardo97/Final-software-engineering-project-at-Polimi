package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.ammotile.AmmoDeck;
import it.polimi.ingsw.model.card.ammotile.AmmoTile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AmmoDeckTest {

    /**
     * Adds an ammotile to the deck
     */
    @Test
    void addAmmoTile() {
        AmmoDeck ad = new AmmoDeck();

        assertTrue(ad.getSize() == 0);

        AmmoTile a1 = new AmmoTile();
        AmmoTile a2 = new AmmoTile();
        ad.addAmmoTile(a1);
        ad.addAmmoTile(a2);

        assertTrue(ad.getSize() == 2);
    }

    /**
     * Removes an ammotile from the deck
     */
    @Test
    void removeAmmoTile() {
        AmmoDeck ad = new AmmoDeck();
        AmmoTile a1 = new AmmoTile();
        AmmoTile a2 = new AmmoTile();
        AmmoTile a3 = new AmmoTile();
        ad.addAmmoTile(a1);
        ad.addAmmoTile(a2);
        ad.addAmmoTile(a3);
        ad.removeAmmoTile(0);

        assertTrue(ad.getSize() == 2);

        ad.removeAmmoTile(1);

        assertTrue(ad.getSize() == 1);

        ad.removeAmmoTile(0);

        assertTrue(ad.getSize() == 0);

    }

    /**
     * Gets the first ammotile from the deck
     */
    @Test
    void getFirstAmmo() {
        AmmoDeck ad = new AmmoDeck();
        AmmoTile a1 = new AmmoTile();
        AmmoTile a2 = new AmmoTile();
        ad.addAmmoTile(a1);
        ad.addAmmoTile(a2);

        ad.getFirstAmmo();

        assertTrue(ad.getSize() == 1);

        ad.removeAmmoTile(0);

        assertTrue(ad.getFirstAmmo() == null);
    }

    /**
     * Returns the size of the deck
     */
    @Test
    void getSize() {
        AmmoDeck ad = new AmmoDeck();

        assertTrue(ad.getSize() == 0);

        AmmoTile a1 = new AmmoTile();
        AmmoTile a2 = new AmmoTile();
        ad.addAmmoTile(a1);
        ad.addAmmoTile(a2);

        assertTrue(ad.getSize() == 2);

        ad.removeAmmoTile(0);
        ad.removeAmmoTile(0);
        ad.removeAmmoTile(0);

        assertTrue(ad.getSize() == 0);

    }

}