package Server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class ActiveUsers extends TimerTask
{
    @Override
    public void run()
    {
        List<String> usernames = new ArrayList<>();
        usernames.addAll(ThreadServer.userNames.keySet());
        for (Map.Entry<String, Boolean> e: ThreadServer.userNames.entrySet())
        {
            System.out.println(e.getKey() + " is active: " + e.getValue());
            if (!e.getValue())
            {
                ThreadServer.userNames.remove(e.getKey());
            }
        }
        if (!ThreadServer.userNames.keySet().containsAll(usernames))
        {
            ClientHandler.broadcast(ClientHandler.getUserNames());
        }

        for (Map.Entry<String, Boolean> e: ThreadServer.userNames.entrySet())
        {
            e.setValue(false);
        }
    }
}
