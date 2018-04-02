package objects;



import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ShipPort extends Observable implements Runnable{
    private final String name;
    private Map<String, Pier> listOfPiers;
    private BlockingQueue<Ship> queueOfShips;
    private BlockingQueue<Ship> portEntrance;
    private Yard portYard;


    public ShipPort(String name, int numOfPiers) {
        this.name = name;

        listOfPiers = new LinkedHashMap<>();
        portEntrance = new SynchronousQueue<>();
        queueOfShips = new LinkedBlockingQueue<>();

        for (int i = 0; i < numOfPiers; i++) {
            listOfPiers.put("Pier " + String.valueOf(i + 1), new Pier());
        }
    }

    public Map<String, Pier> getMapOfPiers() {
        return listOfPiers;
    }

    public List<Pier> getListOfPiers() {
        return new ArrayList<Pier>(listOfPiers.values());
    }

    public List<Ship> getListOfShipsInPort() {
        List<Ship> shipList = new ArrayList<>();
        for (Pier pier : getListOfPiers()) {
            shipList.addAll(pier.getListOfShips());
        }
        return shipList;
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


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public BlockingQueue<Ship> getQueueOfShips() {
        return queueOfShips;
    }
}
