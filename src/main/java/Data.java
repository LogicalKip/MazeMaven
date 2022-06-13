public class Data {
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

    public void decrementX() {
        x--;
    }

    public void decrementY() {
        y--;
    }

    public void setY(int i) {
        y = i;
    }

    public void setX(int i) {
        x = i;
    }

    public void incrementY() {
        y++;
    }

    public void incrementX() {
        x++;
    }

    public int getCurrentWall() {
        return wallArray[getX()][getY()];
    }

    public void setWallAtCurrent(int possibleWall) {
        wallArray[getX()][getY()] = getCurrentWall() == MazeBuilder.VERT_AND_HORIZ_WALL ? possibleWall : MazeBuilder.NO_WALL;
    }
}