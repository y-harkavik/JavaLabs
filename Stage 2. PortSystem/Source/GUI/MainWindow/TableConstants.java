package GUI.MainWindow;

import objects.Transport.Status.ShipStatus;

import javax.swing.*;

public class TableConstants {
    public static final String[] SHIP_TABLE_HEADERS = {"Ship", "Good", "Operation", "Count", "Progress"};
    public static final Class[] SHIP_TABLE_COLUMN_CLASSES = {String.class, String.class, ShipStatus.class, Integer.class, JProgressBar.class};

    public static final String[] QUEUE_TABLE_HEADERS = {"Ship", "Good", "Operation", "Count"};
    public static final Class[] QUEUE_TABLE_COLUMN_CLASSES = {String.class, String.class, ShipStatus.class, Integer.class};
}
