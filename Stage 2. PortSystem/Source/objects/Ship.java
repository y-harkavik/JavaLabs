package objects;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;

public class Ship extends Observable implements Runnable {
    private String nameShip;
    private float progress;
    private ShipStatus status;
    private List<ShipPort> shipPorts;
    private List<Cargo> shipCargo;
    private static final int speed = 50;
    private Cargo currentCargo;
    private Phaser phaser;

    public Ship(String nameShip, List<Cargo> shipCargo, List<ShipPort> shipPorts) {
        this.shipPorts = shipPorts;
        this.nameShip = nameShip;
        this.shipCargo = shipCargo;
        this.currentCargo = null;
        this.phaser = new Phaser(1);
        status = ShipStatus.ON_WAY;
        progress = 0.0f;
    }

    public void unloading() {
        int count = currentCargo.getParameters().getCount();
        while (count > 0) {
            count -= speed;
            if (count <= 0) {
                currentCargo.getParameters().setCount(0);
            }
            progress = ((float) count / currentCargo.getParameters().getCount()) * 100;
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
        int count = 0;
        int shipCount = currentCargo.getParameters().getCount();
        while (count < shipCount) {
            count += speed;
            if (count >= shipCount) {
                currentCargo.getParameters().setCount(count);
            }
            progress = ((float) count / shipCount) * 100;
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    ShipPort checkShipPort(Cargo cargo) {
        Iterator<ShipPort> shipPortIterator = shipPorts.iterator();
        Integer count;
        while (shipPortIterator.hasNext()) {
            ShipPort shipPort = shipPortIterator.next();
            Map<TypeOfProduct, Integer> products = shipPort.getPortYard().getProducts();
            if (((count = products.get(cargo.getParameters().getTypeOfProduct())) != null) && (count >= cargo.getParameters().getCount())) {
                return shipPort;
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {
            ShipPort shipPort;
            for (Cargo cargo : shipCargo) {
                if ((shipPort = checkShipPort(cargo)) != null) {
                    if (cargo.getParameters().getCount() <= 0) continue;
                    currentCargo = cargo;
                    shipPort.getPortEntrance().put(this);
                    phaser.register();
                    phaser.arriveAndAwaitAdvance();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getNameShip() {
        return this.nameShip;
    }

    public float getProgress() {

        return progress;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public List<Cargo> getShipCargoList() {
        return shipCargo;
    }

    public void setShipCargoList(List<Cargo> shipCargo) {
        this.shipCargo = shipCargo;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }

    public Cargo getCurrentCargo() {
        return currentCargo;
    }

    public Phaser getPhaser() {
        return phaser;
    }
}