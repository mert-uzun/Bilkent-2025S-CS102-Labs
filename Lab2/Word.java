import java.util.ArrayList;

/**
 * Word class with instance variables name (which is the word itself) and an ArrayList of possible chain words
 * Rule for possible chain words is explained in lab document
 * 
 * @author Mert Uzun
 * Date: 2/26/2025
 */
public class Word {
    private String name;
    private ArrayList<Word> possibleChains;

    /**
     * Construction method for Word object
     * @param name string representation of words
     */
    public Word(String name) {
        this.name = name.toUpperCase();
        possibleChains = new ArrayList<>();
    }

    /**
     * A method to look if two words can be chained based on the rules explained in lab document
     * @param otherWord other Word object
     * @return true or false if two words can be chained or not
     */
    public boolean canChain(Word otherWord){
        if (Math.abs(this.getLength() - otherWord.getLength()) >= 2) {
            return false;
        }
        else if (this.getLength() == otherWord.getLength()) {
            int counter = 0;
            for(int i = 0; i < this.getLength(); i++){
                if (this.name.charAt(i) != otherWord.getName().charAt(i)) {
                    counter++;
                }
            }

            if (counter == 0) {
                return false; //It is the same word then
            }
            else if (counter == 1) {
                return true; //Can form a chain
            }
            else{
                return false; //More than one change of letter
            }
        }
        else if (this.getLength() == otherWord.getLength() + 1) {
            return helperForInsertionOrDeletion(this, otherWord);
        }
        else if (this.getLength() == otherWord.getLength() - 1) {
            return helperForInsertionOrDeletion(otherWord, this);
        }

        return false; //DEFAULT
    }

    /**
     * Looks that if given word already exists in current possibleChains ArrayList
     * @param wordToBeAdded word to be looked for its existance in possibleChains ArrayList
     * @return true or false if given word exists in possibleChains ArrayList or not
     */
    public boolean isInTheChain(Word wordToBeAdded){
        for(Word word : this.possibleChains){
            if (wordToBeAdded.getName().equalsIgnoreCase(word.getName())) {
                return true;
            }
        }

        return false;  
    }

    /**
     * Adds the given word to possibleChains ArrayList if this word can be chained and is not already in this ArrayList
     * @param otherWord word to be added to possibleChains ArrayList
     */
    public void addToPossibleWords(Word otherWord){
        if (!isInTheChain(otherWord) && this.canChain(otherWord) ) {
            this.possibleChains.add(otherWord);
        }
    }

    /**
     * Helper method to look for 2 words with length difference of 1 if they can be chained or not based on the rules described in lab document
     * @param longerWord longer word to be looked
     * @param shorterWord shorter word to be looked
     * @return true or false based on if these 2 words can be chained or not
     */
    private boolean helperForInsertionOrDeletion(Word longerWord, Word shorterWord){
        int i = 0;
        int j = 0;
        boolean oneDifferenceFound = false;

        while (i < longerWord.getLength() && j <= shorterWord.getLength()) {
            if (!oneDifferenceFound && j == shorterWord.getLength()) {
                return true; //Edge case
            }
            
            if (longerWord.getName().charAt(i) != shorterWord.getName().charAt(j)) {
                if (oneDifferenceFound) {
                    return false; //Means there are more than one difference
                }
                else{
                    oneDifferenceFound = true;
                }

                i++;
            }
            else{
                i++;
                j++;
            }
        }

        return oneDifferenceFound;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    /**
     * Equals method that looks for 2 Word objects' names
     * @param other other Word object to be considered
     * @return true or false based on if these 2 words' names are equal or not
     */
    public boolean equals(Word other){
        return this.getName().equals(other.getName());
    }

    //GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public int getLength(){
        return name.length();
    }

    public ArrayList<Word> getPossibleChains(){
        return possibleChains;
    }

    public void setName(String name) {
        this.name = name;
    }
}