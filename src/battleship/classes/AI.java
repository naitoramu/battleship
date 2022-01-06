package battleship.classes;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AI {
    private final Random randomGenerator = new Random();

    public Area shoot(Map<Coordinates, Area> opponentsBoard) {
        Coordinates shotCoordinates;
        do {
            shotCoordinates = new Coordinates(randomGenerator.nextInt(10), randomGenerator.nextInt(10));
        } while (opponentsBoard.get(shotCoordinates).wasHit());
        return opponentsBoard.get(shotCoordinates);
    }

    // TODO: Place ships only in available places, check if new ship fits the board(doesn't cross from one row to another, through side)
    public void placeShips(Board board, List<Short> shipsLengths) {
        for (Short shipLength : shipsLengths) {
            boolean placeHorizontal = randomGenerator.nextBoolean();
            ShipPlacement shipPlacement;
            do {
                Coordinates coordinates = new Coordinates(randomGenerator.nextInt(9), randomGenerator.nextInt(9));
                shipPlacement = board.getShipsAreas(board.getAreas().get(coordinates), placeHorizontal, shipLength);
            } while (!shipPlacement.isPossible());
            board.placeShip(shipPlacement);
        }
    }
}
