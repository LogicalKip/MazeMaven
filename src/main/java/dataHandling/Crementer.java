package dataHandling;

import main.MazeBuilder;

public abstract class Crementer {
    protected final Data data;
    protected final Orientation orientation;
    private final Runnable restarter;
    private final MazeBuilder mazeBuilder;

    public Crementer(Data data, Orientation orientation, Runnable restarter, MazeBuilder mazeBuilder) {
        this.data = data;
        this.orientation = orientation;
        this.restarter = restarter;
        this.mazeBuilder = mazeBuilder;
    }

    protected abstract void toImplement();

    public void doStuff() {
        toImplement();
        data.nextStep();
        restarter.run();
    }

}
