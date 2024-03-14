import exceptions.InvalidMazeException;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import exceptions.MazeUnsolvableException;
import io.*;

import java.io.IOException;

import java.util.Scanner;

/**
 * Entry point for app.
 * Allows users to load a maze from a text file and choose between a terminal text-based
 * interface or graphical user interface.
 */
public class Launcher {

    /**
     * Main method that initialises and runs program.
     * Loads map and interface type based on input, but defaults to text-based interface and a
     * preloaded map if no arguments are given.
     *
     * @param args Command-line arguments. Accepts an optional maze file name and the "GUI" flag to
     *             use the graphical user interface.
     * @throws MazeSizeMissmatchException   If the maze dimensions do not match the provided size.
     * @throws IOException                  If there is an issue with file IO.
     * @throws MazeMalformedException       If the maze data is not correctly formatted.
     * @throws InvalidMazeException         If the MazeComponent array is invalid in any way.
     */
    public static void main(String[] args) throws MazeSizeMissmatchException, IOException,
            MazeMalformedException, InvalidMazeException, MazeUnsolvableException {
        String textFileInput = "SmallMap.txt"; // Uses SmallMap.txt by default.
        boolean useGUI = false; // Uses System.out by default.

        // Process command-line arguments.
        for (String arg : args) {
            if (arg.contains(".txt")) {
                textFileInput = arg;
            } else if (arg.contains("GUI")) {
                useGUI = true;
            }
        }

        Maze mazeUltimate = loadMaze("maps/" + textFileInput);

        if (useGUI) {
            MazeGUI gui = new MazeGUI(mazeUltimate);
            gui.redraw();
            gui.setVisible(true);
        } else {
            try (Scanner scanner = new Scanner(System.in)) {
                while (!mazeUltimate.hasBeenSolved()) {
                    mazeUltimate.printMaze();
                    mazeUltimate.playMaze(scanner.next().charAt(0));
                }
                mazeUltimate.printMaze();
            }
            System.out.println("Congratulations! You solved the maze!");
        }
    }

    /**
     * Converts a maze in a text file to an instance of the Maze class.
     *
     * @param fileName File path of the txt document containing maze data.
     * @return A Maze object representing the loaded maze.
     * @throws MazeSizeMissmatchException   If the maze dimensions do not match the provided size.
     * @throws IOException                  If there is an issue with file IO.
     * @throws MazeMalformedException       If the maze data is not correctly formatted.
     * @throws InvalidMazeException         If the MazeComponent array is invalid in any way.
     */
    public static Maze loadMaze(String fileName) throws MazeSizeMissmatchException, IOException,
            MazeMalformedException, InvalidMazeException {
        FileLoader fileLoader = new FileLoader();
        return new Maze(fileLoader.load(fileName));
    }
}