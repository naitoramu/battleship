package battleship.controller;

import static battleship.Main.getUsers;

import java.io.IOException;

import battleship.classes.CSVDictReader;
import battleship.classes.CustomTableColumn;
import battleship.model.Statistics;
import battleship.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RankingController {

    @FXML
    private Button backButton;
    @FXML
    private Label titleLable;
    @FXML
    private ChoiceBox<String> difficultyLevelChoiceBox;
    @FXML
    private TableView rankingTableView;

    private String rootPath;
    private CSVDictReader tableColumnLabels;


    public RankingController() {
        rootPath = System.getProperty("user.dir");
    }

    @FXML
    void initialize() throws IOException {
        loadTableColumnLabels();

        CustomTableColumn<User, String> usernameTableColumn = new CustomTableColumn("username");
        usernameTableColumn.setText(tableColumnLabels.getLabelByName("username").get("PL"));
        usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        CustomTableColumn<User, Integer> numberOfGamesTableColumn = new CustomTableColumn("number_of_games");
        numberOfGamesTableColumn.setText(tableColumnLabels.getLabelByName(numberOfGamesTableColumn.getColumnName()).get("PL"));
        numberOfGamesTableColumn.setCellValueFactory(new PropertyValueFactory<>("easyLvlNumberOfGames"));

        CustomTableColumn<User, Integer> numberOfWinsTableColumn = new CustomTableColumn("number_of_wins");
        numberOfWinsTableColumn.setText(tableColumnLabels.getLabelByName(numberOfWinsTableColumn.getColumnName()).get("PL"));
        numberOfWinsTableColumn.setCellValueFactory(new PropertyValueFactory<>("easyLvlNumberOfWins"));

        CustomTableColumn<User, Integer> accuracyTableColumn = new CustomTableColumn("accuracy");
        accuracyTableColumn.setText(tableColumnLabels.getLabelByName(accuracyTableColumn.getColumnName()).get("PL"));
        accuracyTableColumn.setCellValueFactory(new PropertyValueFactory<>("easyLvlAccuracy"));
        
        rankingTableView.getColumns().add(usernameTableColumn);
        rankingTableView.getColumns().add(numberOfGamesTableColumn);
        rankingTableView.getColumns().add(numberOfWinsTableColumn);
        rankingTableView.getColumns().add(accuracyTableColumn);

        rankingTableView.getItems().addAll(getUsers());


    }

    private void loadTableColumnLabels() throws IOException {

        String csvFilePath = rootPath + "/src/battleship/lang/tableColumn-labels.csv";
        try {
            tableColumnLabels = new CSVDictReader(csvFilePath);
        } catch (Exception IOException) {
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }
    
}
