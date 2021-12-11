package battleship.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import battleship.Main;
import battleship.classes.CustomButton;
import battleship.classes.Menu;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private VBox menuVBox;
    private Menu menu;

    private String rootPath;
    private EventHandler<MouseEvent> buttonEventHandler;

    public MainMenuController(){
        rootPath = System.getProperty("user.dir");
    }

    @FXML
    void initialize() throws IOException{
        titleLabel.setText("BATTLESHIP");
        errorLabel.setVisible(false);
        
        initializeButtonEventHandler();
        initializeMenu();
    }

    private void initializeButtonEventHandler(){
        buttonEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CustomButton btn = (CustomButton) event.getSource();
                try {
                    handleButton(btn);
                } catch (IOException | NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    }

    private void initializeMenu() throws IOException{
        menu = new Menu(buttonEventHandler, rootPath);
        switchMenuVBox(menu.getMainMenuVBox());
    }

    private void handleButton(CustomButton btn) throws IOException, NoSuchAlgorithmException{

        String buttonName = btn.getButtonName();

        switch(buttonName){

            case "pvc":
            startGame(btn);

            case "change-lang":
            switchMenuVBox(menu.getChangeLangMenuVBox());
            System.out.println("Menu VBox has been changed");
            break;

            case "log-or-reg":
            switchMenuVBox(menu.getLogOrSignMenuVBox());
            break;

            case "exit":
            Platform.exit();
            break;

            case "login":
            switchMenuVBox(menu.getLogInMenuVBox());
            break;

            case "registration":
            switchMenuVBox(menu.getRegisterMenuVBox());
            break;

            case "register":
            boolean registrationSucceeded = menu.getRegisterMenuVBox().registerNewUser();
            if(registrationSucceeded){
                switchMenuVBox(menu.getMainMenuVBox());
                Main.setUserLogedIn(true);
                menu.refresh();
                Main.loadDataFromDatabase();
                System.out.println("Registration succeeded");
            }
            break;

            case "log-in":
            boolean logInSucceeded = menu.getLogInMenuVBox().logInUser();
            if(logInSucceeded){
                switchMenuVBox(menu.getMainMenuVBox());
                Main.setUserLogedIn(true);
                menu.refresh();
                System.out.println("Log in succeeded");
            }
            break;

            case "log-out":
            Main.setUserLogedIn(false);
            menu.refresh();
            break;

            case "language":
            switchLanguage(btn.getText());
            switchMenuVBox(menu.getMainMenuVBox());
            System.out.println("Application interface language has been changed");
            break;

            default:
            System.out.println("You pressed button nr. " + buttonName);
            break;
        }
    }

    private void startGame(CustomButton btn) throws IOException{
        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/gameView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/game.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);
    }

    private void switchMenuVBox(VBox VBox){
        VBox newVBox = new VBox(VBox);
        menuVBox.getChildren().clear();
        menuVBox.getChildren().addAll(newVBox.getChildren());
    }

    private void switchLanguage(String lang){
        menu.changeInterfaceLanguage(lang);
        menu.refresh();
    }

}
