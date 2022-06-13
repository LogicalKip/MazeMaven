package dataHandling;

public class Horizontal implements Orientation {
    @Override
    public Data.XorY getXOrY() {
        return Data.XorY.X;
    }

    @Override
    public int getTypeOfWall() {
        return Data.HORIZONTAL_WALL;
    }
}
