package Lab6;

import java.util.*;

public class Account {
    private static final String[] TYPES = {"A", "B", "C", "D"};
    private static double[] rates = new double[TYPES.length];
    private static final double RATE_BELOW_LIMIT = 0.2;
    private static final double RATE_ABOVE_LIMIT = 5;

    private static final Random RANDOM = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    private String type;
    private double balance;
    private double rateToCommonCurrency;
    
    public Account(int index){
        this.type = TYPES[index];
        this.balance = RANDOM.nextDouble(100, 10000);
        this.rateToCommonCurrency = rates[index];
    }

    public static void determineRates(){
        for(int i = 0; i < TYPES.length; i++){
            rates[i] = RANDOM.nextDouble(RATE_BELOW_LIMIT, RATE_ABOVE_LIMIT);
        }
    }

    public static void changeRates(){
        for(int i = 0; i < Account.getTYPES().length; i++){
            System.out.print("Set " + Account.getTYPES()[i] + ": ");
            Account.getRates()[i] = scanner.nextDouble();
            scanner.nextLine();
        }    
    }

    public static void printRates(){
        for(int i = 0; i < TYPES.length; i++){
            System.out.println(TYPES[i] + ": " + rates[i]);
        }
    }

    @Override
    public String toString(){
        return "Type: " + type + 
                " Amount: " + balance + 
                " Common: " + rateToCommonCurrency;
    }

    //GETTERS
    public String getType(){
        return type;
    }

    public double getBalance(){
        return balance;
    }

    public double getRateToCommonCurrency(){
        return rateToCommonCurrency;
    }

    public double getCommonCurrencyBalance(){
        return balance * rateToCommonCurrency;
    }
    
    public static String[] getTYPES(){
        return TYPES;
    }

    public static double[] getRates(){
        return rates;
    }
}
