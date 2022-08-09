package main;

import dataHandling.Crementer;
import dataHandling.Decrementer;
import dataHandling.Incrementer;
import dataHandling.MazeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    private boolean foundExit;

    public MazeBuilder(Random random, int maxHorizontal, int maxVertical) {
        this.random = random;
        final int entrancePosition = random(maxHorizontal);
        this.mazeData = new MazeData(maxHorizontal, maxVertical, entrancePosition);
        this.entrancePosition = entrancePosition;
        this.foundExit = false;
        verticalDecrementer = new Decrementer(mazeData.y, false, this, mazeData);
        verticalIncrementer = new Incrementer(mazeData.y, true, this, mazeData);
        horizontalIncrementer = new Incrementer(mazeData.x, false, this, mazeData);
        horizontalDecrementer = new Decrementer(mazeData.x, true, this, mazeData);
    }

    private int random(int count) {
        return (int) (count * random.nextFloat()) + 1;
    }

    public boolean[][] getHorizontalWallArray() {
        return mazeData.getHorizontalWallArray();
    }

    public boolean[][] getVerticalWallArray() {
        return mazeData.getVerticalWallArray();
    }

    public int getEntrancePosition() {
        return entrancePosition;
    }

    public void buildMaze() {
        if (handleLastRow()) {
            return;
        }

        findAnyDirection().ifPresentOrElse(Crementer::processStep, this::restartFromNextProcessedTile);
    }

    private void restartFromNextProcessedTile() {
        do {
            mazeData.nextTile();
        } while (!mazeData.isProcessedAtCurrent());

        buildMaze();
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
        if (!previousYAvailable) {
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
        mazeData.setWallAtCurrent(false);
    }

    private void fillTheRest() {
        mazeData.backToBottomLeft();
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