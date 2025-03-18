package TicTacToe;
import java.util.Arrays;

public class Game {
  public Board board = new Board(3);
  public Board[] moves = new Board[1];

  public Game() {
    moves[0] = new Board(3);
  }

  public void move(int row, int col) {
    board.move(row, col);

    moves = Arrays.copyOf(moves, moves.length + 1);
    moves[moves.length - 1] = new Board(board);
  }
}
