package battleship.controller;

import battleship.classes.Area;
import battleship.classes.Player;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    Label gameStatus;

    Player playerOne = new Player();
    Player playerTwo = new Player();

    Player currentPlayer = playerOne;

    boolean isSetup = true;
    boolean isShipDirectionHorizontal = true;

    List<Short> shipsLengths = Arrays.asList(new Short[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1});
    int maxPoints = 20;

    int cursor = 0;
    boolean isEveryShipPlaced = false;

    Paint errorColor = Color.RED;

    private void refreshGameStatus() {
        String gameInfo = "Player " + (currentPlayer == playerOne ? "1 turn - " : "2 turn - ") +
                (isSetup ? "place your ships" : "choose area to hit");
        gameStatus.setText(gameInfo);
    }

    private void nextPlayerTurn() {
        currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
        refreshGameStatus();
    }

    private void handleShot(Area clickedArea) {
        if (clickedArea.getOwner() != currentPlayer && !clickedArea.wasHit()) {
            clickedArea.setHit();
            if (clickedArea.getState() == Area.State.SHIP) {
                currentPlayer.addPoint();
                if (currentPlayer.getScore() == maxPoints) {
                    System.out.println(currentPlayer == playerOne ? "Player One wins" : "Player Two wins");
                    //TODO: Stop the game here, show poits, winner etc.
                }
            }

            nextPlayerTurn();
        }
    }

    EventHandler<MouseEvent> areaClickHandler = e -> {
        Area clickedArea = (Area) e.getSource();
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
    };

    EventHandler<MouseEvent> areaHoverHandler = e -> {
        Area recClicked = (Area) e.getSource();
        if (isSetup) {
            if (!isEveryShipPlaced && currentPlayer == recClicked.getOwner()) drawShipGhost(recClicked);
        }
    };

    public void placeShip(Area recClicked) {
        if (!isEveryShipPlaced && currentPlayer == recClicked.getOwner()) {
            List<Area> areaToPutShip = getAreasToPaint(recClicked);
            List<Area> currentList = currentPlayer.getPlayersAreas();
            int arrSize = areaToPutShip.size();
            boolean wasErrorCode = false;
            for (int i = 0; i < areaToPutShip.size(); i++) {//puts ship
                if (areaToPutShip.get(i).getFill() == errorColor) {
                    System.out.println("Place your ship elsewhere");
                    wasErrorCode = true;
                    break;
                }
                areaToPutShip.get(i).setState(Area.State.SHIP);
            }
            //puts border around ship  -> state 9.0
            if (isShipDirectionHorizontal && !wasErrorCode) {
                boolean atLeftEdge = false;
                boolean atRightEgde = false;
                boolean atUpperEdge = false;
                boolean atBottomEdge = false;

                if (areaToPutShip.get(0).getRid() % 10 == 0) {
                    atLeftEdge = true;
                    System.out.println("położenie statku: LEWA KRAWĘDŹ PLANSZY");
                }
                if ((areaToPutShip.get(arrSize - 1).getRid() % 10 == 9)) {
                    atRightEgde = true;
                    System.out.println("położenie statku: PRAWA KRAWĘDŹ PLANSZY");
                }
                if (areaToPutShip.get(0).getRid() < 10) {
                    atUpperEdge = true;
                    System.out.println("położenie statku: GoRNA KRAWĘDŹ PLANSZY");
                }
                if (areaToPutShip.get(0).getRid() >= 90) {
                    atBottomEdge = true;
                    System.out.println("położenie statku: DoLNA KRAWĘDŹ PLANSZY");
                }

                for (int i = 0; i < areaToPutShip.size(); i++) {
                    if (atUpperEdge) {//border directly above and under the ship
                        currentList.get(areaToPutShip.get(i).getRid() + 10).setState(Area.State.FORBIDDEN);
                    } else if (atBottomEdge) {
                        currentList.get(areaToPutShip.get(i).getRid() - 10).setState(Area.State.FORBIDDEN);
                    } else if (!atUpperEdge && !atBottomEdge) {
                        currentList.get(areaToPutShip.get(i).getRid() - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(i).getRid() + 10).setState(Area.State.FORBIDDEN);
                    }
                }
                if (atLeftEdge) {
                    if (atUpperEdge) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setState(Area.State.FORBIDDEN);
                    } else if (atBottomEdge) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setState(Area.State.FORBIDDEN);
                    } else if (!atUpperEdge && !atBottomEdge) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setState(Area.State.FORBIDDEN);
                    }
                } else if (atRightEgde) {//left border
                    if (atUpperEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(Area.State.FORBIDDEN);
                    } else if (atBottomEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(Area.State.FORBIDDEN);
                    } else if (!atUpperEdge && !atBottomEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(Area.State.FORBIDDEN);
                    }
                } else if (!atLeftEdge && !atRightEgde) {
                    if (atUpperEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setState(Area.State.FORBIDDEN);
                    } else if (atBottomEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(Area.State.FORBIDDEN);
                    } else if (!atUpperEdge && !atBottomEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setState(Area.State.FORBIDDEN);
                    }
                }
            } else if (!isShipDirectionHorizontal && !wasErrorCode) {
                //when ship direction VERTICAL
                boolean atLeftEdge = false;
                boolean atRightEgde = false;
                boolean atUpperEdge = false;
                boolean atBottomEdge = false;

                if (areaToPutShip.get(0).getRid() % 10 == 0) {
                    atLeftEdge = true;
                    System.out.println("położenie statku: LEWA KRAWĘDŹ PLANSZY");
                }
                if ((areaToPutShip.get(0).getRid() % 10 == 9)) {
                    atRightEgde = true;
                    System.out.println("położenie statku: PRAWA KRAWĘDŹ PLANSZY");
                }
                if (areaToPutShip.get(0).getRid() < 10) {
                    atUpperEdge = true;
                    System.out.println("położenie statku: GoRNA KRAWĘDŹ PLANSZY");
                }
                if (areaToPutShip.get(arrSize - 1).getRid() >= 90) {
                    atBottomEdge = true;
                    System.out.println("położenie statku: DoLNA KRAWĘDŹ PLANSZY");
                }

                for (int i = 0; i < areaToPutShip.size(); i++) {
                    if (atLeftEdge) {
                        currentList.get(areaToPutShip.get(i).getRid() + 1).setState(Area.State.FORBIDDEN);
                    } else if (atRightEgde) {
                        currentList.get(areaToPutShip.get(i).getRid() - 1).setState(Area.State.FORBIDDEN);
                    } else if (!atLeftEdge && !atRightEgde) {
                        currentList.get(areaToPutShip.get(i).getRid() - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(i).getRid() + 1).setState(Area.State.FORBIDDEN);
                    }
                }
                if (atUpperEdge) {
                    if (atLeftEdge) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setState(Area.State.FORBIDDEN);
                    } else if (atRightEgde) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setState(Area.State.FORBIDDEN);
                    } else if (!atLeftEdge && !atRightEgde) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setState(Area.State.FORBIDDEN);
                    }
                } else if (atBottomEdge) {
                    if (atLeftEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(Area.State.FORBIDDEN);
                    } else if (atRightEgde) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(Area.State.FORBIDDEN);
                    } else if (!atLeftEdge && !atRightEgde) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(Area.State.FORBIDDEN);
                    }
                } else if (!atUpperEdge && !atBottomEdge) {
                    if (atLeftEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setState(Area.State.FORBIDDEN);
                    } else if (atRightEgde) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setState(Area.State.FORBIDDEN);
                    } else if (!atLeftEdge && !atRightEgde) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setState(Area.State.FORBIDDEN);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setState(Area.State.FORBIDDEN);
                    }
                }
            }
            if (cursor < shipsLengths.size() - 1 && !wasErrorCode) {
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

    public List<Area> getAreasToPaint(Area recClicked) {
        List<Area> occupiedAreas = new ArrayList<>();
        int rid = recClicked.getRid();
        List<Area> currentList = currentPlayer.getPlayersAreas();
        if (isShipDirectionHorizontal) {
            while (rid < recClicked.getRid() + shipsLengths.get(cursor) && rid / 10 == recClicked.getRid() / 10 && currentList.get(rid).getState() != Area.State.SHIP && currentList.get(rid).getState() != Area.State.FORBIDDEN) {
                occupiedAreas.add(currentList.get(rid));
                rid += 1;
            }
        } else {
            while (rid < recClicked.getRid() + shipsLengths.get(cursor) * 10 && rid < 100 && currentList.get(rid).getState() != Area.State.SHIP && currentList.get(rid).getState() != Area.State.FORBIDDEN) {
                occupiedAreas.add(currentList.get(rid));
                rid += 10;
            }
        }
        return occupiedAreas;
    }


    public void clearGhosts() {
        for (Area area : currentPlayer.getPlayersAreas()) {
            Area.State currentState = area.getState();
            if (currentState == Area.State.ERROR || currentState == Area.State.GHOST) {
                area.setState(Area.State.WATER);
            }
        }
    }

    public void drawShipGhost(Area areaClicked) {
        List<Area> areasToPaint = getAreasToPaint(areaClicked);
        Area.State stateToSet = (areasToPaint.size() < shipsLengths.get(cursor)) ? Area.State.ERROR : Area.State.GHOST;
        clearGhosts();
        for (Area rec : areasToPaint) {
            rec.setState(stateToSet);
        }
    }

    public void initialize() {
        //printMatrixOfNames();
        playerTwoReadyButton.setVisible(false);
        setupBattleFields();
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
        while (i < currentPlayer.getPlayersAreas().size()) {
            System.out.print(currentPlayer.getPlayersAreas().get(i).getState() + "\t");
            if ((i + 1) % 10 == 0) System.out.println();
            i += 1;
        }
    }

    public void prepareAreas(Player player, AnchorPane playersBoardPane) {
        int xOffset = 52;
        int yOffset = 70;
        int areaSize = 30;

        ObservableList boardsAreas = playersBoardPane.getChildren();
        ArrayList<Area> areas = new ArrayList<>(100);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Area newArea = new Area(xOffset + areaSize * col, yOffset + areaSize * row, areaSize, areaSize, 10 * row + col, getHumanReadableCoordinates(col, row), player);
                newArea.addEventHandler(MouseEvent.MOUSE_CLICKED, areaClickHandler);
                newArea.addEventHandler(MouseEvent.MOUSE_ENTERED, areaHoverHandler);

                areas.add(newArea);
                boardsAreas.add(newArea);
            }
        }
        player.setPlayersAreas(areas);
    }

    public void setupBattleFields() {
        prepareAreas(playerOne, playerOnePane);
        prepareAreas(playerTwo, playerTwoPane);
    }

    public void playerReady() {
        boardSetupCompleteHandler(currentPlayer.getPlayersAreas());
        if (isEveryShipPlaced) {
            if (currentPlayer == playerOne) {
                isEveryShipPlaced = false;
                cursor = 0;
                playerTwoReadyButton.setVisible(true);
                playerOneReadyButton.setVisible(false);
            } else {
                isSetup = false;
                playerTwoReadyButton.setVisible(false);
                System.out.println("START ROZGRYWKI");
            }
            nextPlayerTurn();
        }
    }

    private void boardSetupCompleteHandler(ArrayList<Area> boardsAreas) {
        for (Area area : boardsAreas) {
            if (area.getState() != Area.State.SHIP) area.setState(Area.State.WATER);
            area.setStateHidden(true);
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
