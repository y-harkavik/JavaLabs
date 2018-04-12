package objects.Buildings.Port;

import GUI.MainWindow.PortSystemGUIController;
import objects.Transport.Marine.Ship;
import objects.Transport.Status.ShipStatus;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

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


    public BlockingQueue<Ship> getPortEntrance() {
        return portEntrance;
    }

    public List<Ship> getListOfShipsInQueue() {
        return new ArrayList<Ship>(queueOfShips);
    }

    public List<Ship> getListOfProcessingShips() {
        return processingShips;
    }

    public Map<String, PortPier> getMapOfPiers() {
        return listOfPiers;
    }

    public PortYard getPortYard() {
        return portYard;
    }

    public void setPortYard(PortYard portYard) {
        this.portYard = portYard;
    }

    public String getPortName() {
        return portName;
    }
}
