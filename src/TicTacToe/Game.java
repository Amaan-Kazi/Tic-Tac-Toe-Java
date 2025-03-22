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

  public boolean move(int row, int col) {
    boolean success = board.move(row, col);

    if (success) {
      // trim the moves so they can no longer be redone
      moves = Arrays.copyOf(moves, moveNo + 1);

      moves = Arrays.copyOf(moves, moves.length + 1);
      moves[moves.length - 1] = new Board(board);

      moveNo++;
    }

    return success;
  }


  public int minimax(Board b, boolean maximize, int alpha, int beta, int depth, int maxDepth) {
    nodes++;
    
    if (depth >= maxDepth || !b.state.equals("ongoing")) {
      return b.evaluate(depth);
    }
    
    if (maximize) {
      int bestScore = Integer.MIN_VALUE;

      for (int i = 0; i < b.size; i++) {
        for (int j = 0; j < b.size; j++) {
          if (b.grid[i][j] == 0) {
            Board copyBoard = new Board(b);
            copyBoard.move(i, j);

            int moveScore = minimax(copyBoard, !maximize, alpha, beta, depth + 1, maxDepth);
            bestScore = Math.max(moveScore, bestScore);

            alpha = Math.max(alpha, moveScore);
            if (beta <= alpha) break;
          }
        }
      }

      return bestScore;
    }
    else {
      int bestScore = Integer.MAX_VALUE;

      for (int i = 0; i < b.size; i++) {
        for (int j = 0; j < b.size; j++) {
          if (b.grid[i][j] == 0) {
            Board copyBoard = new Board(b);
            copyBoard.move(i, j);

            int moveScore = minimax(copyBoard, !maximize, alpha, beta, depth + 1, maxDepth);
            bestScore = Math.min(moveScore, bestScore);

            beta = Math.min(beta, moveScore);
            if (beta <= alpha) break;
          }
        }
      }

      return bestScore;
    }
  }

  public void botMove() {
    boolean maximize = board.xTurn;
    nodes = 0;
    int[][] validMoves = board.validMoves();

    if (!board.state.equals("ongoing")) return;
    if (validMoves.length == 0)                  return;

    int row = validMoves[0][0];
    int col = validMoves[0][1];
    int score = maximize ? -5 : 5;

    String scores[][] = new String[board.size][board.size];
    for (int i = 0; i < board.size; i++) {
      for (int j = 0; j < board.size; j++) {
        if      (board.grid[i][j] == 1) scores[i][j] = "X";
        else if (board.grid[i][j] == 2) scores[i][j] = "O";
        else                            scores[i][j] = "0";
      }
    }

    int maxDepth;
    if      (board.size == 3) maxDepth = 9;
    else if (board.size == 4) maxDepth = 6;
    else if (board.size == 5) maxDepth = 4;
    else                      maxDepth = 2;

    for (int i = 0; i < board.size; i++) {
      for (int j = 0; j < board.size; j++) {
        if (board.grid[i][j] == 0) {
          Board copyBoard = new Board(board);
          copyBoard.move(i, j);
          
          int moveScore = minimax(copyBoard, !maximize, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, maxDepth);
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


  public void undo(int n) {
    if (moveNo > n - 1) {
      moveNo -= n;
      board = new Board(moves[moveNo]);
    }
  }

  public void redo(int n) {
    if (moves.length > moveNo + n) {
      moveNo += n;
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
