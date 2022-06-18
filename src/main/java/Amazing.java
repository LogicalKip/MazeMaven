/**
 * + The original program is by Jack Hauber, and the source is
 * "Basic Computer Games." Used with permission of David Ahl;
 * see www.SwapMeetDave.com.
 * + This exercise was inspired by Alan Hensel's use of Amazing
 * as a refactoring challenge.
 * + This transliteration to Java was created by Bill Wake, William.Wake@acm.org
 */

import main.MazeBuilder;
import main.MazePrinter;

import java.util.Random;

public class Amazing {
    public static Random random = new Random(0);
    static StringBuilder result = MazePrinter.result;

    public static void main(String[] args) {
        doIt(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println(result);
    }

    public static void doIt(int maxHorizontal, int maxVertical) {
        if (maxHorizontal == 1 || maxVertical == 1) {
            return;
        }
        var mazeBuilder = new MazeBuilder(random, maxHorizontal, maxVertical);
        mazeBuilder.createMaze();
        new MazePrinter().printMaze(maxHorizontal, maxVertical, mazeBuilder.getWallArray(), mazeBuilder.getEntrancePosition());
    }

}
