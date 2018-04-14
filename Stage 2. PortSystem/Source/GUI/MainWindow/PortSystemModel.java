package GUI.MainWindow;

import objects.Transport.Marine.Ship;
import objects.Buildings.Port.ShipPort;
import objects.Buildings.Port.PortYard;

import java.util.*;

/**
 * Model class for PortSystem window. *
 *
 * @author Yauheni
 * @version 1.0
 */
public class PortSystemModel {
    private Map<String, ShipPort> shipPorts;

    /**
     * Initializes shipPort map.
     */
    public PortSystemModel() {
        shipPorts = new LinkedHashMap<>();
    }

    /**
     * Add port in Map of ship ports.
     *
     * @param name       Name of port.
     * @param numOfPiers Num of piers.
     * @param portYard   Port yard.
     * @param controller GUI controller.
     * @see PortYard
     * @see PortSystemGUIController
     */
    void addPortInList(String name, int numOfPiers, PortYard portYard, PortSystemGUIController controller) {
        ShipPort shipPort = new ShipPort(name, numOfPiers, controller);
        shipPort.setPortYard(portYard);
        shipPorts.put(name, shipPort);
        Thread t = new Thread(shipPort);
        t.setDaemon(true);
        t.start();
    }

    /**
     * @param nameOfPort Port name.
     * @return True - if port exist, else - false.
     */
    boolean checkPort(String nameOfPort) {
        return shipPorts.containsKey(nameOfPort);
    }

    /**
     * Return ship in pier if ship there.
     *
     * @param port Port name.
     * @param pier Pier name.
     * @return Ship in pier.
     */
    Ship getShipInPier(String port, String pier) {
        return shipPorts.get(port).getMapOfPiers().get(pier).getCurrentShip();
    }

    /**
     * Return list of ships in port.
     *
     * @param portName Port name.
     * @return list of ships in port.
     */
    List getListOfShipsInPort(String portName) {
        return shipPorts.get(portName).getListOfProcessingShips();
    }

    /**
     * Return list of ship ports.
     *
     * @return list of ship ports.
     */
    List<ShipPort> getListOfShipPorts() {
        return new ArrayList<ShipPort>(shipPorts.values());
    }

    /**
     * Return list of all processing ships.
     *
     * @return list of all processing ships.
     */
    List<Ship> getAllShipsList() {
        List<Ship> shipList = new ArrayList<>();
        for (ShipPort shipPort : getListOfShipPorts()) {
            shipList.addAll(shipPort.getListOfProcessingShips());
        }
        return shipList;
    }

    /**
     * Return all ships in queue.
     *
     * @return all ships in queue.
     */
    List<Ship> getAllShipsInQueue() {
        List<Ship> shipsInQueue = new ArrayList<>();
        for (ShipPort shipPort : getListOfShipPorts()) {
            shipsInQueue.addAll(shipPort.getListOfShipsInQueue());
        }
        return shipsInQueue;
    }

    /**
     * Return Map of ship ports.
     *
     * @return Map of ship ports.
     */
    Map<String, ShipPort> getMapOfShipPorts() {
        return shipPorts;
    }
}
