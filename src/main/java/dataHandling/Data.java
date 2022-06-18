package dataHandling;

public class Data {
    public static final int VERT_AND_HORIZ_WALL = 0;
    public static final int VERTICAL_WALL = 1;
    public static final int HORIZONTAL_WALL = 2;
    public static final int NO_WALL = 3;
    /**
     * shows presence of both vertical and horizontal with the same data.
     * 0, 1 : vertical wall.
     * 0, 2 : horizontal wall.
     * Therefore :
     * 0 : both walls
     * 1 : vertical wall
     * 2 : horizontal wall
     * 3 : no wall at all.
     * Starts fully filled with double walls, then gets simplified little by little.
     * It's the output of the MazeBuilder, and isn't used at all to create the maze
     */
    public final int[][] wallArray;
    private final int maxHorizontal;
    private final int maxVertical;
    /**
     * Everything is at false at start except the entrance door.
     * At the end everything is true except the top and side tiles
     * Maybe means "has been processed" ?
     */
    private final boolean[][] processed;
    /**
     * ex r
     */
    public int x;
    /**
     * ex s
     */
    public int y;
    /**
     * ex c
     */
    private int stepCount;

    public Data(int maxHorizontal, int maxVertical, int entrancePosition) {
        this.processed = new boolean[maxHorizontal + 1][maxVertical + 1];
        this.maxHorizontal = maxHorizontal;
        this.maxVertical = maxVertical;
        this.wallArray = new int[maxHorizontal + 1][maxVertical + 1];
        this.stepCount = 2;
        for (int i = 0; i <= maxHorizontal; i++) {
            this.wallArray[i] = new int[maxVertical + 1];
        }
        for (int i = 0; i <= maxHorizontal; i++) {
            processed[i] = new boolean[maxVertical + 1];
        }

        processed[entrancePosition][1] = true;
    }

    public boolean yMaxed() {
        return y == maxVertical;
    }

    public int[][] getWallArray() {
        return wallArray;
    }

    public boolean stepsAreNotAllFilled() {
        return stepCount < this.maxHorizontal * this.maxVertical + 1;
    }

    /**
     * Moves to the right. If no more room, Move to first tile of next line above.
     */
    public void nextTile() {
        if (x == maxHorizontal) {
            y = (y % maxVertical) + 1;
        }
        x = (x % maxHorizontal) + 1;
    }

    public void nextStep() {
        processed[x][y] = true;
        stepCount++;
    }

    public void setY(int i) {
        y = i;
    }

    public void setX(int i) {
        x = i;
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

    public void allowPassage(Orientation orientation) {
        wallArray[x][y] = wallArray[x][y] == VERT_AND_HORIZ_WALL ? orientation.getTypeOfWall() : NO_WALL;
    }

    public boolean isProcessedAt(int xDelta, int yDelta) {
        var xChanged = x + xDelta;
        var yChanged = y + yDelta;
        if (xChanged == 0 || yChanged == 0) {
            return true;
        }
        if (xChanged > maxHorizontal || yChanged > maxVertical) {
            return true;
        }
        return processed[xChanged][yChanged];
    }

    public void setWallAtCurrent(int wallType) {
        wallArray[x][y] = wallType;
    }

    public boolean isProcessedAtCurrent() {
        return processed[x][y];
    }

    enum XorY {
        X,
        Y
    }
}