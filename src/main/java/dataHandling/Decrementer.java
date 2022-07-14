package dataHandling;

import main.MazeBuilder;

public class Decrementer extends Crementer {
    public Decrementer(boolean horizontal, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder, MazeData mazeData) {
        super(mazeData, checkIfStepsFilledBeforeRestart, mazeBuilder, horizontal);
    }

    @Override
    protected void moveThrough() {
        mazeData.decrement(isHorizontal);
        mazeData.setHorizontalWallAtCurrent(isHorizontal);
    }
}
