package battleship.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import battleship.Main;
import battleship.classes.CSVDictReader;
import battleship.classes.DifficultyLevel;
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

    private static final String ROOT_PATH = Main.getRootPath();
    private CSVDictReader dictionary;

    public PlayersSelectionController() {
        dictionary = Main.getDictionary();
        playerOneUsernameLabel = new Label();
        playerTwoUsernameLabel = new Label();

    }

    @FXML
    void initialize() throws IOException {

        backButton.setText(dictionary.getLabelByName("back").get(Main.getInterfaceLanguage()));
        startButton.setText(dictionary.getLabelByName("start-game").get(Main.getInterfaceLanguage()));
        startButton.setDisable(true);
        titleLabel.setText(dictionary.getLabelByName(Main.getGameMode()).get(Main.getInterfaceLanguage()));

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

        playerOneImageView.setImage(loadAvatar(ROOT_PATH + "/src/battleship/avatar/human-avatar.png"));
        playerOneUsernameLabel.setText(Main.getPlayerOne().getUsername());
        playerOneVBox.getChildren().add(playerOneUsernameLabel);

        refresh();

    }

    private void initializePlayerTwoAsHuman() throws FileNotFoundException {

        playerTwoImageView.setImage(loadAvatar(ROOT_PATH + "/src/battleship/avatar/human-avatar.png"));

        if (Main.getPlayerTwo() != null) {

            playerTwoUsernameLabel.setText(Main.getPlayerTwo().getUsername());
            playerTwoVBox.getChildren().add(playerTwoUsernameLabel);
            playerTwoVBox.getChildren().add(generateChangePlayerTwoButton());

        } else {

            playerTwoVBox.getChildren().addAll(generateLogInAndRegisterButtons());

        }

        refresh();

    }

    private void initializePlayerOneAsComputer() throws FileNotFoundException {

        playerOneImageView.setImage(loadAvatar(ROOT_PATH + "/src/battleship/avatar/computer-avatar.png"));
        playerOneUsernameLabel.setText(dictionary.getLabelByName("computer").get(Main.getInterfaceLanguage()));

        playerOneVBox.getChildren().add(playerOneUsernameLabel);
        playerOneVBox.getChildren().add(generateChooseDiffLvlHBox(true));

    }

    private void initializePlayerTwoAsComputer() throws FileNotFoundException {

        playerTwoImageView.setImage(loadAvatar(ROOT_PATH + "/src/battleship/avatar/computer-avatar.png"));
        playerTwoUsernameLabel.setText(dictionary.getLabelByName("computer").get(Main.getInterfaceLanguage()));

        playerTwoVBox.getChildren().add(playerTwoUsernameLabel);
        playerTwoVBox.getChildren().add(generateChooseDiffLvlHBox(false));

    }

    private Button generateChangePlayerTwoButton() {
        Button changePlayerTwoButton = new Button();
        changePlayerTwoButton.setText(dictionary.getLabelByName("change-player").get(Main.getInterfaceLanguage()));
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

        return changePlayerTwoButton;
    }

    private ArrayList<Button> generateLogInAndRegisterButtons() {

        Button logInButton = new Button();
        Button registerButton = new Button();

        logInButton.setText(dictionary.getLabelByName("log-in").get(Main.getInterfaceLanguage()));
        registerButton.setText(dictionary.getLabelByName("register").get(Main.getInterfaceLanguage()));

        logInButton.setOnMouseClicked(event -> {
            try {
                showLogInMenu((Button) event.getSource());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        registerButton.setOnMouseClicked(event -> {
            try {
                showRegisterMenu((Button) event.getSource());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return new ArrayList<Button>(Arrays.asList(logInButton, registerButton));
    }

    private HBox generateChooseDiffLvlHBox(boolean isThisForPlayerOne) {
        ChoiceBox<DifficultyLevel> difficultyLevelChoiceBox = new ChoiceBox();
        Label chooseDifficultyLevelLabel = new Label();
        HBox hbox = new HBox();

        difficultyLevelChoiceBox.getItems().addAll(generateDifficultyLevels());

        chooseDifficultyLevelLabel
                .setText(dictionary.getLabelByName("select-diff-lvl").get(Main.getInterfaceLanguage()));

        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().add(chooseDifficultyLevelLabel);
        hbox.getChildren().add(difficultyLevelChoiceBox);

        if (isThisForPlayerOne) {
            difficultyLevelChoiceBox.setOnAction(actionEvent -> setComputerDifficultyLevel(actionEvent, 1));
        } else {
            difficultyLevelChoiceBox.setOnAction(actionEvent -> setComputerDifficultyLevel(actionEvent, 2));
        }

        return hbox;
    }

    private ArrayList<DifficultyLevel> generateDifficultyLevels() {
        DifficultyLevel easyLevel = new DifficultyLevel("easy");
        DifficultyLevel mediumLevel = new DifficultyLevel("medium");
        DifficultyLevel hardLevel = new DifficultyLevel("hard");

        ArrayList<DifficultyLevel> difficultyLevels = new ArrayList<>();
        difficultyLevels.add(easyLevel);
        difficultyLevels.add(mediumLevel);
        difficultyLevels.add(hardLevel);

        return difficultyLevels;
    }

    private void setComputerDifficultyLevel(ActionEvent actionEvent, int playerNumber) {

        DifficultyLevel difficultyLevel = (DifficultyLevel) ((ChoiceBox<DifficultyLevel>) actionEvent.getSource())
                .getValue();

        if (playerNumber == 1) {
            Main.setPlayerOneDifficultyLevel(difficultyLevel);
        } else if (playerNumber == 2) {
            Main.setPlayerTwoDifficultyLevel(difficultyLevel);
        } else {
            System.err.println("Not appropriate player number");
        }
        refresh();
    }

    private void refresh() {
        if (Main.isPlayerOneIsHuman() && Main.isPlayerTwoIsHuman()) {
            if (Main.getPlayerOne() != null && Main.getPlayerTwo() != null) {
                startButton.setDisable(false);
            } else {
                startButton.setDisable(true);
            }
        } else if (!Main.isPlayerOneIsHuman() && !Main.isPlayerTwoIsHuman()) {
            if (Main.getPlayerOneDifficultyLevel() != null && Main.getPlayerTwoDifficultyLevel() != null) {
                startButton.setDisable(false);
            } else {
                startButton.setDisable(true);
            }
        } else {
            if (Main.getPlayerOne() != null && Main.getPlayerTwoDifficultyLevel() != null) {
                startButton.setDisable(false);
            } else {
                startButton.setDisable(true);
            }
        }

        System.out.println("P1 " + Main.getPlayerOneDifficultyLevel());
        System.out.println("P2 " + Main.getPlayerTwoDifficultyLevel());
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

    private Image loadAvatar(String pathToImg) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(pathToImg);
        return new Image(input);
    }

    public void startButtonPressed(ActionEvent actionEvent) throws IOException {
        startGame((Button) actionEvent.getSource());
    }

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        resetPlayersDifficultyLevels();
        backToMainMenu((Button) actionEvent.getSource());
    }

    private void resetPlayersDifficultyLevels() {
        Main.setPlayerOneDifficultyLevel(null);
        Main.setPlayerTwoDifficultyLevel(null);
    }

    private void startGame(Button btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/gameView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/game.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

    // private void startGame(Button btn) throws IOException {

    //     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/view/gameView.fxml"));
    //     Parent newRoot = fxmlLoader.load();

    //     GameController controller = fxmlLoader.getController();
    //     controller.setPlayers(new Player(isPlayerOneAI, controller), new Player(isPlayerTwoAI, controller));
    //     controller.startGame();

    //     Scene scene = new Scene(newRoot);
    //     Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
    //     scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/game.css").toExternalForm());
    //     stageTheButtonBelongs.setScene(scene);
    // }

    private void backToMainMenu(Button btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/mainMenuView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/mainMenu.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

}
