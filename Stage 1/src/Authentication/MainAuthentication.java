package Authentication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAuthentication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("Authentication/login.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Authentication/login.fxml"));
        Parent root = loader.load();
        ((LoginController)loader.getController()).setPrimaryStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign in");
        primaryStage.show();
    }
}
