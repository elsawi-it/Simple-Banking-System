package banking;

public class Main {

    public static void main(String[] args) {

        SqliteDB.databaseName(String.valueOf(args[1]));
        SqliteDB.sqliteDBConnection();
        BankScreens.mainScreen();
        SqliteDB.closeSqliteDBConnection();

    }

}

