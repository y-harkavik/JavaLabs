package Authentication;

import Catalog.Controller;
import Catalog.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Persons.Base;

/**
 * Class controller for AuthenticationWindow window.
 *@author Yauheni
 *@version 1.0
 */
public class LoginController {
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField loginTextField;

    /**
     *
     */
    AuthModel authModel = new AuthModel();

    /**
     * Method that caused after initialize FXML file
     */
    public void initialize() {
        //authModel.example();
        authModel.getBaseOfAccounts().getBaseFromFile();
    }

    @FXML
    /**
     * Caused when user push Guest link.
     *@param event Object of ActionEvent
     */
    private void guestAction(ActionEvent event) {
        createCatalog(null,-1);
    }

    @FXML
    /**
     * Caused when user push "Log In" button. If user inputted wrong login/Database, create Alert Message.
     * @param event Object of ActionEvent
     */
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

    /**
     * This method create a Catalog window.
     * @param base Object of Base that contain base of users.
     * @param index Index of user in Base.
     */
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
