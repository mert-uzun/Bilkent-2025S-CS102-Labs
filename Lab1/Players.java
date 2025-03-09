import java.util.ArrayList;
import java.util.Random;

/**
 * This class is responsible for creating the players and their properties. 
 * It also contains the methods for the players to take turns, move, buy properties, 
 * sell properties, pay rent, build houses, and check if they are bankrupt.
 * 
 * @author Mert Uzun
 * Date: 2/19/2025
 */
public class Players {

    private boolean canPlayThisTurn;
    private boolean isPlayer;
    private String name;
    private String nameInitial;
    private int budget;
    private ArrayList<Properties> properties;
    private int currentTile;
    private int previousTile;
    private boolean isBankrupt;

    private static final Random random = new Random();
    
    /**
     * Constructor for the Players class. It creates a player with a name and a budget of 10 coins.
     * If the player is not a human player, it assigns a random name to the player.
     * @param isPlayer boolean variable about whether the player is a human player or not.
     * @param name name of the player, used explicitly if the player is a human player.
     */
    public Players(boolean isPlayer, String name){
        
        if (isPlayer) {
            this.name = name;
            
        }
        else{
            this.name = Integer.toString(random.nextInt(9) + 1);
        }

        this.isPlayer = isPlayer;
        this.nameInitial = this.name.substring(0,1);
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
            System.out.println("Player " + name + " is taking turn!");
            move();
        }
        else{
            System.out.println("Player " + name + " is in jail and can't play this turn!");
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
            this.budget += 3; //Passing the start tile
        }
        System.out.println("Player " + name + " rolled a/an " + roll + " and landed on tile with number " + currentTile);

