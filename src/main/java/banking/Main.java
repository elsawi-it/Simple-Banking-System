package banking;

public class Main {

    public static void main(String[] args) {

        SqliteDB.sqliteDBConnection();
        BankScreens.mainScreen();
        SqliteDB.closeSqliteDBConnection();


    }

}

