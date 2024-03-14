package mazeComponents;

import java.awt.*;

public class Path extends MazeComponent {
    /**
     * Represents a traversable path of a maze.
     *
     * @param xPos x coordinate of this path.
     * @param yPos y coordinate of this path.
     */
    public Path(int xPos, int yPos) {
        super(xPos, yPos, " ", Color.white, true);
    }
}