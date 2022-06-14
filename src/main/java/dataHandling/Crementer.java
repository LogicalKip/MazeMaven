package dataHandling;

public abstract class Crementer {
    protected final Data data;
    protected final Orientation orientation;

    public Crementer(Data data, Orientation orientation) {
        this.data = data;
        this.orientation = orientation;
    }

    protected abstract void toImplement();

    public void doStuff() {
        toImplement();
        data.nextStep();
    }

}
