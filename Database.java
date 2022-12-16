import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private String databaseUrl = null;
    private String databaseName = null;
    private Connection connection = null;

    Database(String databaseName) {
        this.databaseName = databaseName;
        databaseUrl = "jdbc:sqlite:" + databaseName + ".db";
        try {
            connection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public <T> int insert(String[] columns, T[] values) {
        if (columns.length != 0 && values.length != 0) {
            try {
                Statement statement = connection.createStatement();
                String statementString = "insert into " + databaseName + " values (";
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return 0;
    }
}