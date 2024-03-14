package tests;

import exceptions.InvalidMazeException;
import exceptions.MazeUnsolvableException;
import io.Player;
import mazeComponents.*;
import io.Maze;
import org.junit.*;

public class MazeTest {
    Maze maze;

    /**
     * Initialises maze used for testing.
     */
    @Before
    public void setUp() throws InvalidMazeException {
        char[][] charMaze = {
                {'#', '#', '#', '#', '#', '#', '#'},
                {'#', 'S', '#', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', '#', '#', ' ', '#'},
                {'#', ' ', '#', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', ' ', '#', ' ', '#'},
                {'#', ' ', ' ', ' ', '#', 'E', '#'},
                {'#', '#', '#', '#', '#', '#', '#'}
        };
        maze = new Maze(charMaze);
    }

    /**
     * Tests different cases of both valid and invalid moves and asserts their success.
     */
    @Test
    public void testValidMove() {
        Assert.assertTrue(maze.validMove(2, 1)); // Valid path.
        Assert.assertTrue(maze.validMove(3, 5)); // Valid path.
        Assert.assertFalse(maze.validMove(0, 0)); // Invalid wall.
        Assert.assertFalse(maze.validMove(-1, -1)); // Out of bounds invalid move.
    }

    /**
     * Tests various move cases, both valid and invalid, and checks their success.
     * Should not throw unsolvable exception as given maze is solvable.
     *
     * @throws MazeUnsolvableException  If maze is unsolvable.
     */
    @Test
    public void testMovePlayer() throws MazeUnsolvableException {
        int initialRow = maze.getPlayer().getPlayerRow();
        int initialCol = maze.getPlayer().getPlayerCol();

        maze.movePlayer(1, 0); // Move down to a path (valid move).
        Assert.assertEquals(initialRow + 1, maze.getPlayer().getPlayerRow());
        Assert.assertEquals(initialCol, maze.getPlayer().getPlayerCol());

        // Initial row + 1 used because of player's move above.
        maze.movePlayer(0, 1); // Move right into a wall (no change).
        Assert.assertEquals(initialRow + 1, maze.getPlayer().getPlayerRow());
        Assert.assertEquals(initialCol, maze.getPlayer().getPlayerCol()); // No change in col.

        maze.movePlayer(0, -1); // Move out of bounds (no change).
        Assert.assertEquals(initialRow + 1, maze.getPlayer().getPlayerRow());
        Assert.assertEquals(initialCol, maze.getPlayer().getPlayerCol());
    }

    /**
     * Tests that when the maze is unsolved, hasBeenSolved() returns false, and when the maze is
     * solved, hasBeenSolved() returns true.
     * Should not throw unsolvable exception as given maze is solvable.
     *
     * @throws MazeUnsolvableException  If maze is unsolvable.
     */
    @Test
    public void testHasBeenSolved() throws MazeUnsolvableException {
        Assert.assertFalse(maze.hasBeenSolved()); // Maze is unsolved.
        // Move to the endpoint to solve the maze.
        maze.movePlayer(4, 4);
        Assert.assertTrue(maze.hasBeenSolved());
    }

    /**
     * Checks that the interpreted dimensions are the same as the given ones.
     */
    @Test
    public void testGetDimensions() {
        int[] dimensions = maze.getDimensions();
        Assert.assertEquals(7, dimensions[0]); // Check number of rows.
        Assert.assertEquals(7, dimensions[1]); // Check number of columns.
    }

    /**
     * Checks that the automatically initialised player is properly initialised: that is, it is not
     * null and is initialised with the start point coordinates.
     */
    @Test
    public void testGetPlayer() {
        Player player = maze.getPlayer();
        Assert.assertNotNull(player);
        Assert.assertEquals(1, player.getPlayerRow());
        Assert.assertEquals(1, player.getPlayerCol());
    }

    /**
     * Checks maze is initialised properly with correctly initialised components.
     * Check instance of path, wall, start point, and end point.
     */
    @Test
    public void testGetComponentAt() {
        MazeComponent startPoint = maze.getComponentAt(1, 1);
        MazeComponent path = maze.getComponentAt(3, 3);
        MazeComponent wall = maze.getComponentAt(0, 0);
        MazeComponent endPoint = maze.getComponentAt(5, 5);

        Assert.assertTrue(startPoint instanceof StartPoint);
        Assert.assertTrue(path instanceof Path);
        Assert.assertTrue(wall instanceof Wall);
        Assert.assertTrue(endPoint instanceof EndPoint);
    }
}

