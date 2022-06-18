package dataHandling;

public class Decrementer extends Crementer {
    public Decrementer(Data data, Orientation orientation, boolean checkIfStepsFilledBeforeRestart) {
        super(data, orientation, checkIfStepsFilledBeforeRestart);
    }

    @Override
    public void toImplement() {
        data.decrement(orientation.getXOrY());
        data.setWallAtCurrent(orientation.getTypeOfWall());
    }
}
