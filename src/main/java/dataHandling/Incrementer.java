package dataHandling;

public class Incrementer extends Crementer {
    public Incrementer(Data data, Orientation orientation) {
        super(data, orientation);
    }
    @Override
    public void doStuff() {
        data.setPossibleWallAtCurrent(orientation.getTypeOfWall());
        data.increment(orientation.getXOrY());
        data.nextStep();
    }
}
