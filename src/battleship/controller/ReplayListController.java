package battleship.controller;

import battleship.Main;
import battleship.classes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class ReplayListController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    TableView table;
    @FXML
    TableColumn<GameRecordTableRow, String> filenameColumn;
    @FXML
    TableColumn<GameRecordTableRow, String> playerOneColumn;
    @FXML
    TableColumn<GameRecordTableRow, String> playerTwoColumn;
    @FXML
    TableColumn<GameRecordTableRow, String> winnerColumn;
    @FXML
    TableColumn<GameRecordTableRow, Integer> movesColumn;


    @FXML
    void initialize() {
        table.setRowFactory(tv -> {
            TableRow<GameRecordTableRow> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    GameRecordTableRow recorderRow = row.getItem();
                    showReplay(recorderRow.getRecord());
                }
            });
            return row;
        });

        filenameColumn.setCellValueFactory(new PropertyValueFactory<>("playerOne"));
        playerOneColumn.setCellValueFactory(new PropertyValueFactory<>("playerOne"));
        playerTwoColumn.setCellValueFactory(new PropertyValueFactory<>("playerTwo"));
        winnerColumn.setCellValueFactory(new PropertyValueFactory<>("winner"));
        movesColumn.setCellValueFactory(new PropertyValueFactory<>("movesCount"));

        for (GameRecorder record : GameRecorder.readAll()) {
            table.getItems().add(new GameRecordTableRow(record));
        }
    }

    public void backToMainMenu() {
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

    public void showReplay(GameRecorder recorder) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/battleship/view/replayView.fxml"));
        try {
            Parent newRoot = fxmlLoader.load();
            ReplayController controller = fxmlLoader.getController();
            controller.prepareReplay(recorder);
            Scene scene = new Scene(newRoot);
            scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/game.css").toExternalForm());

            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            justExit();
        }
    }

    public void justExit() {
        Platform.exit();
    }

}
