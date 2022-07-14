package dataHandling;

import main.MazeBuilder;

public class Decrementer extends Crementer {
    public Decrementer(MazeData mazeData, boolean horizontal, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        super(mazeData, checkIfStepsFilledBeforeRestart, mazeBuilder, horizontal);
    }

    @Override
    protected void moveThrough() {
        mazeData.decrement(isHorizontal);
        mazeData.setHorizontalWallAtCurrent(isHorizontal);
    }
}
