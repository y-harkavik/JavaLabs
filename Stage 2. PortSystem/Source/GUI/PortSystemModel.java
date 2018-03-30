package GUI;

import objects.Pier;
import objects.Ship;
import objects.ShipPort;

import java.util.*;

public class PortSystemModel {
    private Map<String, ShipPort> shipPorts;

    public PortSystemModel() {
        shipPorts = new LinkedHashMap<>();
    }

    void addPortInList(String name, int numOfPiers) {
        shipPorts.put(name, new ShipPort(name, numOfPiers));
    }

    boolean checkPort(String nameOfPort) {
        return shipPorts.containsKey(nameOfPort);
    }

    List getListOfShipsInPier(String port, String nameOfPier) {
        return shipPorts.get(port).getMapOfPiers().get(nameOfPier).getListOfShips();
    }

    List getListOfShipsInPort(String portName) {
        List<Ship> allShips = new ArrayList();
        ShipPort shipPort = shipPorts.get(portName);
        List<Pier> piers = new ArrayList<Pier>(shipPort.getMapOfPiers().values());

        for (Pier pier : piers) {
            allShips.addAll(pier.getListOfShips());
        }
        return allShips;
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
}
