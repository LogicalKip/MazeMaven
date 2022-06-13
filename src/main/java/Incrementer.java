public class Incrementer implements ICrementer {
    private final Data data;
    private final Orientation orientation;

    public Incrementer(Data data, Orientation orientation) {
        this.data = data;
        this.orientation = orientation;
    }
    @Override
    public void doStuff() {
        data.setPossibleWallAtCurrent(orientation.getTypeOfWall());
        data.increment(orientation.getXOrY());
    }
}
