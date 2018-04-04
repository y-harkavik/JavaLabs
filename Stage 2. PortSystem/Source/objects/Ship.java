

package objects;

import java.util.List;
import java.util.Observable;
import java.util.Random;

public class Ship extends Observable implements Runnable {
    private String nameShip;
    /*private String cargo;
    private int weight;*/
    private float progress;
    private ShipStatus status;
    private List<ShipPort> shipPorts;
    private Cargo shipCargo;

    public Ship(String nameShip, /*String cargo, int weight,*/ Cargo shipCargo, List<ShipPort> shipPorts) {
        this.shipPorts = shipPorts;
        this.nameShip = nameShip;
        /*this.cargo = cargo;
        this.weight = weight;*/
        this.shipCargo = this.shipCargo;
        status = ShipStatus.ON_WAY;

        progress = 0.0f;
    }
    public void unloading() {
        Random r = new Random();
        long count = shipCargo.getCount();
        while (count > 0) {
            int random = Math.abs(r.nextInt() % 10);
            count += random;
            if (count > shipCargo.getCount()) {
                count = shipCargo.getCount();
            }
            progress = ((float) count / shipCargo.getCount()) * 100;
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loading() {
        Random r = new Random();
        long count = 0;
        while (count < shipCargo.getCount()) {
            int random = Math.abs(r.nextInt() % 10);
            count += random;
            if (count > shipCargo.getCount()) {
                count = shipCargo.getCount();
            }
            progress = ((float) count / shipCargo.getCount()) * 100;
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {

            shipPorts.get(0).getPortEntrance().put(this);
            System.out.println("Ship");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getNameShip() {
        return this.nameShip;
    }

    /*public String getCargo() {
        return cargo;
    }

    public int getWeight() {
        return weight;
    }*/

    public float getProgress() {

        return progress;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public Cargo getShipCargo() {
        return shipCargo;
    }

    public void setShipCargo(Cargo shipCargo) {
        this.shipCargo = shipCargo;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }
}
