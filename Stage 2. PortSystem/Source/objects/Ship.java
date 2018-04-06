package objects;

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
        progress = 0;
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
        progress = 0;
    }

    ShipPort checkShipPort(Cargo cargo) {
        Integer count;
        //List<ShipPort> shipPortsList = new ArrayList<>(this.shipPorts.values());
        for (ShipPort shipPort : shipPorts.values()) {
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
            do {
                Iterator<Cargo> cargoIterator = shipCargo.iterator();
                while (cargoIterator.hasNext()) {
                    synchronized (Ship.class) {
                        Cargo cargo = cargoIterator.next();
                       // System.out.println(getNameShip());
                        //System.out.println("Cargo cargo = cargoIterator.next();");
                        if ((shipPort = checkShipPort(cargo)) != null) {
                            if (cargo.getParameters().getCount() <= 0) continue;
                            currentCargo = cargo;
                            //System.out.println("currentCargo = cargo;");
                            shipPort.getPortEntrance().put(this);
                           // System.out.println(getNameShip() + " shipPort.getPortEntrance().put(this);");
                            phaser.register();
                            //System.out.println(getNameShip() + " phaser.register(); + await");
                            phaser.arriveAndAwaitAdvance();
                        } else {
                            continue;
                        }
                    }
                    phaser.register();
                   // System.out.println(getNameShip() + "phaser.register(); + await");
                    phaser.arriveAndAwaitAdvance();
                    cargoIterator.remove();
                }

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