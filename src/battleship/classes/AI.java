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

    public boolean shoot(Map<Coordinates, Area> opponentsBoard) {
        Coordinates shotCoordinates;
        int diffLvl = 3;//here it should depend on users pre-settings
        do {
            shotCoordinates = new Coordinates(randomGenerator.nextInt(10), randomGenerator.nextInt(10));
                if(opponentsBoard.get(shotCoordinates).getState() == Area.State.SHIP){
                    switch(diffLvl) {
                            case 1:
                                if(changeTarget(0.8)){//easy
                                    shotCoordinates = new Coordinates(randomGenerator.nextInt(10), randomGenerator.nextInt(10));
                                }
                                break;
                            case 2:
                                if(changeTarget(0.35)){//medium
                                    shotCoordinates = new Coordinates(randomGenerator.nextInt(10), randomGenerator.nextInt(10));
                                }
                                break;
                                //for hard lvl it isn't changing so no need for statement
                        }
                }
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
        return areaToShoot.getState() == Area.State.SHIP;
    }

    // TODO: Place ships only in available places, check if new ship fits the board(doesn't cross from one row to another, through side)
    public void placeShips(Board board, List<Short> shipsLengths, boolean delayExecution) {
        for (Short shipLength : shipsLengths) {
            boolean placeHorizontal = randomGenerator.nextBoolean();
            ShipPlacement shipPlacement;
            do {
                Coordinates coordinates = new Coordinates(randomGenerator.nextInt(9), randomGenerator.nextInt(9));
                shipPlacement = board.getShipsAreas(board.getAreas().get(coordinates), placeHorizontal, shipLength);
            } while (!shipPlacement.isPossible());
            board.placeShip(shipPlacement);
        }

        game.setEveryShipPlaced(true);

        if (delayExecution) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.runLater(() -> game.playerReady());
                } catch (InterruptedException exc) {
                    throw new Error("Unexpected thread interruption");
                }
            });
            thread.start();

        } else {
            game.playerReady();
        }
    }

    public boolean changeTarget(double threshold){//probability of changing target
        Double rand = new Random().nextDouble();
        if(rand <= threshold){
            return true;
        }
        return false;
    }
}
    