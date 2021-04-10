package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.clientmodel.RealPlayerClientModel;
import it.polimi.ingsw.model.clientmodel.TerminatorClientModel;
import it.polimi.ingsw.model.clientmodel.WeaponClient;

import java.util.ArrayList;

public class CLI {

    private Map map;
    private ArrayList<RealPlayerClientModel> players;
    private ArrayList<String> otherPlayers = new ArrayList<>();
    private TerminatorClientModel terminator;
    private boolean isTerminator;
    private String playerName;
    private String[] killShotTrack;
    private int[] playersPoint = {0, 0, 0, 0, 0};

    private final int FIRSTCOLUMN = 40;
    private final int SECONDCOLUMN = 92;
    private final int TOPSPACE = 49;



    public void plotCLI() {

        clearCLI();

        //MAP

        otherPlayers.clear();
        initOtherPlayers();

        int numVerticalSquare = map.getNumVerticalSquare();
        int numHorizontalSquare = map.getNumHorizontalSquare();
        int verticalSize = map.getVerticalSize();
        int horizontalSize = map.getHorizontalSize();

        System.out.print(Color.ANSI_WHITE.escape());

        //TOP BOX
        System.out.print("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");

        int countPlayer = 0;
        for (int r = 0; r < numVerticalSquare * verticalSize; r++) {

            System.out.println();
            System.out.flush();
            System.out.print(Color.ANSI_WHITE.escape());
            for (int c = 0; c < numHorizontalSquare * horizontalSize; c++) {
                System.out.print(map.getTiles()[r][c]);
                System.out.print(Color.ANSI_WHITE.escape());
            }
            if(countPlayer < otherPlayers.size())
                printScoreBoard(r % 5, otherPlayers.get(countPlayer));
            else
                System.out.print("                                                                            │");
            if(r % 5 == 4)
                countPlayer++;

        }
        System.out.println("");
        System.out.println("                                                                                                                                    │");

        //MAP BOX

        System.out.print(Color.ANSI_RESET.escape());
        System.out.flush();
        System.out.println("┌───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│                                       │                                                                                           │");

        //Killshot Track
        System.out.print("│  Killshot Track: ");
        int count = 19;
        for(int i = 0; i < killShotTrack.length; i++) {
            if(killShotTrack[i] == null) {
                System.out.print("\u001B[31m▊ ");
                count += 2;
            } else {
                pickColor(killShotTrack[i].charAt(0));
                System.out.print("▊ ");
                count += 2;
                resetColor();
            }
        }
        System.out.print(Color.ANSI_RESET.escape());
        fillFirstoColumn(count);

        //Red Spawnpoint
        System.out.print("│  Red Spawnpoint:     ");
        count = 23;
        for(WeaponClient w: map.getRedSpawnpoint()) {
            if (w != null) {
                if (map.getRedSpawnpoint().indexOf(w) != (map.getRedSpawnpoint().size() - 1)) {
                    System.out.print(w.getName() + ", ");
                    count += (w.getName().length() + 2);
                } else {
                    System.out.print(w.getName());
                    count += w.getName().length();
                }
            }
        }
        fillSecondColumn(count);
        System.out.println("│");

        //Overkills
        System.out.print("│  Overkills:      ");
        count = 19;
        for(int i = 0; i < killShotTrack.length; i++) {
            if(killShotTrack[i] == (null) || killShotTrack[i].length() == 1) {
                System.out.print("  ");
                count += 2;
            } else {
                pickColor(killShotTrack[i].charAt(1));
                System.out.print("▊ ");
                count += 2;
                resetColor();
            }
        }
        System.out.print(Color.ANSI_RESET.escape());
        fillFirstoColumn(count);

        //Blue Spawnpoint
        System.out.print("│  Blue Spawnpoint:    ");
        count = 23;
        for(WeaponClient w: map.getBlueSpawnpoint()) {
            if (w != null) {
                if (map.getBlueSpawnpoint().indexOf(w) != (map.getBlueSpawnpoint().size() - 1)) {
                    System.out.print(w.getName() + ", ");
                    count += (w.getName().length() + 2);
                } else {
                    System.out.print(w.getName());
                    count += w.getName().length();
                }
            }
        }
        fillSecondColumn(count);
        System.out.println("│");

        //Yellow Spawnpoint and Score

        System.out.print("│  Score: " + playersPoint[indexFromColor(getPlayer(playerName).getColor())]);
        if(playersPoint[indexFromColor(getPlayer(playerName).getColor())] < 10)
            fillFirstoColumn(11);
        else
            fillFirstoColumn(12);

        System.out.print("│  Yellow Spawnpoint:  ");
        count = 23;
        for(WeaponClient w: map.getYellowSpawnpoint()) {
            if (w != null) {
                if (map.getYellowSpawnpoint().indexOf(w) != (map.getYellowSpawnpoint().size() - 1)) {
                    System.out.print(w.getName() + ", ");
                    count += (w.getName().length() + 2);
                } else {
                    System.out.print(w.getName());
                    count += w.getName().length();
                }
            }
        }
        fillSecondColumn(count);

        System.out.println("│");


        //DIVIDER
        System.out.println("│                                       │                                                                                           │");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│                                       │                                                                                           │");


        //PLAYER BOX

        //Damages
        System.out.print("│  Damages: ");
        count = 12;

        for(int i = 0; i < getPlayer(playerName).getPlayerBoard().getDamages().length; i++) {
            switch(getPlayer(playerName).getPlayerBoard().getDamages()[i]) {
                case 'b':
                    System.out.print("\u001B[34m▊ ");
                    count += 2;
                    break;
                case 'e':
                    System.out.print("\u001B[32m▊ ");
                    count += 2;
                    break;
                case 'g':
                    System.out.print("\u001B[37m▊ ");
                    count += 2;
                    break;
                case 'v':
                    System.out.print("\u001B[35m▊ ");
                    count += 2;
                    break;
                case 'y':
                    System.out.print("\u001B[33m▊ ");
                    count += 2;
                    break;
            }
        }
        fillFirstoColumn(count);

        //Weapons
        System.out.print("│  Weapons:  ");
        count = 13;
        if(getPlayer(playerName).getPlayerHand().getWeapons().size() != 0) {
            for (WeaponClient w : getPlayer(playerName).getPlayerHand().getWeapons()) {
                if(getPlayer(playerName).getPlayerHand().getWeapons().indexOf(w) != (getPlayer(playerName).getPlayerHand().getWeapons().size() -1)) {
                    if(w.isLoaded()) {
                        System.out.print("● ");
                        count += 2;
                    }
                    System.out.print(w.getName() + ", ");
                    count += (w.getName().length() + 2);
                } else {
                    if(w.isLoaded()) {
                        System.out.print("● ");
                        count += 2;
                    }
                    System.out.print(w.getName());
                    count += w.getName().length();
                }
            }
        }
        fillSecondColumn(count);
        System.out.println("│");
        System.out.println("│                                       │                                                                                           │");

        //Marks
        System.out.print("│  Marks:   ");
        count = 12;

        for(int i = 0; i < getPlayer(playerName).getPlayerBoard().getMarkedDamages().length; i++) {
            for(int j = 0; j < getPlayer(playerName).getPlayerBoard().getMarkedDamages()[i]; j++) {
                count = pickMarkColor(i, count);
            }
        }
        fillFirstoColumn(count);

        //Powerup1
        System.out.print("│  Powerups: ");
        count = 13;

        if(getPlayer(playerName).getPlayerHand().getPowerups().size() != 0) {
            pickColor(getPlayer(playerName).getPlayerHand().getPowerups().get(0).getColor());
            System.out.print(getPlayer(playerName).getPlayerHand().getPowerups().get(0).getName());
            count += getPlayer(playerName).getPlayerHand().getPowerups().get(0).getName().length();
            System.out.print(Color.ANSI_RESET.escape());
        }
        fillSecondColumn(count);
        System.out.println("│");

        //Powerup2
        System.out.print("│                                       │            ");
        count = 13;
        if(getPlayer(playerName).getPlayerHand().getPowerups().size() > 1) {
            pickColor(getPlayer(playerName).getPlayerHand().getPowerups().get(1).getColor());
            System.out.print(getPlayer(playerName).getPlayerHand().getPowerups().get(1).getName());
            count += getPlayer(playerName).getPlayerHand().getPowerups().get(1).getName().length();
            System.out.print(Color.ANSI_RESET.escape());
        }
        fillSecondColumn(count);
        System.out.println("│");

        //Ammo
        System.out.print("│  Ammo:    ");
        count = 21;
        printAmmo(playerName);
        fillFirstoColumn(count);

        //Powerup3
        System.out.print("│            ");
        count = 13;
        if(getPlayer(playerName).getPlayerHand().getPowerups().size() > 2) {
            pickColor(getPlayer(playerName).getPlayerHand().getPowerups().get(2).getColor());
            System.out.print(getPlayer(playerName).getPlayerHand().getPowerups().get(2).getName());
            count += getPlayer(playerName).getPlayerHand().getPowerups().get(2).getName().length();
            System.out.print(Color.ANSI_RESET.escape());
        }
        fillSecondColumn(count);
        System.out.println("│");

        System.out.println("│                                       │                                                                                           │");
        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("");
        System.out.print(Color.ANSI_RESET.escape());

        return;
    }

