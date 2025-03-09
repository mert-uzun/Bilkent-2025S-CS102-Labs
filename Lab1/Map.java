/**
 * This class is a singleton that represents the map of the game. It is a 5x5 matrix of Properties objects.
 * 
 * @author Mert Uzun
 * Date: 2/19/2025
 */

public class Map {

    private static Map instance;
    Properties[][] table = new Properties[5][5];;

    //Fill the table with properties
    private Map(){

        //FIRTS ROW
        int i = 0;
        for (int j = 0; j < 5; j++){
            table[i][j] = new Properties(j);
        }

        //SECOND ROW
        i = 1;
        for (int j = 0; j < 5; j++){
            if (j == 0){
                table[i][j] = new Properties(15);
            }
            else if (j < 4){
                table[i][j] = new Properties(-1);
            }
            else{
                table[i][j] = new Properties(5);
            }
        }

        //THIRD ROW
        i = 2;
        for (int j = 0; j < 5; j++){
            if (j == 0){
                table[i][j] = new Properties(14);
            }
            else if (j < 4){
                table[i][j] = new Properties(-1);
            }
            else{
                table[i][j] = new Properties(6);
            }
        }

        //FOURTH ROW
        i = 3;
        for (int j = 0; j < 5; j++){
            if (j == 0){
                table[i][j] = new Properties(13);
            }
            else if (j < 4){
                table[i][j] = new Properties(-1);
            }
            else{
                table[i][j] = new Properties(7);
            }
        }

        //FIFTH ROW
        i = 4;
        for (int j = 0; j < 5; j++){
            table[i][j] = new Properties(12 - j);
        }    
    }
    public static void main(String[] args) {
        Map map = new Map();

        map.printMap();
    }

    /**
     * This method prints the map to the console.
     */
    public void printMap() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(table[i][j].getFirstLine() + " ");
            }
            System.out.println(); 

            for (int j = 0; j < 5; j++) {
                System.out.print(table[i][j].getSecondLine() + " ");
            }
            System.out.println(); 
        }
    }    


    //GETTERS AND SETTERS
    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    public Properties getTile(int x, int y){
        return table[x][y];
    }

    public Properties getTileByNum(int num){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (table[i][j].getTileNum() == num){
                    return table[i][j];
                }
            }
        }
    
        return null;
    }
}
