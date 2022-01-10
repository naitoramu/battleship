package battleship.classes;

public class ShotRecord {
    final int targetPlayerId;
    final Coordinates areaCoordinates;

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
