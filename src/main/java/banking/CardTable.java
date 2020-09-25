package banking;

import java.sql.*;

public class CardTable extends SqliteDB {

    public static void createCardTable() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER NOT NULL constraint cards_pk primary key autoincrement," +
                    "number TEXT NOT NULL unique," +
                    "pin TEXT NOT NULL," +
                    "balance INTEGER DEFAULT 0)");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    } // The End Of createCardTable Method

    public static boolean selectAllDataFromNumberColume(String currentNum)  {
        boolean check = true;
        try {
            ResultSet resultSet = getStatement().executeQuery("select number from card where number =" + currentNum);
            while (resultSet.next()) {

                String number = resultSet.getString("number");
                if (!currentNum.equals(number)) {
                    check = true;
                } else {
                    check = false;
                    ClientAccount.createAccount();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    } // The End Of selectAllDataFromCardsTable Method


    public static void insertDataIntoCardsTable(String num, String pin, int balance) {
        try {
            getStatement().executeUpdate("insert into card (number, pin, balance) VALUES (" + num + "," + pin + "," + balance + " );");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // The End Of insertDataIntoCardsTable Method


    public static boolean cardVerification(String num, String pin) {
        try {
            boolean quary = getStatement().execute("select number, pin\n" +
                    "from card\n" +
                    "where number = " + num + "\n" +
                    "and pin =" + pin + ";\n");

            ResultSet resultSet = getStatement().executeQuery("select number, pin\n" +
                    "from card\n" +
                    "where number = " + num + "\n" +
                    "and pin =" + pin + ";\n");

            int count = 0;
            while (resultSet.next()) {
                String number = resultSet.getString("number");
                String pinn = resultSet.getString("pin");
                if (num.equals(number) && pin.equals(pinn)) {
                    count++;
                }
            }
            return quary && count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }// The End Of CardVerification Method


    public static boolean cardIsAvaliable(String num) {
        try {
            ResultSet resultSet = getStatement().executeQuery("select number from card where number =" + num);
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            if (count == 0) {
                return false;
            }
            if (count == 1) {
                return true;
            } else {
                System.out.println("somthing Wrong!!");
            }

        } catch (SQLException e) {
            e.getMessage();
        }
        return false;
    } // The End Of cardIsAvaliable Method


    public static void getCardBalance(String num, String pin, int amount) {
        try {
            int blance = 0;
            int reciverBlance = 0;
            ResultSet resultSet = getStatement().executeQuery("select balance from card where number = " + num +
                    " AND pin = "+ pin +" ;");
            while (resultSet.next()) {
                blance = resultSet.getInt("balance");
            }
            if (amount > blance) {
                System.out.println("Not enough money!");
                BankScreens.login();
            } else {
                try {
                    int minus = blance - amount;
                    SqliteDB.getStatement().executeUpdate(" update card set balance = " + minus +
                            " WHERE number = " + BankScreens.getInputCardNumber() +
                            " AND pin = "+BankScreens.getInputPinCode() + " ;");

                    resultSet = getStatement().executeQuery("select balance from card where number = " + ClientAccount.getReciver() + " ;");
                    int count = 0;
                    while (resultSet.next()) {
                        reciverBlance = resultSet.getInt("balance");
                        count++;
                    }
                    if (count == 1) {
                        int sum = reciverBlance + amount;
                        SqliteDB.getStatement().executeUpdate(" update card set balance = " + sum +
                                " WHERE number = " + ClientAccount.getReciver() + " ;");
                        System.out.println("Success!");
                        BankScreens.login();
                    } else {
                        System.out.println("Somthing wrong!");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    } // The End Of getCardBalance Method


    public static void deleteAccount(String num) {
        try {
               SqliteDB.getStatement().executeUpdate("delete from card where number = " + num + " ;");
                System.out.println("The account has been closed! \n");
                BankScreens.mainScreen();

        } catch (SQLException e) {
            e.getMessage();
        }
    } // The End Of deleteAccount Method


} // The End Of CardTable Class




// -------------------------------------------------------------------------------------
// -- Those hidden methods may used in the future!
// -------------------------------------------------------------------------------------

    /*
    public void selectAllDataFromCardsTable()  {
        try {
            ResultSet resultSet = getStatement().executeQuery("select * from card ");
            System.out.println("id \t    number \t\t\tpin \tbalance");
            System.out.println("---------------------------------------");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String pin = resultSet.getString("pin");
                int balance = resultSet.getInt("balance");
                System.out.printf("%d\t%s\t%s\t%d%n", id, number, pin, balance);
                System.out.println(".........................................");
            }
           // getCon().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // The End Of selectAllDataFromCardsTable Method

     */


// -------------------------------------------------------------------------------------

