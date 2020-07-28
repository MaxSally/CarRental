package edu.unl.cse.csce361.car_rental.frontend;

import java.io.IOException;

public class ThankYouController extends ScreenController {

    public void thankYouButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }
}
