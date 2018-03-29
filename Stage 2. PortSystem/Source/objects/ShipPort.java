package objects;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ShipPort {
    private ArrayList<Pier> listOfPiers = new ArrayList<>();
    private ObservableList<Ship> queueOfShips = FXCollections.observableArrayList();

}
