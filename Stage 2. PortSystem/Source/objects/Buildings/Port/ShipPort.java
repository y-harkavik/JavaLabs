package objects.Buildings.Port;

import GUI.MainWindow.PortSystemGUIController;
import objects.Transport.Marine.Ship;
import objects.Transport.Status.ShipStatus;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ShipPort implements Runnable {
    private final String name;
    private Map<String, PortPier> listOfPiers;
    private BlockingQueue<Ship> queueOfShips;
    private BlockingQueue<Ship> portEntrance;
    private List<Ship> processingShips;
    private PortYard portPortYard;
    private PortSystemGUIController controller;

    public ShipPort(String name, int numOfPiers, PortSystemGUIController controller) {
        this.name = name;
        this.controller = controller;

        listOfPiers = new LinkedHashMap<>();
        portEntrance = new SynchronousQueue<>();
        queueOfShips = new LinkedBlockingQueue<>();
        processingShips = new Vector<>();

        for (int i = 0; i < numOfPiers; i++) {
            PortPier portPier = new PortPier(queueOfShips, controller, processingShips);
            listOfPiers.put("PortPier " + String.valueOf(i + 1), portPier);
            Thread thread = new Thread(portPier);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Ship ship = portEntrance.take();
               // System.out.println(ship.getNameShip() + "portEntrance.take()");

                controller.getMainWindow().logTextArea.append(ship.getNameShip() + " - пришвартовался\n");
                //System.out.println("getPortPortYard().changeProductCount");
                getPortPortYard().changeProductCount(ship.getCurrentCargo().getParameters().getTypeOfProduct(), ship.getCurrentCargo().getParameters().getCount(), ship.getCurrentCargo().getOperation());
                //System.out.println("ship.getPhaser().arriveAndDeregister();");
                ship.setStatus(ShipStatus.IN_QUEUE);
                ship.getPhaser().arriveAndDeregister();
                //System.out.println("queueOfShips.put(ship);");
                queueOfShips.put(ship);
                controller.repaintQueueTable();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public BlockingQueue<Ship> getQueueOfShips() {
        return queueOfShips;
    }

    public BlockingQueue<Ship> getPortEntrance() {
        return portEntrance;
    }

    public List<Ship> getListOfShipsInQueue() {
        return new ArrayList<Ship>(queueOfShips);
    }

    public Map<String, PortPier> getMapOfPiers() {
        return listOfPiers;
    }

    public List<Ship> getListOfProcessingShips() {
        return processingShips;
    }

    public PortYard getPortPortYard() {
        return portPortYard;
    }

    public void setPortPortYard(PortYard portPortYard) {
        this.portPortYard = portPortYard;
    }

    public String getName() {
        return name;
    }
}
