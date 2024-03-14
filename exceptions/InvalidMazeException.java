package exceptions;

public class InvalidMazeException extends Exception {
    public InvalidMazeException() {
        super("The provided maze is invalid.");
    }
}