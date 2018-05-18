import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader implements Runnable {
    Data data;
    ReentrantReadWriteLock rwl;

    public Reader(Data data, ReentrantReadWriteLock rwl) {
        this.data = data;
        this.rwl = rwl;
    }

    @Override
    public void run() {
        try {
            rwl.readLock().lock();
            System.out.println("Read: " + data.getCurrentVegetable());
            Thread.sleep(2000);
            System.out.println("End read.");
            rwl.readLock().unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
