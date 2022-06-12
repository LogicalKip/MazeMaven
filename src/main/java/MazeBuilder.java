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
        this.entrancePosition = random(maxHorizontal);
        this.maxHorizontal = maxHorizontal;
        this.maxVertical = maxVertical;
        this.stepCount = 2;
        this.q = false;
        initializeArrays(this.maxHorizontal, this.maxVertical);
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

        doFirstInstructionHandleX();
    }

    private void doFirstInstructionHandleX() {
        if (isProcessedAt(+0, -1)) {
            someMethod();
        } else {
            List<Runnable> result = of(this::i1000);
            addHorizontalAndOr1090IfNeeded(result);
            chooseRandomlyOneOf(result);
        }
    }

    private void addHorizontalAndOr1090IfNeeded(List<Runnable> result) {
        if (!isProcessedAt(+1, +0)) {
            result.add(this::handleHorizontalStuff);
        }
        if (!isProcessedAt(+0, +1)) {
            result.add(q ? this::subi1090 : this::handleVerticalStuff);
        }
    }

    private void subi1090() { // FIXME seems very related to processed[x][y + 1] == false and y < max
        wentThrough1090WithQTrue = true;
        q = false;
        wallArray[x][y] = VERTICAL_WALL;
        x = 1;
        y = 1;
        restartFromNextProcessedTile();
    }

    private void restartFromNextProcessedTile() {
        if (x == maxHorizontal) {
            y = (y % maxVertical) + 1;
        }
        x = (x % maxHorizontal) + 1;
        if (processed[x][y]) {
            firstInstruction();
        } else {
            restartFromNextProcessedTile();
        }
    }

    private void firstInstruction() {
        if (isProcessedAt(-1, +0)) {
            doFirstInstructionHandleX();
        } else if (isProcessedAt(+0, -1)) {
            doFirstInstructionHandleY();
        } else {
            doFirstInstructionHandleOther();
        }
    }

    private void doFirstInstructionHandleY() {
        if (y < this.maxVertical) {
            List<Runnable> instructionList = of(this::i940);
            addHorizontalAndOr1090IfNeeded(instructionList);
            chooseRandomlyOneOf(instructionList);
            return;
        }

        q = true;
        if (isProcessedAt(+1, +0) && wentThrough1090WithQTrue) {
            i940();
            return;
        }

        int random = random(3);
        if (random == 1) {
            i940();
        } else if (random == 2) {
            handleHorizontalStuff();
        } else if (q) {
            subi1090();
        } else {
            handleVerticalStuff();
        }
    }

    private void doFirstInstructionHandleOther() {
        final boolean notX = !isProcessedAt(+1, +0);
        final boolean notY = !isProcessedAt(+0, +1);

        final int random = random(notX || notY ? 3 : 2);

        if (random == 1) {
            i940();
        } else if (random == 2){
            i1000();
        } else if (notX) {
            handleHorizontalStuff();
        } else if (q) {
            subi1090();
        } else {
            handleVerticalStuff();
        }
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

        addHorizontalAndOr1090IfNeeded(instructionList);
        if (instructionList.isEmpty()) {
            instructionList.add(this::restartFromNextProcessedTile);
        }
        chooseRandomlyOneOf(instructionList);
    }

    private void chooseRandomlyOneOf(List<Runnable> actions) {
        if (actions.isEmpty()) {
            return;
        }
        var i =
                (actions.size() == 1) ?
                        0 :
                        (random(actions.size()) - 1);
        actions.get(i).run();
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

    private List<Runnable> of(Runnable instruction) {
        return new ArrayList<>(List.of(instruction));
    }

    private List<Runnable> of(Runnable instruction, Runnable instruction2) {
        return new ArrayList<>(List.of(instruction, instruction2));
    }
}