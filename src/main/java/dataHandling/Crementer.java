package dataHandling;

import main.MazeBuilder;

public abstract class Crementer {
    protected final Data data;
    protected final Orientation orientation;
    private final boolean mustCheckIfStepsFilledBeforeRestart;
    private final MazeBuilder mazeBuilder;

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
        if (mustCheckIfStepsFilledBeforeRestart) {
            if (data.stepsAreNotAllFilled()) {
                mazeBuilder.firstInstruction();
            }
        } else {
            mazeBuilder.firstInstruction();
        }
    }

}
