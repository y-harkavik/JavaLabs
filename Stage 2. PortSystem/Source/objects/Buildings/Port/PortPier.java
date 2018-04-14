package objects.Buildings.Port;

import GUI.MainWindow.PortSystemGUIController;
import objects.Product.Characteristics.Operation;
import objects.Transport.Marine.Ship;
import objects.Transport.Status.ShipStatus;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Class that defines port pier.
 *
 * @author Yauheni
 * @version 1.0
 */
public class PortPier implements Runnable {
    private Ship currentShip;
    private PortSystemGUIController controller;
    private BlockingQueue<Ship> queueOfShips;
    private List<Ship> processingShips;
    private String name;

    /**
     * Creates port pier.
     *
     * @param queue           Common queue for piers.
     * @param controller      GUI controller.
     * @param processingShips Common list of processing ships.
     * @see PortSystemGUIController
     * @see Ship
     */
    public PortPier(BlockingQueue<Ship> queue, PortSystemGUIController controller, List<Ship> processingShips) {
        this.controller = controller;
        this.queueOfShips = queue;
        this.processingShips = processingShips;
    }

    /**
     * Return current ships that processing.
     *
     * @return Current ships that processing.
     */
    public Ship getCurrentShip() {
        return currentShip;
    }

    @Override
    public void run() {
        while (true) {
            try {
                currentShip = queueOfShips.take();
                if (currentShip != null) {
                    controller.getMainWindow().printAction(currentShip.getNameShip() + " - вошёл в " + name + "\n");

                    if (currentShip.getCurrentCargo().getCargoOperation() == Operation.LOADING) {
                        currentShip.setStatus(ShipStatus.LOADING);
                        processingShips.add(currentShip);
                        controller.repaintTables();
                        Thread.sleep(200);
                        currentShip.loading();
                    } else {
                        currentShip.setStatus(ShipStatus.UNLOADING);
                        processingShips.add(currentShip);
                        controller.repaintTables();
                        Thread.sleep(200);
                        currentShip.unloading();
                    }

                    processingShips.remove(currentShip);
                    controller.getMainWindow().printAction(currentShip.getNameShip() + " - отшвартовался от " + name + "\n");
                    currentShip.setStatus(ShipStatus.ON_WAY);
                    currentShip.getPhaser().arriveAndDeregister();
                    currentShip = null;
                    controller.repaintTables();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set pier name.
     *
     * @param name Pier name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
