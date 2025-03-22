package TicTacToe;
import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class Server {
    private static PrintWriter out;
    private Consumer<String> callback;

    public Server(Consumer<String> callback) {
        this.callback = callback;

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server Started");
            System.out.println("Waiting for connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Client Connected: " + socket.getInetAddress() + "\n");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start listening thread
            new Thread(() -> receiveMessages(in)).start();
            Thread.sleep(500);

            // Example: Send a test message
            sendMessage("Connection Succesfull");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Function to send a message
    public void sendMessage(String message) {
        if (out != null) {
            System.out.println("Server: " + message);
            out.println(message);
            out.flush();
        }
    }

    // Function to handle incoming messages
    public void receiveMessages(BufferedReader in) {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Client: " + message + "\n");
                callback.accept(message);
            }
        } catch (IOException e) {
            System.out.println("Connection closed.");
        }
    }
}
