package dataHandling;

public class X extends XorY {

    public X(int startingValue) {
        value = startingValue;
    }

    @Override
    public boolean isHorizontal() {
        return true;
    }
}
