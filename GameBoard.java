package sudoku;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JPanel {
    // Name-constants for the game board properties
    public static final int GRID_SIZE = 9;    // Size of the board
    public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
    // Name-constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH = CELL_SIZE * GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;
    // Board width/height in pixels
    // The game board composes of 9x9 "Customized" JTextFields,
    static Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    // It also contains a Puzzle
    private Puzzle puzzle = new Puzzle();

    //cell to share reference to SudokuMain class to show hint
    static Cell sCell = null;
    // Constructor
    public GameBoard() {
        super.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));  // JPanel
        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }
        }
        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        CellInputListener listener = new CellInputListener();

        // [TODO 4] Every editable cell adds this common listener
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Initialize the puzzle number, status, background/foreground color,
     * of all the cells from puzzle[][] and isRevealed[][].
     * Call to start a new game.
     */
    public void init(int num) {
        // Get a new puzzle according to the level
        puzzle.newPuzzle(num);
        // Based on the puzzle, initialize all the cells.
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col].init(puzzle.numbers[row][col], puzzle.isShown[row][col]);
            }
        }
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of NO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.NO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS)
                    return false;
            }
        }
        return true;
    }

    // [TODO 2] Define a Listener Inner Class
    // [TODO 2] Define a Listener Inner Class
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
    // Get the JTextField that triggers this event
            Cell sourceCell = (Cell) e.getSource();
            sCell = sourceCell;
    // For debugging
            System.out.println("You entered " + sourceCell.getText());

            /*
             * [TODO 5]
             * 1. Get the input String via sourceCell.getText()
             * 2. Convert the String to int via Integer.parseInt().
             * 3. Assume that the solution is unique.
             *
            Compare the input number with the number in sourceCell.number.
                    * Set its status via sourceCell.status = CellStatus.CORRECT_GUESS (or WRONG_GUESS)
                    * and sourceCell.paint() to update the display.
                    *
             *
             */
            String inputString = sourceCell.getText();
            int convertedString = Integer.parseInt(inputString);
            if(sourceCell.number ==  convertedString){
                sourceCell.status = CellStatus.CORRECT_GUESS;
            }else{
                sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint();
            SudokuMain.statusLabel.setText("Number of Boxes left: "+ (--Puzzle.guess));
            sourceCell.setEditable(false);
            /*
             * [TODO 6][Later] Check if the player has solved the puzzle after this move, * by call isSolved(). Put up a congratulation JOptionPane, if so.
             */
            if(isSolved()){
                JOptionPane.showMessageDialog(null, "Congratulation!");
            }

        }
    }
}