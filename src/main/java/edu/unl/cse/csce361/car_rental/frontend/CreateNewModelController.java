package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.backend.Model;
import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CreateNewModelController extends ScreenController {

    ObservableList<String> classChoices;
    ObservableList<Integer> numDoorChoices = FXCollections.observableArrayList(null, 2, 3, 4, 5);
    ObservableList<String> fuelTypeChoices;
    ObservableList<String> transmissionChoices;

    @FXML
    private TextField txtFieldManufacturer;
    @FXML
    private TextField txtFieldModelName;
    @FXML
    private TextField txtFieldFuelEconomy;
    @FXML
    private ChoiceBox<String> classOptions;
    @FXML
    private ChoiceBox<Integer> numDoorOptions;
    @FXML
    private ChoiceBox<String> fuelTypeOptions;
    @FXML
    private ChoiceBox<String> transmissionOptions;

    @FXML
    private void initialize() {
        classChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllVehicleClass());
        fuelTypeChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllFuelType());
        transmissionChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllTransmission());
        classOptions.setValue("");
        classOptions.setItems(classChoices);
        numDoorOptions.setValue(null);
        numDoorOptions.setItems(numDoorChoices);
        fuelTypeOptions.setValue("");
        fuelTypeOptions.setItems(fuelTypeChoices);
        transmissionOptions.setValue("");
        transmissionOptions.setItems(transmissionChoices);
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "addNewCar.fxml");
    }

    public void createModelButton(javafx.event.ActionEvent event) throws IOException {
        Integer fuelEconomy = null;
        try {
            fuelEconomy = parseInt(txtFieldFuelEconomy.getText());
        } catch (NumberFormatException e) {
            System.out.println("Number Format Exception" + e);
            alertScreen("Cannot Accept Fuel Economy", "Please enter a value for fuel economy", "It cannot be left blank",
                    "Try Again!");
            return;
        }
        if(DataLogic.getInstance().hasModelName(txtFieldModelName.getText())) {
            alertScreen("Cannot Create Account", "It seems the model name you entered already exists!",
                    "Please create a different model", "Try Again");
        } else if(createNewCarModelValidation(txtFieldManufacturer.getText(), txtFieldModelName.getText(), classOptions.getValue(),
                numDoorOptions.getValue(), fuelTypeOptions.getValue(), transmissionOptions.getValue(), fuelEconomy)) {
            if(DataLogic.getInstance().createModel(txtFieldManufacturer.getText(), txtFieldModelName.getText(), Model.VehicleClass.valueOf(classOptions.getValue()),
                    numDoorOptions.getValue(), Model.Transmission.valueOf(transmissionOptions.getValue()), Model.Fuel.valueOf(fuelTypeOptions.getValue()),
                    fuelEconomy)) {
                alertScreen("New Car Added", "The car was successfully added to inventory", "",
                        "Thank you!");
                switchScreen(event, "addNewCar.fxml");
            } else {
                alertScreen("Model Cannot Be Added", "Something went wrong", "Please try again!", "Try Again");
            }
        } else {
            alertScreen("Cannot Create Model", "Please make sure to fill all fields", "", "Try Again");
        }
    }
}
