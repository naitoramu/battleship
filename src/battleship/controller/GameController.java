package battleship.controller;

import battleship.classes.Area;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import  javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    Rectangle rectangleFieldA;
    @FXML
    Rectangle rectangleFieldB;

    ArrayList<ArrayList<Area>> arrListRecStorageLeft = new ArrayList<>(10);//pamięć

    ArrayList<Area> row1 = new ArrayList<>(10);
    ArrayList<Area> row2 = new ArrayList<>(10);
    ArrayList<Area> row3 = new ArrayList<>(10);
    ArrayList<Area> row4 = new ArrayList<>(10);
    ArrayList<Area> row5 = new ArrayList<>(10);
    ArrayList<Area> row6 = new ArrayList<>(10);
    ArrayList<Area> row7 = new ArrayList<>(10);
    ArrayList<Area> row8 = new ArrayList<>(10);
    ArrayList<Area> row9 = new ArrayList<>(10);
    ArrayList<Area> row10 = new ArrayList<>(10);

    int initial_state = 0;
    double x_coordinate = 165;
    double y_coordinate = 165;
    String[][] matrixOfNames = new String[10][10];//macierz nazw pól - ex. "A1", "C3", "J10"


    public void initialize(){
        setMatrixOfNames();
        printMatrixOfNames();
        fillStorage();
        createBattleField();
        //printStorageAsStates();
        clickingAtBattlefield_AssociationOfEventHandlersForRectangles();



    }

    public void setMatrixOfNames(){
        for(int i = 0; i < 10; i++){//i odp za y -> vertical - > cyfry
            for(int j=0; j < 10; j++){// j odp za z -> horizontal -> litery
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
        arrListRecStorageLeft.add(row1);
        arrListRecStorageLeft.add(row2);
        arrListRecStorageLeft.add(row3);
        arrListRecStorageLeft.add(row4);
        arrListRecStorageLeft.add(row5);
        arrListRecStorageLeft.add(row6);
        arrListRecStorageLeft.add(row7);
        arrListRecStorageLeft.add(row8);
        arrListRecStorageLeft.add(row9);
        arrListRecStorageLeft.add(row10);

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Area rec = new Area(x_coordinate+30*j,y_coordinate+30*i,30,30, initial_state, 10*i + j, matrixOfNames[i][j]);
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

    public void clickingAtBattlefield_AssociationOfEventHandlersForRectangles(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++) {
                int finalI1 = i;
                int finalJ1 = j;
                arrListRecStorageLeft.get(i).get(j).setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                        arrListRecStorageLeft.get(finalI1).get(finalJ1).setState(1);
                        arrListRecStorageLeft.get(finalI1).get(finalJ1).setFill(Color.YELLOW);
                        arrListRecStorageLeft.get(finalI1).get(finalJ1).setStroke(null);
                    }
                });
            }
        }
    }

    public void check(){
        System.out.println("Sprawdzanie wywołane - printStorageAsStates()");
        printStorageAsStates();
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

//    public void is_there_X_of_Y(int numberOfShips, int lengthOfShip){
//        System.out.println("Wywołanie is_there_X_of_Y dla numberOfShips = " + numberOfShips + " numberOfSegmentsInShip " + lengthOfShip);
//        int nr_of_ships_found = 0;
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10 - lengthOfShip + 1; j++) {
//                for (int s = 0; s < lengthOfShip; s++) {//szukanie w X statku
//                    if (arrListRecStorageLeft.get(10*i + j + s).getState() != 1.0) {
//                        break;
//                    }
//                    if (s == lengthOfShip - 1) {
//                        nr_of_ships_found += 1;
//                        System.out.println("ship found horizontally at : " + "x:" + i + " y:" + j);
//                        //if_ship_found_horizontally_make_border(j ,i, lengthOfShip);
//                        //printMatrix(matrixA);
//                    }
//                }
//
//            }
//            //System.out.println();
//        }
//        System.out.println("nr_of_ships_found" + " " + nr_of_ships_found + " of length " + lengthOfShip + " horizontally");
//    }

    public void justExit(){
        Platform.exit();
    }

}
