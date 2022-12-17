import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private String databaseUrl = null;
    private String databaseName = null;
    private Connection connection = null;

    public Database(String databaseName) {
        this.databaseName = databaseName;
        databaseUrl = "jdbc:sqlite:" + databaseName + ".db";
        try {
            connection = DriverManager.getConnection(databaseUrl);
            Statement statement = connection.createStatement();
            String sql = "create table if not exists users (" +
                    "id integer," +
                    "username text," +
                    "highscore integer," +
                    "curQuestionNum integer," +
                    "qurQuestionText text," +
                    "unique(id)" +
                    ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ResultSet getUsers() {
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from " + databaseName;
            ResultSet resultSet = statement.executeQuery(sql);

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLeaderboard() {
        try {
            String res = "";
            Statement statement = connection.createStatement();

            String sql = "select username, highscore from " + databaseName + " order by highscore desc";
            ResultSet rs = statement.executeQuery(sql);

            int rank = 1;
            while (rs.next()) {
                res += rank + ". " + rs.getString("username") + " – " + rs.getInt("highscore") + "\n";
                rank++;
            }

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserInfo(long userId, String username, int currentQuestionNumber, String qurrentQuestionText,
            int highScore) {
        try {
            Statement statement = connection.createStatement();
            String statementString = "insert into " + databaseName +
                    " (id, username, highscore, curQuestionNum, qurQuestionText) " +
                    "values (" +
                    userId + ", '" +
                    username + "', " + highScore + ", " + currentQuestionNumber + ", '" + qurrentQuestionText +
                    "') on conflict(id)" +
                    "do update set " +
                    "username = '" + username + "'" +
                    ", highscore = " + highScore +
                    ", curQuestionNum = " + currentQuestionNumber +
                    ", qurQuestionText = '" + qurrentQuestionText +
                    "' where id = " + userId;
            statement.executeUpdate(statementString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}