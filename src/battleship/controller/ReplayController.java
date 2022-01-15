package battleship.controller;

import battleship.Main;
import battleship.classes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ReplayController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    AnchorPane playerOnePane;
    @FXML
    AnchorPane playerTwoPane;
    @FXML
    Rectangle rectangleFieldA;
    @FXML
    Rectangle rectangleFieldB;
    @FXML
    Button startReplay;
    @FXML
    Button backButton;
    @FXML
    Button exitButton;
    @FXML
    Label playerOneBoardLabel;
    @FXML
    Label playerTwoBoardLabel;

    CSVDictReader dictionary = Main.getDictionary();

    Board playerOneBoard;
    Board playerTwoBoard;

    int playerOneId;
    int playerTwoId;

    Timeline timeline;

    public void prepareReplay(GameRecorder recorder) {
        labelingButtonsAndLabels();

        playerOneId = recorder.getPlayerOneId();
        playerTwoId = recorder.getPlayerTwoId();

        playerOneBoard = new Board();
        for (Coordinates coordinates : recorder.getPlayerOneShipsCoordinates()) {
            playerOneBoard.getAreas().get(coordinates).setState(Area.State.SHIP);
        }
        prepareAreas(playerOneBoard, playerOnePane);

        playerTwoBoard = new Board();
        for (Coordinates coordinates : recorder.getPlayerTwoShipsCoordinates()) {
            playerTwoBoard.getAreas().get(coordinates).setState(Area.State.SHIP);
        }
        prepareAreas(playerTwoBoard, playerTwoPane);

        timeline = new Timeline();
        for (int i = 0; i < recorder.getRecords().size(); i++) {
            final int index = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i + 1), e -> shoot(recorder.getRecords().get(index))));
        }
    }

    private void labelingButtonsAndLabels() {
        backButton.setText(dictionary.getLabelByName("back").get(Main.getInterfaceLanguage()));
        exitButton.setText(dictionary.getLabelByName("exit").get(Main.getInterfaceLanguage()));
        startReplay.setText(dictionary.getLabelByName("start-replay").get(Main.getInterfaceLanguage()));

        playerOneBoardLabel.setText(dictionary.getLabelByName("player-board").get(Main.getInterfaceLanguage()) + 1);
        playerTwoBoardLabel.setText(dictionary.getLabelByName("player-board").get(Main.getInterfaceLanguage()) + 2);
    }

    public void shoot(ShotRecord shot) {
        (shot.getTargetPlayerId() == playerOneId ? playerOneBoard : playerTwoBoard)
                .getAreas()
                .get(shot.getAreaCoordinates())
                .setHit();
    }

    public void prepareAreas(Board board, AnchorPane pane) {
        for (Area area : board.getAreas().values()) {
            pane.getChildren().add(area);
        }
    }

    public void handleStartButton() {
        startReplay.setDisable(true);
        timeline.play();
    }

    public void go_to_menu() {
        backToMainMenu();
    }

    private void backToMainMenu() {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/mainMenuView.fxml"));
            Scene scene = new Scene(newRoot);
            Stage stageTheButtonBelongs = (Stage) anchorPane.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/mainMenu.css").toExternalForm());
            stageTheButtonBelongs.setScene(scene);
        } catch (IOException e) {
            justExit();
        }
    }

    public void justExit() {
        Platform.exit();
    }

}
