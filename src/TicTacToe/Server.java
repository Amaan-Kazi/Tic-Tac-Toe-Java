package TicTacToe;
import java.io.*;
import java.net.*;

public class Server {
    private static PrintWriter out;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Waiting for opponent...");

            Socket socket = serverSocket.accept();
            System.out.println("Opponent connected: " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start listening thread
            new Thread(() -> receiveMessages(in)).start();

            Thread.sleep(500);
            // Example: Send a test message
            sendMessage("Hello from Server!");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Function to send a message
    public static void sendMessage(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    // Function to handle incoming messages
    public static void receiveMessages(BufferedReader in) {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from client: " + message);
            }
        } catch (IOException e) {
            System.out.println("Connection closed.");
        }
    }
}
