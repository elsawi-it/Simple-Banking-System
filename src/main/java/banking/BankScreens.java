package banking;

public class BankScreens {

    private static long inputCardNumber = 0;
    private static int inputPinCode = 0;

    public static void setInputCardNumber(long cardNumber) {
        inputCardNumber = cardNumber;
    }
    public static void setInputPinCode(int PinCode) {
        inputPinCode = PinCode;
    }
    public static long getInputCardNumber() {
        return inputCardNumber;
    }
    public static int getInputPinCode() {
        return inputPinCode;
    }


    public static void mainScreen() {
        System.out.println();
        SqliteDB.sqliteDBConnection();
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        System.out.print(">");

        int input = new java.util.Scanner(System.in).nextInt();
        System.out.println();
        if (input == 1) {
            ClientAccount.createAccount();
        } else if (input == 2) {
            logIntoAccount();
        } else if (input == 0) {
            exit();

        } else {
            mainScreen();
        }
        System.out.println();
    }


    public static void logIntoAccount() {

        System.out.println("Enter your card number:");
        System.out.print(">");
        BankScreens.setInputCardNumber(new java.util.Scanner(System.in).nextLong());
        System.out.println("Enter your PIN:");
        System.out.print(">");
        BankScreens.setInputPinCode(new java.util.Scanner(System.in).nextInt());

        if (CardTable.cardVerification(String.valueOf(BankScreens.getInputCardNumber()),String.valueOf(BankScreens.getInputPinCode()))) {
            System.out.println("You have successfully logged in! \n");
            login();
        } else {
            System.out.println("Wrong card number or PIN! \n");
            mainScreen();
        }
    }


    public static void login() {

        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        System.out.print(">");

        int input = new java.util.Scanner(System.in).nextInt();
        System.out.println();

        if (input == 1) {
            ClientAccount.balance();
            System.out.println();
            login();
        } else if (input == 2) {
            System.out.println("Enter income: ");
            System.out.print(">");
            int addIncome = new java.util.Scanner(System.in).nextInt();
            ClientAccount.addIncome(addIncome);
            System.out.println("Income was added! \n");
            login();
        } else if (input == 3) {
            ClientAccount.doTransfer();
        } else if (input == 4) {
            ClientAccount.closeAccount(String.valueOf(BankScreens.getInputCardNumber()));
        } else if (input == 5) {
            logOut();
            System.out.println();
            mainScreen();
        } else if (input == 0) {
            exit();
        } else {
            login();
        }
    }

    public static void logOut() {
        System.out.println("You have successfully logged out!");
    }


    public static void exit() {
        SqliteDB.closeSqliteDBConnection();
        System.out.println("Bye!");
    }



} // The End Of BankScreens Class