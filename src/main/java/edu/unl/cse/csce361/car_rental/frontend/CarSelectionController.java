package edu.unl.cse.csce361.car_rental.frontend;

import java.io.IOException;

public class CarSelectionController extends ScreenController {

    public void switchToFilterScreen(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "filters.fxml");
    }
}
