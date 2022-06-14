package dataHandling;

public class Decrementer extends Crementer {
    public Decrementer(Data data, Orientation orientation) {
        super(data, orientation);
    }

    @Override
    public void toImplement() {
        data.decrement(orientation.getXOrY());
        data.setWallAtCurrent(orientation.getTypeOfWall());
    }
}
