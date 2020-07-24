package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ManagerOptionsController extends ScreenController{
    @FXML
    TextField txtFieldUpdateAccountName;
    @FXML
    Label errorMessageUpdateAccount;
    @FXML TextField txtFieldNegotiatedRate;

    public void createCorporateCustomer(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "corporateCustomerAccountByManager.fxml");
    }

    public void updateCorporateCustomer(javafx.event.ActionEvent event) throws  IOException{
        errorMessageUpdateAccount.setVisible(true);
        if(txtFieldUpdateAccountName.getText() == null){
            errorMessageUpdateAccount.setText("Please enter account name that you wish to update");
        }else{
            if(!DataLogic.getInstance().hasCustomerName(txtFieldUpdateAccountName.getText())){
                errorMessageUpdateAccount.setText("Invalid account name");
            }else if(DataLogic.getInstance().setCorporateCustomerByManager(txtFieldUpdateAccountName.getText(), "", "", "", "", "", "", Double.parseDouble(txtFieldNegotiatedRate.getText()))){
                errorMessageUpdateAccount.setText("Update successfully");
            }else{
                errorMessageUpdateAccount.setText("Cannot set negotiated rate to this account");
            }
        }
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }
}
