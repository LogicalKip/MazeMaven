package dataHandling;

import main.MazeBuilder;

public class Incrementer extends Crementer {
    public Incrementer(Data data, boolean mustCheckIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        this(data, null, mustCheckIfStepsFilledBeforeRestart, mazeBuilder);
    }

    public Incrementer(Data data, Orientation orientation, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        super(data, orientation, checkIfStepsFilledBeforeRestart, mazeBuilder);
    }

    @Override
    protected void toImplement() {
        data.simplifyWallInto(orientation.getTypeOfWall());
        data.increment(orientation.getXOrY());
    }
}
