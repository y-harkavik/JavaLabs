package objects;

import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Ship extends Observable implements Runnable {
    private String nameShip;
    private float progress;
    private ShipStatus status;
    private List<ShipPort> shipPorts;
    private Map<Operation,Cargo> shipCargo;
    private static final int speed = 10;

    public Ship(String nameShip, Map<Operation,Cargo> shipCargo, List<ShipPort> shipPorts) {
        this.shipPorts = shipPorts;
        this.nameShip = nameShip;
        this.shipCargo = shipCargo;
        status = ShipStatus.ON_WAY;
        progress = 0.0f;
    }
    public void unloading(int needUnload) {

        while (count > 0) {
            count -= speed;
            if (count <= 0) {
                shipCargo.setCount(0);
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

    public void loading(int needLoad) {
        long count = 0;
        int shipCount = shipCargo.getCount();
        while (count < shipCount) {
            count += speed;
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
            for()
            shipPorts.get(0).getPortEntrance().put(this);
            System.out.println("Ship");
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
}
