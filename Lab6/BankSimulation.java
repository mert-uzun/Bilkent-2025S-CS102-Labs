package Lab6;

import java.util.*;

public class BankSimulation {
    private static final int USERS_ARRAY_LENGTH = 100000;

    private static final Scanner scanner = new Scanner(System.in);

    private User[] users;
    private User[] usersUnsorted;
    private User[] usersSorted;
    private int totalNumberOfUsers;
    private boolean programContinues = true;

    public BankSimulation(){
        users = new User[USERS_ARRAY_LENGTH];
        usersUnsorted = new User[USERS_ARRAY_LENGTH];
        usersSorted = new User[USERS_ARRAY_LENGTH];
        totalNumberOfUsers = 0;
    }

    public void startSimulation(){
        System.out.println("Welcome to the bank!\n");

        Account.determineRates();
        System.out.println("Current convertion rates: ");
        Account.printRates();

        while (programContinues) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. Generate random users");
            System.out.println("2. List users");
            System.out.println("3. Show user with ID");
            System.out.println("4. Set conversion rates");
            System.out.println("5. Sort users");
            System.out.println("6. Reset to the original unsorted array");
            System.out.println("0. Exit");
            System.out.print("Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.println();

            switch (choice) {
                case 1:
                    generateRandomUsers();
                    break;
                case 2:
                    listUsers();
                    break;
                case 3:
                    showUserWithID();
                    break;
                case 4:
                    setConversionRates();
                    break;
                case 5:
                    sortUsers();
                    break;
                case 6:
                    resetToUnsortedArray();
                    break;
                case 0:
                    programContinues = false;
                    System.out.println("\nBye!");
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void generateRandomUsers(){
        System.out.print("Generate how many? ");
        int numberOfUsersToGenerate = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Generating " + numberOfUsersToGenerate + " random users...");

        for(int i = 0; i < numberOfUsersToGenerate; i++){
            usersUnsorted[totalNumberOfUsers++] = new User();
        }
    }

    private void listUsers(){
        for(int i = 0; i < totalNumberOfUsers; i++){
            System.out.println(usersUnsorted[i]);
        }
    }

    private void showUserWithID(){
        System.out.print("Enter User ID: ");
        String id = scanner.nextLine();

        if (id.length() != 9) {
            System.out.println("Cannot find the user!");
            return;
        }

        for(int i = 0; i < totalNumberOfUsers; i++){
            if(usersUnsorted[i].getID().equals(id)){
                System.out.println("\n" + usersUnsorted[i]);
                System.out.println("Accounts: ");
                usersUnsorted[i].printAccounts();
                return;
            }
        }

        System.out.println("Cannot find the user!");
    }

    public void setConversionRates(){
        for(int i = 0; i < Account.getTYPES().length; i++){
            System.out.print("Set " + Account.getTYPES()[i] + ": ");
            Account.getRates()[i] = scanner.nextDouble();
            scanner.nextLine();
        }        
    }

    public void sortUsers(){
        System.out.println("Choose sort type: ");
        System.out.println("1. By ID");
        System.out.println("2. By Total Amount");
        System.out.print("Option: ");

        int sortingChoice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter sort limit: ");
        int sortLimit = scanner.nextInt();
        scanner.nextLine();

        switch(sortingChoice){
            case 1:
                usersUnsorted = Arrays.copyOf(usersUnsorted, USERS_ARRAY_LENGTH);
        }
    }

    public User[] sortAccordingToID(int limit, User[] partitionOfUsers){
        if (partitionOfUsers.length <= limit) {
            return insertionSortBasedOnID(partitionOfUsers);
        }
    }

    public User[] sortAccordingToTotalBalance(int limit, User[] partitionOfUsers){
        if (usersUnsorted.length <= limit) {
            return insertionSortBasedOnTotalBalance(usersUnsorted);
        }
    }

    public User[] insertionSortBasedOnID(User[] partitionOfArray){
        for (int i = 1; i < partitionOfArray.length; i++) {
            User elementToInsert = partitionOfArray[i];

            for (int j = i - 1; j >= 0; j--) {
                if (elementToInsert.compareTo(partitionOfArray[j]) < 0) {
                    User temp = partitionOfArray[j];
                    partitionOfArray[j] = elementToInsert;
                    partitionOfArray[j + 1] = temp;
                }
            }
        }

        return partitionOfArray;
    }

    public User[] insertionSortBasedOnTotalBalance(User[] partitionOfArray){
        for (int i = 1; i < partitionOfArray.length; i++) {
            User elementToInsert = partitionOfArray[i];

            for (int j = i - 1; j >= 0; j--) {
                if (partitionOfArray[j].getTotalBalanceInCommonCurrency() < elementToInsert.getTotalBalanceInCommonCurrency()) {
                    User temp = partitionOfArray[j];
                    partitionOfArray[j] = elementToInsert;
                    partitionOfArray[j + 1] = temp;
                }
            }
        }

        return partitionOfArray;
    }
}