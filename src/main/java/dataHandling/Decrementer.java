package dataHandling;

import main.MazeBuilder;

public class Decrementer extends Crementer {

    public Decrementer(MazeData mazeData, Orientation orientation, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        super(mazeData, orientation, checkIfStepsFilledBeforeRestart, mazeBuilder);
    }

    @Override
    protected void moveThrough() {
        mazeData.decrement(orientation.isHorizontal());
        mazeData.setHorizontalWallAtCurrent(orientation.isHorizontal());
    }
}
