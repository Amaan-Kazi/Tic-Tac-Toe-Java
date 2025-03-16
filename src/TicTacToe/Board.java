package TicTacToe;
import java.util.ArrayList;

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
