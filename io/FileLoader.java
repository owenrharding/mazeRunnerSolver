package io;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader implements FileInterface {

    /**
     * Loads a maze from the specified filename and converts it into a 2D character array.
     * <p>
     * This method attempts to read a maze file with the following expectations:
     * - The first line should contain the maze's dimensions, separated by a space (e.g., "10 15").
     * - Subsequent lines should provide the maze data with specific characters representing the maze elements.
     * </p>
     * <p>
     * Exception Handling:
     * - Throws {@link MazeMalformedException} if the maze data doesn't match the given format.
     * - Throws {@link MazeSizeMissmatchException} if the maze data doesn't match the specified dimensions.
     * - Throws {@link IllegalArgumentException} for other general validation errors, such as invalid characters.
     * - Throws {@link FileNotFoundException} if the specified maze file is not found.
     * </p>
     *
     * @param filename The path to the maze file to be loaded.
     * @return A 2D character array representing the loaded maze.
     * @throws MazeMalformedException     If the maze data is not correctly formatted.
     * @throws MazeSizeMissmatchException If the maze dimensions do not match the provided size.
     * @throws IllegalArgumentException   For other validation errors.
     * @throws FileNotFoundException      If the maze file is not found.
     */

    @Override
    public char[][] load(String filename) throws MazeMalformedException, MazeSizeMissmatchException,
            IllegalArgumentException, IOException {
        // Get maze dimensions from first line of the file.
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String dimensionsLine = reader.readLine();
            if (dimensionsLine == null) {
                throw new MazeMalformedException("No dimensions given.");
            }

            String[] dimensionsParsed = dimensionsLine.split(" ");
            if (dimensionsParsed.length != 2) {
                throw new MazeMalformedException("Dimensions not provided in appropriate format.");
            }

            int expectedRows = Integer.parseInt(dimensionsParsed[0]);
            int expectedColumns = Integer.parseInt(dimensionsParsed[1]);

            // Create 2D array that maps the given maze with appropriate dimensions.
            char[][] mazeMap = new char[expectedRows][expectedColumns];
            int currentRow = 0;
            int currentCol = 0;
            boolean hasStartPoint = false;
            boolean hasEndPoint = false;

            int dataIn = reader.read();
            while (dataIn != -1) {
                char castedChar = (char) dataIn;

                // Do not add new line characters to the array.
                // \n indicates the end of a row.
                if (castedChar == '\n') {
                    currentCol = 0;
                    currentRow++;
                    dataIn = reader.read();
                    continue;
                }
                switch (castedChar) {
                    case '#', ' ', '.' -> {}
                    case 'S' -> {
                        if (hasStartPoint) { // Throws error if there already exists a start point.
                            throw new MazeMalformedException("More than one start point.");
                        }
                        hasStartPoint = true;
                    }
                    case 'E' -> {
                        if (hasEndPoint) { // Throws error if there already exists an end point.
                            throw new MazeMalformedException("More than one end point.");
                        }
                        hasEndPoint = true;
                    }
                    default -> throw new MazeMalformedException("Invalid character in maze.");
                }

                if (currentRow >= expectedRows || currentCol >= expectedColumns) {
                    throw new MazeSizeMissmatchException
                            ("Specified dimensions incongruent to provided map.");
                }

                mazeMap[currentRow][currentCol] = castedChar;
                currentCol++;
                dataIn = reader.read();
            }

            // Maze must have a single start and end point.
            if (!hasStartPoint || !hasEndPoint) {
                throw new MazeMalformedException("No start point or end point provided.");
            }

            reader.close();

            return mazeMap;

        } catch (FileNotFoundException e) {
            // Handle the FileNotFoundException here, or rethrow it if you want the caller to handle it.
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
}
