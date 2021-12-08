package battleship.classes;


import javafx.scene.shape.Rectangle;



public class Area extends Rectangle {

    private int state;
    private int rid;
    private String ridAsCoor;

    public Area(double x, double y, double width, double height, int state, int rid, String ridAsCoor) {
        super(x, y, width, height);
        this.state = state;
        this.rid = rid;
        this.ridAsCoor = ridAsCoor;
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
}
