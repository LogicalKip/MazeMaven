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
        final boolean alreadyDoneNextX = data.isAlreadyProcessedAt(+1, +0);
        final boolean alreadyDonePreviousY = data.isAlreadyProcessedAt(+0, -1);
        final boolean alreadyDonePreviousX = data.isAlreadyProcessedAt(-1, +0);
        if (alreadyDonePreviousX) {
            if (alreadyDonePreviousY) {
                incrementWherePossible(alreadyDoneNextX);
            } else {
                chooseOneOfUpTo3Ways(verticalDecrementer);
            }
        } else if (alreadyDonePreviousY) {
            if (!data.yMaxed()) {
                chooseOneOfUpTo3Ways(horizontalDecrementer);
                return;
            }
            doFirstInstructionHandleY(alreadyDoneNextX);
        } else {
            doFirstInstructionHandleOther();
        }
    }

    private void incrementWherePossible(boolean alreadyDoneNextX) {
        final boolean nextXAvailable = !alreadyDoneNextX;
        if (nextXAvailable && data.yMaxed()) {
            Crementer crementer = hasRestarted ?
                    horizontalIncrementer :
                    verticalDecrementer;
            crementer.doStuff();

            return;
        }

        final boolean nextYAvailable = !data.isAlreadyProcessedAt(+0, +1);
        if (nextXAvailable && nextYAvailable) {
            getRandomElement(new ArrayList<>(List.of(horizontalIncrementer, verticalIncrementer))).doStuff();
        } else if (nextXAvailable) {
            horizontalIncrementer.doStuff();
        } else if (nextYAvailable) {
            verticalIncrementer.doStuff();
        } else {
            restartFromNextProcessedTile();
        }
    }

    private void doFirstInstructionHandleY(boolean alreadyDoneNextX) {
        if (alreadyDoneNextX && hasRestarted) {
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
        final boolean xProcessed = data.isAlreadyProcessedAt(+1, +0);
        final boolean yProcessed = data.isAlreadyProcessedAt(+0, +1);

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

    private void chooseOneOfUpTo3Ways(Decrementer guaranteedPossibility) {
        List<Crementer> crementers = of(guaranteedPossibility);
        if (!data.isAlreadyProcessedAt(+1, +0)) {
            crementers.add(horizontalIncrementer);
        }
        if (!data.isAlreadyProcessedAt(+0, +1)) {
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