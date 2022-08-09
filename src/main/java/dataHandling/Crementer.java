package dataHandling;

import main.MazeBuilder;

public abstract class Crementer {
    protected final MazeData mazeData;
    protected final XorY xorY;
    private final boolean mustCheckIfStepsFilledBeforeRestart;
    private final MazeBuilder mazeBuilder;

    public Crementer(MazeData mazeData, boolean mustCheckIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder, XorY xorY) {
        this.mazeData = mazeData;
        this.mustCheckIfStepsFilledBeforeRestart = mustCheckIfStepsFilledBeforeRestart;
        this.mazeBuilder = mazeBuilder;
        this.xorY = xorY;
    }

    protected abstract void moveThrough();

    public void processStep() {
        moveThrough();
        mazeData.nextStep();
        restart();
    }

    private void restart() {
        if (mustCheckIfStepsFilledBeforeRestart && !mazeData.stepsAreNotAllFilled()) {
            return;
        }
        mazeBuilder.buildMaze();
    }

}
