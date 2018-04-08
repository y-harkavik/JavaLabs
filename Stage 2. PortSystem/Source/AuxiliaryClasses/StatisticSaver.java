package AuxiliaryClasses;

import objects.Buildings.Port.ShipPort;
import objects.Product.Characteristics.TypeOfProduct;
import objects.Transport.Marine.Ship;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StatisticSaver implements Runnable {
    FileWriter fileWriter;
    Map<String, ShipPort> shipPorts;

    public StatisticSaver(Map<String, ShipPort> shipPorts) {
        this.shipPorts = shipPorts;
        try {
            fileWriter = new FileWriter("..\\JavaLabs\\statistic.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                List<ShipPort> shipPortArrayList = new ArrayList<>(shipPorts.values());
                for (ShipPort port : shipPortArrayList) {
                    fileWriter.write(port.getPortName() + ":\n");
                    fileWriter.append("Ships in queue: ");
                    for (Ship ship : port.getListOfShipsInQueue()) {
                        fileWriter.append(ship.getNameShip());
                        fileWriter.append("; ");
                    }
                    fileWriter.append("\nShips in processing: ");
                    for (Ship ship : port.getListOfProcessingShips()) {
                        fileWriter.append(ship.getNameShip());
                        fileWriter.append("; ");
                    }
                    fileWriter.append("\nProducts in yard: ");
                    Map<TypeOfProduct, Integer> products = new HashMap<>(port.getPortYard().getProducts());
                    for (TypeOfProduct typeOfProduct : products.keySet()) {
                        fileWriter.append(typeOfProduct.getType() + " - " + products.get(typeOfProduct).toString());
                        fileWriter.append("; ");
                    }
                    fileWriter.append('\n');
                    fileWriter.flush();
                }
            } catch (InterruptedException e) {
                try {
                    fileWriter.write("\nProgram finished\n");
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FileWriter getFileWriter() {
        return fileWriter;
    }
}
