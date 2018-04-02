package GUI;

import objects.Ship;
import objects.ShipPort;

import java.util.*;

public class PortSystemModel {
    private Map<String, ShipPort> shipPorts;

    public PortSystemModel() {
        shipPorts = new LinkedHashMap<>();
    }

    void addPortInList(String name, int numOfPiers, PortSystemGUIController controller) {
        ShipPort shipPort = new ShipPort(name, numOfPiers, controller);
        shipPorts.put(name, shipPort);
        Thread t = new Thread(shipPort);
        t.setDaemon(true);
        t.start();
    }

    boolean checkPort(String nameOfPort) {
        return shipPorts.containsKey(nameOfPort);
    }

    Ship getShipInPier(String port, String pier) {
        return shipPorts.get(port).getMapOfPiers().get(pier).getCurrentShip();
    }

    List getListOfShipsInPort(String portName) {
        /*List<Ship> allShips = new ArrayList();
        ShipPort shipPort = shipPorts.get(portName);
        List<Pier> piers = new ArrayList<Pier>(shipPort.getMapOfPiers().values());

        for (Pier pier : piers) {
            allShips.addAll(pier.getListOfShips());
        }*/

        return shipPorts.get(portName).getListOfShipsInPort();
    }

    List<ShipPort> getListOfShipPorts() {
        return new ArrayList<ShipPort>(shipPorts.values());
    }

    List<Ship> getAllShipsList() {
        List<Ship> shipList = new ArrayList<>();
        for (ShipPort shipPort : getListOfShipPorts()) {
           shipList.addAll(shipPort.getListOfShipsInPort());
        }
        return shipList;
    }

    synchronized StringBuilder getShipsLog() {
        StringBuilder log = new StringBuilder("1\n");
        return log;
    }

    Map<String,ShipPort> getMapOfShipPorts() {
        return shipPorts;
    }
}
