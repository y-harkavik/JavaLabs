package objects.Buildings.Port;

import GUI.MainWindow.PortSystemGUIController;
import objects.Product.Characteristics.Operation;
import objects.Transport.Marine.Ship;
import objects.Transport.Status.ShipStatus;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class PortPier implements Runnable {
    private Ship currentShip;
    private PortSystemGUIController controller;
    private BlockingQueue<Ship> queueOfShips;
    private List<Ship> processingShips;

    public PortPier(BlockingQueue<Ship> queue, PortSystemGUIController controller, List<Ship> processingShips) {
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
                currentShip = queueOfShips.take();

                if (currentShip.getCurrentCargo().getOperation() == Operation.LOADNIG) {
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
                currentShip.setStatus(ShipStatus.ON_WAY);
                controller.getMainWindow().logTextArea.append(currentShip.getNameShip() + " закончил операцию" + "\n");
                controller.repaintTables();
                currentShip.getPhaser().arriveAndDeregister();
                currentShip = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
