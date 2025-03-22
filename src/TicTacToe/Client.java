package TicTacToe;
import java.io.*;
import java.net.*;

public class Client {
    private static PrintWriter out;

    public Client(String ip) {
        try (Socket socket = new Socket(ip, 12345)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start listening thread
            new Thread(() -> receiveMessages(in)).start();

            Thread.sleep(500);
            // Example: Send a test message
            sendMessage("Hello from Client!");

            while (true) {
                Thread.sleep(1000);  // Prevents CPU overuse
            }
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
                System.out.println("Received from server: " + message);
            }
        } catch (IOException e) {
            System.out.println("Connection closed.");
        }
    }
}
