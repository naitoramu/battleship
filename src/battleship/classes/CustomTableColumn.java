package battleship.classes;

import javafx.scene.control.TableColumn;

public class CustomTableColumn<S, T> extends TableColumn<S, T> {

    private String columnName;

    public CustomTableColumn(String tableName) {
        this.setColumnName(tableName);
    }

    public String getColumnName() {
        return columnName;
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    

}
