package objects.Buildings.Port;

import GUI.MainWindow.PortSystemGUIController;
import objects.Transport.Marine.Ship;
import objects.Transport.Status.ShipStatus;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Class that defines ship port.
 *
 * @author Yauheni
 * @version 1.0
 */
public class ShipPort implements Runnable {
    private final String portName;
    private Map<String, PortPier> listOfPiers;
    private BlockingQueue<Ship> queueOfShips;
    private BlockingQueue<Ship> portEntrance;
    private List<Ship> processingShips;
    private PortYard portYard;
    private PortSystemGUIController controller;

    @Override
    public void run() {
        while (true) {
            try {
                Ship ship = portEntrance.take();
                controller.getMainWindow().printAction(ship.getNameShip() + " - пришвартовался к " + portName + "\n");
                getPortYard().changeProductCount(ship.getCurrentCargo().getCargoParameters().getTypeOfProduct(), ship.getCurrentCargo().getCargoParameters().getCargoCount(), ship.getCurrentCargo().getCargoOperation());
                ship.setStatus(ShipStatus.IN_QUEUE);
                ship.getPhaser().arriveAndDeregister();
                queueOfShips.put(ship);
                controller.repaintQueueTable();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates ship port and port piers.
     *
     * @param portName   Port name.
     * @param numOfPiers Num of piers.
     * @param controller GUI controller.
     */
    public ShipPort(String portName, int numOfPiers, PortSystemGUIController controller) {
        this.portName = portName;
        this.controller = controller;

        listOfPiers = new LinkedHashMap<>();
        portEntrance = new SynchronousQueue<>();
        queueOfShips = new LinkedBlockingQueue<>();
        processingShips = new Vector<>();

        for (int i = 0; i < numOfPiers; i++) {
            PortPier portPier = new PortPier(queueOfShips, controller, processingShips);
            portPier.setName("Pier " + String.valueOf(i + 1));
            listOfPiers.put("Pier " + String.valueOf(i + 1), portPier);
            Thread thread = new Thread(portPier);
            thread.setDaemon(true);
            thread.start();
        }
    }


    /**
     * Return port entrance queue.
     *
     * @return Port entrance queue.
     */
    public BlockingQueue<Ship> getPortEntrance() {
        return portEntrance;
    }

    /**
     * Return List of ships in queue.
     *
     * @return List of ships in queue.
     */
    public List<Ship> getListOfShipsInQueue() {
        return new ArrayList<Ship>(queueOfShips);
    }

    /**
     * Return list of processing ships.
     *
     * @return list of processing ships.
     */
    public List<Ship> getListOfProcessingShips() {
        return processingShips;
    }

    /**
     * Return Map of piers.
     *
     * @return map of piers.
     */
    public Map<String, PortPier> getMapOfPiers() {
        return listOfPiers;
    }

    /**
     * Return port yard.
     *
     * @return port yard.
     */
    public PortYard getPortYard() {
        return portYard;
    }

    /**
     * Set port yard for ship port.
     *
     * @param portYard adding port yard.
     */
    public void setPortYard(PortYard portYard) {
        this.portYard = portYard;
    }

    /**
     * Return port name.
     *
     * @return port name.
     */
    public String getPortName() {
        return portName;
    }
}
