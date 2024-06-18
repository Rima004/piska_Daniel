package org.example.probs.Controllerr;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import org.example.probs.Services.ServiceClient;
import org.example.probs.Services.ServiceDepertment;
import org.example.probs.Services.ServiceEmployee;
import org.example.probs.Services.ServiceProduct;
import org.example.probs.objects.Client;
import org.example.probs.objects.Department;
import org.example.probs.objects.Employee;
import org.example.probs.objects.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
    private TableColumn<org.example.probs.objects.Department, ComboBox<Employee>> ComboxManager;
    @FXML
    private TableColumn DeleteDepartment;

    @FXML
    void Add_Department(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/probs/AddDepartment.fxml"));

        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            showInfoDepartments();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private TextField SearchDepartmnet;

    @FXML
    void SerachDepartment(KeyEvent event) {
        this.SearchInfoDepartment();
    }


    //EMPLOYEE
    @FXML
    private TableView<Employee> TableEmployee;
    @FXML
    private TableColumn<Employee, Integer> id_employee;
    @FXML
    private TableColumn<Employee, String> Name_employee;
    @FXML
    private TableColumn<Employee, String> Surname_Employee;
    @FXML
    private TableColumn<Employee, String> Address_employee;
    @FXML
    private TableColumn<Employee, String> Phone_employee;
    @FXML
    private TableColumn<Employee, ComboBox<Department>> Department_employee;
    @FXML
    private TableColumn Delete_employee;


    private final ServiceEmployee dbEmployee = new ServiceEmployee();
    private final ServiceClient dbHandler = new ServiceClient();
    private final ServiceProduct dbProduct = new ServiceProduct();
    private final ServiceDepertment dbDepartment = new ServiceDepertment();

    private static final ObservableList<Client> data = FXCollections.observableArrayList();
    private static final ObservableList<Product> data_products = FXCollections.observableArrayList();
    private static final ObservableList<Department> data_departments = FXCollections.observableArrayList();

    private static ObservableList<Employee> data_employee = FXCollections.observableArrayList();

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

    public ObservableList<Employee> getData_employee() {
        return data_employee;
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
        getDeleteDepartment();
        showInfoEmployee();
        editInfoEmployees();
        getDeleteEmployee();


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
        ResultSet departmentResultSet = dbDepartment.Show();

        try {
            while (departmentResultSet.next()) {
                int departmentId = departmentResultSet.getInt("id");
                String departmentName = departmentResultSet.getString("name_department");
                String managerIdStr = departmentResultSet.getString("id_manager");

                Department department = new Department();
                department.setId(departmentId);
                department.setName(departmentName);

                if (managerIdStr != null) {
                    int managerId = Integer.parseInt(managerIdStr);
                    ResultSet insertManager = dbDepartment.InsertManager(managerId);
                    String managerName = null;
                    String surname = null;
                    String address = null;
                    String phone = null;

                    while (insertManager.next()) {
                        managerName = insertManager.getString("Name_employee");
                        surname = insertManager.getString("Surname");
                        address = insertManager.getString("Address");
                        phone = insertManager.getString("Phone");
                    }

                    Employee manager = new Employee();
                    manager.setId_emloyee(managerId);
                    manager.setName(managerName);
                    manager.setSurname(surname);
                    manager.setPhone(phone);
                    manager.setAddress(address);

                    department.setManager(manager);
                    manager.setDepartment(department);
                }

                // Создаем ComboBox для сотрудников отдела
                ResultSet namManager = dbDepartment.AddEmployee(departmentId);
                ComboBox<Employee> nameComboBox = new ComboBox<>();
                while (namManager.next()) {
                    Employee employee = new Employee(
                            namManager.getInt("id"),
                            namManager.getString("name_employee"),
                            namManager.getString("Surname"),
                            department
                    );
                    nameComboBox.getItems().add(employee);
                    nameComboBox.getSelectionModel().select(department.getManager());
                }

                nameComboBox.setOnAction(e -> {
                    Employee selectedEmployee = nameComboBox.getValue();
                    if (selectedEmployee != null) {
                        dbDepartment.editDateIndex(selectedEmployee.getDepartment().getId(), "id_manager", selectedEmployee.getId_emloyee());
                        alert.setContentText("Operation completed successfully");
                        alert.show();
                        showInfoDepartments();


                    }
                });

                department.setComboxManager(nameComboBox);
                data_departments.add(department);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        id_department.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name_department.setCellValueFactory(new PropertyValueFactory<>("name"));
        ComboxManager.setCellValueFactory(new PropertyValueFactory<>("ComboxManager"));
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

    public void getDeleteDepartment() {
        Callback<TableColumn<Department, String>, TableCell<Department, String>> cellFactory = (param) ->
        {
            final TableCell<Department, String> cellDepartment = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button deleteDepartment = new Button("DELETE");
                        deleteDepartment.setStyle("-fx-background-color: #FFCCCC; -fx-text-fill: black;");
                        deleteDepartment.setOnAction(event -> {

                            Department department = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText("Delete Department");
                            alert.setContentText("When this department is deleted, all employees belonging to this department will be deleted." +
                                    "To prevent this, change the employee's department in the table: Employee");

                            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);


                            Button yesButton = (Button) alert.getDialogPane().lookupButton(buttonTypeYes);
                            yesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

                            Button noButton = (Button) alert.getDialogPane().lookupButton(buttonTypeNo);
                            noButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                            Optional<ButtonType> result = alert.showAndWait();


                            if (result.isPresent() && result.get() == buttonTypeYes) {
                                dbDepartment.DeleteEmployee(department.getId());
                                dbDepartment.Delete(department.getId());


                                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                                deleteAlert.setTitle("Information");
                                deleteAlert.setHeaderText(null);
                                deleteAlert.setContentText("You deleted a department named: " + department.getName());
                                deleteAlert.show();

                                showInfoDepartments();
                            }
                        });
                        setGraphic(deleteDepartment);
                        setText(null);
                    }
                }
            };

            return cellDepartment;
        };

        DeleteDepartment.setCellFactory(cellFactory);
    }

    public void SearchInfoDepartment() {
        String searchInfo = SearchDepartmnet.getText();
        data_departments.clear();
        data_departments.addAll(dbDepartment.filterByName(searchInfo));

    }

