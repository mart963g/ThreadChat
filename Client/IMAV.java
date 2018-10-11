package Client;

import java.io.PrintWriter;
import java.util.TimerTask;

public class IMAV extends TimerTask
{
    private PrintWriter out;

    public IMAV(PrintWriter out)
    {
        this.out = out;
    }

    @Override
    public void run()
    {
        out.println("IMAV");
    }
}
