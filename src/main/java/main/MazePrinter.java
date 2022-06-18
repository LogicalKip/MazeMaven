package main;

public class MazePrinter {
    public static final String HORIZONTAL_WALL = ":--";
    public static final String HORIZONTAL_SPACE = ":  ";
    public static final String VERTICAL_WALL = "  I";
    public static final String VERTICAL_SPACE = "   ";

    public static final StringBuilder result = new StringBuilder();

    public void printMaze(int maxHorizontal, int maxVertical, int[][] valueArray, int entrancePosition) {
        clear();
        printHeader();
        printFirstWall(maxHorizontal, entrancePosition);
        // 1200:
        for (int y = 1; y <= maxVertical; y++) {
            printVerticalPartOfCurrentLine(maxHorizontal, valueArray, y);
            printHorizontalPartOfCurrentLine(maxHorizontal, valueArray, y);
        }
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

    private void printHeader() {
        print("Amazing - Copyright by Creative Computing, Morristown, NJ");
        println();
    }

    private void printHorizontalPartOfCurrentLine(int maxHorizontal, int[][] wallArray, int y) {
        for (int x = 1; x <= maxHorizontal; x++) {
            var current = wallArray[x][y];
            // 1310, 1340
            if (current == 0 || current == 2) {
                print(HORIZONTAL_WALL);   // 1300, 1340
            } else {
                print(HORIZONTAL_SPACE); // 1320
            }
        }

        print(":");    // 1360
        println();
    }

    private void printVerticalPartOfCurrentLine(int maxHorizontal, int[][] wallArray, int y) {
        print("I");        // 1210
        for (int x = 1; x <= maxHorizontal; x++) {
            if (wallArray[x][y] >= 2) {
                print(VERTICAL_SPACE);  // 1240
            } else {
                print(VERTICAL_WALL);  // 1260
            }
        }

        print(" ");   // 1280
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