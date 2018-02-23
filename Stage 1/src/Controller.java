import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Controller {
    private Logic logic = new Logic();
    @FXML
    private void documentAction(ActionEvent event) {
        File file = logic.getFile(logic.DOCUMENTS);
    }

    @FXML
    private void imagesAction(ActionEvent event) {
        File file = logic.getFile(logic.IMAGES);

    }

    @FXML
    private void videoAction(ActionEvent event) {
        File file = logic.getFile(logic.VIDEO);

    }

    @FXML
    private void audioAction(ActionEvent event) {
        File file = logic.getFile(logic.AUDIO);

    }

    @FXML
    private void addAction(ActionEvent event) {

    }

    @FXML
    private void removeAction(ActionEvent event) {

    }
}
