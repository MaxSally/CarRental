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
        getListView();
    }

    public void removeCarButton(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().removeCar(listView.getSelectionModel().getSelectedIndex());
        alertScreen("Car Removed", "Car was successfully removed from inventory", "", "Thank You");
        getListView();
    }

    public void moveToGarageButton(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().moveToGarage(listView.getSelectionModel().getSelectedIndex());
        alertScreen("Car Moved To Garage", "Car was successfully moved to the garage", "", "Thank You");
        getListView();
    }

    public void moveOutOfGarageButton(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().moveOutOfGarage(listView.getSelectionModel().getSelectedIndex());
        alertScreen("Car Moved Out Of Garage", "Car was successfully moved out of garage", "", "Thank You");
        getListView();
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "managerOptions.fxml");
    }

    private void getListView() {
        ObservableList<String> availableCar = FXCollections.observableArrayList(DataLogic.getInstance().getCarDescriptionManager());
        listView.getItems().clear();
        listView.getItems().addAll(availableCar);
    }
}
