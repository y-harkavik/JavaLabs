import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class Controller {
    @FXML
    private TableView<Item> tableView;

    @FXML
    private Button documentButton;
    /*@FXML
    private TableColumn previewColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn typeColumn;
    @FXML
    private TableColumn sizeColumn;
    @FXML
    private TableColumn dateColumn;*/
    private String tableState = Logic.DOCUMENTS;

    private Logic logic = new Logic();

    public void initialize() {
        documentButton.fire();
    }

    @FXML
    private void documentAction(ActionEvent event) {
        tableState = Logic.DOCUMENTS;
        setRows();
    }

    @FXML
    private void imagesAction(ActionEvent event) {
        tableState = Logic.IMAGES;
        setRows();
    }

    @FXML
    private void videoAction(ActionEvent event) {
        tableState = Logic.VIDEO;
        setRows();
    }

    @FXML
    private void audioAction(ActionEvent event) {
        tableState = Logic.AUDIO;
        setRows();
    }

    @FXML
    private void addAction(ActionEvent event) {
        tableView.getItems().addAll(logic.addFiles(tableState));
    }

    @FXML
    private void removeAction(ActionEvent event) {
        //tableView.getItems().addAll(logic.addFiles(tableState));
    }

    void setRows() {
        tableView.getItems().addAll(logic.getItems(tableState));
    }
}
