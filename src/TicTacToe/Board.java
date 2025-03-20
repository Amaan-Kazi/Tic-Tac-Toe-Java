package TicTacToe;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
  public static final int EMPTY = 0;
  public static final int X = 1;
  public static final int O = 2;

  public int[][] grid;
  public int size;

  public boolean xTurn = true;
  public String state = "ongoing";
  public boolean[][] winnerCell;
  
  public Board(int size) {
    this.size  = size;
    grid       = new int[size][size];
    winnerCell = new boolean[size][size];
  }
  public Board(Board copyBoard) {
    size = copyBoard.size;
    grid = Arrays.copyOf(copyBoard.grid, copyBoard.grid.length);
    winnerCell = Arrays.copyOf(copyBoard.winnerCell, copyBoard.winnerCell.length);
  }

  public void move(int row, int col) {
    if (state != "ongoing") return;
    if (grid[row][col] != EMPTY) return;

    grid[row][col] = xTurn ? X : O;

    boolean horizontal = isHorizontalWin();
    boolean vertical   = isVerticalWin();
    boolean diagonal   = isDiagonalWin();
    boolean draw       = isDraw();
    
    if (!(horizontal || vertical || diagonal || draw)) {
      xTurn = !xTurn;
    }
  }

  public int[][] validMoves() {
    ArrayList<int[]> moves = new ArrayList<>();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (grid[i][j] == 0) {
          moves.add(new int[]{i, j});
        }
      }
    }

    // convert ArrayList to array, int[0][] is the required format
    return moves.toArray(new int[0][]);
  }


  public boolean isDraw() {
    if (state != "ongoing" && state != "Draw") return false;

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (grid[i][j] == EMPTY) return false;
      }
    }

    state = "Draw";
    return true;
  }

  public boolean isHorizontalWin() {
    for (int row = 0; row < size; row++) {
      int first = grid[row][0];
      if (first == EMPTY) continue;

      boolean win = true;

      for (int col = 1; col < size; col++) {
        if (grid[row][col] != first) {
          win = false;
          break;
        }
      }

      if (win) {
        state = (first == X) ? "X wins" : "O wins";
        for (int col = 0; col < size; col++) winnerCell[row][col] = true;
        return true;
      }
    }

    return false;
  }

  public boolean isVerticalWin() {
    for (int col = 0; col < size; col++) {
      int first = grid[0][col];
      if (first == EMPTY) continue;

      boolean win = true;

      for (int row = 1; row < size; row++) {
        if (grid[row][col] != first) {
          win = false;
          break;
        }
      }

      if (win) {
        state = (first == X) ? "X wins" : "O wins";
        for (int row = 0; row < size; row++) winnerCell[row][col] = true;
        return true;
      }
    }

    return false;
  }

  public boolean isDiagonalWin() {
    boolean isWin = false;
    
    // Top-left to bottom-right diagonal
    int first = grid[0][0];
    if (first != EMPTY) {
      boolean win = true;

      for (int i = 1; i < size; i++) {
        if (grid[i][i] != first) {
            win = false;
            break;
        }
      }

      if (win) {
        state = (first == X) ? "X wins" : "O wins";
        for (int i = 0; i < size; i++) winnerCell[i][i] = true;
        isWin = true;
      }
    }

    // Top-right to bottom-left diagonal
    first = grid[0][size - 1];
    if (first != EMPTY) {
      boolean win = true;
      
      for (int i = 1; i < size; i++) {
        if (grid[i][size - 1 - i] != first) {
          win = false;
          break;
        }
      }
      
      if (win) {
        state = (first == X) ? "X wins" : "O wins";
        for (int i = 0; i < size; i++) winnerCell[i][size - 1 - i] = true;
        isWin = true;
      }
    }

    return isWin;
  }


  public void printBoard() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int cell = grid[i][j];

        if  (cell == EMPTY) System.out.print(" ");
        else if (cell == X) System.out.print("X");
        else if (cell == O) System.out.print("O");

        if (j < size - 1) System.out.print(" | ");
      }
      System.out.println("");
    }
  }
}
