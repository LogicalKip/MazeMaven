package main;

public class MazePrinter {
    public static final String HORIZONTAL_WALL = ":--";
    public static final String HORIZONTAL_SPACE = ":  ";
    public static final String VERTICAL_WALL = "  I";
    public static final String VERTICAL_SPACE = "   ";

    public static final StringBuilder result = new StringBuilder();

    public void printMaze(int maxHorizontal, int maxVertical, boolean[][] horizontalWallArray, boolean[][] verticalWallArray, int entrancePosition) {
        clear();
        printHeader();
        printFirstWall(maxHorizontal, entrancePosition);
        for (int y = 1; y <= maxVertical; y++) {
            printVerticalPartOfCurrentLine(maxHorizontal, verticalWallArray, y);
            printHorizontalPartOfCurrentLine(maxHorizontal, horizontalWallArray, y);
        }
    }

    private void printHeader() {
        print("Amazing - Copyright by Creative Computing, Morristown, NJ");
        println();
    }

    void printFirstWall(int maxHorizontal, int entrancePosition) {
        for (int i = 1; i <= maxHorizontal; i++) {
            if (i == entrancePosition) {
                print(HORIZONTAL_SPACE);
            } else {
                print(HORIZONTAL_WALL);
            }
        }
        print(":"); // top right corner
        println();
    }

    private void printHorizontalPartOfCurrentLine(int maxHorizontal, boolean[][] horizontalWallArray, int y) {
        for (int x = 1; x <= maxHorizontal; x++) {
            print(horizontalWallArray[x][y] ? HORIZONTAL_WALL : HORIZONTAL_SPACE);
        }

        print(":");
        println();
    }

    private void printVerticalPartOfCurrentLine(int maxHorizontal, boolean[][] verticalWallArray, int y) {
        print("I");
        for (int x = 1; x <= maxHorizontal; x++) {
            print(verticalWallArray[x][y] ? VERTICAL_WALL : VERTICAL_SPACE);
        }

        print(" ");
        println();
    }

    private void println() {
        result.append("\n");
    }

    private void print(String text) {
        result.append(text);
    }

    private void clear() {
        result.setLength(0);
    }
}