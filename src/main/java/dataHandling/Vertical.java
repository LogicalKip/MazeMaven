package dataHandling;

public class Vertical implements Orientation {
    @Override
    public MazeData.XorY getXOrY() {
        return MazeData.XorY.Y;
    }

    @Override
    public boolean isHorizontal() {
        return false;
    }
}
