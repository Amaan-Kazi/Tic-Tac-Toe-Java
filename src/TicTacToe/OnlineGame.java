package TicTacToe;
import java.util.Arrays;
import java.util.function.Consumer;

public class OnlineGame {
  public Board board = new Board(3);
  public Board[] moves = new Board[1];
  public int moveNo = 0;

  public int nodes = 0;

  public String device;
  private Server server;
  private Client client;

  private Consumer<String> callback;

  public OnlineGame(int size, Consumer<String> callback, String device, String ip) {
    board = new Board(size);
    moves = new Board[1];
    moves[0] = new Board(size);
    moveNo = 0;

    this.device = device;
    this.callback = callback;

    if (device.equals("Server")) {
      server = new Server(this::messageRecieved);
    }
    else {
      client = new Client(ip, this::messageRecieved);
    }
  }

  public void messageRecieved(String message) {
    if (message.startsWith("MOVE")) {
      String[] parts = message.split(" ");
      if (parts.length != 3) System.out.println(message + " [Invalid number of arguments]");

      try {
        int row = Integer.parseInt(parts[1]);
        int col = Integer.parseInt(parts[2]);
        move(row, col, device.equals("Server") ? "Client" : "Server");
      }
      catch (Exception e) {
        System.out.println("Non numeric move arguments");
      }
    }
    else if (message.equals("UNDO")) undo(1, "Server");
    else if (message.equals("REDO")) redo(1, "Server");
    else if (message.equals("RESET")) reset("Server");
  }

  public boolean move(int row, int col, String movingDevice) {
    if ( board.xTurn && movingDevice.equals("Client")) return false;
    if (!board.xTurn && movingDevice.equals("Server")) return false;
    boolean success = board.move(row, col);

    if (success) {
      // trim the moves so they can no longer be redone
      moves = Arrays.copyOf(moves, moveNo + 1);

      moves = Arrays.copyOf(moves, moves.length + 1);
      moves[moves.length - 1] = new Board(board);

      moveNo++;
      callback.accept("");

      if (device.equals(movingDevice)) {
        if (device == "Server") server.sendMessage("MOVE " + row + " " + col);
        else                    client.sendMessage("MOVE " + row + " " + col);
      }
    }

    return success;
  }


  public void undo(int n, String undoingDevice) {
    if (!undoingDevice.equals("Server")) return;

    if (moveNo > n - 1) {
      moveNo -= n;
      board = new Board(moves[moveNo]);
    }

    if (device.equals("Server")) server.sendMessage("UNDO");
    callback.accept("");
  }

  public void redo(int n, String redoingDevice) {
    if (!redoingDevice.equals("Server")) return;
    
    if (moves.length > moveNo + n) {
      moveNo += n;
      board = new Board(moves[moveNo]);
    }

    if (device.equals("Server")) server.sendMessage("REDO");
    callback.accept("");
  }

  public void reset(String resettingDevice) {
    if (!resettingDevice.equals("Server")) return;
    
    board = new Board(3);
    moves = null;

    moves = new Board[1];
    moves[0] = new Board(3);

    moveNo = 0;

    if (device.equals("Server")) server.sendMessage("RESET");
    callback.accept("");
  }
}
