package io;

import exceptions.InvalidMazeException;
import exceptions.MazeUnsolvableException;
import mazeComponents.*;

public class Maze {
    private MazeComponent[][] componentMaze;
    private StartPoint startPoint;
    private EndPoint endPoint;
    private final Player player;
    private final int[] dimensions;

    /**
     * A 2D array consisting of unique instances of the MazeComponent class.
     * Also instantiates instance of player that will be unique to the maze.
     *
     * @param charMaze A 2D array of characters representing the maze map.
     * @throws InvalidMazeException For invalid parameter handling.
     */
    public Maze(char[][] charMaze) throws InvalidMazeException {
        if (charMaze == null || charMaze.length == 0 || charMaze[0].length == 0) {
            throw new InvalidMazeException();
        }
        // charMaze[0] used under assumption that 0th row has same length as all rows in maze.
        this.dimensions = new int[]{charMaze.length, charMaze[0].length};
        initializeMazeComponents(charMaze);
        this.player = new Player(startPoint.getComponentRow(), startPoint.getComponentCol());
    }

    /**
     * Converts given 2D char array into 2D MazeComponent array.
     * Iterates through given charMaze and instantiates corresponding MazeComponent.
     * '#' -> Wall, ' ' -> Path, 'S' -> StartPoint, 'E' -> EndPoint.
     *
     * @param charMaze A 2D array of characters representing the maze map.
     */
    private void initializeMazeComponents(char[][] charMaze) {
        this.componentMaze = new MazeComponent[dimensions[0]][dimensions[1]];
        for (int row = 0; row < dimensions[0]; row++) {
            for (int col = 0; col < dimensions[1]; col++) {
                char element = charMaze[row][col];
                // Error handling not required as charMaze contents already validated in fileLoader.
                switch (element) {
                    case '#' -> componentMaze[row][col] = new Wall(row, col);
                    case 'S' -> {
                        startPoint = new StartPoint(row, col);
                        componentMaze[row][col] = startPoint;
                    }
                    case 'E' -> {
                        endPoint = new EndPoint(row, col);
                        componentMaze[row][col] = endPoint;
                    }
                    case ' ' -> componentMaze[row][col] = new Path(row, col);
                }
            }
        }
    }

    /**
     * Prints a visual representation of maze to the terminal.
     * Iterates through componentMaze and prints corresponding component representation.
     */
    public void printMaze() {
        for (int row = 0; row < dimensions[0]; row++) {
            for (int col = 0; col < dimensions[1]; col++) {
                if (row == player.getPlayerRow() && col == player.getPlayerCol()) {
                    System.out.print(player.getPlayerRep());
                } else {
                    System.out.print(componentMaze[row][col].getComponentRep());
                }
            }
            System.out.println();
        }
    }

    /**
     * Determines whether an attempted move is valid.
     * Validity criteria:
     * - Component at position is traversable.
     * - Attempted position is within the bounds of the map dimensions.
     *
     * @param nextRow Row coordinate to be validated.
     * @param nextCol Column coordinate to be validated.
     * @return boolean -> true if move is valid, false if move is invalid.
     */
    public boolean validMove(int nextRow, int nextCol) {
        return nextRow >= 0 && nextCol < dimensions[1]
                && getComponentAt(nextRow, nextCol).isTraversable();
    }

    /**
     * Takes an input and changes player's position accordingly.
     *
     * @param input A single character indicating directional input (actionable: one of w, a, s, d).
     */
    public void playMaze(char input) throws MazeUnsolvableException {
        switch (input) {
            case 'w' -> movePlayer(-1, 0); // Up.
            case 's' -> movePlayer(1, 0); // Down.
            case 'a' -> movePlayer(0, -1); // Left.
            case 'd' -> movePlayer(0, 1); // Right.
        }
    }

    /**
     * Increments player's position by the given amounts.
     * Also handles tile traversal.
     * Takes player's current position, gets the attempted position, and checks whether it is a
     * valid move. If it is, move the player, if not, do nothing. No error to be thrown on invalid
     * moves as wrong moves are bound to be made, and it would be unproductive to constantly throw
     * errors.
     * Error handling:
     * - Invalid move: void. Player remains in the same position.
     *
     * @param changeRow Amount to increase/decrease player's row by.
     * @param changeCol Amount to increase/decrease player's column by.
     */
    public void movePlayer(int changeRow, int changeCol) throws MazeUnsolvableException {
        int currentRow = player.getPlayerRow();
        int currentCol = player.getPlayerCol();
        int newRow = currentRow + changeRow;
        int newCol = currentCol + changeCol;
        MazeComponent currentTile = getComponentAt(currentRow, currentCol);
        MazeComponent newTile = getComponentAt(newRow, newCol);
        if (validMove(newRow, newCol)) {
            getPlayer().incrementRow(changeRow);
            getPlayer().incrementCol(changeCol);
            if (allPathsTraversed() && !hasBeenSolved()) {
                throw new MazeUnsolvableException("Maze is unsolvable." +
                        " All paths have been traversed without reaching end point.");
            }
            // Path traversal and re-traversal highlighting.
            if (newTile.isTraversedOnce()) {
                currentTile.setTraversedTwice();
            } else {
                newTile.setTraversedOnce();
            }
        }
    }

    /**
     * Determines whether the maze has been completed. It does this by comparing the player's
     * position to the maze's endpoint's position.
     *
     * @return boolean -> true if completed, false if uncompleted.
     */
    public boolean hasBeenSolved() {
        return player.getPlayerRow() == endPoint.getComponentRow() &&
                player.getPlayerCol() == endPoint.getComponentCol();
    }

    /**
     * Iterates through the mazeComponent array to see if all paths have been traversed. Used to
     * assist with checking unsolvability.
     *
     * @return boolean -> true if all paths have been traversed, false if not.
     */
    public boolean allPathsTraversed() {
        for (int row = 0; row < dimensions[0]; row++) {
            for (int col = 0; col < dimensions[1]; col++) {
                if (componentMaze[row][col] instanceof Path path) {
                    if (!path.isTraversedOnce()) {
                        return false;
                    }
                }
            }
        }
        return true; // All Path instances have been traversed.
    }

    /**
     * Returns the instance of maze component that exists at the requested coordinate.
     *
     * @param row Row of requested component.
     * @param col Column of requested component.
     * @return MazeComponent object.
     */
    public MazeComponent getComponentAt(int row, int col) {
        return componentMaze[row][col];
    }

    public Player getPlayer() {
        return player;
    }
    public int[] getDimensions() {
        return dimensions;
    }
}
