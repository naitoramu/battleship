package battleship.classes;

import java.util.ArrayList;

public class ShipPlacement {
    public boolean isPossible() {
        return isPossible;
    }

    public final boolean isPossible;
    public final ArrayList<Area> areas;

    public ArrayList<Area> getAreas() {
        return areas;
    }

    public ShipPlacement(ArrayList<Area> areas, boolean isPossible) {
        this.areas = areas;
        this.isPossible = isPossible;
    }
}
