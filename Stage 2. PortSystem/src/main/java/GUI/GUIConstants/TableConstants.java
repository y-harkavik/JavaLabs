package GUI.GUIConstants;

import objects.Transport.Status.ShipStatus;

import javax.swing.*;

/**
 * Class that contains constants for our JTables.
 *
 * @author Yauheni
 * @version 1.0
 */
public class TableConstants {
    /**
     * String array that contains headers for ship table.
     */
    public static final String[] SHIP_TABLE_HEADERS = {"Ship", "Good", "Operation", "Count", "Progress"};
    /**
     * Class array that contains classes for ship table columns.
     */
    public static final Class[] SHIP_TABLE_COLUMN_CLASSES = {String.class, String.class, ShipStatus.class, Integer.class, JProgressBar.class};

    /**
     * String array that contains headers for queue table.
     */
    public static final String[] QUEUE_TABLE_HEADERS = {"Ship", "Good", "Operation", "Count"};
    /**
     * Class array that contains classes for queue table columns.
     */
    public static final Class[] QUEUE_TABLE_COLUMN_CLASSES = {String.class, String.class, ShipStatus.class, Integer.class};

    private TableConstants() { }
}
