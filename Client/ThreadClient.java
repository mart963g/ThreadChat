package Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadClient
{
    private static InetAddress host;
    private static final int PORT = 1234;
    private static String username;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException, InterruptedException
    {
        try
        {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException uhEx)
        {
            System.out.println("\nHost ID not found!\n");
            System.exit(1);
        }
        //Connects to server
        Socket socket = new Socket(host, PORT);

        login(socket);
        IMAV alive = new IMAV(out);
        Timer imav = new Timer(true);
        imav.scheduleAtFixedRate(alive, 0, 60*1000);
        sendMessages(socket);
    }

    private static void sendMessages(Socket socket) throws IOException, InterruptedException
    {

        Thread clientIn = new Thread(new ClientRead(socket));
        Thread clientOut = new Thread(new ClientWrite(socket, username));
        clientIn.start();
        clientOut.start();

        while (clientOut.isAlive())
        {
        }
        System.out.println("You have quit!");
        clientIn.interrupt();
    }

    private static void login(Socket socket)
    {
        try
        {
            String answer;
            do
            {
                System.out.println("Enter desired username: ");
                Scanner userEntry = new Scanner(System.in);
                username = userEntry.nextLine();

                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("JOIN " + username + "," + host.getHostAddress() + ":" + PORT);
                Scanner scanner = new Scanner(socket.getInputStream());
                answer = scanner.nextLine();

                if (answer.substring(0,4).equals("J_ER"))
                {
                    System.out.println("Error " + answer.substring(5));
                    System.out.println();
                }
            }
            while (!answer.equals("J_OK"));
            System.out.println("Welcome " + username + " - You have successfully joined the chat!");

        } catch (IOException e)
        {
            System.err.println("J_ER 3: Failed_to_connect_to_server");
        }
    }
}