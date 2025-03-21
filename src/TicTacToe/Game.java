package TicTacToe;
import java.util.Arrays;

public class Game {
  public Board board = new Board(3);
  public Board[] moves = new Board[1];
  public int moveNo = 0;

  public int nodes = 0;

  public Game(int size) {
    board = new Board(size);
    moves = new Board[1];
    moves[0] = new Board(size);
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


  public int minimax(Board b, boolean maximize, int depth, int maxDepth) {
    nodes++;
    
    if (depth >= maxDepth || !b.state.equals("ongoing")) {
      return b.evaluate(depth);
    }
    
    if (maximize) {
      int maximum = Integer.MIN_VALUE;

      for (int i = 0; i < b.size; i++) {
        for (int j = 0; j < b.size; j++) {
          if (b.grid[i][j] == 0) {
            Board copyBoard = new Board(b);
            copyBoard.move(i, j);

            int moveScore = minimax(copyBoard, !maximize, depth + 1, maxDepth);
            if (moveScore > maximum) maximum = moveScore;
          }
        }
      }

      return maximum;
    }
    else {
      int minimum = Integer.MAX_VALUE;

      for (int i = 0; i < b.size; i++) {
        for (int j = 0; j < b.size; j++) {
          if (b.grid[i][j] == 0) {
            Board copyBoard = new Board(b);
            copyBoard.move(i, j);

            int moveScore = minimax(copyBoard, !maximize, depth + 1, maxDepth);
            if (moveScore < minimum) minimum = moveScore;
          }
        }
      }

      return minimum;
    }
  }

  public void botMove() {
    boolean maximize = board.xTurn;
    nodes = 0;

    if (!board.state.equals("ongoing")) return;
    if (board.validMoves().length == 0)          return;

    int row = 0;
    int col = 0;
    int score = maximize ? -5 : 5;

    String scores[][] = new String[board.size][board.size];
    for (int i = 0; i < board.size; i++) {
      for (int j = 0; j < board.size; j++) {
        if      (board.grid[i][j] == 1) scores[i][j] = "X";
        else if (board.grid[i][j] == 2) scores[i][j] = "O";
        else                            scores[i][j] = "0";
      }
    }

    for (int i = 0; i < board.size; i++) {
      for (int j = 0; j < board.size; j++) {
        if (board.grid[i][j] == 0) {
          Board copyBoard = new Board(board);
          copyBoard.move(i, j);
          int moveScore = minimax(copyBoard, !maximize, 0, board.size * board.size);
          scores[i][j] = "" + moveScore;

          if (maximize) {
            if (moveScore > score) {
              score = moveScore;
              row = i;
              col = j;
            }
          }
          else {
            if (moveScore < score) {
              score = moveScore;
              row = i;
              col = j;
            }
          }
        }
      }
    }

    move(row, col);
    System.out.println("Nodes Evaluated: " + nodes);
    nodes = 0;

    for (int i = 0; i < board.size; i++) {
      for (int j = 0; j < board.size; j++) {
        if (i == row && j == col) System.out.print("*");
        System.out.print(scores[i][j]);
        if (j != board.size - 1) System.out.print("\t|\t");
      }
      System.out.print("\n");
    }
    System.out.println("");
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
