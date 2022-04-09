package sudoku;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import static sudoku.GameBoard.GRID_SIZE;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    // private variables
    GameBoard board = new GameBoard();

    //buttons to reset, hint, and give
    JButton btnRest = new JButton("Rest");
    JButton btnHint = new JButton("Hint");
    JButton btnGiveUp = new JButton("Give Up");

    //bottom and top containers for buttons and labels
    Box bottomBox = Box.createHorizontalBox();
    Box topBox = Box.createHorizontalBox();

    //label to represent remaining boxes
    static JLabel statusLabel = new JLabel();

    //SudokuMain's object
    static SudokuMain main;

    // Constructor
    public SudokuMain() {

        //getting input from user to select level
        int level = Integer.parseInt(JOptionPane.showInputDialog("Please choose level:\n" +
                "1 - Easy\n" +
                "2 - Intermediate\n" +
                "3 - Difficult"));
        Container cp = getContentPane();


        //listeners for buttons
        btnRest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //close the running game
                main.dispose();
                //start the new game
                main = new SudokuMain();
            }
        });

        btnHint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //show hint in the selected box
                GameBoard.sCell.setText(GameBoard.sCell.number + "");
                GameBoard.sCell.status = CellStatus.CORRECT_GUESS;
                GameBoard.sCell.paint();
            }
        });

        btnGiveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //show answers in all box
                for (int row = 0; row < GRID_SIZE; ++row) {
                    for (int col = 0; col < GRID_SIZE; ++col) {
                        if (GameBoard.cells[row][col].isEditable()) {
                            GameBoard.cells[row][col].setText(GameBoard.cells[row][col].number+"");
                            GameBoard.cells[row][col].status = CellStatus.CORRECT_GUESS;
                            GameBoard.cells[row][col].paint();

                        }
                    }
                }
                //set the remaining boxes to 0
                statusLabel.setText("Number of Boxes left: 0");

            }
        });
        //adding label in top
        topBox.add(statusLabel);

        //adding button in bottom box
        bottomBox.add(btnRest);
        bottomBox.add(btnHint);
        bottomBox.add(btnGiveUp);


        cp.setLayout(new BorderLayout());
        cp.add(board, BorderLayout.CENTER);
        //adding top box in north of the content pane
        cp.add(topBox, BorderLayout.NORTH);
        //adding bottom box in south of the content pane
        cp.add(bottomBox, BorderLayout.SOUTH);


        //controlling time and number of boxes to fill according to level chosen by user
        int time = 0;
        if (level == 1) {
            board.init(10);
            statusLabel.setText("Number of Boxes left: 10");

            time = 600000;

        } else if (level == 2) {
            board.init(15);
            statusLabel.setText("Number of Boxes left: 15");
            time = 450000;

        } else {
            board.init(20);
            statusLabel.setText("Number of Boxes left: 20");
            time = 300000;
        }



        //running the timer in background for time chosen from above
        new Timer(time, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setVisible(false);
            }
        }).start();

        pack();     // Pack the UI components, instead of setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setLocationRelativeTo(null);
        setTitle("Sudoku");
        setVisible(true);

    }

    /**
     * The entry main() entry method
     */
    public static void main(String[] args) {
        // [TODO 1] Check Swing program template on how to run the constructor
        //starting the main window
        main = new SudokuMain();
    }
}