    //PRINT METHODS

    private void clearCLI() {
        System.out.println("\033[H\033[2J");
    }

    private void printNameAndDamages(String name) {
        System.out.print("   ");
        pickColor(terminator.getColor());
        System.out.print("terminator:  ");
        System.out.print(Color.ANSI_RESET.escape());

        System.out.print("Damages:   ");
        int count = 0;
        for(int i = 0; i < terminator.getPlayerBoard().getDamages().length; i++) {
            switch(terminator.getPlayerBoard().getDamages()[i]) {
                case 'b':
                    System.out.print("\u001B[34m▊ ");
                    count += 2;
                    break;
                case 'e':
                    System.out.print("\u001B[32m▊ ");
                    count += 2;
                    break;
                case 'g':
                    System.out.print("\u001B[37m▊ ");
                    count += 2;
                    break;
                case 'v':
                    System.out.print("\u001B[35m▊ ");
                    count += 2;
                    break;
                case 'y':
                    System.out.print("\u001B[33m▊ ");
                    count += 2;
                    break;
            }
        }
        fillTopSpace(count);
        System.out.print("│");

    }

    private void printNameWeapon(String name) {
        if(getPlayer(name).getYourTurn()) {
            System.out.print(" Δ ");
        } else if(getPlayer(name).isDisconnected()) {
            System.out.print(Color.ANSI_RED.escape());
            System.out.print(" X ");
            System.out.print(Color.ANSI_RESET.escape());
        } else
            System.out.print("   ");
        pickColor(getPlayer(name).getColor());
        System.out.print(name);
        System.out.print(Color.ANSI_RESET.escape());
        int count = name.length();

        for(; count < 13; count++) {
            System.out.print(" ");
        }
        int i = 0;
        System.out.print(getPlayer(name).getPlayerHand().getWeapons().size() + " ");
        System.out.print("Weapons: ");
        if(getPlayer(name).getPlayerHand().getWeapons().size() != 0) {
            for (WeaponClient w : getPlayer(name).getPlayerHand().getWeapons()) {
                if(!w.isLoaded()) {
                    if (getPlayer(name).getPlayerHand().getWeapons().indexOf(w) != (getPlayer(name).getPlayerHand().getWeapons().size() - 1)) {
                        System.out.print(w.getName() + ", ");
                        i += w.getName().length() + 2;
                    } else {
                        System.out.print(w.getName());
                        i += w.getName().length();
                    }
                }
            }
        }
        fillTopSpace(i);
        System.out.print("│");
    }

