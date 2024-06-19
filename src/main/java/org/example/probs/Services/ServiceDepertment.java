package org.example.probs.Services;

import javafx.scene.control.ComboBox;
import org.example.probs.objects.Client;
import org.example.probs.objects.Department;
import org.example.probs.objects.Employee;
import org.example.probs.objects.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDepertment implements Service<Department> {

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
        String getDepartment =  "SELECT id, name_department, id_manager " +
                "FROM departments";
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


    public ResultSet InsertManager(int id)
    {String getDepartment = "SELECT Name_employee, Surname, Address, Phone " +
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

    public ResultSet AddEmployee(int id) {
        String getDepartment = "SELECT Name_employee, id,Surname " +
                "FROM employee " +
                "WHERE id_department = " + id;

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

    public void editDate(int id, String name_colm, String newInfo) {
        String query = "UPDATE departments SET " + name_colm + " = ? WHERE id = ?";
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

    public void editDateIndex(int id, String name_colm, int newInfo) {
        String query = "UPDATE departments SET " + name_colm + " = ? WHERE id = ?";
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
    public void Delete(int id_Department) {
        String query = "DELETE FROM departments WHERE id = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, id_Department);
            prST.executeUpdate();
            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void DeleteEmployee(int id_Department) {
        String query = "DELETE FROM employee WHERE  id_department = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, id_Department);
            prST.executeUpdate();
            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Add(Department department) {

        String insertDepartment = "INSERT INTO departments (name_department)" +
                "VALUES(?)";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(insertDepartment);
            prST.setString(1, department.getName());
            prST.executeUpdate();
            prST.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Department> filterByName(String name) {
        try {
            List<Department> departments = new ArrayList<>();
            String query = "SELECT * FROM departments";
            if (name.length() > 0) {
                query += " WHERE name_department LIKE ?";
            }
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            if (name.length() > 0) {
                prST.setString(1, "%"+name+"%");
            }

            ResultSet reSt = prST.executeQuery();


            while (reSt.next()) {
                int id_Deaprtment = reSt.getInt(1);
                String name_department = reSt.getString(2);
                String managerIdStr = reSt.getString("id_manager");
                Department department = new Department();
                department.setId(id_Deaprtment);
                department.setName(name_department);
                if (managerIdStr != null) {
                    int managerId = Integer.parseInt(managerIdStr);
                    ResultSet insertManager = InsertManager(managerId);
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
                    ResultSet namManager = AddEmployee(id_Deaprtment);
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
                            editDateIndex(selectedEmployee.getDepartment().getId(), "id_manager", selectedEmployee.getId_emloyee());
                        }
                    });

                    department.setComboxManager(nameComboBox);

                }
                departments.add(department);
            }
            return departments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet AllDepartments() {
        String getDepartment = "SELECT id, name_department " +
                "FROM departments";
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

    public void editManager(int id) {
        String query = "UPDATE departments SET  id_manager = NULL WHERE id = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, id);

            prST.executeUpdate();

            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





}
