package dataHandling;

public class X extends Axis {

    public X(int startingValue) {
        value = startingValue;
    }

    @Override
    public boolean isHorizontal() {
        return true;
    }
}