    private void printCardsDamages(String name) {
        System.out.print("   ");
        System.out.print(getPlayer(name).getPlayerHand().getPowerups().size());
        System.out.print(" Cards      ");

        System.out.print("Damages:   ");
        int count = 0;
        for(int i = 0; i < getPlayer(name).getPlayerBoard().getDamages().length; i++) {
            switch(getPlayer(name).getPlayerBoard().getDamages()[i]) {
                case 'b':
                    System.out.print("\u001B[34m▊ ");
                    count += 2;
                    break;
                case 'e':
                    System.out.print("\u001B[32m▊ ");
                    count += 2;
                    break;
                case 'g':
                    System.out.print("\u001B[37m▊ ");
                    count += 2;
                    break;
                case 'v':
                    System.out.print("\u001B[35m▊ ");
                    count += 2;
                    break;
                case 'y':
                    System.out.print("\u001B[33m▊ ");
                    count += 2;
                    break;
            }
        }
        fillTopSpace(count);
        System.out.print("│");

    }

    private void printMarks(String name) {
        System.out.print("   ");
        int count = 0;
        if(name.equals("terminator")) {
            for(int i = 0; i < terminator.getPlayerBoard().getPointsToAssignSide1().length; i++) {
                if(terminator.getPlayerBoard().getPointsToAssignSide1()[i] != -1) {
                    System.out.print(terminator.getPlayerBoard().getPointsToAssignSide1()[i] + " ");
                    count += 2;
                }
            }
        } else {
            for (int i = 0; i < getPlayer(name).getPlayerBoard().getPointsToAssignSide1().length; i++) {
                if (getPlayer(name).getPlayerBoard().getPointsToAssignSide1()[i] != -1) {
                    System.out.print(getPlayer(name).getPlayerBoard().getPointsToAssignSide1()[i] + " ");
                    count += 2;
                }
            }
        }
        for(; count < 13; count++) {
            System.out.print(" ");
        }

        System.out.print("Marks:     ");
        count = 0;
        if(name.equals("terminator")) {
            for (int i = 0; i < terminator.getPlayerBoard().getMarkedDamages().length; i++) {
                for (int j = 0; j < terminator.getPlayerBoard().getMarkedDamages()[i]; j++) {
                    count = pickMarkColor(i, count);
                }
            }
        } else {
            for (int i = 0; i < getPlayer(name).getPlayerBoard().getMarkedDamages().length; i++) {
                for (int j = 0; j < getPlayer(name).getPlayerBoard().getMarkedDamages()[i]; j++) {
                    count = pickMarkColor(i, count);
                }
            }
        }
        fillTopSpace(count);
        System.out.print("│");

    }

