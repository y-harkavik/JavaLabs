package GUI;

import objects.Transport.Marine.Ship;
import objects.Buildings.Port.ShipPort;
import objects.Product.Characteristics.TypeOfProduct;
import objects.Buildings.Port.PortYard;

import java.util.*;

public class PortSystemModel {
    private Map<String, ShipPort> shipPorts;

    public PortSystemModel() {
        shipPorts = new LinkedHashMap<>();
    }

    void addPortInList(String name, int numOfPiers, PortSystemGUIController controller) {
        ShipPort shipPort = new ShipPort(name, numOfPiers, controller);
        shipPort.setPortPortYard(new PortYard());
        shipPort.getPortPortYard().addProduct(TypeOfProduct.WEED, 1000);
        shipPort.getPortPortYard().addProduct(TypeOfProduct.CLOTH, 1000);
        shipPort.getPortPortYard().addProduct(TypeOfProduct.COAL, 1000);
        shipPort.getPortPortYard().addProduct(TypeOfProduct.GOLD, 1000);
        shipPort.getPortPortYard().addProduct(TypeOfProduct.SLAVES, 1000);
        shipPort.getPortPortYard().addProduct(TypeOfProduct.WOOD, 1000);

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

    synchronized StringBuilder getShipsLog() {
        StringBuilder log = new StringBuilder("1\n");
        return log;
    }

    Map<String, ShipPort> getMapOfShipPorts() {
        return shipPorts;
    }
}
