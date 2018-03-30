package objects;

import java.util.ArrayList;
import java.util.List;

public class Pier {
    private List<Ship> listOfShips = new ArrayList<>();


    public List<Ship> getListOfShips() {
        return listOfShips;
    }

    public void setListOfShips(List<Ship> listOfShips) {
        this.listOfShips = listOfShips;
    }
}
