package ThreadPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ExecutorService threadPool;

    public Server(int poolsize) {
        this.threadPool = Executors.newFixedThreadPool(poolsize);
    }

    public void handleClient(Socket clientSocket) { // Fixed variable name
        try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            toSocket.println("Hello from Server Astonishing " + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close(); // Ensuring client socket is closed properly
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolsize = 10;
        Server server = new Server(poolsize);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("server is listening on port " + port); // Fixed spacing

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Fixed variable name
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
