import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try {
                    InetAddress address = InetAddress.getByName("localhost");
                    try (Socket socket = new Socket(address, port); // Auto-close socket
                         PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true); 
                         BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                        toSocket.println("hello from client"); // Sending request
                        String line = fromSocket.readLine(); // Receiving response
                        System.out.println("response from server: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        long startTime = System.currentTimeMillis(); // Start time tracking

        for (int i = 0; i < 100000; i++) { // 100 clients
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis(); // End time tracking (after starting all threads)
        System.out.println("Total time taken: " + (endTime - startTime) + " ms");
    }
}
