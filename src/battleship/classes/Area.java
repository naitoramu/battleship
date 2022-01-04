package battleship.classes;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Area extends Rectangle {
    public enum State {
        WATER, SHIP, GHOST, FORBIDDEN, ERROR
    }

    private State state;
    private final int rid;
    private final String ridAsCoor;
    private boolean isStateHidden;
    private boolean wasHit;
    private final Player owner;

    public Area(double x, double y, double width, double height, int rid, String ridAsCoor, Player owner) {
        super(x, y, width, height);
        this.state = State.WATER;
        this.rid = rid;
        this.ridAsCoor = ridAsCoor;
        this.isStateHidden = false;
        this.owner = owner;
        refreshAppearance();
    }

    public Player getOwner() {
        return owner;
    }

    public boolean wasHit() {
        return wasHit;
    }

    public void setHit() {
        wasHit = true;
        isStateHidden = false;
        refreshAppearance();
    }

    public void setStateHidden(boolean stateHidden) {
        isStateHidden = stateHidden;
        refreshAppearance();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        refreshAppearance();
    }

    public int getRid() {
        return rid;
    }

    public void refreshAppearance() {
        Paint newFill;
        if (isStateHidden) {
            newFill = Color.LIGHTBLUE;
        } else {
            newFill = switch (this.state) {
                case SHIP -> wasHit ? Color.ORANGE : Color.BLACK;
                case ERROR -> Color.RED;
                case GHOST -> Color.YELLOW;
                case FORBIDDEN -> Color.BLUE;
                case WATER -> wasHit ? Color.DEEPSKYBLUE : Color.LIGHTBLUE;
            };
        }

        this.setFill(newFill);
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
