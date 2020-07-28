package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;

public class CorporateCheckoutController extends ScreenController {

    @FXML
    private TextField txtFieldBankAccountNumber;
    @FXML
    private ListView txtFieldAddOn;
    @FXML
    private ListView txtFieldCarInfo;

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "carReviewAddOns.fxml");
    }

    @FXML
    public void initialize() {
        txtFieldCarInfo.getItems().add(DataLogic.getInstance().getSelectedCarDescription());
        txtFieldAddOn.getItems().add(DataLogic.getInstance().getPriceSummary());
    }

    public void rentCar(javafx.event.ActionEvent event) throws IOException {
        if(DataLogic.getInstance().rentCarCorporate(txtFieldBankAccountNumber.getText())) {
            alertScreen("Congratulations", "You have successfully rented the car!", "", "Thank you!");
            switchScreen(event, "thankYou.fxml");
        } else {
            alertScreen("Failed", "We were unable to process your request", "You can only rent one car!", "Try Again!");
        }
    }
}
