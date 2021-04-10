package it.polimi.ingsw.model.game.squares;

import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.card.weapon.WeaponDeck;
import it.polimi.ingsw.model.exceptions.WrongValueException;

public class SpawnpointSquare extends GenericSquare {

    private WeaponDeck weaponSpawnpoint = new WeaponDeck();

    /**
     * Constructor of SpawnpointSquare
     *
     * @param num       an int identifying the cell
     * @param room      an int specifing the room of belonging
     * @param sp        a boolean value for the spawnpoint
     * @param n         a boolean value for the north side door
     * @param e         a boolean value for the east side door
     * @param s         a boolean value for the south side door
     * @param o         a boolean value for the west side door
     * @param color     a char for the color of the room
     */
    public SpawnpointSquare(int num, int room, boolean sp, boolean n, boolean e, boolean s, boolean o, char color) {
        super(num, room, sp, n, e, s, o, color);
    }

    /**
     * Returns the WeaponDeck in the Spawnpoint
     *
     * @return      a WeaponDeck, the one which is present in the specified Spawnpoint
     */
    public WeaponDeck getWeaponSpawnpoint() {
        return weaponSpawnpoint;
    }

    /**
     * Adds a given Weapon to the Deck of weapons that are inside of the SpawnpointSquare
     *
     * @param w Weapon to be added
     */
    public void addSpawnpointWeapon(Weapon w) {
        this.weaponSpawnpoint.addWeapon(w);
    }

    /**
     * Removes the specified Weapon from the ones inside the SpawnpointSquare
     *
     * @param w     a Weapon, the one which has to be removed
     */
    public void removeWeapon(Weapon w) {
        try {
            this.validChoice(w);
            weaponSpawnpoint.removeWeapon(w);
        } catch (WrongValueException wvE) {
            wvE.wrong();
        }
    }

    /**
     * Verifies the right choice of the Weapon in the WeaponDeck of the Spawnpoint
     *
     * @param w0                        a Weapon, the one which has to be put under investigation
     * @throws WrongValueException      an exception which notifies the wrong choice
     */
    private void validChoice(Weapon w0) throws WrongValueException {
        for(Weapon w : getWeaponSpawnpoint().getWeapons()){
            if (w==w0) {
                return;
            }
        }
        throw new WrongValueException();
    }

    public boolean hasWeaponSpawnpoint(String weaponName) {
        return this.getWeaponSpawnpoint().getWeapons().contains(this.getWeaponSpawnpoint().getWeapon(weaponName));
    }

}
