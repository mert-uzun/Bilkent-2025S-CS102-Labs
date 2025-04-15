import java.util.ArrayList;
import java.util.Random;
import javax.swing.SwingUtilities;

import java.awt.Color;

public class PlayersGUI {
    private boolean canPlayThisTurn;
    public boolean isPlayer;
    private String name;
    private int budget;
    public ArrayList<PropertiesGUI> properties;
    private int currentTile;
    private int previousTile;
    private boolean isBankrupt;
    private Color color;

    private static final Random random = new Random();
    
    /**
     * Constructor for the Players class. It creates a player with a name and a budget of 10 coins.
     * If the player is not a human player, it assigns a random name to the player.
     * @param isPlayer boolean variable about whether the player is a human player or not.
     * @param name name of the player, used explicitly if the player is a human player.
     */
    public PlayersGUI(boolean isPlayer, String name, Color color){
        this.color = color;
        this.name = name;
        this.isPlayer = isPlayer;
        this.budget = 10; //10 coins as starter
        this.properties = new ArrayList<>();
        this.isBankrupt = false;
        this.currentTile = 0;
        this.canPlayThisTurn = true;
    }

    /**
     * This method is responsible for the player to take a turn.
     * If the player is in jail, it skips the turn.
     * If the player is not in jail, it rolls a dice, moves the player to the new tile, and checks the consequences of landing on that tile.
     */
    public void takeTurn(){
        if (canPlayThisTurn) {
            MainGUI.logArea.append("\nPlayer " + name + " is taking turn!");
            move();
        }
        else{
            MainGUI.logArea.append("Player " + name + " is in jail and can't play this turn!");
            canPlayThisTurn = true;
        }
    }

    /**
     * This method is responsible for moving the player to a new tile.
     * It rolls a dice, adds the roll to the current tile number, and checks if the player passed the start tile.
     * If the player passed the start tile, it adds 3 coins to the player's budget.
     * It prints the roll number and the new tile number.
     * It adds the player to the new tile.
     * It prints the current state of the map.
     */
    public void move(){
        int roll = random.nextInt(6) + 1;
        this.previousTile = this.currentTile;
        this.currentTile += roll;
        
        if (this.currentTile > 15){
            this.currentTile -= 16;
        }

        MainGUI.logArea.append("Player " + name + " rolled a/an " + roll + " and landed.");

        PropertiesGUI prevTile = MainGUI.mapPanel.getTileByNum(this.previousTile);        
        if (prevTile != null) {
            prevTile.removePlayerFromTile(this);
        }

        PropertiesGUI currTile = MainGUI.mapPanel.getTileByNum(this.currentTile);

        if (currTile != null) {
            currTile.addPeopleOnThisTile(this);
        }

        SwingUtilities.invokeLater(() -> {
            MainGUI.mapPanel.redraw();
        });


        landingConsequences();
    }

    /**
     * This method checks if the player is bankrupt.
     * If the player is bankrupt, it prints a message and turns isBankrupt value to false.
     * If the player is not bankrupt, it continues the game.
     */
    public void checkIfBankrupt(){
        if (budget < 0) {
            isBankrupt = true;
            MainGUI.logArea.append("Player " + name + " is bankrupt and out of the game!");
            returnAllProperties();
            MainGUI.players.remove(this);
            MainGUI.mapPanel.getTileByNum(currentTile).removePlayerFromTile(this);
            MainGUI.mapPanel.redraw();
        }
    }

    /**
     * This method checks the consequences of landing on a tile.
     * If the tile is a special tile, it calls the specialEvents method.
     * If the tile is owned by another player, it calls the payRent method.
     * If the tile is unowned, it calls the askToDecideToBuy method.
     * If the tile is owned by the player, it calls the askToDecideForHouses method.
     * If the player is bankrupt, it calls the checkIfBankrupt method.
     * If the player is not bankrupt, it continues the game.
     */
    private void landingConsequences(){

        if (!isBankrupt) {
            if (MainGUI.mapPanel.getTileByNum(currentTile).getPropertyPrice() == -1) {
                specialEvents();
            }
            else if (MainGUI.mapPanel.getTileByNum(currentTile).isOwned()) {
                if (MainGUI.mapPanel.getTileByNum(currentTile).getOwner() != this) {
                    if (!isPlayer) {
                        askToSellForBots();
                    }

                    payRent();
                    checkIfBankrupt();

                    if (isBankrupt) {
                        return;
                    }
                }
                else{
                    if (!isPlayer) {
                        asktoBuildHousesOnOneOfAllTheirPropertiesForBots();
                    }
                }
            }
            else{
                if (!isPlayer){
                    if (budget > MainGUI.mapPanel.getTileByNum(currentTile).getPropertyPrice()) {
                        askToDecideToBuyForBots();
                    }
                    else{
                        askToSellForBots();
                    }
                }
            }
        }

        checkIfBankrupt();
    }

