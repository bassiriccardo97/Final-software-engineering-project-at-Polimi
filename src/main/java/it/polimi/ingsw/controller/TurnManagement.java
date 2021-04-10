package it.polimi.ingsw.controller;

import it.polimi.ingsw.connections.Server;
import it.polimi.ingsw.connections.User;
import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.player.Terminator;
import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.controller.GameController.tryParse;

public class TurnManagement extends Server {

    private static final String CHOICESTRING = "Please, type your choice";
    private static final String ERRORSTRING = "Wrong choice, please try again";
    private static final String DISABLE_SHOOT = "DISABLE_SHOOT";
    private static final String DISABLE_ALL = "DISABLE_ALL";
    private static final String ENABLE_ALL_ACTION = "ENABLE_ALL_ACTION";
    private static final String ENABLE_RELOAD = "ENABLE_RELOAD";
    private static final String DISABLE_RELOAD = "DISABLE_RELOAD";
    private static final String ENABLE_USE_PU = "ENABLE_USE_PU";
    private static final String ENABLE_SELECT_POWERUP_HAND = "ENABLE_SELECT_POWERUP_HAND";
    private static final String DISABLE_SELECT_POWERUP_HAND = "DISABLE_SELECT_POWERUP_HAND";
    private static final String ENABLE_END = "ENABLE_END";
    private static final String DISABLE_END = "DISABLE_END";
    private static final String ONLY_NAME = "ONLY_NAME";
    private static final String ONLY_COLOR = "ONLY_COLOR";
    private static final String ENABLE_FRENETIC_BEFORE_FIRST = "ENABLE_FRENETIC_BEFORE_FIRST";
    private static final String ENABLE_FRENETIC_AFTER_FIRST = "ENABLE_FRENETIC_AFTER_FIRST";
    private static final String DISABLE_SELECT_PLAYER = "DISABLE_SELECT_PLAYER";
    private static final String ENABLE_SELECT_WEAPON_HAND = "ENABLE_SELECT_WEAPON_HAND";
    private static final String DISABLE_SELECT_WEAPON_HAND = "DISABLE_SELECT_WEAPON_HAND";
    private ActionsManagement am = new ActionsManagement();
    private CardsManagement cm = new CardsManagement();

    /**
     * Manages the very first turn of the starting player
     *
     * @param players               an ArrayList of RealPlayers, which contains all the RealPlayers in game
     * @param actionFigures         an ArrayList of Player, which contains all the Players in game
     */
    public void firstTurnManagement(ArrayList<RealPlayer> players, ArrayList<Player> actionFigures){
        sendModelRMX();
        int count = 0;
        int choice;
        while(count < 2){
            messageTo(players.get(0).getColor(), "Now you can do your actions", false);
            choice = turnManagement(players.get(0), actionFigures, true, 0);
            if(afterTimeout){
                afterTimeout = false;
                return;
            }
            if(choice == 0)
                count--;
            count++;
            sendModelRMX();
        }
    }

    /**
     * Manages a normal turn at the beginning, calling the specific methods
     *
     * @param p                     a RealPlayer, the one whose attribute "turn" is set at true
     * @param actionFigures         a List of Player, which contains all the Player in game
     * @param firstTurn             a boolean, which indicates if the turn is the very first one
     * @param choice2               an int, which indicates which action has been chosen by the player
     * @return                      an int, which indicates if the action was successful or not
     */
    public int turnManagement(RealPlayer p, List<Player> actionFigures, boolean firstTurn, int choice2) {
        if(choice2 == 0){
            int choice = turnMenu(p, firstTurn);
            while(!choiceControl(p, choice, firstTurn)){
                try{
                    choice = tryParse(messageTo(p.getColor(), ERRORSTRING, true));
                }catch (WrongValueException wVE){
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                }
            }
            int num = numSetting(p, choice);
            return turnChoiceManagement(p, choice, actionFigures, num);
        }
        else{
            while(!choiceControl(p, choice2, firstTurn)){
                try{
                    choice2 = tryParse(messageTo(p.getColor(), ERRORSTRING, true));
                }catch (WrongValueException wVE){
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                }
            }
            int num = numSetting(p, choice2);
            return turnChoiceManagement(p, choice2, actionFigures, num);
        }
    }

