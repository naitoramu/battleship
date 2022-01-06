package battleship.classes;

import battleship.controller.GameController;

import java.util.Map;

public class Player {
    private Integer points;
    private final Board board;
    private final boolean isAI;

    public Player(boolean isAI, GameController game) {
        this.points = 0;
        this.isAI = isAI;
        this.board = new Board(this, game);
    }

    public boolean isAI() {
        return isAI;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void addPoint() {
        points++;
    }

    public Integer getScore() {
        return points;
    }

    public Board getBoard() {
        return board;
    }
}
