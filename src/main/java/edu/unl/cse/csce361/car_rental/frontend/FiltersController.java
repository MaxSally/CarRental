package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.backend.Model;
import edu.unl.cse.csce361.car_rental.backend.ValidationUtil;
import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.isEmptyString;

public class FiltersController extends ScreenController{

    ObservableList<String> modelChoices;
    ObservableList<String> classChoices;
    ObservableList<String> colorChoices;
    ObservableList<Integer> numDoorChoices = FXCollections.observableArrayList(null, 2, 3, 4, 5);
    ObservableList<String> fuelTypeChoices;
    ObservableList<String> transmissionChoices;
    ObservableList<String> fuelEconomyChoices = FXCollections.observableArrayList(List.of("","< 30", "30 - 59", "60 - 89", "90 - 119", "> 120"));

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
        modelChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllModels());
        colorChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllColors());
        classChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllVehicleClass());
        fuelTypeChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllFuelType());
        transmissionChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllTransmission());
        modelOptions.setValue("");
        modelOptions.setItems(modelChoices);
        classOptions.setValue("");
        classOptions.setItems(classChoices);
        colorOptions.setValue("");
        colorOptions.setItems(colorChoices);
        numDoorOptions.setValue(null);
        numDoorOptions.setItems(numDoorChoices);
        fuelTypeOptions.setValue("");
        fuelTypeOptions.setItems(fuelTypeChoices);
        fuelEconomyOptions.setValue("");
        fuelEconomyOptions.setItems(fuelEconomyChoices);
        transmissionOptions.setValue("");
        transmissionOptions.setItems(transmissionChoices);
        DataLogic instance = DataLogic.getInstance();
        refillCriteria(classChoices, classOptions, instance.getCriteriaVehicleClass());
        refillCriteria(modelChoices, modelOptions, instance.getCriteriaVehicleModel());
        refillCriteria(transmissionChoices, transmissionOptions, instance.getCriteriaTransmission());
        refillCriteria(colorChoices, colorOptions, instance.getCriteriaColor());
        refillCriteria(fuelTypeChoices, fuelTypeOptions, instance.getCriteriaFuelType());
        refillCriteria(numDoorChoices, numDoorOptions, instance.getCriteriaNumberOfDoor());
        int previousSelectedMinFuelEconomy = instance.getCriteriaMinFuelEconomy();
        int previousSelectedMaxFuelEconomy = instance.getCriteriaMaxFuelEconomy();
        if(previousSelectedMaxFuelEconomy == 150 && previousSelectedMinFuelEconomy == 0){
            fuelEconomyOptions.setValue("");
        }else{
            fuelEconomyOptions.setValue(fuelEconomyChoices.get(previousSelectedMinFuelEconomy/30 + 1));
        }
    }

    private <E, Object> void refillCriteria(ObservableList<E> choiceList, ChoiceBox<Object> selectedChoiceBox, Object previousSelectedChoice){
        if(choiceList.contains(previousSelectedChoice)){
            selectedChoiceBox.setValue(previousSelectedChoice);
        }
    }

    public void moveToCarSelection(ActionEvent event) throws IOException {
        DataLogic instance = DataLogic.getInstance();
        instance.setFilterClass(!isEmptyString(classOptions.getValue())?classOptions.getValue():"");
        instance.setFilterColor(!isEmptyString(colorOptions.getValue())?colorOptions.getValue():"");
        instance.setFilterTransmission(!isEmptyString(transmissionOptions.getValue())?transmissionOptions.getValue():"");
        instance.setFilterModel(!isEmptyString(modelOptions.getValue())?modelOptions.getValue():"");
        instance.setFilterFuelType(!isEmptyString(fuelTypeOptions.getValue())?fuelTypeOptions.getValue():"");
        instance.setFilterNumberOfDoor(numDoorOptions.getValue() != null?numDoorOptions.getValue():null);
        if(!isEmptyString(fuelEconomyOptions.getValue())){
            int minFuelEconomy, maxFuelEconomy;
            switch (fuelEconomyChoices.indexOf(fuelEconomyOptions.getValue())){
                case 1:
                    minFuelEconomy = 0;
                    maxFuelEconomy = 29;
                    break;
                case 2:
                    minFuelEconomy = 30;
                    maxFuelEconomy = 59;
                    break;
                case 3:
                    minFuelEconomy = 60;
                    maxFuelEconomy = 89;
                    break;
                case 4:
                    minFuelEconomy = 90;
                    maxFuelEconomy = 119;
                    break;
                case 5:
                    minFuelEconomy = 120;
                    maxFuelEconomy = 150;
                    break;
                default:
                    minFuelEconomy = 0;
                    maxFuelEconomy = 150;
                    break;
            }
            instance.setFilterFuelEconomy(minFuelEconomy, maxFuelEconomy);
        }
        switchScreen(event, "carSelection.fxml");
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }

    public void returnCarButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "returnCar.fxml");
    }

}
