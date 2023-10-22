package dataHandling;

import main.MazeBuilder;

public class Incrementer extends Crementer {

    public Incrementer(Axis axis, MazeBuilder mazeBuilder, MazeData mazeData) {
        super(mazeData, mazeBuilder, axis);
    }

    @Override
    protected void moveThrough() {
        mazeData.allowPassage(axis.isHorizontal());
        axis.value++;
    }
}
