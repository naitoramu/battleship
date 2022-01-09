package battleship.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import battleship.Main;
import battleship.classes.CustomButton;
import battleship.classes.Menu;
import battleship.classes.MenuVBox;
import battleship.classes.Player;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private StackPane menuStackPane;
    private Menu menu;

    private EventHandler<MouseEvent> buttonEventHandler;

    public MainMenuController() {
        
    }

    @FXML
    void initialize() throws IOException {
        titleLabel.setText("BATTLESHIP");
        errorLabel.setVisible(false);

        initializeButtonEventHandler();
        initializeMenu();
    }

    private void initializeButtonEventHandler() {
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

    private void initializeMenu() throws IOException {
        menu = new Menu(buttonEventHandler);
        menu.refresh();

        switch (Main.getMenuStartPage()) {
            case "main-menu":
                menuStackPane.getChildren().addAll(menu.getMainMenuVBox());
                break;

            case "change-passwd-menu":
                menuStackPane.getChildren().addAll(menu.getChangePasswdMenuVBox());
                Main.setMenuStartPage("main-menu");
                break;

            case "login-menu":
                menuStackPane.getChildren().addAll(menu.getLogInMenuVBox());
                Main.setMenuStartPage("main-menu");
                break;

            case "register-menu":
                menuStackPane.getChildren().addAll(menu.getRegisterMenuVBox());
                Main.setMenuStartPage("main-menu");
                break;
        }
    }

    private void handleButton(CustomButton btn) throws IOException, NoSuchAlgorithmException {

        String buttonName = btn.getButtonName();

        switch (buttonName) {

            case "pvc":
                startGame(false, true, btn);
                break;

            case "pvp":
                startGame(false, false, btn);
                break;

            case "cvc":
                startGame(true, true, btn);
                break;

            case "rank":
                showRanking(btn);
                break;

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
                if (registrationSucceeded) {

                    System.out.println("Registration succeeded");
                    Main.loadDataFromDatabase();
                    if (Main.isAuthenticatePlayerTwo()) {
                        showPlayersSelection(btn);
                    } else {
                        switchMenuVBox(menu.getMainMenuVBox());
                        menu.refresh();
                    }
                    // menu.refresh();
                }
                break;

            case "log-in":
                boolean logInSucceeded = menu.getLogInMenuVBox().logInUser();
                if (logInSucceeded) {

                    System.out.println("Log in succeeded");
                    if (Main.isAuthenticatePlayerTwo()) {
                        showPlayersSelection(btn);
                    } else {
                        // Main.loadDataFromDatabase();
                        switchMenuVBox(menu.getMainMenuVBox());
                        menu.refresh();
                    }
                }
                break;

            case "log-out":
                Main.setUserLogedIn(false);
                Main.setLogedUser(null);
                menu.refresh();
                break;

            case "language":
                Main.setInterfaceLanguage(btn.getText());
                switchLanguage();
                switchMenuVBox(menu.getMainMenuVBox());
                System.out.println("Application interface language has been changed");
                break;

            case "my-account":
                showMyAccount(btn);
                break;

            case "submit-passwd-change":
                boolean passwdChangeSucceeded = menu.getChangePasswdMenuVBox().changePassword();
                if (passwdChangeSucceeded) {
                    Main.loadDataFromDatabase();
                    switchMenuVBox(menu.getMainMenuVBox());
                    menu.refresh();
                    System.out.println("Password changed succesfully");
                }
                break;

            case "back":
                if (menuStackPane.getChildren().get(0).equals(menu.getLogInMenuVBox()) ||
                        menuStackPane.getChildren().get(0).equals(menu.getRegisterMenuVBox())) {
                    switchMenuVBox(menu.getLogOrSignMenuVBox());
                } else {
                    switchMenuVBox(menu.getMainMenuVBox());
                }
                break;

            default:
                System.out.println("You pressed button '" + buttonName + "'");
                break;
        }
    }

    private void showPlayersSelection(CustomButton btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/playersSelection.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets()
                .add(getClass().getResource("/battleship/view/stylesheet/playersSelection.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);
        controller.startGame();
    }

    private void showRanking(CustomButton btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/rankingView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/ranking.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

    private void showMyAccount(CustomButton btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/myAccountView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets()
                .add(getClass().getResource("/battleship/view/stylesheet/myAccount.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

    private void switchMenuVBox(MenuVBox VBox) {
        menuStackPane.getChildren().clear();
        menuStackPane.getChildren().addAll(VBox);
    }

    private void switchLanguage() {
        menu.changeInterfaceLanguage(Main.getInterfaceLanguage());
        menu.refresh();
    }

    private void startGame(boolean isPlayerOneAI, boolean isPlayerTwoAI, CustomButton btn) throws IOException {
    
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/view/gameView.fxml"));
        Parent newRoot = fxmlLoader.load();

        GameController controller = fxmlLoader.getController();
        controller.setPlayers(new Player(isPlayerOneAI, controller), new Player(isPlayerTwoAI, controller));
        controller.startGame();

        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/game.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);
    }

}
