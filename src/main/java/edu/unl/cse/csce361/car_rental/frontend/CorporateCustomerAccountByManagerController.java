package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CorporateCustomerAccountByManagerController extends ScreenController{

    @FXML TextField txtFieldName;
    @FXML TextField txtFieldStreetAddress1;
    @FXML TextField txtFieldStreetAddress2;
    @FXML TextField txtFieldCity;
    @FXML TextField txtFieldState;
    @FXML TextField txtFieldZip;
    @FXML TextField txtFieldBankAccount;
    @FXML TextField txtFieldNegotiatedRate;

    public void alertScreen() {
        alertScreen("Cannot Create Account", "It seems an account already exists with the name you entered!",
                "Please create an account under an unused name", "Try Again");
    }

    public void createCorporateCustomer(javafx.event.ActionEvent event) throws IOException {
        if (DataLogic.getInstance().hasCustomerName(txtFieldName.getText()) == true) {
            alertScreen();
        } else if(txtFieldStreetAddress1.getText().isEmpty() || txtFieldStreetAddress2.getText().isEmpty() || txtFieldCity.getText().isEmpty()
                || txtFieldState.getText().isEmpty() || txtFieldZip.getText().isEmpty() || txtFieldBankAccount.getText().isEmpty()
                || txtFieldNegotiatedRate.getText().isEmpty()) {
            alertScreen("Cannot Create Account", "Please make sure to fill in all fields", "", "Try Again");
        } else {
            DataLogic.getInstance().createCorporateCustomerAccountWithNegotiatedRate(txtFieldName.getText(), txtFieldStreetAddress1.getText(),
                    txtFieldStreetAddress2.getText(), txtFieldCity.getText(), txtFieldState.getText(), txtFieldZip.getText(), txtFieldBankAccount.getText(),
                    Double.valueOf(txtFieldNegotiatedRate.getText()));
            switchScreen(event, "home.fxml");
        }
    }
}
