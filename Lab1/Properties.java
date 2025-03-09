/**
 * This class is responsible for creating the properties of the game. 
 * It represents both tiles with properties, special events and the empty ones.
 * It also contains the methods for owning a property, selling a property, building houses,
 * adding players on the tile, and resetting the players on the tile.
 * 
 * @author Mert Uzun
 * Date: 2/19/2025
 */

public class Properties {
    private int tileNum;
    private int properyPrice;
    private String[] currentPlayersOn;
    private Players owner;
    private String ownersInitial;
    private int buildedHouses;
    private String representationOfHouses;
    private int housePrice;
    private boolean isOwned;
    private int rentPrice;
    private String stringRepresentationOfTileNum;
    private String lettersForTiles = "0ABC1DEF2GHI3JKL";

    /**
     * This constructor is responsible for creating the properties of the game.
     * @param tileNum
     */
    public Properties(int tileNum){
        this.tileNum = tileNum;

        this.currentPlayersOn = new String[4];
        for(int i = 0; i < 4; i++){
            this.currentPlayersOn[i] = ".";
        }

        if (tileNum == 0 || tileNum == 4 || tileNum == 8 || tileNum == 12) {
            this.properyPrice = -1;
        }

        if (tileNum > 0 && tileNum <= 3) {
            this.properyPrice = 2;
        }
        else if (tileNum > 4 && tileNum <= 7) {
            this.properyPrice = 4;
        }
        else if (tileNum > 8 && tileNum <= 11) {
            this.properyPrice = 6;
        }
        else if (tileNum > 12 && tileNum <= 15) {
            this.properyPrice = 8;
        }

        if (tileNum <= 3) {
            this.housePrice = 1;
        }
        else if (tileNum > 4 && tileNum <= 7) {
            this.housePrice = 1;      
        }
        else if (tileNum > 8 && tileNum <= 11) {
            this.housePrice = 2;
        }
        else if (tileNum > 12 && tileNum <= 15) {
            this.housePrice = 3;
        }

        this.owner = null;
        this.ownersInitial = "."; //As a representation of it being null
        this.representationOfHouses = "."; //As a placeholder
        this.buildedHouses = 0;
        this.isOwned = false;
        this.rentPrice = 0; //By default
        
        for(int i = 0; i <= 15; i++){
            if (tileNum == i) {
                this.stringRepresentationOfTileNum = Character.toString(lettersForTiles.charAt(i));
            }
        }
    }
    
    /**
     * This method is responsible for returning the string representation of the tile.
     */
    @Override
    public String toString(){
        return "Tile " + stringRepresentationOfTileNum + ", owner: " + ownersInitial + ", houses: " +
                    buildedHouses + ", sell price: " + properyPrice + ", rent price: " + rentPrice;
    }

    /**
     * This method is responsible for owning the property.
     * It checks if the property is owned, if the player has enough budget to own the property,
     * and if the property has a price.
     * If all the conditions are met, it decreases the budget of the player by the property price,
     * sets the rent price of the property, sets the owner, ownersInitial, representationOfHouses, and isOwned.
     * @param newOwner
     */
    public void own(Players newOwner){
        if (!isOwned && newOwner.getBudget() >= properyPrice && properyPrice != -1) {
            setRentPrice();
            newOwner.decreaseBudget(properyPrice);
            setOwner(newOwner);
            owner.addProperty(this);
            this.ownersInitial = newOwner.getName().substring(0,1);
            isOwned = true;
            representationOfHouses = Integer.toString(buildedHouses);

            System.out.printf("This property is now owned by %s%n", ownersInitial);
        }
        else if (isOwned){
            System.out.printf("This property is already owned by %s%n", ownersInitial);
        }
        else{
            System.out.println("Not enough budget to own this property!");
        }
    }

    /**
     * This method is responsible for selling the property.
     * It checks if the property is owned by someone, and if it is, it increases the budget of the owner by the property price.
     * It also resets the owner, ownersInitial, representationOfHouses, isOwned, buildedHouses, and rentPrice.
     * If the property is not owned by anyone, it prints a message saying that the property is not owned by anyone.
     */
    public void sellProperty(){
        if (isOwned) {
            owner.increaseBudget(properyPrice);

            owner.removeProperty(this);
            owner = null;
            this.ownersInitial = ".";
            representationOfHouses = ".";
            isOwned = false;
            buildedHouses = 0;
            rentPrice = 0;

            System.out.printf("Property sold by %s successfully!", ownersInitial);
        }
        else{
            System.out.println("This property is not owned by anyone!");
        }
    }

    /**
     * This method is responsible for returning the property to the bank.
     * It resets the owner, ownersInitial, representationOfHouses, isOwned, buildedHouses, and rentPrice.
     * It doesn't increase the budget because this method is meant to be used when a player goes bankrupt. 
     */
    public void returnProperty(){
        owner.removeProperty(this);
        owner = null;
        this.ownersInitial = ".";
        representationOfHouses = ".";
        isOwned = false;
        buildedHouses = 0;
        rentPrice = 0;
    }

