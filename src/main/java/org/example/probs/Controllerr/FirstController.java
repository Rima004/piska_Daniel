package org.example.probs.Controllerr;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class FirstController {

    @FXML // fx:id="robot"
    private ImageView robot; // Value injected by FXMLLoader

    @FXML // fx:id="robot1"
    private ImageView robot1; // Value injected by FXMLLoader

    @FXML // fx:id="start"
    private Button start; // Value injected by FXMLLoader

    @FXML
    void initialize() {
        assert start != null : "fx:id=\"start\" was not injected: check your FXML file 'main.fxml'.";
        start.setOnAction(actionEvent -> {
            start.getScene().getWindow().hide();
            FXMLLoader loder=new FXMLLoader();
            loder.setLocation(getClass().getResource("/org/example/probs/Menu.fxml"));

            try {
                loder.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root=loder.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

    }

}
