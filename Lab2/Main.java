import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * A game that has lists of chain words based on the instruction of lab document.
 * It creates the chains if there are no corresponding files and if there are, program loads them
 * Based on these files, user chooses between three options:
 * 1) Displaying a 10 word chain, starting with the word user enters
 * 2) A word guessing game, which has 3 words but the middle word is concealed. User has 3 guesses to find this word
 * 3) Exit the game
 * 
 * @author Mert Uzun
 * Date: 2/26/2025
 */
public class Main {
    private static ArrayList<Word> allWords = new ArrayList<>();
    private static ArrayList<Word> filteredWords = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException {        
        playGame();
    }

    /**
     * Play game method that runs the main cycle of the game
     * @throws IOException to handle file exceptions
     */
    public static void playGame() throws IOException{
        boolean gameIsOn = true;
        
        initializeGame();
        System.out.println("WELCOME TO THE GAME!");

        while (gameIsOn) {
            System.out.println("1 - WORD CHAIN GENERATOR");
            System.out.println("2 - WORD GUESSING GAME");
            System.out.println("3 - EXIT");
            System.out.print("ENTER YOUR CHOICE: ");

            Integer choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    firstGame();
                    break;
                case 2:
                    secondGame();
                    break;
                case 3:
                    gameIsOn = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("QUITTING THE GAME, COME BACK SOON!");
    }

    /**
     * A method to initialize the basic needs for the game, which are corresponding filtered words and chain files
     * @throws IOException 
     */
    public static void initializeGame() throws IOException {
        String filenameForFilteredWords = "filtered_words.txt";
        String filenameForChains = "chains.txt";
    
        File filteredFile = new File(filenameForFilteredWords);
        File chainsFile = new File(filenameForChains);
    
        if (filteredFile.exists() && filteredFile.length() > 0 &&
                chainsFile.exists() && chainsFile.length() > 0) {
            fillFilteredWords(filenameForFilteredWords);
            fillChains(filenameForChains);
        } 
        else {
            createAllWords(); 
            createFiles();     
        }
    }

    /**
     * A method that implements the first game in which user enters a word and program displays the user a word chain.
     * @throws IOException handles file exceptions
     */
    public static void firstGame() throws IOException{
        System.out.println("WELCOME TO THE CHAIN GENERATOR!");
        boolean flag = true;
        do{     
            System.out.print("ENTER A WORD TO START WITH: ");
            String startingWord = sc.nextLine();

            if (findInFilteredWords(startingWord) == null) {
                System.out.println("THIS WORD IS NOT IN THE LIST!");
            }
            else{
                Word firstWord = findInFilteredWords(startingWord);
                flag = false;
                ArrayList<Word> tenPieceArrayList = creatingTenPieceChain(firstWord);

                for(int i = 0; i < tenPieceArrayList.size(); i++){
                    System.out.print(tenPieceArrayList.get(i));
                    if (i < tenPieceArrayList.size() - 1) {
                        System.out.print("-->");
                    }
                    else{
                        System.out.println("\n");
                    }
                }
            }
        }while(flag);
    }

    /**
     * A method that implements the second game in which user tries to find the concealed middle word in a three word chain
     */
    public static void secondGame(){
        Random random = new Random();

        Word firstWord = filteredWords.get(random.nextInt(filteredWords.size()));
        while (firstWord.getPossibleChains().size() < 3) {
            firstWord = filteredWords.get(random.nextInt(filteredWords.size()));
        }
        
        Word middleWord = firstWord.getPossibleChains().get(random.nextInt(firstWord.getPossibleChains().size()));
        while (middleWord.getPossibleChains().size() < 3) {
            middleWord = firstWord.getPossibleChains().get(random.nextInt(firstWord.getPossibleChains().size()));
        }

        Word thirdWord = middleWord.getPossibleChains().get(random.nextInt(middleWord.getPossibleChains().size()));
        while (thirdWord.equals(firstWord) || thirdWord.getPossibleChains().size() < 3) {
            thirdWord = middleWord.getPossibleChains().get(random.nextInt(middleWord.getPossibleChains().size()));
        }      

        System.out.println("THEN GUESSING GAME IT IS!");
        System.out.println("HERE IS OUR SEQUENCE WITH THE MIDDLE WORD CONCEALED!");
        System.out.println("TRY TO FIND THE MIDDLE WORD!");
        System.out.println(firstWord.getName() + "     -->     _______     -->     " + thirdWord.getName());

        boolean middleWordFound = false;
        for(int i = 3; i >= 1; i--){
            System.out.print("ENTER YOUR GUESS: (YOU HAVE " + i + " GUESSES LEFT!)");
            String guess = sc.nextLine();
            if (guess.equalsIgnoreCase(middleWord.getName())) {
                System.out.println("YOU GUESSEED IT RIGHT, THE MIDDLE WORD IS: " + middleWord.getName());
                middleWordFound = true;
                break;
            }
            else{
                System.out.println("YOUR GUESS IS WRONG, TRY AGAIN!");
            }
        }
        if (!middleWordFound) {
            System.out.println("MIDDLE WORD WAS " + middleWord.getName() + "!");
        }
    }

    /**
     * Loads the current words.txt file into the allWords ArrayList
     * @throws FileNotFoundException handles file exceptions
     */
    public static void createAllWords() throws FileNotFoundException {
        String filename = "words.txt";
        Scanner fileScanner = new Scanner(new File(filename));

        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            allWords.add(new Word(line));
        }
        fileScanner.close();
    }

