package battleship.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import battleship.Main;
import battleship.classes.CSVDictReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayersSelectionController {

    @FXML
    private Button backButton;
    @FXML
    private Button startButton;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView playerOneImageView;
    @FXML
    private ImageView playerTwoImageView;
    @FXML
    private VBox playerOneVBox;
    @FXML
    private VBox playerTwoVBox;

    private Label playerOneUsernameLabel;
    private Label playerTwoUsernameLabel;

    private String rootPath;
    private CSVDictReader buttonLabels;

    public PlayersSelectionController() {
        rootPath = System.getProperty("user.dir");
        playerOneUsernameLabel = new Label();
        playerTwoUsernameLabel = new Label();
    }

    @FXML
    void initialize() throws IOException {
        loadButtonLabels();
        backButton.setText(buttonLabels.getLabelByName("back").get(Main.getInterfaceLanguage()));
        startButton.setText(buttonLabels.getLabelByName("start-game").get(Main.getInterfaceLanguage()));
        startButton.setDisable(true);
        titleLabel.setText(buttonLabels.getLabelByName(Main.getGameMode()).get(Main.getInterfaceLanguage()));

        checkIfPlayersAreHumans();

        preparePlayersViewByPlayerType();

        System.out.println(Main.getGameMode());

    }

    private void checkIfPlayersAreHumans() {
        String gameMode = Main.getGameMode();

        if (gameMode.charAt(0) == 'p') {
            Main.setPlayerOneIsHuman(true);
        } else {
            Main.setPlayerOneIsHuman(false);
        }

        if (gameMode.charAt(2) == 'p') {
            Main.setPlayerTwoIsHuman(true);
        } else {
            Main.setPlayerTwoIsHuman(false);
        }
    }

    private void preparePlayersViewByPlayerType() throws FileNotFoundException {
        if (Main.isPlayerOneIsHuman()) {
            initializePlayerOneAsHuman();
        } else {
            initializePlayerOneAsComputer();
        }

        if (Main.isPlayerTwoIsHuman()) {
            initializePlayerTwoAsHuman();
        } else {
            initializePlayerTwoAsComputer();
        }
    }

    private void initializePlayerOneAsHuman() throws FileNotFoundException {

        Main.setPlayerOne(Main.getLogedUser());

        playerOneImageView.setImage(loadAvatar(rootPath + "/src/battleship/avatar/human-avatar.png"));
        playerOneUsernameLabel.setText(Main.getPlayerOne().getUsername());
        playerOneVBox.getChildren().add(playerOneUsernameLabel);

        refresh();

    }

    private void initializePlayerTwoAsHuman() throws FileNotFoundException {

        playerTwoImageView.setImage(loadAvatar(rootPath + "/src/battleship/avatar/human-avatar.png"));

        if (Main.getPlayerTwo() != null){
            playerTwoUsernameLabel.setText(Main.getPlayerTwo().getUsername());
            playerTwoVBox.getChildren().add(playerTwoUsernameLabel);
            Button changePlayerTwoButton = new Button();
            changePlayerTwoButton.setText(buttonLabels.getLabelByName("change-player").get(Main.getInterfaceLanguage()));
            changePlayerTwoButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Main.setPlayerTwo(null);
                    try {
                        playerTwoVBox.getChildren().remove(playerTwoUsernameLabel);
                        playerTwoVBox.getChildren().remove(changePlayerTwoButton);
                        initializePlayerTwoAsHuman();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }));

            playerTwoVBox.getChildren().add(changePlayerTwoButton);
        } else {

            Button logInButton = new Button();
            Button registerButton = new Button();

            logInButton.setText(buttonLabels.getLabelByName("log-in").get(Main.getInterfaceLanguage()));
            registerButton.setText(buttonLabels.getLabelByName("register").get(Main.getInterfaceLanguage()));

            logInButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    try {
                        showLogInMenu((Button) event.getSource());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }));

            registerButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    try {
                        showRegisterMenu((Button) event.getSource());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }));

            playerTwoVBox.getChildren().add(logInButton);
            playerTwoVBox.getChildren().add(registerButton);

        }

        refresh();

    }

    private void initializePlayerOneAsComputer() throws FileNotFoundException {
        playerOneImageView.setImage(loadAvatar(rootPath + "/src/battleship/avatar/computer-avatar.png"));
        playerOneUsernameLabel.setText("Computer");
        playerOneVBox.getChildren().add(playerOneUsernameLabel);

        ChoiceBox difficultyLevelChoiceBox = new ChoiceBox();
        difficultyLevelChoiceBox.getItems().add("EASY");
        difficultyLevelChoiceBox.getItems().add("MEDIUM");
        difficultyLevelChoiceBox.getItems().add("HARD");
        difficultyLevelChoiceBox.setValue("EASY");

        Label chooseDifficultyLevelLabel = new Label();
        chooseDifficultyLevelLabel.setText("Select difficulty level:");

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().add(chooseDifficultyLevelLabel);
        hbox.getChildren().add(difficultyLevelChoiceBox);

        playerOneVBox.getChildren().add(hbox);
    }

    private void initializePlayerTwoAsComputer() throws FileNotFoundException {
        playerTwoImageView.setImage(loadAvatar(rootPath + "/src/battleship/avatar/computer-avatar.png"));
        playerTwoUsernameLabel.setText("Computer");
        playerTwoVBox.getChildren().add(playerTwoUsernameLabel);

        ChoiceBox difficultyLevelChoiceBox = new ChoiceBox();
        difficultyLevelChoiceBox.getItems().add("EASY");
        difficultyLevelChoiceBox.getItems().add("MEDIUM");
        difficultyLevelChoiceBox.getItems().add("HARD");
        difficultyLevelChoiceBox.setValue("EASY");

        Label chooseDifficultyLevelLabel = new Label();
        chooseDifficultyLevelLabel.setText("Select difficulty level:");

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().add(chooseDifficultyLevelLabel);
        hbox.getChildren().add(difficultyLevelChoiceBox);

        playerTwoVBox.getChildren().add(hbox);
    }

    private void refresh() {
        if( Main.getPlayerOne() != null && Main.getPlayerTwo() != null ) {
            startButton.setDisable(false);
        } else {
            startButton.setDisable(true);
        }
    }

    private void showLogInMenu(Button btn) throws IOException {
        Main.setAuthenticatePlayerTwo(true);
        Main.setMenuStartPage("login-menu");
        backToMainMenu(btn);
    }

    private void showRegisterMenu(Button btn) throws IOException {
        Main.setAuthenticatePlayerTwo(true);
        Main.setMenuStartPage("register-menu");
        backToMainMenu(btn);
    }

    private void loadButtonLabels() throws IOException {

        String csvFilePath = rootPath + "/src/battleship/lang/button-labels.csv";
        try {
            buttonLabels = new CSVDictReader(csvFilePath);
        } catch (Exception IOException) {
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    private Image loadAvatar(String pathToImg) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(pathToImg);
        return new Image(input);
    }

    public void startButtonPressed(ActionEvent actionEvent) throws IOException {
        startGame((Button) actionEvent.getSource());
    }

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        backToMainMenu((Button) actionEvent.getSource());
    }

    private void startGame(Button btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/gameView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/game.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

    private void backToMainMenu(Button btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/mainMenuView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/mainMenu.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

}
