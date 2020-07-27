package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.io.IOException;

public class CarReviewAddOnsController extends ScreenController {

    @FXML
    ListView reviewCarDetails;
    @FXML
    CheckBox rentalInsurance;
    @FXML
    CheckBox satelliteRadio;
    @FXML
    CheckBox sunShade;

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().resetSelectedCars();
        switchScreen(event, "carSelection.fxml");
    }
    @FXML
    public void initialize(){
        reviewCarDetails.getItems().add(DataLogic.getInstance().getSelectedCarDescription());
    }

    public void switchToCheckoutButton(javafx.event.ActionEvent event) throws IOException {
        if(rentalInsurance.isSelected()) {
            DataLogic.getInstance().addNewAddOn("Rental Insurance", 25);
        }
        if(satelliteRadio.isSelected()) {
            DataLogic.getInstance().addNewAddOn("Satellite Radio", 10);
        }
        if(sunShade.isSelected()){
            DataLogic.getInstance().addNewAddOn("Dashboard Sunshade", 5);
        }
        if(DataLogic.getInstance().isIndividualCustomerType()) {
            switchScreen(event, "individualCheckout.fxml");
        } else if(DataLogic.getInstance().isCorporateCustomerType()) {
            switchScreen(event, "corporateCheckout.fxml");
        } else {
            alertScreen("Unsuccessful", "Something went wrong", "We don't seem to recognize you", "Try Again!");
        }
    }
}
