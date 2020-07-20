package edu.unl.cse.csce361.car_rental.frontend;

import java.io.IOException;

public class HomeController extends ScreenController{

    public void switchToCreateIndividualAccount(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "individualCustomerAccount.fxml");
    }

    public void switchToCreateCorporateAccount(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "corporateCustomerAccount.fxml");
    }
}