        Map.getInstance().getTileByNum(previousTile).removePlayerFromTile(this);
        Map.getInstance().getTileByNum(currentTile).addPeopleOnThisTile(this);
        Map.getInstance().printMap();

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
            System.out.println("Player " + name + " is bankrupt and out of the game!");
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
            if (Map.getInstance().getTileByNum(currentTile).getPropertyPrice() == -1) {
                specialEvents();
            }
            else if (Map.getInstance().getTileByNum(currentTile).isOwned()) {
                if (Map.getInstance().getTileByNum(currentTile).getOwner() != this) {
                    if (isPlayer) {
                        askToSell();
                    }
                    else{
                        askToSellForBots();
                    }
                    payRent();
                    checkIfBankrupt();
                    if (isBankrupt) {
                        return;
                    }
                }
                else{
                    if (isPlayer) {
                        asktoBuildHousesOnOneOfAllTheirProperties();
                    }
                    else{
                        asktoBuildHousesOnOneOfAllTheirPropertiesForBots();
                    }
                }
            }
            else{
                if (isPlayer) {
                    askToDecideToBuy();
                }
                else{
                    askToDecideToBuyForBots();  
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
                Map.getInstance().getTileByNum(4).removePlayerFromTile(this);
            }
            else if (diceRoll == 4) {
                currentTile += 2;  
                landingConsequences();
                Map.getInstance().getTileByNum(4).removePlayerFromTile(this);
            }
            else if (diceRoll == 5) {
                increaseBudget(1);
                currentTile += 1;             
                landingConsequences();
                Map.getInstance().getTileByNum(4).removePlayerFromTile(this);
            }
            else if (diceRoll == 6) {
                increaseBudget(2);
                currentTile += 2;           
                landingConsequences();
                Map.getInstance().getTileByNum(4).removePlayerFromTile(this);
            }
        }
        else if (this.currentTile == 8) {
            increaseBudget(Main.players.size());
            for (Players player : Main.players) {
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
        checkIfBankrupt();
    }

    /**
     * This method increases the player's budget by the increasingNum.
     * @param increasingNum the amount of money that will be increased to the player's budget.
     */
    public void increaseBudget(int increasingNum){
        this.budget += increasingNum;
    }
    
    /**
     * This method adds a property to the player's properties.
     * @param newProperty the property that will be added to the player's properties.
     */
    public void addProperty(Properties newProperty){
        this.properties.add(newProperty);
    }

    /**
     * This method removes a property from the player's properties.
     * @param property the property that will be removed from the player's properties.
     */
    public void removeProperty(Properties property){
        this.properties.remove(property);
    }

    /**
     * This method is responsible for the player to pay rent to the owner of the property. 
     */
    public void payRent(){
        decreaseBudget(Map.getInstance().getTileByNum(currentTile).getRentPrice());
    }

    /**
     * This method is responsible for the player to decide whether to buy an unowned property or not.
     * If the player decides to buy the property, it calls the own method of the property.
     */
    public void askToDecideToBuy(){
        System.out.print("Player " + name + " landed on an unowned property. Do you want to own it? (Y/N) ");
        String answer = Main.scanner.nextLine();
        if (answer.equalsIgnoreCase("Y")) {
            Map.getInstance().getTileByNum(currentTile).own(this);
        }
        else{
            askToSell();
        }
    }

    /**
     * This method is responsible for a bot to decide whether to buy an unowned property or not.
     * If the bot decides to buy the property, it calls the own method of the property.
     */
    public void askToDecideToBuyForBots(){
        if (random.nextBoolean()) {
            Map.getInstance().getTileByNum(currentTile).own(this);
        }
        else{
            askToSellForBots();
        }
    }

    /**
     * This method is responsible for the player to decide whether to sell a property or not.
     * If the player decides to sell a property, it calls the sellProperty method of the property.
     */
    public void askToSell(){
        if (properties.size() > 0) {
            System.out.print("Do you want to sell one of your properties this turn? (Y/N) ");
            String answer = Main.scanner.nextLine();
            if (answer.equalsIgnoreCase("Y")) {
                System.out.println("Which property do you want to sell?");

                for (int i = 0; i < properties.size(); i++) {
                    System.out.println((i + 1) + "-" + properties.get(i).toString());
                }

                int propertyNum = Main.scanner.nextInt();
                properties.get(propertyNum - 1).sellProperty();
            }
            else{
                asktoBuildHousesOnOneOfAllTheirProperties();
            }
        }
        else{
            System.out.println("You don't have any properties to sell!");
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
        if (!flag) {
            asktoBuildHousesOnOneOfAllTheirPropertiesForBots();
        }
    }

    /**
     * This method is responsible for the player to decide whether to build houses on their property or not.
     * If the player decides to build houses, it calls the buildHouses method of the property.
     * 
     */
    public void asktoBuildHousesOnOneOfAllTheirProperties(){
        if (properties.size() > 0) {
            System.out.println("Do you want to build houses on one of your properties this turn? (Y/N) ");
            String answer = Main.scanner.nextLine();
            if (answer.equalsIgnoreCase("Y")) {
                for (int i = 0; i < properties.size(); i++) {
                    System.out.println((i + 1) + "-" + properties.get(i).toString());
                }
                System.out.println("Which property do you want to build houses on?");
                int propertyNum = Main.scanner.nextInt();  
                Main.scanner.nextLine(); // Consume the newline character
                properties.get(propertyNum - 1).buildHouses();
            }
        }
        else{
            System.out.println("You don't have any properties to build houses on!");
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
            System.out.println("You don't have any properties to build houses on!");
        }
    }

    /**
     * This method is responsible for returning all of the player's properties to the bank. Without any compensation.
     */
    public void returnAllProperties(){
        for(int i = properties.size() - 1; i >= 0; i--){
            properties.get(i).returnProperty();
            budget = -1; //To prevent the player from selling properties again, and guarantee that the player is bankrupt.
        }
    }

    //GETTERS AND SETTERS
    public String getName(){
        return name;
    }

    public String getNameInitial(){
        return nameInitial;
    }

    public int getBudget() {
        return budget;
    }

    public int getCurrentTile() {
        return currentTile;
    }

    public boolean isBankrupt(){
        return isBankrupt;
    }
}

