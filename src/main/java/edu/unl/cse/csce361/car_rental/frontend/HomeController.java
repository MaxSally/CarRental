package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

    public void logIn(javafx.event.ActionEvent event) throws IOException {
        if(DataLogic.getInstance().logIn(txtUsername.getText())){
            switchScreen(event, "individualCustomerAccount.fxml");
        }else{
            txtUsername.setText("Failed");
        }
    }
}
