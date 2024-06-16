package org.example.probs.Services;

import java.sql.*;

public class ServiceDepertment {

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
        String getDepartment =  "SELECT d.id, d.name_department, e.id AS employee_id, e.Name_employee, e.Surname, e.Address, e.Phone " +
                "FROM departments d " +
                "INNER JOIN employee e ON d.id_manager = e.id";
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

    public ResultSet AddEmployee(int id_department) {
        String getDepartment = "SELECT Name_employee " +
                "FROM employee " +
                "WHERE id_department = " + id_department;

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



}
