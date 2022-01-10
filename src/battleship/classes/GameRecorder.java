package battleship.classes;

import java.util.ArrayList;

public class GameRecorder {
    final int playerOneId;
    final int playerTwoId;
    ArrayList<ShotRecord> shotQueue;
    ArrayList<Coordinates> playerOneShipsCoordinates;
    ArrayList<Coordinates> playerTwoShipsCoordinates;

    public GameRecorder(Player playerOne, Player playerTwo) {
        this.playerOneId = playerOne.getUserId();
        this.playerTwoId = playerTwo.getUserId();
        this.shotQueue = new ArrayList<>();
    }

    public void recordShot(Area shotArea) {
        shotQueue.add(new ShotRecord(shotArea.getOwner(), shotArea.getCoordinates().copy()));
    }

    public ArrayList<ShotRecord> getRecords() {
        return shotQueue;
    }

    public void setPlayerOneShipsCoordinates(ArrayList<Coordinates> playerOneShipsCoordinates) {
        this.playerOneShipsCoordinates = playerOneShipsCoordinates;
    }

    public void setPlayerTwoShipsCoordinates(ArrayList<Coordinates> playerTwoShipsCoordinates) {
        this.playerTwoShipsCoordinates = playerTwoShipsCoordinates;
    }

    public ArrayList<Coordinates> getPlayerOneShipsCoordinates() {
        return playerOneShipsCoordinates;
    }

    public ArrayList<Coordinates> getPlayerTwoShipsCoordinates() {
        return playerTwoShipsCoordinates;
    }

    public int getPlayerOneId() {
        return playerOneId;
    }

    public int getPlayerTwoId() {
        return playerTwoId;
    }
}
