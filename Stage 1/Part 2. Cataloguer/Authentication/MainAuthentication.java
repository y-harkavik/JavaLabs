package Authentication;
/**
 * Class is used to create authentication window and start app
 * @author Yauheni
 *@version 1.0
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainAuthentication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign in");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
