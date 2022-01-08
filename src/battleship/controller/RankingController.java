package battleship.controller;

import static battleship.Main.getUsers;

import java.io.IOException;
import java.util.ArrayList;

import battleship.Main;
import battleship.classes.CSVDictReader;
import battleship.classes.CustomTableColumn;
import battleship.classes.DifficultyLevel;
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
    private ChoiceBox<DifficultyLevel> categoryChoiceBox;
    @FXML
    private TableView rankingTableView;

    private CSVDictReader dictionary;


    private ArrayList<CustomTableColumn> currentTableColumns;

    private Ranking ranking;


    public RankingController() throws IOException {
        this.dictionary = Main.getDictionary();
    }

    @FXML
    void initialize() throws IOException {

        backButton.setText(dictionary.getLabelByName("back").get(Main.getInterfaceLanguage()));
        titleLabel.setText(dictionary.getLabelByName("rank").get(Main.getInterfaceLanguage()));

        ranking = new Ranking(rankingTableView.widthProperty());

        addDifficultyLevelsToChoiceBox();

        categoryChoiceBox.setOnAction((event) -> {
            
            switchCategory();
            System.out.println(categoryChoiceBox.getValue());

        });
        
        rankingTableView.getColumns().add(ranking.getUsernameTableColumn());

        currentTableColumns = ranking.getEasyLvlTableColumns();

        rankingTableView.getColumns().addAll(currentTableColumns);

        rankingTableView.getItems().addAll(getUsers());

    }

    private void addDifficultyLevelsToChoiceBox() {
        categoryChoiceBox.getItems().addAll(generateDifficultyLevels());
        categoryChoiceBox.setValue(categoryChoiceBox.getItems().get(0));
    } 

    private ArrayList<DifficultyLevel> generateDifficultyLevels() {
        DifficultyLevel easyLevel = new DifficultyLevel("easy");
        DifficultyLevel mediumLevel = new DifficultyLevel("medium");
        DifficultyLevel hardLevel = new DifficultyLevel("hard");

        ArrayList<DifficultyLevel> difficultyLevels = new ArrayList<>();
        difficultyLevels.add(easyLevel);
        difficultyLevels.add(mediumLevel);
        difficultyLevels.add(hardLevel);

        return difficultyLevels;
    }

    private void switchCategory() {
        String category = categoryChoiceBox.getValue().getName();

        switch(category) {
            case "easy":
            setEasyTableColumns();
            break;

            case "medium":
            setMediumTableColumns();
            break;

            case "hard":
            setHardTableColumns();
            break;

            default:
            System.out.println("Not known category: " + category);
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