    /**
     * Creates the filtered_words.txt and chains.txt files based on the rules mentioned in lab document
     * @throws IOException handles file exceptions
     */
    public static void createFiles() throws IOException{
        for(Word word : allWords){
            for(Word wordToAddToTheChain : allWords){
                word.addToPossibleWords(wordToAddToTheChain);
            }
        }

        Iterator<Word> iterator = allWords.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (word.getPossibleChains().isEmpty()) {
                iterator.remove();
            }
            else{
                filteredWords.add(word);
            }
        }        

        String filteredWordsFile = "filtered_words.txt";
        FileWriter fileWriterOne = new FileWriter(filteredWordsFile); // Open file once
        for (Word word : filteredWords) {
            fileWriterOne.write(word.getName() + "\n");
        }
        fileWriterOne.close(); // Close after loop
        

        String chainsFile = "chains.txt";
        FileWriter fileWriterTwo = new FileWriter(chainsFile); // Open once

        for (Word word : filteredWords) {
            fileWriterTwo.write(word.getName() + ": ");

            ArrayList<Word> chains = word.getPossibleChains();
            for (int i = 0; i < chains.size(); i++) {
                fileWriterTwo.write(chains.get(i).getName());
                if (i < chains.size() - 1) fileWriterTwo.write(" ");
            }
            
            fileWriterTwo.write("\n"); // New line for next word
        }

        fileWriterTwo.close(); // Close after writing
    }

    /**
     * Loads filtered_words.txt file into filteredWords ArrayList
     * @param filename filename (filtered_text.txt here) to be loaded
     * @throws IOException handles file exceptions
     */
    private static void fillFilteredWords(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            filteredWords.add(new Word(line.trim()));
        }
        
        br.close();
    }

    /**
     * Loads chains.txt file into filteredWords ArrayList
     * @param filename filename (chains.txt here) to be loaded
     * @throws IOException handles file exceptions
     */
    public static void fillChains(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":"); //Splits at ":" for the first word
            if (parts.length < 2) {
                continue; //Skip invalid lines
            }

            String wordName = parts[0].trim();
            Word mainWord = findInFilteredWords(wordName);

            if (mainWord == null) {
                continue; //Skip if word isn't in the filtered words list
            }

            String[] chainWords = parts[1].trim().split(" ");
            for(String chainWord : chainWords){
                Word chain = findInFilteredWords(chainWord.trim());
                if (chain != null) {
                    mainWord.addToPossibleWords(chain);
                }
            }
        }

        br.close();
    }

    /**
     * Searches if Word object with given name exists in filteredWords ArrayList
     * @param name Word object's name
     * @return the Word object if a Word with given name exists, null if not
     */
    public static Word findInFilteredWords(String name){
        for(Word word : filteredWords){
            if (word.getName().equalsIgnoreCase(name)) {
                return word;
            }
        }

        return null;
    }

    /**
     * Creates the ten Word chain to be displayed in first game
     * @param startingWord starting Word's name given by user
     * @return the ten Word chain, stored in an ArrayList
     */
    public static ArrayList<Word> creatingTenPieceChain(Word startingWord){
        Random random = new Random();
        ArrayList<Word> currentChainForDisplay = new ArrayList<>();

        currentChainForDisplay.add(startingWord);

        for(int i = 0; i < 9; i++){
            if (currentChainForDisplay.get(i).getPossibleChains().isEmpty()) {
                break; // Stop if no more chains are available
            }

            boolean flag = true;
            int tries = 0;
            while (flag) {
                if (tries > 10) {
                    return currentChainForDisplay;
                }
                Word wordToAdd = currentChainForDisplay.get(i).getPossibleChains().get(random.nextInt(currentChainForDisplay.get(i).getPossibleChains().size()));
                if (!currentChainForDisplay.contains(wordToAdd)) {
                    currentChainForDisplay.add(wordToAdd);
                    flag = false;
                }
                tries++;
            }
        }

        return currentChainForDisplay;
    }
}
