package mazeComponents;

import java.awt.*;

public class Wall extends MazeComponent {
    /**
     * Represents an untraversable wall in a maze.
     *
     * @param xPos x coordinate of this wall.
     * @param yPos y coordinate of this wall.
     */
    public Wall(int xPos, int yPos) {
        super(xPos, yPos, "█", Color.gray, false);
    }
}
