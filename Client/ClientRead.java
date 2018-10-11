package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientRead implements Runnable
{
    private Socket socket;

    public ClientRead(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        String message;
        Scanner input = null;
        try
        {
            input = new Scanner(socket.getInputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            while(!Thread.currentThread().isInterrupted())
            {
                message = input.nextLine();

                switch (message.substring(0,4))
                {
                    case "DATA":
                        System.out.println(message.substring(5));
                        break;
                    case "J_ER":
                        System.out.println("Error code " + message.substring(5));
                        break;
                    case "LIST":
                        //Kan laves med kommaer hvis det hjælper til overblik
                        System.out.println("Active users: " + message.substring(5));
                        break;
                }
            }
        }
        catch (NoSuchElementException NSEe)
        {
        }
    }
}
