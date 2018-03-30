package objects;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ShipPort {
    private final String name;
    private ArrayList<Pier> listOfPiers;
    private ObservableList<Ship> queueOfShips = FXCollections.observableArrayList();

    public ShipPort(String name, int num) {
        this.name=name;
        listOfPiers = new ArrayList<>();

        for(int i=0;i<num;i++) {
            listOfPiers.add(new Pier());
        }


    }
}
