package org.example.probs.Controllerr;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.probs.Services.ServiceClient;
import org.example.probs.Services.ServiceContract;
import org.example.probs.Services.ServiceEmployee;
import org.example.probs.Services.ServiceProduct;
import org.example.probs.objects.Client;
import org.example.probs.objects.Contract;
import org.example.probs.objects.Employee;
import org.example.probs.objects.Product;

public class AddContractsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox clients;

    @FXML
    private ComboBox employee;

    @FXML
    private ComboBox products;
    @FXML
    private TextField DAY;

    @FXML
    private TextField MONTH;

    @FXML
    private Button SAVE;

    @FXML
    private TextField YEAR;



    @FXML
    void Select_Employee(ActionEvent event) {
     developer=(Employee)employee.getSelectionModel().getSelectedItem();
    }

    @FXML
    void Select_client(ActionEvent event) {
    client= (Client)clients.getSelectionModel().getSelectedItem();

    }

    @FXML
    void Select_product(ActionEvent event) {
        product=(Product)products.getSelectionModel().getSelectedItem();
    }
    private ObservableList<Client> clients1 = FXCollections.observableArrayList();
    private ObservableList<Product> products1 = FXCollections.observableArrayList();
    private ObservableList<Employee> employees1 = FXCollections.observableArrayList();

    private final ServiceEmployee dbEmployee = new ServiceEmployee();
    private final ServiceClient dbHandler = new ServiceClient();
    private final ServiceProduct dbProduct = new ServiceProduct();
    private final ServiceContract dbContract = new ServiceContract();

    private  Client client = new Client();
    private Product product = new Product();
    private Employee developer = new Employee();

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    void initialize() {

       showInfoClients();
       showInfoProducts();
       showInfoEmployees();

        SAVE.setOnAction(actionEvent -> {
            try {
                int year = Integer.parseInt(YEAR.getText());
                int month = Integer.parseInt(MONTH.getText());
                int day = Integer.parseInt(DAY.getText());

                LocalDate date = LocalDate.of(year, month, day);

                if (client != null && product != null && developer != null) {
                    Contract contract = new Contract(developer, product, client, date);
                    dbContract.Add(contract);
                    SAVE.getScene().getWindow().hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Error, field is empty!");
                    alert.show();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid date format or empty fields!");
                alert.show();
            }
            catch (DateTimeException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid date format!");
                alert.show();
            }
        });
    }

    private void showInfoClients() {
        clients1.clear();

        ResultSet clie = dbHandler.Show();

        try {
            while (clie.next()) {
                int id = clie.getInt(1);
                String name = clie.getString(2);
                String surname = clie.getString(3);

                Client client = new Client();
                client.setId(id);
                client.setFirstName(name);
                client.setLastName(surname);
                clients1.add(client);

            }
            clients.getItems().addAll(clients1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showInfoProducts() {
        products1.clear();
        ResultSet product_query = dbProduct.Show();
        try {
            while (product_query.next()) {
                Product product = new Product(product_query.getInt(1),
                        product_query.getString(2),
                        product_query.getFloat(3));

                products1.add(product);
            }
            products.getItems().addAll(products1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void showInfoEmployees() {
        products1.clear();
        ResultSet employee_query = dbEmployee.ShowEmployee();
        try {
            while (employee_query.next()) {
                Employee employee1 = new Employee();
                employee1.setId_emloyee(employee_query.getInt(1));
                employee1.setName(employee_query.getString(2));
                employee1.setSurname(employee_query.getString(3));

                employees1.add(employee1);
            }
            employee.getItems().addAll(employees1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}



