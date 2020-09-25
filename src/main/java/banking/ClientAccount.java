package banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class ClientAccount {

    private static long cardNumber = 0;
    private static int cardPinCode = 0;
    private static long balance = 0;

    private static String reciver = "";

    public static String getReciver() {
        return reciver;
    }

    public static void setReciver(String reciver) {
        ClientAccount.reciver = reciver;
    }


    public static long getCardNumber() {
        return cardNumber;
    }

    public static void setCardNumber(long cardNumber) {
        ClientAccount.cardNumber = cardNumber;
    }

    public static int getCardPinCode() {
        return cardPinCode;
    }

    public static void setCardPinCode(int cardPinCode) {
        ClientAccount.cardPinCode = cardPinCode;
    }

    public static long getBalance() {
        return balance;
    }

    public static void setBalance(long balance) {
        ClientAccount.balance = balance;
    }


    public static void createAccount() {
        creatCardNumber();
        createCardPIN();
        if (CardTable.selectAllDataFromNumberColume(String.valueOf(getCardNumber()))) {
            insertIntoTable();
        }
        System.out.println("Your card has been created");
        System.out.println(getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(getCardPinCode());
        System.out.println();
        BankScreens.mainScreen();
    }


    public static void insertIntoTable() {
        CardTable.insertDataIntoCardsTable(String.valueOf(getCardNumber()),
                String.valueOf(getCardPinCode()),
                (int) getBalance());
    }

    public static void creatCardNumber() {

        long bankID = 400000;
        Random random = new Random();
        long randomCardNumber = random.nextInt(999999999 - 111111111 + 1) + 111111111;
        //long randomCardNumber = (long) (Math.random() * 1100000000 + 1);
        long checksum = 3;

        String strBankID = Long.toString(bankID);
        String strRandomCardNumberResult = Long.toString(randomCardNumber);
        String strChecksumResult = Long.toString(checksum);
        String cardNumberResult = "";
        if (strRandomCardNumberResult.length() == 9) {
            cardNumberResult = strBankID + strRandomCardNumberResult + strChecksumResult;
            setCardNumber(Long.parseLong(cardNumberResult));
        }

        if (luhnAlgorithm(cardNumberResult)) {
            setCardNumber(Long.parseLong(cardNumberResult));
        } else {
            creatCardNumber();
        }

    }

    public static void createCardPIN() {
        int randomPIN = (int) (Math.random() * 10000 + 1);
        if (randomPIN >= 1000 && randomPIN <= 9999) {
            setCardPinCode(randomPIN);
        } else {
            createCardPIN();
        }
    }



    public static boolean luhnAlgorithm(String num) {

        String number = String.valueOf(num);
        int[] nums = new int[15];

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(String.valueOf(number.charAt(i)));

            if (i % 2 == 0) {
                nums[i] = nums[i] * 2;
            }
            if (nums[i] > 9) {
                nums[i] = nums[i] - 9;
            }
            sum = nums[i] + sum;
        }

        int sumAndScheckSum = sum + 3;
        return sumAndScheckSum % 10 == 0;
    }



    public static void balance() {
        boolean check = CardTable.cardVerification(String.valueOf(BankScreens.getInputCardNumber()), String.valueOf(BankScreens.getInputPinCode()));
        if (check) {
            try {
                int balance = 0;
                int count = 0;
                ResultSet resultSet = SqliteDB.getStatement().executeQuery("select balance\n" +
                        "from card\n" +
                        "where number = " + BankScreens.getInputCardNumber() + "\n" +
                        "and pin =" + BankScreens.getInputPinCode() + ";\n");

                while (resultSet.next()) {
                    balance = resultSet.getInt("balance");
                    count++;
                }

                if (count == 1) {
                    setBalance(balance);
                    System.out.println("Balance: "+ balance);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
    }



    public static void addIncome(int addIncome) {

        boolean check = CardTable.cardVerification(String.valueOf(BankScreens.getInputCardNumber()), String.valueOf(BankScreens.getInputPinCode()));
        if (check) {
            try {
                SqliteDB.getStatement().executeUpdate(" update card set balance = " + addIncome +
                        " WHERE number =" + BankScreens.getInputCardNumber() +
                        " AND pin = "+BankScreens.getInputPinCode() + " ;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void doTransfer() {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        System.out.print(">");
        String recievedCardNumber = new java.util.Scanner(System.in).next();
        setReciver(recievedCardNumber);
        System.out.println();

        if (!luhnAlgorithm(recievedCardNumber)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            BankScreens.login();
        }

        if (CardTable.cardIsAvaliable(recievedCardNumber)
            && !String.valueOf(BankScreens.getInputCardNumber()).equals(recievedCardNumber)) {
            System.out.println("Enter how much money you want to transfer:");
            System.out.print(">");
            int amount = new java.util.Scanner(System.in).nextInt();
            CardTable.getCardBalance(String.valueOf(BankScreens.getInputCardNumber()),
                                     String.valueOf(BankScreens.getInputPinCode()),
                                     amount);
        }

        if (!CardTable.cardIsAvaliable(recievedCardNumber)) {
            System.out.println("Such a card does not exist.");
            BankScreens.login();
        }
    }


    public static void closeAccount(String num) {
        CardTable.deleteAccount(num);
    }


} // The End Of ClientAccount Class
