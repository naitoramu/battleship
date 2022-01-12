package battleship;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import battleship.classes.CSVDictReader;
import battleship.classes.DifficultyLevel;
import battleship.model.User;
import battleship.util.DBUtil;

public class Main extends Application {

    private static DBUtil db = new DBUtil();
    private static CSVDictReader dictionary;
    private static List<User> users;
    private static User logedUser = null;
    private static User playerOne = null;
    private static User playerTwo = null;
    private static DifficultyLevel playerOneDifficultyLevel;
    private static DifficultyLevel playerTwoDifficultyLevel;

    private static boolean userLogedIn = false;
    private static boolean playerOneIsHuman;
    private static boolean playerTwoIsHuman;
    private static boolean authenticatePlayerTwo = false;
    private static String gameMode = null;
    private static String menuStartPage = "main-menu";
    private static String interfaceLanguage = "EN";
    private static final String ROOT_PATH = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        loadDataFromDatabase();
        loadDictionary();
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

    private static void loadDictionary() throws IOException {

        String csvFilePath = ROOT_PATH + "/src/battleship/lang/dictionary.csv";
        try {
            dictionary = new CSVDictReader(csvFilePath);
        } catch (Exception IOException) {
            System.out.println("Cannot load file: " + csvFilePath);
        }
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
        return playerTwo;
    }

    public static void setPlayerTwo(User playerTwo) {
        Main.playerTwo = playerTwo;
    }

    public static DifficultyLevel getPlayerTwoDifficultyLevel() {
        return playerTwoDifficultyLevel;
    }

    public static void setPlayerTwoDifficultyLevel(DifficultyLevel difficultyLevel) {
        playerTwoDifficultyLevel = difficultyLevel;
    }

    public static User getPlayerOne() {
        return playerOne;
    }

    public static void setPlayerOne(User playerOne) {
        Main.playerOne = playerOne;
    }

    public static DifficultyLevel getPlayerOneDifficultyLevel() {
        return playerOneDifficultyLevel;
    }

    public static void setPlayerOneDifficultyLevel(DifficultyLevel difficultyLevel) {
        playerOneDifficultyLevel = difficultyLevel;
    }

    public static boolean isAuthenticatePlayerTwo() {
        return authenticatePlayerTwo;
    }

    public static void setAuthenticatePlayerTwo(boolean afterAuthRedirectToPvP) {
        Main.authenticatePlayerTwo = afterAuthRedirectToPvP;
    }

    public static CSVDictReader getDictionary() {
        return dictionary;
    }

    public static void setDictionary(CSVDictReader dictionary) {
        Main.dictionary = dictionary;
    }

    public static String getRootPath() {
        return ROOT_PATH;
    }
}