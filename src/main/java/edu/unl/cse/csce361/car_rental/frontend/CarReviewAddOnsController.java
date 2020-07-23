package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import java.io.IOException;

public class CarReviewAddOnsController extends ScreenController {

    public void switchToFilterScreen(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().resetCriteriaFilter();
        switchScreen(event, "filters.fxml");
    }
}
