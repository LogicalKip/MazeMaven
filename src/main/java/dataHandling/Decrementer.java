package dataHandling;

import main.MazeBuilder;

public class Decrementer extends Crementer {
    public Decrementer(Data data, boolean mustCheckIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        this(data, null, mustCheckIfStepsFilledBeforeRestart, mazeBuilder);
    }

    public Decrementer(Data data, Orientation orientation, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        super(data, orientation, checkIfStepsFilledBeforeRestart, mazeBuilder);
    }

    @Override
    protected void moveThrough() {
        data.decrement(orientation.getXOrY());
        data.setWallAtCurrent(orientation.getTypeOfWall());
    }
}
