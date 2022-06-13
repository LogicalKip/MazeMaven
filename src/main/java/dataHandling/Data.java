package dataHandling;

public class Data {
    enum XorY {
        X,
        Y
    }

    public static final int VERT_AND_HORIZ_WALL = 0;
    public static final int VERTICAL_WALL = 1;
    public static final int HORIZONTAL_WALL = 2;
    public static final int NO_WALL = 3;

    public int[][] getWallArray() {
        return wallArray;
    }

    /**
     * shows presence of both vertical and horizontal with the same data.
     * 0, 1 : vertical wall.
     * 0, 2 : horizontal wall.
     * Therefore :
     * 0 : both walls
     * 1 : vertical wall
     * 2 : horizontal wall
     * 3 : no wall at all.
     * It's the output of the MazeBuilder, and isn't used at all to create the maze
     */
    public final int[][] wallArray;
    /**
     * ex r
     */
    public int x;
    /**
     * ex s
     */
    public int y;

    public Data(int maxHorizontal, int maxVertical) {
        this.wallArray = new int[maxHorizontal + 1][maxVertical + 1];
        for (int i = 0; i <= maxHorizontal; i++) {
            this.wallArray[i] = new int[maxVertical + 1];
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void decrement(XorY xorY) {
        if (xorY == XorY.X) {
            x--;
        } else {
            y--;
        }
    }

    public void increment(XorY xorY) {
        if (xorY == XorY.X) {
            x++;
        } else {
            y++;
        }
    }

    public void setY(int i) {
        y = i;
    }

    public void setX(int i) {
        x = i;
    }

    public int getCurrentWall() {
        return wallArray[x][y];
    }

    public void setPossibleWallAtCurrent(int possibleWall) {
        wallArray[x][y] = getCurrentWall() == VERT_AND_HORIZ_WALL ? possibleWall : NO_WALL;
    }

    public void setWallAtCurrent(int horizontalWall) {
        wallArray[x][y] = horizontalWall;
    }
}