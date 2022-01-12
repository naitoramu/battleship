package battleship.classes;

import java.io.Serializable;

public class ShotRecord implements Serializable {
    final int targetPlayerId;
    final Coordinates areaCoordinates;

    private static final long serialVersionUID = 1L;

    public ShotRecord(Player victim, Coordinates areaCoordinates) {
        this.targetPlayerId = victim.getUserId();
        this.areaCoordinates = areaCoordinates;
    }

    public int getTargetPlayerId() {
        return targetPlayerId;
    }

    public Coordinates getAreaCoordinates() {
        return areaCoordinates;
    }
}
