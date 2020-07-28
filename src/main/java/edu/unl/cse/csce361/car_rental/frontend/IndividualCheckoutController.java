package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;

public class IndividualCheckoutController extends ScreenController {

    @FXML
    private TextField txtFieldCardNumber;
    @FXML
    private TextField txtFieldCVV;
    @FXML
    private ChoiceBox<Integer> expirationMonthOptions;
    @FXML
    private ChoiceBox<Integer> expirationYearOptions;
    @FXML
    private ListView txtFieldAddOn;
    @FXML
    private ListView txtFieldCarInfo;

    ObservableList<Integer> expirationMonthChoices = FXCollections.observableArrayList(null,1,2,3,4,5,6,7,8,9,10,11,12);
    ObservableList<Integer> expirationYearChoices = FXCollections.observableArrayList(null,2020,2021,2022,2023,2024,2025);

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "carReviewAddOns.fxml");
    }

    @FXML
    public void initialize(){
        txtFieldCarInfo.getItems().add(DataLogic.getInstance().getSelectedCarDescription());
        txtFieldAddOn.getItems().add(DataLogic.getInstance().getPriceSummary());
        expirationMonthOptions.setItems(expirationMonthChoices);
        expirationYearOptions.setItems(expirationYearChoices);
    }

    public void rentCar(javafx.event.ActionEvent event) throws IOException {
        if(DataLogic.getInstance().rentCarIndividual(txtFieldCardNumber.getText(), expirationMonthOptions.getValue(), expirationYearOptions.getValue(),
                txtFieldCVV.getText())) {
            alertScreen("Congratulations", "You have successfully rented the car!", "", "Thank you!");
            switchScreen(event, "thankYou.fxml");
        } else {
            alertScreen("Failed", "We were unable to process your request", "You can only rent one car!", "Try Again!");
        }
    }


}
