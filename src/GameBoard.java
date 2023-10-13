import java.util.List;
import java.util.ArrayList;

public class GameBoard {
    private char[][] board;

    private List<Integer> playerPositions1;
    private List<Integer> playerPositions2;

    // Konstruktor
    public GameBoard(List<Integer> playerPositions1, List<Integer> playerPositions2) {
        this.playerPositions1 = playerPositions1;
        this.playerPositions2 = playerPositions2;
    }
    //2D array för spelplan
    public GameBoard() {
        board = new char[][]{
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };

        playerPositions1 = new ArrayList<>();
        playerPositions2 = new ArrayList<>();

    }
    // Metod för spelplan
    public void printBoard() {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public void placePiece(int pos, String user) {
        char symbol = ' ';

        if (user.equals("Player 1")) {
            symbol = 'X';
            playerPositions1.add(pos);
        } else if (user.equals("Player 2") || user.equals("Computer")) {
            symbol = 'O';
            playerPositions2.add(pos);
        }

        // Omvandla positionen till rätt koordinater på spelplanen.
        switch (pos) {
            case 1:
                board[0][0] = symbol;
                break;
            case 2:
                board[0][2] = symbol;
                break;
            case 3:
                board[0][4] = symbol;
                break;
            case 4:
                board[2][0] = symbol;
                break;
            case 5:
                board[2][2] = symbol;
                break;
            case 6:
                board[2][4] = symbol;
                break;
            case 7:
                board[4][0] = symbol;
                break;
            case 8:
                board[4][2] = symbol;
                break;
            case 9:
                board[4][4] = symbol;
                break;
            default:
                break;
        }
    }
    public void clearBoard() {
        board = new char[][]{
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };
    }
}

