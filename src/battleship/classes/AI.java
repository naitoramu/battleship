package battleship.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {
    private final Random randomGenerator = new Random();

    public Area shoot(ArrayList<Area> opponentsBoard) {
        int shotIndex;
        do {
            shotIndex = randomGenerator.nextInt(opponentsBoard.size() - 1);
            //System.out.println(shotIndex);
            //System.out.println(opponentsBoard.get(shotIndex).wasHit());
        } while (opponentsBoard.get(shotIndex).wasHit());
        System.out.println("END");
        return opponentsBoard.get(shotIndex);
    }

    // TODO: Place ships only in available places, check if new ship fits the board(doesn't cross from one row to another, through side)
    public void placeShips(ArrayList<Area> playerBoard, List<Short> shipsLengths) {
        for (Short shipLength : shipsLengths) {
            int startAreaIndex = randomGenerator.nextInt(playerBoard.size() - 1);
            for (int i = startAreaIndex; i < startAreaIndex + shipLength; i++) {
                playerBoard.get(i).setState(Area.State.SHIP);
            }
        }
    }
}
