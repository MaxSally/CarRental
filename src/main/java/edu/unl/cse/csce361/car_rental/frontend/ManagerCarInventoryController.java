package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ManagerCarInventoryController extends ScreenController {

    @FXML
    ListView listView;

    @FXML
    public void initialize(){
        ObservableList<String> availableCar = FXCollections.observableArrayList(DataLogic.getInstance().getValidCarDescription());
        listView.getItems().addAll(availableCar);
    }

    public void removeCarButton(javafx.event.ActionEvent event) throws IOException {

    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "managerOptions.fxml");
    }
}
