public class Horizontal implements Orientation {
    @Override
    public Data.XorY getXOrY() {
        return Data.XorY.X;
    }

    @Override
    public int getTypeOfWall() {
        return MazeBuilder.HORIZONTAL_WALL;
    }
}
