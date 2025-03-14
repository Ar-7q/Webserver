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
                    Socket socket = new Socket(address, port);
                    try (
                            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true); // Enabled autoFlush
                            BufferedReader fromSocket = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()))) {

                        toSocket.println("hello from the client"); // Fixed typo
                        String line = fromSocket.readLine();
                        System.out.println("response from the socket " + line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        socket.close(); // Ensure socket is closed properly
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();
        long startTime= System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            } catch (Exception e) {
                return;
            }
        }
        long endTime=System.currentTimeMillis();
        System.out.println("time taken in ms "+(endTime-startTime));
    }
}
