package objects;

import GUI.PortSystemGUIController;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ShipPort implements Runnable {
    private final String name;
    private Map<String, Pier> listOfPiers;
    private BlockingQueue<Ship> queueOfShips;
    private BlockingQueue<Ship> portEntrance;
    private List<Ship> processingShips;
    private Yard portYard;
    private PortSystemGUIController controller;

    public ShipPort(String name, int numOfPiers, PortSystemGUIController controller) {
        this.name = name;
        this.controller = controller;

        listOfPiers = new LinkedHashMap<>();
        portEntrance = new SynchronousQueue<>();
        queueOfShips = new LinkedBlockingQueue<>();
        processingShips = new Vector<>();

        for (int i = 0; i < numOfPiers; i++) {
            Pier pier = new Pier(queueOfShips, controller, processingShips);
            listOfPiers.put("Pier " + String.valueOf(i + 1), pier);
            Thread thread = new Thread(pier);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public List<Ship> getListOfShipsInQueue() {
        return new ArrayList<Ship>(queueOfShips);
    }

    public Map<String, Pier> getMapOfPiers() {
        return listOfPiers;
    }

    public List<Ship> getListOfProcessingShips() {
        return processingShips;
    }

    public Yard getPortYard() {
        return portYard;
    }

    public void setPortYard(Yard portYard) {
        this.portYard = portYard;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Ship ship = portEntrance.take();
                System.out.println(ship.getNameShip()+"portEntrance.take()");

                controller.getMainWindow().logTextArea.append(ship.getNameShip() + "   пришвартовался\n");
                getPortYard().changeProductCount(ship.getCurrentCargo().getParameters().getTypeOfProduct(), ship.getCurrentCargo().getParameters().getCount(), ship.getCurrentCargo().getOperation());
                System.out.println("ship.getPhaser().arriveAndDeregister();\n");
                ship.getPhaser().arriveAndDeregister();
                ship.setStatus(ShipStatus.IN_QUEUE);
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
}
