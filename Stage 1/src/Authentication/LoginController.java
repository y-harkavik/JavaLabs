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
import person.Person;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField loginTextField;

    private Stage primaryStage;

    void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    AuthModel authModel = new AuthModel();

    public void initialize() {
        //authModel.example();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("login.ser"))) {
            authModel.setListOfAccounts((ArrayList<Person>) objectInputStream.readObject());
        } catch (Exception e) {
            Catalog.Model.createAlertError(Model.READ_ERROR);
        }
    }

    @FXML
    private void guestAction(ActionEvent event) {

    }

    @FXML
    private void loginAction(ActionEvent event) {
        if (!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
            Person account = authModel.checkEnteredInformation(loginTextField.getText(), passwordTextField.getText());
            if (account != null) {
                createCatalog(account);
            } else {
                Model.createAlertError(AuthModel.LOGIN_ERROR);
            }
        }
    }

    void createCatalog(Person account) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\Catalog\\sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Controller catalogController = loader.getController();
            catalogController.setUser(account);
            primaryStage.setOnHidden(event -> catalogController.exitApplication(event));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Catalog");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
