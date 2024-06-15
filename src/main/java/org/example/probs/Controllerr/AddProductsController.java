package org.example.probs.Controllerr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.probs.Services.ServiceClient;
import org.example.probs.Services.ServiceProduct;
import org.example.probs.objects.Product;

public class AddProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox InsertInfo;

    @FXML
    private TextField InsertName;

    @FXML
    private TextField InsertPrice;

    @FXML
    private Button Save;

    ServiceProduct DB= new ServiceProduct();
    private static final Alert alert = new Alert(Alert.AlertType.INFORMATION);


    @FXML
    void initialize() {
        Save.setOnAction(actionEvent -> {
            if (!InsertName.getText().isEmpty() && !InsertPrice.getText().isEmpty()) {
                String price= InsertPrice.getText();
               DB.Add(new Product(InsertName.getText(), Float.parseFloat(price)));
                Save.getScene().getWindow().hide();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();

            }
        });

    }

}
