package ThreadPool;

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
                            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true); // AutoFlush enabled
                            BufferedReader fromSocket = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()))) {

                        toSocket.println("hello from client"); // Sending request
                        String line = fromSocket.readLine(); // Receiving response
                        System.out.println("response from server: " + line);
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

        Client client=new Client();
        long start=System.currentTimeMillis();
        for(int i=0;i<100;i++){
            try {
                Thread thread=new Thread(client.getRunnable());
                thread.start();
            } catch (Exception e) {
               return;
            }
        }
       long end=System.currentTimeMillis();
       System.out.println("total time taken "+(end-start)+" ms");
        

    }
    
}
