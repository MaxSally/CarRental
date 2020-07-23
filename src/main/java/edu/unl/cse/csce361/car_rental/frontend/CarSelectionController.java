package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.io.IOException;

public class CarSelectionController extends ScreenController {
    @FXML
    ListView listViewCar;

    @FXML
    private ChoiceBox<String> sortByPriceOptions;

    public void switchToFilterScreen(javafx.event.ActionEvent event) throws IOException {
        DataLogic.getInstance().resetCriteriaFilter();
        switchScreen(event, "filters.fxml");
    }
    
    @FXML
    public void initialize(){
        ObservableList<String> availableCar = FXCollections.observableArrayList(DataLogic.getInstance().getValidCarDescription());
        ObservableList<String> priceSortingChoices = FXCollections.observableArrayList("","High to Low", "Low to High");
        listViewCar.getItems().addAll(availableCar);
        sortByPriceOptions.setItems(priceSortingChoices);
    }
}
