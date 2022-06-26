package dataHandling;

import main.MazeBuilder;

public class Incrementer extends Crementer {

    public Incrementer(Data data, Orientation orientation, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        super(data, orientation, checkIfStepsFilledBeforeRestart, mazeBuilder);
    }

    @Override
    protected void moveThrough() {
        data.allowPassage(orientation);
        data.increment(orientation.getXOrY());
    }
}
