package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.User;
import model.Statistics;

public class DBUtil {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:database.db";

    private Connection connection;
    private Statement usersStatement;
    private Statement statisticsStatement;

    public DBUtil() {
        try {
            Class.forName(DBUtil.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(DB_URL);
            usersStatement = connection.createStatement();
            statisticsStatement = DriverManager.getConnection(DB_URL).createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }

        createTables();
    }

    public boolean createTables() {
        String createUsers = "CREATE TABLE IF NOT EXISTS users (user_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, username varchar(255) NOT NULL UNIQUE, password varchar(32) NOT NULL)";
        String createStatistics = "CREATE TABLE IF NOT EXISTS statistics (stats_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, number_of_wins INTEGER NOT NULL DEFAULT 0, number_of_losts INTEGER NOT NULL DEFAULT 0, FOREIGN KEY(user_id) REFERENCES users(user_id))";
        String createTrigger = "CREATE TRIGGER IF NOT EXISTS users_insert AFTER INSERT ON users BEGIN INSERT INTO statistics(user_id) VALUES(NEW.user_id); END";
        try {
            usersStatement.execute(createUsers);
            usersStatement.execute(createStatistics);
            usersStatement.execute(createTrigger);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertUser(String username, String password) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement(
                    "insert into users values (NULL, ?, ?);");
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error while insert user");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertStatistics(int userID) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement(
                    "insert into ksiazki values (NULL, ?, NULL, NULL);");
            prepStmt.setInt(1, userID);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error while insert statistics");
            return false;
        }
        return true;
    }

    public List<User> selectUsers() {
        List<User> users = new LinkedList<User>();
        try {
            ResultSet result = usersStatement.executeQuery("SELECT * FROM users");
            int userID;
            String username, password;
            Statistics statistics;
            while (result.next()) {
                userID = result.getInt("user_id");
                username = result.getString("username");
                password = result.getString("password");
                statistics = selectStatisticsByID(userID);
                users.add(new User(userID, username, password, statistics));
            }
        } catch (SQLException e) {
            System.err.println("Error while selecting users");
            e.printStackTrace();
            return null;
        }
        return users;
    }

    private Statistics selectStatisticsByID(int userID) throws SQLException {
        Statistics statistics;
        try {
            ResultSet result = statisticsStatement.executeQuery("SELECT * FROM statistics WHERE user_id = " + userID);
            int numberOfWins, numberOfLosts;
            if (result.next()) {
                numberOfWins = result.getInt("number_of_wins");
                numberOfLosts = result.getInt("number_of_losts");
                statistics = new Statistics(numberOfWins, numberOfLosts);
            } else {
                System.out.println("Statistics not found");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error wile selecting statistics by user_id");
            e.printStackTrace();
            return null;
        }
        return statistics;
    }

    public List<Statistics> selectKsiazki() {
        List<Statistics> statistics = new LinkedList<Statistics>();
        try {
            ResultSet result = usersStatement.executeQuery("SELECT * FROM ksiazki");
            int userID, numberOfWins, numberOfLosts;
            while (result.next()) {
                userID = result.getInt("user_id");
                numberOfWins = result.getInt("number_of_wins");
                numberOfLosts = result.getInt("number_of_losts");
                statistics.add(new Statistics(numberOfWins, numberOfLosts));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return statistics;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}
