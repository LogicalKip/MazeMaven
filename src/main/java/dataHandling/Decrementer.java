package dataHandling;

import main.MazeBuilder;

public class Decrementer extends Crementer {
    public Decrementer(XorY horizontal, boolean checkIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder, MazeData mazeData) {
        super(mazeData, checkIfStepsFilledBeforeRestart, mazeBuilder, horizontal);
    }

    @Override
    protected void moveThrough() {
        xorY.value--;
        mazeData.setWallAtCurrent(xorY.isHorizontal());
    }
}
