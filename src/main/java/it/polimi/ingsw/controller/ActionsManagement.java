package it.polimi.ingsw.controller;

import it.polimi.ingsw.connections.Server;
import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.exceptions.WrongPlayerException;
import it.polimi.ingsw.model.exceptions.WrongSquareException;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.game.squares.SpawnpointSquare;
import it.polimi.ingsw.model.game.squares.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.player.Terminator;
import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.controller.GameController.tryParse;

public class ActionsManagement extends Server {

    private static final String CHOICESTRING = "Please, type your choice";
    private static final String ERRORSTRING = "Wrong choice, please try again";
    private static final String BASEADDED = "Effects reordered - Base effect added";
    private static final String BASEADDEDLAST = "Base effect added as the last one";
    private static final String REORDERED = "Effects reordered";
    private static final String END = "end";
    private static final String ENABLE_SQUARE = "ENABLE_SQUARE";
    private static final String DISABLE_SQUARE = "DISABLE_SQUARE";
    private static final String ENABLE_SELECT_PLAYER = "ENABLE_SELECT_PLAYER";
    private static final String DISABLE_SELECT_PLAYER = "DISABLE_SELECT_PLAYER";
    private static final String ENABLE_SELECT_WEAPON_HAND = "ENABLE_SELECT_WEAPON_HAND";
    private static final String DISABLE_SELECT_WEAPON_HAND = "DISABLE_SELECT_WEAPON_HAND";
    private static final String ENABLE_SPAWN = "ENABLE_SPAWN";
    private static final String DISABLE_SPAWN = "DISABLE_SPAWN";
    private static final String ENABLE_END = "ENABLE_END";
    private static final String DISABLE_END = "DISABLE_END";
    private static final String DISABLE_ALL = "DISABLE_ALL";
    private static final String ENABLE_CONTINUE = "ENABLE_CONTINUE";
    private static final String DISABLE_CONTINUE = "DISABLE_CONTINUE";
    private static final String ENABLE_CHOOSE_EFFECT = "ENABLE_CHOOSE_EFFECT";
    private static final String DISABLE_CHOOSE_EFFECT = "DISABLE_CHOOSE_EFFECT";
    private CardsManagement cm = new CardsManagement();
    private PaymentManagement pm = new PaymentManagement();

    //Shoot management

