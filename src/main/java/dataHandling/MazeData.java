package dataHandling;

public class MazeData {
    /**
     * Shows presence of walls.
     * Both arrays starts fully filled with walls, then gets emptied little by little.
     * It's the output of the MazeBuilder.
     */
    private final boolean[][] horizontalWallArray;
    private final boolean[][] verticalWallArray;
    private final int maxHorizontal;
    private final int maxVertical;
    /**
     * Everything is at false at start except the entrance door.
     * At the end everything is true except the top and side tiles
     */
    private final boolean[][] processed;
    private int x;
    private int y;
    private int stepCount;

    public MazeData(int maxHorizontal, int maxVertical, int entrancePosition) {
        this.processed = new boolean[maxHorizontal + 1][maxVertical + 1];
        this.maxHorizontal = maxHorizontal;
        this.maxVertical = maxVertical;
        this.horizontalWallArray = new boolean[maxHorizontal + 1][maxVertical + 1];
        this.verticalWallArray = new boolean[maxHorizontal + 1][maxVertical + 1];
        this.stepCount = 1;
        this.x = entrancePosition;
        this.y = 1;
        initializeArrays(maxHorizontal, maxVertical, entrancePosition);
    }

    private void initializeArrays(int maxHorizontal, int maxVertical, int entrancePosition) {
        for (int i = 0; i <= maxHorizontal; i++) {
            this.horizontalWallArray[i] = new boolean[maxVertical + 1];
            this.verticalWallArray[i] = new boolean[maxVertical + 1];
            for (int j = 0; j <= maxVertical; j++) {
                this.horizontalWallArray[i][j] = true;
                this.verticalWallArray[i][j] = true;
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

    public boolean[][] getHorizontalWallArray() {
        return horizontalWallArray;
    }

    public boolean[][] getVerticalWallArray() {
        return verticalWallArray;
    }

    public boolean stepsAreNotAllFilled() {
        return stepCount < this.maxHorizontal * this.maxVertical;
    }

    /**
     * Moves to the right. If no more room, move to first tile of next line above.
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

    public void backToBottomLeft() {
        x = 1;
        y = 1;
    }

    public void decrement(boolean isHorizontal) {
        if (isHorizontal) {
            x--;
        } else {
            y--;
        }
    }

    public void increment(boolean isHorizontal) {
        if (isHorizontal) {
            x++;
        } else {
            y++;
        }
    }

    public void allowPassage(boolean isHorizontal) {
        final boolean bothWalls = horizontalWallArray[x][y] && verticalWallArray[x][y];
        horizontalWallArray[x][y] = bothWalls && isHorizontal;
        verticalWallArray[x][y] = bothWalls && !isHorizontal;
    }

    public void setHorizontalWallAtCurrent(boolean isHorizontal) {
        horizontalWallArray[x][y] = isHorizontal;
        verticalWallArray[x][y] = !isHorizontal;
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

}
