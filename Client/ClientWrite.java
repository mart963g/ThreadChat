package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWrite implements Runnable
{
    private Socket socket;
    private String username;

    public ClientWrite(Socket socket, String username)
    {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public void run()
    {
        String message = "";
        Scanner userEntry = new Scanner(System.in);
        PrintWriter output;
        try
        {
            output = new PrintWriter(socket.getOutputStream(),true);
            System.out.print("Enter message ('QUIT' to exit): \n");
            while (!message.equals("QUIT"))
            {
                message = userEntry.nextLine();
                if (message.equals("QUIT"))
                {
                    output.println("QUIT");
                }
                else
                {
                    output.println("DATA " + username + ": " + message);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //Thread.currentThread().interrupt();
    }
}
