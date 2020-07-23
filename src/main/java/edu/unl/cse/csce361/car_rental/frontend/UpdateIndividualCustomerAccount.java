package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UpdateIndividualCustomerAccount extends ScreenController {

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



    public void updateIndividualCustomer(javafx.event.ActionEvent event) throws IOException {

        if(txtFieldName.getText() == null){
            invalidAlert("Cannot Update Account", "Please enter account name that you wish to update", "", "Try Again");
        } else {
            if(!DataLogic.getInstance().hasCustomerName(txtFieldName.getText())){
                invalidAlert("Cannot Update Account", "Please enter a valid account name", "", "Try Again");
            } else if(DataLogic.getInstance().setAddress(txtFieldName.getText(), txtFieldStreetAddress1.getText(), txtFieldStreetAddress2.getText(),
                    txtFieldCity.getText(), txtFieldState.getText(), txtFieldZip.getText())){
                invalidAlert("Individual Account Update", "Account has been updated successfully", "", "Thank You");
                switchScreen(event, "home.fxml");
            }else{
                invalidAlert("Cannot Update Account", "Something went wrong, please try again", "", "Try Again");
            }
        }
    }
}