    /**
     * Sets the number of squares through which the player can run accordin to the specific action
     *
     * @param p                     a RealPlayer, the one whose attribute "turn" is set at true
     * @param choice                an int, which indicates which action has been chose
     * @return                      an int, which indicates if the action was successful or not
     */
    public int numSetting(RealPlayer p, int choice){
        int num = 0;
        if (choice == 1)
            num = 3;
        else {
            if ((choice == 2 && p.getPb().getAbility() == 0) || (choice == 3 && p.getPb().getAbility() == 2))
                num = 1;
            else{
                if(choice == 2 && (p.getPb().getAbility() == 1 || p.getPb().getAbility() == 2))
                    num = 2;
            }
        }
        return num;
    }

    /**
     * Manages a normal turn of the Terminator
     *
     * @param t                     the Terminator
     * @param players               an ArrayList of RealPlayer, which contains all the RealPlayer in game
     * @return                      an int, which indicates if the action was successful
     */
    public int turnManagement(Terminator t, ArrayList<RealPlayer> players) {
        int exit = 1;
        if (t.getPb().countDamages() < 3) {
            messageTo(t.getOwnerColor(), "Since Terminator has less than 3 damages, it could run and shoot", false);
            GenericSquare temp = t.getPlayerPosition();
            am.runManagement(t, 1, t.getOwnerColor());
            if(t.isSomeoneVisibleTerminator(players)){
                exit = am.shootManagement(t, players, false);
                if(exit == 0) {
                    t.setPlayerPosition(temp);
                }
            }
            else
                messageTo(t.getOwnerColor(), "Terminator can't see anyone", false);
            if (getClientByColor(t.getOwnerColor()).isGuiInterface()){
                messageTo(t.getOwnerColor(), DISABLE_SELECT_PLAYER, false);
            }
        } else {
            messageTo(t.getOwnerColor(), "Since Terminator has 3 damages or more, it could run, shoot and mark", false);
            am.runManagement(t, 1, t.getOwnerColor());
            GenericSquare temp = t.getPlayerPosition();
            if(t.isSomeoneVisibleTerminator(players)){
                exit = am.shootManagement(t, players, true);
                if(exit == 0)
                    t.setPlayerPosition(temp);
            }
            else
                messageTo(t.getOwnerColor(), "Terminator can't see anyone", false);
        }
        return exit;
    }

    /**
     * Manages the choice of the action, calling the specific method which shows the player all the possible actions
     *
     * @param p                     a RealPlayer, the one whose attribute "turn" is set at true
     * @param firstTurn             a boolean, which indicates if the turn is the very first one
     * @return                      an int, which indicates the action chosen
     */
    public int turnMenu(RealPlayer p, boolean firstTurn) {
        turnOnlyMenu(p, firstTurn);
        int end;
        while(true){
            try {
                end = tryParse(messageTo(p.getColor(), CHOICESTRING, true));
                break;
            }catch (WrongValueException wVE){
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
            }
        }
        return end;
    }

    /**
     * Manages the chosen action calling the specific methods of the normal actions
     *
     * @param p                         a RealPlayer, the one whose attribute "turn" is set at true
     * @param choice                    an int, which indicates the chosen action
     * @param actionFigures             a List of Player, which contains all the Player in game
     * @param num                       an int, which indicates the amount of squares the player can run through according to the specific action
     * @return                          an int, which indicate if the action was successful or not
     */
    public int turnChoiceManagement(RealPlayer p, int choice, List<Player> actionFigures, int num) {
        boolean end = false;
        GenericSquare temp;
        int exit;
        while (!end) {
            switch (choice) {
                case 1:
                    am.runManagement(p, num, p.getColor());
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    end = true;
                    sendModelRMX();
                    break;
                case 2:
                    temp = p.getPlayerPosition();
                    am.runManagement(p, num, p.getColor());
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    sendModelRMX();
                    exit = am.grabManagement(p);
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    if(exit == 0)
                        p.setPlayerPosition(temp);
                    sendModelRMX();
                    return exit;
                case 3:
                    temp = p.getPlayerPosition();
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    if (p.getPb().countDamages() >= 6) {
                        am.runManagement(p, num, p.getColor());
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return -1;
                        sendModelRMX();
                    }
                    exit = am.shootManagement(p, actionFigures);
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    if(exit ==0)
                        p.setPlayerPosition(temp);
                    sendModelRMX();
                    return exit;
                case 4:
                    cm.usablePowerup(p, actionFigures);
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    return 0;
                default:
                    while(true){
                        try {
                            choice = tryParse(messageTo(p.getColor(), ERRORSTRING, true));
                            break;
                        }catch (WrongValueException wVE){
                            if(getClientByColor(p.getColor()).isTurnOver())
                                return -1;
                        }
                    }
            }
        }
        return 1;
    }

