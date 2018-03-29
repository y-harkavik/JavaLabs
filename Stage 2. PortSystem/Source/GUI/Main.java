package GUI;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                    PortSystemGUIController controller = new PortSystemGUIController();
                    controller.start();
                }
        );
    }
}