    /**
     * This method is responsible for building houses on the property.
     * It checks if the player has enough budget to build a house, if the maximum number of houses is reached,
     * and if the player is the owner of the property.
     * If all the conditions are met, it decreases the budget of the owner by the house price,
     * increases the buildedHouses by 1, and sets the rent price of the property.
     */
    public void buildHouses(){
        if (buildedHouses < 4 && owner.getBudget() >= housePrice) {
            owner.decreaseBudget(housePrice);

            buildedHouses += 1;
            representationOfHouses = Integer.toString(buildedHouses);
            setRentPrice();

            System.out.printf("House builded by %s successfully!", ownersInitial);
        }
        else if (owner.getBudget() < housePrice){
            System.out.println("Not enough budget to build a house!");
        }
        else{
            System.out.println("Maximum number of houses (4) reached, cannot build more houses!");
        }
    }

    /**
     * This method is responsible for adding the players on the tile.
     * @param playerOnTile is the player that is going to be added on the tile.
     */
    public void addPeopleOnThisTile(Players playerOnTile){
        for(int i = 0; i < 4; i++){
            if (currentPlayersOn[i].equals(".")) {
                currentPlayersOn[i] = playerOnTile.getNameInitial();
                break;
            }
        }
    }

    /**
     * This method is responsible for resetting the players on the tile.
     * It sets all the elements of the currentPlayersOn array to "." as a placeholder
     * to be reviewed again in next turn's start.
     */
    public void resetPlayersOnTile(){
        for(int i = 0; i < 4; i++){
            currentPlayersOn[i] = ".";
        }
    }

    public void removePlayerFromTile(Players player){
        for(int i = 0; i < 4; i++){
            if (currentPlayersOn[i].equals(player.getNameInitial())) {
                currentPlayersOn[i] = ".";
                break;
            }
        }
    }

    /**
     * This method is responsible for returning the first line of the tile.
     * @return the first line of the tile.
     */
    public String getFirstLine() {
        if (tileNum == -1) {
            return "      ";
        }
        
        if (owner == null) {
            return "|" + stringRepresentationOfTileNum + "." + ownersInitial + representationOfHouses + "|";
        } else {
            return "|" + stringRepresentationOfTileNum + "." + ownersInitial + representationOfHouses + "|";
        }
    }
    
    /**
     * This method is responsible for returning the second line of the tile.
     * @return the second line of the tile.
     */
    public String getSecondLine() {
        if (tileNum == -1) {
            return "      ";
        }
        
        String result = "|";

        for (int i = 0; i < 4; i++) {
            result += currentPlayersOn[i];
        }

        result += "|";
        
        return result;
    }    

    //GETTERS AND SETTERS
    public int getTileNum(){
        return tileNum;
    }

    public int getPropertyPrice(){
        return properyPrice;
    }

    public Players getOwner(){
        return owner;
    }

    public int getBuildedHouses(){
        return buildedHouses;
    }

    public int getRentPrice(){
        return rentPrice;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public boolean isOwned(){
        return isOwned;
    }

    public void setOwner(Players newOwner){
        this.owner = newOwner;
    }

    public void setRentPrice(){
        if (tileNum <= 3) {
            if (buildedHouses == 0) {
                rentPrice = 1;
            }
            else if (buildedHouses == 1) {
                rentPrice = 2;
            }
            else if (buildedHouses == 2) {
                rentPrice = 3;
            }
            else if (buildedHouses == 3) {
                rentPrice = 4;
            }
            else if (buildedHouses == 4) {
                rentPrice = 6;
            }
        }
        else if (tileNum > 4 && tileNum <= 7) {
            if (buildedHouses == 0) {
                rentPrice = 2;
            }
            else if (buildedHouses == 1) {
                rentPrice = 2;
            }
            else if (buildedHouses == 2) {
                rentPrice = 3;
            }
            else if (buildedHouses == 3) {
                rentPrice = 3;
            }
            else if (buildedHouses == 4) {
                rentPrice = 7;
            } 
        }
        else if (tileNum > 8 && tileNum <= 11) {
            if (buildedHouses == 0) {
                rentPrice = 1;
            }
            else if (buildedHouses == 1) {
                rentPrice = 3;
            }
            else if (buildedHouses == 2) {
                rentPrice = 4;
            }
            else if (buildedHouses == 3) {
                rentPrice = 6;
            }
            else if (buildedHouses == 4) {
                rentPrice = 7;
            }  
        }
        else if (tileNum > 12 && tileNum <= 15) {
            if (buildedHouses == 0) {
                rentPrice = 3;
            }
            else if (buildedHouses == 1) {
                rentPrice = 3;
            }
            else if (buildedHouses == 2) {
                rentPrice = 6;
            }
            else if (buildedHouses == 3) {
                rentPrice = 6;
            }
            else if (buildedHouses == 4) {
                rentPrice = 9;
            }
        }
    }
}
