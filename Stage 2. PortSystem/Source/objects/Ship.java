package objects;

import java.util.Observable;
import java.util.Random;

public class Ship extends Observable implements Runnable {

    private Thread thisThread;
    private String nameShip;
    private String cargo;
    private int weight;
    private float progress;
    private ShipStatus status;

    public Ship(String nameShip, String cargo, int weight) {

        this.nameShip = nameShip;
        this.cargo = cargo;
        this.weight = weight;
        status = ShipStatus.ON_WAY;

        progress = 0.0f;
        thisThread = new Thread(this);
        thisThread.start();
    }

    @Override
    public void run() {
        Random r = new Random();
        int count = 0;
        while (count < weight) {
            int random = Math.abs(r.nextInt() % 10);
            count += random;
            if (count > weight) {
                count = weight;
            }
            progress = ((float) count / weight) * 100;
            setChanged();
            notifyObservers();
            try {
                thisThread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getNameShip() {
        return this.nameShip;
    }

    public String getCargo() {
        return cargo;
    }

    public int getWeight() {
        return weight;
    }

    public float getProgress() {
        return progress;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }
}
