import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the Monopoly game. The game runs on this file.
 * It is responsible for creating the players, setting the playing order,
 * and running the game until there is a winner or a draw.
 * 
 * @author Mert Uzun
 * Date: 2/19/2025
*/
public class Main {
    public static ArrayList<Players> players = new ArrayList<>();
    public static final Scanner scanner = new Scanner(System.in); 
    
    public static void main(String[] args) {
        int turnCounter = 1;
        
        //Ask the user how many human players are there.
        System.out.println("WELCOME TO MONOPOLY!\n");
        System.out.print("How many players will play? ");
        int playerCount = scanner.nextInt();

        scanner.nextLine(); // Consume the newline character

        //Get the names of the human players and create them.
        for (int i = 0; i < playerCount; i++) {
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            String name = scanner.next();
            players.add(new Players(true, name));
        }

        scanner.nextLine(); // Consume the newline character

        //Create bot players to fill up to 4 players if there are less than 4 human players.
        for (int i = players.size(); i < 4; i++) {
            players.add(new Players(false, ""));
        }

        //Set the playing order by randomizing the players
        for (int i = 0; i < 100; i++) {
            int randomIndex1 = (int) (Math.random() * players.size());
            int randomIndex2 = (int) (Math.random() * players.size());
            Players temp = players.get(randomIndex1);
            players.set(randomIndex1, players.get(randomIndex2));
            players.set(randomIndex2, temp);
        }

        //Start the game
        System.out.println("\nThe game is starting!\n");

        //Initialize the players into the starting tile.
        for(Players player : players){
            Map.getInstance().getTileByNum(0).addPeopleOnThisTile(player);
        }

        //Run the game until there is a winner or a draw, draw happens when the turn count reaches 100.
        while (players.size() > 1 && turnCounter < 100) {
            
            System.out.printf("%s %d, ", "TURN", turnCounter);  
            
            for (Players player : players) {
                player.takeTurn();
                displayBudgets();
            }

            //Remove the bankrupt players from the game at the end of the turn
            //    and return all of their properties to the bank.
            for(Players player : players){
                if (player.isBankrupt()) {
                    player.returnAllProperties();
                }
            }

            players.removeIf(player -> player.isBankrupt());
        
            turnCounter++;
        }

        //Display the winner or the draw message, and the final state of the map.
        System.out.println("The game is over! Final view of the map is: ");
        Map.getInstance().printMap();

        if (players.size() == 1) {
            System.out.println("The winner is " + players.get(0).getName() + "! Congratulations!");
        }
        else{
            System.out.print("The game is a draw between ");
            for (Players player : players) {
                System.out.print(player.getName() + " ");
            }
            System.out.println("!");
        }
    }

    /**
     * Displays the budgets of the players.
     */
    public static void displayBudgets(){
        System.out.println();
        System.out.println("Current budgets of the players:");
        for (Players player : players) {
            System.out.println(player.getName() + " has " + player.getBudget() + " coins.");
        }
        System.out.println();
    }

    //GETTER
    public static ArrayList<Players> getPlayers(){
        return players;
    }
}
