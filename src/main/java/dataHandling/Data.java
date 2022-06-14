package dataHandling;

public class Data {
    private final int maxHorizontal;
    private final int maxVertical;



    enum XorY {
        X,
        Y;
    }
    public static final int VERT_AND_HORIZ_WALL = 0;

    public static final int VERTICAL_WALL = 1;
    public static final int HORIZONTAL_WALL = 2;
    public static final int NO_WALL = 3;
    public int[][] getWallArray() {
        return wallArray;
    }


    /**
     * ex c
     */
    private int stepCount;


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

    /**
     * Everything is at false at start except the entrance door.
     * At the end everything is true except the top and side tiles
     * Maybe means "has been processed" ?
     */
    private final boolean[][] processed;

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

    public boolean stepsAreNotAllFilled() {
        return stepCount < this.maxHorizontal * this.maxVertical + 1;
    }

    public void continueXY() {
        if (x == maxHorizontal) {
            y = (y % maxVertical) + 1;
        }
        x = (x % maxHorizontal) + 1;
    }
    public void nextStep() {
        setCurrentToProcessed();
        stepCount++;
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

    public boolean isProcessedAt(int xDelta, int yDelta) {
        var xChanged = getX() + xDelta;
        var yChanged = getY() + yDelta;
        if (xChanged == 0 || yChanged == 0) {
            return true;
        }
        if (xChanged > maxHorizontal || yChanged > maxVertical) {
            return true;
        }
        return processed[xChanged][yChanged];
    }

    public void setWallAtCurrent(int horizontalWall) {
        wallArray[x][y] = horizontalWall;
    }

    public boolean isProcessedAtCurrent() {
        return processed[getX()][getY()];
    }

    public void setCurrentToProcessed() {
        processed[getX()][getY()] = true;
    }
}