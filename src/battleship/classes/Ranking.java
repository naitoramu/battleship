package battleship.classes;

import static battleship.Main.getInterfaceLanguage;

import java.io.IOException;
import java.util.ArrayList;

import battleship.Main;
import battleship.model.User;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.cell.PropertyValueFactory;

public class Ranking {

    private CSVDictReader dictionary;

    private CustomTableColumn<User, String> usernameTableColumn;
    private ReadOnlyDoubleProperty tableWidthProperty;

    private ArrayList<CustomTableColumn> easyLvlTableColumns;
    private ArrayList<CustomTableColumn> mediumLvlTableColumns;
    private ArrayList<CustomTableColumn> hardLvlTableColumns;

    public Ranking(ReadOnlyDoubleProperty tableWidthProperty) throws IOException {

        dictionary = Main.getDictionary();
        easyLvlTableColumns = new ArrayList<>();
        mediumLvlTableColumns = new ArrayList<>();
        hardLvlTableColumns = new ArrayList<>();
        this.tableWidthProperty = tableWidthProperty;

        createUsernameTableColumn();
        createTableColumnByDifficultyLvl("EASY", easyLvlTableColumns);
        createTableColumnByDifficultyLvl("MEDIUM", mediumLvlTableColumns);
        createTableColumnByDifficultyLvl("HARD", hardLvlTableColumns);

        setColumnsWidth(easyLvlTableColumns, 0.2);
        setColumnsWidth(mediumLvlTableColumns, 0.2);
        setColumnsWidth(hardLvlTableColumns, 0.2);

    }

    private void createUsernameTableColumn() {
        usernameTableColumn = new CustomTableColumn("username");
        usernameTableColumn.setText(dictionary.getLabelByName("username").get(getInterfaceLanguage()));
        usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameTableColumn.prefWidthProperty().bind(tableWidthProperty.multiply(0.4));
    }

    private void createTableColumnByDifficultyLvl(String difficulty, ArrayList<CustomTableColumn> tableColumns) {

        difficulty = difficulty.toLowerCase();

        CustomTableColumn<User, Integer> numberOfGamesTableColumn = new CustomTableColumn("number_of_games");
        numberOfGamesTableColumn.setText(dictionary.getLabelByName(numberOfGamesTableColumn.getColumnName())
                .get(getInterfaceLanguage()));
        numberOfGamesTableColumn.setCellValueFactory(new PropertyValueFactory<>(difficulty + "LvlNumberOfGames"));

        CustomTableColumn<User, Integer> numberOfWinsTableColumn = new CustomTableColumn("number_of_wins");
        numberOfWinsTableColumn.setText(dictionary.getLabelByName(numberOfWinsTableColumn.getColumnName())
                .get(getInterfaceLanguage()));
        numberOfWinsTableColumn.setCellValueFactory(new PropertyValueFactory<>(difficulty + "LvlNumberOfWins"));

        CustomTableColumn<User, Integer> accuracyTableColumn = new CustomTableColumn("accuracy");
        accuracyTableColumn.setText(
                dictionary.getLabelByName(accuracyTableColumn.getColumnName()).get(getInterfaceLanguage()));
        accuracyTableColumn.setCellValueFactory(new PropertyValueFactory<>(difficulty + "LvlAccuracy"));

        tableColumns.add(numberOfGamesTableColumn);
        tableColumns.add(numberOfWinsTableColumn);
        tableColumns.add(accuracyTableColumn);
    }

    private void setColumnsWidth(ArrayList<CustomTableColumn> tableColumns, Double factor) {
        for (CustomTableColumn column : tableColumns) {
            column.prefWidthProperty().bind(tableWidthProperty.multiply(factor));
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
