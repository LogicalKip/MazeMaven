package dataHandling;

public abstract class Crementer {
    protected final Data data;
    protected final Orientation orientation;
    private final boolean checkIfStepsFilledBeforeRestart;

    public Crementer(Data data, Orientation orientation, boolean checkIfStepsFilledBeforeRestart) {
        this.data = data;
        this.orientation = orientation;
        this.checkIfStepsFilledBeforeRestart = checkIfStepsFilledBeforeRestart;
    }

    protected abstract void toImplement();

    public void doStuff() {
        toImplement();
        data.nextStep();
    }

}
