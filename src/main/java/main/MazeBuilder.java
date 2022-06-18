package main;

import dataHandling.Crementer;
import dataHandling.Data;
import dataHandling.Decrementer;
import dataHandling.Horizontal;
import dataHandling.Incrementer;
import dataHandling.Orientation;
import dataHandling.Vertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static dataHandling.Data.VERTICAL_WALL;

public class MazeBuilder {

    private final Random random;

    private final int entrancePosition;
    private final Data data;

    /**
     * ex x
     */
    private boolean hasRestarted;

    public MazeBuilder(Random random, int maxHorizontal, int maxVertical) {
        this.random = random;
        final int entrancePosition = random(maxHorizontal);
        this.data = new Data(maxHorizontal, maxVertical, entrancePosition);
        this.entrancePosition = entrancePosition;
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
        hasRestarted = false;
        data.setX(entrancePosition);
        data.setY(1);

        firstInstruction();
    }

    private void i940() {
        new Decrementer(data, new Horizontal(), true, this).doStuff();
    }

    private void i1000() {
        new Decrementer(data, new Vertical(), false, this).doStuff();

    }

    private void handleVerticalStuff() {
        new Incrementer(data, new Vertical(), true, this).doStuff();
    }

    private void handleHorizontalStuff() { // FIXME seems very related to processed[x + 1][y] == false
        new Incrementer(data, new Horizontal(), false, this).doStuff();
    }

    private void restartFromNextProcessedTile() {
        data.changeXY();
        if (data.isProcessedAtCurrent()) {
            firstInstruction();
        } else {
            restartFromNextProcessedTile();
        }
    }

    public void firstInstruction() {
        if (data.isProcessedAt(-1, +0)) {
            doFirstInstructionHandleX();
        } else if (data.isProcessedAt(+0, -1)) {
            doFirstInstructionHandleY();
        } else {
            doFirstInstructionHandleOther();
        }
    }

    private void doFirstInstructionHandleX() { // FIXME there might be a link between isProcessed and the "direction" we then go to. Which is shown by the method chosen
        if (!data.isProcessedAt(+0, -1)) {
            List<Runnable> result = of(this::i1000);
            addHorizontalAndOrVerticalIfNeeded(result);
            chooseRandomlyOneOf(result);
            return;
        }
        someMethod();
    }

    private void doFirstInstructionHandleY() {
        if (!data.yMaxed()) {
            List<Runnable> instructionList = of(this::i940);
            addHorizontalAndOrVerticalIfNeeded(instructionList);
            chooseRandomlyOneOf(instructionList);
            return;
        }

        if (data.isProcessedAt(+1, +0) && hasRestarted) {
            i940();
            return;
        }

        int random = random(3);
        if (random == 2) {
            handleHorizontalStuff();
        } else {
            hasRestarted = true;
            data.setWallAtCurrent(VERTICAL_WALL);
            data.setX(1);
            data.setY(1);
            restartFromNextProcessedTile();
        }
    }

    private void doFirstInstructionHandleOther() {
        final boolean xProcessed = data.isProcessedAt(+1, +0);
        final boolean yProcessed = data.isProcessedAt(+0, +1);

        final int random = random(xProcessed && yProcessed ? 2 : 3);


        Orientation orientation = new Horizontal();
        Crementer crementer = new Incrementer(data, false, this);
        if (random == 1) {
            crementer = new Decrementer(data, false, this);
        } else if (random == 2) {
            crementer = new Decrementer(data, false, this);
            orientation = new Vertical();
        } else if (xProcessed) {
            orientation = new Vertical();
        }

        crementer.setOrientation(orientation);
        crementer.doStuff();
    }

    private void someMethod() {
        if (!data.isProcessedAt(+1, +0) && data.yMaxed()) {
            Crementer crementer = hasRestarted ?
                    new Incrementer(data, new Horizontal(), false, this) :
                    new Decrementer(data, new Vertical(), false, this);
            crementer.doStuff();

            return;
        }

        List<Runnable> instructionList = new ArrayList<>();

        addHorizontalAndOrVerticalIfNeeded(instructionList);
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

    private void addHorizontalAndOrVerticalIfNeeded(List<Runnable> result) {
        if (!data.isProcessedAt(+1, +0)) {
            result.add(this::handleHorizontalStuff);
        }
        if (!data.isProcessedAt(+0, +1)) {
            result.add(this::handleVerticalStuff);
        }
    }

}