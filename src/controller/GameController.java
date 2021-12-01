package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import  javafx.scene.shape.Rectangle;
import model.SupremeRectangle;

import java.util.ArrayList;

public class GameController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    Rectangle rectangleFieldA;
    @FXML
    Rectangle rectangleFieldB;

    int state_depending_on_circumstances = 0;

    //int click_counter = 0;

    ArrayList<SupremeRectangle> Left = new ArrayList<SupremeRectangle>(100);
    double[][][] matrixA = new double[10][10][4];//3 dimension: [0] -> x, [1] -> y, [2] -> stan, [3] -> ID as nr,
    String[][] matrixAid = new String[10][10];//[] -> x row of ID as letter, [] -> y row if ID as number

    public void initialize(){
        rectangleFieldA.setStyle("-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 3;"); //ustawienie tła dla faktycznej macierzy
        System.out.println(Left);
        ridAsCoordinates();//wypełnia tablice koordynatów
        createBattleField(165,165);

        printMatrixIDasLetters(matrixAid);


//        createBattleField(830, 165);
//
//
//        System.out.println(Left);

//
//
//        arrayToMatrix(Left);
//        printMatrix(matrixA);
//        System.out.println(Left);






    }

    //165,165 i 165,830
    public void createBattleField(int x_coordinate, int y_coordinate){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                SupremeRectangle rec = new SupremeRectangle(x_coordinate+30*i,y_coordinate+30*j,30,30,state_depending_on_circumstances, 10*i + j, matrixAid[j][i]);
                SupremeRectangle.getClassCssMetaData();
                rec.setOnMouseClicked(new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && rec.getState() != 1.0) {
                                    rec.setState(1);
                                    rec.setFill(Color.BLACK);
                                    System.out.println(/*"Counter:" + click_counter + */ /*" X: " + rec.getX() + " Y:" + rec.getY() + " RID as nr: " + Left.indexOf(rec) + */
                                            " RidAsCoor: " + rec.getRidAsCoor() + " state: " + rec.getState());
                                    //click_counter++;
        //                            if (rec.getState() == 0) {
        //                                rec.setFill(Color.BLUE);
        //                            }
        //                            if (rec.getState() == 1) {
        //                                rec.setFill(Color.GRAY);
        //                            }

                        }else if(mouseEvent.getButton() == MouseButton.PRIMARY && rec.getState() == 1.0){
                            rec.setState(0);
                            rec.setFill(Color.web("0x14f2fa"));
                            System.out.println(/*"Counter:" + click_counter + */ /*" X: " + rec.getX() + " Y:" + rec.getY() + " RID as nr: " + Left.indexOf(rec) + */
                                    " RidAsCoor: " + rec.getRidAsCoor() + " state: " + rec.getState());
                        }else if(mouseEvent.getButton() == MouseButton.SECONDARY){
                            //click_counter++;
                            rec.setFill(Color.RED);
                            is_there_X_of_Y(1,3);
                        }
                        arrayToMatrix(Left);
                        ridAsCoordinates();
                        //printMatrix(matrixA);
                    }
                });
                anchorPane.getChildren().add(rec);
                Left.add(rec);
            }
        }
    }

    public void ridAsCoordinates(){
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

                matrixAid[i][j] = nameOfsquare;
                //Left.get(i + 10*j).setRidAsCoor(nameOfsquare);
                //nameOfsquare = "";

            }
        }
    }

    public void arrayToMatrix(ArrayList<SupremeRectangle> arrList){
        for(int i = 0; i < 10; i++){
            for(int j=0; j < 10; j++){
                matrixA[i][j][0] = arrList.get(i + 10*j).getX();
                matrixA[i][j][1] = arrList.get(i + 10*j).getY();
                matrixA[i][j][2] = arrList.get(i + 10*j).getState();
                matrixA[i][j][3]  = arrList.get(i + 10*j).getRid();
            }
        }
    }

    public void printMatrix(double[][][] matrix){
        for(int i = 0; i < 10; i++){
            for(int j=0; j < 10; j++){
                System.out.print("(" +matrix[i][j][0] + "," + matrix[i][j][1] + ") s: " + matrix[i][j][2] + " RID: " + Left.get(i+10*j).getRidAsCoor() + /*matrix[i][j][3]*/ matrixAid[i][j] +"\t");
            }
            System.out.println();
        }
    }

    public void printMatrixIDasLetters(String[][] matrix){
        for(int i = 0; i < 10; i++){
            for(int j=0; j < 10; j++){
                System.out.print(matrix[i][j] +"\t");
            }
            System.out.println();
        }
    }

    public void is_there_X_of_Y(int numberOfShips, int lengthOfShip) {
        System.out.println("Wywołanie is_there_X_of_Y dla numberOfShips = " + numberOfShips + " numberOfSegmentsInShip " + lengthOfShip);
        //najpierw sprawdzanie statków w poziomie
        boolean is_piece_of_ship = false;
        int nr_of_ships_found = 0;
        for (int i = 0; i < 10; i++) {//zmiana X
            for (int j = 0; j < 10 - lengthOfShip + 1; j++) {//zmiana Y
                //System.out.print(battleefield[i][j] + " ");
                for (int s = 0; s < lengthOfShip; s++) {//szukanie w X statku
                    if (matrixA[i][j + s][2] != 1.0) {
                        break;
                    }
                    if (s == lengthOfShip - 1) {
                        nr_of_ships_found += 1;
                        System.out.println("ship found horizontally: " + "x:" + j + " y:" + i);
                        if_ship_found_horizontally_make_border(j ,i, lengthOfShip);
                    }
                }

            }
            //System.out.println();
        }
        System.out.println("nr_of_ships_found" + " " + nr_of_ships_found);
    }

    public  void if_ship_found_horizontally_make_border(int x_cor, int y_cor, int lengthOfShip){
        int helper[][] = new int[12][12];//dzięki tej macierzy każdy znaleziony okręt dostanie obwódkę a potem wytniemy z niego macierz mniejszą -> brak problemów z brzegami
        int helper_border = 8;
        for(int i = 0; i < 12; i++){
            helper[0][i] = helper_border;
        }
        for(int i = 0; i < 12; i++){
            helper[11][i] = helper_border;
        }
        for(int i = 1; i < 11; i++){
            helper[i][0] = helper_border;
        }
        for(int i = 1; i < 11; i++){
            helper[i][11] = helper_border;
        }
        for(int i = 1; i < 11; i++){
            for(int j=1; j < 11; j++){
                helper[i][j] = (int) matrixA[i-1][j-1][2];
            }
            //System.out.println();
        }
        int x_cor_at_helper = x_cor + 1;
        int y_cor_at_helper = y_cor + 1;

        helper[y_cor_at_helper][x_cor_at_helper-1] = 9;
        helper[y_cor_at_helper][x_cor_at_helper + lengthOfShip] = 9;
        for(int i = 0; i < lengthOfShip+2;i++){
            helper[y_cor_at_helper+1][x_cor_at_helper-1+i] = 9;
            helper[y_cor_at_helper-1][x_cor_at_helper-1+i] = 9;
        }



        for(int i = 0; i < 12; i++){
            for(int j=0; j < 12; j++){
                System.out.print(helper[i][j]+"\t");
            }
            System.out.println();
        }



    }

//    public void is_there_only_x_with_y_segments(int[][][] matrix,int numer_of_shpis , int number_of_segments_in_ship ){
//        for(int i = 0; i < 10; i++){
//            for(int )
//        }
//    }

    public void justExit(){
        Platform.exit();
    }
    public void info(){
        System.out.println("info");
    }
}
