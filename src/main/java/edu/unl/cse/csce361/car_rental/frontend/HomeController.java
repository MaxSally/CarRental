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

    public void alertScreen() {
        invalidAlert("Login Failed", "Sorry, it seems you entered an invalid login!", "");
    }

    public void logIn(javafx.event.ActionEvent event) throws IOException {
        if(DataLogic.getInstance().logIn(txtUsername.getText())) {
            switchScreen(event, "filters.fxml");
        } else {
            alertScreen();
        }
    }
}
