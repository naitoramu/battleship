package battleship.controller;

import battleship.classes.Area;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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

    ArrayList<Area> areasList = new ArrayList<>(100);//pamięć

    int initial_state = 0;
    double x_coordinate = 165;
    double y_coordinate = 165;
    boolean isPlayerOneTurn = true;
    boolean isSetup = true;
    boolean isShipDirectionHorizontal = true;
    List<Short> shipsLengthsFor1 = Arrays.asList(new Short[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1});
    int cursor1 = 0;
    List<Short> shipsLengthsFor2 = Arrays.asList(new Short[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1});
    int cursor2 = 0;

    Paint primaryColor = Color.LIGHTBLUE;
    Paint ghostColor = Color.YELLOW;
    Paint errorColor = Color.RED;
    //Paint shipColor = Color.BLACK;
    Paint borderColor = Color.BLUEVIOLET;


    EventHandler<MouseEvent> recClickHandler = e -> {
        Area recClicked = (Area) e.getSource();
        if (!isSetup) {
            if (e.getButton() == MouseButton.PRIMARY && recClicked.getState() != 1.0) {
                recClicked.setState(1);
                recClicked.setFill(Color.YELLOW);
                recClicked.setStroke(null);
            }
            if (e.getButton() == MouseButton.PRIMARY && recClicked.getState() == 1.0) {
                recClicked.setState(0);
                recClicked.setFill(Color.BLACK);
                recClicked.setStroke(null);
            }
        } else {
            if (e.getButton() == MouseButton.PRIMARY) {
                placeShip(recClicked);
            }else if(e.getButton() == MouseButton.MIDDLE){
                recClicked.setState(0);
                recClicked.setFill(primaryColor);
            }else {
                isShipDirectionHorizontal = !isShipDirectionHorizontal;
                drawShipGhost(recClicked);
            }

        }
    };
    EventHandler<MouseEvent> recHoverHandler = e -> {
        Area recClicked = (Area) e.getSource();
        if (isSetup) {
            drawShipGhost(recClicked);
        }
    };

    public void placeShip(Area recClicked) {
        printStorageAsStates();//use getAreasToPaint() to implement placing ships
        List<Area> areaToPutShip = getAreasToPaint(recClicked);
        int arrSize = areaToPutShip.size();
        for(int i = 0; i < areaToPutShip.size(); i++){//puts ship
            areaToPutShip.get(i).setState(1);
            areaToPutShip.get(i).setFill(Color.BLACK);
        }
        //puts border around ship  -> state 9.0
        if(isShipDirectionHorizontal){
            boolean atLeftEdge = false;
            boolean atRightEgde = false;
            boolean atUpperEdge = false;
            boolean atBottomEdge = false;

            if(areaToPutShip.get(0).getRid() % 10 == 0){ atLeftEdge = true; System.out.println("położenie statku: LEWA KRAWĘDŹ PLANSZY");}
            if((areaToPutShip.get(arrSize-1).getRid() % 10 == 9)) {atRightEgde = true;   System.out.println("położenie statku: PRAWA KRAWĘDŹ PLANSZY");}
            if( areaToPutShip.get(0).getRid() < 10) {atUpperEdge = true; System.out.println("położenie statku: GoRNA KRAWĘDŹ PLANSZY");}
            if( areaToPutShip.get(0).getRid() >= 90) {atBottomEdge = true; System.out.println("położenie statku: DoLNA KRAWĘDŹ PLANSZY");}

            for(int i = 0; i < areaToPutShip.size(); i++){
                if(atUpperEdge){//border directly above and under the ship
                    areasList.get(areaToPutShip.get(i).getRid()+10).setState(9);
                    areasList.get(areaToPutShip.get(i).getRid()+10).setFill(borderColor);
                }else if(atBottomEdge){
                    areasList.get(areaToPutShip.get(i).getRid()-10).setState(9);
                    areasList.get(areaToPutShip.get(i).getRid()-10).setFill(borderColor);
                }else if(!atUpperEdge && !atBottomEdge){
                    areasList.get(areaToPutShip.get(i).getRid()-10).setState(9);
                    areasList.get(areaToPutShip.get(i).getRid()-10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(i).getRid()+10).setState(9);
                    areasList.get(areaToPutShip.get(i).getRid()+10).setFill(borderColor);
                }
            }
            if(atLeftEdge){
                if(atUpperEdge){
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 + 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 + 10).setFill(borderColor);
                }else if(atBottomEdge){
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 - 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 - 10).setFill(borderColor);
                }else if(!atUpperEdge && !atBottomEdge){
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 - 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 - 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 + 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 + 10).setFill(borderColor);
                }
            }else if(atRightEgde){//left border
                if(atUpperEdge){
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 + 10).setFill(borderColor);
                }else if(atBottomEdge){
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 - 10).setFill(borderColor);
                }else if(!atUpperEdge && !atBottomEdge){
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 - 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 + 10).setFill(borderColor);
                }
            }else if(!atLeftEdge && !atRightEgde){
                if(atUpperEdge){
                    areasList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 + 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 + 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 + 10).setFill(borderColor);
                }else if(atBottomEdge){
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 - 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 - 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 - 10).setFill(borderColor);
                }else if(!atUpperEdge && !atBottomEdge){
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 - 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 - 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 + 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 1 + 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 - 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 - 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 + 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() +1 + 10).setFill(borderColor);
                }
            }
        }else{//when ship direction VERTICAL
            boolean atLeftEdge = false;
            boolean atRightEgde = false;
            boolean atUpperEdge = false;
            boolean atBottomEdge = false;

            if(areaToPutShip.get(0).getRid() % 10 == 0){ atLeftEdge = true; System.out.println("położenie statku: LEWA KRAWĘDŹ PLANSZY");}
            if((areaToPutShip.get(0).getRid() % 10 == 9)) {atRightEgde = true;   System.out.println("położenie statku: PRAWA KRAWĘDŹ PLANSZY");}
            if( areaToPutShip.get(0).getRid() < 10) {atUpperEdge = true; System.out.println("położenie statku: GoRNA KRAWĘDŹ PLANSZY");}
            if( areaToPutShip.get(arrSize-1).getRid() >= 90) {atBottomEdge = true; System.out.println("położenie statku: DoLNA KRAWĘDŹ PLANSZY");}

            for(int i = 0; i < areaToPutShip.size(); i++){
                if(atLeftEdge){
                    areasList.get(areaToPutShip.get(i).getRid() +1).setState(9);
                    areasList.get(areaToPutShip.get(i).getRid() +1).setFill(borderColor);
                }else if(atRightEgde){
                    areasList.get(areaToPutShip.get(i).getRid() - 1).setState(9);
                    areasList.get(areaToPutShip.get(i).getRid() - 1).setFill(borderColor);
                }else if(!atLeftEdge && !atRightEgde){
                    areasList.get(areaToPutShip.get(i).getRid() - 1).setState(9);
                    areasList.get(areaToPutShip.get(i).getRid() - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(i).getRid() +1).setState(9);
                    areasList.get(areaToPutShip.get(i).getRid() +1).setFill(borderColor);
                }
            }
            if(atUpperEdge){
                if(atLeftEdge){
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 + 1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 + 1).setFill(borderColor);
                }else if(atRightEgde){
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 - 1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 - 1).setFill(borderColor);
                }else if(!atLeftEdge && !atRightEgde){
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 - 1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 + 1).setState(9);
                    areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 + 1).setFill(borderColor);
                }
            }else if(atBottomEdge){
                if(atLeftEdge){
                    areasList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 10 + 1).setFill(borderColor);
                }else if(atRightEgde){
                    areasList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 10 - 1).setFill(borderColor);
                }else if(!atLeftEdge && !atRightEgde){
                    areasList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 10 - 1).setFill(borderColor);
                    areasList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(9);
                    areasList.get(areaToPutShip.get(0).getRid() - 10 + 1).setFill(borderColor);
                }
            }else if(!atUpperEdge && !atBottomEdge){
                        if(atLeftEdge){
                            areasList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                            areasList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                            areasList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(9);
                            areasList.get(areaToPutShip.get(0).getRid() - 10 + 1).setFill(borderColor);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setState(9);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setFill(borderColor);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 + 1).setState(9);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 + 1).setFill(borderColor);
                        }else if(atRightEgde){
                            areasList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                            areasList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                            areasList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(9);
                            areasList.get(areaToPutShip.get(0).getRid() - 10 - 1).setFill(borderColor);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setState(9);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setFill(borderColor);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 - 1).setState(9);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 - 1).setFill(borderColor);
                        }else if(!atLeftEdge && !atRightEgde){
                            areasList.get(areaToPutShip.get(0).getRid() - 10).setState(9);
                            areasList.get(areaToPutShip.get(0).getRid() - 10).setFill(borderColor);
                            areasList.get(areaToPutShip.get(0).getRid() - 10 - 1).setState(9);
                            areasList.get(areaToPutShip.get(0).getRid() - 10 - 1).setFill(borderColor);
                            areasList.get(areaToPutShip.get(0).getRid() - 10 + 1).setState(9);
                            areasList.get(areaToPutShip.get(0).getRid() - 10 + 1).setFill(borderColor);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setState(9);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10).setFill(borderColor);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 - 1).setState(9);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 - 1).setFill(borderColor);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 + 1).setState(9);
                            areasList.get(areaToPutShip.get(arrSize-1).getRid() + 10 + 1).setFill(borderColor);
                        }
                }
            }
        }



    public List<Area> getAreasToPaint(Area recClicked) {
        List<Area> occupiedAreas = new ArrayList<>();
        int rid = recClicked.getRid();
        if (isShipDirectionHorizontal) {
            while (rid < recClicked.getRid() + shipsLengthsFor1.get(cursor1) && rid / 10 == recClicked.getRid() / 10 && areasList.get(rid).getState() != 1.0 && areasList.get(rid).getState() != 9.0) {
                occupiedAreas.add(areasList.get(rid));
                rid += 1;
            }
        } else {
            while (rid < recClicked.getRid() + shipsLengthsFor1.get(cursor1) * 10 && rid < 100 && areasList.get(rid).getState() != 1.0 && areasList.get(rid).getState() != 9.0) {
                occupiedAreas.add(areasList.get(rid));
                rid += 10;
            }
        }
        return occupiedAreas;
    }

    public void clearGhosts() {
        for (Area rec : areasList) {
            Paint recColor = rec.getFill();
            if (recColor == ghostColor || recColor == errorColor) {
                rec.setFill(primaryColor);
            }
        }
    }

    public void drawShipGhost(Area recClicked) {
        List<Area> areasToPaint = getAreasToPaint(recClicked);
        Paint shipColor = (areasToPaint.size() < shipsLengthsFor1.get(cursor1)) ? errorColor : ghostColor;
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
                Area rec = new Area(x_coordinate + 30 * col, y_coordinate + 30 * row, 30, 30, initial_state, 10 * row + col, getHumanReadableCoordinates(col, row), primaryColor);
                rec.addEventHandler(MouseEvent.MOUSE_CLICKED, recClickHandler);
                rec.addEventHandler(MouseEvent.MOUSE_ENTERED, recHoverHandler);
                areasList.add(rec);
            }
        }
    }

    public void printStorageAsStates() {
        System.out.println("********************************");
        System.out.println("printStorageAsStates");
        System.out.println("********************************");
        int i = 0;
        while (i < areasList.size()) {
            System.out.print(/*areasList.get(i).getRidAsCoor() + "-" + */areasList.get(i).getState() + "\t");
            if ((i + 1) % 10 == 0) System.out.println();
            i += 1;
        }
    }

    public void createBattleField() {
        for (Area rec : areasList) {
            anchorPane.getChildren().add(rec);
        }
    }

    public void check() {
        System.out.println("Sprawdzanie wywołane - printStorageAsStates()");
        printStorageAsStates();
        //is_there_X_of_Y(3, 3);
        /*System.out.println("row1");
        for(int i = 0; i < 10; i++){
            System.out.print(row1.get(i).getState()+" "+row1.get(i).getRidAsCoor()+" "+ row1.get(i).getRid() +"\t");
        }

        System.out.println("row3");
        for(int i = 0; i < 10; i++){
            System.out.print(row3.get(i).getState()+" "+row3.get(i).getRidAsCoor()+" "+ row3.get(i).getRid() +"\t");
        }

        System.out.println("row8");
        for(int i = 0; i < 10; i++){
            System.out.print(row8.get(i).getState()+" "+row8.get(i).getRidAsCoor()+" "+ row8.get(i).getRid() +"\t");
        }*/

        //is_there_X_of_Y(1,3);
    }

   /* public void is_there_X_of_Y(int numberOfShips, int lengthOfShip) {
        System.out.println("Wywołanie is_there_X_of_Y dla numberOfShips = " + numberOfShips + " numberOfSegmentsInShip " + lengthOfShip);
        int nr_of_ships_found = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10 - lengthOfShip + 1; j++) {
                for (int s = 0; s < lengthOfShip; s++) {//szukanie w X statku
                    if (areasList.get(i).get(j + s).getState() != 1.0) {
                        break;
                    }
                    if (s == lengthOfShip - 1) {
                        nr_of_ships_found += 1;
                        System.out.println("ship found horizontally at : " + "x:" + i + " y:" + j);
                        //if_ship_found_horizontally_make_border(j ,i, lengthOfShip);
                    }
                }
            }
            //System.out.println();
        }
        System.out.println("nr_of_ships_found" + " " + nr_of_ships_found + " of length " + lengthOfShip + " horizontally");
    }*/

    public void justExit() {
        Platform.exit();
    }
}
