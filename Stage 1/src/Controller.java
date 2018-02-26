import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {
    @FXML
    private TableView<Item> tableView;

    @FXML
    private Button documentsButton;
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
    private String tableState = Model.DOCUMENTS;

    private Model model = new Model();

    public void initialize() {
        documentsButton.fire();
        tableView.setRowFactory(tv->{
            TableRow<Item> row= new TableRow<Item>();
            row.setOnMouseClicked(event -> {
                if(!row.isEmpty()&&event.getClickCount()==2) {
                    try {
                        Desktop.getDesktop().open(row.getItem().getPath());
                    }catch (Exception ex) {
                        Alert alertFileNotFound = new Alert(Alert.AlertType.ERROR);
                        alertFileNotFound.setTitle("Hello amigo");
                        alertFileNotFound.setContentText("File not found.");
                        alertFileNotFound.showAndWait();
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void documentAction(ActionEvent event) {
        tableState = Model.DOCUMENTS;
        setRows();
    }

    @FXML
    private void imagesAction(ActionEvent event) {
        tableState = Model.IMAGES;
        setRows();
    }

    @FXML
    private void videoAction(ActionEvent event) {
        tableState = Model.VIDEO;
        setRows();
    }

    @FXML
    private void audioAction(ActionEvent event) {
        tableState = Model.AUDIO;
        setRows();
    }

    @FXML
    private void addAction(ActionEvent event) {
        model.addFiles(tableState);
        setRows();
    }

    @FXML
    private void removeAction(ActionEvent event) {
        //tableView.getItems().addAll(logic.addFiles(tableState));
    }

    @FXML
    private void rowFactory(Callback<TableView<Item>,TableRow<Item>> event) {

    }
    void setRows() {
        clearRows();
        tableView.getItems().addAll(model.getItems(tableState));
    }

    void clearRows() {
        tableView.getItems().clear();
    }
}
