package battleship.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import battleship.model.User;
import battleship.model.Statistics;

public class DBUtil {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:database.db";

    private Connection userConnection;
    private Connection statisticsConnection;
    private Statement usersStatement;
    private Statement statisticsStatement;

    public DBUtil() {
        try {
            Class.forName(DBUtil.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not found");
            e.printStackTrace();
        }

        try {
            userConnection = DriverManager.getConnection(DB_URL);
            usersStatement = userConnection.createStatement();
            statisticsConnection = DriverManager.getConnection(DB_URL);
            statisticsStatement = statisticsConnection.createStatement();
        } catch (SQLException e) {
            System.err.println("Error while connecting to database");
            e.printStackTrace();
        }

        createTables();
    }

    public void openConnection(){
        try {
            userConnection = DriverManager.getConnection(DB_URL);
            usersStatement = userConnection.createStatement();
            statisticsStatement = DriverManager.getConnection(DB_URL).createStatement();
        } catch (SQLException e) {
            System.err.println("Error while connecting to database");
            e.printStackTrace();
        }
    }

    public boolean createTables() {

        String createUsers = """
            CREATE TABLE IF NOT EXISTS users (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
                username varchar(255) NOT NULL UNIQUE,
                password varchar(64) NOT NULL
            )""";

        String createDifficultyLevels = """
            CREATE TABLE IF NOT EXISTS difficulty_levels (
                level_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
                name varchar(32) NOT NULL UNIQUE
            )""";

        String createStatistics = """
            CREATE TABLE IF NOT EXISTS statistics (
                stats_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                difficulty_level varchar(32) NOT NULL,
                number_of_games INTEGER NOT NULL DEFAULT 0,
                number_of_wins INTEGER NOT NULL DEFAULT 0,
                number_of_losts INTEGER NOT NULL DEFAULT 0,
                number_of_shoots INTEGER NOT NULL DEFAULT 0,
                number_of_hits INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY(user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE SET NULL,
                FOREIGN KEY(difficulty_level) REFERENCES difficulty_levels(name) ON UPDATE CASCADE ON DELETE SET NULL,
                UNIQUE(user_id, difficulty_level)
            )""";

        String createTrigger = """
            CREATE TRIGGER IF NOT EXISTS users_insert
            AFTER INSERT ON users
            BEGIN
                INSERT INTO statistics(user_id, difficulty_level)
                VALUES(NEW.user_id, 'EASY');
                INSERT INTO statistics(user_id, difficulty_level)
                VALUES(NEW.user_id, 'MEDIUM');
                INSERT INTO statistics(user_id, difficulty_level)
                VALUES(NEW.user_id, 'HARD');
            END
            """;

        try {
            usersStatement.execute(createUsers);
            usersStatement.execute(createDifficultyLevels);
            usersStatement.execute(createStatistics);
            usersStatement.execute(createTrigger);
            
            insertDifficultyLevel("EASY");
            insertDifficultyLevel("MEDIUM");
            insertDifficultyLevel("HARD");

        } catch (SQLException e) {
            System.err.println("Error while creating tables");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertDifficultyLevel(String name) {
        try {
            PreparedStatement prepStmt = userConnection.prepareStatement("""
                INSERT OR IGNORE INTO difficulty_levels(name)
                VALUES (?);
            """);
            prepStmt.setString(1, name);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error while inserting difficulty level");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertUser(String username, String password) {
        try {
            PreparedStatement prepStmt = userConnection.prepareStatement("""
                INSERT INTO users
                VALUES (NULL, ?, ?);
            """);
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

    public boolean updateUserPassword(String username, String password) {
        try {
            PreparedStatement prepStmt = userConnection.prepareStatement("""
                UPDATE users
                SET password = ?
                WHERE username = ?;
            """);
            prepStmt.setString(1, password);
            prepStmt.setString(2, username);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error while insert user");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<User> selectUsers() {
        List<User> users = new LinkedList<User>();
        ResultSet result = null;
        try {
            result = usersStatement.executeQuery("SELECT * FROM users");
            int userID;
            String username, password;
            Statistics easyLvlStatistics, mediumLvlStatistics, hardLvlStatistics;
            while (result.next()) {
                userID = result.getInt("user_id");
                username = result.getString("username");
                password = result.getString("password");
                easyLvlStatistics = selectStatisticsByID(userID, "EASY");
                mediumLvlStatistics = selectStatisticsByID(userID, "MEDIUM");
                hardLvlStatistics = selectStatisticsByID(userID, "HARD");
                users.add(new User(userID, username, password, easyLvlStatistics, mediumLvlStatistics, hardLvlStatistics));
            }
        } catch (SQLException e) {
            System.err.println("Error while selecting users");
            e.printStackTrace();
            return null;
        } finally {
            if(result != null){
                try {
                    result.close();    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    private Statistics selectStatisticsByID(int userID, String difficultyLevel) throws SQLException {
        Statistics statistics;
        ResultSet result = null;
        try {
            result = statisticsStatement.executeQuery("SELECT * FROM statistics WHERE user_id = " + userID + " AND difficulty_level = '" + difficultyLevel + "'");
            int numberOfWins, numberOfLosts, numberOfGames, numberOfShoots, numberOfHits;
            if (result.next()) {
                numberOfGames = result.getInt("number_of_games");
                numberOfWins = result.getInt("number_of_wins");
                numberOfLosts = result.getInt("number_of_losts");
                numberOfShoots = result.getInt("number_of_shoots");
                numberOfHits = result.getInt("number_of_hits");

                statistics = new Statistics(difficultyLevel, numberOfGames, numberOfWins, numberOfLosts, numberOfShoots, numberOfHits);
            } else {
                System.out.println("Statistics not found");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error while selecting statistics by user_id");
            e.printStackTrace();
            return null;
        } finally {
            if(result != null){
                try {
                    result.close();    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return statistics;
    }

    // public List<Statistics> selectKsiazki() throws SQLException {
    //     List<Statistics> statistics = new LinkedList<Statistics>();
    //     ResultSet result = null;
    //     try {
    //         result = usersStatement.executeQuery("SELECT * FROM ksiazki");
    //         int userID, numberOfWins, numberOfLosts;
    //         while (result.next()) {
    //             userID = result.getInt("user_id");
    //             numberOfWins = result.getInt("number_of_wins");
    //             numberOfLosts = result.getInt("number_of_losts");
    //             statistics.add(new Statistics(numberOfWins, numberOfLosts));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null;
    //     } finally {
    //         if(result != null){
    //             try {
    //                 result.close();    
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    //     return statistics;
    // }

    public void closeConnection() {
        try {
            userConnection.close();
            statisticsConnection.close();
        } catch (SQLException e) {
            System.err.println("Error while closing connection with database");
            e.printStackTrace();
        }
    }
}
