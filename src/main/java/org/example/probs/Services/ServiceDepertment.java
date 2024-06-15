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


}
