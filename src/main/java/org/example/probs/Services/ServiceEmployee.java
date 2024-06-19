package org.example.probs.Services;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import org.example.probs.objects.Client;
import org.example.probs.objects.Department;
import org.example.probs.objects.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceEmployee implements  Service<Employee>{
    Connection connection;
    ResultSet resSet = null;

    public Connection getDBConnection() {
        String Connection = "jdbc:mysql://localhost:3306/it-company";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(Connection, "root", "Rtiop#iti567");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public ResultSet Show() {
        String getEmployee =  "SELECT id, Name_employee, Surname,Address,Phone,id_department,Commission " +
                "FROM employee";
        PreparedStatement prST = null;
        try {
            prST = getDBConnection().prepareStatement(getEmployee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            resSet = prST.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }
    public ResultSet SearchDepartment(int id_deparment)
    {String getDepartment = "SELECT name_department,id_manager FROM departments WHERE id = " + id_deparment ;

        PreparedStatement prST = null;
        try {
            prST = getDBConnection().prepareStatement(getDepartment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            resSet = prST.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;

    }
    public void editDateIndex(int id, String name_colm, int newInfo) {
        String query = "UPDATE employee SET " + name_colm + " = ? WHERE id= ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, newInfo);
            prST.setInt(2, id);

            prST.executeUpdate();

            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void editDate(int id, String name_colm, String newInfo) {
        String query = "UPDATE employee SET " + name_colm + " = ? WHERE id = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setString(1, newInfo);
            prST.setInt(2, id);

            prST.executeUpdate();

            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void Delete(int id_Employee) {
        String query = "DELETE FROM employee WHERE id = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, id_Employee);
            prST.executeUpdate();
            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editDateCommissio(int id, String name_colm, float newInfo) {
        String query = "UPDATE employee SET " + name_colm + " = ? WHERE id= ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setFloat(1, newInfo);
            prST.setInt(2, id);

            prST.executeUpdate();

            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void Add(Employee employee) {

        String insertEmployee= "INSERT INTO employee ( Name_employee, Surname, Address, Phone, Commission)" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(insertEmployee);
            prST.setString(1, employee.getName());
            prST.setString(2, employee.getSurname());
            prST.setString(3, employee.getAddress());
            prST.setString(4, employee.getPhone());
            prST.setFloat(5, employee.getCommission());
            prST.executeUpdate();
            prST.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> filterByName(String name) {
        try {
            List<Employee> employees = new ArrayList<>();
            String query = "SELECT * FROM employee";
            if (name.length() > 0) {
                query += " WHERE Name_employee LIKE ? OR Surname LIKE ? OR Address LIKE ? OR PHONE LIKE ? OR Commission LIKE ?";
            }
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            if (name.length() > 0) {
                prST.setString(1, "%"+name+"%");
                prST.setString(2, "%"+name+"%");
                prST.setString(3, "%"+name+"%");
                prST.setString(4, "%"+name+"%");
                prST.setString(5, "%"+name+"%");
            }

            ResultSet reSt = prST.executeQuery();


            while (reSt.next()) {
                int id_Employee = reSt.getInt(1);
                String name_employee = reSt.getString(2);
                String surname_employee = reSt.getString(3);
                String address_employee = reSt.getString(4);
                String id_deapartment = reSt.getString(5);
                String phone_employee = reSt.getString(6);
                float commission = reSt.getFloat(7);


                Employee employee = new Employee();
                 employee.setId_emloyee(id_Employee);
                 employee.setName(name_employee);
                 employee.setSurname(surname_employee);
                 employee.setAddress(address_employee);
                if (id_deapartment != null) {
                    int department = Integer.parseInt(id_deapartment);
                    ResultSet rst=SearchDepartment(department) ;
                    Department department1 = new Department();
                    String Name_department = null;
                    int id_manager;

                    while (rst.next()) {
                        Name_department = rst.getString(1);
                        id_manager=rst.getInt(2);
                        Employee manager = new Employee(id_manager);
                        department1.setManager(manager);
                        department1.setName(Name_department);
                    }
                    employee.setDepartment(department1);
                    employee.setCommission(commission);
                    employee.setPhone(phone_employee);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    ComboBox<Department> nameComboBox = new ComboBox<>();
                    ServiceDepertment dbDepartment = new ServiceDepertment();
                    ResultSet combobox_department = dbDepartment.AllDepartments();
                    while (combobox_department.next()) {
                        Department department2 = new Department( combobox_department.getString("name_department"),combobox_department.getInt("id"));
                        nameComboBox.getItems().add(department2);

                        // Выбираем текущий отдел сотрудника в ComboBox для редактирования
                        if (employee.getDepartment() != null && department1.getId() == employee.getDepartment().getId()) {
                            nameComboBox.getSelectionModel().select(department1);
                        }
                    }
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
                                    editDateIndex(employee.getId_emloyee(), "id_department", editdepartment.getId());
                                    alert.setContentText("Менеджер отдела был удален.");
                                    alert.show();

                                } else {
                                    confirmationAlert.close();
                                }
                            } else {
                                // Обычное изменение отдела сотрудника
                                editDateIndex(employee.getId_emloyee(), "id_department", editdepartment.getId());
                                alert.setContentText("Операция выполнена успешно");

                            }
                        }
                    });

                    employee.setCombo_box_Department(nameComboBox);

                }
                employees.add(employee);
            }
            return employees;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ResultSet SerachEmployee(int id)
    {String getDepartment = "SELECT Name_employee, Surname " +
            "FROM employee WHERE id = " + id;

        PreparedStatement prST = null;
        try {
            prST = getDBConnection().prepareStatement(getDepartment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            resSet = prST.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;

    }

    public ResultSet ShowEmployee() {
        String getEmployee =  "SELECT id, Name_employee, Surname " +
                "FROM employee";
        PreparedStatement prST = null;
        try {
            prST = getDBConnection().prepareStatement(getEmployee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            resSet = prST.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }





}
