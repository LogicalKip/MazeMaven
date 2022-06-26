package dataHandling;

import main.MazeBuilder;

public abstract class Crementer {
    protected final MazeData mazeData;
    private final boolean mustCheckIfStepsFilledBeforeRestart;
    private final MazeBuilder mazeBuilder;
    protected Orientation orientation;

    public Crementer(MazeData mazeData, Orientation orientation, boolean mustCheckIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        this.mazeData = mazeData;
        this.orientation = orientation;
        this.mustCheckIfStepsFilledBeforeRestart = mustCheckIfStepsFilledBeforeRestart;
        this.mazeBuilder = mazeBuilder;
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
