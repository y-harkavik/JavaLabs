package objects;

import GUI.PortSystemGUIController;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Pier implements Runnable {
    private Ship currentShip;
    private PortSystemGUIController controller;
    private BlockingQueue<Ship> queueOfShips;
    private List<Ship> processingShips;

    Pier(BlockingQueue<Ship> queue, PortSystemGUIController controller, List<Ship> processingShips) {
        this.controller = controller;
        this.queueOfShips = queue;
        this.processingShips = processingShips;
    }

    public Ship getCurrentShip() {
        return currentShip;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Ship ship = queueOfShips.take();
                currentShip = ship;
                processingShips.add(ship);
                controller.repaintTable();
                if (ship.getCurrentCargo().getOperation() == Operation.LOADNIG) {
                    ship.setStatus(ShipStatus.LOADING);
                    ship.loading();
                } else {
                    ship.setStatus(ShipStatus.UNLOADING);
                    ship.unloading();
                }
                ship.setStatus(ShipStatus.ON_WAY);
                processingShips.remove(ship);
                controller.getMainWindow().logTextArea.append(ship.getNameShip() + " " + ship.getCurrentCargo().getOperation() + " " + ship.getCurrentCargo().getParameters().getTypeOfProduct().getType() + "\n");
                currentShip = null;
                ship.getPhaser().arriveAndDeregister();
                controller.repaintTable();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
