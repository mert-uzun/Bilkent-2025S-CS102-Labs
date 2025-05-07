package Lab6;

import java.util.*;

public class User implements Comparable<User>{
    private String id;
    private String name;
    private String surname;
    private double totalBalanceInCommonCurrency;

    private ArrayList<Account> accounts;
    private static final int MIN_ACCOUNTS = 2;
    private static final int MAX_ACCOUNTS = 10;

    private static final Random RANDOM = new Random();

    private static String[] names = { "Liam", "Ava", "Noah", "Sophia", "Elijah",
                                      "Isabella", "James", "Mia", "William", "Charlotte",
                                      "Benjamin", "Amelia", "Lucas", "Harper", "Henry"};
    private static String[] surnames = {"Anderson", "Brown", "Clark", "Davis", "Evans",
                                        "Garcia", "Harris", "Johnson", "King", "Lee",
                                        "Martinez", "Nelson", "Robinson", "Smith", "Walker"};


    public User(){
        this.id = generateId();
        this.name = generateName();
        this.surname = generateSurname();

        this.accounts = new ArrayList<>();

        for(int i = 0; i < RANDOM.nextInt(MIN_ACCOUNTS, MAX_ACCOUNTS); i++){
            accounts.add(new Account(RANDOM.nextInt(Account.getTYPES().length)));
        }

        this.totalBalanceInCommonCurrency = 0;
        calculateTotalBalanceInCommonCurrency();
    }

    public void printAccounts(){
        int index = 1;
        for(Account account : accounts){
            System.out.println(index + ". " + account);
            index++;
        }
    }

    @Override
    public String toString(){
        return "" + id + 
                " " + name + 
                " " + surname + 
                " Total Amount: " + totalBalanceInCommonCurrency;
    }

    @Override 
    public boolean equals(Object obj){
        if(obj instanceof User){
            User other = (User) obj;
            return this.id.equals(other.id);
        }

        return false;
    }

    @Override
    public int compareTo(User other){
        return this.id.compareTo(other.getID());
    }

    public void calculateTotalBalanceInCommonCurrency(){
        for(Account account : accounts){
            totalBalanceInCommonCurrency += account.getCommonCurrencyBalance();
        }
    }

    public void resetTotalBalanceForRateChange(){
        this.totalBalanceInCommonCurrency = 0;
        calculateTotalBalanceInCommonCurrency();
    }

    private static String generateId(){
        String id = "";

        for(int i = 0; i < 9; i++){
            id += RANDOM.nextInt(10);
        }

        return id;
    }

    private static String generateName(){
        return names[RANDOM.nextInt(names.length)];
    }

    private static String generateSurname(){
        return surnames[RANDOM.nextInt(surnames.length)];
    }

    //GETTERS AND SETTERS
    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }
    
    public double getTotalBalanceInCommonCurrency(){
        return totalBalanceInCommonCurrency;
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }
}
