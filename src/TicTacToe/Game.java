package TicTacToe;
import java.util.Arrays;

public class Game {
  public Board board = new Board(3);
  public Board[] moves = new Board[1];
  public int moveNo = 0;

  public Game() {
    board = new Board(3);
    moves = new Board[1];
    moves[0] = new Board(3);
    moveNo = 0;
  }

  public void move(int row, int col) {
    board.move(row, col);

    // trim the moves so they can no longer be redone
    moves = Arrays.copyOf(moves, moveNo + 1);

    moves = Arrays.copyOf(moves, moves.length + 1);
    moves[moves.length - 1] = new Board(board);

    moveNo++;
  }


  public void undo() {
    if (moveNo > 0) {
      moveNo--;
      board = new Board(moves[moveNo]);
    }
  }

  public void redo() {
    if (moves.length > moveNo + 1) {
      moveNo++;
      board = new Board(moves[moveNo]);
    }
  }

  public void reset() {
    board = new Board(3);
    moves = null;

    moves = new Board[1];
    moves[0] = new Board(3);

    moveNo = 0;
  }
}
