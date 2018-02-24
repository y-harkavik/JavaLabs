import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CheckBoxDemo extends Application {

    Label response;
    @Override
    public void start(Stage myStage) throws Exception {
        /*myStage.setTitle("Demonstrate Check Boxes");
        FlowPane rootNode = new FlowPane(10, 10);
        rootNode.setAlignment(Pos.CENTER);
        Scene myScene = new Scene(rootNode,200,300);
        myStage.setScene(myScene);
        response = new Label("Select computer type");
        TextField textField = new TextField();
        textField.setPrefColumnCount(15);
        textField.setPromptText("Поиск");
        //textField.setEffect(new Reflection());
        textField.setEffect(new BoxBlur(1,1,5));
        textField.setRotate(45);
        ObservableList<String> computerTypes = FXCollections.observableArrayList("Smartphone","Notebook","sdasd","adasd");
        ListView<String> lvComputers = new ListView<>(computerTypes);
        lvComputers.setPrefSize(100,100);
        lvComputers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        MultipleSelectionModel<String> lvSelModel = lvComputers.getSelectionModel();
        lvSelModel.selectedItemProperty().addListener(( changed, oldVal, newVal)->{
            response.setText("computer selected is " +newVal);
        });
        rootNode.getChildren().addAll(lvComputers,response,textField);
        myStage.show();*/
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String a = LocalDateTime.now().format(dateTimeFormatter);
        System.out.println(a);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
