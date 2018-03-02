package Authentication;

import Catalog.Controller;
import Catalog.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import person.Base;
import person.Guest;
import person.Person;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField loginTextField;

    AuthModel authModel = new AuthModel();

    public void initialize() {
        //authModel.example();
        authModel.getBaseOfAccounts().getBaseFromFile();
    }

    @FXML
    private void guestAction(ActionEvent event) {
        createCatalog(null,-1);
    }

    @FXML
    private void loginAction(ActionEvent event) {
        if (!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
            int index = authModel.checkEnteredInformation(loginTextField.getText(), passwordTextField.getText());
            if (index >= 0) {
                createCatalog(authModel.getBaseOfAccounts(),index);
            } else {
                Model.createAlertError(AuthModel.LOGIN_ERROR);
            }
        }
    }

    void createCatalog(Base base,int index) {
        try {
            Stage primaryStage = (Stage)loginTextField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\Catalog\\sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Controller catalogController = loader.getController();
            catalogController.setUser(base,index);
            primaryStage.setResizable(true);
            if(base!=null){
                primaryStage.setOnHidden(event -> catalogController.exitApplication(event));
            }
            primaryStage.setScene(scene);
            primaryStage.setTitle("Catalog");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
