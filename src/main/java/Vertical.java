public class Vertical implements Orientation {
    @Override
    public Data.XorY getXOrY() {
        return Data.XorY.Y;
    }

    @Override
    public int getTypeOfWall() {
        return MazeBuilder.VERTICAL_WALL;
    }
}
