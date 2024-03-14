package io;

import java.awt.Color;

public class Player {
    private int playerRow;
    private int playerCol;
    private final Color guiColour = Color.GREEN;

    /**
     * The avatar that the user navigates the maze with.
     *
     * @param row Row position of the player.
     * @param col Column position of the player.
     */
    public Player(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
    }

    /**
     * Gets the player's row position.
     *
     * @return The row position.
     */
    public int getPlayerRow() {
        return playerRow;
    }

    /**
     * Gets the player's column position.
     *
     * @return The column position.
     */
    public int getPlayerCol() {
        return playerCol;
    }

    /**
     * Gets the player's representation.
     *
     * @return The player's representation string.
     */
    public String getPlayerRep() {
        return "\u001B[32m█\u001B[0m";
    }

    /**
     * Increment the player's row position by the given amount.
     *
     * @param rowChange The amount to increment the row position by.
     */
    public void incrementRow(int rowChange) {
        playerRow += rowChange;
    }

    /**
     * Increment the player's column position by the given amount.
     *
     * @param colChange The amount to increment the column position by.
     */
    public void incrementCol(int colChange) {
        playerCol += colChange;
    }

    /**
     * Gets the GUI color associated with the player.
     *
     * @return The GUI color.
     */
    public Color getGuiColour() {
        return guiColour;
    }
}