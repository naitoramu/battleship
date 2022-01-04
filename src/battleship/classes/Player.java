package battleship.classes;

import java.util.ArrayList;

public class Player {
    private Integer points;
    private ArrayList<Area> playersAreas;

    public Player() {
        this.points = 0;
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
