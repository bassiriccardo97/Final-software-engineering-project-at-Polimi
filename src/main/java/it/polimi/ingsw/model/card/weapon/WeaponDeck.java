package it.polimi.ingsw.model.card.weapon;

import it.polimi.ingsw.model.card.Deck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponDeck implements Deck, Serializable {

    private ArrayList<Weapon> weapons = new ArrayList<>();

    /**
     * Adds the specific Weapon to the bottom of the WeaponDeck
     *
     * @param w     a Weapon, the one which has to be added
     */
    public void addWeapon(Weapon w) {
        weapons.add(w);
    }

    /**
     * Getter of weapons (all the cards in the WeaponDeck)
     *
     * @return      an ArrayList of Weapon, the one which contains all the weapons
     */
    public List<Weapon> getWeapons() { return weapons; }

    /**
     * Removes the Weapon in the position specified by the index from the WeaponDeck
     *
     * @param index    an int for the position of the Weapon that has to be removed
     */
    public void removeWeapon(int index) {
        if(!weapons.isEmpty()) {
            weapons.remove(index);
        }
    }

    /**
     * Setter for a Weapon in the Deck
     *
     * @param index     an int, the index where to set the Weapon
     * @param w         the Weapon
     */
    public void setWeapon(int index, Weapon w){
        weapons.set(index, w);
    }

    /**
     * Removes the specific Weapon from the WeaponDeck
     *
     * @param weapon    a Weapon, the one which has to be removed from the WeaponDeck
     */
    public void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
    }

    /**
     * Returns from the WeaponDeck the Weapon at the position specified by the index given
     *
     * @param index     an int for the position of the Weapon
     * @return          a Weapon at the index position in the WeaponDeck
     */
    public Weapon getWeapon(int index) {
        if (index < weapons.size() && index >= 0)
            return weapons.get(index);
        return null;
    }

    /**
     * Returns the specific Weapon from the WeaponDeck
     *
     * @param weaponName    a String
     * @return              a Weapon, the one which has been looked for
     */
    public Weapon getWeapon(String weaponName) {
        for (Weapon w : weapons)
            if(w != null && w.getName().equals(weaponName))
                return w;
        return null;
    }

    /**
     * Returns the Weapon on top of the WeaponDeck and removes it from the Deck
     *
     * @return      the Weapon on top of the WeaponDeck
     */
    public Weapon getFirstWeapon() {
        if (!weapons.isEmpty()) {
            Weapon temp = weapons.get(0);
            weapons.remove(0);
            return temp;
        }
        return null;
    }

    /**
     * Returns the number of Weapons inside the WeaponDeck
     *
     * @return      an int that specifies the size of the WeaponDeck
     */
    public int getSize() {
        return weapons.size();
    }

    /**
     * Shuffles the Weapondeck
     */
    public void shuffle() {
        Collections.shuffle(weapons);
    }

}
