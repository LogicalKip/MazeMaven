package dataHandling;

public class MazeData {
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
     * It's the output of the MazeBuilder.
     */
    public final int[][] horizontalWallArray;
    public final int[][] verticalWallArray;
    private final int maxHorizontal;
    private final int maxVertical;
    /**
     * Everything is at false at start except the entrance door.
     * At the end everything is true except the top and side tiles
     */
    private final boolean[][] processed;
    public int x;
    public int y;
    private int stepCount;

    public MazeData(int maxHorizontal, int maxVertical, int entrancePosition) {
        this.processed = new boolean[maxHorizontal + 1][maxVertical + 1];
        this.maxHorizontal = maxHorizontal;
        this.maxVertical = maxVertical;
        this.horizontalWallArray = new int[maxHorizontal + 1][maxVertical + 1];
        this.verticalWallArray = new int[maxHorizontal + 1][maxVertical + 1];
        this.stepCount = 2;
        for (int i = 0; i <= maxHorizontal; i++) {
            this.horizontalWallArray[i] = new int[maxVertical + 1];
            for (int j = 0; j <= maxVertical; j++) {
                this.horizontalWallArray[i][j] = 2;
            }
        }
        for (int i = 0; i <= maxHorizontal; i++) {
            this.verticalWallArray[i] = new int[maxVertical + 1];
            for (int j = 0; j <= maxVertical; j++) {
                this.verticalWallArray[i][j] = 1;
            }
        }
        for (int i = 0; i <= maxHorizontal; i++) {
            processed[i] = new boolean[maxVertical + 1];
        }

        processed[entrancePosition][1] = true;
    }

    public boolean yMaxed() {
        return y == maxVertical;
    }

    public int[][] getHorizontalWallArray() {
        return horizontalWallArray;
    }

    public int[][] getVerticalWallArray() {
        return verticalWallArray;
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
        if (horizontalWallArray[x][y] == HORIZONTAL_WALL && verticalWallArray[x][y] == VERTICAL_WALL) {
            if (orientation.getTypeOfWall() == VERTICAL_WALL) {
                verticalWallArray[x][y] = VERTICAL_WALL;
                horizontalWallArray[x][y] = NO_WALL;
            } else {
                verticalWallArray[x][y] = NO_WALL;
                horizontalWallArray[x][y] = HORIZONTAL_WALL;
            }
        } else {
            verticalWallArray[x][y] = NO_WALL;
            horizontalWallArray[x][y] = NO_WALL;
        }
    }

    public void setWallAtCurrent(int wallType) {
        if (wallType == HORIZONTAL_WALL) {
            horizontalWallArray[x][y] = wallType;
            verticalWallArray[x][y] = NO_WALL;
        } else if (wallType == VERTICAL_WALL) {
            horizontalWallArray[x][y] = NO_WALL;
            verticalWallArray[x][y] = wallType;
        } else {
            System.out.println("unexpected");
        }
    }

    public boolean isAvailable(int xDelta, int yDelta) {
        var xChanged = x + xDelta;
        var yChanged = y + yDelta;
        if (xChanged == 0 || yChanged == 0) {
            return false;
        }
        if (xChanged > maxHorizontal || yChanged > maxVertical) {
            return false;
        }
        return !processed[xChanged][yChanged];
    }

    public boolean isProcessedAtCurrent() {
        return processed[x][y];
    }

    enum XorY {
        X,
        Y
    }
}