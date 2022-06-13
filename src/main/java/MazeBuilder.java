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

    private final int entrancePosition;
    private final int maxHorizontal;
    private final int maxVertical;
    private final Data data;

    /**
     * ex c
     */
    private int stepCount;
    /**
     * ex q
     */
    private boolean q;

    /**
     * ex x
     */
    private boolean wentThrough1090WithQTrue;

    public MazeBuilder(Random random, int maxHorizontal, int maxVertical) {
        this.random = random;
        this.processed = new boolean[maxHorizontal + 1][maxVertical + 1];
        this.data = new Data(maxHorizontal, maxVertical);
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
        return data.getWallArray();
    }

    public int getEntrancePosition() {
        return entrancePosition;
    }

    private void initializeArrays(int maxHorizontal, int maxVertical) {
        for (int i = 0; i <= maxHorizontal; i++) {
            processed[i] = new boolean[maxVertical + 1];
        }


        processed[entrancePosition][1] = true;
    }

    public void createMaze() {
        wentThrough1090WithQTrue = false; // ex Z
        data.setX(entrancePosition);
        data.setY(1);

        firstInstruction();
    }

    private void i940() {
        data.decrementX(); // TODO what if x and y were represented by classes, that this "method" would know. You'd just call "decrement" and it would call the correct class because of how it's been set (with a "x" class here). "decrement()" would know to set a wall, but which exactly ? given by the x/y class. While "increment()" would know to update from existing value
        data.setWallAtCurrent(HORIZONTAL_WALL);

        nextStep();

        if (stepsAreNotAllFilled()) {
            q = false;
            firstInstruction();
        }
    }

    private void i1000() {
        data.decrementY();
        data.setWallAtCurrent(VERTICAL_WALL);

        nextStep();

        q = false;
        firstInstruction();
    }

    private void handleVerticalStuff() {
        data.setPossibleWallAtCurrent(VERTICAL_WALL);
        data.incrementY();

        nextStep();

        if (stepsAreNotAllFilled()) {
            firstInstruction();
        }
    }

    private void handleHorizontalStuff() { // FIXME seems very related to processed[x + 1][y] == false and is a cousin of i1090
        data.setPossibleWallAtCurrent(HORIZONTAL_WALL);
        data.incrementX();

        nextStep();

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

    private void subi1090() { // FIXME seems very related to processed[x][y + 1] == false and y < max
        wentThrough1090WithQTrue = true;
        q = false;
        data.setWallAtCurrent(VERTICAL_WALL);
        data.setX(1);
        data.setY(1);
        restartFromNextProcessedTile();
    }


    private void restartFromNextProcessedTile() {
        if (data.getX() == maxHorizontal) {
            data.setY((data.getY() % maxVertical) + 1);
        }
        data.setX((data.getX() % maxHorizontal) + 1);
        if (processed[data.getX()][data.getY()]) {
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
        if (data.getY() < this.maxVertical) {
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
        } else {
            dependsOnQ();
        }
    }

    private void doFirstInstructionHandleOther() {
        final boolean notX = !isProcessedAt(+1, +0);
        final boolean notY = !isProcessedAt(+0, +1);

        final int random = random(notX || notY ? 3 : 2);

        if (random == 1) {
            i940();
        } else if (random == 2) {
            i1000();
        } else if (notX) {
            handleHorizontalStuff();
        } else {
            dependsOnQ();
        }
    }

    private void someMethod() {
        var falseForXPlus1 = !isProcessedAt(+1, +0);
        if (falseForXPlus1 && data.getY() == maxVertical) {
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
        var xChanged = data.getX() + xDelta;
        var yChanged = data.getY() + yDelta;
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

    private void addHorizontalAndOr1090IfNeeded(List<Runnable> result) {
        if (!isProcessedAt(+1, +0)) {
            result.add(this::handleHorizontalStuff);
        }
        if (!isProcessedAt(+0, +1)) {
            result.add(deduceInstructionFromQ());
        }
    }

    private void dependsOnQ() {
        deduceInstructionFromQ().run();
    }

    private Runnable deduceInstructionFromQ() {
        return q ? this::subi1090 : this::handleVerticalStuff;
    }

    private void nextStep() {
        processed[data.getX()][data.getY()] = true;
        stepCount++;
    }
}