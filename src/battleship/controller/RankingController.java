package battleship.controller;

import static battleship.Main.getUsers;

import java.io.IOException;
import java.util.ArrayList;

import battleship.Main;
import battleship.classes.CSVDictReader;
import battleship.classes.CustomTableColumn;
import battleship.classes.Ranking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class RankingController {

    @FXML
    private Button backButton;
    @FXML
    private Label titleLabel;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TableView rankingTableView;

    private CSVDictReader buttonLabels;
    private String rootPath;

    private ArrayList<CustomTableColumn> currentTableColumns;

    private Ranking ranking;


    public RankingController() throws IOException {
        rootPath = System.getProperty("user.dir");
    }

    @FXML
    void initialize() throws IOException {

        loadButtonLabels();
        backButton.setText(buttonLabels.getLabelByName("back").get(Main.getInterfaceLanguage()));
        titleLabel.setText(buttonLabels.getLabelByName("rank").get(Main.getInterfaceLanguage()));

        ranking = new Ranking(rankingTableView.widthProperty());

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

    private void loadButtonLabels() throws IOException {

        String csvFilePath = rootPath + "/src/battleship/lang/button-labels.csv";
        try {
            buttonLabels = new CSVDictReader(csvFilePath);
        } catch (Exception IOException) {
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        backToMainMenu((Button) actionEvent.getSource());
    }

    private void backToMainMenu(Button btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/mainMenuView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/mainMenu.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

}
