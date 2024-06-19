package org.example.probs.Controllerr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.probs.Services.ServiceClient;
import org.example.probs.Services.ServiceEmployee;
import org.example.probs.objects.Client;
import org.example.probs.objects.Employee;

public class AddEmployeeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField InsertAddress;

    @FXML
    private TextField InsertCommission;

    @FXML
    private VBox InsertInfo;

    @FXML
    private TextField InsertName;

    @FXML
    private TextField InsertPhone;

    @FXML
    private TextField InsertSurname;

    @FXML
    private Button Save_Employee;

    ServiceEmployee DB= new ServiceEmployee();
    private static final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML

        void initialize() {
            Save_Employee.setOnAction(actionEvent -> {
                String name = InsertName.getText();
                String surname = InsertSurname.getText();
                String phone = InsertPhone.getText();
                String address = InsertAddress.getText();
                Float commission = parseCommission(InsertCommission.getText());

                if (isValidInput(name, surname, phone, address) && isValidCommission(commission)) {
                    Employee employee=new Employee();
                    employee.setName(name);
                    employee.setSurname(surname);
                    employee.setAddress(address);
                    employee.setPhone(phone);
                    employee.setCommission(commission);
                    DB.Add(employee);
                    alert.setContentText("Employee successfully added!\n You can manually select the employee's department");
                    alert.show();

                    // Закрытие текущего окна после добавления сотрудника
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                } else {
                    alert.setContentText("Error: Fields cannot be empty or invalid commission value!");
                    alert.show();
                }
            });
        }

        private boolean isValidInput(String name, String surname, String phone, String address) {
            return !name.isEmpty() && !surname.isEmpty() && !phone.isEmpty() && !address.isEmpty();
        }

        private Float parseCommission(String input) {
            try {
                Float commission = Float.parseFloat(input);
                return commission;
            } catch (NumberFormatException e) {
                return null; // Если не удалось преобразовать в Float, возвращаем null
            }
        }

        private boolean isValidCommission(Float commission) {
            return commission != null && commission >= 0.0f && commission <= 1.0f;
        }
}