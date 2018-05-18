package Graphics.Constants;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public abstract class GraphicsDialogs {
    public static void showDialog(String text, int style) {
        Shell shell = new Shell();
        MessageBox messageBox = new MessageBox(shell, style);
        messageBox.setMessage(text);
        messageBox.open();
    }
}
