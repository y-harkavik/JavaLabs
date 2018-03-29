package GUI;

import objects.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PortSystemGUIController {
    private static PortSystemGUI mainWindow;

    private void initializeListeners() {
        mainWindow.addShip.addActionListener((event)->{
            List<Ship> ships = new ArrayList<Ship>(Arrays.asList(new Ship("1","a",100),new Ship("2","b",100),new Ship("3","c",100)));
            mainWindow.tableModel.addAll(ships);
        });
    }

    public void start() {
            mainWindow = new PortSystemGUI();
            initializeListeners();
            mainWindow.setVisible(true);
    }
}
