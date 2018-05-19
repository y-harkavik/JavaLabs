package Catalog;

import Persons.Base;
import Persons.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

/**
 * Class controller for Catalog window.
 *
 * @author Yauheni
 * @version 1.0
 */
public class Controller {
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TextField searchField;
    @FXML
    private Button documentsButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;
    /**
     * Variable that contains a current state of opened table list.
     */
    private String tableState = Model.DOCUMENTS;

    private Model model = new Model();

    public void initialize() {
        documentsButton.fire();
        tableView.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<Item>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    openFile(row.getItem());
                }
            });
            return row;
        });
        tableView.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                openFile(tableView.getSelectionModel().getSelectedItem());
            }
        });
        /*tableView.setOnKeyPressed(keyEvent -> {
            Item focusedItem = tableView.getSelectionModel().getSelectedItem();
            if ((focusedItem != null) && (keyEvent.getCode().equals(KeyCode.DELETE))) {
                model.removeFile(focusedItem);
                setRows();
            }
        });*/
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            clearRows();
            tableView.getItems().addAll(model.findNeededItems(newValue, tableState));
        }));
        model.getItemsFromFile();
        setRows();
    }

    /**
     * Method opened file from catalog.
     *
     * @param openedItem File that user want to open.
     */
    void openFile(Item openedItem) {
        try {
            Desktop.getDesktop().open(openedItem.getPath());
        } catch (Exception ex) {
            model.createAlertError(Model.PATH_ERROR);
            model.removeFile(openedItem);
            setRows();
        }
    }

    /**
     * Method that caused when user close catalog window.
     *
     * @param value WindowEvent variable.
     */
    public void exitApplication(WindowEvent value) {
        model.saveProgramInformation();
        Platform.exit();
    }

    /**
     * Caused when user pushed document button. Set documents in table.
     *
     * @param event Object of ActionEvent.
     */
    @FXML
    private void documentAction(ActionEvent event) {
        tableState = Model.DOCUMENTS;
        setRows();
    }

    /**
     * Caused when user pushed image button. Set images in table.
     *
     * @param event Object of ActionEvent.
     */
    @FXML
    private void imagesAction(ActionEvent event) {
        tableState = Model.IMAGES;
        setRows();
    }

    /**
     * Caused when user pushed video button. Set video in table.
     *
     * @param event Object of ActionEvent.
     */
    @FXML
    private void videoAction(ActionEvent event) {
        tableState = Model.VIDEO;
        setRows();
    }

    /**
     * Caused when user pushed audio button. Set audio in table.
     *
     * @param event Object of ActionEvent.
     */
    @FXML
    private void audioAction(ActionEvent event) {
        tableState = Model.AUDIO;
        setRows();
    }

    /**
     * Caused when user pushed add button. Show FileChooser where user chooses added files.
     *
     * @param event Object of ActionEvent.
     */
    @FXML
    private void addAction(ActionEvent event) {
        model.addFiles(tableState);
        setSizeUserCanAdd();
        setRows();
    }

    /**
     * Caused when user pushed remove button. Remove file from table.
     *
     * @param event Object of ActionEvent.
     */
    @FXML
    private void removeAction(ActionEvent event) {
        model.removeFile(tableView.getSelectionModel().getSelectedItem());
        setRows();
    }

    /*@FXML
    private void removeOnDeleteButton(KeyEvent keyEvent) {
        Item focusedItem = tableView.getSelectionModel().getSelectedItem();
        if ((focusedItem != null) && (keyEvent.getCode().equals(KeyCode.DELETE))) {
            model.removeFile(focusedItem);
            setRows();
        }
    }*/

    /*@FXML
    private void searchInTable(ActionEvent event) {
        clearRows();
        tableView.getItems().addAll(model.findNeededItems(searchField.getText(), tableState));
    }*/

    /**
     * This method caused clearRows and set files in table.
     *
     * @see Controller#clearRows()
     */
    void setRows() {
        clearRows();
        tableView.getItems().addAll(model.getItems(tableState));
    }

    /**
     * Clear table.
     */
    void clearRows() {
        tableView.getItems().clear();
    }

    /**
     * This method shows for user a count of enable size memory for add.
     */
    void setSizeUserCanAdd() {
        if (model.getAccount() instanceof User) {
            addButton.setText("Add (" + model.getCanAdd() + " bytes left)");
            if (model.getCanAdd() == 0) addButton.setDisable(true);
        }
    }

    /**
     * This method set base of users for catalog and save a current user that opened catalog.
     *
     * @param base  Object of Base that contain base of users.
     * @param index Index of user in Base.
     */
    public void setUser(Base base, int index) {
        model.setBaseOfAccounts(base);
        if (model.getBaseOfAccounts() == null) {
            removeButton.setDisable(true);
            addButton.setDisable(true);
        } else {
            model.setAccount(base.getListOfAccounts().get(index));
            setSizeUserCanAdd();
            tableView.setOnKeyPressed(keyEvent -> {
                Item focusedItem = tableView.getSelectionModel().getSelectedItem();
                if ((focusedItem != null) && (keyEvent.getCode().equals(KeyCode.DELETE))) {
                    model.removeFile(focusedItem);
                    setRows();
                }
            });
        }
    }

    /**
     * @param event Object of ActionEvent.
     */
    @FXML
    public void changeUser(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) addButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\AuthenticationWindow\\login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            model.saveProgramInformation();
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sign in");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
