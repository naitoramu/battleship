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

    public static void main(String[] args) throws Exception {

        loadDataFromDatabase();
        System.out.println(users);

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

    private static void loadDataFromDatabase() {
        setUsers(db.selectUsers());
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        Main.users = users;
    }

    public static DBUtil getDB(){
        return db;
    }
}