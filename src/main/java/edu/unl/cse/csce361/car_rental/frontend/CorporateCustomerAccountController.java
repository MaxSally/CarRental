package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CorporateCustomerAccountController extends  ScreenController{
    @FXML TextField txtFieldName;
    @FXML TextField txtFieldStreetAddress1;
    @FXML TextField txtFieldStreetAddress2;
    @FXML TextField txtFieldCity;
    @FXML TextField txtFieldState;
    @FXML TextField txtFieldZip;
    @FXML TextField txtFieldBankAccount;

    public void createCorporateCustomer(javafx.event.ActionEvent event) throws IOException {
        if(DataLogic.getInstance().createCorporateCustomerAccount(txtFieldName.getText(), txtFieldStreetAddress1.getText(),
                txtFieldStreetAddress2.getText(), txtFieldCity.getText(), txtFieldState.getText(), txtFieldZip.getText(), txtFieldBankAccount.getText())){
            switchScreen(event, "home.fxml");
        }else{
            txtFieldName.setText("Failed");
        }
    }
}
