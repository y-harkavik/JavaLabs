package GUI;

import objects.ShipPort;

import java.util.*;

public class PortSystemModel {
    private Map<String,ShipPort> shipPorts;

    public PortSystemModel() {
        //shipPorts = new ArrayList<>(Arrays.asList(new ShipPort("1",3),new ShipPort("2",2),new ShipPort("3",1)));
        shipPorts = new LinkedHashMap<>();
    }

    void addPortInList(String name, int numOfPiers) {
        shipPorts.put(name,new ShipPort(name,numOfPiers));

    }

    int checkPort(String name) {
        return Arrays.asList(shipPorts.keySet().toArray()).indexOf(name);
    }
}
