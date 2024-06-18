package org.example.probs.Controllerr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.probs.Services.ServiceClient;
import org.example.probs.Services.ServiceDepertment;
import org.example.probs.objects.Client;
import org.example.probs.objects.Department;

public class AddDepartmentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox InsertInfo;

    @FXML
    private TextField InsertName;

    @FXML
    private Button SAVE;

    ServiceDepertment DB = new ServiceDepertment();
    private static final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    void initialize() {
        SAVE.setOnAction(actionEvent -> {
            if (!InsertName.getText().isEmpty()) {
                DB.Add(new Department(InsertName.getText()));
                SAVE.getScene().getWindow().hide();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();

            }
        });
    }

}
