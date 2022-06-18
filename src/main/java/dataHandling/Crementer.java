package dataHandling;

import main.MazeBuilder;

public abstract class Crementer {
    protected final Data data;
    private final boolean mustCheckIfStepsFilledBeforeRestart;
    private final MazeBuilder mazeBuilder;
    protected Orientation orientation;

    public Crementer(Data data, Orientation orientation, boolean mustCheckIfStepsFilledBeforeRestart, MazeBuilder mazeBuilder) {
        this.data = data;
        this.orientation = orientation;
        this.mustCheckIfStepsFilledBeforeRestart = mustCheckIfStepsFilledBeforeRestart;
        this.mazeBuilder = mazeBuilder;
    }

    protected abstract void toImplement();

    public void doStuff() {
        toImplement();
        data.nextStep();
        restart();
    }

    private void restart() {
        if (mustCheckIfStepsFilledBeforeRestart && !data.stepsAreNotAllFilled()) {
            return;
        }
        mazeBuilder.firstInstruction();
    }


    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
