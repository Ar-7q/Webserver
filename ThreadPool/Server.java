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

    public void handleClient(Socket clienSocket){
        try(PrintWriter toSocket=new PrintWriter(clienSocket.getOutputStream(), true)){
            toSocket.println("Hello from Server Astomishing"+clienSocket.getInetAddress());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolsize = 10;
        Server server = new Server(poolsize);

        try {
            ServerSocket serverSocket=new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("server is listening on port"+port);

            while (true) {
                Socket clienSocket=serverSocket.accept();

                server.threadPool.execute(()->server.handleClient(clienSocket));
                
            }
        } catch (IOException e) {e.printStackTrace();
           
        }
        finally{
            
        }

    }
}
