import UI.*;

public class Main {
  public static void main(String args[]) {
    new MainMenu();
    
    Board board = new Board(3);
    board.printBoard();
  }
}
