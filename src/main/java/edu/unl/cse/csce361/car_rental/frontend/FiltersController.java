package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.backend.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;

public class FiltersController {

    ObservableList<String> modelChoices = FXCollections.observableArrayList("ILX", "Model 3", "Stag", "Montego", "Highlander", "Versa",
            "Pacifica", "Jetta", "Bolt", "Malibu", "Allegro", "Ranger");
    ObservableList<String> classChoices;
    ObservableList<String> colorChoices = FXCollections.observableArrayList("Black", "White", "Red", "Silver", "Yellow", "Blue",
            "Fuchsia", "Grey", "Cyan", "Magenta");
    ObservableList<Integer> numDoorChoices = FXCollections.observableArrayList(2, 3, 4, 5);
    ObservableList<String> fuelTypeChoices = FXCollections.observableArrayList("UNKNOWN", "OTHER", "GASOLINE", "DIESEL", "PLUGIN_ELECTRIC");
    ObservableList<String> transmissionChoices = FXCollections.observableArrayList("UNKNOWN", "OTHER", "AUTOMATIC", "MANUAL");
    ObservableList<String> fuelEconomyChoices = FXCollections.observableArrayList("< 30", "30 - 59", "60 - 89", "90 - 119", "> 120");

    @FXML
    private ChoiceBox<String> modelOptions;
    @FXML
    private ChoiceBox<String> classOptions;
    @FXML
    private ChoiceBox<String> colorOptions;
    @FXML
    private ChoiceBox<Integer> numDoorOptions;
    @FXML
    private ChoiceBox<String> fuelTypeOptions;
    @FXML
    private ChoiceBox<String> fuelEconomyOptions;
    @FXML
    private ChoiceBox<String> transmissionOptions;

    @FXML
    private void initialize() {
        List<String> vehicleClasses = new ArrayList<>();
        for(Model.VehicleClass vehicleClass : Model.VehicleClass.values()) {
            vehicleClasses.add(vehicleClass.toString());
        }
        classChoices = FXCollections.observableArrayList(vehicleClasses);
        modelOptions.setItems(modelChoices);
        classOptions.setItems(classChoices);
        colorOptions.setItems(colorChoices);
        numDoorOptions.setItems(numDoorChoices);
        fuelTypeOptions.setItems(fuelTypeChoices);
        fuelEconomyOptions.setItems(fuelEconomyChoices);
        transmissionOptions.setItems(transmissionChoices);
    }
}