    /**
     * Manages eventually the choice of reload
     *
     * @param p                         a RealPlayer, the one whose attribute "turn" is set at true
     * @param frenetic                  boolean whether frenetic are starts or not
     */
    public void reloadChoiceManagement(RealPlayer p, boolean frenetic) {
        if(getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_ALL, false);
            messageTo(p.getColor(), ENABLE_SELECT_WEAPON_HAND, false);
            messageTo(p.getColor(), ENABLE_RELOAD, false);
            messageTo(p.getColor(), ENABLE_END, false);
        }
        while (true) {
            String choice2;
            if (!frenetic) {
                if (!getClientByColor(p.getColor()).isGuiInterface())
                    choice2 = messageTo(p.getColor(), "In addition, type '1' if you want to reload; otherwise type 'end'", true).toLowerCase();
                else
                    choice2 = messageTo(p.getColor(), "If you want you can choose a weapon to reload; otherwise press End", true).toLowerCase();
            } else
                choice2 = messageTo(p.getColor(), "If you want you can choose a weapon to reload; otherwise press End", true).toLowerCase();

            int choice = 0;
            if (choice2.equals("end"))
                break;
            else {
                try {
                    if(!frenetic){
                        choice = tryParse(choice2);
                        if (choice == 1) {
                            try {
                                am.reloadManagement(p);
                                break;
                            } catch (WrongValueException wVE) {
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                                messageTo(p.getColor(), ERRORSTRING, false);
                            }
                        } else
                            messageTo(p.getColor(), ERRORSTRING, false);
                    } else {
                        if (p.getPh().hasWeapon(choice2) && !p.getPh().getWeaponDeck().getWeapon(choice2).getLoaded()){
                            try {
                                am.reloadManagement(p);
                                break;
                            } catch (WrongValueException wVE) {
                                if(getClientByColor(p.getColor()).isTurnOver())
                                    return;
                                messageTo(p.getColor(), ERRORSTRING, false);
                            }
                        } else
                            messageTo(p.getColor(), ERRORSTRING, false);
                    }
                } catch (WrongValueException wVE) {
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return;
                    messageTo(p.getColor(), ERRORSTRING, false);
                }
            }
        }
        if(getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_SELECT_WEAPON_HAND, false);
            messageTo(p.getColor(), DISABLE_RELOAD, false);
            messageTo(p.getColor(), DISABLE_END, false);
        }
    }

    /**
     * Manages a turn in "frenetic actions-mode", calling the specific methods according to the "position" of the player in the shift (before or after the starting one)
     *
     * @param p                         a RealPlayer, the one whose attribute "turn" is set a true
     * @param actionFigures             a List of Player, which contains all the Player in game
     */
    public void turnMenuFrenetic(RealPlayer p, List<Player> actionFigures) {
        boolean firstAction = false;
        int count = 0;
        sendModelRMX();
        if (p.getFf2actions()) {
            while (true) {
                if (!firstAction) {
                    if (!getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), "You can do 2 actions between these 3 (in any order):", false);
                    else
                        messageTo(p.getColor(), "Now you can do your frenetic actions", false);
                } else if (!getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), "You can do 1 frenetic actions between these 3:", false);
                else
                    messageTo(p.getColor(), "Now you can do your frenetic actions", false);
                if (!getClientByColor(p.getColor()).isGuiInterface()) {
                    messageTo(p.getColor(), "   - Press 1 to run (up to 4 squares)", false);
                    messageTo(p.getColor(), "   - Press 2 to run (up to 2 square) and grab", false);
                    messageTo(p.getColor(), "   - Press 3 to run, reload and shoot", false);
                }else {
                    messageTo(p.getColor(), ENABLE_FRENETIC_BEFORE_FIRST, false);
                }
                if (p.getPh().hasAlwaysUsablePowerup()) {
                    if (!getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), "In addition, if you want to use one of your powerups, type 4", false);
                    else {
                        messageTo(p.getColor(), ENABLE_USE_PU, false);
                    }
                }
                int choice;
                while (true) {
                    try {
                        choice = tryParse(messageTo(p.getColor(), CHOICESTRING, true));
                        break;
                    } catch (WrongValueException wVE) {
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return;
                        messageTo(p.getColor(), ERRORSTRING, false);
                    }
                }
                int action;
                action = turnChoiceManagementFrenetic(p, choice, actionFigures);
                firstAction = true;
                count++;
                if (action == 0)
                    count--;
                if (count > 1)
                    return;
            }
        } else {
            if (!getClientByColor(p.getColor()).isGuiInterface()) {
                messageTo(p.getColor(), "You can do 1 frenetic action between these 2:", false);
                messageTo(p.getColor(), "   - Press 1 to run (up to 3 squares) and grab", false);
                messageTo(p.getColor(), "   - Press 3 to run (up to 2 squares), reload and shoot", false);
                if (p.getPh().hasAlwaysUsablePowerup())
                    messageTo(p.getColor(), "In addition, if you want to use one of your powerups, type 4", false);
            } else {
                messageTo(p.getColor(), ENABLE_FRENETIC_AFTER_FIRST, false);
                messageTo(p.getColor(), "Now you can do your frenetic action", false);
                if (p.getPh().hasAlwaysUsablePowerup())
                    messageTo(p.getColor(), ENABLE_USE_PU, false);
            }

            int choice;
            int action;
            while (true) {
                while (true) {
                    try {
                        choice = tryParse(messageTo(p.getColor(), CHOICESTRING, true));
                        break;
                    } catch (WrongValueException wVE) {
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return;
                        messageTo(p.getColor(), ERRORSTRING, false);
                    }
                }
                action = turnChoiceManagementFrenetic2(p, choice, actionFigures);
                if(action!=0)
                    break;
            }
        }
        sendModelRMX();
    }

    /**
     * Manages a turn in "frenetic actions-mode" if the turn of the player is before the starting one's
     *
     * @param p                         a RealPlayer, the one whose attribute "turn" is set at true
     * @param choice                    an int, which indicates the chosen action
     * @param actionFigures             a List of Player, which contains all the Player in game
     * @return                          an int, which indicates if the action was successful or not
     */
    public int turnChoiceManagementFrenetic(RealPlayer p, int choice, List<Player> actionFigures) {
        int exit = 0;
        GenericSquare temp;
        while (true) {
            switch (choice) {
                case 1:
                    am.run4Management(p);
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    sendModelRMX();
                    return 1;
                case 2:
                    temp = p.getPlayerPosition();
                    am.runManagement(p, 2, p.getColor());
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    sendModelRMX();
                    exit = am.grabManagement(p);
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    if(exit == 0)
                        p.setPlayerPosition(temp);
                    sendModelRMX();
                    return exit;
                case 3:
                    temp = p.getPlayerPosition();
                    am.runManagement(p, 1, p.getColor());
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    sendModelRMX();
                    if(p.getPh().unloadedWeapons() && p.checkAllResources()){
                        reloadChoiceManagement(p, true);
                        sendModelRMX();
                    }
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    if(p.getPh().loadedWeapons()){
                        exit = am.shootManagement(p, actionFigures);
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return -1;
                        sendModelRMX();
                    }
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    if(exit == 0)
                        p.setPlayerPosition(temp);
                    sendModelRMX();
                    return exit;
                case 4:
                    cm.usablePowerup(p, actionFigures);
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    sendModelRMX();
                    return 0;
                default:
                    while(true){
                        try {
                            choice = tryParse(messageTo(p.getColor(), ERRORSTRING, true));
                            break;
                        }catch (WrongValueException wVE){
                            if(getClientByColor(p.getColor()).isTurnOver())
                                return -1;
                        }
                    }
            }
        }
    }

    /**
     * Manages a turn in "frenetic actions-mode" if the turn of the player is after the starting one's
     *
     * @param p                         a RealPlayer, the one whose attribute "turn" is set at true
     * @param choice                    an int, which indicates the chosen action
     * @param actionFigures             a List of Player, which contains all the Player in game
     * @return                          an int, which indicates if the action was successful or not
     */
    public int turnChoiceManagementFrenetic2(RealPlayer p, int choice, List<Player> actionFigures) {
        int exit;
        GenericSquare temp;
        while (true) {
            if (choice == 1) {
                am.runManagement(p, 3, p.getColor());
                if(getClientByColor(p.getColor()).isTurnOver())
                    return -1;
                sendModelRMX();
                am.grabManagement(p);
                return 1;
            } else {
                if (choice == 4){
                    cm.usablePowerup(p, actionFigures);
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    sendModelRMX();
                    return 0;
                }
                else if (choice == 3) {
                    exit = 1;
                    temp = p.getPlayerPosition();
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return -1;
                    am.runManagement(p, 2, p.getColor());
                    sendModelRMX();
                    if (p.getPh().unloadedWeapons() && p.checkAllResources()){
                        reloadChoiceManagement(p, true);
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return -1;
                        sendModelRMX();
                    }

                    if(p.getPh().loadedWeapons()){
                        exit = am.shootManagement(p, actionFigures);
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return -1;
                        sendModelRMX();
                    }
                    if(exit == 0){
                        p.setPlayerPosition(temp);
                        sendModelRMX();

                    }
                    return exit;
                } else {
                    while(true){
                        try {
                            choice = tryParse(messageTo(p.getColor(), ERRORSTRING, true));
                            break;
                        }catch (WrongValueException wVE){
                            if(getClientByColor(p.getColor()).isTurnOver())
                                return -1;
                        }
                    }
                }
            }
        }
    }

    /**
     * Manages eventually the respawn of a player
     *
     * @param p                         a RealPlayer, the one which has to respawn
     */
    public void respawnManagement(RealPlayer p) {
        if(afterTimeout){
            afterTimeout = false;
            return;
        }
        p.respawn();
        String powerupName = null;
        String powerupColor = null;
        boolean end = false;
        messageTo(p.getColor(), "You died! You've to respawn", false);
        sendModelRMX();
        if(p.getPh().getPowerupDeck().getSize() == 1){
            powerupName = p.getPh().getPowerupDeck().getPowerups().get(0).getName();
            powerupColor = cm.identifyColor(p.getPh().getPowerupDeck().getPowerups().get(0).getColor());
        }
        else{
            while (!end) {
                if (!getClientByColor(p.getColor()).isGuiInterface())
                    powerupName = messageTo(p.getColor(), "Choose the powerup name you want to discard", true).toLowerCase();
                else {
                    messageTo(p.getColor(), ENABLE_SELECT_POWERUP_HAND, false);
                    messageTo(p.getColor(), ONLY_NAME, false);
                    powerupName = messageTo(p.getColor(), "Choose the powerup you want to discard", true).toLowerCase();
                }
                if (p.getPh().hasPowerup(powerupName))
                    end = true;
                if(getClientByColor(p.getColor()).isDisconnected())
                    return;
            }
            end = false;
            if(getClientByColor(p.getColor()).isDisconnected())
                return;
            Powerup pu = p.getPh().singlePowerupDetector(powerupName);
            if(pu == null){
                while (!end) {
                    if (!getClientByColor(p.getColor()).isGuiInterface())
                        powerupColor = messageTo(p.getColor(), "Choose the powerup color you want to discard", true).toLowerCase();
                    else{
                        messageTo(p.getColor(), ONLY_COLOR, false);
                        powerupColor = messageTo(p.getColor(), "Choose the powerup you want to discard", true).toLowerCase();
                    }
                    if (p.getPh().hasPowerupColor(powerupName, powerupColor))
                        end = true;
                    if(getClientByColor(p.getColor()).isDisconnected())
                        return;
                }
            }
            else
                powerupColor = cm.identifyColor(pu.getColor());
            if(getClientByColor(p.getColor()).isDisconnected())
                return;
            messageTo(p.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
        }
        p.getPb().setAlive();
        if(getClientByColor(p.getColor()).isDisconnected())
            return;
        sendModelRMX();
        p.initPosition(powerupName, powerupColor.charAt(0));
        if(getClientByColor(p.getColor()).isDisconnected())
            return;
        messageTo(p.getColor(), "Respawned", false);
    }

    /**
     * Manages the type of "normal turn", showing all the possible actions (normal and adrenalinic ones)
     *
     * @param p                     a RealPlayer, the one whose attribute "turn" is set a true
     * @param firstTurn             a boolean, which indicates if the turn is the very first one
     */
    public void turnOnlyMenu(RealPlayer p, boolean firstTurn){
        if(getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), "Choose an action.", false);
            messageTo(p.getColor(), DISABLE_ALL, false);
            messageTo(p.getColor(), ENABLE_ALL_ACTION, false);
            if (!p.getPh().loadedWeapons() || firstTurn)
                messageTo(p.getColor(), DISABLE_SHOOT, false);
            if (p.getPh().hasAlwaysUsablePowerup() && (!firstTurn || (firstTurn && (!p.getPh().hasPowerup("newton") || (p.getPh().hasPowerup("newton") && (p.getPh().hasPowerup("teleporter") || Server.isTerminator()))))))
                messageTo(p.getColor(), ENABLE_USE_PU, false);
        } else {
            messageTo(p.getColor(), "Choose an action between these:", false);
            messageTo(p.getColor(), "    - Type 1 to run (up to 3 squares)", false);
            if (p.getPb().getAbility() >= 1)
                messageTo(p.getColor(), "    - Type 2 to run (up to 2 squares) and grab", false);
            else
                messageTo(p.getColor(), "    - Type 2 to run 1 square and grab", false);
            if (p.getPh().loadedWeapons() && !firstTurn) {
                if (p.getPb().getAbility() == 2)
                    messageTo(p.getColor(), "    - Type 3 to run and shoot", false);
                else
                    messageTo(p.getColor(), "    - Type 3 to shoot", false);
            } else if (!p.getPh().loadedWeapons()){
                messageTo(p.getColor(), "You can't shoot because you dont'have loaded weapons", false);
            }
            else if (firstTurn){
                messageTo(p.getColor(), "You can't shoot because you don't have reachable targets", false);
            }

            if (p.getPh().hasAlwaysUsablePowerup() && (!firstTurn || (firstTurn && (!p.getPh().hasPowerup("newton") || (p.getPh().hasPowerup("newton") && (p.getPh().hasPowerup("teleporter") || Server.isTerminator())))))) {
                messageTo(p.getColor(), "In addition, if you want to use one of your powerups, type 4", false);
            }
        }
    }

    /**
     * Checks if the choice of the action is a possible one or not
     *
     * @param p                     a RealPlayer, the one which has chosen the action
     * @param choice2               an int, which indicates the chosen action
     * @param firstTurn             a boolean, which indicates if the turn is the very first one
     * @return                      a boolean, which indicates if the action is possible or not
     */
    public boolean choiceControl(RealPlayer p, int choice2, boolean firstTurn){
        if((choice2 != 1 && choice2 != 2 && choice2 != 3 && choice2 != 4) || (choice2 == 3 && (!p.getPh().loadedWeapons() || firstTurn)) || (choice2 == 4 && (!p.getPh().hasAlwaysUsablePowerup() || (p.getPh().usablePowerupsDetector("newton") && firstTurn && !Server.isTerminator())))) {
            return false;
        }
        return true;
    }

}