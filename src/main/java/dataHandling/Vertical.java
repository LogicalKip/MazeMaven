package dataHandling;

public class Vertical implements Orientation {
    @Override
    public Data.XorY getXOrY() {
        return Data.XorY.Y;
    }

    @Override
    public int getTypeOfWall() {
        return Data.VERTICAL_WALL;
    }
}
