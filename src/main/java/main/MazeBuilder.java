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
    private final Incrementer horizontalIncrementer;
    private final Decrementer verticalDecrementer;
    private final Incrementer verticalIncrementer;
    private final Decrementer horizontalDecrementer;
    /**
     * ex x
     */
    private boolean hasRestarted;

    public MazeBuilder(Random random, int maxHorizontal, int maxVertical) {
        this.random = random;
        final int entrancePosition = random(maxHorizontal);
        this.data = new Data(maxHorizontal, maxVertical, entrancePosition);
        this.entrancePosition = entrancePosition;
        verticalDecrementer = new Decrementer(data, new Vertical(), false, this);
        verticalIncrementer = new Incrementer(data, new Vertical(), true, this);
        horizontalIncrementer = new Incrementer(data, new Horizontal(), false, this);
        horizontalDecrementer = new Decrementer(data, new Horizontal(), true, this);
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
        data.nextTile();
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
            incrementOrGoBackTheOnlyWay(verticalDecrementer);
            return;
        }
        final boolean notProcessedInXPlus1 = !data.isProcessedAt(+1, +0);
        if (notProcessedInXPlus1 && data.yMaxed()) {
            Crementer crementer = hasRestarted ?
                    horizontalIncrementer :
                    verticalDecrementer;
            crementer.doStuff();

            return;
        }

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

    private void doFirstInstructionHandleY() {
        if (!data.yMaxed()) {
            incrementOrGoBackTheOnlyWay(horizontalDecrementer);
            return;
        }
        if (data.isProcessedAt(+1, +0) && hasRestarted) {
            horizontalDecrementer.doStuff();
        } else if (random(3) == 2) {
            horizontalIncrementer.doStuff();
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
        final Decrementer decrementer = new Decrementer(data, false, this);
        if (random == 1) {
            crementer = decrementer;
        } else if (random == 2) {
            crementer = decrementer;
            orientation = new Vertical();
        } else if (xProcessed) {
            orientation = new Vertical();
        }

        crementer.setOrientation(orientation);
        crementer.doStuff();
    }

    private void incrementOrGoBackTheOnlyWay(Decrementer decrementer) {
        List<Crementer> crementers = of(decrementer);
        if (!data.isProcessedAt(+1, +0)) {
            crementers.add(horizontalIncrementer);
        }
        if (!data.isProcessedAt(+0, +1)) {
            crementers.add(verticalIncrementer);
        }
        getRandomElement(crementers).doStuff();
    }

    private <T> T getRandomElement(List<T> actions) {
        var i =
                (actions.size() == 1) ?
                        0 :
                        (random(actions.size()) - 1);
        return actions.get(i);
    }

    private List<Crementer> of(Crementer crementer) {
        return new ArrayList<>(List.of(crementer));
    }
}