package mazeComponents;

import java.awt.*;

public class EndPoint extends MazeComponent {
    /**
     * Represents the end point of a maze.
     *
     * @param xPos x coordinate of this end point.
     * @param yPos y coordinate of this end point.
     */
    public EndPoint(int xPos, int yPos) {
        super(xPos, yPos, "\u001B[31m█\u001B[0m", Color.red, true);
    }
}
