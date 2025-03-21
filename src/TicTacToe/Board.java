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

    // Deep copy for grid
    grid = new int[size][size];
    for (int i = 0; i < size; i++) {
      grid[i] = Arrays.copyOf(copyBoard.grid[i], size);
    }

    // Deep copy for winnerCell
    winnerCell = new boolean[size][size];
    for (int i = 0; i < size; i++) {
      winnerCell[i] = Arrays.copyOf(copyBoard.winnerCell[i], size);
    }

    xTurn = copyBoard.xTurn;
    state = copyBoard.state;
  }


  public int evaluate(int depth) {
    if (state.equals("X wins")) {
      return 10 - depth; // Prefer quicker wins for X
    }
    if (state.equals("O wins")) {
      return depth - 10; // Prefer quicker wins for O
    }
    if (state.equals("Draw")) {
      return 0;
    }

    int score = 0;

    // Check rows and columns
    for (int i = 0; i < size; i++) {
      score += evaluateLine(grid[i]);             // Row
      score += evaluateLine(getColumn(i));        // Column
    }

    // Check diagonals
    score += evaluateLine(getPrimaryDiagonal());     // Top-left to bottom-right
    score += evaluateLine(getSecondaryDiagonal());   // Top-right to bottom-left

    return score;
  }

  private int evaluateLine(int[] line) {
    int xCount = 0;
    int oCount = 0;

    for (int cell : line) {
      if (cell == X) xCount++;
      if (cell == O) oCount++;
    }

    if (xCount > 0 && oCount > 0) return 0; // Mixed lines have no advantage
    
    // Prefer strong threats, prioritize X since it's maximizing
    if (xCount > 0) return (int) Math.pow(10, xCount); 
    if (oCount > 0) return -(int) Math.pow(10, oCount); 

    return 0; // Empty line
  }

  private int[] getColumn(int col) {
    int[] column = new int[size];

    for (int i = 0; i < size; i++) {
      column[i] = grid[i][col];
    }

    return column;
  }

  private int[] getPrimaryDiagonal() {
    int[] diagonal = new int[size];

    for (int i = 0; i < size; i++) {
      diagonal[i] = grid[i][i];
    }

    return diagonal;
  }

  private int[] getSecondaryDiagonal() {
    int[] diagonal = new int[size];

    for (int i = 0; i < size; i++) {
      diagonal[i] = grid[i][size - 1 - i];
    }

    return diagonal;
  }


  public void move(int row, int col) {
    if (!state.equals("ongoing")) return;
    if (grid[row][col] != EMPTY) return;

    grid[row][col] = xTurn ? X : O;

    if (isHorizontalWin() || isVerticalWin() || isDiagonalWin()) {
      state = xTurn ? "X wins" : "O wins";
    } else if (isDraw()) {
      state = "Draw";
    } else {
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
    if (!state.equals("ongoing") && state != "Draw") return false;

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
