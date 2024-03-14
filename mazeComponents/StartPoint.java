package mazeComponents;

import java.awt.*;

public class StartPoint extends MazeComponent {
    /**
     * Represents the start point of a maze.
     *
     * @param xPos x coordinate of this start point.
     * @param yPos y coordinate of this start point.
     */
    public StartPoint(int xPos, int yPos) {
        super(xPos, yPos, "\u001B[96m█\u001B[0m", Color.cyan, true);
    }
}

