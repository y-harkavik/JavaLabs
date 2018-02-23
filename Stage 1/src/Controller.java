import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Controller {
    @FXML
    private TableView<String> tableView;

    private Logic logic = new Logic();

    @FXML
    private void documentAction(ActionEvent event) {
        File file = logic.getFile(logic.DOCUMENTS);
        tableView.getItems().addAll("hello");
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
