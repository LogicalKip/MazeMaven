import dataHandling.Crementer;
import dataHandling.Data;
import dataHandling.Decrementer;
import dataHandling.Horizontal;
import dataHandling.Incrementer;
import dataHandling.Vertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static dataHandling.Data.VERTICAL_WALL;

public class MazeBuilder {

    private final Random random;

    private final int entrancePosition;
    private final Data data;
    private final Incrementer verticalIncrementer;
    private final Incrementer horizontalIncrementer;
    private final Decrementer horizontalDecrementer;
    private final Decrementer verticalDecrementer;

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
        final int entrancePosition = random(maxHorizontal);
        this.data = new Data(maxHorizontal, maxVertical, entrancePosition);
        this.entrancePosition = entrancePosition;
        this.q = false;
        verticalDecrementer = new Decrementer(data, new Vertical());
        verticalIncrementer = new Incrementer(data, new Vertical());
        horizontalIncrementer = new Incrementer(data, new Horizontal());
        horizontalDecrementer = new Decrementer(data, new Horizontal());
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

    // FIXME maybe we can use the random and ifs to determine the correct parts of objects (behaviors), then combine them and call the resulting object
    public void createMaze() {
        wentThrough1090WithQTrue = false; // ex Z
        data.setX(entrancePosition);
        data.setY(1);

        firstInstruction();
    }

    private void i940() {
        horizontalDecrementer.doStuff();

        if (data.stepsAreNotAllFilled()) {
            q = false;
            firstInstruction();
        }
    }

    private void i1000() {
        verticalDecrementer.doStuff();

        q = false;
        firstInstruction();
    }

    private void handleVerticalStuff() {
        verticalIncrementer.doStuff();

        if (data.stepsAreNotAllFilled()) {
            firstInstruction();
        }
    }

    private void handleHorizontalStuff() { // FIXME seems very related to processed[x + 1][y] == false and is a cousin of i1090
        horizontalIncrementer.doStuff();

        doFirstInstructionHandleX();
    }


    private void doFirstInstructionHandleX() {
        if (data.isProcessedAt(+0, -1)) { // FIXME there might be a link between isProcessed and the "direction" we then go to. Which is shown by the method chosen
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
        data.changeXY();
        if (data.isProcessedAtCurrent()) {
            firstInstruction();
        } else {
            restartFromNextProcessedTile();
        }
    }

    private void firstInstruction() {
        if (data.isProcessedAt(-1, +0)) {
            doFirstInstructionHandleX();
        } else if (data.isProcessedAt(+0, -1)) {
            doFirstInstructionHandleY();
        } else {
            doFirstInstructionHandleOther();
        }
    }

    private void doFirstInstructionHandleY() {
        if (!data.yMaxed()) {
            List<Runnable> instructionList = of(this::i940);
            addHorizontalAndOr1090IfNeeded(instructionList);
            chooseRandomlyOneOf(instructionList);
            return;
        }

        q = true;
        if (data.isProcessedAt(+1, +0) && wentThrough1090WithQTrue) {
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
        final boolean notX = !data.isProcessedAt(+1, +0);
        final boolean notY = !data.isProcessedAt(+0, +1);

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
        if (!data.isProcessedAt(+1, +0) && data.yMaxed()) {
            Crementer crementer = wentThrough1090WithQTrue ? horizontalIncrementer : verticalDecrementer;
            crementer.doStuff();
            firstInstruction();

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

    private List<Runnable> of(Runnable instruction) {
        return new ArrayList<>(List.of(instruction));
    }

    private void addHorizontalAndOr1090IfNeeded(List<Runnable> result) {
        if (!data.isProcessedAt(+1, +0)) {
            result.add(this::handleHorizontalStuff);
        }
        if (!data.isProcessedAt(+0, +1)) {
            result.add(this::handleVerticalStuff);
        }
    }

    private void dependsOnQ() {
        final Runnable runnable = (q ? this::subi1090 : this::handleVerticalStuff);
        runnable.run();
    }


}