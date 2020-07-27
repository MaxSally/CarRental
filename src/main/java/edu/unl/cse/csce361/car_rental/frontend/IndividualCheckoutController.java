package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;

import java.io.IOException;

public class IndividualCheckoutController extends ScreenController {

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "carReviewAddOns.fxml");
    }
}
