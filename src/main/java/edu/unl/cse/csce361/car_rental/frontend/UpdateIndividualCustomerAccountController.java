package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.io.IOException;

public class UpdateIndividualCustomerAccountController extends ScreenController {

    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldStreetAddress1;
    @FXML
    private TextField txtFieldStreetAddress2;
    @FXML
    private TextField txtFieldCity;
    @FXML
    private TextField txtFieldState;
    @FXML
    private TextField txtFieldZip;
    @FXML
    private TextField txtFieldCardNumber;
    @FXML
    private TextField txtFieldCVV;
    @FXML
    private ChoiceBox<Integer> expirationMonthOptions;
    @FXML
    private ChoiceBox<Integer> expirationYearOptions;

    ObservableList<Integer> expirationMonthChoices = FXCollections.observableArrayList(null,1,2,3,4,5,6,7,8,9,10,11,12);
    ObservableList<Integer> expirationYearChoices = FXCollections.observableArrayList(null,2020,2021,2022,2023,2024,2025);

    @FXML
    private void initialize() {
        expirationMonthOptions.setItems(expirationMonthChoices);
        expirationYearOptions.setItems(expirationYearChoices);
    }


    public void updateIndividualCustomer(javafx.event.ActionEvent event) throws IOException {
        if(txtFieldName.getText() == null){
            alertScreen("Cannot Update Account", "Please enter account name that you wish to update", "", "Try Again");
        } else {
            if(!DataLogic.getInstance().hasCustomerName(txtFieldName.getText())){
                alertScreen("Cannot Update Account", "Please enter a valid account name", "", "Try Again");
            } else if(DataLogic.getInstance().setIndividualCustomer(txtFieldName.getText(), txtFieldStreetAddress1.getText(), txtFieldStreetAddress2.getText(),
                    txtFieldCity.getText(), txtFieldState.getText(), txtFieldZip.getText(), txtFieldCardNumber.getText(), txtFieldCVV.getText(),
                    expirationMonthOptions.getValue(), expirationYearOptions.getValue())){
                alertScreen("Individual Account Update", "Account has been updated successfully", "", "Thank You");
                switchScreen(event, "home.fxml");
            }else{
                alertScreen("Cannot Update Account", "Something went wrong, please try again", "", "Try Again");
            }
        }
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }
}
