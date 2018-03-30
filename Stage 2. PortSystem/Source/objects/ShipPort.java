package objects;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class ShipPort {
    private final String name;
    private Map<String,Pier> listOfPiers;
    private ObservableList<Ship> queueOfShips = FXCollections.observableArrayList();

    public ShipPort(String name, int num) {
        this.name=name;
        listOfPiers = new LinkedHashMap<>();

        for(int i=0;i<num;i++) {
            listOfPiers.put("Pier "+String.valueOf(i+1),new Pier());
        }
    }

    int getIndexOfPier(String name) {
        return 2;
    }

    public Map<String,Pier> getMapOfPiers() {
        return listOfPiers;
    }

    public List<Pier> getListOfPiers() {
        return new ArrayList<Pier>(listOfPiers.values());
    }
}
