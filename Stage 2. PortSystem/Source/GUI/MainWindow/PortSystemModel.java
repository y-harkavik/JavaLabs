package GUI.MainWindow;

import objects.Transport.Marine.Ship;
import objects.Buildings.Port.ShipPort;
import objects.Buildings.Port.PortYard;

import java.util.*;

public class PortSystemModel {
    private Map<String, ShipPort> shipPorts;

    public PortSystemModel() {
        shipPorts = new LinkedHashMap<>();
    }

    void addPortInList(String name, int numOfPiers, PortYard portYard, PortSystemGUIController controller) {
        ShipPort shipPort = new ShipPort(name, numOfPiers, controller);
        shipPort.setPortYard(portYard);
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
        return shipPorts.get(portName).getListOfProcessingShips();
    }

    List<ShipPort> getListOfShipPorts() {
        return new ArrayList<ShipPort>(shipPorts.values());
    }

    List<Ship> getAllShipsList() {
        List<Ship> shipList = new ArrayList<>();
        for (ShipPort shipPort : getListOfShipPorts()) {
            shipList.addAll(shipPort.getListOfProcessingShips());
        }
        return shipList;
    }

    List<Ship> getAllShipsInQueue() {
        List<Ship> shipsInQueue = new ArrayList<>();
        for (ShipPort shipPort : getListOfShipPorts()) {
            shipsInQueue.addAll(shipPort.getListOfShipsInQueue());
        }
        return shipsInQueue;
    }

    Map<String, ShipPort> getMapOfShipPorts() {
        return shipPorts;
    }
}
