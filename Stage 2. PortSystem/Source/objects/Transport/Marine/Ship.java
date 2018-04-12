package objects.Transport.Marine;


import objects.Buildings.Port.ShipPort;
import objects.Product.Cargo.Cargo;
import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;
import objects.Transport.Status.ShipStatus;

import java.util.*;
import java.util.concurrent.Phaser;

public class Ship extends Observable implements Runnable {
    private String nameShip;
    private float progress;
    private ShipStatus status;
    private Map<String, ShipPort> shipPorts;
    private List<Cargo> shipCargo;
    private static final int speed = 50;
    private Cargo currentCargo;
    private Phaser phaser;

    public Ship(String nameShip, List<Cargo> shipCargo, Map<String, ShipPort> shipPorts) {
        this.shipPorts = shipPorts;
        this.nameShip = nameShip;
        this.shipCargo = new LinkedList<>(shipCargo);
        this.currentCargo = null;
        this.phaser = new Phaser(1);
        status = ShipStatus.ON_WAY;
        progress = 0.0f;
    }

    public void unloading() {
        int count = currentCargo.getCargoParameters().getCargoCount();
        int cargoCount = count;
        while (count > 0) {
            count -= speed;
            progress = ((float) (cargoCount - count) / cargoCount) * 100;
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentCargo.getCargoParameters().setCargoCount(0);
        progress = 0;
    }

    public void loading() {
        int count = 0;
        int shipCount = currentCargo.getCargoParameters().getCargoCount();
        while (count < shipCount) {
            count += speed;
            progress = ((float) count / shipCount) * 100;
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentCargo.getCargoParameters().setCargoCount(count);
        progress = 0;
    }

    ShipPort checkShipPort(Cargo cargo) {
        Integer count;
        List<ShipPort> shipPortsList = new ArrayList<>(this.shipPorts.values());
        ShipPort minShips = null;

        boolean flag = true;

        if (cargo.getCargoOperation() == Operation.LOADING) {
            for (ShipPort shipPort : shipPortsList) {
                Map<TypeOfProduct, Integer> products = shipPort.getPortYard().getProducts();
                if (((count = products.get(cargo.getCargoParameters().getTypeOfProduct())) != null) && (count >= cargo.getCargoParameters().getCargoCount())) {
                    if (flag) {
                        minShips = shipPort;
                        flag = false;
                        continue;
                    }
                    if (minShips.getListOfProcessingShips().size() > shipPort.getListOfProcessingShips().size()) {
                        minShips = shipPort;
                    } else if (minShips.getListOfShipsInQueue().size() > shipPort.getListOfShipsInQueue().size()) {
                        minShips = shipPort;
                    }
                }
            }
        } else {
            for (ShipPort shipPort : shipPortsList) {
                Map<TypeOfProduct, Integer> products = shipPort.getPortYard().getProducts();
                if (products.containsKey(cargo.getCargoParameters().getTypeOfProduct())) {
                    if (flag) {
                        minShips = shipPort;
                        flag = false;
                        continue;
                    }
                    if (minShips.getListOfProcessingShips().size() > shipPort.getListOfProcessingShips().size()) {
                        minShips = shipPort;
                    } else if (minShips.getListOfShipsInQueue().size() > shipPort.getListOfShipsInQueue().size()) {
                        minShips = shipPort;
                    }
                }
            }
        }
        return minShips;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            ShipPort shipPort;
            do {
                Iterator<Cargo> cargoIterator = shipCargo.iterator();
                while (cargoIterator.hasNext()) {
                    synchronized (Ship.class) {
                        Cargo cargo = cargoIterator.next();
                        if ((shipPort = checkShipPort(cargo)) != null) {
                            if (cargo.getCargoParameters().getCargoCount() <= 0) continue;
                            currentCargo = cargo;
                            shipPort.getPortEntrance().put(this);
                            phaser.register();
                            phaser.arriveAndAwaitAdvance();
                        } else {
                            continue;
                        }
                    }
                    phaser.register();
                    phaser.arriveAndAwaitAdvance();
                    cargoIterator.remove();
                    Thread.sleep(1000);
                }
                Thread.sleep(2000);
            } while (!shipCargo.isEmpty());
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

    public List<Cargo> getShipCargoList() {
        return shipCargo;
    }

    public void setShipCargoList(List<Cargo> shipCargo) {
        this.shipCargo = shipCargo;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }

    public Cargo getCurrentCargo() {
        return currentCargo;
    }

    public void setCurrentCargo(Cargo currentCargo) {
        this.currentCargo = currentCargo;
    }

    public Phaser getPhaser() {
        return phaser;
    }
}