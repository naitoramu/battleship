package battleship.classes;

import battleship.Main;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameRecorder implements Serializable {
    final int playerOneId;
    final int playerTwoId;
    ArrayList<ShotRecord> shotQueue;
    ArrayList<Coordinates> playerOneShipsCoordinates;
    ArrayList<Coordinates> playerTwoShipsCoordinates;

    static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
    private static final long serialVersionUID = 1L;

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

    public void save() {
        try {
            File file = new File("saved_games/" + dateFormat.format(new Date()) + ".bs");
            file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<GameRecorder> readAll() {
        File folder = new File("saved_games");
        System.out.println(folder.getAbsolutePath());
        ArrayList<GameRecorder> records = new ArrayList<>();
        for (File file : folder.listFiles()) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                records.add((GameRecorder) objectIn.readObject());
            } catch (Exception e) {
                System.out.println("Error reading saved games");
            }
        }
        return records;
    }
}
