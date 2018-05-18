import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Writer implements Runnable {
    Data data;
    ReentrantReadWriteLock rwl;

    public Writer(Data data, ReentrantReadWriteLock rwl) {
        this.data = data;
        this.rwl = rwl;
    }

    @Override
    public void run() {
        try {
            rwl.writeLock().lock();
            data.setCurrentVegetable();
            System.out.println("Write: " + data.getCurrentVegetable());
            Thread.sleep(2000);
            System.out.println("End write.");
            rwl.writeLock().unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
