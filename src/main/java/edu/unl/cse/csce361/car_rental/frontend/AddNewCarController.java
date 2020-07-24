package edu.unl.cse.csce361.car_rental.frontend;

import edu.unl.cse.csce361.car_rental.rental_logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class AddNewCarController extends ScreenController {

    ObservableList<String> modelChoices = FXCollections.observableArrayList("","ILX", "Model 3", "Stag", "Montego", "Highlander", "Versa",
            "Pacifica", "Jetta", "Bolt", "Malibu", "Allegro", "Ranger");

    @FXML
    private ChoiceBox<String> modelOptions;

    @FXML
    private void initialize() {
        modelOptions.setValue("");
        modelOptions.setItems(modelChoices);
    }

    public void cancelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "home.fxml");
    }

    public void addNewModelButton(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "createNewModel.fxml");
    }

}
