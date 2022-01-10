package battleship.controller;

import battleship.Main;
import battleship.classes.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


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


    List<Short> shipsLengths = Arrays.asList(new Short[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1});
    int maxPoints = 20;

    int cursor = 0;
    boolean isEveryShipPlaced = false;

    @FXML
    void initialize() {
        setPlayers(new Player(!Main.isPlayerOneIsHuman(), this), new Player(!Main.isPlayerTwoIsHuman(), this));
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

    private void nextPlayerTurn() {
        currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
        refreshGameStatus();
        if (currentPlayer.isAI() && !isGameFinished) {
            if (isSetup) {
                ai.placeShips(currentPlayer.getBoard(), shipsLengths);
                isEveryShipPlaced = true;
            } else {
                ai.shoot((currentPlayer == playerOne ? playerTwo : playerOne).getBoard().getAreas());
            }
        }
    }

    public void handleShot(Area clickedArea) {
        if (clickedArea.getOwner() != currentPlayer && !clickedArea.wasHit()) {
            clickedArea.setHit();
            if (clickedArea.getState() == Area.State.SHIP) {
                currentPlayer.addPoint();
                if (currentPlayer.getScore() == maxPoints) {
                    System.out.println(currentPlayer == playerOne ? "Player One wins" : "Player Two wins");
                    isGameFinished = true;
                }
            }

            nextPlayerTurn();
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
        playerTwoReadyButton.setVisible(false);
        playerTwoAutoPosition.setVisible(false);

        currentPlayer = playerOne;
        setupBattleFields();
        if (currentPlayer.isAI()) {
            ai.placeShips(currentPlayer.getBoard(), shipsLengths);
            isEveryShipPlaced = true;
        }
        refreshGameStatus();
    }

    public void handleStartButton() {
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
            if (currentPlayer == playerOne) {
                isEveryShipPlaced = false;
                cursor = 0;

                playerTwoReadyButton.setVisible(true);
                playerTwoAutoPosition.setVisible(true);

                playerOneReadyButton.setVisible(false);
                playerOneAutoPosition.setVisible(false);
            } else {
                isSetup = false;
                playerTwoReadyButton.setVisible(false);
            }
            nextPlayerTurn();
        }
    }

    public void autoPosition() {
        if (cursor == 0 && !isEveryShipPlaced) {
            ai.placeShips(currentPlayer.getBoard(), shipsLengths);
            isEveryShipPlaced = true;
            (currentPlayer == playerOne ? playerOneAutoPosition : playerTwoAutoPosition).setVisible(false);
            playerReady();
        }
    }

    public void go_to_menu(ActionEvent actionEvent) throws IOException {
        backToMainMenu((Button) actionEvent.getSource());
    }

    private void backToMainMenu(Button btn) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/mainMenuView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/mainMenu.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);
    }

    public void justExit() {
        Platform.exit();
    }
}
