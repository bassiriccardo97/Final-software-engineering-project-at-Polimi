package it.polimi.ingsw.controller;

import it.polimi.ingsw.connections.Server;
import it.polimi.ingsw.model.card.powerup.Powerup;
import it.polimi.ingsw.model.exceptions.WrongValueException;
import it.polimi.ingsw.model.game.Board;
import it.polimi.ingsw.model.game.squares.GenericSquare;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.player.Terminator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import static it.polimi.ingsw.controller.GameController.tryParse;

public class CardsManagement extends Server {

    private static final String ERRORSTRING = "Wrong choice, please try again";
    private static final String DISABLE_USE_PU = "DISABLE_USE_PU";
    private static final String ENABLE_SQUARE = "ENABLE_SQUARE";
    private static final String DISABLE_SQUARE = "DISABLE_SQUARE";
    private static final String ENABLE_SELECT_PLAYER = "ENABLE_SELECT_PLAYER";
    private static final String DISABLE_SELECT_PLAYER = "DISABLE_SELECT_PLAYER";
    private static final String ENABLE_SELECT_POWERUP_HAND = "ENABLE_SELECT_POWERUP_HAND";
    private static final String DISABLE_SELECT_POWERUP_HAND = "DISABLE_SELECT_POWERUP_HAND";
    private static final String ENABLE_USE_PU = "ENABLE_USE_PU";
    private static final String ENABLE_END = "ENABLE_END";
    private static final String DISABLE_END = "DISABLE_END";
    private static final String DISABLE_ALL = "DISABLE_ALL";
    private static final String ONLY_NAME = "ONLY_NAME";
    private static final String ONLY_COLOR = "ONLY_COLOR";
    private PaymentManagement pm = new PaymentManagement();
    private String response;
    //private static RealPlayer playerTB;

