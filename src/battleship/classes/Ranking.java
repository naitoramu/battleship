package battleship.classes;

import static battleship.Main.getInterfaceLanguage;

import java.io.IOException;
import java.util.ArrayList;

import battleship.model.User;
import javafx.scene.control.cell.PropertyValueFactory;

public class Ranking {

    private String rootPath;
    private CSVDictReader tableColumnLabels;

    private CustomTableColumn<User, String> usernameTableColumn;

    private ArrayList<CustomTableColumn> easyLvlTableColumns;
    private ArrayList<CustomTableColumn> mediumLvlTableColumns;
    private ArrayList<CustomTableColumn> hardLvlTableColumns;

    public Ranking() throws IOException {
        rootPath = System.getProperty("user.dir");
        easyLvlTableColumns = new ArrayList<>();
        mediumLvlTableColumns = new ArrayList<>();
        hardLvlTableColumns = new ArrayList<>();

        loadTableColumnLabels();

        createUsernameTableColumn();
        createTableColumnByDifficultyLvl("EASY", easyLvlTableColumns);
        createTableColumnByDifficultyLvl("MEDIUM", mediumLvlTableColumns);
        createTableColumnByDifficultyLvl("HARD", hardLvlTableColumns);

    }

    private void createUsernameTableColumn() {
        usernameTableColumn = new CustomTableColumn("username");
        usernameTableColumn.setText(tableColumnLabels.getLabelByName("username").get(getInterfaceLanguage()));
        usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    private void createTableColumnByDifficultyLvl(String difficulty, ArrayList<CustomTableColumn> tableColumns) {

        difficulty = difficulty.toLowerCase();

        CustomTableColumn<User, Integer> numberOfGamesTableColumn = new CustomTableColumn("number_of_games");
        numberOfGamesTableColumn.setText(tableColumnLabels.getLabelByName(numberOfGamesTableColumn.getColumnName())
                .get(getInterfaceLanguage()));
        numberOfGamesTableColumn.setCellValueFactory(new PropertyValueFactory<>(difficulty + "LvlNumberOfGames"));

        CustomTableColumn<User, Integer> numberOfWinsTableColumn = new CustomTableColumn("number_of_wins");
        numberOfWinsTableColumn.setText(tableColumnLabels.getLabelByName(numberOfWinsTableColumn.getColumnName())
                .get(getInterfaceLanguage()));
        numberOfWinsTableColumn.setCellValueFactory(new PropertyValueFactory<>(difficulty + "LvlNumberOfWins"));

        CustomTableColumn<User, Integer> accuracyTableColumn = new CustomTableColumn("accuracy");
        accuracyTableColumn.setText(
                tableColumnLabels.getLabelByName(accuracyTableColumn.getColumnName()).get(getInterfaceLanguage()));
        accuracyTableColumn.setCellValueFactory(new PropertyValueFactory<>(difficulty + "LvlAccuracy"));

        tableColumns.add(numberOfGamesTableColumn);
        tableColumns.add(numberOfWinsTableColumn);
        tableColumns.add(accuracyTableColumn);
    }

    private void loadTableColumnLabels() throws IOException {

        String csvFilePath = rootPath + "/src/battleship/lang/tableColumn-labels.csv";
        try {
            tableColumnLabels = new CSVDictReader(csvFilePath);
        } catch (Exception IOException) {
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    public CustomTableColumn getUsernameTableColumn() {
        return usernameTableColumn;
    }
    public ArrayList<CustomTableColumn> getEasyLvlTableColumns() {
        return easyLvlTableColumns;
    }
    public ArrayList<CustomTableColumn> getMediumLvlTableColumns() {
        return mediumLvlTableColumns;
    }
    public ArrayList<CustomTableColumn> getHardLvlTableColumns() {
        return hardLvlTableColumns;
    }

}
