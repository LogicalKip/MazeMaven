public class Decrementer implements ICrementer {

    private final Data data;
    private final Orientation orientation;

    public Decrementer(Data data, Orientation orientation) {
        this.data = data;
        this.orientation = orientation;
    }
    @Override
    public void doStuff() {
        data.setPossibleWallAtCurrent(orientation.getTypeOfWall());
        data.increment(orientation.getXOrY());
    }
}
