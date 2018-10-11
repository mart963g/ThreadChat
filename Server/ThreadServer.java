package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadServer
{
    public static List<Socket> clients = new ArrayList<>();
    public static Map<String, Boolean> userNames = new HashMap<>();
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException
    {
        ActiveUsers killerTask = new ActiveUsers();
        Timer activeUsers = new Timer(true);
        activeUsers.scheduleAtFixedRate(killerTask,0, 60*1000);

        try
        {
            serverSocket = new ServerSocket(PORT);
            System.out.println(serverSocket.getInetAddress().getLocalHost().getHostAddress().trim());
        }
        catch (IOException ioEx)
        {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        while(true)
        {
            //Wait for client…
            Socket client = serverSocket.accept();
            System.out.println("\nNew client accepted.\n");

            //Add client to threadPool (which executes thread)
            ClientHandler handler = new ClientHandler(client);
            executor.submit(handler);
        }
    }
}
