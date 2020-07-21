package edu.unl.cse.csce361.car_rental.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        primaryStage.setTitle("Husker Klunker Car Rental");
        primaryStage.setScene(new Scene(root, 450, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
