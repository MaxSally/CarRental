package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarSelectionController extends ScreenController {
    @FXML
    ListView listViewCar;

    @FXML
    private ChoiceBox<String> sortByPriceOptions;
    @FXML
    private Label txtCarSelection;

    private ObservableList<String> priceSortingChoices = FXCollections.observableArrayList("","High to Low", "Low to High");

    public void switchToFilterScreen(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().resetCriteriaFilter();
        switchScreen(event, "filters.fxml");
    }
    
    @FXML
    public void initialize(){
        ObservableList<String> availableCar = FXCollections.observableArrayList(DataLogic.getInstance().getValidCarDescription());
        listViewCar.getItems().addAll(availableCar);
        sortByPriceOptions.setValue("");
        sortByPriceOptions.setItems(priceSortingChoices);
    }

    public void sortByPrice(){
        ObservableList<String> availableCar = null;
        switch (priceSortingChoices.indexOf(sortByPriceOptions.getValue())){
            case 1:
                availableCar = FXCollections.observableArrayList(DataLogic.getInstance().sortByPrice(false));
                break;
            case 2:
                availableCar = FXCollections.observableArrayList(DataLogic.getInstance().sortByPrice(true));
                break;
            default:
        }
        listViewCar.getItems().clear();
        listViewCar.getItems().addAll(availableCar);
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }
}
