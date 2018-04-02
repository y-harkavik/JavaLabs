package system;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ABQApp
{
    public static void main(String[] args)
    {
        BlockingQueue<String> drop = new ArrayBlockingQueue(1, true);
        synchronized (drop) {
            (new Thread(new Producer(drop))).start();
            (new Thread(new Consumer(drop))).start();
        }
    }
}