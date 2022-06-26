package main;

import dataHandling.Crementer;
import dataHandling.Decrementer;
import dataHandling.Horizontal;
import dataHandling.Incrementer;
import dataHandling.MazeData;
import dataHandling.Vertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static dataHandling.MazeData.VERTICAL_WALL;

public class MazeBuilder {

    private final Random random;

    /**
     * or is it exit ?
     */
    private final int entrancePosition;
    private final MazeData mazeData;
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
        this.mazeData = new MazeData(maxHorizontal, maxVertical, entrancePosition);
        this.entrancePosition = entrancePosition;
        this.foundExit = false;
        verticalDecrementer = new Decrementer(mazeData, new Vertical(), false, this);
        verticalIncrementer = new Incrementer(mazeData, new Vertical(), true, this);
        horizontalIncrementer = new Incrementer(mazeData, new Horizontal(), false, this);
        horizontalDecrementer = new Decrementer(mazeData, new Horizontal(), true, this);
    }

    private int random(int count) {
        return (int) (count * random.nextFloat()) + 1;
    }

    public int[][] getWallArray() {
        return mazeData.getWallArray();
    }

    public int getEntrancePosition() {
        return entrancePosition;
    }

    public void createMaze() {
        mazeData.setX(entrancePosition);
        mazeData.setY(1);

        buildMazeForCurrentStep();
    }

    private void restartFromNextProcessedTile() {
        do {
            mazeData.nextTile();
        } while (!mazeData.isProcessedAtCurrent());

        buildMazeForCurrentStep();
    }

    public void buildMazeForCurrentStep() {
        if (handleLastRow()) {
            return;
        }

        findAnyDirection().ifPresentOrElse(Crementer::processStep, this::restartFromNextProcessedTile);
    }

    private boolean handleLastRow() {
        if (!mazeData.yMaxed()) {
            return false;
        }

        if (!mazeData.isAvailable(+1, +0)) {
            return false;
        }

        final boolean previousXAvailable = mazeData.isAvailable(-1, +0);
        if (previousXAvailable) {
            if (random(3) == 2) {
                horizontalIncrementer.processStep();
            } else { // Happens only once
                createExitHere();
                fillTheRest();
            }
            return true;
        }

        final boolean previousYAvailable = mazeData.isAvailable(+0, -1);
        if (!previousYAvailable && !mazeData.xMaxed()) {
            (foundExit ?
                    horizontalIncrementer :
                    verticalDecrementer).processStep();
            return true;
        }
        return false;
    }

    private Optional<Crementer> findAnyDirection() {
        List<Crementer> crementers = new ArrayList<>();
        if (mazeData.isAvailable(-1, +0)) {
            crementers.add(horizontalDecrementer);
        }
        if (mazeData.isAvailable(+0, -1)) {
            crementers.add(verticalDecrementer);
        }
        if (mazeData.isAvailable(+1, +0)) {
            crementers.add(horizontalIncrementer);
        }
        if (mazeData.isAvailable(+0, +1)) {
            crementers.add(verticalIncrementer);
        }
        if (crementers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getRandomElement(crementers));
    }

    private void createExitHere() {
        foundExit = true;
        mazeData.setWallAtCurrent(VERTICAL_WALL);
    }

    private void fillTheRest() {
        mazeData.setX(1);
        mazeData.setY(1);
        restartFromNextProcessedTile();
    }

    private Crementer getRandomElement(List<Crementer> actions) {
        var i =
                (actions.size() == 1) ?
                        0 :
                        (random(actions.size()) - 1);
        return actions.get(i);
    }
}