    private void printAmmo(String name) {

        System.out.print("\u001B[34m▊");
        System.out.print(Color.ANSI_RESET.escape());
        System.out.print(getPlayer(name).getPlayerBoard().getAmmoBox()[0] + " ");
        System.out.print("\u001B[31m▊");
        System.out.print(Color.ANSI_RESET.escape());
        System.out.print(getPlayer(name).getPlayerBoard().getAmmoBox()[1] + " ");
        System.out.print("\u001B[33m▊");
        System.out.print(Color.ANSI_RESET.escape());
        System.out.print(getPlayer(name).getPlayerBoard().getAmmoBox()[2] + " ");

        System.out.print(Color.ANSI_RESET.escape());

    }

    private void printScoreBoard(int n, String name) {
        switch(n) {
            case 1:
                if(name.equals("terminator"))
                    printNameAndDamages("terminator");
                else
                    printNameWeapon(name);
                break;
            case 2:
                if(name.equals("terminator"))
                    printMarks("terminator");
                else
                    printCardsDamages(name);
                break;
            case 3:
                if(!name.equals("terminator"))
                    printMarks(name);
                else
                    System.out.print("                                                                            │");
                break;
            case 4:
                if(!name.equals("terminator")) {
                    System.out.print("                Ammo:      ");
                    printAmmo(name);
                    fillTopSpace(9);
                    System.out.print("│");
                } else
                    System.out.print("                                                                            │");
                break;
            default:
                System.out.print("                                                                            │");
                break;
        }
    }

    private void fillFirstoColumn(int count) {
        System.out.print(Color.ANSI_RESET.escape());
        for(; count < FIRSTCOLUMN; count++) {
            System.out.print(" ");
        }
    }

    private void fillSecondColumn(int count) {
        System.out.print(Color.ANSI_RESET.escape());
        for(; count < SECONDCOLUMN; count++) {
            System.out.print(" ");
        }
    }

    private void fillTopSpace(int count) {
        System.out.print(Color.ANSI_RESET.escape());
        for(; count < TOPSPACE; count++) {
            System.out.print(" ");
        }
    }

    //CHECK

    private void initOtherPlayers() {
        for(RealPlayerClientModel p: players) {
            if(!playerName.equals(p.getPlayerName())){
                otherPlayers.add(p.getPlayerName());
            }
        }
        if(isTerminator)
            otherPlayers.add("terminator");
    }

    private RealPlayerClientModel getPlayer(String name) {
        for(RealPlayerClientModel p: players) {
            if(p.getPlayerName().equals(name))
                return p;
        }
        return null;
    }

    private void pickColor(char color) {
        switch(color) {
            case 'b':
                System.out.print(Color.ANSI_BLUE.escape());
                break;
            case 'e':
                System.out.print(Color.ANSI_GREEN.escape());
                break;
            case 'g':
                System.out.print(Color.ANSI_WHITE.escape());
                break;
            case 'v':
                System.out.print(Color.ANSI_PURPLE.escape());
                break;
            case 'r':
                System.out.print(Color.ANSI_RED.escape());
                break;
            case 'y':
                System.out.print(Color.ANSI_YELLOW.escape());
                break;
        }
    }

    private void resetColor() {
        System.out.print(Color.ANSI_RESET.escape());
    }

    private int pickMarkColor(int i, int count) {
        switch (i) {
            case 0:
                System.out.print("\u001B[34m▊ ");
                count += 2;
                break;
            case 1:
                System.out.print("\u001B[32m▊ ");
                count += 2;
                break;
            case 2:
                System.out.print("\u001B[37m▊ ");
                count += 2;
                break;
            case 3:
                System.out.print("\u001B[35m▊ ");
                count += 2;
                break;
            default:
                System.out.print("\u001B[33m▊ ");
                count += 2;
                break;
        }
        return count;
    }

    private int indexFromColor(char color) {
        switch(color) {
            case 'b':
                return 0;
            case 'e':
                return 1;
            case 'g':
                return 2;
            case 'v':
                return 3;
            case 'y':
                return 4;
        }
        return -1;
    }

    //GETTER

    public Map getMap() {
        return map;
    }

    //SETTER

    public void setTerminator(TerminatorClientModel terminator) {
        this.terminator = terminator;
    }

    public void setTerminator(boolean terminator) {
        isTerminator = terminator;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayers(ArrayList<RealPlayerClientModel> players) {
        this.players = players;
    }

    public void setOtherPlayers(ArrayList<String> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    public void setKillShotTrack(String[] killShotTrack) {
        this.killShotTrack = killShotTrack;
    }

    public void setPlayersPoint(int[] playersPoint) {
        this.playersPoint = playersPoint;
    }
}
