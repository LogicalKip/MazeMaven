package dataHandling;

public class Y extends XorY {
    public Y(int startingValue) {
        value = startingValue;
    }

    @Override
    public boolean isHorizontal() {
        return false;
    }
}
