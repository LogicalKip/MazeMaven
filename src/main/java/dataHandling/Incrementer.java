package dataHandling;

public class Incrementer extends Crementer {
    public Incrementer(Data data, Orientation orientation, boolean checkIfStepsFilledBeforeRestart) {
        super(data, orientation, checkIfStepsFilledBeforeRestart);
    }

    @Override
    public void toImplement() {
        data.simplifyWallInto(orientation.getTypeOfWall());
        data.increment(orientation.getXOrY());
    }
}
