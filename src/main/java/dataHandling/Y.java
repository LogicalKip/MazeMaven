package dataHandling;

public class Y extends Axis {
    public Y(int startingValue) {
        value = startingValue;
    }

    @Override
    public boolean isHorizontal() {
        return false;
    }
}
