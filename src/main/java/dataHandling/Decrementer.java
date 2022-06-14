package dataHandling;

public class Decrementer extends Crementer {

    private final Data data;
    private final Orientation orientation;

    public Decrementer(Data data, Orientation orientation) {
        this.data = data;
        this.orientation = orientation;
    }
    @Override
    public void doStuff() {
        data.decrement(orientation.getXOrY());
        data.setWallAtCurrent(orientation.getTypeOfWall());

        data.nextStep();
    }
}
