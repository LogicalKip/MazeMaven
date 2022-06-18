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
            List<Crementer> crementers = of(new Decrementer(data, new Vertical(), false, this));
            addCrementersAsNeeded(crementers);
            getRandomElement(crementers).doStuff();
            return;
        }
        someMethod();
    }

    private void doFirstInstructionHandleY() {
        if (!data.yMaxed()) {
            List<Crementer> crementers = of(new Decrementer(data, new Horizontal(), true, this));
            addCrementersAsNeeded(crementers);
            getRandomElement(crementers).doStuff();
            return;
        }

        if (data.isProcessedAt(+1, +0) && hasRestarted) {
            new Decrementer(data, new Horizontal(), true, this).doStuff();
        } else if (random(3) == 2) {
            new Incrementer(data, new Horizontal(), false, this).doStuff();
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
        final Incrementer horizontalIncrementer = new Incrementer(data, new Horizontal(), false, this);
        final boolean notProcessedInXPlus1 = !data.isProcessedAt(+1, +0);
        if (notProcessedInXPlus1 && data.yMaxed()) {
            Crementer crementer = hasRestarted ?
                    horizontalIncrementer :
                    new Decrementer(data, new Vertical(), false, this);
            crementer.doStuff();

            return;
        }

        final Incrementer verticalIncrementer = new Incrementer(data, new Vertical(), true, this);
        final boolean notProcessedInYPlus1 = !data.isProcessedAt(+0, +1);
        if (notProcessedInXPlus1 && notProcessedInYPlus1) {
            getRandomElement(new ArrayList<>(List.of(horizontalIncrementer, verticalIncrementer))).doStuff();
        } else if (notProcessedInXPlus1) {
            horizontalIncrementer.doStuff();
        } else if (notProcessedInYPlus1) {
            verticalIncrementer.doStuff();
        } else {
            restartFromNextProcessedTile();
        }
    }

    private <T> T getRandomElement(List<T> actions) {
        if (actions.isEmpty()) {
            return null;
        }
        var i =
                (actions.size() == 1) ?
                        0 :
                        (random(actions.size()) - 1);
        return actions.get(i);
    }

    private List<Crementer> of(Crementer crementer) {
        return new ArrayList<>(List.of(crementer));
    }

    private void addCrementersAsNeeded(List<Crementer> result) {
        if (!data.isProcessedAt(+1, +0)) {
            result.add(new Incrementer(data, new Horizontal(), false, this));
        }
        if (!data.isProcessedAt(+0, +1)) {
            result.add(new Incrementer(data, new Vertical(), true, this));
        }
    }
}