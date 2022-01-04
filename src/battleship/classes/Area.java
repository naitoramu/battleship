package battleship.classes;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Area extends Rectangle {

    private int state;
    private final int rid;
    private final String ridAsCoor;
    private boolean belongsToPlane1;

    public Area(double x, double y, double width, double height, int state, int rid, String ridAsCoor, boolean belongsToPlane1, Paint fill) {
        super(x, y, width, height);
        this.state = state;
        this.rid = rid;
        this.ridAsCoor = ridAsCoor;
        this.belongsToPlane1 = belongsToPlane1;
        super.setFill(fill);
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

    public boolean isBelongsToPlane1() {
        return belongsToPlane1;

    }

    public String getRidAsCoor() {
        return ridAsCoor;
    }


    @Override
    public String toString() {
        return "Area{" +
                "state=" + state +
                ", rid=" + rid +
                ", ridAsCoor='" + ridAsCoor + '\'' +
                '}';
    }
}
