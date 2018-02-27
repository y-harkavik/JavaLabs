import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setOnHidden(event -> ((Controller)loader.getController()).exitApplication(event));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catalog");
        primaryStage.show();
    }

    /*@Override
    public void stop() throws Exception {

    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
