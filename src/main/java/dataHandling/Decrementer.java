package dataHandling;

import main.MazeBuilder;

public class Decrementer extends Crementer {
    public Decrementer(Axis axis, MazeBuilder mazeBuilder, MazeData mazeData) {
        super(mazeData, mazeBuilder, axis);
    }

    @Override
    protected void moveThrough() {
        axis.value--;
        mazeData.setWallAtCurrent(axis.isHorizontal()); // Note : using here the same logic as incrementer would make a similar but more open labyrinth (less cul-de-sac)
    }
}
