package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteDB {


    private static Connection con = null;
    private static Statement statement = null;
    private static final String databaseName = "card.sqlite";


    public static void setStatement(Statement statement) {
        SqliteDB.statement = statement;
    }
    public static void setCon(Connection con) {
        SqliteDB.con = con;
    }
    public static Statement getStatement() {
        return statement;
    }

    /**
     * if want to create your a new specific database name by invoking its name
     * from the main method use this databaseName(String fileName) method.

    //->  public static void databaseName(String fileName) { databaseName = fileName; }

    */

    public static void sqliteDBConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + databaseName;
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(url);
            con = dataSource.getConnection();
            statement = con.createStatement();
            setStatement(statement);

            if (con.isValid(5)) {
                    CardTable.createCardTable();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setCon(con);
    } // The End Of sqliteDBConnection Method


    public static void closeSqliteDBConnection() {
        try {
            if (con.isValid(3)) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("ClassNotFoundException of org.sqlite.JDBC");
            //e.printStackTrace();
        }
    } // The End Of closeSqliteDBConnection Method

} // The End Of SqliteDB Class