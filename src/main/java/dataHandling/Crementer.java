package dataHandling;

import main.MazeBuilder;

public abstract class Crementer {
    protected final MazeData mazeData;
    protected final boolean isHorizontal;
    private final boolean mustCheckIfStepsFilledBeforeRestart;
    private final MazeBuilder mazeBuilder;

    public Crementer(MazeData mazeData, boolean mustCheckIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder, boolean isHorizontal) {
        this.mazeData = mazeData;
        this.mustCheckIfStepsFilledBeforeRestart = mustCheckIfStepsFilledBeforeRestart;
        this.mazeBuilder = mazeBuilder;
        this.isHorizontal = isHorizontal;
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
        mazeBuilder.buildMazeForCurrentStep();
    }

}
