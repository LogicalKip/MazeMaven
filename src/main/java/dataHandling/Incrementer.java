package dataHandling;

import main.MazeBuilder;

public class Incrementer extends Crementer {

    public Incrementer(MazeData mazeData, Orientation orientation, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        super(mazeData, orientation, checkIfStepsFilledBeforeRestart, mazeBuilder);
    }

    @Override
    protected void moveThrough() {
        mazeData.allowPassage(orientation);
        mazeData.increment(orientation.getXOrY());
    }
}
