import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true); // Enabled autoFlush
                toClient.println("hello from server new arpit");
                toClient.close();
                clientSocket.close(); // Ensure socket is closed properly
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        try {
            ServerSocket serverSocket = new ServerSocket(port); // Fixed missing port parameter
            serverSocket.setSoTimeout(10000);
            System.out.println("server is listening on port " + port);
            while (true) {
                Socket acceptSocket = serverSocket.accept();
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptSocket));
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
