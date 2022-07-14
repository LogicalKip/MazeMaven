package dataHandling;

public class Horizontal implements Orientation {
    @Override
    public MazeData.XorY getXOrY() {
        return MazeData.XorY.X;
    }

    @Override
    public boolean getTypeOfWall() {
        return true;
    }
}
