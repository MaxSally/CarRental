package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.io.IOException;

public class UpdateCorporateCustomerAccountController extends ScreenController {

    @FXML
    TextField txtFieldName;
    @FXML
    TextField txtFieldStreetAddress1;
    @FXML
    TextField txtFieldStreetAddress2;
    @FXML
    TextField txtFieldCity;
    @FXML
    TextField txtFieldState;
    @FXML
    TextField txtFieldZip;
    @FXML
    TextField txtFieldBankAccount;



    public void updateCorporateCustomer(javafx.event.ActionEvent event) throws IOException {

        if(txtFieldName.getText() == null){
            alertScreen("Cannot Update Account", "Please enter account name that you wish to update", "", "Try Again");
        } else {
            if(!DataLogic.getInstance().hasCustomerName(txtFieldName.getText())){
                alertScreen("Cannot Update Account", "Please enter a valid account name", "", "Try Again");
            } else if(DataLogic.getInstance().setCorporateCustomer(txtFieldName.getText(), txtFieldStreetAddress1.getText(), txtFieldStreetAddress2.getText(),
                    txtFieldCity.getText(), txtFieldState.getText(), txtFieldZip.getText(), txtFieldBankAccount.getText()) ){
                alertScreen("Individual Account Update", "Account has been updated successfully", "", "Thank You");
                switchScreen(event, "home.fxml");
            }else{
                alertScreen("Cannot Update Account", "Something went wrong, please try again", "", "Try Again");
            }
        }
    }
}
