package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import static java.lang.Integer.parseInt;

public class AddNewCarController extends ScreenController {

    ObservableList<String> modelChoices;
    ObservableList<String> colorChoices;

    @FXML
    private ChoiceBox<String> modelOptions;
    @FXML
    private ChoiceBox<String> colorOptions;
    @FXML
    private TextField txtFieldLicensePlateNumber;
    @FXML
    private TextField txtFieldVIN;
    @FXML
    private TextField txtFieldDailyRate;

    @FXML
    private void initialize() {
        modelChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllModels());
        colorChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllColors());
        modelOptions.setValue("");
        modelOptions.setItems(modelChoices);
        colorOptions.setValue("");
        colorOptions.setItems(colorChoices);
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }

    public void addNewModelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "createNewModel.fxml");
    }

    public void addNewCarButton(javafx.event.ActionEvent event) throws IOException {
        if(addNewCarValidation(modelOptions.getValue(), colorOptions.getValue(), txtFieldLicensePlateNumber.getText(),
                txtFieldVIN.getText())) {
            if(DataLogic.getInstance().createCar(modelOptions.getValue(), colorOptions.getValue(), txtFieldLicensePlateNumber.getText(),
                    txtFieldVIN.getText(), null, null)) {
                alertScreen("New Car Added", "The car was successfully added to inventory", "",
                        "Thank you!");
                switchScreen(event, "managerOptions.fxml");
            } else {
                alertScreen("Car Cannot Be Added", "Something went wrong", "Please try again!", "Try Again");
            }
        } else {
            alertScreen("Cannot Add Car", "Please fill in all fields", "Please follow the guidelines listed for each field", "Try Again");
        }
    }
}