    /**
     * This method is responsible for the special events that happen when the player lands on a special tile.
     * If the player lands on the start tile, it adds 3 coins to the player's budget.
     * If the player lands on the chance tile, it rolls a dice and applies the consequences according to the dice roll.
     * If the player lands on the tax tile, it increases the player's budget by the number of players
     * in the game and decreases the budget of all other players by 1.
     * If the player lands on the jail tile, it skips the player's turn.
     */
    public void specialEvents(){
        if (this.currentTile == 0){
            this.budget += 3; //Landing right on the start tile{
            MainGUI.mapPanel.updateBudgetDisplay();
        }
        else if (this.currentTile == 4){
            int diceRoll = random.nextInt(6) + 1;
            if (diceRoll == 1) {
                decreaseBudget(2);
            }
            else if (diceRoll == 2) {
                decreaseBudget(1);
            }
            else if (diceRoll == 3) {
                currentTile += 1;
                landingConsequences();
                MainGUI.mapPanel.getTileByNum(4).removePlayerFromTile(this);
                MainGUI.mapPanel.getTileByNum(5).addPeopleOnThisTile(this);
            }
            else if (diceRoll == 4) {
                currentTile += 2;  
                landingConsequences();
                MainGUI.mapPanel.getTileByNum(4).removePlayerFromTile(this);
                MainGUI.mapPanel.getTileByNum(6).addPeopleOnThisTile(this);
            }
            else if (diceRoll == 5) {
                increaseBudget(1);
                currentTile += 1;             
                landingConsequences();
                MainGUI.mapPanel.getTileByNum(4).removePlayerFromTile(this);
                MainGUI.mapPanel.getTileByNum(5).addPeopleOnThisTile(this);
            }
            else if (diceRoll == 6) {
                increaseBudget(2);
                currentTile += 2;           
                landingConsequences();
                MainGUI.mapPanel.getTileByNum(4).removePlayerFromTile(this);
                MainGUI.mapPanel.getTileByNum(6).addPeopleOnThisTile(this);
            }
        }
        else if (this.currentTile == 8) {
            increaseBudget(MainGUI.players.size());
            for (PlayersGUI player : MainGUI.players) {
                player.decreaseBudget(1);
            }
        }
        else if (this.currentTile == 12) {
            this.canPlayThisTurn = false;
        }
    }

    /**
     * This method decreases the player's budget by the decreasingNum.
     * If the player's budget is less than 0, it calls the checkIfBankrupt method.
     * 
     * @param decreasingNum the amount of money that will be decreased from the player's budget.
     */
    public void decreaseBudget(int decreasingNum){
        this.budget -= decreasingNum;
        MainGUI.mapPanel.updateBudgetDisplay();
        checkIfBankrupt();
    }

    /**
     * This method increases the player's budget by the increasingNum.
     * @param increasingNum the amount of money that will be increased to the player's budget.
     */
    public void increaseBudget(int increasingNum){
        this.budget += increasingNum;
        MainGUI.mapPanel.updateBudgetDisplay();
    }
    
    /**
     * This method adds a property to the player's properties.
     * @param newProperty the property that will be added to the player's properties.
     */
    public void addProperty(PropertiesGUI newProperty){
        this.properties.add(newProperty);
    }

    /**
     * This method removes a property from the player's properties.
     * @param property the property that will be removed from the player's properties.
     */
    public void removeProperty(PropertiesGUI property){
        this.properties.remove(property);
    }

    /**
     * This method is responsible for the player to pay rent to the owner of the property. 
     */
    public void payRent(){
        decreaseBudget(MainGUI.mapPanel.getTileByNum(currentTile).getRentPrice());
        MainGUI.logArea.append(name + " paid " + MainGUI.mapPanel.getTileByNum(currentTile).getRentPrice() 
                                + " coins to " + MainGUI.mapPanel.getTileByNum(currentTile).getOwner().getName() + " for rent.");
    }

    /**
     * This method is responsible for a bot to decide whether to buy an unowned property or not.
     * If the bot decides to buy the property, it calls the own method of the property.
     */
    public void askToDecideToBuyForBots(){
        if (random.nextBoolean()) {
            MainGUI.mapPanel.getTileByNum(currentTile).own(this);
        }
        else{
            askToSellForBots();
        }
    }

    /**
     * This method is responsible for a bot to decide whether to sell a property or not.
     * If the bot decides to sell a property, it calls the sellProperty method of the property.
     */
    public void askToSellForBots(){
        boolean flag = false;
        if (properties.size() > 0) {
            if (budget < 5) {
                properties.get(random.nextInt(properties.size())).sellProperty();
                flag = true;
            }
        }
        if (!flag && !isPlayer) {
            asktoBuildHousesOnOneOfAllTheirPropertiesForBots();
        }
    }

    /**
     * This method is responsible for a bot to decide whether to build houses on their property or not.
     * If the bot decides to build houses, it calls the buildHouses method of the property.
     */
    public void asktoBuildHousesOnOneOfAllTheirPropertiesForBots(){
        if (properties.size() > 0) {
            if (budget > 5) {
                properties.get(random.nextInt(properties.size())).buildHouses();
            }
        }
        else{
            MainGUI.logArea.append("You don't have any properties to build houses on!");
        }
    }

    /**
     * This method is responsible for returning all of the player's properties to the bank. Without any compensation.
     */
    public void returnAllProperties(){
        for(int i = properties.size() - 1; i >= 0; i--){
            properties.get(i).returnProperty();
            budget = -1; //To prevent the player from selling properties again, and guarantee that the player is bankrupt.
            MainGUI.mapPanel.updateBudgetDisplay();
        }
    }

    //GETTERS AND SETTERS
    public String getName(){
        return name;
    }

    public int getBudget() {
        return budget;
    }

    public int getCurrentTile() {
        return currentTile;
    }

    public Color getColor(){
        return color;
    }

    public boolean isBankrupt(){
        return isBankrupt;
    }
}
