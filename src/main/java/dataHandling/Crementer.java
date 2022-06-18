package dataHandling;

public abstract class Crementer {
    protected final Data data;
    protected final Orientation orientation;
    private final Runnable restarter;

    public Crementer(Data data, Orientation orientation, Runnable restarter) {
        this.data = data;
        this.orientation = orientation;
        this.restarter = restarter;
    }

    protected abstract void toImplement();

    public void doStuff() {
        toImplement();
        data.nextStep();
        restarter.run();
    }

}
