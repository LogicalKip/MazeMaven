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

    /**
     * or is it exit ?
     */
    private final int entrancePosition;
    private final Data data;
    private final Incrementer horizontalIncrementer;
    private final Decrementer verticalDecrementer;
    private final Incrementer verticalIncrementer;
    private final Decrementer horizontalDecrementer;
    /**
     * ex x
     * Maybe means "exit was found", which allows more liberty in choices afterwards
     */
    private boolean foundExit;

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
        foundExit = false;
        data.setX(entrancePosition);
        data.setY(1);

        firstInstruction();
    }

    private void findNextProcessedTileInOrder() {
        do {
            data.nextTile();
        } while (!data.isProcessedAtCurrent());

        firstInstruction();
    }

    public void firstInstruction() {
        final boolean alreadyDoneNextX = data.isAlreadyProcessedAt(+1, +0);
        final boolean alreadyDonePreviousY = data.isAlreadyProcessedAt(+0, -1);
        final boolean alreadyDonePreviousX = data.isAlreadyProcessedAt(-1, +0);
        final boolean previousXAvailable = !alreadyDonePreviousX;
        final boolean previousYAvailable = !alreadyDonePreviousY;

        if (data.yMaxed() && handleLastRow(alreadyDoneNextX, previousXAvailable, previousYAvailable)) {
            return;
        }

        if (previousXAvailable && previousYAvailable) {
            goProbablyBackwards();
            return;
        }

        if (!previousXAvailable && !previousYAvailable) {
            goForwardsIfPossibleOtherwiseNextTile(alreadyDoneNextX);
            return;
        }

        goAnyDirection();
    }

    private boolean handleLastRow(boolean alreadyDoneNextX, boolean previousXAvailable, boolean previousYAvailable) {
        if (!previousXAvailable && !previousYAvailable && !data.xMaxed()) {
            Crementer crementer = foundExit ?
                    horizontalIncrementer :
                    verticalDecrementer;
            crementer.processStep();
            return true;
        }

        if (previousXAvailable) {
            if (alreadyDoneNextX) {
                horizontalDecrementer.processStep();
            } else if (random(3) == 2) {
                horizontalIncrementer.processStep();
            } else { // Happens only once
                createExitHere();
                fillTheRest();
            }
            return true;
        }
        return false;
    }

    private void goForwardsIfPossibleOtherwiseNextTile(boolean alreadyDoneNextX) {
        final boolean nextXAvailable = !alreadyDoneNextX;

        final boolean nextYAvailable = !data.isAlreadyProcessedAt(+0, +1);
        if (nextXAvailable && nextYAvailable) {
            getRandomElement(new ArrayList<>(List.of(horizontalIncrementer, verticalIncrementer))).processStep();
        } else if (nextXAvailable) {
            horizontalIncrementer.processStep();
        } else if (nextYAvailable) {
            verticalIncrementer.processStep();
        } else {
            findNextProcessedTileInOrder();
        }
    }

    private void goProbablyBackwards() {
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
        crementer.processStep();
    }

    private void goAnyDirection() {
        List<Crementer> crementers = new ArrayList<>();
        if (!data.isAlreadyProcessedAt(+0, -1)) {
            crementers.add(verticalDecrementer);
        }
        if (!data.isAlreadyProcessedAt(-1, +0)) {
            crementers.add(horizontalDecrementer);
        }
        if (!data.isAlreadyProcessedAt(+1, +0)) {
            crementers.add(horizontalIncrementer);
        }
        if (!data.isAlreadyProcessedAt(+0, +1)) {
            crementers.add(verticalIncrementer);
        }
        getRandomElement(crementers).processStep();
    }

    private void createExitHere() {
        foundExit = true;
        data.setWallAtCurrent(VERTICAL_WALL);
    }

    private void fillTheRest() {
        data.setX(1);
        data.setY(1);
        findNextProcessedTileInOrder();
    }

    private Crementer getRandomElement(List<Crementer> actions) {
        var i =
                (actions.size() == 1) ?
                        0 :
                        (random(actions.size()) - 1);
        return actions.get(i);
    }
}