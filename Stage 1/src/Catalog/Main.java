package Catalog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Catalog/sample.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setOnHidden(event -> ((Controller)loader.getController()).exitApplication(event));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catalog");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