    /**
     * Manages the shoot of a RealPlayer
     *
     * @param p                     a RealPlayer, the one which wants to shoot
     * @param actionFigures         an ArrayList of Player, which contains all the Player in game
     * @return                      an int, which indicates if the shoot was successful or not
     */
    public int shootManagement(RealPlayer p, List<Player> actionFigures) {
        ArrayList<Player> victims = new ArrayList<>();
        ArrayList<GenericSquare> s = new ArrayList<>();
        String[] effects = new String[3];
        String weaponName;
        int choice = 1;
        if (getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_ALL, false);
        while (true) {
            while (true) {
                try {
                    weaponName = chooseWeapon(p, effects);
                    break;
                } catch (WrongValueException wVE) {
                    messageTo(p.getColor(), "Shoot goes wrong!", false);
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    return 0;
                }
            }
            victims.clear();
            if (getClientByColor(p.getColor()).isGuiInterface()) {
                messageTo(p.getColor(), ENABLE_SELECT_PLAYER, false);
                messageTo(p.getColor(), ENABLE_END, false);
            }
            victimsChoice(p, actionFigures, victims, weaponName, effects);
            if (getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), DISABLE_SELECT_PLAYER, false);
            s.clear();
            if (getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), ENABLE_SQUARE, false);
            squaresChoice(p, s, weaponName, effects);
            if (getClientByColor(p.getColor()).isGuiInterface()) {
                messageTo(p.getColor(), DISABLE_SQUARE, false);
                messageTo(p.getColor(), DISABLE_END, false);
            }
            try {
                p.getPh().getWeaponDeck().getWeapon(weaponName).doEffect(effects[0], effects[1], effects[2], p, victims, s);
                messageTo(p.getColor(), "Shoot completed", false);
                pm.paymentEffectsCost(p, weaponName, effects);
                messageTo(p.getColor(), "Payment completed", false);
                p.getPh().getWeaponDeck().getWeapon(weaponName).setLoaded(false);
                if (!victims.isEmpty()) {
                    for (int i = 0; i < victims.size(); i++) {
                        if (!victims.get(i).getJustDamaged()) {
                            victims.remove(i);
                            i--;
                        }
                    }
                }
                if (!s.isEmpty()) {
                    for (GenericSquare sq : s) {
                        for (Player pl : sq.getPlayers()) {
                            if (pl.getJustDamaged())
                                victims.add(pl);
                        }
                    }
                } else {
                    for (Player pl : p.getPlayerPosition().getPlayers()) {
                        if (pl.getJustDamaged())
                            victims.add(pl);
                    }
                }
                if (!s.isEmpty()){
                    for (Square sq : Board.getSquares()) {
                        if (sq.getRoom() == s.get(0).getRoom()) {
                            for (Player pl : sq.getPlayers())
                                if (pl.getJustDamaged())
                                    victims.add(pl);
                        }
                    }
                    for (SpawnpointSquare spsq : Board.getSpawnpointSquares()) {
                        if (spsq.getRoom() == s.get(0).getRoom()) {
                            for (Player pl : spsq.getPlayers())
                                if (pl.getJustDamaged())
                                    victims.add(pl);
                        }
                    }
                }
                if(victims.size()>1)
                    removeDoubleVictims(victims);
                p.setTurn(false);
                sendModel();
                ArrayList<RealPlayer> temp = new ArrayList<>();
                for (Player player : victims) {
                    if (player.getColor() != Server.getTerminatorColor() && player.getJustDamaged()) {
                        RealPlayer pl = (RealPlayer) player;
                        temp.add(pl);
                    }
                }
                cm.revengePowerup(p, temp);
                p.setTurn(true);
                sendModel();
                cm.pay1Powerup(p, victims);
                break;
            } catch (WrongValueException wVE) {
                messageTo(p.getColor(), "Error, something was wrong", false);
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
                choice = errorManagement(p);
            } catch (WrongSquareException wSE) {
                messageTo(p.getColor(), "Error, you can't reach the square number " + wSE.getError().getNum(), false);
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
                choice = errorManagement(p);
            } catch (WrongPlayerException wPE) {
                messageTo(p.getColor(), "Error, you were wrong choosing the " + cm.identifyColor(wPE.getError().getColor()) + " target", false);
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
                choice = errorManagement(p);
            }
            if (choice == 0)
                break;
        }
        if (choice == 0) {
            p.getPh().getWeaponDeck().getWeapon(weaponName).setLoaded(true);
            messageTo(p.getColor(), "Shoot goes wrong!", false);
            return 0;
        } else {
            sendModelRMX();
            for (Player pl : victims) {
                if (pl.getJustDamaged())
                    pl.setJustDamaged(false);
            }
            return 1;
        }
    }

    /**
     * Asks the player which weapon he wants to use to shoot
     *
     * @param p                         a RealPlayer, the one which want to shoot
     * @param effects                   an array of String, which will contains all the chosen effects
     * @return                          a String, which is the name of the chosen weapon
     * @throws WrongValueException      if the add of the effects goes wrong (not enough resources to pay for them)
     */
    public String chooseWeapon(RealPlayer p, String[] effects) throws WrongValueException {
        //Weapon choice
        if (getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_ALL, false);
            messageTo(p.getColor(), ENABLE_SELECT_WEAPON_HAND, false);
        }
        String weaponName;
        int count = 0;
        int index = 0;
        for(int i = 0; i < p.getPh().getWeaponDeck().getSize(); i++) {
            if (p.getPh().getWeaponDeck().getWeapons().get(i).getLoaded()){
                index = i;
                count++;
            }
        }
        if (p.getPh().getWeaponDeck().getSize() == 1 || count == 1) {
            weaponName = p.getPh().getWeaponDeck().getWeapons().get(index).getName();
            messageTo(p.getColor(), weaponName + " detected", false);
        } else {
            weaponName = messageTo(p.getColor(), "Choose the weapon you want to use to shoot", true).toLowerCase();
            while (!p.getPh().hasWeapon(weaponName))
                weaponName = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
        }

        //Effect choice
        Weapon w = p.getPh().getWeaponDeck().getWeapon(weaponName);
        if (!w.isOpt1Effect() && !w.isOpt2Effect() && !w.isAltEffect()) {
            if (getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), DISABLE_SELECT_WEAPON_HAND, false);
            effects[0] = "base";
            messageTo(p.getColor(), "Effect detected", false);
        } else {
            if (getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), DISABLE_SELECT_WEAPON_HAND, false);
            while (true) {
                try {
                    messageTo(p.getColor(), "Choose effects in the order you like; stop add pressing End", false);
                    if (!getClientByColor(p.getColor()).isGuiInterface()) {
                        messageTo(p.getColor(), "   - Type 1 to select the base effect", false);
                        if (p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt1Effect())
                            messageTo(p.getColor(), "   - Type 2 to select the first optional effect", false);
                        if (p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt2Effect())
                            messageTo(p.getColor(), "   - Type 3 to select the second optional effect", false);
                        if (p.getPh().getWeaponDeck().getWeapon(weaponName).isAltEffect())
                            messageTo(p.getColor(), "   - Type 4 to select the alternative mode", false);
                        if (p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt1Effect())
                            messageTo(p.getColor(), "If there are optional effects, stop adding them typing 'end'; otherwise the process is automatic", false);
                    }
                    addEffects(p, effects, weaponName);
                    break;
                } catch (WrongValueException wVE) {
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return null;
                    throw new WrongValueException();
                }
            }
        }
        return weaponName;
    }

    /**
     * Asks the player which effects he wants to use
     *
     * @param p                         a RealPlayer, the one which wants to shoot
     * @param effects                   an array of String, the one in which are added the chosen effect
     * @param weaponName                a String, the name of the chosen weapon
     * @throws WrongValueException      if the add of the effects goes wrong (not enough resources to pay for them)
     */
    public void addEffects(RealPlayer p, String[] effects, String weaponName) throws WrongValueException {
        boolean end = false;
        int count = 0;
        int[] choices = new int[3];

        //check on not-possible effects (only base/base-opt1 effects)
        if (getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), ENABLE_CHOOSE_EFFECT + " " + weaponName, false);
        }
        while (count < 3 && !end) {
            if(p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt1Effect() && !p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt2Effect() && count == 2)
                break;
            if (count == 1 && getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), ENABLE_END, false);
            int choice = validationEffectChoice(p, weaponName);
            if (choice == 0 && count == 0)
                messageTo(p.getColor(), ERRORSTRING, false);
            else {
                end = baseAltEffectsCheck(p, choices, weaponName, choice);      //returns true if alt || base effects of an "alt weapon"
                if (!end) {                                             //if end == false -> enter in if statement
                    end = doubleEffects(p, choices, choice, count);                    //returns false if wrong choice (double effects)
                    if (!end) {
                        count--;
                    } else {
                        choices[count] = choice;
                        end = false;
                        if (choices[count] == 0)
                            break;
                        count++;
                    }
                } else {
                    break;
                }
            }
        }
        if (getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_CHOOSE_EFFECT, false);
            messageTo(p.getColor(), DISABLE_END, false);
        }
        setChoices(choices, effects);
        reorderEffects(p, effects, weaponName);
        try{
            askingPlayer(p, effects, weaponName);
        }catch (WrongValueException wVE){
            if(getClientByColor(p.getColor()).isTurnOver())
                return;
            throw new WrongValueException();
        }
    }

    /**
     * Adds the specific methods according to the number in choices (1 for base, 2 for opt1, 3 for opt2, 4 for alt)
     *
     * @param choices               an array of int, which contains all the choices of the player
     * @param effects               an array of String, which contains all the effects
     */
    public void setChoices(int[] choices, String[] effects) {
        for (int i = 0; i < choices.length; i++) {
            if (choices[i] == 1)
                effects[i] = "base";
            else if (choices[i] == 2)
                effects[i] = "opt1";
            else if (choices[i] == 3)
                effects[i] = "opt2";
            else if (choices[i] == 4)
                effects[i] = "alt";
            else {
                effects[i] = null;
                break;
            }
        }
        if (effects[1] == null)
            effects[2] = null;
    }

    /**
     * Asks the player about the payment of each effect, calling the specific method
     *
     * @param p                         a RealPlayer, the one which wants to shoot
     * @param effects                   an array of String, which contains all the effects
     * @param weaponName                a String, the name of the weapon
     * @throws WrongValueException      if the add of the effects goes wrong (not enough resources to pay for them)
     */
    public void askingPlayer(RealPlayer p, String[] effects, String weaponName) throws WrongValueException {
        for (int i = 0; i < effects.length && effects[i] != null; i++) {
            switch (effects[i]) {
                case "opt1":
                    messageTo(p.getColor(), "First optional effect payment:" ,false);
                    char[] cost = new char[1];
                    if (p.getPh().getWeaponDeck().getWeapon(weaponName).getCostOpt()[0] != '\u0000') {
                        cost[0] = p.getPh().getWeaponDeck().getWeapon(weaponName).getCostOpt()[0];
                        if (pm.paymentChecks(p, cost)[0] == 4) {
                            messageTo(p.getColor(), "You can't use the first optional effect because you don't have enough resources", false);
                            throw new WrongValueException();
                        }
                    }
                    break;
                case "opt2":
                    messageTo(p.getColor(), "Second optional effect payment:" ,false);
                    cost = new char[1];
                    if (p.getPh().getWeaponDeck().getWeapon(weaponName).getCostOpt()[1] != '\u0000') {
                        cost[0] = p.getPh().getWeaponDeck().getWeapon(weaponName).getCostOpt()[1];
                        if (pm.paymentChecks(p, cost)[0] == 4) {
                            messageTo(p.getColor(), "You can't use the second optional effect because you don't have enough resources", false);
                            throw new WrongValueException();
                        }
                    }
                    break;
                case "alt":
                    cost = new char[2];
                    messageTo(p.getColor(), "Alternative effect payment:" ,false);
                    if (p.getPh().getWeaponDeck().getWeapon(weaponName).getCostAlt()[0] != '\u0000') {
                        cost = p.getPh().getWeaponDeck().getWeapon(weaponName).getCostAlt();
                        if (pm.paymentChecks(p, cost)[0] == 4) {
                            messageTo(p.getColor(), "You can't use the alternative effect because you don't have enough resources", false);
                            throw new WrongValueException();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Reorders the effects according to the weapon
     *
     * @param p                 a RealPlayer, the one which wants to shoot
     * @param effects           an array of String, which contains all the chosen effects
     * @param weaponName        a String, the weapon name
     */
    public void reorderEffects(RealPlayer p, String[] effects, String weaponName) {

        //Adding base effect if player chooses an optional effect without the base one
        Weapon w = p.getPh().getWeaponDeck().getWeapon(weaponName);
        switch (w.getParticularWeapon()) {
            case 1:
                if (effects[0] != null && effects[1] != null && effects[2] == null) { //2 effects
                    if (effects[0].equals("opt1") && effects[1].equals("opt2")) {
                        effects[1] = "base";
                        effects[2] = "opt2";
                        messageTo(p.getColor(), BASEADDED, false);
                    } else if (effects[0].equals("opt2") && effects[1].equals("opt1")) {
                        effects[0] = "base";
                        effects[1] = "opt2";
                        effects[2] = "opt1";
                        messageTo(p.getColor(), BASEADDED, false);
                    }
                } else {
                    if (effects[0] != null && effects[1] == null) { //1 effect
                        if (effects[0].equals("opt1")) {
                            effects[1] = "base";
                            effects[2] = null;
                            messageTo(p.getColor(), BASEADDED, false);
                        } else if (effects[0].equals("opt2")) {
                            effects[0] = "base";
                            effects[1] = "opt2";
                            effects[2] = null;
                            messageTo(p.getColor(), BASEADDED, false);
                        }
                    } else { //3 effects
                        if (effectsSpecialManagement(effects))
                            messageTo(p.getColor(), REORDERED, false);
                    }
                }
                break;

            case 2:
                if (effects[0] != null && effects[0].equals("opt1") && effects[1] == null) {
                    effects[0] = "opt1";
                    effects[1] = "base";
                    messageTo(p.getColor(), BASEADDEDLAST, false);
                }
                break;

            case 3:
                if (effects[0] != null && effects[1] == null) {
                    if (effects[0].equals("opt1")) {
                        effects[0] = "base";
                        effects[1] = "opt1";
                    } else if (effects[0].equals("opt2")) {
                        effects[0] = "base";
                        effects[1] = "opt1";
                        effects[2] = "opt2";
                        messageTo(p.getColor(), BASEADDEDLAST, false);
                        messageTo(p.getColor(), REORDERED, false);
                    }
                } else if (effects[0] != null && effects[1] != null && effects[2] == null) {
                    if ((effects[0].equals("opt1") && effects[1].equals("base"))) {
                        effects[0] = "base";
                        effects[1] = "opt1";
                        messageTo(p.getColor(), REORDERED, false);
                    } else if ((effects[0].equals("opt2") && effects[1].equals("base")) || (effects[0].equals("opt2") && effects[1].equals("opt1")) || (effects[0].equals("base") && effects[1].equals("opt2")) || (effects[0].equals("opt1") && effects[1].equals("opt2"))) {
                        effects[0] = "base";
                        effects[1] = "opt1";
                        effects[2] = "opt2";
                        messageTo(p.getColor(), "Effects fixed", false);
                    }
                } else if (effects[0] != null && effects[1] != null && effects[2] != null) {
                    if ((effects[0].equals("base") && effects[1].equals("opt2")) || (effects[0].equals("opt1") || (effects[0].equals("opt2")))) {
                        effects[0] = "base";
                        effects[1] = "opt1";
                        effects[2] = "opt2";
                        messageTo(p.getColor(), "Effects fixed", false);
                    }
                }

            default: //all the other weapons
                if (effects[0] != null && effects[1] == null) { //1 effect
                    if (effects[0].equals("opt1") || effects[0].equals("opt2")) {
                        String temp = effects[0];
                        effects[0] = "base";
                        effects[1] = temp;
                        messageTo(p.getColor(), BASEADDED, false);
                    }
                } else {
                    if (effects[0] != null && effects[1] != null && effects[2] == null) {//2 effects
                        if (effects[0].equals("opt1") && effects[1].equals("opt2") || (effects[0].equals("opt2") && effects[1].equals("opt1"))) {
                            String temp = effects[0];
                            String temp1 = effects[1];
                            effects[0] = "base";
                            effects[1] = temp;
                            effects[2] = temp1;
                            messageTo(p.getColor(), BASEADDED, false);
                        }
                    } else { //3 effects
                        if (effectsManagement(effects))
                            messageTo(p.getColor(), REORDERED, false);
                    }

                }
                break;
        }

        if (p.getPh().getWeaponDeck().getWeapon(weaponName).isAltEffect() || (!p.getPh().getWeaponDeck().getWeapon(weaponName).isAltEffect() && !p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt1Effect() && !p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt2Effect())) {
            effects[1] = null;
            effects[2] = null;
        }
    }

    /**
     * Reorders the effects according to the weapon (a particular one, specified with the attribute of the weapon), calling the specific methods
     *
     * @param effects               an array of String, which contains all the chosen effect
     * @return                      a boolean, which notifies if the effects have been reordered or not
     */
    public boolean effectsSpecialManagement(String[] effects) { //all the 3 effects are != null, but they are put in the wrong order -> base before opt2
        if (effects[0].equals("opt1") && effects[1].equals("opt2") && effects[2].equals("base")) {
            effects[1] = "base";
            effects[2] = "opt2";
            return true;
        } else if (effects[0].equals("opt2")) {
            effects[0] = "base";
            effects[1] = "opt2";
            effects[2] = "opt1";
            return true;
        }
        return false;
    }

    /**
     * Reorders the effects according to the weapon
     *
     * @param effects               an array of String, which contains all the chosen effect
     * @return                      a boolean, which notifies if the effects have been reordered or not
     */
    public boolean effectsManagement(String[] effects) { //all the 3 effects are != null, but they are put in the wrong order -> base before the others
        if (effects[1].equals("base")) {
            String temp = effects[0];
            String temp1 = effects[2];
            effects[0] = "base";
            effects[1] = temp;
            effects[2] = temp1;
            return true;
        } else {
            if (effects[2].equals("base")) {
                String temp = effects[0];
                String temp1 = effects[1];
                effects[0] = "base";
                effects[1] = temp;
                effects[2] = temp1;
                return true;
            }
        }
        return false;
    }

    /**
     * Manages the player choices of the effects
     *
     * @param p                     a RealPlayer, the one which wants to shoot
     * @param weaponName            a String, the name of the weapon
     * @return                      an int, which indicates the choice (or 0 if the choice went wrong)
     */
    public int validationEffectChoice(RealPlayer p, String weaponName) {
        boolean close = false;
        String choice;
        while (true) {
            try {
                choice = messageTo(p.getColor(), CHOICESTRING, true);
                if (choice.equals("end") || tryParse(choice) == 1 || tryParse(choice) == 2 || tryParse(choice) == 3 || tryParse(choice) == 4)
                    break;
            } catch (WrongValueException wVE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
            }
        }
        while (!close) {
            try {
                if (!choice.equals(choice)) {
                    if ((tryParse(choice) == 1 || (tryParse(choice) == 2 && p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt1Effect()) || (tryParse(choice) == 3 && p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt2Effect()) || (tryParse(choice) == 4 && p.getPh().getWeaponDeck().getWeapon(weaponName).isAltEffect()) || choice.equals("end")))
                        close = true;
                    else {
                        while (true) {
                            choice = messageTo(p.getColor(), ERRORSTRING, true);
                            break;
                        }
                    }
                } else
                    break;
            } catch (WrongValueException wVE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
            }
        }
        if (!choice.equals("end")) {
            try {
                return tryParse(choice);
            } catch (WrongValueException wVE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
            }
        }
        return 0;
    }

    /**
     * Checks if there are two equals chosen effects
     *
     * @param p                         a RealPlayer, the one which wants to shoot
     * @param choices                   an array of int, which contains all the choices
     * @param choice                    an int, which indicates the current choice which is "under investigation"
     * @param cont                      an int, which represents the index at which the choice has to be added if there is not another equals
     * @return                          a boolean, which notifies it the choice has been added or not
     */
    public boolean doubleEffects(RealPlayer p, int[] choices, int choice, int cont) {
        for (int i = 0; i < choices.length - 1; i++) {          //if a player chooses the same effect twice, it is necessary to add another effect (wrong choice)
            for (int j = i + 1; j < choices.length; j++) {
                if (choices[i] == choices[j] && choices[i] != 0) {
                    messageTo(p.getColor(), "You have already chosen this effect", false);
                    return false;
                }
            }
        }
        choices[cont] = choice;
        return true;
    }

    /**
     * Checks if there has already added a base or an alternative effect of a weapon which has only these two effects
     *
     * @param p                         a RealPlayer, the one which wants to shoot
     * @param choices                   an array of int, which contains all the choices
     * @param weaponName                a String, the name of the weapon
     * @param choice                    an int, which is the current "under investigation" choice
     * @return                          a boolean, which notifies it the choice has been added or not
     */
    public boolean baseAltEffectsCheck(RealPlayer p, int[] choices, String weaponName, int choice) {
        if ((choice == 4 || choice == 1) && !p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt1Effect() && !p.getPh().getWeaponDeck().getWeapon(weaponName).isOpt2Effect()) { //if the player chooses an alt effect, it is the only one permitted
            choices[0] = choice;
            choices[1] = 0;
            choices[2] = 0;
            return true;
        }
        return false;
    }

    /**
     * Manages the choice of the shoot victims
     *
     * @param p                         a RealPlayer, the one which wants to shoot
     * @param actionFigures             a List of Player, which contains all the Player in game
     * @param victims                   an ArrayList of Player, in which are added all the chosen victims
     * @param weaponName                a String, the name of the weapon
     * @param effects                   an array of String, which contains all the effects
     */
    public void victimsChoice(RealPlayer p, List<Player> actionFigures, ArrayList<Player> victims, String weaponName, String[] effects) {
        Weapon w = p.getPh().getWeaponDeck().getWeapon(weaponName);
        String victimColor;
        for (int i = 0; i < effects.length && effects[i] != null; i++) {
            switch (effects[i]) {
                case "base":
                    boolean close = false;
                    if (w.getnTargetsBase() != 0) {
                        while (!close) {
                            if (!getClientByColor(p.getColor()).isGuiInterface())
                                victimColor = messageTo(p.getColor(), "Choose victim colors for the base effect; otherwise type 'end'", true).toLowerCase();
                            else
                                victimColor = messageTo(p.getColor(), "Select victim for the base effect; stop add pressing End", true).toLowerCase();
                            targetsManagement(p, victimColor, actionFigures, victims);
                            if (!victimColor.equals(END))
                                close = true;
                        }
                    }
                    for (int k = 1; k < w.getnTargetsBase(); k++) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            victimColor = messageTo(p.getColor(), "Choose victim colors for the base effect; otherwise type 'end'", true).toLowerCase();
                        else
                            victimColor = messageTo(p.getColor(), "Select victim for the base effect; stop add pressing End", true).toLowerCase();
                        targetsManagement(p, victimColor, actionFigures, victims);
                        if (victimColor.equals(END))
                            break;
                    }
                    break;
                case "opt1":
                    close = false;
                    if (w.getnTargetsOpt1() != 0) {
                        while (!close) {
                            if (!getClientByColor(p.getColor()).isGuiInterface())
                                victimColor = messageTo(p.getColor(), "Choose victim colors for the first optional effect; otherwise type 'end'", true).toLowerCase();
                            else
                                victimColor = messageTo(p.getColor(), "Select victim for the first optional effect; stop add pressing End", true).toLowerCase();
                            targetsManagement(p, victimColor, actionFigures, victims);
                            if (!victimColor.equals(END))
                                close = true;
                        }
                    }
                    for (int k = 1; k < w.getnTargetsOpt1(); k++) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            victimColor = messageTo(p.getColor(), "Choose victim colors for the first optional effect; otherwise type 'end'", true).toLowerCase();
                        else
                            victimColor = messageTo(p.getColor(), "Select victim for the first optional effect; stop add pressing End", true).toLowerCase();
                        targetsManagement(p, victimColor, actionFigures, victims);
                        if (victimColor.equals(END))
                            break;
                    }
                    break;
                case "opt2":
                    close = false;
                    if (w.getnTargetsOpt2() != 0) {
                        while (!close) {
                            if (!getClientByColor(p.getColor()).isGuiInterface())
                                victimColor = messageTo(p.getColor(), "Choose victim colors for the second optional effect; otherwise type 'end'", true).toLowerCase();
                            else
                                victimColor = messageTo(p.getColor(), "Select victim for the second optional effect; stop add pressing End", true).toLowerCase();
                            targetsManagement(p, victimColor, actionFigures, victims);
                            if (!victimColor.equals(END))
                                close = true;
                        }
                    }
                    for (int k = 1; k < w.getnTargetsOpt2(); k++) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            victimColor = messageTo(p.getColor(), "Choose victim colors for the second optional effect; otherwise type 'end'", true).toLowerCase();
                        else
                            victimColor = messageTo(p.getColor(), "Select victim for the second optional effect; stop add pressing End", true).toLowerCase();
                        targetsManagement(p, victimColor, actionFigures, victims);
                        if (victimColor.equals(END))
                            break;
                    }
                    break;
                case "alt":
                    close = false;
                    if (w.getnTargetsAlt() != 0) {
                        while (!close) {
                            if (!getClientByColor(p.getColor()).isGuiInterface())
                                victimColor = messageTo(p.getColor(), "Choose victim colors for the alternative effect; otherwise type 'end'", true).toLowerCase();
                            else
                                victimColor = messageTo(p.getColor(), "Select victim for the alternative effect; stop add pressing End", true).toLowerCase();
                            targetsManagement(p, victimColor, actionFigures, victims);
                            if (!victimColor.equals(END))
                                close = true;
                        }
                    }
                    for (int k = 1; k < w.getnTargetsAlt(); k++) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            victimColor = messageTo(p.getColor(), "Choose victim colors for the alternative effect; otherwise type 'end'", true).toLowerCase();
                        else
                            victimColor = messageTo(p.getColor(), "Select victim for the alternative effect; stop add pressing End", true).toLowerCase();
                        targetsManagement(p, victimColor, actionFigures, victims);
                        if (victimColor.equals(END))
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Manages the choice of the shoot squares
     *
     * @param p                         a RealPlayer, the one which wants to shoot
     * @param s                         an ArrayList of GenericSquare, in which are added all the chosen squares
     * @param weaponName                a String, the name of the weapon
     * @param effects                   an array of String, which contains all the effects
     */
    public void squaresChoice(RealPlayer p, ArrayList<GenericSquare> s, String weaponName, String[] effects) {
        Weapon w = p.getPh().getWeaponDeck().getWeapon(weaponName);
        String squareNum;
        if(w.isNotNecessarySquare() && getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), ENABLE_END, false);
        for (int i = 0; i < effects.length && effects[i] != null; i++) {
            switch (effects[i]) {
                case "base":
                    boolean close = false;
                    if (w.getnSquaresBase() != 0) {
                        while (!close) {
                            if (!getClientByColor(p.getColor()).isGuiInterface())
                                squareNum = messageTo(p.getColor(), "Choose the squares numbers for the base effect; otherwise type 'end'", true).toLowerCase();
                            else
                                squareNum = messageTo(p.getColor(), "Select the squares for the base effect; stop add pressing End", true).toLowerCase();
                            try{
                                squaresManagement(p, squareNum, s);
                                if (!squareNum.equals(END))
                                    close = true;
                                else if (w.isNotNecessarySquare()) {
                                    close = true;
                                }
                            }catch (WrongValueException e){
                                messageTo(p.getColor(), ERRORSTRING, false);
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                    }
                    for (int k = 1; k < w.getnSquaresBase(); k++) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            squareNum = messageTo(p.getColor(), "Choose the squares numbers for the base effect; otherwise type 'end'", true).toLowerCase();
                        else
                            squareNum = messageTo(p.getColor(), "Select the squares for the base effect; stop add pressing End", true).toLowerCase();
                        while(true){
                            try{
                                squaresManagement(p, squareNum, s);
                                if (squareNum.equals(END))
                                    break;
                            }catch (WrongValueException e){
                                messageTo(p.getColor(), ERRORSTRING, false);
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                        break;
                    }
                    break;

                case "opt1":
                    close = false;
                    if (w.getnSquaresOpt1() != 0) {
                        while (!close) {
                            if (!getClientByColor(p.getColor()).isGuiInterface())
                                squareNum = messageTo(p.getColor(), "Choose the squares numbers for the first optional effect; otherwise type 'end'", true).toLowerCase();
                            else
                                squareNum = messageTo(p.getColor(), "Select the squares for the first optional effect; stop add pressing End", true).toLowerCase();
                            try{
                                squaresManagement(p, squareNum, s);
                                if (!squareNum.equals(END))
                                    close = true;
                            }catch (WrongValueException e){
                                messageTo(p.getColor(), ERRORSTRING, false);
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                    }
                    for (int k = 1; k < w.getnSquaresOpt1(); k++) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            squareNum = messageTo(p.getColor(), "Choose the squares numbers for the first optional effect; otherwise type 'end'", true).toLowerCase();
                        else
                            squareNum = messageTo(p.getColor(), "Select the squares for the first optional effect; stop add pressing End", true).toLowerCase();
                        while(true){
                            try{
                                squaresManagement(p, squareNum, s);
                                if (squareNum.equals(END))
                                    break;
                            }catch (WrongValueException e){
                                messageTo(p.getColor(), ERRORSTRING, false);
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                        break;
                    }
                    break;
                case "opt2":
                    close = false;
                    if (w.getnSquaresOpt2() != 0) {
                        while (!close) {
                            if (!getClientByColor(p.getColor()).isGuiInterface())
                                squareNum = messageTo(p.getColor(), "Choose the squares numbers for the second optional effect; otherwise type 'end'", true).toLowerCase();
                            else
                                squareNum = messageTo(p.getColor(), "Select the squares for the second optional effect; stop add pressing End", true).toLowerCase();
                            try{
                                squaresManagement(p, squareNum, s);
                                if (!squareNum.equals(END))
                                    close = true;
                            }catch (WrongValueException e){
                                messageTo(p.getColor(), ERRORSTRING, false);
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                    }
                    for (int k = 1; k < w.getnSquaresOpt2(); k++) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            squareNum = messageTo(p.getColor(), "Choose the squares numbers for the second optional effect; otherwise type 'end'", true).toLowerCase();
                        else
                            squareNum = messageTo(p.getColor(), "Select the squares for the second optional effect; stop add pressing End", true).toLowerCase();
                        while(true){
                            try{
                                squaresManagement(p, squareNum, s);
                                if (squareNum.equals(END))
                                    break;
                            }catch (WrongValueException e){
                                messageTo(p.getColor(), ERRORSTRING, false);
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                        break;
                    }
                    break;
                case "alt":
                    close = false;
                    if (w.getnSquaresAlt() != 0) {
                        while (!close) {
                            if (!getClientByColor(p.getColor()).isGuiInterface())
                                squareNum = messageTo(p.getColor(), "Choose the squares numbers for the alternative effect; otherwise type 'end'", true).toLowerCase();
                            else
                                squareNum = messageTo(p.getColor(), "Select the squares for the alternative effect; stop add pressing End", true).toLowerCase();
                            try{
                                squaresManagement(p, squareNum, s);
                                if (!squareNum.equals(END))
                                    close = true;
                            }catch (WrongValueException e){
                                messageTo(p.getColor(), ERRORSTRING, false);
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                    }
                    for (int k = 1; k < w.getnSquaresAlt(); k++) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            squareNum = messageTo(p.getColor(), "Choose the squares numbers for the alternative effect; otherwise type 'end'", true).toLowerCase();
                        else
                            squareNum = messageTo(p.getColor(), "Select the squares for the alternative effect; stop add pressing End", true).toLowerCase();
                        while(true){
                            try {
                                squaresManagement(p, squareNum, s);
                                if (squareNum.equals(END))
                                    break;

                            }catch (WrongValueException e ){
                                messageTo(p.getColor(), ERRORSTRING, false);
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Manages the case in which the shoot goes wrong
     *
     * @param p                 a RealPlayer, the one which wants to shoot
     * @return                  an int, which indicates if the player wants to try again or do another action
     */
    public int errorManagement(RealPlayer p) {
        if (getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), ENABLE_END, false);
            messageTo(p.getColor(), ENABLE_CONTINUE, false);
        }
        while (true) {
            String choice2;
            if (!getClientByColor(p.getColor()).isGuiInterface())
                choice2 = messageTo(p.getColor(), "If you realize you can't shoot in any case, please type 'end'; otherwise, type 'continue'", true);
            else
                choice2 = messageTo(p.getColor(), "If you realize you can't shoot in any case, please press End; otherwise, press Continue", true);
            if (choice2.equals("end"))
                return 0;
            else if (choice2.equals("continue"))
                break;
            else
                messageTo(p.getColor(), ERRORSTRING, false);
        }
        if (getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_END, false);
            messageTo(p.getColor(), DISABLE_CONTINUE, false);
        }
        return 1;
    }

    /**
     * Manages the chosen victims, checking every choice
     *
     * @param p                         a RealPlayer, the one which wants to shoot
     * @param victimColor               a String, the color of the current victim
     * @param actionFigures             a List of Player, which contains all the Player in game
     * @param victims                   an ArrayList of Player, in which are added the victims, calling the specific method
     */
    public void targetsManagement(RealPlayer p, String victimColor, List<Player> actionFigures, ArrayList<Player> victims) {
        while (!victimColor.equals("end")) {
            try {
                cm.getPlayerOfColor(victimColor, actionFigures, victims);      //it also adds the chosen victim
                if (!getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), "Player detected", false);
                break;
            } catch (WrongValueException wVE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return;
                victimColor = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
            }
        }
    }

    /**
     * Manages the chosen victims, checking every choice
     *
     * @param p                         a RealPlayer, the one which wants to shoot
     * @param squareNum                 a String, the number of the current square
     * @param s                         an ArrayList of GenericSquare, in which are added the squares, calling the specific method
     * @throws WrongValueException      if tryParse gets a wrong value
     */
    public void squaresManagement(RealPlayer p, String squareNum, ArrayList<GenericSquare> s) throws WrongValueException {
        while (!squareNum.equals("end")) {
            try {
                while (true) {
                    cm.getGenericSquare(tryParse(squareNum), s);
                    if (!getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), "Square detected", false);
                    break;
                }
                break;
            } catch (WrongValueException wVE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return;
                throw new WrongValueException();
            }
        }

    }

    /**
     * Manages the Terminator shoot
     *
     * @param t                     the Terminator
     * @param players               an ArrayList of RealPlayer, which contains all the RealPlayer in game
     * @param mark                  a boolean, which notifies if the Terminator has also to mark his victim
     * @return                      an int, which notifies if the shoot was successful or not
     */
    public int shootManagement(Terminator t, ArrayList<RealPlayer> players, boolean mark) {
        String victimColor;
        String choice2;
        while (true) {
            if (getClientByColor(t.getOwnerColor()).isGuiInterface()){
                messageTo(t.getOwnerColor(), ENABLE_SELECT_PLAYER, false);
            }
            victimColor = messageTo(t.getOwnerColor(), "Choose your victim", true).toLowerCase();
            if(getClientByColor(t.getOwnerColor()).isGuiInterface())
                messageTo(t.getOwnerColor(), ENABLE_SELECT_PLAYER, false);
            try {
                t.shoot(cm.getRealPlayerOfColor(victimColor, players));
                if(mark)
                    cm.getRealPlayerOfColor(victimColor, players).getPb().addMarkedDamage(t.getColor());
                ArrayList<RealPlayer> temp = new ArrayList<>();
                temp.add(cm.getRealPlayerOfColor(victimColor, players));
                cm.revengePowerup(t, temp);
                return 1;
            } catch (WrongValueException | WrongPlayerException e) {
                messageTo(t.getOwnerColor(), "Error, something goes wrong", false);
                if(getClientByColor(t.getOwnerColor()).isTurnOver())
                    return -1;
                if (getClientByColor(t.getOwnerColor()).isGuiInterface()) {
                    messageTo(t.getOwnerColor(), DISABLE_SELECT_PLAYER, false);
                    messageTo(t.getOwnerColor(), ENABLE_END, false);
                    messageTo(t.getOwnerColor(), ENABLE_CONTINUE, false);
                }
                while (true) {
                    choice2 = messageTo(t.getOwnerColor(), "If you realize you can't shoot in any case, please type 'end'; otherwise, type 'continue'", true);
                    if (choice2.equals("end") || choice2.equals("continue"))
                        break;
                    else
                        messageTo(t.getOwnerColor(), ERRORSTRING, false);
                }
                if (getClientByColor(t.getOwnerColor()).isGuiInterface()) {
                    messageTo(t.getOwnerColor(), DISABLE_END, false);
                    messageTo(t.getOwnerColor(), DISABLE_CONTINUE, false);

                }
            }
            if (choice2.equals("end"))
                messageTo(t.getOwnerColor(), "Shoot goes wrong!", false);
            if(getClientByColor(t.getOwnerColor()).isGuiInterface())
                messageTo(t.getOwnerColor(), DISABLE_SELECT_PLAYER, false);
            return 0;
        }
    }

    /**
     * Removes double victims from an array
     *
     * @param victims                   an ArrayList of Player, which contains all the chosen victims
     */
    private void removeDoubleVictims(ArrayList<Player> victims) {
        for (int i = 0; i < victims.size() - 1; i++) {
            for (int j = 1; j < victims.size(); j++) {
                if (victims.get(i).getColor() == victims.get(j).getColor()) {
                    victims.remove(victims.get(j));
                }
            }
        }
    }

    //Other actions
    /**
     * Manages all the grab actions, calling the specific methods
     *
     * @param p                 a RealPlayer, the one which wants to grab
     * @return                  an int, which notifies if the action was successful or not
     */
    public int grabManagement(RealPlayer p) {
        String choice2;
        boolean done;
        if (getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_ALL, false);
            messageTo(p.getColor(), ENABLE_SQUARE, false);
        }
        if (!p.getPlayerPosition().isSpawnpoint()) {
            try{
                grabAmmo(p);
                return 1;
            }catch (WrongSquareException e){
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
                messageTo(p.getColor(), "There are no ammo in this square!", false);
                return 0;
            }
        } else {
            while (true) {
                try {
                    done = grabWeapon(p);
                    break;
                } catch (WrongValueException wVE) {
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    if (getClientByColor(p.getColor()).isGuiInterface()) {
                        messageTo(p.getColor(), ENABLE_END, false);
                        messageTo(p.getColor(), ENABLE_CONTINUE, false);

                    }
                    while (true) {
                        if (!getClientByColor(p.getColor()).isGuiInterface())
                            choice2 = messageTo(p.getColor(), "If you realize you can't grab in any case, please type 'end'; otherwise, type 'continue'", true);
                        else
                            choice2 = messageTo(p.getColor(), "If you realize you can't grab in any case, please press End; otherwise, press Continue", true);
                        if (choice2.equals("end") || choice2.equals("continue"))
                            break;
                        else
                            messageTo(p.getColor(), ERRORSTRING, false);
                    }
                    if (getClientByColor(p.getColor()).isGuiInterface()) {
                        messageTo(p.getColor(), DISABLE_END, false);
                        messageTo(p.getColor(), DISABLE_CONTINUE, false);

                    }
                }
                if (choice2.equals("end")){
                    done = false;
                    break;
                }
            }
        }
        if (getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_SQUARE, false);
        if(!done)
            return 0;
        return 1;
    }

    /**
     * Manages the grab of an AmmoTile
     *
     * @param p                         a RealPlayer, the one which wants to grab
     * @throws WrongSquareException     if there is not an AmmoTile on the chosen square
     */
    public void grabAmmo(RealPlayer p) throws WrongSquareException{
        Square s = (Square) p.getPlayerPosition();
        try{
            int numP = p.getPh().getPowerupDeck().getSize();
            p.grab(s, Board.getPowerups());
            if (!getClientByColor(p.getColor()).isGuiInterface()) {
                messageTo(p.getColor(), "Ammo grabbed", false);
                if (p.getPh().getPowerupDeck().getSize() > numP)
                    messageTo(p.getColor(), "Powerup grabbed", false);
            }
        }catch (WrongSquareException e){
            if(getClientByColor(p.getColor()).isTurnOver())
                return;
            throw new WrongSquareException(p.getPlayerPosition());
        }
    }

    /**
     * Manages the grab of a Weapon
     *
     * @param p                         a RealPlayer, the one which wants to grab
     * @return                          an int, which notifies if the action was successful or not
     * @throws WrongValueException      if the player hasn't enough ammo to grab the weapon
     */
    public boolean grabWeapon(RealPlayer p) throws WrongValueException {
        boolean end = false;
        boolean paid = true;
        try {
            if (getClientByColor(p.getColor()).isGuiInterface()) {
                messageTo(p.getColor(), DISABLE_ALL, false);
                messageTo(p.getColor(), ENABLE_SPAWN, false);
            }
            String discard = null;
            String pickup = messageTo(p.getColor(), "Choose the weapon you want to pick up", true).toLowerCase();
            SpawnpointSquare sp = (SpawnpointSquare) p.getPlayerPosition();
            while (!end) {
                if (sp.hasWeaponSpawnpoint(pickup))
                    end = true;
                else
                    pickup = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
            }
            if (getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), DISABLE_SPAWN, false);
            if (p.getPh().getWeaponDeck().getSize() > 2) {
                if (getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), ENABLE_SELECT_WEAPON_HAND, false);
                discard = messageTo(p.getColor(), "Choose the weapon you want to discard", true).toLowerCase();
                end = false;
                while (!end) {
                    if (p.getPh().hasWeapon(discard))
                        end = true;
                    else
                        discard = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
                }
                if (getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), DISABLE_SELECT_WEAPON_HAND, false);
            }
            char[] costConv = pm.grabCost(sp.getWeaponSpawnpoint().getWeapon(pickup).getCost());
            if (costConv[0] != '\u0000')
                paid = pm.paymentManagement(p, costConv);
            if(paid)
                p.grab(pickup, discard);
        } catch (WrongValueException wVE) {
            if(getClientByColor(p.getColor()).isTurnOver())
                return false;
            messageTo(p.getColor(), "You can't grab this weapon because you don't have enough resources", false);
            throw new WrongValueException();
        }
        return paid;
    }

    /**
     * Manages a run action during frenetic actions
     *
     * @param p                     a RealPlayer, the one which wants to run
     */
    public void run4Management(RealPlayer p) {
        int squareNum;
        if (getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_ALL, false);
            messageTo(p.getColor(), ENABLE_SQUARE, false);
        }
        while (true) {
            while (true) {
                try {
                    squareNum = tryParse(messageTo(p.getColor(), "Choose the square you want to run to", true));
                    if (squareNum <= 11 && squareNum >= 0)
                        break;
                } catch (WrongValueException wVE) {
                    messageTo(p.getColor(), ERRORSTRING, false);
                }
            }
            while (true) {
                try {
                    if (squareNum == 2 || squareNum == 4 || squareNum == 11)
                        p.run4(Board.getSpawnpoint(squareNum));
                    else
                        p.run4(Board.getSquare(squareNum));
                    break;
                } catch (WrongSquareException wSE) {
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return;
                    messageTo(p.getColor(), ERRORSTRING, false);
                }
            }
            break;
        }
        if (getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_SQUARE, false);
    }

    /**
     * Manages a run action
     *
     * @param p                     a RealPlayer, the one which wants to run
     * @param num                   an int, which represents the number of the square the player wants to run to
     * @param color                 a char, which represent the color of the player or the owner of the Terminator
     */
    public void runManagement(Player p, int num, char color) {
        String string;
        int squareNum;
        if (getClientByColor(color).isGuiInterface()) {
            messageTo(color, DISABLE_ALL, false);
            messageTo(color, ENABLE_SQUARE, false);
        }
        while (true) {
            try {
                if (!getClientByColor(color).isGuiInterface()) {
                    string = messageTo(color, "Select a correct square number", true);
                    squareNum = tryParse(string);
                } else {
                    string = messageTo(color, "Select a correct square", true);
                    squareNum = tryParse(string);
                }
                if (squareNum <= 11 && squareNum >= 0)
                    break;
                else{
                    if(string.equals("TIMED_OUT"))
                        return;
                    messageTo(color, ERRORSTRING, false);
                }
            } catch (WrongValueException wVE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return;
            }
        }
        boolean end = false;
        while (!end) {
            try {
                if (squareNum == 2 || squareNum == 4 || squareNum == 11) {
                    if (Board.isVisibleRun(p.getPlayerPosition(), Board.getSpawnpoint(squareNum)) <= num) {
                        p.run(Board.getSpawnpoint(squareNum));
                        if (!getClientByColor(color).isGuiInterface())
                            messageTo(color, "Player moved to square: " + p.getPlayerPosition().getNum(), false);
                        end = true;
                    } else {
                        while (true) {
                            try {
                                squareNum = tryParse(messageTo(color, ERRORSTRING, true));
                                break;
                            } catch (WrongValueException wVE) {
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                    }
                } else {
                    if (Board.isVisibleRun(p.getPlayerPosition(), Board.getSquare(squareNum)) <= num) {
                        p.run(Board.getSquare(squareNum));
                        if (!getClientByColor(color).isGuiInterface())
                            messageTo(color, "Player moved to square: " + p.getPlayerPosition().getNum(), false);
                        end = true;
                    } else {
                        while (true) {
                            try {
                                squareNum = tryParse(messageTo(color, ERRORSTRING, true));
                                break;
                            } catch (WrongValueException wVE) {
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                            }
                        }
                    }
                }
            } catch (WrongSquareException wSE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return;
                while (true) {
                    try {
                        squareNum = tryParse(messageTo(color, ERRORSTRING, true));
                        break;
                    } catch (WrongValueException wVE) {
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return;
                    }
                }
            }
        }
        if (getClientByColor(color).isGuiInterface())
            messageTo(color, DISABLE_SQUARE, false);
    }

    /**
     * Manages a reload action, asking which weapon the player wants to reload if has more then one reloadable weapon
     *
     * @param p                         a RealPlayer, the one which wants to reload
     * @throws WrongValueException      if the player hasn't enough resource to reload the weapon
     */
    public void reloadManagement(RealPlayer p) throws WrongValueException {
        String weaponName = null;
        int count = 0;
        weaponName = weaponToReloadDetector(p);
        boolean paid;
        for (Weapon w : p.getPh().getWeaponDeck().getWeapons()) {
            if (w.getName().equals(weaponName)) {
                try {
                    paid = pm.paymentManagement(p, w.getCost());
                    if(paid)
                        w.setLoaded(true);
                    if (!getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), weaponName + " realoaded", false);
                    else
                        messageTo(p.getColor(), "Reload was impossible, you don't have any payment method available", false);
                } catch (WrongValueException wVE) {
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return;
                    throw new WrongValueException();
                }
                break;
            }
        }
        if (getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_SELECT_WEAPON_HAND, false);
    }

    /**
     * Manages the choice of the weapon to reload
     *
     * @param p                     a RealPlayer, the one who wants to reload
     * @return                      a String, the name of the weapon the player wants to reload
     */
    public String weaponToReloadDetector(RealPlayer p){
        int count = 0;
        String weaponName = null;
        for(Weapon w : p.getPh().getWeaponDeck().getWeapons()) {
            if (!w.getLoaded())
                count++;
        }

        if (count == 1) {
            for (Weapon w : p.getPh().getWeaponDeck().getWeapons()) {
                if (!w.getLoaded()) {
                    weaponName = w.getName();
                    break;
                }
            }
        }
        else {
            if (getClientByColor(p.getColor()).isGuiInterface()) {
                messageTo(p.getColor(), DISABLE_ALL, false);
                messageTo(p.getColor(), ENABLE_SELECT_WEAPON_HAND, false);
            }
            weaponName = messageTo(p.getColor(), "Choose the weapon you'd like to reload", true).toLowerCase();
        }
        while (true) {
            if (p.getPh().hasWeapon(weaponName) && !p.getPh().getWeaponDeck().getWeapon(weaponName).getLoaded())
                break;
            else
                weaponName = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
        }
        return weaponName;
    }

}
