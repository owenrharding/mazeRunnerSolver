package tests;

import io.FileLoader;
import org.junit.*;

import exceptions.MazeSizeMissmatchException;
import exceptions.MazeMalformedException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FileLoaderTest {
    FileLoader fileLoader;

    /**
     * Initialises fileLoader before testing.
     */
    @Before
    public void setUp() {
        fileLoader = new FileLoader();
    }

    /**
     * Tests that a valid text file is correctly converted into a 2D char array with correct
     * dimensions.
     * This is a test case where no exceptions should be thrown.
     *
     * @throws MazeSizeMissmatchException   If the maze dimensions do not match the provided size.
     * @throws IOException                  If there are IO errors concerning the file.
     * @throws MazeMalformedException       If the maze data is not correctly formatted.
     */
    @Test
    public void validMazeLoadTest() throws MazeSizeMissmatchException, IOException,
            MazeMalformedException {
        char[][] testMaze = fileLoader.load("src/maps/SmallMap.txt");

        Assert.assertNotNull(testMaze); // Check maze has been initialised.
        Assert.assertEquals(7, testMaze.length); // Check number of rows is correct.
        Assert.assertEquals(7, testMaze[0].length); // Check number of columns is correct.
    }

    // Possible cases of error throws:
    // - File not found.
    // - Dimensions line is null.
    // - Dimensions line is formatted incorrectly; has more than two arguments.
    // - Dimensions and maze height/width are incongruent.
    // - More than one Start Point.
    // - No Start Point.
    // - More than one End Point.
    // - No End Point.
    // - Incorrect symbol in maze.
    // - Maze is unsolvable.

    /**
     * Tests that FileNotFoundException is thrown when filepath does not exist.
     */
    @Test
    public void fileNotFoundTest() {
        Assert.assertThrows(FileNotFoundException.class,
                () -> {fileLoader.load("src/maps/tests/ThisFileDoesNotExist.txt");}
        );
    }

    /**
     * Tests that the MazeMalformedException is thrown when dimensions line is null.
     */
    @Test
    public void nullDimensionsTest() {
        Assert.assertThrows(MazeMalformedException.class,
                () -> {fileLoader.load("src/maps/tests/NullDimensionsSmallMap.txt");
                });
    }

    /**
     * Tests that the MazeMalformedException is thrown when dimensions have more than two arguments.
     */
    @Test
    public void dimensionsMalformedTest() {
        Assert.assertThrows(MazeMalformedException.class,
                () -> {fileLoader.load("src/maps/tests/DimensionsMalformedSmallMap.txt");}
        );
    }

    /**
     * Tests that the MazeMissmatchException is thrown when dimensions are incongruent to maze
     * format.
     */
    @Test
    public void dimensionsIncongruentTest() {
        // Test height mismatch.
        Assert.assertThrows(MazeSizeMissmatchException.class,
                () -> {fileLoader.load("src/maps/tests/HeightMismatchSmallMap.txt");}
        );
        // Test width mismatch.
        Assert.assertThrows(MazeSizeMissmatchException.class,
                () -> {fileLoader.load("src/maps/tests/WidthMismatchSmallMap.txt");}
        );
    }

    /**
     * Tests that there is only ever a single start point in a map.
     */
    @Test
    public void validStartPointTest() {
        // Test for when there is no start point.
        Assert.assertThrows(MazeMalformedException.class,
                () -> {fileLoader.load("src/maps/tests/NoStartPointSmallMap.txt");}
        );
        // Test for when there is more than one start point.
        Assert.assertThrows(MazeMalformedException.class,
                () -> {fileLoader.load("src/maps/tests/TwoStartPointsSmallMap.txt");}
        );
    }

    /**
     * Tests that there is only ever a single end point in a map.
     */
    @Test
    public void validEndPointTest() {
        // Test for when there is no end point.
        Assert.assertThrows(MazeMalformedException.class,
                () -> {fileLoader.load("src/maps/tests/NoEndPointSmallMap.txt");}
        );
        // Test for when there is more than one end point.
        Assert.assertThrows(MazeMalformedException.class,
                () -> {fileLoader.load("src/maps/tests/TwoEndPointsSmallMap.txt");}
        );
    }

    /**
     * Tests the case when an invalid character appears in the map text file.
     */
    @Test
    public void invalidCharacterTest() {
        Assert.assertThrows(MazeMalformedException.class,
                () -> {fileLoader.load("src/maps/tests/InvalidCharacterSmallMap.txt");}
        );
    }
}
