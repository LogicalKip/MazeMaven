package dataHandling;

public class Decrementer extends Crementer {
    public Decrementer(Data data, Orientation orientation) {
        super(data, orientation);
    }
    @Override
    public void doStuff() {
        data.decrement(orientation.getXOrY());
        data.setWallAtCurrent(orientation.getTypeOfWall());

        data.nextStep();
    }
}
