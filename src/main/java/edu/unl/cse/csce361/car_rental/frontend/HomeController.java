package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController extends ScreenController{
    @FXML TextField txtUsername;

    public void switchToCreateIndividualAccount(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "individualCustomerAccount.fxml");
    }

    public void switchToCreateCorporateAccount(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "corporateCustomerAccount.fxml");
    }

    public void switchToManagerOptions(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "managerOptions.fxml");
    }

    public void invalidLoginAlert() {

        Stage window = new Stage();
        //application modality allows the application and screen below to remain open,
        //but non-functional until the alert screen closes
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login Failed");
        window.setMinHeight(150);
        window.setMinWidth(300);

        Label label = new Label();
        label.setText("Sorry, it seems you entered an invalid login!");

        Button tryAgain = new Button("Try Again");
        tryAgain.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, tryAgain);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void logIn(javafx.event.ActionEvent event) throws IOException {
        if(DataLogic.getInstance().logIn(txtUsername.getText())) {
            switchScreen(event, "filters.fxml");
        } else {
            invalidLoginAlert();
        }
    }
}
