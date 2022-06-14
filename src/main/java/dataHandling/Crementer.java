package dataHandling;

public abstract class Crementer {
    protected final Data data;
    protected final Orientation orientation;

    public Crementer(Data data, Orientation orientation) {
        this.data = data;
        this.orientation = orientation;
    }

    public abstract void doStuff();

}
