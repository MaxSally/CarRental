package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class CarSelectionController extends ScreenController {
    @FXML
    ListView listViewCar;

    @FXML
    private ChoiceBox<String> sortByPriceOptions;

    private ObservableList<String> priceSortingChoices = FXCollections.observableArrayList("","High to Low", "Low to High");
    private ObservableList<String> availableCar;

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "filters.fxml");
    }
    
    @FXML
    public void initialize() {
        ObservableList<String> availableCar = FXCollections.observableArrayList(DataLogic.getInstance().getValidCarDescription());
        listViewCar.getItems().addAll(availableCar);
        sortByPriceOptions.setValue("");
        sortByPriceOptions.setItems(priceSortingChoices);
    }

    public void sortByPrice() {
        availableCar = null;
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

    public void goHomeButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }


    public void goToAddOn(javafx.event.ActionEvent event) throws  IOException {
        DataLogic.getInstance().addSelectedCar(listViewCar.getSelectionModel().getSelectedIndex());
        switchScreen(event, "carReviewAddOns.fxml");
    }
}
