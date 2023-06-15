import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class TicTacToeGUI extends JFrame {
    private char[][] board;
    private char currentPlayer;
    private JButton[][] buttons;
    private Random random;

    public TicTacToeGUI(boolean vsAI) {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));

        board = new char[3][3];
        currentPlayer = 'X';
        buttons = new JButton[3][3];
        random = new Random();

        initializeBoard();
        initializeButtons(vsAI);

        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = '-';
            }
        }
    }

    private void initializeButtons(boolean vsAI) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                buttons[row][col] = button;

                int finalRow = row;
                int finalCol = col;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playMove(finalRow, finalCol);
                        button.setText(String.valueOf(board[finalRow][finalCol]));
                        button.setEnabled(false);
                        checkGameOver();
                        if (vsAI && currentPlayer == 'O') {
                            makeAIMove();
                            checkGameOver();
                        }
                    }
                });

                add(button);
            }
        }
    }

    private void playMove(int row, int col) {
        if (board[row][col] == '-') {
            board[row][col] = currentPlayer;
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private void makeAIMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '-');

        playMove(row, col);
        buttons[row][col].setText(String.valueOf(board[row][col]));
        buttons[row][col].setEnabled(false);
    }

    private void checkGameOver() {
        // Check for winning conditions or a draw
        if (checkRows() || checkColumns() || checkDiagonals() || isBoardFull()) {
            showGameOverDialog();
            initializeBoard();
            resetButtons();
        }
    }

    private boolean checkRows() {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] != '-' && board[row][0] == board[row][1] && board[row][0] == board[row][2]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int col = 0; col < 3; col++) {
            if (board[0][col] != '-' && board[0][col] == board[1][col] && board[0][col] == board[2][col]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return (board[0][0] != '-' && board[0][0] == board[1][1] && board[0][0] == board[2][2])
                || (board[0][2] != '-' && board[0][2] == board[1][1] && board[0][2] == board[2][0]);
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private void showGameOverDialog() {
        String message;
        if (checkRows() || checkColumns() || checkDiagonals()) {
            message = "Player " + ((currentPlayer == 'X') ? 'O' : 'X') + " wins!";
        } else {
            message = "It's a draw!";
        }
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(true);
                buttons[row][col].setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            boolean vsAI = askOpponentType();
            new TicTacToeGUI(vsAI);
        });
    }

    private static boolean askOpponentType() {
        int option = JOptionPane.showOptionDialog(
                null,
                "Choose opponent type:",
                "Opponent Type",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"AI", "1vs1"},
                "AI"
        );

        return option == 0;
    }
}
