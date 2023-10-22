package dataHandling;

import main.MazeBuilder;

public abstract class Crementer {
    protected final MazeData mazeData;
    protected final Axis axis;
    private final MazeBuilder mazeBuilder;

    public Crementer(MazeData mazeData, MazeBuilder mazeBuilder, Axis axis) {
        this.mazeData = mazeData;
        this.mazeBuilder = mazeBuilder;
        this.axis = axis;
    }

    protected abstract void moveThrough();

    public void processStep() {
        moveThrough();
        mazeData.markStepAsProcessed();
        restart();
    }

    private void restart() {
        if (mazeData.stepsAreAllFilled()) {
            return;
        }
        mazeBuilder.buildMaze();
    }

}
