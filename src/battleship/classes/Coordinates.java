package battleship.classes;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates add(Coordinates coordinates) {
        return new Coordinates(x + coordinates.getX(), y + coordinates.getY());
    }

    public Coordinates copy() {
        return new Coordinates(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates key = (Coordinates) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 100 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
