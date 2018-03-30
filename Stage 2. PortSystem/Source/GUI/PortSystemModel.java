package GUI;

import objects.ShipPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortSystemModel {
    private List<ShipPort> shipPorts;

    public PortSystemModel() {
        //shipPorts = new ArrayList<>(Arrays.asList(new ShipPort("1",3),new ShipPort("2",2),new ShipPort("3",1)));
    }

    void addPortInList(String name, int numOfPiers) {
        shipPorts.add(new ShipPort(name,numOfPiers));

    }
}
