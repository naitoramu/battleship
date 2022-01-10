package battleship.classes;

import battleship.controller.GameController;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class Board {
    List<Coordinates> shipSurroundingsIndexIncrements = Arrays.asList(
            new Coordinates(-1, -1),
            new Coordinates(0, -1),
            new Coordinates(1, -1),
            new Coordinates(-1, 0),
            new Coordinates(1, 0),
            new Coordinates(-1, 1),
            new Coordinates(0, 1),
            new Coordinates(1, 1));

    private Map<Coordinates, Area> areas;
    private Player owner;

    public Board(Player player, GameController game) {
        this.owner = player;
        this.areas = new HashMap<>();

        int xOffset = 52;
        int yOffset = 70;
        int areaSize = 30;

        for (short row = 0; row < 10; row++) {
            for (short col = 0; col < 10; col++) {
                Coordinates newAreaCoordinates = new Coordinates(col, row);
                Area newArea = new Area(newAreaCoordinates, xOffset + areaSize * col, yOffset + areaSize * row, areaSize, areaSize, player);
                newArea.addEventHandler(MouseEvent.MOUSE_CLICKED, game.areaClickHandler);
                newArea.addEventHandler(MouseEvent.MOUSE_ENTERED, game.areaHoverHandler);

                areas.put(newAreaCoordinates, newArea);
            }
        }
    }

    public Board() {
        this.areas = new HashMap<>();

        int xOffset = 52;
        int yOffset = 70;
        int areaSize = 30;

        for (short row = 0; row < 10; row++) {
            for (short col = 0; col < 10; col++) {
                Coordinates newAreaCoordinates = new Coordinates(col, row);
                Area newArea = new Area(newAreaCoordinates, xOffset + areaSize * col, yOffset + areaSize * row, areaSize, areaSize);

                areas.put(newAreaCoordinates, newArea);
            }
        }
    }

    public void handleSetupComplete() {
        for (Area area : areas.values()) {
            if (area.getState() != Area.State.SHIP) area.setState(Area.State.WATER);
            area.setStateHidden(true);
        }
    }

    public ShipPlacement getShipsAreas(Area recClicked, boolean isShipDirectionHorizontal, short shipLength) {
        boolean isAllowed = true;
        ArrayList<Area> occupiedAreas = new ArrayList<>();
        Coordinates currentCoordinates = recClicked.getCoordinates().copy();
        Map<Coordinates, Area> board = owner.getBoard().getAreas();
        while (isShipDirectionHorizontal
                ? currentCoordinates.getX() < recClicked.getCoordinates().getX() + shipLength
                : currentCoordinates.getY() < recClicked.getCoordinates().getY() + shipLength) {

            if (currentCoordinates.getY() > 9 || currentCoordinates.getX() > 9) {
                isAllowed = false;
                break;
            }

            Area currentArea = board.get(currentCoordinates);
            if (currentArea.getState() == Area.State.SHIP || currentArea.getState() == Area.State.FORBIDDEN)
                isAllowed = false;
            occupiedAreas.add(currentArea);

            if (isShipDirectionHorizontal) {
                currentCoordinates.setX(currentCoordinates.getX() + 1);
            } else {
                currentCoordinates.setY(currentCoordinates.getY() + 1);
            }
            System.out.println(currentCoordinates);
        }
        return new ShipPlacement(occupiedAreas, isAllowed);
    }

    public void placeShip(ShipPlacement shipPlacement) {
        for (Area area : shipPlacement.getAreas()) {
            makeAreaShip(area);
        }
    }

    public void makeAreaShip(Area baseArea) {
        baseArea.setState(Area.State.SHIP);
        for (Coordinates indexIncrement : shipSurroundingsIndexIncrements) {
            try {
                Area currentArea = areas.get(baseArea.getCoordinates().add(indexIncrement));
                if (currentArea.getState() != Area.State.SHIP)
                    currentArea.setState(Area.State.FORBIDDEN);
            } catch (Exception e) {
            }
        }
    }

    public Map<Coordinates, Area> getAreas() {
        return areas;
    }
}
