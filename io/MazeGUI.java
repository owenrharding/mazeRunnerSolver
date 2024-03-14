package io;

import exceptions.MazeUnsolvableException;
import mazeComponents.MazeComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeGUI extends JFrame implements KeyListener {
    private final Maze maze;

    /**
     * Graphical User Interface for displaying and interacting with the maze. Is a form of JFrame
     * and implements KeyListener to get keyboard directional input.
     *
     * @param maze The maze to be displayed and navigated. Instance of Maze class.
     */
    public MazeGUI(Maze maze) {
        this.maze = maze;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setSize(800, 800);
        this.setLayout(new GridLayout(maze.getDimensions()[0], maze.getDimensions()[1]));
    }

    /**
     * Overrides original KeyListener function.
     * Receives and actions keyboard input.
     * This function moves the player corresponding to user input, terminating the program if the
     * maze has been solved. If it hasn't been solved and the given input corresponds to a valid
     * move, the GUI should be redrawn with the maze and player's updated state.
     *
     * @param keyInput the keyboard event to be processed.
     */
    @Override
    public void keyPressed(KeyEvent keyInput) {
        try {
            maze.playMaze(keyInput.getKeyChar());
        } catch (MazeUnsolvableException e) {
            throw new RuntimeException(e);
        }
        if (maze.hasBeenSolved()) {
            this.dispose();
        }
        // Remove old content then redraw.
        this.getContentPane().removeAll();
        redraw();
        this.revalidate();
        this.repaint();
    }

    // Necessary functions to implement KeyListener interface.
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    /**
     * Draws a visual representation of the maze in this JFrame.
     * As player's position does not directly influence the contents of MazeComponent array, the
     * player must be drawn over the top.
     *
     * @throws IllegalArgumentException If a maze component is null.
     */
    public void redraw() throws IllegalArgumentException {
        for (int row = 0; row < maze.getDimensions()[0]; row++) {
            for (int col = 0; col < maze.getDimensions()[1]; col++) {
                Color colour;
                if (row == maze.getPlayer().getPlayerRow()
                        && col == maze.getPlayer().getPlayerCol()) {
                    colour = maze.getPlayer().getGuiColour();
                } else {
                    MazeComponent component = maze.getComponentAt(row, col);
                    if (component == null) {
                        throw new IllegalArgumentException("Maze component is null.");
                    }
                    colour = component.getGuiColour();
                }
                JPanel panel = new JPanel();
                panel.setBackground(colour);
                this.add(panel);
            }
        }
    }
}