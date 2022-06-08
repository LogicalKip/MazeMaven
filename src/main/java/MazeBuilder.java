import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeBuilder {

    public static final int VERT_AND_HORIZ_WALL = 0;
    public static final int VERTICAL_WALL = 1;
    public static final int HORIZONTAL_WALL = 2;
    public static final int NO_WALL = 3;
    private final Random random;
    /**
     * Everything is at false at start except the entrance door.
     * At the end everything is true except the top and side tiles
     * Maybe means "has been processed" ?
     */
    private final boolean[][] processed;
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
    private final int[][] wallArray;

    private final int entrancePosition;
    private final int maxHorizontal;
    private final int maxVertical;

    /**
     * ex c
     */
    private int stepCount;
    /**
     * ex q
     */
    private boolean q;
    /**
     * ex r
     */
    private int x;

    /**
     * ex s
     */
    private int y;

    /**
     * ex x
     */
    private boolean wentThrough1090WithQTrue;

    public MazeBuilder(Random random, int maxHorizontal, int maxVertical) {
        this.random = random;
        this.processed = new boolean[maxHorizontal + 1][maxVertical + 1];
        this.wallArray = new int[maxHorizontal + 1][maxVertical + 1];
        entrancePosition = random(maxHorizontal);
        this.maxHorizontal = maxHorizontal;
        this.maxVertical = maxVertical;
        initializeArrays(this.maxHorizontal, this.maxVertical);
        stepCount = 2;
        q = false;
    }

    private int random(int count) {
        return (int) (count * random.nextFloat()) + 1;
    }

    public int[][] getWallArray() {
        return wallArray;
    }

    public int getEntrancePosition() {
        return entrancePosition;
    }

    private void initializeArrays(int maxHorizontal, int maxVertical) {
        for (int i = 0; i <= maxHorizontal; i++) {
            processed[i] = new boolean[maxVertical + 1];
        }

        for (int i = 0; i <= maxHorizontal; i++) {
            wallArray[i] = new int[maxVertical + 1];
        }

        processed[entrancePosition][1] = true;
    }

    public void createMaze() {
        wentThrough1090WithQTrue = false; // ex Z
        x = entrancePosition;
        y = 1;

        firstInstruction();
    }

    private void i940() {
        x--;
        wallArray[x][y] = HORIZONTAL_WALL;

        processed[x][y] = true;
        stepCount++;

        if (stepsAreNotAllFilled()) {
            q = false;
            firstInstruction();
        }
    }

    private void i1000() {
        y--;
        wallArray[x][y] = VERTICAL_WALL;

        processed[x][y] = true;
        stepCount++;

        q = false;
        firstInstruction();
    }

    private void handleVerticalStuff() {
        wallArray[x][y] = wallArray[x][y] == VERT_AND_HORIZ_WALL ? VERTICAL_WALL : NO_WALL;
        y++;

        processed[x][y] = true;
        stepCount++;

        if (stepsAreNotAllFilled()) {
            firstInstruction();
        }
    }

    private void handleHorizontalStuff() { // FIXME seems very related to processed[x + 1][y] == false and is a cousin of i1090
        wallArray[x][y] = wallArray[x][y] == VERT_AND_HORIZ_WALL ? HORIZONTAL_WALL : NO_WALL;
        x++;

        processed[x][y] = true;
        stepCount++;

        chooseRandomlyOneOf(getFirstInstructionHandleX());
    }

    private void i1090() { // FIXME seems very related to processed[x][y + 1] == false and y < max
        if (!q) {
            handleVerticalStuff();
            return;
        }

        wentThrough1090WithQTrue = true;
        q = false;
        wallArray[x][y] = VERTICAL_WALL;
        x = 1;
        y = 1;
        if (!processed[x][y]) {
            restartFromNextTrueTile();
        } else {
            firstInstruction();
        }
    }

    private void restartFromNextTrueTile() {
        if (x == maxHorizontal) {
            y = (y % maxVertical) + 1;
        }
        x = (x % maxHorizontal) + 1;
        if (!processed[x][y]) {
            restartFromNextTrueTile();
        } else {
            firstInstruction();
        }
    }

    private void firstInstruction() {
        if (isProcessedAt(-1, +0)) {
            chooseRandomlyOneOf(getFirstInstructionHandleX());
        } else if (isProcessedAt(+0, -1)) {
            chooseRandomlyOneOf(getFirstInstructionHandleY());
        } else {
            chooseRandomlyOneOf(getFirstInstructionHandleOther());
        }
    }

    private List<Runnable> getFirstInstructionHandleX() {
        if (isProcessedAt(+0, -1)) {
            return of(this::someMethod);
        }

        List<Runnable> instructionList = of(this::i1000);
        addHandleHorizontalStuffIfNeeded(instructionList);
        addi1090IfNeeded(instructionList);
        return instructionList;
    }

    private List<Runnable> getFirstInstructionHandleY() {
        List<Runnable> instructionList = of(this::i940);
        if (y == this.maxVertical) {
            q = true;
            if (isProcessedAt(+1, +0) && wentThrough1090WithQTrue) {
                return instructionList;
            }
            instructionList.add(this::handleHorizontalStuff);
            instructionList.add(this::i1090);
            return instructionList;
        }

        addHandleHorizontalStuffIfNeeded(instructionList);

        addi1090IfNeeded(instructionList);
        return instructionList;
    }

    private List<Runnable> getFirstInstructionHandleOther() {
        List<Runnable> instructionList = new ArrayList<>(List.of(this::i940, this::i1000));
        if (isProcessedAt(+1, +0)) {
            addi1090IfNeeded(instructionList);
            return instructionList;
        }

        instructionList.add(this::handleHorizontalStuff);
        return instructionList;
    }

    private void someMethod() {
        var falseForXPlus1 = !isProcessedAt(+1, +0);
        if (falseForXPlus1 && y == maxVertical) {
            if (wentThrough1090WithQTrue) {
                handleHorizontalStuff();
            } else {
                q = true;
                i1000();
            }
            return;
        }

        List<Runnable> instructionList = new ArrayList<>();


        addHandleHorizontalStuffIfNeeded(instructionList);
        addi1090IfNeeded(instructionList);
        if (instructionList.isEmpty()) {
            instructionList.add(this::restartFromNextTrueTile);
        }
        chooseRandomlyOneOf(instructionList);
    }

    private void chooseRandomlyOneOf(List<Runnable> actions) {
        if (actions.size() == 1) {
            actions.get(0).run();
            return;
        }
        actions.get(random(actions.size()) - 1).run();
    }

    private boolean stepsAreNotAllFilled() {
        return stepCount < this.maxHorizontal * this.maxVertical + 1;
    }

    private boolean isProcessedAt(int xDelta, int yDelta) {
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

    private void addi1090IfNeeded(List<Runnable> instructionList) {
        if (!isProcessedAt(+0, +1)) {
            instructionList.add(this::i1090);
        }
    }

    private void addHandleHorizontalStuffIfNeeded(List<Runnable> instructionList) {
        if (!isProcessedAt(+1, +0)) {
            instructionList.add(this::handleHorizontalStuff);
        }
    }

    private ArrayList<Runnable> of(Runnable instruction) {
        return new ArrayList<>(List.of(instruction));
    }
}