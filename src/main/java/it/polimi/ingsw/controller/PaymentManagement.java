package it.polimi.ingsw.controller;

import it.polimi.ingsw.connections.Server;
import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.card.weapon.Weapon;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.player.RealPlayer;
import java.util.ArrayList;
import static it.polimi.ingsw.controller.GameController.tryParse;

public class PaymentManagement extends Server{

    private static final String CHOICESTRING = "Please, type your choice";
    private static final String ERRORSTRING = "Wrong choice, please try again";
    private static final String ENABLE_ALL_PAYMENT_CHOICE = "ENABLE_ALL_PAYMENT_CHOICE";
    private static final String DISABLE_ALL_PAYMENT_CHOICE = "DISABLE_ALL_PAYMENT_CHOICE";
    private static final String ENABLE_PAY_WITH_AMMO = "ENABLE_PAY_WITH_AMMO";
    private static final String ENABLE_PAY_WITH_POWERUP = "ENABLE_PAY_WITH_POWERUP";
    private static final String ENABLE_PAY_WITH_BOTH = "ENABLE_PAY_WITH_BOTH";
    private static final String DISABLE_ALL = "DISABLE_ALL";
    private static final String ENABLE_SELECT_POWERUP_HAND = "ENABLE_SELECT_POWERUP_HAND";
    private static final String DISABLE_SELECT_POWERUP_HAND = "DISABLE_SELECT_POWERUP_HAND";
    private static final String ENABLE_USE_PU = "ENABLE_USE_PU";
    private static final String DISABLE_USE_PU = "DISABLE_USE_PU";
    private static final String ENABLE_END = "ENABLE_END";
    private static final String DISABLE_END = "DISABLE_END";
    private static final String ENABLE_AMMO_CHOICE = "ENABLE_AMMO_CHOICE";
    private static final String DISABLE_AMMO_CHOICE = "DISABLE_AMMO_CHOICE";
    private static final String DISABLE_PAY_WITH_AMMO = "DISABLE_PAY_WITH_AMMO";
    private static final String DISABLE_PAY_WITH_POWERUP = "DISABLE_PAY_WITH_POWERUP";
    private static final String DISABLE_PAY_WITH_BOTH = "DISABLE_PAY_WITH_BOTH";
    private static final CardsManagement cm = new CardsManagement();

