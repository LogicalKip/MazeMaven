package dataHandling;

import main.MazeBuilder;

public class Incrementer extends Crementer {
    public Incrementer(Data data, Orientation orientation, Runnable checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        super(data, orientation, checkIfStepsFilledBeforeRestart, mazeBuilder);
    }

    @Override
    public void toImplement() {
        data.simplifyWallInto(orientation.getTypeOfWall());
        data.increment(orientation.getXOrY());
    }
}
