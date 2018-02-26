import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TextField searchField;
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
        tableView.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<Item>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    try {
                        Desktop.getDesktop().open(row.getItem().getPath());
                    } catch (Exception ex) {
                        Alert alertFileNotFound = new Alert(Alert.AlertType.ERROR);
                        alertFileNotFound.setTitle("Hello amigo");
                        alertFileNotFound.setContentText("File not found.");
                        alertFileNotFound.showAndWait();
                        model.removeFile(row.getItem());
                        setRows();
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
        model.removeFile(tableView.getSelectionModel().getSelectedItem());
        setRows();
    }

    @FXML
    private void removeOnDeleteButton(KeyEvent keyEvent) {
        Item focusedItem = tableView.getSelectionModel().getSelectedItem();
        if ((focusedItem != null) && (keyEvent.getCode().equals(KeyCode.DELETE))) {
            model.removeFile(focusedItem);
            setRows();
        }
    }

    @FXML
    private void searchInTable(ActionEvent event) {
        String searchRequest = searchField.getText();
            if (!model.getItems(tableState).isEmpty()) {
                List<Item> resultOfSearch = new ArrayList<Item>(model.getItems(tableState));
                clearRows();
                for (Item findItem : resultOfSearch) {
                    if (findItem.getName().contains(searchRequest)) {
                        tableView.getItems().add(findItem);
                    }
                }
            }
        /*String searchRequest = searchField.getText();
        if (!model.getItems(tableState).isEmpty()) {
            //List<Item> resultOfSearch = new ArrayList<Item>(model.getItems(tableState));
            Iterator<Item> iterator = model.getItems(tableState).iterator();
            clearRows();
            while(iterator.hasNext()) {
                if (iterator.next().getName().contains(searchRequest)) {
                    tableView.getItems().add(iterator.next());
                }
            }*/
        }

    void setRows() {
        clearRows();
        tableView.getItems().addAll(model.getItems(tableState));
    }

    void clearRows() {
        tableView.getItems().clear();
    }
}
