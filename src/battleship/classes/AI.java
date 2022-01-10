package battleship.classes;

import battleship.controller.GameController;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class AI {
    private final Random randomGenerator = new Random();
    private final GameController game;

    public AI(GameController game) {
        this.game = game;
    }

    public void shoot(Map<Coordinates, Area> opponentsBoard) {
        Coordinates shotCoordinates;
        do {
            shotCoordinates = new Coordinates(randomGenerator.nextInt(10), randomGenerator.nextInt(10));
        } while (opponentsBoard.get(shotCoordinates).wasHit());

        final Area areaToShoot = opponentsBoard.get(shotCoordinates);

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10);
                Platform.runLater(() -> game.handleShot(areaToShoot));
            } catch (InterruptedException exc) {
                throw new Error("Unexpected thread interruption");
            }
        });
        thread.start();
    }

    // TODO: Place ships only in available places, check if new ship fits the board(doesn't cross from one row to another, through side)
    public void placeShips(Board board, List<Short> shipsLengths) {
        final Timeline timeline = new Timeline();
        for (Short shipLength : shipsLengths) {
            boolean placeHorizontal = randomGenerator.nextBoolean();
            ShipPlacement shipPlacement;
            do {
                Coordinates coordinates = new Coordinates(randomGenerator.nextInt(9), randomGenerator.nextInt(9));
                shipPlacement = board.getShipsAreas(board.getAreas().get(coordinates), placeHorizontal, shipLength);
            } while (!shipPlacement.isPossible());
            board.placeShip(shipPlacement);
        }

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1500);
                Platform.runLater(() -> game.playerReady());
            } catch (InterruptedException exc) {
                throw new Error("Unexpected thread interruption");
            }
        });
        thread.start();
    }
}
