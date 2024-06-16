package org.example.probs.Controllerr;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import org.example.probs.Services.ServiceClient;
import org.example.probs.Services.ServiceDepertment;
import org.example.probs.Services.ServiceProduct;
import org.example.probs.objects.Client;
import org.example.probs.objects.Department;
import org.example.probs.objects.Employee;
import org.example.probs.objects.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SecondController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab Clients;

    @FXML
    private TableColumn<Client, String> address;

    @FXML
    private Button Add;

    @FXML
    private TableView<Client> client;

    @FXML
    private TableColumn<Client, Integer> id_client;

    @FXML
    private TableColumn<Client, String> name;

    @FXML
    private TableColumn<Client, String> phone;

    @FXML
    private TableColumn<Client, String> surname;

    @FXML
    private TableColumn edit;
    @FXML
    private TextField TextSearch;

    @FXML
    void SearchKey(KeyEvent event) {
        this.SearchIno();
    }

    //PRODUCTS
    @FXML
    private Tab ProductTab;

    @FXML
    private TableColumn<Product, Integer> id_product;
    @FXML
    private TableColumn DeleteProduct;

    @FXML
    private TableColumn<Product, String> Name;

    @FXML
    private TableColumn<Product, Float> Price;

    @FXML
    private TableView<Product> Products;

    @FXML
    private Button AddProduct;

    @FXML
    void AddProduct(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/probs/AddProducts.fxml"));

        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            showInfoProducts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private TextField SerchProduct;

    @FXML
    void SerchProducts(KeyEvent ignoredEvent) {
        this.SearchInoProduct();
    }

    //Department
    @FXML
    private TableView<Department> Department;
    @FXML
    private TableColumn<Department, Integer> id_department;
    @FXML
    private TableColumn<Department, String> Name_department;
    @FXML
    private TableColumn<Department, String> Name_Manager;
    @FXML
    private TableColumn<org.example.probs.objects.Department, String> ComboxManager;


    private final ServiceClient dbHandler = new ServiceClient();
    private final ServiceProduct dbProduct = new ServiceProduct();
    private final ServiceDepertment dbDepartment = new ServiceDepertment();

    private static final ObservableList<Client> data = FXCollections.observableArrayList();
    private static final ObservableList<Product> data_products = FXCollections.observableArrayList();
    private static final ObservableList<Department> data_departments = FXCollections.observableArrayList();

    private static ObservableList<Employee> data_emloyee = FXCollections.observableArrayList();

    private static final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public ObservableList<Client> getData() {
        return data;
    }

    public ObservableList<Product> getData_products() {
        return data_products;
    }

    public ObservableList<Department> getData_departments() {
        return data_departments;
    }

    public ObservableList<Employee> getData_emloyee() {
        return data_emloyee;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editInfoClients();
        getDelete();
        getAdd();
        showInfoClients();
        showInfoProducts();
        getDeleteProduct();
        editInfoProducts();
        showInfoDepartments();
        editInfoDepartments();

    }

    //CLIENT
    private void showInfoClients() {
        data.clear();
        ResultSet clients = dbHandler.Show();
        try {
            while (clients.next()) {
                Client client1 = new Client(clients.getInt(1),
                        clients.getString(2),
                        clients.getString(3),
                        clients.getString(4),
                        clients.getString(5));
                data.add(client1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        id_client.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Client, String>("FirstName"));
        surname.setCellValueFactory(new PropertyValueFactory<Client, String>("LastName"));
        phone.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));
        address.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));

    }

    public void getAdd() {
        Add.setOnAction(actionEvent -> {
            Add.getScene().getWindow();
            FXMLLoader loder1 = new FXMLLoader();
            loder1.setLocation(getClass().getResource("/org/example/probs/AddClients.fxml"));

            try {
                loder1.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loder1.getRoot();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root));
            stage1.showAndWait();
            showInfoClients();
        });

    }

    public void getDelete() {
        Callback<TableColumn<Client, String>, TableCell<Client, String>> cellFactory = (param) ->
        {
            final TableCell<Client, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button editButton = new Button("DELETE");
                        editButton.setStyle("-fx-background-color: #FFCCCC;-fx-text-fill: black;");
                        editButton.setOnAction(event -> {

                            Client client = getTableView().getItems().get(getIndex());

                            dbHandler.Delete(client.getId());

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("You deleted a user named: " + client.getFirstName());
                            alert.show();
                            showInfoClients();

                        });
                        setGraphic(editButton);
                        setText(null);
                    }

                }


            };

            return cell;
        };

        edit.setCellFactory(cellFactory);

    }


    public void editInfoClients() {
        name.setCellFactory(TextFieldTableCell.<Client>forTableColumn());
        name.setOnEditCommit(event -> {

            Client client1 = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbHandler.editDate(client1.getId(), "Name", event.getNewValue());
                alert.setContentText("Operation completed successfully!");
                alert.show();
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoClients();
            }

        });

        surname.setCellFactory(TextFieldTableCell.<Client>forTableColumn());
        surname.setOnEditCommit(event -> {
            Client client1 = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbHandler.editDate(client1.getId(), "Surname", newValue);
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoClients();
            }

        });

        phone.setCellFactory(TextFieldTableCell.<Client>forTableColumn());
        phone.setOnEditCommit(event -> {
            Client client1 = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbHandler.editDate(client1.getId(), "Phnoe", newValue);
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoClients();
            }

        });
        address.setCellFactory(TextFieldTableCell.<Client>forTableColumn());
        address.setOnEditCommit(event -> {
            Client client1 = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbHandler.editDate(client1.getId(), "Address", newValue);
                alert.setContentText("Operation completed successfully!");
                alert.show();

            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoClients();
            }

        });

    }

    public void SearchIno() {
        String searchInfo = TextSearch.getText();
        data.clear();
        data.addAll(dbHandler.filterByName(searchInfo));

    }

    //PRODUCT
    private void showInfoProducts() {
        data_products.clear();
        ResultSet products = dbProduct.Show();
        try {
            while (products.next()) {
                Product product = new Product(products.getInt(1),
                        products.getString(2),
                        products.getFloat(3));
                data_products.addAll(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        id_product.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id_product"));
        Name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        Price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
    }


    public void getDeleteProduct() {
        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory = (param) ->
        {
            final TableCell<Product, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button editDelete = new Button("DELETE");
                        editDelete.setStyle("-fx-background-color: #FFCCCC;-fx-text-fill: black;");
                        editDelete.setOnAction(event -> {

                            Product product = getTableView().getItems().get(getIndex());

                            dbProduct.Delete(product.getId_product());

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("You deleted a product named: " + product.getName());
                            alert.show();
                            showInfoProducts();

                        });
                        setGraphic(editDelete);
                        setText(null);
                    }

                }


            };

            return cell;
        };

        DeleteProduct.setCellFactory(cellFactory);

    }

    public void editInfoProducts() {
        Name.setCellFactory(TextFieldTableCell.<Product>forTableColumn());
        Name.setOnEditCommit(event -> {

            Product product = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbProduct.editDate(product.getId_product(), "ProductName", event.getNewValue());
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoProducts();
            }

        });

        Price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        Price.setOnEditCommit(event -> {
            Product product = event.getTableView().getItems().get(event.getTablePosition().getRow());
            Float newValue = event.getNewValue();

            if (newValue != null && !newValue.equals(0.0f)) {
                try {
                    float newPrice = newValue.floatValue();
                    dbProduct.editDatePrice(product.getId_product(), "Price", newValue);
                    alert.setContentText("Operation completed successfully!");
                    alert.show();
                } catch (NumberFormatException e) {
                    alert.setContentText("Error, invalid value entered!");
                    alert.show();
                    showInfoProducts();
                }
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoProducts();
            }
        });

    }

    public void SearchInoProduct() {
        String searchInfo = SerchProduct.getText();
        data_products.clear();
        data_products.addAll(dbProduct.filterByName(searchInfo));

    }

    //DEPEPERTMENT
    private void showInfoDepartments() {
        data_departments.clear();
        ResultSet department = dbDepartment.Show();

        try {
            while (department.next()) {
                int departmentId = department.getInt("id");
                String departmentName = department.getString("name_department");
                int managerId = department.getInt("employee_id");
                String managerName = department.getString("name_employee");
                String Susrname = department.getString("Surname");
                String Address = department.getString("Address");
                String Phone = department.getString("Phone");

                Employee employee = new Employee();
                employee.setId_emloyee(managerId);
                employee.setName(managerName);
                employee.setSurname(Susrname);
                employee.setPhone(Phone);
                employee.setAddress(Address);

                Department department1 = new Department();
                department1.setId(departmentId);
                department1.setName(departmentName);
                department1.setManager(employee);
                employee.setDepartment(department1);
                ResultSet namManager = dbDepartment.AddEmployee(departmentId);
                ComboBox<String> NAME = new ComboBox<>();
                while (namManager.next()) {
                    NAME.getItems().add(namManager.getString("name_employee"));
                    NAME.getSelectionModel().select(department1.getManager().getName());
                }
                department1.setComboxManager(NAME);

                data_departments.add(department1);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        id_department.setCellValueFactory(new PropertyValueFactory<Department, Integer>("id"));
        Name_department.setCellValueFactory(new PropertyValueFactory<Department, String>("name"));

        ComboxManager.setCellValueFactory(new PropertyValueFactory<org.example.probs.objects.Department, String>("ComboxManager"));


    }

    private void editInfoDepartments() {
        Name_department.setCellFactory(TextFieldTableCell.<Department>forTableColumn());
        Name_department.setOnEditCommit(event -> {
            Department newdepartment = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbDepartment.editDate(newdepartment.getId(), "name_department", newValue);
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoDepartments();
            }
        });


    }
}







