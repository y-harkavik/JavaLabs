package Authentication;

import Catalog.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoginController {
    AuthModel authModel = new AuthModel();

    public void initialize() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("usersInfo.ser")))
        {

        }catch (Exception e) {
            Catalog.Model.createAlertError(Model.READ_ERROR);
        }
    }

    @FXML
    private void guestAction(ActionEvent event) {

    }

    @FXML
    private void loginAction(ActionEvent event) {

    }
}
