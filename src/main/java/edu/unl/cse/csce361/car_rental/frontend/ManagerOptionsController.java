package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;

import java.io.IOException;

public class ManagerOptionsController extends ScreenController{

    public void createCorporateCustomer(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "corporateCustomerAccountByManager.fxml");
    }
}
