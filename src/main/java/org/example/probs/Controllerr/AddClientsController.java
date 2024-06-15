package org.example.probs.Controllerr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.probs.Services.ServiceClient;
import org.example.probs.objects.Client;

public class AddClientsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField InsertAddress;

    @FXML
    private TextField InsertName;

    @FXML
    private TextField InsertPhone;

    @FXML
    private TextField InsertSurname;

    @FXML
    private Button Save;

    ServiceClient DB= new ServiceClient();
    private static final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    void initialize() {
        Save.setOnAction(actionEvent -> {
            if (!InsertName.getText().isEmpty() && !InsertSurname.getText().isEmpty()
                    && !InsertPhone.getText().isEmpty() && !InsertAddress.getText().isEmpty()) {
                DB.Add(new Client(InsertName.getText(),InsertSurname.getText(),InsertPhone.getText(),InsertAddress.getText()));
                Save.getScene().getWindow().hide();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();

            }
        });
    }



}



