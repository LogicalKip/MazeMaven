import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeBuilder {

    public static final int VERT_AND_HORIZ_WALL = 0;
    public static final int VERTICAL_WALL = 1;
    public static final int HORIZONTAL_WALL = 2;
    public static final int NO_WALL = 3;
    private final Random random;
    private final boolean[][] escapeRouteArray;
    /**
     * shows presence of both vertical and horizontal with the same data
     * 0, 1 : vertical wall.
     * 0, 2 : horizontal wall.
     * Therefore :
     * 0 : both walls
     * 1 : vertical wall
     * 2 : horizontal wall
     * 3 : no wall at all.
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
        this.escapeRouteArray = new boolean[maxHorizontal + 1][maxVertical + 1];
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
            escapeRouteArray[i] = new boolean[maxVertical + 1];
        }

        for (int i = 0; i <= maxHorizontal; i++) {
            wallArray[i] = new int[maxVertical + 1];
        }

        escapeRouteArray[entrancePosition][1] = true;
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

        escapeRouteArray[x][y] = true;
        stepCount++;
        
        if (stepsAreNotAllFilled()) {
            q = false;
            firstInstruction();
        }
    }
    private void handleVerticalStuff() {
        wallArray[x][y] = wallArray[x][y] == VERT_AND_HORIZ_WALL ? VERTICAL_WALL : NO_WALL;
        y++;

        escapeRouteArray[x][y] = true;
        stepCount++;

        if (stepsAreNotAllFilled()) {
            firstInstruction();
        }
    }
    private void handleHorizontalStuff() {
        wallArray[x][y] = wallArray[x][y] == VERT_AND_HORIZ_WALL ? HORIZONTAL_WALL : NO_WALL;
        x++;

        escapeRouteArray[x][y] = true;
        stepCount++;

        chooseRandomlyOneOf(getFirstInstructionHandleX());
    }

    private void i1000() {
        y--;
        wallArray[x][y] = VERTICAL_WALL;

        escapeRouteArray[x][y] = true;
        stepCount++;

        q = false;
        firstInstruction();
    }

    private void restartFromNextTrueTile() {
        if (x == maxHorizontal) {
            y = (y % maxVertical) + 1;
        }
        x = (x % maxHorizontal) + 1;
        if (!escapeRouteArray[x][y]) {
            restartFromNextTrueTile();
        } else {
            firstInstruction();
        }
    }

    private void someMethod() {
        var falseForXPlus1 = !isOkFor(+1, +0);
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

        if (falseForXPlus1) {
            instructionList.add(this::handleHorizontalStuff);
        }
        if (!isOkFor(+0, +1)) {
            instructionList.add(this::i1090);
        }
        if (instructionList.isEmpty()) {
            instructionList.add(this::restartFromNextTrueTile);
        }
        chooseRandomlyOneOf(instructionList);
    }

    private void firstInstruction() {
        if (isOkFor(-1, +0)) {
            chooseRandomlyOneOf(getFirstInstructionHandleX());
        } else if (isOkFor(+0, -1)) {
            chooseRandomlyOneOf(getFirstInstructionHandleY());
        } else {
            chooseRandomlyOneOf(getFirstInstructionHandleOther());
        }
    }

    private List<Runnable> getFirstInstructionHandleOther() {
        List<Runnable> instructionList = new ArrayList<>(List.of(this::i940, this::i1000));
        if (isOkFor(+1, +0)) {
            if (!isOkFor(+0, +1)) {
                instructionList.add(this::i1090);
            }
            return instructionList;
        }

        instructionList.add(this::handleHorizontalStuff);
        return instructionList;
    }

    private List<Runnable> getFirstInstructionHandleX() {
        if (isOkFor(+0, -1)) {
            return new ArrayList<>(List.of(this::someMethod));
        }

        List<Runnable> instructionList = new ArrayList<>(List.of(this::i1000));
        if (!isOkFor(+1, +0)) {
            instructionList.add(this::handleHorizontalStuff);
        }
        if (!isOkFor(+0, +1)) {
            instructionList.add(this::i1090);
        }
        return instructionList;
    }

    private List<Runnable> getFirstInstructionHandleY() {
        var isNotOkForX = !isOkFor(+1, +0);
        List<Runnable> instructionList = new ArrayList<>(List.of(this::i940));
        if (y == this.maxVertical) {
            q = true;
            if (wentThrough1090WithQTrue && !isNotOkForX) {
                return instructionList;
            }
            instructionList.add(this::handleHorizontalStuff);
            instructionList.add(this::i1090);
            return instructionList;
        }
        var isNotOkForY = !isOkFor(+0, +1);

        if (isNotOkForX) {
            instructionList.add(this::handleHorizontalStuff);
        }

        if (isNotOkForY) {
            instructionList.add(this::i1090);
        }
        return instructionList;
    }

    private void i1090() { // FIXME semble beaucoup lié à escapeRouteArray[x][y + 1] == false et y < max
        if (!q) {
            handleVerticalStuff();
            return;
        }

        wentThrough1090WithQTrue = true;
        q = false;
        wallArray[x][y] = VERTICAL_WALL;
        x = 1;
        y = 1;
        if (!escapeRouteArray[x][y]) {
            restartFromNextTrueTile();
        } else {
            firstInstruction();
        }
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

    private boolean isOkFor(int xDelta, int yDelta) {
        if (this.x == maxHorizontal && xDelta == +1) {
            return true;
        }
        if (this.y == maxVertical && yDelta == +1) {
            return true;
        }
        if (this.x == 1 && xDelta == -1) {
            return true;
        }
        if (this.y == 1 && yDelta == -1) {
            return true;
        }
        return escapeRouteArray[x + xDelta][y + yDelta];
    }
}