package battleship.controller;

import battleship.classes.Area;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import  javafx.scene.shape.Rectangle;

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

    ArrayList<ArrayList<Area>> arrListRecStorageLeft = new ArrayList<>(10);//pamięć

    int initial_state = 0;
    double x_coordinate = 165;
    double y_coordinate = 165;
    String[][] matrixOfNames = new String[10][10];//macierz nazw pól - ex. "A1", "C3", "J10"
    boolean isPlayerOneTurn = true;
    boolean isSetup = true;
    boolean isShipDirectionHorizontal = true;
    List<Short> shipsLengthsFor1 = Arrays.asList(new Short[] {4, 3, 3, 2, 2, 2, 1, 1, 1, 1});
    List<Short> shipsLengthsFor2 = Arrays.asList(new Short[] {4, 3, 3, 2, 2, 2, 1, 1, 1, 1});



    EventHandler<MouseEvent> recClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Area recClicked = (Area) e.getSource();
            if(!isSetup){
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
            }else{
                placeShip(recClicked);
            }
        }
    };
    EventHandler<MouseEvent> recHoverHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Area recClicked = (Area) e.getSource();
            if(isSetup){

            }
        }
    };
    public void placeShip(Area recClicked){
        if(isShipDirectionHorizontal){
            recClicked.setState(0);
            //recClicked.ge
        }else{

        }
    }
    public void initialize(){
        setMatrixOfNames();
        printMatrixOfNames();
        fillStorage();
        createBattleField();

        //printStorageAsStates();
        //clickingAtBattlefield_AssociationOfEventHandlersForRectangles();



    }

    public void setMatrixOfNames(){
        for(int i = 0; i < 10; i++){//vertical - > cyfry
            for(int j=0; j < 10; j++){//horizontal -> litery
                String nameOfsquare = "";
                if(j == 0){   nameOfsquare += "A";  }
                if(j == 1){   nameOfsquare += "B";  }
                if(j == 2){   nameOfsquare += "C";  }
                if(j == 3){   nameOfsquare += "D";  }
                if(j == 4){   nameOfsquare += "E";  }
                if(j == 5){   nameOfsquare += "F";  }
                if(j == 6){   nameOfsquare += "G";  }
                if(j == 7){   nameOfsquare += "H";  }
                if(j == 8){   nameOfsquare += "I";  }
                if(j == 9){   nameOfsquare += "J";  }

                if(i == 0){    nameOfsquare += (i+1);  }
                if(i == 1){    nameOfsquare += (i+1);  }
                if(i == 2){    nameOfsquare += (i+1);  }
                if(i == 3){    nameOfsquare += (i+1);  }
                if(i == 4){    nameOfsquare += (i+1);  }
                if(i == 5){    nameOfsquare += (i+1);  }
                if(i == 6){    nameOfsquare += (i+1);  }
                if(i == 7){    nameOfsquare += (i+1);  }
                if(i == 8){    nameOfsquare += (i+1);  }
                if(i == 9){    nameOfsquare += (i+1);  }

                matrixOfNames[i][j] = nameOfsquare;
            }
        }
    }

    public void printMatrixOfNames(){
        System.out.println("********************************");
        System.out.println("printMatrixOfNames");
        System.out.println("********************************");

        for(int i = 0; i < 10; i++){
            for(int j=0; j < 10; j++){
                System.out.print(matrixOfNames[j][i] + "\t");
            }
            System.out.println();
        }
    }

    public void fillStorage(){
        for(int i = 0; i < 10; i++){
            arrListRecStorageLeft.add(new ArrayList<>(10));
        }

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Area rec = new Area(x_coordinate+30*j,y_coordinate+30*i,30,30, initial_state, 10*i + j, matrixOfNames[i][j], i , j);
                rec.addEventHandler(MouseEvent.MOUSE_CLICKED, recClickHandler);
                rec.addEventHandler(MouseEvent.MOUSE_ENTERED, recHoverHandler);
                arrListRecStorageLeft.get(i).add(rec);
            }
        }
    }

    public void printStorageAsStates(){
        System.out.println("********************************");
        System.out.println("printStorageAsStates");
        System.out.println("********************************");

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                System.out.print(arrListRecStorageLeft.get(i).get(j).getState() + "\t");
            }
            System.out.println();
        }
    }

    public void createBattleField(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                anchorPane.getChildren().add(arrListRecStorageLeft.get(i).get(j));

            }
        }
    }

    public void check(){
        System.out.println("Sprawdzanie wywołane - printStorageAsStates()");
        printStorageAsStates();
        is_there_X_of_Y(3,3);
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

    public void is_there_X_of_Y(int numberOfShips, int lengthOfShip){
        System.out.println("Wywołanie is_there_X_of_Y dla numberOfShips = " + numberOfShips + " numberOfSegmentsInShip " + lengthOfShip);
        int nr_of_ships_found = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10 - lengthOfShip + 1; j++) {
                for (int s = 0; s < lengthOfShip; s++) {//szukanie w X statku
                    if (arrListRecStorageLeft.get(i).get(j + s).getState() != 1.0) {
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
    }

    public void justExit(){
        Platform.exit();
    }

}
