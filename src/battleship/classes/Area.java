package battleship.classes;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Area extends Rectangle {
    public enum State {
        WATER, SHIP, FORBIDDEN
    }

    private State state;
    private boolean isStateHidden;
    private boolean isHighlited;
    private boolean wasHit;
    private Player owner;
    private final Coordinates coordinates;

    public Area(Coordinates coordinates, double x, double y, double width, double height, Player owner) {
        super(x, y, width, height);
        this.state = State.WATER;
        this.isStateHidden = false;
        this.owner = owner;
        this.coordinates = coordinates;
        refreshAppearance();
    }

    public Area(Coordinates coordinates, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.state = State.WATER;
        this.isStateHidden = false;
        this.coordinates = coordinates;
        refreshAppearance();
    }

    public void setHighlited(boolean highlited) {
        isHighlited = highlited;
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

    public void refreshAppearance() {
        Paint newFill;
        if (isStateHidden) {
            newFill = Color.LIGHTBLUE;
        } else if (isHighlited) {
            newFill = state == State.WATER ? Color.YELLOW : Color.RED;
        } else {
            newFill = switch (this.state) {
                case SHIP -> wasHit ? Color.ORANGE : Color.BLACK;
                case FORBIDDEN -> Color.BLUE;
                case WATER -> wasHit ? Color.DEEPSKYBLUE : Color.LIGHTBLUE;
            };
        }

        this.setFill(newFill);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
