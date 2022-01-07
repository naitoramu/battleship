package battleship;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import battleship.model.User;
import battleship.util.DBUtil;

public class Main extends Application {

    private static DBUtil db = new DBUtil();
    private static List<User> users;
    private static User logedUser = null;
    private static User PlayerOne = null;
    private static User PlayerTwo = null;

    private static boolean userLogedIn = false;
    private static boolean playerOneIsHuman;
    private static boolean playerTwoIsHuman;
    private static boolean authenticatePlayerTwo = false;
    private static String gameMode = null;
    private static String menuStartPage = "main-menu";
    private static String interfaceLanguage = "EN";

    public static void main(String[] args) throws Exception {

        loadDataFromDatabase();
        // if(users.get(0).getPassword().equals(users.get(1).getPassword())){
        // System.out.println("działa zajebiście");
        // } else {
        // System.out.println("nie działa ;(");
        // }
        // System.out.println(users.get(5).getPassword().length());

        // CSVDictReader labels = new CSVDictReader();
        // labels.laodCSVFile("/home/kuba/Git/battleship/src/battleship/lang/button-labels.csv");
        // System.out.println(labels.getLabelByName("pvp").get("PL"));

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                db.closeConnection();
                System.out.println("Database connection closed");
            }
        }));

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainMenuView.fxml"));
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.getScene().getStylesheets()
                .add(getClass().getResource("view/stylesheet/mainMenu.css").toExternalForm());
    }

    public static void loadDataFromDatabase() {
        setUsers(db.selectUsers());
        System.out.println(users);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        Main.users = users;
    }

    public static DBUtil getDB() {
        return db;
    }

    public static boolean isUserLogedIn() {
        return userLogedIn;
    }

    public static void setUserLogedIn(boolean isUserLogedIn) {
        Main.userLogedIn = isUserLogedIn;
    }

    public static String getInterfaceLanguage() {
        return Main.interfaceLanguage;
    }
    public static void setInterfaceLanguage(String interfaceLanguage) {
        Main.interfaceLanguage = interfaceLanguage;
    }

    public static User getLogedUser() {
        return logedUser;
    }
    public static void setLogedUser(User logedUser) {
        Main.logedUser = logedUser;
    }

    public static String getMenuStartPage() {
        return menuStartPage;
    }
    public static void setMenuStartPage(String menuStartPage) {
        Main.menuStartPage = menuStartPage;
    }

    public static String getGameMode() {
        return gameMode;
    }
    public static void setGameMode(String gameMode) {
        Main.gameMode = gameMode;
    }

    public static boolean isPlayerTwoIsHuman() {
        return playerTwoIsHuman;
    }
    public static void setPlayerTwoIsHuman(boolean playerTwoIsHuman) {
        Main.playerTwoIsHuman = playerTwoIsHuman;
    }

    public static boolean isPlayerOneIsHuman() {
        return playerOneIsHuman;
    }
    public static void setPlayerOneIsHuman(boolean playerOneIsHuman) {
        Main.playerOneIsHuman = playerOneIsHuman;
    }

    public static User getPlayerTwo() {
        return PlayerTwo;
    }
    public static void setPlayerTwo(User playerTwo) {
        PlayerTwo = playerTwo;
    }

    public static User getPlayerOne() {
        return PlayerOne;
    }
    public static void setPlayerOne(User playerOne) {
        PlayerOne = playerOne;
    }

    public static boolean isAuthenticatePlayerTwo() {
        return authenticatePlayerTwo;
    }
    public static void setAuthenticatePlayerTwo(boolean afterAuthRedirectToPvP) {
        Main.authenticatePlayerTwo = afterAuthRedirectToPvP;
    }
}