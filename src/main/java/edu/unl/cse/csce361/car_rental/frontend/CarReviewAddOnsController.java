package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import org.hibernate.annotations.Check;

import javax.xml.crypto.Data;
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

    public void switchToFilterScreen(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().resetCriteriaFilter();
        switchScreen(event, "filters.fxml");
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
        reviewCarDetails.getItems().add(DataLogic.getInstance().getPriceSummary());
        //switchScreen();
    }
}
