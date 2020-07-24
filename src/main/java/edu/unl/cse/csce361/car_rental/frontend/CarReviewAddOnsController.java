package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import javax.xml.crypto.Data;
import java.io.IOException;

public class CarReviewAddOnsController extends ScreenController {

    @FXML
    ListView reviewCarDetails;
    @FXML ListView addOnOptions;

    public void switchToFilterScreen(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().resetCriteriaFilter();
        switchScreen(event, "filters.fxml");
    }
    @FXML
    public void initialize(){
        reviewCarDetails.getItems().add(DataLogic.getInstance().getPriceSummary());
    }
}
