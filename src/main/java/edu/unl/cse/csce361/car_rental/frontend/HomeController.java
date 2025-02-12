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

    public void switchToUpdateIndividualAccount(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "updateIndividualCustomerAccount.fxml");
    }

    public void switchToCreateCorporateAccount(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "corporateCustomerAccount.fxml");
    }

    public void switchToUpdateCorporateAccount(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "updateCorporateCustomerAccount.fxml");
    }

    public void switchToManagerOptions(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "managerOptions.fxml");
    }

    public void alertScreen() {
        alertScreen("Login Failed", "Sorry, it seems you entered an invalid login!", "", "Try Again");
    }

    public void logIn(javafx.event.ActionEvent event) throws IOException {
        if(DataLogic.getInstance().logIn(txtUsername.getText())) {
            switchScreen(event, "filters.fxml");
        } else {
            alertScreen();
        }
    }
}
