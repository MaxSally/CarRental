package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.backend.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.isEmptyString;

public abstract class ScreenController {

    public void switchScreen(ActionEvent event, String screenName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(screenName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void alertScreen(String title, String message, String message2, String buttonLabel) {

        Stage window = new Stage();
        //application modality allows the application and screen below to remain open,
        //but non-functional until the alert screen closes
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(150);
        window.setMinWidth(350);

        Label label = new Label();
        label.setText(message);
        Label label2 = new Label();
        label2.setText(message2);

        Button tryAgain = new Button(buttonLabel);
        tryAgain.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, label2, tryAgain);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public boolean addressFieldValidation(String streetAddress1, String city, String state, String zipCode) {
        if(streetAddress1.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean createNewCarModelValidation(String manufacturer, String model, String vehicleClass, Integer numDoorOptions,
                                               String fuelTypeOptions, String transmissionOption, Integer fuelEconomy) {
        return !(isEmptyString(manufacturer) || isEmptyString(model) || isEmptyString(vehicleClass) || numDoorOptions == null ||
                isEmptyString(fuelTypeOptions) || isEmptyString(transmissionOption) || fuelEconomy == null);
    }
}