//Employee
    private void showInfoEmployee() {
        data_employee.clear(); // Очищаем текущие данные сотрудников из коллекции

        try {
            ResultSet employeResalt = dbEmployee.Show(); // Получаем список сотрудников из базы данных

            while (employeResalt.next()) {
                Employee employee = new Employee();
                employee.setId_emloyee(employeResalt.getInt("id"));
                employee.setName(employeResalt.getString("Name_employee"));
                employee.setSurname(employeResalt.getString("Surname"));
                employee.setAddress(employeResalt.getString("Address"));
                employee.setPhone(employeResalt.getString("Phone"));
                String id_department = employeResalt.getString("id_department");

                // Если у сотрудника указан отдел, то извлекаем его данные
                if (id_department != null) {
                    int departmentId = Integer.parseInt(id_department);
                    ResultSet rst = dbEmployee.SearchDepartment(departmentId);
                    if (rst.next()) {
                        String departmentName = rst.getString("name_department");
                        int Id_manager = rst.getInt("id_manager");
                        Employee manager = new Employee(Id_manager);
                        Department department = new Department();
                        department.setId(departmentId);
                        department.setName(departmentName);
                        department.setManager(manager);
                        employee.setDepartment(department);
                    }
                    rst.close();
                }

                // Создаем ComboBox для выбора отдела сотрудника
                ComboBox<Department> nameComboBox = new ComboBox<>();
                ResultSet combobox_department = dbDepartment.AllDepartments();
                while (combobox_department.next()) {
                    Department department = new Department( combobox_department.getString("name_department"),combobox_department.getInt("id"));
                    nameComboBox.getItems().add(department);

                    // Выбираем текущий отдел сотрудника в ComboBox для редактирования
                    if (employee.getDepartment() != null && department.getId() == employee.getDepartment().getId()) {
                        nameComboBox.getSelectionModel().select(department);
                    }
                }
                combobox_department.close();

                // Обработка события выбора отдела в ComboBox
                nameComboBox.setOnAction(e -> {
                    Department editdepartment = nameComboBox.getValue();
                    if (editdepartment != null) {
                        if (employee.getDepartment() != null && employee.getDepartment().getManager().getId_emloyee() == employee.getId_emloyee()) {
                            // Сотрудник является менеджером текущего отдела
                            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmationAlert.setTitle("Подтвердите изменение");
                            confirmationAlert.setHeaderText("Сотрудник является менеджером текущего отдела");
                            confirmationAlert.setContentText("Если отдел сотрудника изменится, отдел останется без менеджера.\nВы уверены, что хотите продолжить?");

                            ButtonType buttonTypeYes  = new ButtonType("Да", ButtonBar.ButtonData.YES);
                            ButtonType noButton = new ButtonType("Нет", ButtonBar.ButtonData.NO);
                            confirmationAlert.getButtonTypes().setAll(buttonTypeYes , noButton);

                            Optional<ButtonType> result = confirmationAlert.showAndWait();
                            if (result.isPresent() && result.get() == buttonTypeYes ) {
                                // Удаляем менеджера из текущего отдела
                                dbDepartment.editManager(employee.getDepartment().getId());
                                // Обновляем отдел сотрудника
                                dbEmployee.editDateIndex(employee.getId_emloyee(), "id_department", editdepartment.getId());
                                alert.setContentText("Менеджер отдела был удален.");
                                alert.show();
                                // Обновляем информацию о сотрудниках и отделах
                                showInfoEmployee();
                                showInfoDepartments();
                            } else {
                                confirmationAlert.close();
                                showInfoEmployee();
                                showInfoDepartments();
                            }
                        } else {
                            // Обычное изменение отдела сотрудника
                            dbEmployee.editDateIndex(employee.getId_emloyee(), "id_department", editdepartment.getId());
                            alert.setContentText("Операция выполнена успешно");
                            alert.show();
                            showInfoEmployee();
                            showInfoDepartments();
                        }
                    }
                });

                // Устанавливаем ComboBox для выбора отдела сотрудника
                employee.setCombo_box_Department(nameComboBox);
                data_employee.add(employee); // Добавляем сотрудника в коллекцию для отображения
            }

            employeResalt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        id_employee.setCellValueFactory(new PropertyValueFactory<>("id_emloyee"));
        Name_employee.setCellValueFactory(new PropertyValueFactory<>("name"));
        Surname_Employee.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        Address_employee.setCellValueFactory(new PropertyValueFactory<>("address"));
        Phone_employee.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Department_employee.setCellValueFactory(new PropertyValueFactory<>("Combo_box_Department"));
    }
    private void editInfoEmployees() {
        Name_employee.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
        Name_employee.setOnEditCommit(event -> {
            Employee newemployee = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbEmployee.editDate(newemployee.getId_emloyee(), "Name_employee", newValue);
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoEmployee();
            }
        });

        Surname_Employee.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
        Surname_Employee.setOnEditCommit(event -> {
            Employee newemployee = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbEmployee.editDate(newemployee.getId_emloyee(), "Surname", newValue);
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoEmployee();
            }
        });


        Address_employee.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
        Address_employee.setOnEditCommit(event -> {
            Employee newemployee = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbEmployee.editDate(newemployee.getId_emloyee(), "Address", newValue);
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoEmployee();
            }
        });

        Phone_employee.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
        Phone_employee.setOnEditCommit(event -> {
            Employee newemployee = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            if (newValue != null && !newValue.isEmpty()) {
                dbEmployee.editDate(newemployee.getId_emloyee(), "Phone", newValue);
                alert.setContentText("Operation completed successfully!");
                alert.show();
            } else {
                alert.setContentText("Error, field is empty!");
                alert.show();
                showInfoEmployee();
            }
        });


    }
    public void getDeleteEmployee() {
        Callback<TableColumn<Employee, String>, TableCell<Employee, String>> cellFactory = (param) -> {
            final TableCell<Employee, String> cellEmployee = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button deleteEmployee = new Button("DELETE");
                        deleteEmployee.setStyle("-fx-background-color: #FFCCCC; -fx-text-fill: black;");
                        deleteEmployee.setOnAction(event -> {
                            Employee employee = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText("Delete Employee");
                            if (employee.getId_emloyee() == employee.getDepartment().getManager().getId_emloyee()) {
                                alert.setContentText("This employee is the manager of the " + employee.getDepartment() + " department.\nAre you sure you want to remove this employee?");
                            } else {
                                alert.setContentText("Are you sure you want to remove this employee?");
                            }

                            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == buttonTypeYes) {
                                dbDepartment.editManager(employee.getDepartment().getId());
                                dbEmployee.Delete(employee.getId_emloyee());
                                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                                deleteAlert.setTitle("Information");
                                deleteAlert.setHeaderText(null);
                                deleteAlert.setContentText("You deleted an employee: " + employee.getName() + " " + employee.getSurname());
                                deleteAlert.show();
                                showInfoDepartments();
                                showInfoEmployee();
                            }
                        });
                        setGraphic(deleteEmployee);
                        setText(null);
                    }
                }
            };
            return cellEmployee;
        };

        Delete_employee.setCellFactory(cellFactory);
    }







}







