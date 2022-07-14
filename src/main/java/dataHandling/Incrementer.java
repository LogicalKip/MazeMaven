package dataHandling;

import main.MazeBuilder;

public class Incrementer extends Crementer {

    public Incrementer(MazeData mazeData, boolean horizontal, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        super(mazeData, checkIfStepsFilledBeforeRestart, mazeBuilder, horizontal);
    }

    @Override
    protected void moveThrough() {
        mazeData.allowPassage(isHorizontal);
        mazeData.increment(isHorizontal);
    }
}
