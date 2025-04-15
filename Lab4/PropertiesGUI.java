import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class PropertiesGUI extends JPanel{
    private static final int WIDTH = 80;
    private static final int LENGTH = 80;
    private int tileNum;
    private int propertyPrice;
    private ArrayList<Color> currentPlayersOn;
    private JComponent representationOfPlayersOnTile;
    private PlayersGUI owner;
    private Color ownersColor;
    private int buildedHouses;
    private JComponent representationOfHouses;
    private int housePrice;
    private boolean isOwned;
    private int rentPrice;
    private String stringRepresentationOfTileNum;
    private JLabel tileLabel;
    private String lettersForTiles = "0ABC1DEF2GHI3JKL";

    public PropertiesGUI(int tileNum){
        super();
        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.tileNum = tileNum;

        this.currentPlayersOn = new ArrayList<>();

        representationOfPlayersOnTile = new JComponent() {
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                for (int i = 0; i < currentPlayersOn.size(); i++) {
                    if (currentPlayersOn.get(i) != null) {
                        g.setColor(currentPlayersOn.get(i));
                        g.fillOval(i * 20, 5, 15, 15);
                        g.setColor(Color.BLACK);
                        g.drawOval(i * 20, 5, 15, 15);
                    }
                }
            }
        };
        representationOfPlayersOnTile.setPreferredSize(new Dimension(80, 20));
        this.add(representationOfPlayersOnTile, BorderLayout.SOUTH);

        if (tileNum == 0 || tileNum == 4 || tileNum == 8 || tileNum == 12) {
            this.propertyPrice = -1;
        }

        if (tileNum > 0 && tileNum <= 3) {
            this.propertyPrice = 2;
        }
        else if (tileNum > 4 && tileNum <= 7) {
            this.propertyPrice = 4;
        }
        else if (tileNum > 8 && tileNum <= 11) {
            this.propertyPrice = 6;
        }
        else if (tileNum > 12 && tileNum <= 15) {
            this.propertyPrice = 8;
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

        this.isOwned = false;
        this.owner = null;

        if (tileNum % 4 == 0) {
            this.ownersColor = Color.DARK_GRAY; //As a representation of it being a special tile
            this.setBackground(ownersColor);
        }
        else{
            this.ownersColor = Color.WHITE; //As a representation of it being available
            this.setBackground(ownersColor);
        }
        

        this.buildedHouses = 0;
        this.representationOfHouses = new JComponent() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < buildedHouses; i++) {
                    g.setColor(Color.BLACK);
                    g.fillRect(10 * i, 0, 10, 10);
                }
            }
        };
        representationOfHouses.setPreferredSize(new Dimension(40, 10));
        this.add(representationOfHouses, BorderLayout.NORTH);

        this.rentPrice = 0; //By default
        
        for(int i = 0; i <= 15; i++){
            if (tileNum == i) {
                this.stringRepresentationOfTileNum = Character.toString(lettersForTiles.charAt(i));
            }
        }

        this.tileLabel = new JLabel(stringRepresentationOfTileNum, SwingConstants.CENTER);
        this.tileLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        this.tileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.tileLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.add(tileLabel, BorderLayout.CENTER);
    }

    /**
     * This method is responsible for owning the property.
     * It checks if the property is owned, if the player has enough budget to own the property,
     * and if the property has a price.
     * If all the conditions are met, it decreases the budget of the player by the property price,
     * sets the rent price of the property, sets the owner, ownersInitial, representationOfHouses, and isOwned.
     * @param newOwner
     */
    public void own(PlayersGUI newOwner){
        if (!isOwned && newOwner.getBudget() >= propertyPrice && propertyPrice != -1) {
            setRentPrice();
            newOwner.decreaseBudget(propertyPrice);
            setOwner(newOwner);
            owner.addProperty(this);
            this.ownersColor = newOwner.getColor();
            this.setBackground(ownersColor);
            isOwned = true;
            buildedHouses = 0;
            representationOfHouses.repaint();

            MainGUI.logArea.append("This property is now owned by " + owner.getName());
        }
        else if (isOwned){
            MainGUI.logArea.append("This property is already owned by " + owner.getName());
        }
        else{
            MainGUI.logArea.append("Not enough budget to own this property!");
        }

        repaint();
    }

    /**
     * This method is responsible for selling the property.
     * It checks if the property is owned by someone, and if it is, it increases the budget of the owner by the property price.
     * It also resets the owner, ownersInitial, representationOfHouses, isOwned, buildedHouses, and rentPrice.
     * If the property is not owned by anyone, it prints a message saying that the property is not owned by anyone.
     */
    public void sellProperty(){
        if (isOwned) {
            owner.increaseBudget(propertyPrice);
            owner.removeProperty(this);
            MainGUI.logArea.append("Property sold by " + owner.getName() + " successfully!");

            owner = null;
            this.ownersColor = Color.WHITE;
            this.setBackground(ownersColor);
            buildedHouses = 0;
            representationOfHouses.repaint();
            isOwned = false;
            rentPrice = 0;
        }
        else{
            MainGUI.logArea.append("This property is not owned by anyone!");
        }

        repaint();
    }

    /**
     * This method is responsible for returning the property to the bank.
     * It resets the owner, ownersInitial, representationOfHouses, isOwned, buildedHouses, and rentPrice.
     * It doesn't increase the budget because this method is meant to be used when a player goes bankrupt. 
     */
    public void returnProperty(){
        owner.removeProperty(this);
        owner = null;
        this.ownersColor = Color.WHITE;
        this.setBackground(ownersColor);
        buildedHouses = 0;
        representationOfHouses.repaint();
        isOwned = false;
       
        rentPrice = 0;

        repaint();
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
            representationOfHouses.repaint();
            setRentPrice();

            MainGUI.logArea.append("House builded by " + owner.getName() + " successfully!");
        }
        else if (owner.getBudget() < housePrice){
            MainGUI.logArea.append("Not enough budget to build a house!");
        }
        else{
            MainGUI.logArea.append("Maximum number of houses (4) reached, cannot build more houses!");
        }

        repaint();
    }

    /**
     * This method is responsible for adding the players on the tile.
     * @param playerOnTile is the player that is going to be added on the tile.
     */
    public void addPeopleOnThisTile(PlayersGUI playerOnTile){
        currentPlayersOn.add(playerOnTile.getColor());

        representationOfPlayersOnTile.repaint();

        repaint();
    }

    /**
     * This method is responsible for resetting the players on the tile.
     * It sets all the elements of the currentPlayersOn array to "." as a placeholder
     * to be reviewed again in next turn's start.
     */
    public void resetPlayersOnTile(){
        currentPlayersOn.clear();

        representationOfPlayersOnTile.repaint();

        repaint();
    }

    public void removePlayerFromTile(PlayersGUI player){
        currentPlayersOn.remove(player.getColor());

        representationOfPlayersOnTile.repaint();

        repaint();
    }

    //GETTERS AND SETTERS
    public int getTileNum(){
        return tileNum;
    }

    public int getPropertyPrice(){
        return propertyPrice;
    }

    public PlayersGUI getOwner(){
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

    public Color getColor(){
        return ownersColor;
    }

    public String getStringRepresentationOfTileNum(){
        return stringRepresentationOfTileNum;
    }

    public boolean isOwned(){
        return isOwned;
    }

    public void setOwner(PlayersGUI newOwner){
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