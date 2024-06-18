package org.example.probs.Services;

import java.sql.*;

public class ServiceEmployee {
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
        String getEmployee =  "SELECT id, Name_employee, Surname,Address,Phone,id_department " +
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



}
