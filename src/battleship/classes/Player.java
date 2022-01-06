package battleship.classes;

import java.util.ArrayList;

public class Player {
    private Integer points;
    private ArrayList<Area> playersAreas;
    private final boolean isAI;

    public Player(boolean isAI) {
        this.points = 0;
        this.isAI = isAI;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setPlayersAreas(ArrayList<Area> playersAreas) {
        this.playersAreas = playersAreas;
    }

    public ArrayList<Area> getPlayersAreas() {
        return playersAreas;
    }

    public void addPoint() {
        points++;
    }

    public Integer getScore() {
        return points;
    }

}
