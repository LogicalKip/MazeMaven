package main;

import dataHandling.Crementer;
import dataHandling.Data;
import dataHandling.Decrementer;
import dataHandling.Horizontal;
import dataHandling.Incrementer;
import dataHandling.Vertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        buildMazeForCurrentStep();
    }

    private void restartFromNextProcessedTileInOrder() {
        do {
            data.nextTile();
        } while (!data.isProcessedAtCurrent());

        buildMazeForCurrentStep();
    }

    public void buildMazeForCurrentStep() {
        final boolean previousXAvailable = data.isAvailable(-1, +0);
        final boolean previousYAvailable = data.isAvailable(+0, -1);

        if (handleLastRow(previousXAvailable, previousYAvailable)) {
            return;
        }

        findAnyDirection().ifPresentOrElse(Crementer::processStep, this::restartFromNextProcessedTileInOrder);
    }

    private boolean handleLastRow(boolean previousXAvailable, boolean previousYAvailable) {
        if (!data.yMaxed()) {
            return false;
        }
        if (previousXAvailable) {
            final boolean nextXNotAvailable = !data.isAvailable(+1, +0);
            if (nextXNotAvailable) {
                horizontalDecrementer.processStep();
            } else if (random(3) == 2) {
                horizontalIncrementer.processStep();
            } else { // Happens only once
                createExitHere();
                fillTheRest();
            }
            return true;
        }

        if (!previousYAvailable && !data.xMaxed()) {
            (foundExit ?
                    horizontalIncrementer :
                    verticalDecrementer).processStep();
            return true;
        }
        return false;
    }

    private Optional<Crementer> findAnyDirection() {
        List<Crementer> crementers = new ArrayList<>();
        if (data.isAvailable(-1, +0)) {
            crementers.add(horizontalDecrementer);
        }
        if (data.isAvailable(+0, -1)) {
            crementers.add(verticalDecrementer);
        }
        if (data.isAvailable(+1, +0)) {
            crementers.add(horizontalIncrementer);
        }
        if (data.isAvailable(+0, +1)) {
            crementers.add(verticalIncrementer);
        }
        if (crementers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getRandomElement(crementers));
    }

    private void createExitHere() {
        foundExit = true;
        data.setWallAtCurrent(VERTICAL_WALL);
    }

    private void fillTheRest() {
        data.setX(1);
        data.setY(1);
        restartFromNextProcessedTileInOrder();
    }

    private Crementer getRandomElement(List<Crementer> actions) {
        var i =
                (actions.size() == 1) ?
                        0 :
                        (random(actions.size()) - 1);
        return actions.get(i);
    }
}