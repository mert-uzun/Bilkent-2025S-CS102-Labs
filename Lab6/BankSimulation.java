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
        totalNumberOfUsers = 0;
        usersUnsorted = new User[totalNumberOfUsers];
        usersSorted = new User[totalNumberOfUsers];
        
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
            users[totalNumberOfUsers++] = new User();
        }
    }

    private void listUsers(){
        for(int i = 0; i < totalNumberOfUsers; i++){
            System.out.println(users[i]);
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
            if(users[i].getID().equals(id)){
                System.out.println("\n" + users[i]);
                System.out.println("Accounts: ");
                users[i].printAccounts();
                return;
            }
        }

        System.out.println("Cannot find the user!");
    }

    public void setConversionRates(){
        Account.changeRates();
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
                double startTime = System.currentTimeMillis();
                usersUnsorted = Arrays.copyOf(users, totalNumberOfUsers);
                usersSorted = sortAccordingToID(sortLimit, usersUnsorted);
                users = Arrays.copyOf(usersSorted, totalNumberOfUsers);
                double endTime = System.currentTimeMillis();
                double duration = (endTime - startTime) / 1000;
                System.out.println("Time taken: " + duration + " milliseconds");
                break;
            case 2:
                startTime = System.currentTimeMillis();
                usersUnsorted = Arrays.copyOf(users, totalNumberOfUsers);
                usersSorted = sortAccordingToTotalBalance(sortLimit, usersUnsorted);
                users = Arrays.copyOf(usersSorted, totalNumberOfUsers);
                endTime = System.currentTimeMillis();
                duration = (endTime - startTime) / 1000;
                System.out.println("Time taken: " + duration + " milliseconds");
                break;
            default:
                System.out.println("Invalid option!");
                break;
        }
    }

    public User[] sortAccordingToID(int limit, User[] partitionOfUsers){
        if (partitionOfUsers.length <= limit) {
            return insertionSortBasedOnID(partitionOfUsers);
        }

        int pivotIndex = partitionOfUsers.length / 2;
        User pivot = partitionOfUsers[pivotIndex];

        ArrayList<User> lessThanPivot = new ArrayList<>();
        ArrayList<User> equalPivot = new ArrayList<>();
        ArrayList<User> greaterThanPivot = new ArrayList<>();

        for(int i = 0; i < partitionOfUsers.length; i++){
            if(partitionOfUsers[i].compareTo(pivot) < 0){
                lessThanPivot.add(partitionOfUsers[i]);
            }
            else if (partitionOfUsers[i].compareTo(pivot) > 0){
                greaterThanPivot.add(partitionOfUsers[i]);
            }
            else{
                equalPivot.add(partitionOfUsers[i]);
            }
        }

        User[] sortedLessThanPivot = sortAccordingToID(limit, ArrayListToArray(lessThanPivot));
        User[] sortedGreaterThanPivot = sortAccordingToID(limit, ArrayListToArray(greaterThanPivot));

        return concatenateArrays(sortedLessThanPivot, equalPivot, sortedGreaterThanPivot);
    }

    public User[] sortAccordingToTotalBalance(int limit, User[] partitionOfUsers){
        if (partitionOfUsers.length <= limit) {
            return insertionSortBasedOnTotalBalance(partitionOfUsers);
        }

        int pivotIndex = partitionOfUsers.length / 2;
        User pivot = partitionOfUsers[pivotIndex];

        ArrayList<User> lessThanPivot = new ArrayList<>();
        ArrayList<User> equalPivot = new ArrayList<>();
        ArrayList<User> greaterThanPivot = new ArrayList<>();

        for(int i = 0; i < partitionOfUsers.length; i++){
            if(partitionOfUsers[i].getTotalBalanceInCommonCurrency() < pivot.getTotalBalanceInCommonCurrency()){
                lessThanPivot.add(partitionOfUsers[i]);
            }
            else if (partitionOfUsers[i].getTotalBalanceInCommonCurrency() > pivot.getTotalBalanceInCommonCurrency()){
                greaterThanPivot.add(partitionOfUsers[i]);
            }
            else{
                equalPivot.add(partitionOfUsers[i]);
            }
        }

        User[] sortedLessThanPivot = sortAccordingToTotalBalance(limit, ArrayListToArray(lessThanPivot));
        User[] sortedGreaterThanPivot = sortAccordingToTotalBalance(limit, ArrayListToArray(greaterThanPivot));

        return concatenateArrays(sortedLessThanPivot, equalPivot, sortedGreaterThanPivot);
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
                else{
                    break;
                }
            }
        }

        return partitionOfArray;
    }

    public User[] insertionSortBasedOnTotalBalance(User[] partitionOfArray){
        for (int i = 1; i < partitionOfArray.length; i++) {
            User elementToInsert = partitionOfArray[i];

            for (int j = i - 1; j >= 0; j--) {
                if (elementToInsert.getTotalBalanceInCommonCurrency() < partitionOfArray[j].getTotalBalanceInCommonCurrency()) {
                    User temp = partitionOfArray[j];
                    partitionOfArray[j] = elementToInsert;
                    partitionOfArray[j + 1] = temp;
                }
                else{
                    break;
                }
            }
        }

        return partitionOfArray;
    }

    public void resetToUnsortedArray(){
        users = Arrays.copyOf(usersUnsorted, totalNumberOfUsers);
    }

    private User[] ArrayListToArray(ArrayList<User> arrayList){
        User[] array = new User[arrayList.size()];

        for(int i = 0; i < arrayList.size(); i++){
            array[i] = arrayList.get(i);
        }

        return array;
    }

    private User[] concatenateArrays(User[] array1, ArrayList<User> array2, User[] array3){
        User[] concatenatedArray = new User[array1.length + array2.size() + array3.length];

        for(int i = 0; i < array1.length; i++){
            concatenatedArray[i] = array1[i];
        }

        for(int i = 0; i < array2.size(); i++){
            concatenatedArray[array1.length + i] = array2.get(i);
        }

        for(int i = 0; i < array3.length; i++){
            concatenatedArray[array1.length + array2.size() + i] = array3[i];
        }   

        return concatenatedArray;
    }

    private void fillTheRestOfUsersArray(){
        this.users = Arrays.copyOf(usersUnsorted, USERS_ARRAY_LENGTH);
    }
}