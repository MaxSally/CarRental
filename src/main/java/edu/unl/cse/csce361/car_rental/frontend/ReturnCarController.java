package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ReturnCarController extends ScreenController{

    @FXML
    private ListView listViewVehicle;
    @FXML
    private TextArea textAreaAmountOwed;

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }

    public void returnButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "rentalReceipt.fxml");
    }

    @FXML
    public void initialize(){
        listViewVehicle.getItems().add(DataLogic.getInstance().getSelectedCarDescription());
    }
}
