package dataHandling;

import main.MazeBuilder;

public class Incrementer extends Crementer {

    public Incrementer(boolean horizontal, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder, MazeData mazeData) {
        super(mazeData, checkIfStepsFilledBeforeRestart, mazeBuilder, horizontal);
    }

    @Override
    protected void moveThrough() {
        mazeData.allowPassage(isHorizontal);
        mazeData.increment(isHorizontal);
    }
}
