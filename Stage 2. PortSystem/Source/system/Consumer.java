package system;

import java.util.concurrent.BlockingQueue;


class Consumer implements Runnable
{
    private BlockingQueue<String> drop;

    public Consumer(BlockingQueue<String> d) { this.drop = d; }

    public void run()
    {
        try
        {
            String msg = null;
            while (!((msg = drop.take()).equals("DONE")))
                System.out.println(msg);
        }
        catch (InterruptedException intEx)
        {
            System.out.println("Interrupted! " +
                    "Last one out, turn out the lights!");
        }
    }
}