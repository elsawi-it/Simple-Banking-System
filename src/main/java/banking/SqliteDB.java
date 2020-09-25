package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class SqliteDB {


    private static Connection con = null;
    private static Statement statement = null;
    private static String databaseFileName = null;


    public static void setStatement(Statement statement) {
        SqliteDB.statement = statement;
    }
    public static void setCon(Connection con) {
        SqliteDB.con = con;
    }
    public static Connection getCon() {
        return con;
    }
    public static Statement getStatement() {
        return statement;
    }

    public static void databaseName(String fileName) {
        databaseFileName = fileName;

    }
    public static void sqliteDBConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + databaseFileName;
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