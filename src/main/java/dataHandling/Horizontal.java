package dataHandling;

public class Horizontal implements Orientation {
    @Override
    public MazeData.XorY getXOrY() {
        return MazeData.XorY.X;
    }

    @Override
    public int getTypeOfWall() {
        return MazeData.HORIZONTAL_WALL;
    }
}
