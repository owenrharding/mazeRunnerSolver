package mazeComponents;

import java.awt.*;

public class MazeComponent {
    private final int componentRow;
    private final int componentCol;
    protected String componentRep;
    protected Color guiColour;
    protected boolean traversable;
    protected boolean traversedOnce = false;

    /**
     * Initializes a new MazeComponent with the given row and column coordinates.
     *
     * @param row The row coordinate of the component.
     * @param col The column coordinate of the component.
     */
    public MazeComponent(int row, int col, String componentRep, Color guiColour, boolean traversable) {
        this.componentRow = row;
        this.componentCol = col;
        this.componentRep = componentRep;
        this.guiColour = guiColour;
        this.traversable = traversable;
    }

    /**
     * Marks the component as traversed once and updates its appearance.
     */
    public void setTraversedOnce() {
        this.traversedOnce = true;
        this.componentRep = "\u001B[96m█\u001B[0m";
        this.guiColour = Color.cyan;
    }

    /**
     * Marks the component as traversed twice and updates its appearance.
     */
    public void setTraversedTwice() {
        guiColour = Color.blue;
        componentRep = "\u001B[34m█\u001B[0m";
    }

    /**
     * Gets the row coordinate of the component.
     *
     * @return The row coordinate.
     */
    public int getComponentRow() {
        return componentRow;
    }

    /**
     * Gets the column coordinate of the component.
     *
     * @return The column coordinate.
     */
    public int getComponentCol() {
        return componentCol;
    }

    /**
     * Gets the representation of the component.
     *
     * @return The component's representation.
     */
    public String getComponentRep() {
        return componentRep;
    }

    /**
     * Checks if the component is traversable.
     *
     * @return True if the component is traversable, false otherwise.
     */
    public boolean isTraversable() {
        return traversable;
    }

    /**
     * Checks if the component has been traversed once.
     *
     * @return True if the component has been traversed once, false otherwise.
     */
    public boolean isTraversedOnce() {
        return traversedOnce;
    }

    /**
     * Gets the GUI color of the component.
     *
     * @return The GUI color.
     */
    public Color getGuiColour() {
        return guiColour;
    }
}