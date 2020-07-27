package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class ManagerOptionsController extends ScreenController{
    @FXML
    TextField txtFieldUpdateAccountName;
    @FXML
    Label errorMessageUpdateAccount;
    @FXML
    TextField txtFieldNegotiatedRate;
    @FXML
    TextField txtFieldDailyRate;
    @FXML
    ChoiceBox<String> classOptions;

    ObservableList<String> classChoices;

    @FXML
    private void initialize() {
        classChoices = FXCollections.observableArrayList(DataLogic.getInstance().getAllVehicleClass());
        classOptions.setValue("");
        classOptions.setItems(classChoices);
    }

    public void createCorporateCustomer(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "corporateCustomerAccountByManager.fxml");
    }

    public void updateCorporateCustomer(javafx.event.ActionEvent event) throws  IOException {
        errorMessageUpdateAccount.setVisible(true);
        if(txtFieldUpdateAccountName.getText() == null){
            errorMessageUpdateAccount.setText("Please enter account name that you wish to update");
        }else{
            if(!DataLogic.getInstance().hasCustomerName(txtFieldUpdateAccountName.getText())){
                errorMessageUpdateAccount.setText("Invalid account name");
            }else if(DataLogic.getInstance().setCorporateCustomerByManager(txtFieldUpdateAccountName.getText(), "", "", "", "", "", "", Double.parseDouble(txtFieldNegotiatedRate.getText()))){
                errorMessageUpdateAccount.setText("Update successfully");
            }else{
                errorMessageUpdateAccount.setText("Cannot set negotiated rate to this account");
            }
        }
    }

    public void updateDailyRate(javafx.event.ActionEvent event) throws  IOException {
        Integer dailyRate = null;
        try {
            dailyRate = parseInt(txtFieldDailyRate.getText());
        } catch (NumberFormatException e) {
            System.out.println("Number Format Exception" + e);
            alertScreen("Cannot Accept Daily Rate", "Please enter a whole number for the daily rate", "",
                    "Try Again!");
            return;
        }
        if(txtFieldDailyRate.getText() != null && !classOptions.getValue().equals("")) {
            DataLogic.getInstance().setDailyRateByManager(classOptions.getValue(), dailyRate);
            alertScreen("Daily Rate Update", "The daily rate was successfully updated", "", "Thank you!");
        } else {
            alertScreen("Error", "Please choose a class type", "Please enter a daily rate", "Try Again!");
        }
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }

    public void addNewCar(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "addNewCar.fxml");
    }

    public void managerCarInventoryButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "managerCarInventory.fxml");
    }
}
