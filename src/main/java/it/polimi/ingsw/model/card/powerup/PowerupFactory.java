package it.polimi.ingsw.model.card.powerup;

import it.polimi.ingsw.model.card.Effect;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;

import static it.polimi.ingsw.model.card.EffectManager.*;

public class PowerupFactory {

    String description;
    Effect effect;
    boolean pay1 = false;
    boolean alwaysUsable = false;
    boolean victim = true;
    boolean square = false;
    PowerupApplier applier;

    /**
     * Factory constructor for Powerups
     *
     * @param name      a String, the name of the Powerup to create
     */
    public PowerupFactory(String name){

        Effect oneMoreDamage = (p1, victim, s) -> {                         //sistemare per terminator
            checkJustDamaged(victim.get(0));
            PlayerBoard.giveNDamages(p1, victim.get(0), 1);
        };

        Effect move12 = (p1, victim, s) -> {
            if(victim.get(0) != p1) {
                checkDistanceVisibleRun(victim.get(0).getPlayerPosition(), s.get(0), 2, 1);
                checkFixedDirectionReachable(victim.get(0).getPlayerPosition(), s.get(0));
                //victim.get(0).setPlayerPosition(s.get(0), victim.get(0));
                victim.get(0).setPlayerPosition(s.get(0));
            }
            else throw new WrongPlayerException(p1);
        };

        Effect markVisible = (p1, victim, s) -> {
            checkPlayersVisibilityShoot(p1.getPlayerPosition(), victim);
            checkLastDamage(p1, victim.get(0));
            victim.get(0).getPb().addMarkedDamage(p1.getColor());
        };

        Effect teleport = (p1, victim, s) -> {
            GenericSquare.checkExistingSquare(s.get(0));
            //p1.setPlayerPosition(s.get(0), p1);
            p1.setPlayerPosition(s.get(0));
        };

        switch (name){
            case "targeting scope":
                description = "You may play this card when you are dealing damage to one or more targets. Pay 1 ammo cube of any color. Choose 1 of those targets and give it an extra point of damage. Note: You cannot use this to do 1 damage to a target that is receiving only marks.";
                effect = oneMoreDamage;
                pay1 = true;
                break;
            case "newton":
                description = "You may play this card on your turn before or after any action. Choose any other player's figure and move it 1 or 2 squares in one direction. (You can't use this to move a figure after it respawns at the end of your turn. That would be too late.)";
                effect = move12;
                alwaysUsable = true;
                square = true;
                break;
            case "tagback grenade":
                description = "You may play this card when you receive damage from a player you can see. Give that player 1 mark.";
                effect = markVisible;
                break;
            default:        //teleporter
                description = "You may play this card on your turn before or after any action. Pick up your figure and set it down on any square of the board. (You can't use this after you see where someone respawns at the end of your turn. By then it is too late.)";
                alwaysUsable = true;
                victim = false;
                square = true;
                effect = teleport;
        }

        setApplier(name);

    }

    //METHODS

    /**
     * Setter for the applier
     *
     * @param name      is the name of the Powerup
     */
    public void setApplier(String name){

        PowerupApplier teleporter = (p, players, s) -> effect.applyOn(p, null, s);

        PowerupApplier newton = (p, players, s) -> effect.applyOn(p, players, s);

        PowerupApplier tbg = (p, players, s) -> effect.applyOn(p, players, null);

        PowerupApplier ts = (p, players, s) -> effect.applyOn(p, players, null);

        switch (name){
            case "teleporter":
                applier = teleporter;
                break;
            case "newton":
                applier = newton;
                break;
            case "tagback grenade":
                applier = tbg;
                break;
            default: //targeting scope
                applier = ts;
        }
    }

    /**
     * Checks if a specific player has been just damaged
     *
     * @param p                         a player
     * @throws WrongPlayerException     if the player has not been just damaged
     */
    private void checkJustDamaged(Player p) throws WrongPlayerException {
        if (!p.getJustDamaged())
            throw new WrongPlayerException(p);
    }

    /**
     * Checks if the last damage of the first player has been done by the second one
     *
     * @param p1                        a player
     * @param p2                        a player
     * @throws WrongPlayerException     if p1's last damage is not of p2's color
     */
    private void checkLastDamage(Player p1, Player p2) throws WrongPlayerException {
        if (p1.getPb().getLastDamage() != p2.getColor())
            throw new WrongPlayerException(p2);
    }

    //GETTER

    /**
     * Getter for the powerup's description
     *
     * @return      a String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the Powerup's effect
     *
     * @return      an Effect
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Getter for pay1, that indicates whether the Powerup's effect needs the payment of an ammo or not
     *
     * @return      a boolean
     */
    public boolean isPay1() {
        return pay1;
    }

    /**
     * Getter for the effect's applier
     *
     * @return      a PowerupApplier
     */
    public PowerupApplier getApplier() {
        return applier;
    }

    /**
     * Getter for alwaysUsable, that indicates whether the Powerup is always usable or not
     *
     * @return      a boolean
     */
    public boolean isAlwaysUsable() {
        return alwaysUsable;
    }

    /**
     * Getter for victim that, indicates whether the Powerup's effect needs a victim or not
     *
     * @return      a boolean
     */
    public boolean isVictim() {
        return victim;
    }

    /**
     * Getter for sqaure, that indicates whether the Powerup's effect needs a square or not
     *
     * @return      a boolean
     */
    public boolean isSquare() {
        return square;
    }
}
