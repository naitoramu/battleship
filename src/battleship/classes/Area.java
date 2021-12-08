package battleship.classes;


import javafx.scene.shape.Rectangle;



public class Area extends Rectangle {

    private int state;
    private int rid;
    private String ridAsCoor;
    private int row;
    private int column;

    public Area(double x, double y, double width, double height, int state, int rid, String ridAsCoor, int row, int column) {
        super(x, y, width, height);
        this.state = state;
        this.rid = rid;
        this.ridAsCoor = ridAsCoor;
        this.row = row;
        this.column = column;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRidAsCoor() {
        return ridAsCoor;
    }

    public void setRidAsCoor(String ridAsCoor) {
        this.ridAsCoor = ridAsCoor;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