    /**
     * Checks which payment methods are available for the player and lets him decided which one use
     *
     * @param p                 a RealPlayer, the one which has to pay
     * @param cost              an array of char, which represents the cost
     * @return                  an array of int, which represents the choice if its length is equals to 1, otherwise it contains the amount (according to the color, in alphabetical order)
     * of powerup the player has discarded, used to pay the scraps with the ammo
     */
    public int[] paymentChecks(RealPlayer p, char[] cost) {
        if (getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_ALL, false);
        messageTo(p.getColor(), "Choose a payment method", false);
        int count = 0;
        if(p.checkAmmoResources(cost)) {
            if (!getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), "    - If you want to pay with ammo, type 1", false);
            else
                messageTo(p.getColor(), ENABLE_PAY_WITH_AMMO, false);
            count++;
        }
        if(p.checkPowerupResources(cost)) {
            if (!getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), "    - If you want to pay with powerups, type 2", false);
            else
                messageTo(p.getColor(), ENABLE_PAY_WITH_POWERUP, false);
            count++;
        }
        if(p.checkAmmoPowerupResources(cost)){
            if (!getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), "    - If you want to pay both with powerups and ammo, type 3", false);
            else
                messageTo(p.getColor(), ENABLE_PAY_WITH_BOTH, false);
            count++;
        }
        int[] amount;
        int[] choice = new int[1];
        ArrayList<Powerup> powerupsToBeDiscarded;
        if(count!=0){
            while(true){
                while(true){
                    try {
                        choice[0] = tryParse(messageTo(p.getColor(), CHOICESTRING, true));
                        break;
                    }catch (WrongValueException wVE){
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return null;
                    }
                }
                switch (choice[0]) {
                    case 1:
                        if(p.checkAmmoResources(cost)) {
                            if (getClientByColor(p.getColor()).isGuiInterface())
                                messageTo(p.getColor(), DISABLE_ALL_PAYMENT_CHOICE, false);
                            return choice;
                        } else {
                            messageTo(p.getColor(), ERRORSTRING, false);
                        }
                        break;
                    case 2:
                        if(p.checkPowerupResources(cost)) {
                            if (getClientByColor(p.getColor()).isGuiInterface())
                                messageTo(p.getColor(), DISABLE_ALL_PAYMENT_CHOICE, false);
                            return choice;
                        } else {
                            messageTo(p.getColor(), ERRORSTRING, false);
                        }
                        break;

                    case 3:
                        if(p.checkAmmoPowerupResources(cost)) {
                            int[] costConv = p.getPb().convert(cost);
                            powerupsToBeDiscarded = checkNumPowerupsChoice(p, costConv);
                            amount = amountCalculator(powerupsToBeDiscarded);
                            int[] scraps = scrapsCalc(p, cost, amount);
                            char[] conv2 = convert2(scraps);
                            if (p.checkAmmoResources(conv2)) {
                                if (amount[0] == 0 && amount[1] == 0 && amount[2] == 0)
                                    if (!getClientByColor(p.getColor()).isGuiInterface())
                                        messageTo(p.getColor(), "You have paid only with ammo", false);
                                    else if (amount[0] == costConv[0] && amount[1] == costConv[1] && amount[2] == costConv[2])
                                        if (!getClientByColor(p.getColor()).isGuiInterface())
                                            messageTo(p.getColor(), "You have paid only with powerup", false);
                                for (Powerup pu : powerupsToBeDiscarded)
                                    p.getPh().discard(pu);
                                if (getClientByColor(p.getColor()).isGuiInterface())
                                    messageTo(p.getColor(), DISABLE_ALL_PAYMENT_CHOICE, false);
                                return amount;
                            }
                        }
                        else {
                            messageTo(p.getColor(), ERRORSTRING, false);
                            break;
                        }
                        break;
                    default:
                        messageTo(p.getColor(), ERRORSTRING, false);
                        break;
                }
            }
        }
        else{
            messageTo(p.getColor(), "You don't have enough resources", false);
            choice[0] = 4;
            if (getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), DISABLE_ALL_PAYMENT_CHOICE, false);
            return choice;
        }
    }

    /**
     * Manages the payment of a cost
     *
     * @param p                         a RealPlayer, the one which has to pay
     * @param cost                      an array of char, which represents the cost
     * @return                          a boolean, which indicates if the payment was successful or not
     * @throws WrongValueException      if there are no payment method available
     */
    public boolean paymentManagement (RealPlayer p, char[] cost) throws WrongValueException {
        while(true){
            int[] paymentChecksReturn = paymentChecks(p, cost);
            if(getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), ENABLE_ALL_PAYMENT_CHOICE, false);
            switch (paymentChecksReturn.length) {
                case 1:
                    int choice = paymentChecksReturn[0];
                    if(choice == 1){
                        p.getPb().payAmmo(cost);
                        if(getClientByColor(p.getColor()).isGuiInterface())
                            messageTo(p.getColor(), DISABLE_ALL_PAYMENT_CHOICE, false);
                        return true;
                    }
                    else if(choice == 2) {
                        powerupPayment(p, cost);
                        if(getClientByColor(p.getColor()).isGuiInterface())
                            messageTo(p.getColor(), DISABLE_ALL_PAYMENT_CHOICE, false);
                        return true;
                    }
                    else if(choice == 0){
                        messageTo(p.getColor(), "You can't use this payment method because you don't have enough resources", false);
                        throw new WrongValueException();
                    }
                    else if(choice == 4){
                        messageTo(p.getColor(), "You don't have any payment method available", false);
                        if(getClientByColor(p.getColor()).isGuiInterface())
                            messageTo(p.getColor(), DISABLE_ALL_PAYMENT_CHOICE, false);
                        return false;
                    }
                    break;
                case 3:
                    p.getPb().payAmmo(convert2(scrapsCalc(p, cost, paymentChecksReturn))); //paymentChecksReturn -> amount
                    if(getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), DISABLE_ALL_PAYMENT_CHOICE, false);
                    return true;
                default:
                    messageTo(p.getColor(), "You can't use this payment method because you don't have enough resources", false);
                    throw new WrongValueException();
            }
        }
    }

    /**
     * Converts the entire cost of a weapon into the one the player has to pay in order to grab the weapon
     *
     * @param cost                  an array of char, which is the entire cost of the weapon
     * @return                      an array of char, which is the cost of the weapon in order to grab it
     */
    public char[] grabCost(char[] cost) {
        char[] costConv = new char[2];
        costConv[0] = cost[1];
        costConv[1] = cost[2];
        return costConv;
    }

    /**
     * Manages the payment through powerups
     *
     * @param p                     a RealPlayer, the one which has to pay
     * @param cost                  an array of char, which represents the cost
     */
    private void powerupPayment(RealPlayer p, char [] cost){
        String powerupName;
        for(int i = 0; i < cost.length; i++){
            if(p.getPh().numPowerupColor(cost[i]) > 1){
                if (getClientByColor(p.getColor()).isGuiInterface()){
                    messageTo(p.getColor(), DISABLE_ALL, false);
                    messageTo(p.getColor(), ENABLE_SELECT_POWERUP_HAND, false);

                }
                while(true){
                    powerupName = messageTo(p.getColor(), "Choose which of the powerups with the same color you want to discard", true);
                    if(p.getPh().hasPowerupColor(powerupName, cm.identifyColor(cost[i])))
                        break;
                    else
                        messageTo(p.getColor(), ERRORSTRING, false);
                }
                Powerup pu = p.getPh().getPowerupDeck().getPowerup(powerupName, cost[i]);
                p.getPh().discard(pu);
            }
            else if(p.getPh().numPowerupColor(cost[i]) == 1){
                p.getPh().discard(p.getPh().singlePowerupColorDetector(cost[i]));
            }
        }
        if(getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
    }

    /**
     * Calculates the amount of the powerups the player wants to use to pay
     *
     * @param powerupsToBeDiscarded         an ArrayList of Powerup, which contains all the powerups the player decided to use to pay
     * @return                              an array of int, which contains the amount of powerup for each color (in alphabetical order)
     */
    private int[] amountCalculator(ArrayList<Powerup> powerupsToBeDiscarded){
        int[] amount = new int[3];
        for(Powerup pu : powerupsToBeDiscarded){
            switch (pu.getColor()){
                case 'b':
                    amount[0]++;
                    break;
                case 'r':
                    amount[1]++;
                    break;
                default:
                    amount[2]++;
                    break;
            }
        }
        return amount;
    }

    /**
     * Calculates the scraps which has to be paid through ammo
     *
     * @param p                     a RealPlayer, the one which has to pay
     * @param cost                  an array of char, which represents the cost
     * @param amount                an array of int, which represents the amount of powerup for each color (in alphabetical order) the player wants to use to pay
     * @return                      an array of int, which represents the amount of ammo for each color (in alphabetical order) the player has to pay
     */
    private int[] scrapsCalc(RealPlayer p, char[] cost, int[] amount){
        int[] scraps = new int[3];
        int[] temp = p.getPb().convert(cost);

        if(temp[0] > 0)
            scraps[0] = temp[0] - amount[0];
        if(temp[1] > 0)
            scraps[1] = temp[1] - amount[1];
        if(temp[2] > 0)
            scraps[2] = temp[2] - amount[2];
        return scraps;
    }

    /**
     * Converts the cost expressed through an array of int (the amount of the cost for each color, in alphabetical order) in an array of char
     *
     * @param cost                  an array of int, which represents the cost
     * @return                      an array of char, which represents the cost converted
     */
    public char[] convert2(int[] cost) {
        char[] cost2 = new char[3];
        if(cost[0] == 1)
            cost2[0] = 'b';
        else if(cost[0] == 2){
            cost[0] = 'b';
            cost[1] = 'b';
        }
        int j;
        for(j = 0; j < cost.length && cost[j]!='\u0000'; j++)
            ;
        if(cost[1] == 1)
            cost2[j] = 'r';
        else if(cost[1] == 2){
            cost[j] = 'r';
            cost[j+1] = 'r';
        }
        for(j = 0; j < cost.length && cost[j]!='\u0000'; j++)
            ;
        if(cost[2] == 1)
            cost2[j] = 'y';
        else if(cost[2] == 2){
            cost[j] = 'y';
            cost[j+1] = 'y';
        }
        return cost2;
    }

    /**
     * Asks the player to choose the powerups he want to use to pay for each color
     *
     * @param p                     a RealPlayer, the one which has to pay
     * @param cost                  an array of int, which represents the amount of cost for each color (in alphabetical order)
     * @return                      an ArrayList of Powerup, which contains all the Powerup the player wants to use to pay
     */
    public ArrayList<Powerup> checkNumPowerupsChoice(RealPlayer p, int[] cost) {
        int[] amount = new int[3];
        ArrayList<Powerup> powerupsToBeDiscarded = new ArrayList<>();
        int choice;
        String choice2;
        char color;
        for(int i = 0; i < cost.length; i++){
            switch (i){
                case 0:
                    color = 'b';
                    break;
                case 1:
                    color = 'r';
                    break;
                default:
                    color = 'y';
                    break;
            }
            if(cost[i]>=1 && p.getPh().numPowerupColor(color) >= 1 && p.getPb().getAmmoBox()[i] > 0){
                if (getClientByColor(p.getColor()).isGuiInterface()){
                    messageTo(p.getColor(), DISABLE_ALL, false);
                    messageTo(p.getColor(), ENABLE_USE_PU, false);
                    messageTo(p.getColor(), ENABLE_END, false);
                }
                if (!getClientByColor(p.getColor()).isGuiInterface())
                    choice2 = messageTo(p.getColor(), "If you want to use a powerup to pay instead of the " + cm.identifyColor(color) + " ammo, type '4'; otherwise type 'end'", true);
                else
                    choice2 = messageTo(p.getColor(), "If you want to use a powerup to pay instead of the " + cm.identifyColor(color) + " ammo, press 'Use PU'; otherwise press End", true);
                while(true) {
                    if (!choice2.equals("end")) {
                        try {
                            choice = tryParse(choice2);
                            if (choice != 4)
                                messageTo(p.getColor(), ERRORSTRING, false);
                            else
                                break;
                        } catch (WrongValueException wVE) {
                            if(getClientByColor(p.getColor()).isTurnOver())
                                return null;
                            messageTo(p.getColor(), ERRORSTRING, false);
                        }
                    } else
                        break;
                }
                if(!choice2.equals("end")){
                    addingPowerup(p, amount, powerupsToBeDiscarded, cost, color, i);
                }
                if (getClientByColor(p.getColor()).isGuiInterface()){
                    messageTo(p.getColor(), DISABLE_USE_PU, false);
                    messageTo(p.getColor(), DISABLE_END, false);
                }
            }
            else if(p.getPb().getAmmoBox()[i] == 0)
                addingPowerup(p, amount, powerupsToBeDiscarded, cost, color, i);
        }
        return powerupsToBeDiscarded;
    }

    /**
     * Adds all the specific powerups the player decides to use to pay, calculating the amount; if the player has a single powerup of a specific color and intends to use it,
     * he doesn't have to specify which powerup he wants to use
     *
     * @param p                             a RealPlayer, the one which has to pay
     * @param amount                        an array of int, which represents the amount of powerup for each color (in alphabetical order) the player wants to use to pay
     * @param powerupsToBeDiscarded         an ArrayList of Powerup, which contains all the powerups chosen by the player
     * @param cost                          an array of int, which represents the cost
     * @param color                         a char, which represents a specific color
     * @param num                           an int, which represents which color (in alphabetical order) is considered in order to update the amount
     */
    private void addingPowerup(RealPlayer p, int[] amount, ArrayList<Powerup> powerupsToBeDiscarded, int[] cost, char color, int num){
        ArrayList<Powerup> powerupsToBeDiscardedTemp;
        if (p.getPh().numPowerupColor(color) == 1){
            amount[num] = 1;
            powerupsToBeDiscarded.add(p.getPh().singlePowerupColorDetector(color));
        }
        else if (p.getPh().numPowerupColor(color) > 1) {
            powerupsToBeDiscardedTemp = checkColorPowerup(p, cost[num], color);
            amount[num] = powerupsToBeDiscardedTemp.size();
            insertPowerup(powerupsToBeDiscardedTemp, powerupsToBeDiscarded);
        }

    }

    /**
     * Asks the player which powerup of a specific color he wants to use to pay; if the player wants to use a powerup to cover a specific color and he has only a single powerup
     * of that color he doens't have to specify which powerup he wants to use
     *
     * @param p                     a RealPlayer, the one which has to pay
     * @param costColor             an int, which indicates the amount of the cost of the specific color
     * @param color                 a char, which indicates the color
     * @return                      an ArrayList of Powerup, which contains the Powerup the player decided to use to pay
     */
    public ArrayList<Powerup> checkColorPowerup(RealPlayer p, int costColor, char color){
        String powerupName;
        ArrayList<Powerup> powerupsToBeDiscarded = new ArrayList<>();
        int i = 0;
        while(i < costColor){
            if (getClientByColor(p.getColor()).isGuiInterface()){
                messageTo(p.getColor(), DISABLE_ALL, false);
                messageTo(p.getColor(), ENABLE_SELECT_POWERUP_HAND, false);
                messageTo(p.getColor(), ENABLE_END, false);
            }
            if (!getClientByColor(p.getColor()).isGuiInterface())
                powerupName = messageTo(p.getColor(), "Type the name of the " + cm.identifyColor(color) + " powerup you want to discard; if you want to stop type 'end'", true);
            else
                powerupName = messageTo(p.getColor(), "Select the " + cm.identifyColor(color) + " powerup you want to discard; if you want to stop press End", true);
            if(p.getPh().hasPowerupColor(powerupName, cm.identifyColor(color))){
                powerupsToBeDiscarded.add(p.getPh().getPowerupDeck().getPowerup(powerupName, color));
                i++;
            }
            else if(powerupName.equals("end"))
                break;
            else
                messageTo(p.getColor(), ERRORSTRING, false);
        }
        if (getClientByColor(p.getColor()).isGuiInterface()){
            messageTo(p.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
            messageTo(p.getColor(), DISABLE_END, false);
        }
        return powerupsToBeDiscarded;
    }

    /**
     * Manages the payment of a targeting scope, asking the player which ammo he wants to use to pay
     *
     * @param p             a RealPlayer, the one which has to pay
     */
    public void paymentManagementTS(RealPlayer p){
        char[] cost = new char[1];
        if(getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_ALL, false);
            messageTo(p.getColor(), ENABLE_AMMO_CHOICE, false);
            if (p.getPb().getAmmo('b') == 0)
                messageTo(p.getColor(), DISABLE_PAY_WITH_AMMO, false);
            if (p.getPb().getAmmo('r') == 0)
                messageTo(p.getColor(), DISABLE_PAY_WITH_POWERUP, false);
            if (p.getPb().getAmmo('y') == 0)
                messageTo(p.getColor(), DISABLE_PAY_WITH_BOTH, false);
        }
        String choice = messageTo(p.getColor(), "Choose the color of the ammo you want to pay", true).toLowerCase();
        while(true){
            if(!choice.equals("blue") && !choice.equals("red") && !choice.equals("yellow"))
                choice = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
            else{
                cost[0] = choice.charAt(0);
                if (p.getPb().getAmmo(choice.charAt(0)) > 0)
                    break;
                else
                    choice = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
            }
        }
        p.getPb().payAmmo(cost);
        if (getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), DISABLE_AMMO_CHOICE, false);
    }

    /**
     * Manages the payment of the effects of a weapon while shooting
     *
     * @param p                     a RealPlayer, the one which has to pay
     * @param weaponName            a String, which represents the name of the weapon
     * @param effects               an array of String, which contains the effects the player decided to use
     */
    public void paymentEffectsCost(RealPlayer p, String weaponName, String[] effects) {
        Weapon w = p.getPh().getWeaponDeck().getWeapon(weaponName);
        if (!getClientByColor(p.getColor()).isGuiInterface())
            messageTo(p.getColor(), "Weapon detected : " + w.getName(), false);
        for (String e : effects) {
            if (e != null){
                if(!e.equals("base")){
                    w.pay(e, p);
                }
            }
        }
    }

    /**
     * Adds all the powerups contained in the first ArrayList in the second one
     *
     * @param temp                  an ArrayList of Powerup
     * @param finalArray            an ArrayList of Powerup, the one in which are the Powerups
     */
    private void insertPowerup(ArrayList<Powerup> temp, ArrayList<Powerup> finalArray){
        finalArray.addAll(temp);
    }

}