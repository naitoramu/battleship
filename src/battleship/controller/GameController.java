package battleship.controller;

import battleship.Main;
import battleship.classes.*;
import javafx.application.Platform;
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

import java.io.IOException;
import java.util.*;


public class GameController {
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
    Button playerOneReadyButton;
    @FXML
    Button playerTwoReadyButton;
    @FXML
    Button playerOneAutoPosition;
    @FXML
    Button playerTwoAutoPosition;
    @FXML
    Label gameStatus;

    Player playerOne;
    Player playerTwo;

    AI ai = new AI(this);

    public void setPlayers(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    Player currentPlayer;

    boolean isSetup = true;
    boolean isGameFinished = false;
    boolean isShipDirectionHorizontal = true;

    GameRecorder recorder;


    List<Short> shipsLengths = Arrays.asList(new Short[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1});
    int maxPoints = 20;

    int cursor = 0;
    boolean isEveryShipPlaced = false;

    @FXML
    void initialize() {
        setPlayers(
                new Player(!Main.isPlayerOneIsHuman(), this, Main.isPlayerOneIsHuman() ? Main.getPlayerOne().getId() : 0),
                new Player(!Main.isPlayerTwoIsHuman(), this, Main.isPlayerTwoIsHuman() ? Main.getPlayerTwo().getId() : 1)
        );
        startGame();
    }

    private void refreshGameStatus() {
        String gameInfo;
        if (isGameFinished) {
            gameInfo = "Player " + (currentPlayer == playerOne ? "1 " : "2 ") + "wins";
        } else {
            gameInfo = "Player " + (currentPlayer == playerOne ? "1 turn - " : "2 turn - ") +
                    (isSetup ? "place your ships" : "choose area to hit");

        }
        gameStatus.setText(gameInfo);
    }

    public void setEveryShipPlaced(boolean everyShipPlaced) {
        isEveryShipPlaced = everyShipPlaced;
    }

    private void nextTurn(boolean switchPlayers) {
        if (switchPlayers) {
            currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
        }
        refreshGameStatus();
        if (currentPlayer.isAI() && !isGameFinished) {
            if (isSetup) {
                ai.placeShips(currentPlayer.getBoard(), shipsLengths, (currentPlayer == playerOne ? playerTwo : playerOne).isAI());
                isEveryShipPlaced = true;
            } else {
                if (ai.shoot((currentPlayer == playerOne ? playerTwo : playerOne).getBoard().getAreas())) {

                }
            }
        }
    }

    public void handleGameFinish() {
        System.out.println(currentPlayer == playerOne ? "Player One wins" : "Player Two wins");
        isGameFinished = true;
        refreshGameStatus();
        showReplayAlert();
    }

    public void handleShot(Area clickedArea) {
        if (clickedArea.getOwner() != currentPlayer && !clickedArea.wasHit()) {
            recorder.recordShot(clickedArea);
            clickedArea.setHit();
            if (clickedArea.getState() == Area.State.SHIP) {
                currentPlayer.addPoint();
                if (currentPlayer.getScore() == maxPoints) {
                    handleGameFinish();
                    return;
                }
                nextTurn(false);
                return;
            }

            nextTurn(true);
        }
    }

    public EventHandler<MouseEvent> areaClickHandler = e -> {
        Area clickedArea = (Area) e.getSource();
        if (!isGameFinished) {
            if (!isSetup) {
                if (e.getButton() == MouseButton.PRIMARY) {
                    handleShot(clickedArea);
                }
            } else {
                if (e.getButton() == MouseButton.PRIMARY) {
                    if (!isEveryShipPlaced) {
                        placeShip(clickedArea);
                    }
                } else if (e.getButton() == MouseButton.MIDDLE) {
                    clickedArea.setState(Area.State.WATER);
                } else {
                    isShipDirectionHorizontal = !isShipDirectionHorizontal;
                    if (!isEveryShipPlaced) {
                        drawShipGhost(clickedArea);
                    }
                }
            }
        }
    };

    public EventHandler<MouseEvent> areaHoverHandler = e -> {
        Area recClicked = (Area) e.getSource();
        if (isSetup) {
            if (!isEveryShipPlaced && currentPlayer == recClicked.getOwner()) drawShipGhost(recClicked);
        }
    };

    public void placeShip(Area recClicked) {
        if (!isEveryShipPlaced && currentPlayer == recClicked.getOwner()) {
            ShipPlacement shipPlacement = currentPlayer.getBoard().getShipsAreas(recClicked, isShipDirectionHorizontal, shipsLengths.get(cursor));

            if (!shipPlacement.isPossible()) {
                System.out.println("Place your ship elsewhere");
                return;
            }

            (currentPlayer == playerOne ? playerOneAutoPosition : playerTwoAutoPosition).setVisible(false);

            currentPlayer.getBoard().placeShip(shipPlacement);

            if (cursor < shipsLengths.size() - 1) {
                System.out.println("Statek z indexu: " + cursor + "\t " + shipsLengths.get(cursor) + "-masztowiec");
                cursor++;
            } else if (cursor == shipsLengths.size() - 1) {
                cursor = shipsLengths.size() - 1;
                System.out.println("Statek z indexu: " + cursor + "\t " + shipsLengths.get(cursor) + "-masztowiec");
                System.out.print("WSZYSTKIE STATKI NA POLU BITWY dla gracza nr");
                if (currentPlayer == playerOne) {
                    System.out.print(" 1.");
                    System.out.println();
                } else {
                    System.out.print(" 2.");
                    System.out.println();
                }
                isEveryShipPlaced = true;
            }
        }
    }


    public void clearGhosts() {
        for (Area area : currentPlayer.getBoard().getAreas().values()) {
            area.setHighlited(false);
        }
    }

    public void drawShipGhost(Area areaClicked) {
        List<Area> areasToPaint = currentPlayer.getBoard().getShipsAreas(areaClicked, isShipDirectionHorizontal, shipsLengths.get(cursor)).getAreas();
        clearGhosts();
        for (Area rec : areasToPaint) {
            rec.setHighlited(true);
        }
    }

    public void startGame() {
        //printMatrixOfNames();
        recorder = new GameRecorder(playerOne, playerTwo);
        playerTwoReadyButton.setVisible(false);
        playerTwoAutoPosition.setVisible(false);

        if (playerOne.isAI()) {
            playerOneReadyButton.setVisible(false);
            playerOneAutoPosition.setVisible(false);
        }

        currentPlayer = playerOne;
        setupBattleFields();
        if (currentPlayer.isAI()) {
            ai.placeShips(currentPlayer.getBoard(), shipsLengths, true);
            isEveryShipPlaced = true;
        }
        refreshGameStatus();
    }

    public String getHumanReadableCoordinates(int col, int row) {
        return (char) (col + 65) + Integer.toString(row + 1);
    }

    public void printMatrixOfNames() {
        System.out.println("********************************");
        System.out.println("printMatrixOfNames");
        System.out.println("********************************");

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                System.out.print(getHumanReadableCoordinates(col, row) + "\t");
            }
            System.out.println();
        }
    }


    public void printStorageAsStates() {
        System.out.println("********************************");
        System.out.print("printStorageAsStates");
        System.out.print(" PlayersOneTurn  1 ");
        System.out.println();
        System.out.println("********************************");
        int i = 0;
        while (i < currentPlayer.getBoard().getAreas().size()) {
            System.out.print(currentPlayer.getBoard().getAreas().get(i).getState() + "\t");
            if ((i + 1) % 10 == 0) System.out.println();
            i += 1;
        }
    }

    public void prepareAreas(Player player, AnchorPane playersBoardPane) {
        for (Area area : player.getBoard().getAreas().values()) {
            playersBoardPane.getChildren().add(area);
        }
    }

    public void setupBattleFields() {
        prepareAreas(playerOne, playerOnePane);
        prepareAreas(playerTwo, playerTwoPane);
    }

    public void playerReady() {
        if (isEveryShipPlaced) {
            currentPlayer.getBoard().handleSetupComplete();
            recordCurrentPlayerBoard();
            if (currentPlayer == playerOne) {
                isEveryShipPlaced = false;
                cursor = 0;
                if (!playerTwo.isAI()) {
                    playerTwoReadyButton.setVisible(true);
                    playerTwoAutoPosition.setVisible(true);
                }

                playerOneReadyButton.setVisible(false);
                playerOneAutoPosition.setVisible(false);
            } else {
                isSetup = false;
                playerTwoReadyButton.setVisible(false);
                playerTwoAutoPosition.setVisible(false);
            }
            nextTurn(true);
        }
    }

    public void recordCurrentPlayerBoard() {
        ArrayList<Coordinates> occupiedAreas = new ArrayList<>();
        for (Area area : currentPlayer.getBoard().getAreas().values()) {
            if (area.getState() == Area.State.SHIP) occupiedAreas.add(area.getCoordinates());
        }
        if (currentPlayer == playerOne) {
            recorder.setPlayerOneShipsCoordinates(occupiedAreas);
        } else {
            recorder.setPlayerTwoShipsCoordinates(occupiedAreas);
        }
    }

    public void autoPosition() {
        if (cursor == 0 && !isEveryShipPlaced) {
            ai.placeShips(currentPlayer.getBoard(), shipsLengths, false);
        }
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

    public void showReplayAlert() {
        ButtonType okButton = new ButtonType("See replay", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Go back to menu", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Player " + (currentPlayer == playerOne ? "1 " : "2 ") + "wins. Do you want to see a replay?",
                okButton,
                cancelButton);

        alert.setTitle("Game Finished");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == okButton) {
            showReplay();
        } else {
            backToMainMenu();
        }
    }

    public void showReplay() {
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
}
