package battleship.controller;

import static battleship.Main.getUsers;

import java.io.IOException;
import java.util.ArrayList;

import battleship.classes.CustomTableColumn;
import battleship.classes.Ranking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class RankingController {

    @FXML
    private Button backButton;
    @FXML
    private Label titleLable;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TableView rankingTableView;

    private ArrayList<CustomTableColumn> currentTableColumns;

    private Ranking ranking;


    public RankingController() throws IOException {

        ranking = new Ranking();
    }

    @FXML
    void initialize() throws IOException {

        categoryChoiceBox.getItems().add("EASY");
        categoryChoiceBox.getItems().add("MEDIUM");
        categoryChoiceBox.getItems().add("HARD");

        categoryChoiceBox.setOnAction((event) -> {
            
            switchCategory();
            System.out.println(categoryChoiceBox.getValue());

        });
        
        rankingTableView.getColumns().add(ranking.getUsernameTableColumn());

        currentTableColumns = ranking.getEasyLvlTableColumns();

        rankingTableView.getColumns().addAll(currentTableColumns);

        categoryChoiceBox.setValue("EASY");

        rankingTableView.getItems().addAll(getUsers());

    }

    private void switchCategory() {

        switch(categoryChoiceBox.getValue()) {
            case "EASY":
            setEasyTableColumns();
            break;

            case "MEDIUM":
            setMediumTableColumns();
            break;

            case "HARD":
            setHardTableColumns();
            break;

            default:
            System.out.println("Not known category");
            break;
        }
    }

    private void setEasyTableColumns() {
        rankingTableView.getColumns().removeAll(currentTableColumns);

        currentTableColumns = ranking.getEasyLvlTableColumns();
        rankingTableView.getColumns().addAll(currentTableColumns);
    }

    private void setMediumTableColumns() {
        rankingTableView.getColumns().removeAll(currentTableColumns);

        currentTableColumns = ranking.getMediumLvlTableColumns();
        rankingTableView.getColumns().addAll(currentTableColumns);
    }

    private void setHardTableColumns() {
        rankingTableView.getColumns().removeAll(currentTableColumns);

        currentTableColumns = ranking.getHardLvlTableColumns();
        rankingTableView.getColumns().addAll(currentTableColumns);
    }
    
}
