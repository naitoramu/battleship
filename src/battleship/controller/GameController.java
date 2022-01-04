package battleship.controller;

import battleship.classes.Area;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    Rectangle rectangleFieldA;
    @FXML
    Rectangle rectangleFieldB;
    @FXML
    Button confirm_ships_location;

    ArrayList<Area> areasList = new ArrayList<>(100);//pamięć
    ArrayList<Area> areasList2 = new ArrayList<>(100);//pamięć2

    int initial_state = 0;
    double x_coordinate = 165;
    double y_coordinate = 165;
    double x_coordinate2 = 830;
    double y_coordinate2 = 165;
    boolean isPlayerOneTurn = true;
    boolean isSetup = true;
    boolean isShipDirectionHorizontal = true;

    List<Short> shipsLengths = Arrays.asList(new Short[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1});
    int cursor = 0;
    boolean areAllShipsPlaced = false;

    Paint primaryColor = Color.LIGHTBLUE;
    Paint ghostColor = Color.YELLOW;
    Paint errorColor = Color.RED;
    //Paint shipColor = Color.BLACK;
    Paint borderColor = Color.BLUE;
    Paint destroyedPieceColor = Color.ORANGE;
    Paint missColor = Color.DEEPSKYBLUE;


    EventHandler<MouseEvent> recClickHandler = e -> {
        Area recClicked = (Area) e.getSource();
        if (!isSetup) {
            if (e.getButton() == MouseButton.PRIMARY && recClicked.getState() != 1.0 && recClicked.getState() != 2.0) {
                recClicked.setState(3);//pudło
                recClicked.setFill(missColor);
                recClicked.setStroke(null);
            }
            if (e.getButton() == MouseButton.PRIMARY && recClicked.getState() == 1.0) {
                recClicked.setState(2);//trafionyFragment
                recClicked.setFill(destroyedPieceColor);
                recClicked.setStroke(null);
            }
        } else {
            if (e.getButton() == MouseButton.PRIMARY) {
                if(!areAllShipsPlaced){ placeShip(recClicked);}
            }else if(e.getButton() == MouseButton.MIDDLE){
                recClicked.setState(0);
                recClicked.setFill(primaryColor);
            }else {
                isShipDirectionHorizontal = !isShipDirectionHorizontal;
                if(!areAllShipsPlaced) {drawShipGhost(recClicked);}
            }

        }
    };
    EventHandler<MouseEvent> recHoverHandler = e -> {
        Area recClicked = (Area) e.getSource();
        if (isSetup) {
            if(!areAllShipsPlaced && isPlayerOneTurn == recClicked.isBelongsToPlane1()) {drawShipGhost(recClicked);}
        }
    };

    public void placeShip(Area recClicked) {
        if(!areAllShipsPlaced && isPlayerOneTurn == recClicked.isBelongsToPlane1()){
            List<Area> areaToPutShip = getAreasToPaint(recClicked);
            List<Area> currentList = isPlayerOneTurn ? areasList : areasList2;
            int arrSize = areaToPutShip.size();
            boolean wasErrorCode = false;//
            for (int i = 0; i < areaToPutShip.size(); i++) {//puts ship
                if(areaToPutShip.get(i).getFill() == errorColor){
                    System.out.println("Place your ship elsewhere");
                    wasErrorCode = true;
                    break;
                }
                areaToPutShip.get(i).setState(1);
                areaToPutShip.get(i).setFill(Color.BLACK);
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
                        currentList.get(areaToPutShip.get(i).getRid() + 10).setState(9);
                        currentList.get(areaToPutShip.get(i).getRid() + 10).setFill(borderColor);
                    } else if (atBottomEdge) {
                        currentList.get(areaToPutShip.get(i).getRid() - 10).setState(9);
                        currentList.get(areaToPutShip.get(i).getRid() - 10).setFill(borderColor);
                    } else if (!atUpperEdge && !atBottomEdge) {
                        currentList.get(areaToPutShip.get(i).getRid() - 10).setState(9);
                        currentList.get(areaToPutShip.get(i).getRid() - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(i).getRid() + 10).setState(9);
                        currentList.get(areaToPutShip.get(i).getRid() + 10).setFill(borderColor);
                    }
                }
                if (atLeftEdge) {
                    if (atUpperEdge) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setFill(borderColor);
                    } else if (atBottomEdge) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setFill(borderColor);
                    } else if (!atUpperEdge && !atBottomEdge) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setFill(borderColor);
                    }
                } else if (atRightEgde) {//left border
                    if (atUpperEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setFill(borderColor);
                    } else if (atBottomEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setFill(borderColor);
                    } else if (!atUpperEdge && !atBottomEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setFill(borderColor);
                    }
                } else if (!atLeftEdge && !atRightEgde) {
                    if (atUpperEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setFill(borderColor);
                    } else if (atBottomEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setFill(borderColor);
                    } else if (!atUpperEdge && !atBottomEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 1 + 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 1 + 10).setFill(borderColor);
                    }
                }
            } else if(!isShipDirectionHorizontal && !wasErrorCode){
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
                        currentList.get(areaToPutShip.get(i).getRid() + 1).setState(9);
                        currentList.get(areaToPutShip.get(i).getRid() + 1).setFill(borderColor);
                    } else if (atRightEgde) {
                        currentList.get(areaToPutShip.get(i).getRid() - 1).setState(9);
                        currentList.get(areaToPutShip.get(i).getRid() - 1).setFill(borderColor);
                    } else if (!atLeftEdge && !atRightEgde) {
                        currentList.get(areaToPutShip.get(i).getRid() - 1).setState(9);
                        currentList.get(areaToPutShip.get(i).getRid() - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(i).getRid() + 1).setState(9);
                        currentList.get(areaToPutShip.get(i).getRid() + 1).setFill(borderColor);
                    }
                }
                if (atUpperEdge) {
                    if (atLeftEdge) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setFill(borderColor);
                    } else if (atRightEgde) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setFill(borderColor);
                    } else if (!atLeftEdge && !atRightEgde) {
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setFill(borderColor);
                    }
                } else if (atBottomEdge) {
                    if (atLeftEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setFill(borderColor);
                    } else if (atRightEgde) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setFill(borderColor);
                    } else if (!atLeftEdge && !atRightEgde) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setFill(borderColor);
                    }
                } else if (!atUpperEdge && !atBottomEdge) {
                    if (atLeftEdge) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setFill(borderColor);
                    } else if (atRightEgde) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setFill(borderColor);
                    } else if (!atLeftEdge && !atRightEgde) {
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(9);
                        currentList.get(areaToPutShip.get(0).getRid() - 10 + 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 - 1).setFill(borderColor);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setState(9);
                        currentList.get(areaToPutShip.get(arrSize - 1).getRid() + 10 + 1).setFill(borderColor);
                    }
                }
            }
            if(cursor < shipsLengths.size()-1 && !wasErrorCode){
                System.out.println("Statek z indexu: " + cursor + "\t " + shipsLengths.get(cursor) + "-masztowiec");
                cursor++;
            }else if (cursor == shipsLengths.size()-1){
                cursor = shipsLengths.size() - 1;
                System.out.println("Statek z indexu: " + cursor + "\t " + shipsLengths.get(cursor) + "-masztowiec");
                System.out.print("WSZYSTKIE STATKI NA POLU BITWY dla gracza nr");
                if(isPlayerOneTurn){
                    System.out.print(" 1.");
                    System.out.println();
                }else{
                    System.out.print(" 2.");
                    System.out.println();
                }
                areAllShipsPlaced = true;
            }
        }
    }



    public List<Area> getAreasToPaint(Area recClicked) {
        List<Area> occupiedAreas = new ArrayList<>();
        int rid = recClicked.getRid();
        List<Area> currentList = isPlayerOneTurn ? areasList : areasList2;
            if (isShipDirectionHorizontal) {
                while (rid < recClicked.getRid() + shipsLengths.get(cursor) && rid / 10 == recClicked.getRid() / 10 && currentList.get(rid).getState() != 1.0 && currentList.get(rid).getState() != 9.0) {
                    occupiedAreas.add(currentList.get(rid));
                    rid += 1;
                }
            } else {
                while (rid < recClicked.getRid() + shipsLengths.get(cursor) * 10 && rid < 100 && currentList.get(rid).getState() != 1.0 && currentList.get(rid).getState() != 9.0) {
                    occupiedAreas.add(currentList.get(rid));
                    rid += 10;
                }
            }
        return occupiedAreas;
    }



    public void clearGhosts() {
        List<Area> currentList = isPlayerOneTurn ? areasList : areasList2;
        for (Area rec : currentList) {
            Paint recColor = rec.getFill();
            if (recColor == ghostColor || recColor == errorColor) {
                rec.setFill(primaryColor);
            }
        }
    }

    public void drawShipGhost(Area recClicked) {
        List<Area> areasToPaint = getAreasToPaint(recClicked);
        Paint shipColor = (areasToPaint.size() < shipsLengths.get(cursor)) ? errorColor : ghostColor;
        clearGhosts();
        for (Area rec : areasToPaint) {
            rec.setFill(shipColor);
        }
    }

    public void initialize() {
        printMatrixOfNames();
        fillStorage();
        createBattleField();


        //printStorageAsStates();
        //clickingAtBattlefield_AssociationOfEventHandlersForRectangles();


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

    public void fillStorage() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Area rec = new Area(x_coordinate + 30 * col, y_coordinate + 30 * row, 30, 30, initial_state, 10 * row + col, getHumanReadableCoordinates(col, row), true, primaryColor);
                rec.addEventHandler(MouseEvent.MOUSE_CLICKED, recClickHandler);
                rec.addEventHandler(MouseEvent.MOUSE_ENTERED, recHoverHandler);
                areasList.add(rec);
            }
        }

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Area rec = new Area(x_coordinate2 + 30 * col, y_coordinate2 + 30 * row, 30, 30, initial_state, 10 * row + col, getHumanReadableCoordinates(col, row), false, primaryColor);
                rec.addEventHandler(MouseEvent.MOUSE_CLICKED, recClickHandler);
                rec.addEventHandler(MouseEvent.MOUSE_ENTERED, recHoverHandler);
                areasList2.add(rec);
            }
        }
    }

    public void printStorageAsStates() {
        System.out.println("********************************");
        System.out.print("printStorageAsStates");
        ArrayList<Area> currList = isPlayerOneTurn ? areasList : areasList2;
        System.out.print(" PlayersOneTurn  1 ");
        System.out.println();
        System.out.println("********************************");
        int i = 0;
        while (i < areasList.size()) {
            System.out.print(/*areasList.get(i).getRidAsCoor() + "-" + */currList.get(i).getState() + "\t");
            if ((i + 1) % 10 == 0) System.out.println();
            i += 1;
        }


    }

    public void createBattleField() {
        //if(isPlayerOneTurn){
            for (Area rec : areasList) {
                anchorPane.getChildren().add(rec);
            }
       //}else{
            for (Area rec : areasList2) {
                anchorPane.getChildren().add(rec);
            }
       // }

    }


    public void confirm_ships_location() {
        if( isPlayerOneTurn && areAllShipsPlaced){
            printStorageAsStates();
            makeAllSquaresBlue();
            printStorageAsStates();
            isPlayerOneTurn = false;
            areAllShipsPlaced = false;
            cursor = 0;
            confirm_ships_location.setLayoutX(855);
            confirm_ships_location.setText("confirm_ships_location & start the game");

        }
        if( !isPlayerOneTurn && areAllShipsPlaced){
            makeAllSquaresBlue();
            printStorageAsStates();
            makeAllSquaresBlue();
            isSetup = false;
            confirm_ships_location.setVisible(false);
            System.out.println("START ROZGRYWKI");
        }

    }

    private void makeAllSquaresBlue(){
        ArrayList<Area> currList = isPlayerOneTurn ? areasList : areasList2;
        for(Area element: currList) {
            element.setFill(primaryColor);
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