    /**
     * Manages the use of the tagback grenade, giving the opportunity to the previous victims to use it against the attacker
     *
     * @param p                     a Player, which is the previous attacker
     * @param victims               am ArrayList of RealPlayer, which contains all the previous victims of the attacker (except the Terminator)
     */
    /*public void revengePowerup(Player p, ArrayList<RealPlayer> victims) {
        //p is the new victim, the one who has attacked before
        if (p.getColor() != getTerminatorColor())
            messageTo(p.getColor(), "Wait for other players possible choices...", false);
        else {
            Terminator t = (Terminator) p;
            messageTo(t.getOwnerColor(), "Wait for other players possible choices...", false);
        }
        //Detecting all RealPlayers (no terminator)
        int count = 0;
        for (RealPlayer player : victims) {
            if (player.getPh().hasPowerup("tagback grenade"))
                count++;
        }
        if (count == 0) {
            int count2 = 0;
            for (RealPlayer rp : victims) {
                if (rp.getPh().getPowerupDeck().getSize() == 0)
                    count2++;
            }
            if (count2 == victims.size())
                return;
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(new Callable() {
                @Override
                public String call() throws Exception {
                    while (true) {

                    }
                }
            });
            try {
                future.get(10000, TimeUnit.MILLISECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                if (getClientByColor(p.getColor()).isTurnOver())
                    return;
                if (p.getColor() != getTerminatorColor())
                    messageTo(p.getColor(), "Nothing has happened...yet", false);
                else {
                    Terminator t = (Terminator) p;
                    messageTo(t.getOwnerColor(), "Nothing has happened...yet", false);
                }
                future.cancel(true);
            }
            executor.shutdown();
        } else {
            for (RealPlayer player : victims) {
                if (player.getPh().hasPowerup("tagback grenade")) {
                    player.setTurn(true);
                    sendModelRMX();
                    if (getClientByColor(player.getColor()).isGuiInterface()) {
                        messageTo(player.getColor(), ENABLE_USE_PU, false);
                        messageTo(player.getColor(), ENABLE_END, false);
                    }
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    final Future<String> future = executor.submit(new Callable() {
                        @Override
                        public String call() throws Exception {
                            while (true) {
                                if (!(getClientByColor(player.getColor()).isGuiInterface()))
                                    response = messageTo(player.getColor(), "Since another player has damaged you, type '4' if you want to use your tagback grenade, otherwise type 'end'", true);
                                else
                                    response = messageTo(player.getColor(), "Since another player has damaged you, press 'Use PU' if you want to use your tagback grenade, otherwise press End", true);
                                if (response.equals("4") || response.equals("end"))
                                    break;
                                else
                                    messageTo(player.getColor(), ERRORSTRING, false);
                            }
                            messageTo(player.getColor(), DISABLE_ALL, false);
                            if (response.equals("end")){
                                messageTo(player.getColor(), "Choice selected", false);
                                while(true){

                                }
                            }
                            else {
                                validChoiceRevengeManagement(p, player, "tagback grenade");
                                player.setTurn(false);
                                sendModel();
                            }
                            return messageTo(player.getColor(), "", false);
                        }
                    });
                    try {
                        future.get(15000, TimeUnit.MILLISECONDS);
                    } catch (ExecutionException | InterruptedException | TimeoutException e) {
                        if(getClientByColor(p.getColor()).isTurnOver())
                            return;
                        if(p.getColor() != getTerminatorColor())
                            messageTo(p.getColor(), "Nothing has happened...yet", false);
                        else{
                            Terminator t = (Terminator) p;
                            messageTo(t.getOwnerColor(), "Nothing has happened...yet", false);
                        }
                        if(getClientByColor(player.getColor()).isGuiInterface()){
                            messageTo(player.getColor(), DISABLE_USE_PU, false);
                            messageTo(player.getColor(), DISABLE_END, false);
                        }
                        future.cancel(true);
                    }
                    player.setTurn(false);
                    executor.shutdown();
                }
            }
        }
    }*/
    public void revengePowerup(Player p, ArrayList<RealPlayer> victims) {
        //p is the new victim, the one who has attacked before
        if(p.getColor() != getTerminatorColor())
            messageTo(p.getColor(), "Wait for other players possible choices...", false);
        else{
            Terminator t = (Terminator) p;
            messageTo(t.getOwnerColor(), "Wait for other players possible choices...", false);
        }
        //Detecting all RealPlayers (no terminator)
        int count = 0;
        for(RealPlayer player : victims) {
            if (player.getPh().hasPowerup("tagback grenade"))
                count++;
        }
        if(count == 0){
            int count2 = 0;
            for(RealPlayer rp : victims){
                if(rp.getPh().getPowerupDeck().getSize() == 0)
                    count2++;
            }
            if(count2 == victims.size())
                return;
            ExecutorService executor = Executors.newSingleThreadExecutor();
            final Future<String> future = executor.submit(new Callable() {
                @Override
                public String call() throws Exception {
                    while(true){
                    }
                }
            });
            try {
                future.get(10000, TimeUnit.MILLISECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                if(p.getColor() != getTerminatorColor())
                    messageTo(p.getColor(), "Nothing has happened...yet", false);
                else{
                    Terminator t = (Terminator) p;
                    messageTo(t.getOwnerColor(), "Nothing has happened...yet", false);
                }
                future.cancel(true);
            }
            executor.shutdown();
        }
        else{
            for (RealPlayer player : victims) {
                if (player.getPh().hasPowerup("tagback grenade")) {
                    player.setTurn(true);
                    //playerTB = player;
                    //System.out.println(playerTB.getColor());
                    sendModelRMX();
                    if (getClientByColor(player.getColor()).isGuiInterface()) {
                        messageTo(player.getColor(), ENABLE_USE_PU, false);
                        messageTo(player.getColor(), ENABLE_END, false);
                    }
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    final Future<String> future = executor.submit(new Callable() {
                        @Override
                        public String call() throws Exception {
                            while (true) {
                                if (!(getClientByColor(player.getColor()).isGuiInterface()))
                                    response = messageTo(player.getColor(), "Since another player has damaged you, type '4' if you want to use your tagback grenade, otherwise type 'end'", true);
                                else
                                    response = messageTo(player.getColor(), "Since another player has damaged you, press 'Use PU' if you want to use your tagback grenade, otherwise press End", true);
                                if (response.equals("4") || response.equals("end"))
                                    break;
                                else
                                    messageTo(player.getColor(), ERRORSTRING, false);
                            }
                            messageTo(player.getColor(), DISABLE_ALL, false);
                            if (response.equals("end")){
                                messageTo(player.getColor(), "Choice selected", false);
                                while(true){

                                }
                            }
                            else {
                                validChoiceRevengeManagement(p, player, "tagback grenade");
                                player.setTurn(false);
                                sendModel();
                            }
                            return messageTo(player.getColor(), "", false);
                        }
                    });
                    try {
                        future.get(15000, TimeUnit.MILLISECONDS);
                    } catch (ExecutionException | InterruptedException | TimeoutException e) {
                        if(p.getColor() != getTerminatorColor())
                            messageTo(p.getColor(), "Nothing has happened...yet", false);
                        else{
                            Terminator t = (Terminator) p;
                            messageTo(t.getOwnerColor(), "Nothing has happened...yet", false);
                        }
                        if(getClientByColor(player.getColor()).isGuiInterface()){
                            messageTo(player.getColor(), DISABLE_USE_PU, false);
                            messageTo(player.getColor(), DISABLE_END, false);
                        }
                        future.cancel(true);
                    }
                    player.setTurn(false);
                    executor.shutdown();
                }
            }
        }
    }


    /**
     * Manages the case in which one of the previous victims of the attacker decides to use his tagback grenade, asking him which one he wants to use if he has more than one
     *
     * @param p                     a Player, which is the previous attacker
     * @param player                a RealPlayer, which is one of the previous victims which has decided to use his tagback grenade
     * @param powerupName           a String, the name of the powerup
     */
    public void validChoiceRevengeManagement(Player p, RealPlayer player, String powerupName) {
        boolean valid = false;
        String c = null;
        if (p.getColor() != Server.getTerminatorColor() && !getClientByColor(p.getColor()).isGuiInterface())
            messageTo(player.getColor(), "Choice selected", false);
        while (!valid) {
            boolean end = false;
            Powerup pu = player.getPh().singlePowerupDetector("tagback grenade");
            if(pu != null){
                powerupName = pu.getName();
                c = identifyColor(pu.getColor());
            }
            else {
                if(p.getColor() != Server.getTerminatorColor() && getClientByColor(p.getColor()).isGuiInterface()) {
                    messageTo(player.getColor(), DISABLE_ALL, false);
                    messageTo(player.getColor(), ENABLE_SELECT_POWERUP_HAND, false);
                }
                while (!end) {
                    if (p.getColor() != Server.getTerminatorColor() && !getClientByColor(player.getColor()).isGuiInterface())
                        c = messageTo(player.getColor(), "Choose your tagback grenade color", true).toLowerCase();
                    else if (p.getColor() != Server.getTerminatorColor()) {
                        messageTo(player.getColor(), ONLY_COLOR, false);
                        c = messageTo(player.getColor(), "Choose your tagback grenade", true).toLowerCase();
                    }
                    if (!c.equals("yellow") && !c.equals("blue") && !c.equals("red"))
                        messageTo(player.getColor(), ERRORSTRING, false);
                    else {
                        if (player.getPh().hasPowerupColor(powerupName, c)) {
                            messageTo(player.getColor(), "Choice selected", false);
                            end = true;
                        }
                        else
                            messageTo(player.getColor(), ERRORSTRING, false);
                    }
                }
                if(p.getColor() != Server.getTerminatorColor() && getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(player.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
            }
            ArrayList<Player> targets = new ArrayList<>(1);
            targets.add(p);
            try {
                player.getPh().getPowerupDeck().getPowerup(powerupName, c.charAt(0)).doEffect(player, targets, null);
                player.setTurn(false);
                sendModel();
                messageTo(player.getColor(), "Target marked", false);
                messageTo(p.getColor(), "You've been marked!", false);
                player.getPh().discard(player.getPh().getPowerupDeck().getPowerup(powerupName, c.charAt(0)));
                valid = true;
            } catch (WrongValueException wVE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return;
                messageTo(p.getColor(), ERRORSTRING, false);
            }
        }
        if(getClientByColor(p.getColor()).isGuiInterface()) {
            messageTo(p.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
            messageTo(p.getColor(), DISABLE_SELECT_PLAYER, false);
        }
    }

    /**
     * Manages the use of the targeting scope, giving the opportunity to the attacker to use it after his shoot
     *
     * @param p                 a RealPlayer, which is the previous attacker
     * @param victims           a List of Player, which contains all the previous victims
     */
    public void pay1Powerup(RealPlayer p, List<Player> victims) {
        String powerupName;
        char color;
        if(!p.getPh().hasPowerup("targeting scope"))
            return;
        int choice = 0;
        if (getClientByColor(p.getColor()).isGuiInterface()){
            messageTo(p.getColor(), DISABLE_ALL, false);
            messageTo(p.getColor(), ENABLE_USE_PU, false);
            messageTo(p.getColor(), ENABLE_END, false);
        }
        while (true){
            String choice2;
            if (!getClientByColor(p.getColor()).isGuiInterface())
                choice2 = messageTo(p.getColor(), "If you want to use your targeting scope, type '4'; otherwise type 'end'", true);
            else
                choice2 = messageTo(p.getColor(), "If you want to use your targeting scope, press 'Use PU'; otherwise press End", true);
            if(!choice2.equals("end")){
                try{
                    choice = tryParse(choice2);
                    if(choice != 4)
                        messageTo(p.getColor(), ERRORSTRING, false);
                }
                catch(WrongValueException wVE){
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return;
                    messageTo(p.getColor(), ERRORSTRING, false);
                }
            }
            if(choice == 4){
                if (!getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), "Choice selected", false);
                break;
            }
            else {
                if (!getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), "Choice selected", false);
                return;
            }
        }
        if (getClientByColor(p.getColor()).isGuiInterface()){
            messageTo(p.getColor(), DISABLE_USE_PU, false);
            messageTo(p.getColor(), DISABLE_END, false);
        }
        boolean end = false;
        while (!end) {
            Powerup pu = p.getPh().singlePowerupDetector("targeting scope");
            if(pu != null){
                powerupName = pu.getName();
                color = pu.getColor();
            }
            else{
                if(getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), ENABLE_SELECT_POWERUP_HAND, false);
                powerupName = "targeting scope";
                color = getPay1Color(p, "targeting scope");
                if(getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
            }
            ArrayList<Player> targets = new ArrayList<>();
            if(victims.size() > 1) {
                if(getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), ENABLE_SELECT_PLAYER, false);
                getPay1Target(p, victims, targets);
                if(getClientByColor(p.getColor()).isGuiInterface())
                    messageTo(p.getColor(), DISABLE_SELECT_PLAYER, false);
            }
            else
                targets.add(victims.get(0));
            try {
                pm.paymentManagementTS(p);
                p.getPh().getPowerupDeck().getPowerup(powerupName, color).doEffect(p, targets, null);
                messageTo(p.getColor(), "Target damaged", false);
                p.getPh().discard(p.getPh().getPowerupDeck().getPowerup(powerupName, color));
                end = true;
            } catch (WrongValueException wVE) {
                if(getClientByColor(p.getColor()).isTurnOver())
                    return;
                messageTo(p.getColor(), ERRORSTRING, false);
            }
        }
    }

    /**
     * Manages the case in which the previous attacker decides to use his targeting scope, and asks him which of his he wants to use
     *
     * @param p                     a RealPlayer, which is the previous attacker
     * @param powerupName           an ArrayList of Player, which contains all the previous victims
     * @return                      a char, which represents the color
     */
    public char getPay1Color(RealPlayer p, String powerupName) {
        boolean found = false;
        String color;
        if (!getClientByColor(p.getColor()).isGuiInterface())
            color = messageTo(p.getColor(), "Choose the color of your targeting scope", true).toLowerCase();
        else {
            messageTo(p.getColor(), ONLY_COLOR, false);
            color = messageTo(p.getColor(), "Choose your targeting scope", true).toLowerCase();
        }
        while (!found) {
            found = p.getPh().hasPowerupColor(powerupName, color);
            if (!found)
                color = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
        }
        return color.charAt(0);
    }

    /**
     * Manages the case in which the previous attacker decides to use his targeting scope, and asks him which of the previous victims (if they are more than 1) he wants to damage
     *
     * @param p                     a RealPlayer, which is the previous attacker
     * @param victims               a List of Player, which contains all the previous victims
     * @param targets               an ArrayList of Player, in which is added the victim of the targeting scope
     */
    public void getPay1Target(RealPlayer p, List<Player> victims, ArrayList<Player> targets) {
        String c;
        while (true) {
            if (!getClientByColor(p.getColor()).isGuiInterface())
                c = messageTo(p.getColor(), "Choose the color of your target", true).toLowerCase();
            else
                c = messageTo(p.getColor(), "Choose your target", true).toLowerCase();

            if (!c.equals("blue") && !c.equals("emerald") && !c.equals("grey") && !c.equals("yellow") && !c.equals("red") && !c.equals("violet"))
                messageTo(p.getColor(), ERRORSTRING, false);
            else{
                while (true) {
                    for (Player player : victims) {
                        if (player.getJustDamaged() && player.getColor() == c.charAt(0) && victims.contains(player)) {
                            targets.add(player);
                            return;
                        }
                    }
                    messageTo(p.getColor(), ERRORSTRING, false);
                }
            }
        }
    }

    /**
     * Manages the case in which the player decides to use one of his powerups, which can be used anytime (teleporter or newton)
     *
     * @param p                         a RealPlayer, which is the specific player
     * @param actionFigures             a List of Player, which contains all the other players in game
     */
    public void usablePowerup(RealPlayer p, List<Player> actionFigures) {
        boolean end = false;
        while (true) {
            String powerupName = null;
            char color = '\u0000';
            int count = 0;
            for(Powerup pu : p.getPh().getPowerupDeck().getPowerups()){
                if(pu.isAlwaysUsable())
                    count++;
            }
            if(count == 1){
                for(Powerup pu : p.getPh().getPowerupDeck().getPowerups()){
                    if(pu.isAlwaysUsable()){
                        powerupName = pu.getName();
                        color = pu.getColor();
                    }
                }
            }
            else{
                if(getClientByColor(p.getColor()).isGuiInterface()){
                    messageTo(p.getColor(), ENABLE_SELECT_POWERUP_HAND, false);
                    messageTo(p.getColor(), ONLY_NAME, false);
                }
                while (true) {
                    powerupName = messageTo(p.getColor(), "Choose the powerup", true).toLowerCase();
                    if (!p.getPh().hasPowerup(powerupName))
                        messageTo(p.getColor(), ERRORSTRING, false);
                    else
                        break;
                }
                color = powerupChoice(p, powerupName);
            }
            Powerup pu = p.getPh().getPowerupDeck().getPowerup(powerupName, color);
            if(getClientByColor(p.getColor()).isGuiInterface())
                messageTo(p.getColor(), DISABLE_SELECT_POWERUP_HAND, false);
            ArrayList<Player> targets = new ArrayList<>();
            if (pu.isAlwaysUsable()) {
                if (pu.isVictim()) {
                    if(getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), ENABLE_SELECT_PLAYER, false);
                    String c;
                    while(true){
                        if(!getClientByColor(p.getColor()).isGuiInterface())
                            c = messageTo(p.getColor(), "Choose the victim color", true).toLowerCase();
                        else
                            c = messageTo(p.getColor(), "Choose the victim", true).toLowerCase();
                        if(!c.equals("blue") && !c.equals("emerald") && !c.equals("grey") && !c.equals("violet") && !c.equals("yellow"))
                            messageTo(p.getColor(), ERRORSTRING, false);
                        else{
                            while (!end) {
                                try {
                                    getPlayerOfColor(c, actionFigures, targets);
                                }catch (WrongValueException wVE) {
                                    if(getClientByColor(p.getColor()).isTurnOver())
                                        return;
                                    messageTo(p.getColor(), ERRORSTRING, false);
                                }
                                end = true;
                            }
                        }
                        if(end)
                            break;
                    }
                    if(getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), DISABLE_SELECT_PLAYER, false);
                }
                ArrayList<GenericSquare> s = new ArrayList<>();
                if (pu.isSquare()) {
                    if(getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), ENABLE_SQUARE, false);
                    String choice;
                    if(!getClientByColor(p.getColor()).isGuiInterface())
                        choice = messageTo(p.getColor(), "Choose a square number", true);
                    else
                        choice = messageTo(p.getColor(), "Choose a square", true);
                    end = false;
                    while (!end) {
                        try {
                            while (true) {
                                try {
                                    getGenericSquare(tryParse(choice), s);
                                    break;
                                } catch (WrongValueException wVE) {
                                    if(getClientByColor(p.getColor()).isTurnOver())
                                        return;
                                    throw new WrongValueException();
                                }
                            }
                            end = true;
                        } catch (WrongValueException wVE) {
                            if(getClientByColor(p.getColor()).isTurnOver())
                                return;
                            choice = messageTo(p.getColor(), ERRORSTRING, true);
                        }
                    }
                    if(getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), DISABLE_SQUARE, false);
                }
                try {
                    p.getPh().getPowerupDeck().getPowerup(powerupName, color).doEffect(p, targets, s);
                    p.getPh().discard(p.getPh().getPowerupDeck().getPowerup(powerupName, color));
                    if(!getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), "Powerup used", false);
                    if(getClientByColor(p.getColor()).isGuiInterface())
                        messageTo(p.getColor(), DISABLE_USE_PU, false);
                    sendModelRMX();
                    break;
                } catch (WrongValueException wVE) {
                    if(getClientByColor(p.getColor()).isTurnOver())
                        return;
                    messageTo(p.getColor(), ERRORSTRING, false);
                }
            } else
                messageTo(p.getColor(), ERRORSTRING, false);
        }
    }

    /**
     * Manages which of the always-usable-powerup the player wants to use
     *
     * @param p                     a RealPlayer, which is the specific player
     * @param powerupName           a String, which is the name of the specific powerup
     * @return                      a char, which is the specifi powerup color
     */
    public char powerupChoice(RealPlayer p, String powerupName) {
        boolean end = false;
        while (!end) {
            if (p.getPh().usablePowerupsDetector(powerupName))
                end = true;
            else
                powerupName = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
        }
        String color;
        if ((p.getPh().getPowerupDeck().getSize() == 2 && p.getPh().getPowerupDeck().getPowerups().get(0).getName().equals(p.getPh().getPowerupDeck().getPowerups().get(1).getName())) || (p.getPh().getPowerupDeck().getSize() == 3 && (p.getPh().getPowerupDeck().getPowerups().get(1).getName().equals(p.getPh().getPowerupDeck().getPowerups().get(2).getName()) || p.getPh().getPowerupDeck().getPowerups().get(0).getName().equals(p.getPh().getPowerupDeck().getPowerups().get(1).getName()) || p.getPh().getPowerupDeck().getPowerups().get(0).getName().equals(p.getPh().getPowerupDeck().getPowerups().get(2).getName())))) {
            if(!getClientByColor(p.getColor()).isGuiInterface())
                color = messageTo(p.getColor(), "Choose the powerup color", true).toLowerCase();
            else {
                messageTo(p.getColor(), ONLY_COLOR, false);
                color = messageTo(p.getColor(), "Choose the powerup", true).toLowerCase();
            }
            end = false;
            while (!end) {
                if (p.getPh().hasPowerupColor(powerupName, color)) {
                    end = true;
                } else
                    color = messageTo(p.getColor(), ERRORSTRING, true).toLowerCase();
            }
            return color.charAt(0);
        }
        char c = '\u0000';
        for (Powerup pu : p.getPh().getPowerupDeck().getPowerups())
            if (pu.getName().equals(powerupName))
                c = pu.getColor();
        return c;
    }

    /**
     * Adds a specific player to an arraylist
     *
     * @param victimColor               a String, the color of the player which is looked for
     * @param actionFigures             a List of Player, which contains all the other players in game
     * @param victims                   an ArrrayList of Player, in which is added the player which is looked for
     * @throws WrongValueException      if there isn't a player of the specific color
     */
    public void getPlayerOfColor(String victimColor, List<Player> actionFigures, ArrayList<Player> victims) throws WrongValueException {
        if (!victimColor.equals("blue") && !victimColor.equals("emerald") && !victimColor.equals("violet") && !victimColor.equals("grey") && !victimColor.equals("yellow"))
            throw new WrongValueException();
        else {
            for (Player p : actionFigures) {
                if (p.getColor() == victimColor.charAt(0)) {
                    victims.add(p);
                    return;
                }
            }
            throw new WrongValueException();
        }
    }

    /**
     * Checks if there is a player in game of a specific color
     *
     * @param victimColor               a String, which is the specific color
     * @param players                   an ArrayList of Player, which contains all the other RealPlayers in game
     * @return                          a boolean
     */
    public boolean hasRealPlayerOfColor(String victimColor, ArrayList<RealPlayer> players) {
        if (!victimColor.equals("blue") && !victimColor.equals("emerald") && !victimColor.equals("violet") && !victimColor.equals("grey") && !victimColor.equals("yellow"))
            return false;
        else {
            for (RealPlayer p : players) {
                if (p.getColor() == victimColor.charAt(0)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns a RealPlayer of a specific color
     *
     * @param victimColor               a String, which is the specific color
     * @param players                   an ArrayList of RealPlayers, which contains all the RealPlayers in game
     * @return                          a RealPlayer
     * @throws WrongValueException      if there isn't a RealPlayer of this specific color in game
     */
    public RealPlayer getRealPlayerOfColor(String victimColor, ArrayList<RealPlayer> players) throws WrongValueException {
        if (hasRealPlayerOfColor(victimColor, players)) {
            for (RealPlayer p : players) {
                if (p.getColor() == victimColor.charAt(0)) {
                    return p;
                }
            }
            throw new WrongValueException();
        } else
            throw new WrongValueException();
    }

    /**
     * Adds a specific GenericSqaure to an ArrayList
     *
     * @param number                    the number of the GenericSquare
     * @param s                         an ArrayList of GenericSquare, in which is added the specific GenericSquare
     * @throws WrongValueException      if there isn't a GenericSquare of that number
     */
    public void getGenericSquare(int number, ArrayList<GenericSquare> s) throws WrongValueException {
        if (number == 2 || number == 4 || number == 11)
            s.add(Board.getSpawnpoint(number));
        else {
            if (number == 0 || number == 1 || number == 3 || number == 5 || number == 6 || number == 7 || number == 8 || number == 9 || number == 10)
                s.add(Board.getSquare(number));
            else
                throw new WrongValueException();
        }
    }

    /**
     * Returns the specific color identified by a char
     *
     * @param c             a char, which represents the color
     * @return              a String, which is the color
     */
    public String identifyColor(char c) {
        if (c == 'b')
            return "blue";
        if (c == 'r')
            return "red";
        if (c == 'y')
            return "yellow";
        if (c == 'e')
            return "emerald";
        if (c == 'g')
            return "grey";
        return "violet";
    }

}